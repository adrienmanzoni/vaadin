package com.magnolia.tutorial.vaadin.ui.view;

import com.vaadin.navigator.View;

public interface BaseView extends View {

    /**
     * @param presenter
     *            the presenter to set
     */
    void setPresenter(BaseViewListener presenter);
}