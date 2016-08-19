package com.magnolia.tutorial.vaadin.service;

import java.util.Collection;

import com.magnolia.tutorial.vaadin.bean.BrowsableBean;
import com.magnolia.tutorial.vaadin.bean.Team;

public interface TeamService {

    /**
     * Returns all teams
     *
     * @return The whole team list
     */
    Collection<Team> getAll();

    /**
     * Delete the record
     *
     * @param idChain
     *            The id chain
     */
    void delete(long[] idChain);

    /**
     * Delete all
     */
    void deleteAll();

    /**
     * Return the list of elements
     *
     * @param searchFilter
     *            The search filter
     * @return The list of element
     */
    Collection<Object[]> getListElements(String searchFilter);

    /**
     * Return the whole list of elements
     *
     * @return The whole list of elements
     */
    Collection<Object[]> getListElements();

    /**
     * Move one player from one team from another one
     *
     * @param playerIdChain
     *            The player id chain
     * @param teamIdChain
     *            The team id chain
     */
    void move(long[] playerIdChain, long[] teamIdChain);

    /**
     * Returns the list of root elements
     *
     * @return
     */
    Collection<BrowsableBean> getRootElements();

    /**
     * Returns the list of children elements
     *
     * @param idChain
     *            The id chain
     * @return The list of children elements
     */
    Collection<BrowsableBean> getChildrenElements(long[] idChain);
}