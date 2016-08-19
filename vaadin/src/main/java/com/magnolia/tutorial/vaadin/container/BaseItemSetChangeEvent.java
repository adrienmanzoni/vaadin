package com.magnolia.tutorial.vaadin.container;

import java.io.Serializable;
import java.util.EventObject;

import com.vaadin.data.Container;

public class BaseItemSetChangeEvent extends EventObject implements Container.ItemSetChangeEvent, Serializable {
    private static final long serialVersionUID = 1L;

    protected BaseItemSetChangeEvent(Container source) {
	super(source);
    }

    @Override
    public Container getContainer() {
	return (Container) this.getSource();
    }
}