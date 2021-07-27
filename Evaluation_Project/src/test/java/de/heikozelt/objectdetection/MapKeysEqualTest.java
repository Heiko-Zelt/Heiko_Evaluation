package de.heikozelt.objectdetection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MapKeysEqualTest {

	/**
	 * Vergleicht Keys einer leere HashMap mit Keys der selben HashMap 
	 */
	@Test
	public void testAreMapKeysEqual1() {
		HashMap<String, ImageWithObjects> map1 = new HashMap<String, ImageWithObjects>();
		assertTrue(Eval.areMapKeysEqual(map1, map1));
	}
	
	/**
	 * Vergleicht Keys einer HashMap mit einem Eintrag mit Keys der selben HashMap 
	 */
	@Test
	public void testAreMapKeysEqual2() {
		HashMap<String, ImageWithObjects> map1 = new HashMap<String, ImageWithObjects>();
		String[] objects = {"bicyle", "surfboard", "cat" };
		ImageWithObjects img = new ImageWithObjects("test.png", objects);
		map1.put("test.png", img);
		assertTrue(Eval.areMapKeysEqual(map1, map1));
	}
	
	/**
	 * Vergleicht Keys zweier HashMaps mit einem und keinem Eintrag 
	 */
	@Test
	public void testAreMapKeysEqual3() {
		HashMap<String, ImageWithObjects> map1 = new HashMap<String, ImageWithObjects>();
		HashMap<String, ImageWithObjects> map2 = new HashMap<String, ImageWithObjects>();
		String[] objects = {"bicyle", "surfboard", "cat" };
		ImageWithObjects img = new ImageWithObjects("test.png", objects);
		map1.put("test.png", img);
		assertFalse(Eval.areMapKeysEqual(map1, map2));
	}
}
