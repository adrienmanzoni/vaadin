package com.magnolia.tutorial.vaadin.ui.view;

import com.magnolia.tutorial.vaadin.service.TeamService;

public interface Tutorial3View extends BaseView {

    /** Interface used to communicate between ViewImpl and Presenter */
    public interface Tutorial3ViewListener extends BaseViewListener {

	/**
	 * Return the team service
	 *
	 * @return The team service
	 */
	TeamService getTeamService();
    }
}