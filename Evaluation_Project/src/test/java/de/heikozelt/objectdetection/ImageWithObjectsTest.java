package de.heikozelt.objectdetection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * JUnit-Tests für die Klasse ImageWithObjetcs
 * @author Heiko Zelt
 */
public class ImageWithObjectsTest {
	
	/**
	 * Testet Getter- und Setter-Methoden
	 */
	@Test
	public void testSettersAndGetters() {
		ImageWithObjects img = new ImageWithObjects();
		String[] objects1 = {"person", "Bicycle"}; 
		img.setId("img001.png");
		img.setObjects(objects1);
		String[] objects2 = img.getObjects();
		assertEquals("img001.png", img.getId());
		assertEquals("bicycle", objects2[0]);
		assertEquals("person", objects2[1]);
	}
	
	/**
	 * Testet das Parsen einer Zeile einer CSV-Datei
	 */
	@Test
	public void testParseCSVLine1() {
		String line = "\"img001.png\",\"Surfboard\",\"person\",\"bicycle\"";
		ImageWithObjects img = new ImageWithObjects(line);
		assertEquals("img001.png", img.getId());
		String[] objects2 = img.getObjects();
		assertEquals(3, objects2.length);
		assertEquals("bicycle", objects2[0]);
		assertEquals("person", objects2[1]);
		assertEquals("surfboard", objects2[2]);
	}
	
	/**
	 * Testet das Parsen einer Zeile einer CSV-Datei. Nur Dateiname, keine Labels.
	 */
	@Test
	public void testParseCSVLine2() {
		String line = "img001.png";
		ImageWithObjects img = new ImageWithObjects(line);
		assertEquals("img001.png", img.getId());
		String[] objects2 = img.getObjects();
		assertEquals(0, objects2.length);
	}	

	/**
	 * Testet das Parsen einer Zeichenkette mit Länge 0
	 */
	@Test
	public void testParseCSVLine3() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ImageWithObjects("");
		});
	}
	
	/**
	 * Testet das Parsen mit einer Leerstring-Objektklasse
	 */
	@Test
	public void testParseCSVLine4() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ImageWithObjects("img001,,person");
		});
	}
}
