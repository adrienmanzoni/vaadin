package com.magnolia.tutorial.vaadin.ui.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.ui.MainUI;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class MainViewImpl extends BaseViewImpl implements MainView {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public MainViewImpl() {
	this.setSizeFull();
	VerticalLayout layout = new VerticalLayout();

	Label label = new Label("Hello");
	layout.addComponent(label);

	// Tutorial 1
	Button button = new Button("Go to Grid BeanItem View",
		(ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(MainUI.TUTORIAL1));
	layout.addComponent(button);
	layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

	// Tutorial 2
	button = new Button("Go to Table - DaoListContainer View",
		(ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(MainUI.TUTORIAL2));
	layout.addComponent(button);
	layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

	// Tutorial 3
	button = new Button("Go to Tree - DaoTreeContainer View",
		(ClickListener) event -> UI.getCurrent().getNavigator().navigateTo(MainUI.TUTORIAL3));
	layout.addComponent(button);
	layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

	this.setCompositionRoot(layout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
	Notification.show("Welcome to the Vaadin tutorial");
    }
}