package com.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapExample {

	public static void main(String[] args) {

		Map<Object, String> mp = new HashMap<Object, String>();

		// adding or set elements in Map by put method key and value pair
		mp.put(new Integer(2), "Two");
		mp.put(new Integer(1), "One");
		mp.put(new Integer(3), "Three");
		mp.put(new Integer(4), "Four");

		Map<Object, String> mp2 = new HashMap<Object, String>();
		mp2.put(new Integer(2), "Two2");
		mp2.put(new Integer(1), "One2");
		mp2.put(new Integer(3), "Three2");
		mp2.put(new Integer(4), "Four2");

		Map<String, Object> mpMaps = new HashMap<String, Object>();

		mpMaps.put("Map1", mp);
		mpMaps.put("Map2", mp2);

		System.out.println("This is a map of Maps:   " + mpMaps);

		for (int i = 0; i < mpMaps.size(); i++) {
			ArrayList<Object> a = new ArrayList<Object>(mpMaps.keySet());
			Object o = a.get(i);
			System.out.println("all together: " + mpMaps.size() + "each element is:  " + o + " value: " + mpMaps.get(o));
		}
	}
}
