package com.magnolia.tutorial.vaadin.ui.view;

import com.vaadin.ui.CustomComponent;

public abstract class BaseViewImpl extends CustomComponent implements BaseView {
    private static final long serialVersionUID = 1L;

    /** The registered presenter */
    protected BaseViewListener presenter;

    @Override
    public void setPresenter(BaseViewListener presenter) {
	this.presenter = presenter;
    }

    /**
     * @return the presenter
     */
    public BaseViewListener getPresenter() {
	return this.presenter;
    }
}