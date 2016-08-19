package com.magnolia.tutorial.vaadin.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.container.DaoTreeContainer;
import com.magnolia.tutorial.vaadin.ui.view.Tutorial3View.Tutorial3ViewListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class Tutorial3ViewImpl extends BaseViewImpl implements Tutorial1View {
    private static final long serialVersionUID = 1L;

    @Override
    public void enter(ViewChangeEvent event) {
	Notification.show("Welcome to the Grid - BeanItem view");

	VerticalLayout layout = new VerticalLayout();
	TreeTable tree = new TreeTable();

	layout.setMargin(true);

	// List of columns
	List<String> columns = new ArrayList<>();
	columns.add("nodeName");
	columns.add("fullName");
	columns.add("birthDate");
	columns.add("nationality");

	// fetch list of teams from service and assign it to tree
	tree.setContainerDataSource(
		new DaoTreeContainer(((Tutorial3ViewListener) this.presenter).getTeamService(), columns));

	// add Grid to the layout
	layout.addComponents(tree);

	this.setCompositionRoot(layout);
    }
}
