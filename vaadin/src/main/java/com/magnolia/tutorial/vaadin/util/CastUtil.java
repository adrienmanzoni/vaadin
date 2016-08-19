package com.magnolia.tutorial.vaadin.util;

import java.util.Collection;

public class CastUtil {

    private CastUtil() {
	// Not accessible
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> castList(Collection<?> collection) {
	return (Collection<T>) collection;
    }
}