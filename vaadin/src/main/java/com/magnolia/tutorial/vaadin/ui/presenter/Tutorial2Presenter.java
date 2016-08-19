package com.magnolia.tutorial.vaadin.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.service.TeamService;
import com.magnolia.tutorial.vaadin.ui.view.Tutorial2View;

@Component
@Scope("prototype")
public class Tutorial2Presenter extends AbstractPresenter implements Tutorial2View.Tutorial2ViewListener {
    @Autowired
    private TeamService teamService;

    @Override
    public TeamService getTeamService() {
	return this.teamService;
    }
}