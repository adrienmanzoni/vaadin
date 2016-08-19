package com.magnolia.tutorial.vaadin.container;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.magnolia.tutorial.vaadin.bean.BrowsableBean;
import com.magnolia.tutorial.vaadin.service.TeamService;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;

public class DaoTreeContainer extends AbstractDaoContainer implements Container.Hierarchical {
    private static final long serialVersionUID = 1L;
    /** Logger */
    private static final Logger LOG = Logger.getLogger(DaoTreeContainer.class);

    /** List of root {@link DatabaseItemId} */
    protected Collection<DatabaseItemId> rootItemIds;
    /** List of parent-children relations */
    protected Map<DatabaseItemId, Collection<DatabaseItemId>> childrenItemIds;
    /**
     * List of flags checking if the lazy loading of the children has been
     * processed
     */
    protected Map<DatabaseItemId, Boolean> childrenLazyLoad;
    /** List of child-parent relations */
    protected Map<DatabaseItemId, DatabaseItemId> parentItemIds;
    /** List of children allowed flags per {@link DatabaseItemId} */
    protected Map<DatabaseItemId, Boolean> childrenAllowed;

    /**
     * Constructor
     *
     * @param dao
     *            The Team service
     * @param columnsDefinitions
     *            The columns definition
     */
    public DaoTreeContainer(TeamService dao, List<String> columnsDefinitions) {
	super(dao, columnsDefinitions);
    }

    @Override
    protected void initVariables() {
	this.rootItemIds = new LinkedList<>();
	this.childrenLazyLoad = new HashMap<>();
	this.parentItemIds = new HashMap<>();
	this.childrenAllowed = new HashMap<>();
	this.childrenItemIds = new HashMap<>();
    }

    @Override
    public Collection<?> rootItemIds() {
	return this.rootItemIds;
    }

    @Override
    public boolean areChildrenAllowed(Object itemId) {
	boolean areChildrenAllowed = false;

	if (this.childrenAllowed.containsKey(itemId)) {
	    areChildrenAllowed = this.childrenAllowed.get(itemId);
	}

	return areChildrenAllowed;
    }

    @Override
    public boolean hasChildren(Object itemId) {
	return !CollectionUtils.isEmpty(this.getChildren(itemId));
    }

    @Override
    public Collection<?> getChildren(Object itemId) {
	Collection<DatabaseItemId> children = null;

	// Checks the lazy loading status of the item
	this.checkChildrenLazyLoad(itemId);

	if (this.childrenItemIds.containsKey(itemId)) {
	    children = this.childrenItemIds.get(itemId);
	}

	return children;
    }

