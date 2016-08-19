package com.magnolia.tutorial.vaadin.ui.presenter;

import com.magnolia.tutorial.vaadin.ui.view.BaseViewListener;
import com.vaadin.navigator.View;

public abstract class AbstractPresenter implements BaseViewListener {
    /** Related Vaadin view */
    protected View view = null;

    /**
     * Constructor
     *
     * @param view
     *            The related Vaadin view
     */
    public void setView(View view) {
	this.view = view;
    }
}