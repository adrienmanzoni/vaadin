package com.magnolia.tutorial.vaadin;

import org.springframework.context.ApplicationContext;

public class SpringApplicationContext {
    /** Spring Application Context */
    private static ApplicationContext applicationContext;

    /**
     * Constructor
     */
    private SpringApplicationContext() {
	// Nothing
    }

    /**
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
	return applicationContext;
    }

    /**
     * @param applicationContext
     *            the applicationContext to set
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
	SpringApplicationContext.applicationContext = applicationContext;
    }

    /**
     * Returns the bean from the given name
     *
     * @param beanName
     *            The bean name
     * @return The associated bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
	T bean = null;

	if (applicationContext != null) {
	    bean = (T) applicationContext.getBean(beanName);
	}

	return bean;
    }
}