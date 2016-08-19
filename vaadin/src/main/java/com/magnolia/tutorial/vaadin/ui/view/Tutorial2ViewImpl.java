package com.magnolia.tutorial.vaadin.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.container.DaoListContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class Tutorial2ViewImpl extends BaseViewImpl implements Tutorial2View {
    private static final long serialVersionUID = 1L;

    @Override
    public void enter(ViewChangeEvent event) {
	Notification.show("Welcome to the Table - DaoListContainer view");

	VerticalLayout layout = new VerticalLayout();
	Table table = new Table();

	layout.setMargin(true);

	// List of columns
	List<String> columns = new ArrayList<>();
	columns.add("teamName");
	columns.add("fullName");
	columns.add("birthDate");
	columns.add("nationality");

	// fetch list of team players from service and assign it to table
	table.setContainerDataSource(
		new DaoListContainer(((Tutorial2ViewListener) this.presenter).getTeamService(), columns));

	// add table to the layout
	layout.addComponents(table);

	this.setCompositionRoot(layout);
    }
}
