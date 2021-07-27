package de.heikozelt.objectdetection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MultiSetTest {
	
	/**
	 * Differenz zwischen 2 MultiSets
	 * (jedes Element wird nur einmal abgezogen) 
	 */
	@Test
	public void testDiff() {
		String[] a1 = {"a", "b", "b", "c"};
		String[] a2 = {"a", "b"};
        MultiSet ms1 = new MultiSet(a1);
        List<String> result = ms1.diffOccurences(a2);
		assertEquals(2, result.size());
		assertEquals(result.get(0), "b");
		assertEquals(result.get(1), "c");
	}

	@Test
	public void testIntersect() {
		String[] a1 = {"a", "b", "b", "c"};
		String[] a2 = {"a", "b", "b", "d"};
        MultiSet ms1 = new MultiSet(a1);
        List<String> result = ms1.intersectOccurences(a2);
		assertEquals(3, result.size());
		assertEquals(result.get(0), "a");
		assertEquals(result.get(1), "b");
		assertEquals(result.get(2), "b");
	}	
	
}
