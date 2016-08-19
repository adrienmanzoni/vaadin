package com.magnolia.tutorial.vaadin.ui.presenter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.bean.Team;
import com.magnolia.tutorial.vaadin.service.TeamService;
import com.magnolia.tutorial.vaadin.ui.view.Tutorial1View;

@Component
@Scope("prototype")
public class Tutorial1Presenter extends AbstractPresenter implements Tutorial1View.Tutorial1ViewListener {
    @Autowired
    private TeamService teamService;

    @Override
    public Collection<Team> loadTeams() {
	return this.teamService.getAll();
    }
}