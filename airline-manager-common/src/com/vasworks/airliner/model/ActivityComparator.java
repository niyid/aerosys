package com.vasworks.airliner.model;

import java.util.Comparator;

public class ActivityComparator implements Comparator<Activity> {

	@Override
	public int compare(Activity o1, Activity o2) {
		int flag = 0;
		if(o1 != null && o2 != null && o1.getStartTime() != null && o2.getStartTime() != null) {
			flag = o1.getStartTime().compareTo(o2.getStartTime());
		} else if(o1 == null || o1.getStartTime() == null) {
			flag = 1;
		} else if(o2 == null || o2.getStartTime() == null) {
			flag = -1;
		}
		
		if(o1 != null && o2 != null && o1.getEndTime() != null && o2.getEndTime() != null) {
			flag += o1.getEndTime().compareTo(o2.getEndTime());
		} else if(o1 == null || o1.getEndTime() == null) {
			flag += 1;
		} else if(o2 == null || o2.getEndTime() == null) {
			flag += -1;
		}
		
		return flag;
	}

}
