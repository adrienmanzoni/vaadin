package com.magnolia.tutorial.vaadin.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.magnolia.tutorial.vaadin.service.TeamService;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public abstract class AbstractDaoContainer implements Container.Indexed, Container.ItemSetChangeNotifier {
    private static final long serialVersionUID = 1L;

    /** List of change listeners */
    protected List<ItemSetChangeListener> listeners = new LinkedList<ItemSetChangeListener>();
    /** The browsable bean DAO */
    protected TeamService dao;

    /** List of {@link Item} per {@link DatabaseItemId} */
    protected Map<DatabaseItemId, Item> items = new HashMap<>();
    /** List of item ids sorted for indexing purpose */
    protected List<DatabaseItemId> sortedItemIds = new ArrayList<>();

    /** List of container properties id */
    protected Map<String, Class<?>> propertyIds = new HashMap<>();
    /** List of item ids ordered like in the configuration */
    protected List<String> orderedProperties = new ArrayList<>();

    /**
     * Constructor
     *
     * @param dao
     *            The Team service
     * @param columnsDefinitions
     *            The columns definition
     */
    protected AbstractDaoContainer(TeamService dao, List<String> columnsDefinitions) {
	// Set the container properties
	if (!CollectionUtils.isEmpty(columnsDefinitions)) {
	    Iterator<String> it = columnsDefinitions.iterator();
	    String columnDefinition = null;
	    while (it.hasNext()) {
		columnDefinition = it.next();

		// Add the property to the container
		this.addContainerProperty(columnDefinition, String.class, "");
	    }
	}

	// Loads the DAO
	this.dao = dao;

	// Recomputes the list elements
	this.initVariables();
	this.loadRootElements();
    }

    /**
     * Init the subclass variables before the loadRootElements call
     */
    protected void initVariables() {

    }

    @Override
    public int size() {
	return this.items.size();
    }

    @Override
    public Class<?> getType(Object propertyId) {
	return this.propertyIds.get(propertyId);
    }

    @Override
    public Object nextItemId(Object itemId) {
	Object result = null;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    int index = this.indexOfId(itemId);
	    if (index < (this.sortedItemIds.size() - 1)) {
		result = this.sortedItemIds.get(index + 1);
	    }
	}

	return result;
    }

    @Override
    public Object prevItemId(Object itemId) {
	Object result = null;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    int index = this.indexOfId(itemId);
	    if (index > 0) {
		result = this.sortedItemIds.get(index - 1);
	    }
	}

	return result;
    }

    @Override
    public Object firstItemId() {
	Object result = null;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    result = this.sortedItemIds.get(0);
	}

	return result;
    }

    @Override
    public Object lastItemId() {
	Object result = null;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    result = this.sortedItemIds.get(this.sortedItemIds.size() - 1);
	}

	return result;
    }

    @Override
    public boolean isFirstId(Object itemId) {
	boolean result = false;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    result = this.sortedItemIds.get(0).equals(itemId);
	}

	return result;
    }

    @Override
    public boolean isLastId(Object itemId) {
	boolean result = false;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    result = this.sortedItemIds.get(this.sortedItemIds.size()).equals(itemId);
	}

	return result;
    }

    @Override
    public int indexOfId(Object itemId) {
	return this.sortedItemIds.indexOf(itemId);
    }

    @Override
    public List<?> getItemIds(int startIndex, int numberOfItems) {
	List<DatabaseItemId> result = null;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    result = new ArrayList<>();
	    int size = this.sortedItemIds.size();
	    if (startIndex < size) {
		int i = startIndex;

		while (i < (startIndex + numberOfItems)) {
		    if (i < size) {
			result.add(this.sortedItemIds.get(i));
			i++;
		    } else {
			i = Integer.MAX_VALUE;
		    }
		}
	    }
	}

	return result;
    }

    @Override
    public Object getIdByIndex(int index) {
	Object result = false;

	if (!CollectionUtils.isEmpty(this.sortedItemIds)) {
	    result = this.sortedItemIds.get(index);
	}

	return result;
    }

    @Override
    public Collection<?> getItemIds() {
	Collection<DatabaseItemId> results = new LinkedList<>();
	Iterator<DatabaseItemId> it = this.items.keySet().iterator();
	while (it.hasNext()) {
	    results.add(it.next());
	}
	return results;
    }

    @Override
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
	boolean isValid = false;

	if ((itemId instanceof DatabaseItemId) && this.items.containsKey(itemId)) {
	    isValid = true;

	    // Removes recursively from the global variables
	    this.removeItemFromChildrenList((DatabaseItemId) itemId);

	    // Delegates the delete object to the DAO
	    this.dao.delete(((DatabaseItemId) itemId).getIds());
	}

	return isValid;
    }

    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
	// Reset all global variables linked to items
	this.resetGlobalItemsVariable();

	// Delegates the delete all to the DAO
	this.dao.deleteAll();

	return true;
    }

    @Override
    public Item getItem(Object itemId) {
	return this.items.get(itemId);
    }

    @Override
    public boolean containsId(Object itemId) {
	return this.items.containsKey(itemId);
    }

    @Override
    public Property<?> getContainerProperty(Object itemId, Object propertyId) {
	Item item = this.items.get(itemId);

	if (item != null) {
	    return item.getItemProperty(propertyId);
	} else {
	    return null;
	}
    }

    @Override
    public Collection<?> getContainerPropertyIds() {
	Collection<String> results = new LinkedList<>();
	Iterator<String> it = this.propertyIds.keySet().iterator();
	while (it.hasNext()) {
	    results.add(it.next());
	}
	return results;
    }

    @Override
    public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue)
	    throws UnsupportedOperationException {
	boolean isValid = false;

	if (propertyId instanceof String) {
	    this.propertyIds.put((String) propertyId, type);
	    this.orderedProperties.add((String) propertyId);
	    isValid = true;
	}

	return isValid;
    }

    @Override
    public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
	boolean isValid = false;

	if (this.propertyIds.containsKey(propertyId)) {
	    this.propertyIds.remove(propertyId);
	}

	return isValid;
    }

    @Override
    public Item addItem(Object itemId) throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public Object addItemAt(int index) throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public Item addItemAt(int index, Object newItemId) throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public Object addItemAfter(Object previousItemId) throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public Item addItemAfter(Object previousItemId, Object newItemId) throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public void addItemSetChangeListener(ItemSetChangeListener listener) {
	this.listeners.add(listener);
    }

    @Override
    public void addListener(ItemSetChangeListener listener) {
	this.listeners.add(listener);
    }

    @Override
    public void removeItemSetChangeListener(ItemSetChangeListener listener) {
	this.listeners.remove(listener);
    }

    @Override
    public void removeListener(ItemSetChangeListener listener) {
	this.listeners.remove(listener);
    }

    /**
     * Notify of an item set changes
     */
    protected void notifyItemSetChange() {
	final Object[] l = this.listeners.toArray();
	BaseItemSetChangeEvent event = new BaseItemSetChangeEvent(this);
	for (int i = 0; i < l.length; i++) {
	    ((ItemSetChangeListener) l[i]).containerItemSetChange(event);
	}
    }

    /**
     * Initiate the root elements nodes
     */
    protected abstract void loadRootElements();

    /**
     * Reset all the global variables linked to items
     */
    protected void resetGlobalItemsVariable() {
	this.items.clear();
	this.sortedItemIds.clear();
    }

    /**
     * Removes recursively an item from the global variables
     *
     * @param itemId
     *            The item id to delete
     */
    protected void removeItemFromChildrenList(DatabaseItemId itemId) {
	// Removes the global variables
	this.items.remove(itemId);
	this.sortedItemIds.remove(itemId);
    }

    /**
     * Adds the item to the global list
     *
     * @param itemId
     *            The item id
     * @param beanItem
     *            The item
     */
    protected void internalAddItem(DatabaseItemId itemId, Item beanItem) {
	this.items.put(itemId, beanItem);
	this.sortedItemIds.add(itemId);
	Collections.sort(this.sortedItemIds);
    }
}