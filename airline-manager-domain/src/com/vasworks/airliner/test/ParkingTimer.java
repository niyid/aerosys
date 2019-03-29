package com.vasworks.airliner.test;

public class ParkingTimer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i = 3; i < 13; i++) {
			System.out.println(i + " hours => " + (Math.ceil((double)i / 3) * 3 - 3));
		}
	}

}
