package com.vasworks.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Collection utilities
 * 
 * @author developer
 * 
 */
public class Collections {

	/**
	 * Convert an array of objects to a collection
	 * 
	 * @param tt
	 * @return instance of {@link ArrayList}
	 */
	public static Collection<Object> fromArray(Object[] tt) {
		if (tt == null)
			return null;

		Collection<Object> collection = new ArrayList<Object>();
		for (Object t : tt)
			collection.add(t);
		return collection;
	}

	/**
	 * Find an object in collection using comparator
	 * 
	 * @param list
	 * @param toFind
	 * @param comparator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object match(List<?> list, Object toFind, Comparator comparator) {
		for (Object x : list) {
			if (comparator.compare(x, toFind) == 0)
				return x;
		}
		return null;
	}
}
