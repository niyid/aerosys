/*
 * 
 */
package com.vasworks.util;

import java.io.Serializable;
import java.util.Comparator;

import com.vasworks.entity.MySqlBaseEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityComparator.
 */
public class EntityComparator implements Comparator<MySqlBaseEntity>, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8678875900948083012L;
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(MySqlBaseEntity arg0, MySqlBaseEntity arg1) {
		int val = -1;
		
		if(arg0 != null && arg1 != null) {		
			long val0 = arg0.getId() == null ? 0 : arg0.getId();
			long val1 = arg1.getId() == null ? 0 : arg1.getId();			
			val = (int) (val0 - val1);
		}
		
		return val;
	}

}
