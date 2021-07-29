package de.heikozelt.objectdetection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrecisionAndRecallTest {
	
	/**
	 * Testet die Berechnung von Precision und Recall anhand eines Beispiels
	 * 2 von 4 Objekten erkannt.
	 */
	@Test
	public void testCalculate1() {
		String[] annos = {"a", "b", "b", "c"};
		String[] dets = {"a", "b"};
		PrecisionAndRecall preAndRe = new PrecisionAndRecall(annos, dets); 

		assertEquals(1f, preAndRe.getPrecision());
		assertEquals(0.5f, preAndRe.getRecall());
	}
	
	/**
	 * Testet die Berechnung von Precision und Recall anhand eines Beispiels.
	 * Keine Objekte erkannt.
	 */
	@Test
	public void testCalculate2() {
		String[] annos = {"a", "b", "b", "c"};
		String[] dets = {};
		PrecisionAndRecall preAndRe = new PrecisionAndRecall(annos, dets); 

		assertEquals(1f, preAndRe.getPrecision());
		assertEquals(0f, preAndRe.getRecall());
	}
	
	/**
	 * Testet die Berechnung von Precision und Recall anhand eines Beispiels
	 * Alle Objekte falsch erkannt.
	 */
	@Test
	public void testCalculate3() {
		String[] annos = {"a", "b", "b", "c"};
		String[] dets = {"d", "e", "f"};
		PrecisionAndRecall preAndRe = new PrecisionAndRecall(annos, dets); 

		assertEquals(0f, preAndRe.getPrecision());
		assertEquals(0f, preAndRe.getRecall());
	}
	
	/**
	 * Testet die Berechnung von Precision und Recall anhand eines Beispiels
	 * Ein Objekt erkannt, wo keins ist.
	 */
	@Test
	public void testCalculate4() {
		String[] annos = {};
		String[] dets = {"a"};
		PrecisionAndRecall preAndRe = new PrecisionAndRecall(annos, dets); 

		assertEquals(0f, preAndRe.getPrecision());
		assertEquals(0f, preAndRe.getRecall());
	}
}
