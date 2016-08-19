package com.magnolia.tutorial.vaadin.bean;

import java.io.Serializable;
import java.util.Collection;

public interface BrowsableBean extends Serializable {
    String NODE_NAME = "nodeName";

    /**
     * Return the node id
     *
     * @return the node id
     */
    long[] getIdChain();

    /**
     * Return the node name
     *
     * @return the node name
     */
    String getNodeName();

    /**
     * Return the list of children
     *
     * @return The list of children
     */
    <T extends BrowsableBean> Collection<T> getChildren();

    /**
     * Returns whether the children are allowed
     *
     * @return Whether the children are allowed
     */
    boolean areChildrenAllowed();
}