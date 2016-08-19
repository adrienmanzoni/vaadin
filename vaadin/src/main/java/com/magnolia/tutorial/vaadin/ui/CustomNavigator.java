package com.magnolia.tutorial.vaadin.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.magnolia.tutorial.vaadin.SpringApplicationContext;
import com.magnolia.tutorial.vaadin.ui.presenter.AbstractPresenter;
import com.magnolia.tutorial.vaadin.ui.view.BaseView;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

public class CustomNavigator extends Navigator {
    private static final long serialVersionUID = 1L;

    /** Synchronized view presenter Map */
    protected final Map<String, String> viewPresenterMap = Collections.synchronizedMap(new HashMap<String, String>());

    /**
     * Constructor
     *
     * @param ui
     * @param container
     */
    public CustomNavigator(UI ui, ComponentContainer container) {
	super(ui, container);
    }

    /**
     * Constructor
     *
     * @param ui
     * @param stateManager
     * @param display
     */
    public CustomNavigator(UI ui, NavigationStateManager stateManager, ViewDisplay display) {
	super(ui, stateManager, display);
    }

    /**
     * Constructor
     *
     * @param ui
     * @param container
     */
    public CustomNavigator(UI ui, SingleComponentContainer container) {
	super(ui, container);
    }

    /**
     * Constructor
     *
     * @param ui
     * @param display
     */
    public CustomNavigator(UI ui, ViewDisplay display) {
	super(ui, display);
    }

    /**
     * Adds the presenter and the related view to the navigator
     *
     * @param viewName
     *            The view name
     * @param viewClass
     *            The view class
     * @param presenterClass
     *            The presenter class
     */
    public void addBeanViewPresenter(String viewName, Class<? extends View> viewClass,
	    Class<? extends AbstractPresenter> presenterClass) {
	ApplicationContext applicationContext = SpringApplicationContext.getApplicationContext();

	// Checks parameters
	if ((viewName == null) || (viewClass == null) || (presenterClass == null)) {
	    throw new IllegalArgumentException("view , viewClass and presenterClass must be non-null");
	}

	// Checks whether the view is registered in the spring context
	String[] beanNames = applicationContext.getBeanNamesForType(viewClass);
	if (beanNames.length != 1) {
	    throw new IllegalArgumentException(
		    "cant't resolve Spring View bean name for class :" + viewClass.getName());
	}

	// Checks whether the presenter is registered in the spring context
	String[] presenterNames = applicationContext.getBeanNamesForType(presenterClass);
	if (presenterNames.length != 1) {
	    throw new IllegalArgumentException(
		    "cant't resolve Spring Presenter bean name for class :" + presenterClass.getName());
	}

	// Adds the view
	super.addView(viewName, viewClass);
	this.viewPresenterMap.put(viewName, presenterNames[0]);
    }

    @Override
    protected void navigateTo(View view, String viewName, String parameters) {
	// Assigned the presenter to the view
	if (view instanceof BaseView) {
	    // Get an the related presenter name
	    String presenterName = this.viewPresenterMap.get(viewName);
	    if (presenterName != null) {
		// Get an instance of the related presenter
		AbstractPresenter presenter = SpringApplicationContext.getBean(presenterName);

		// Link it to the view
		((BaseView) view).setPresenter(presenter);
	    } else {
		throw new RuntimeException(String.format("No presenter linked to the view '%s'", viewName));
	    }
	} else {
	    throw new RuntimeException(String.format("Invalid view interface '%s'", view.getClass()));
	}

	super.navigateTo(view, viewName, parameters);
    }
}