    @Override
    public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed) throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean setParent(Object itemId, Object newParentId) throws UnsupportedOperationException {
	boolean isValid = false;

	// Checks whether both of the ids are null or instance of the container
	// accepted item id class
	if ((itemId instanceof DatabaseItemId) && ((newParentId == null) || (newParentId instanceof DatabaseItemId))) {
	    // Checks whether the parent is null, and whether the relation
	    // between item and parent is valid
	    DatabaseItemId dbItemId = (DatabaseItemId) itemId;
	    DatabaseItemId dbNewParentId = (DatabaseItemId) newParentId;
	    if ((newParentId != null) && this.isMoveValid(dbItemId, dbNewParentId)) {
		// Moves the item id
		this.moveItem(dbItemId, dbNewParentId);
	    }
	}

	return isValid;
    }

    @Override
    public boolean isRoot(Object itemId) {
	// Vaadin trigger an infinite loop in case a null itemId is returned
	// (https://dev.vaadin.com/ticket/8860)
	boolean isRoot = true;

	if (itemId != null) {
	    isRoot = this.rootItemIds.contains(itemId);
	}

	return isRoot;
    }

    @Override
    public Object getParent(Object itemId) {
	DatabaseItemId parentItemId = null;

	if (itemId instanceof DatabaseItemId) {
	    parentItemId = ((DatabaseItemId) itemId).getParent();
	}

	return parentItemId;
    }

    @Override
    protected void loadRootElements() {
	// Reset the global variables
	this.resetGlobalItemsVariable();

	// Retrieve root element from the provided DAO
	Collection<BrowsableBean> rootElements = this.dao.getRootElements();

	if (!CollectionUtils.isEmpty(rootElements)) {
	    if (LOG.isDebugEnabled()) {
		LOG.debug(String.format("DaoTreeContainer - loadRootElements() - %d results", rootElements.size()));
	    }

	    Iterator<BrowsableBean> it = rootElements.iterator();
	    int queryIndex = 0;
	    while (it.hasNext()) {
		// Creates the BeanItem from the BrowsableBean
		this.createBeanItem(it.next(), null, queryIndex++);
	    }
	    LOG.debug("DaoTreeContainer - loadRootElements() - no results");
	} else if (LOG.isDebugEnabled()) {
	    LOG.debug("DaoTreeContainer - loadRootElements() - no results");
	}
    }

    /**
     * Load the children of the given item
     *
     * @param itemId
     *            The item id
     */
    protected void loadChildrenElelements(DatabaseItemId itemId) {
	// Retrieve children element from the provided DAO
	Collection<BrowsableBean> chidlrenElements = this.dao.getChildrenElements(itemId.getIds());

	if (!CollectionUtils.isEmpty(chidlrenElements)) {
	    if (LOG.isDebugEnabled()) {
		LOG.debug(String.format("DaoTreeContainer - loadChildrenElelements(%s) - %d results", itemId.getIds(),
			chidlrenElements.size()));
	    }

	    Iterator<BrowsableBean> it = chidlrenElements.iterator();
	    int queryIndex = 0;
	    while (it.hasNext()) {
		// Creates the BeanItem from the BrowsableBean
		this.createBeanItem(it.next(), itemId, queryIndex++);
	    }
	} else if (LOG.isDebugEnabled()) {
	    LOG.debug(String.format("DaoTreeContainer - loadChildrenElelements(%s) - no results", itemId.getIds()));
	}
    }

    /**
     * Creates the BeanItem from the BrowsableBean
     *
     * @param browsableBean
     *            The BrowsableBean
     * @param dbParentItemId
     *            The container parent item id
     * @param queryIndex
     *            The order of the record in the query result
     * @return The container id created
     */
    protected DatabaseItemId createBeanItem(BrowsableBean browsableBean, DatabaseItemId dbParentItemId,
	    int queryIndex) {
	// Builds the container item id
	DatabaseItemId dbItemId = new DatabaseItemId(browsableBean.getIdChain());
	if (dbParentItemId != null) {
	    int[] orders = Arrays.copyOf(dbParentItemId.getOrders(), dbParentItemId.getOrders().length + 1);
	    orders[dbParentItemId.getOrders().length] = queryIndex;
	    dbItemId.setOrders(orders);
	} else {
	    dbItemId.setOrders(queryIndex);
	}

	// Instantiate a bean item and add the global property nodeName
	BeanItem<BrowsableBean> beanItem = new BeanItem<>(browsableBean);
	beanItem.addItemProperty(BrowsableBean.NODE_NAME, new ObjectProperty<String>(browsableBean.getNodeName()));

	// Adds the bean item to the map of items
	this.internalAddItem(dbItemId, beanItem);
	// Set the children allowed flag
	this.childrenAllowed.put(dbItemId, browsableBean.areChildrenAllowed());
	// Set the parent
	if (dbParentItemId != null) {
	    // Set the child-parent relation
	    this.parentItemIds.put(dbItemId, dbParentItemId);
	} else {
	    // Adds the bean item to the list of root item
	    this.rootItemIds.add(dbItemId);
	}

	// In case of non lazy loading, the children might be already filled,
	// then recursively built the items tree
	if (!CollectionUtils.isEmpty(browsableBean.getChildren())) {
	    // Flag the lazy loading to Done
	    this.childrenLazyLoad.put(dbItemId, true);

	    Iterator<BrowsableBean> it = browsableBean.getChildren().iterator();
	    Collection<DatabaseItemId> childrenIds = new LinkedList<>();
	    int subQueryIndex = 0;
	    while (it.hasNext()) {
		// Creates the BeanItem from the BrowsableBean
		childrenIds.add(this.createBeanItem(it.next(), dbItemId, subQueryIndex++));
	    }

	    // Set the children item ids list
	    this.childrenItemIds.put(dbItemId, childrenIds);
	}

	return dbItemId;
    }

    /**
     * Checks the lazy loading status of the related ite, If not done yet, calls
     * the DAO and retrieves the children
     *
     * @param itemId
     *            The item id
     */
    protected void checkChildrenLazyLoad(Object itemId) {
	if (itemId instanceof DatabaseItemId) {
	    DatabaseItemId dbItemId = (DatabaseItemId) itemId;

	    if (!this.childrenLazyLoad.containsKey(dbItemId) || !this.childrenLazyLoad.get(dbItemId)) {
		// Flag the children lazy loading as done
		this.childrenLazyLoad.put(dbItemId, true);

		// Loads the children
		this.loadChildrenElelements(dbItemId);
	    }
	}
    }

    @Override
    protected void resetGlobalItemsVariable() {
	super.resetGlobalItemsVariable();

	this.rootItemIds.clear();
	this.childrenItemIds.clear();
	this.childrenAllowed.clear();
	this.childrenLazyLoad.clear();
	this.parentItemIds.clear();
    }

    @Override
    protected void removeItemFromChildrenList(DatabaseItemId itemId) {
	super.removeItemFromChildrenList(itemId);

	// Removes the global variables
	this.rootItemIds.remove(itemId);
	this.childrenAllowed.remove(itemId);
	this.childrenLazyLoad.remove(itemId);
	this.parentItemIds.remove(itemId);

	// Removes recursively the children
	Collection<DatabaseItemId> childrenIds = this.childrenItemIds.get(itemId);
	if (!CollectionUtils.isEmpty(childrenIds)) {
	    Iterator<DatabaseItemId> it = childrenIds.iterator();
	    while (it.hasNext()) {
		this.removeItemFromChildrenList(it.next());
	    }
	}
	this.childrenItemIds.remove(itemId);
    }

    /**
     * Returns whether the move is valid
     *
     * @param itemId
     *            The item id to move
     * @param newParentId
     *            The wished parent
     * @return Whether the move is valid
     */
    protected boolean isMoveValid(DatabaseItemId itemId, DatabaseItemId newParentId) {
	boolean isValid = false;

	if (newParentId == null) {
	    // Can item can only be a root item id
	    isValid = this.rootItemIds.contains(itemId);
	} else {
	    // Check whether the database item of the parent contains one level
	    // less than the item to move
	    isValid = (newParentId.getIds().length == (itemId.getIds().length - 1));
	}

	return isValid;
    }

    /**
     * Moves the item to the new parent
     *
     * @param itemId
     *            The item id to move
     * @param newParentId
     *            The wished parent
     */
    protected void moveItem(DatabaseItemId itemId, DatabaseItemId newParentId) {
	DatabaseItemId formerParentId = this.parentItemIds.get(itemId);

	if ((formerParentId == null) || !formerParentId.equals(newParentId)) {
	    // Change the child-parent relation
	    this.parentItemIds.put(itemId, newParentId);
	    // Change the parent-children relation for the former parent
	    if (formerParentId != null) {
		this.childrenItemIds.get(formerParentId).remove(itemId);
	    }
	    // Change the parent-children relation for the new parent
	    Collection<DatabaseItemId> childrenIds = this.childrenItemIds.get(newParentId);
	    if (childrenIds == null) {
		childrenIds = new LinkedList<>();
	    }
	    childrenIds.add(itemId);
	    this.childrenItemIds.put(newParentId, childrenIds);

	    // Delegates the move to the DAO
	    this.dao.move(itemId.getIds(), newParentId.getIds());
	}
    }
}