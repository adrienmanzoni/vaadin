package com.magnolia.tutorial.vaadin.ui.view;

import java.util.Collection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.bean.Team;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class Tutorial1ViewImpl extends BaseViewImpl implements Tutorial1View {
    private static final long serialVersionUID = 1L;

    @Override
    public void enter(ViewChangeEvent event) {
	Notification.show("Welcome to the Grid - BeanItem view");

	VerticalLayout layout = new VerticalLayout();
	Grid grid = new Grid();

	layout.setMargin(true);

	// fetch list of teams from service and assign it to Grid
	Collection<Team> teams = ((Tutorial1ViewListener) this.presenter).loadTeams();
	grid.setContainerDataSource(new BeanItemContainer<>(Team.class, teams));

	grid.setColumns("name", "creationDate", "creationUser");

	// add Grid to the layout
	layout.addComponents(grid);

	this.setCompositionRoot(layout);
    }
}
