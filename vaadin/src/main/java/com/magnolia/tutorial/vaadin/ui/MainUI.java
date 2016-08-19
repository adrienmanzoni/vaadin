package com.magnolia.tutorial.vaadin.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.magnolia.tutorial.vaadin.ui.presenter.MainPresenter;
import com.magnolia.tutorial.vaadin.ui.presenter.Tutorial1Presenter;
import com.magnolia.tutorial.vaadin.ui.presenter.Tutorial2Presenter;
import com.magnolia.tutorial.vaadin.ui.presenter.Tutorial3Presenter;
import com.magnolia.tutorial.vaadin.ui.view.MainViewImpl;
import com.magnolia.tutorial.vaadin.ui.view.Tutorial1ViewImpl;
import com.magnolia.tutorial.vaadin.ui.view.Tutorial2ViewImpl;
import com.magnolia.tutorial.vaadin.ui.view.Tutorial3ViewImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
@Title("My Vaadin tutorials")
@Widgetset("com.magnolia.tutorial.vaadin.MyAppWidgetset")
@Theme("mytheme")
public class MainUI extends UI {
    private static final long serialVersionUID = 1L;

    /** Main UI - List of tutorials */
    public static final String MAIN = "mainview";
    /** Tutorial 1 UI - Grid and BeanItem */
    public static final String TUTORIAL1 = "tutorial1view";
    /** Tutorial 2 UI - Table and DaoListContainer */
    public static final String TUTORIAL2 = "tutorial2view";
    /** Tutorial 3 UI - Tree and DaoTreeContainer */
    public static final String TUTORIAL3 = "tutorial3view";

    @Override
    protected void init(VaadinRequest request) {
	CustomNavigator navigator = new CustomNavigator(this, this);

	// Set me to UI so that I can be used even from other views.
	this.setNavigator(navigator);

	// Add the views and presenters to the MVPDiscoveryNavigator
	navigator.addBeanViewPresenter(MAIN, MainViewImpl.class, MainPresenter.class);
	navigator.addBeanViewPresenter(TUTORIAL1, Tutorial1ViewImpl.class, Tutorial1Presenter.class);
	navigator.addBeanViewPresenter(TUTORIAL2, Tutorial2ViewImpl.class, Tutorial2Presenter.class);
	navigator.addBeanViewPresenter(TUTORIAL3, Tutorial3ViewImpl.class, Tutorial3Presenter.class);

	// Navigate to the desired View. The presenter also will be tied up with
	// the view
	navigator.navigateTo(MAIN);
    }
}