package com.cmu.journalmap.storage;

import java.util.ArrayList;
import java.util.List;

import com.cmu.journalmap.models.Place;

public class Places {
	
	private static List<Place> items = new ArrayList<Place>();

	public static List<Place> getItems() {
		return items;
	}

	//read from file or ?
	public static void setItems(List<Place> items) {
		Places.items = items;
	}
	
}
