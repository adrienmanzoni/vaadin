package com.magnolia.tutorial.vaadin.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
public class MyVaadinServlet extends VaadinServlet {
    private static final long serialVersionUID = 1L;
}