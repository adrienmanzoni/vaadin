package com.magnolia.tutorial.vaadin.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.service.TeamService;
import com.magnolia.tutorial.vaadin.ui.view.Tutorial3View;

@Component
@Scope("prototype")
public class Tutorial3Presenter extends AbstractPresenter implements Tutorial3View.Tutorial3ViewListener {
    @Autowired
    private TeamService teamService;

    public TeamService getTeamService() {
	return this.teamService;
    }
}