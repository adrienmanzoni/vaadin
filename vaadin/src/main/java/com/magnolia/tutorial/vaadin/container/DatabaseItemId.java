package com.magnolia.tutorial.vaadin.container;

import java.io.Serializable;
import java.util.Arrays;

/** The Database item ids object */
public class DatabaseItemId implements Serializable, Comparable<DatabaseItemId> {
    private static final long serialVersionUID = 1L;

    /** List of database ids composing the key */
    private long[] ids;
    /** Order of the record in the query */
    private int[] orders;

    /**
     * Constructor
     *
     * @param ids
     *            The list of DB ids composing the key
     */
    public DatabaseItemId(long... ids) {
	this.ids = ids;
    }

    /**
     * Constructor
     *
     * @param idString
     *            The id string (id separated with _)
     */
    public DatabaseItemId(String idString) {
	String[] idArray = idString.split("_");
	this.ids = new long[idArray.length];
	for (int i = 0; i < idArray.length; i++) {
	    this.ids[i] = Long.parseLong(idArray[i]);
	}
    }

    /**
     * @return the ids
     */
    public long[] getIds() {
	return this.ids;
    }

    /**
     * Returns the parent database item id
     *
     * @return The parent database item id
     */
    public DatabaseItemId getParent() {
	DatabaseItemId parent = null;

	if ((this.ids != null) && (this.ids.length > 1)) {
	    long[] parentIds = new long[this.ids.length - 1];
	    for (int i = 0; i < (this.ids.length - 1); i++) {
		parentIds[i] = this.ids[i];
	    }
	    parent = new DatabaseItemId(parentIds);
	}

	return parent;
    }

    /**
     * @return the orders
     */
    public int[] getOrders() {
	return this.orders;
    }

    /**
     * @param orders
     *            the orders to set
     */
    public void setOrders(int... orders) {
	this.orders = orders;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = (prime * result) + Arrays.hashCode(this.ids);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	DatabaseItemId other = (DatabaseItemId) obj;
	if (!Arrays.equals(this.ids, other.ids)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();

	for (int i = 0; i < this.ids.length; i++) {
	    builder.append(this.ids[i]);
	    if (i < (this.ids.length - 1)) {
		builder.append(" > ");
	    }
	}

	return builder.toString();
    }

    @Override
    public int compareTo(DatabaseItemId toCompare) {
	int result = 0;

	if ((toCompare == null) || (toCompare.getOrders() == null)) {
	    result = 1;
	} else if (this.getOrders() == null) {
	    result = -1;
	} else {
	    int maxLevel = Math.max(this.getOrders().length, toCompare.getOrders().length);
	    Integer order = 0, orderToCompare = 0;
	    int i = 0;
	    while ((result == 0) && (i < maxLevel)) {
		order = this.getSafeOrder(this.getOrders(), i);
		orderToCompare = this.getSafeOrder(toCompare.getOrders(), i);

		result = order.compareTo(orderToCompare);
		i++;
	    }
	}

	return result;
    }

    /**
     * Method which get the value from the array and the given position
     * Integer.MAX_VALUE otherwise
     *
     * @param array
     *            The array
     * @param position
     *            The wished position
     * @return The related value
     */
    private Integer getSafeOrder(int[] array, int position) {
	Integer result = Integer.MAX_VALUE;

	if (position < array.length) {
	    result = array[position];
	}

	return result;
    }
}