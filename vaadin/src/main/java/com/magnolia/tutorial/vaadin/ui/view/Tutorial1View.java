package com.magnolia.tutorial.vaadin.ui.view;

import java.util.Collection;

import com.magnolia.tutorial.vaadin.bean.Team;

public interface Tutorial1View extends BaseView {

    /** Interface used to communicate between ViewImpl and Presenter */
    public interface Tutorial1ViewListener extends BaseViewListener {

	/**
	 * Returns all teams
	 *
	 * @return The whole list of teams
	 */
	Collection<Team> loadTeams();
    }
}