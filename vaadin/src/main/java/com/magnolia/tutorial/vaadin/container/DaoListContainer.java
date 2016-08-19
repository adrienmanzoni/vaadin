package com.magnolia.tutorial.vaadin.container;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.magnolia.tutorial.vaadin.service.TeamService;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;

public class DaoListContainer extends AbstractDaoContainer {
    private static final long serialVersionUID = 1L;
    /** Logger */
    private static final Logger LOG = Logger.getLogger(DaoListContainer.class);

    /**
     * Constructor
     *
     * @param dao
     *            The Team service
     * @param columnsDefinitions
     *            The columns definition
     */
    public DaoListContainer(TeamService dao, List<String> columnsDefinitions) {
	super(dao, columnsDefinitions);
    }

    /**
     * Fill the list elements with the provided DAO results
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void loadRootElements(String searchFilter) {
	// Reset the global variables
	this.resetGlobalItemsVariable();

	// Retrieve list of element from the provided DAO
	Collection<Object[]> listElements = null;
	if (searchFilter != null) {
	    listElements = this.dao.getListElements(searchFilter);
	} else {
	    listElements = this.dao.getListElements();
	}

	if (!CollectionUtils.isEmpty(listElements)) {
	    if (LOG.isDebugEnabled()) {
		LOG.debug(String.format("%s - loadRootElements(%s) - %d results", this.getClass(), searchFilter,
			listElements.size()));
	    }

	    Iterator<Object[]> it = listElements.iterator();
	    Iterator<String> itp = null;
	    Object[] lineItem = null;
	    PropertysetItem item = null;
	    DatabaseItemId itemId = null;
	    int queryIndex = 0, index = 1;
	    while (it.hasNext()) {
		lineItem = it.next();
		itemId = new DatabaseItemId((String) lineItem[0]);
		itemId.setOrders(queryIndex);

		// Instantiate a bean item and add the global property nodeName
		item = new PropertysetItem();
		itp = this.orderedProperties.iterator();
		index = 1;
		while (itp.hasNext()) {
		    item.addItemProperty(itp.next(), new ObjectProperty(lineItem[index++]));
		}

		// Adds the bean item to the global lists
		this.internalAddItem(itemId, item);
	    }
	} else if (LOG.isDebugEnabled()) {
	    LOG.debug(String.format("&s - loadRootElements(%s) - no results", this.getClass(), searchFilter));
	}
    }

    @Override
    protected void loadRootElements() {
	this.loadRootElements(null);
    }
}