package de.heikozelt.objectdetection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.heikozelt.objectdetection.xml.BoundBox;
import de.heikozelt.objectdetection.xml.DetObject;
import de.heikozelt.objectdetection.xml.DetObjects;
import de.heikozelt.objectdetection.xml.GmafCollection;
import de.heikozelt.objectdetection.xml.GmafData;

/**
 * JUnit-Tests für die Klasse Eval
 * @author Heiko Zelt
 */
public class EvalTest {

	/**
	 * Liest eine Beispiel-XML-Datei ein
	 */
	@Test
	public void testReadResults() {
		try {
			GmafCollection gmafCollection = Eval.readResults("src/test/resources/inputFiles1/result.xml");
			List<GmafData> gmafDatas = gmafCollection.getGmafDatas();
			assertEquals(10, gmafDatas.size());
			GmafData firstGmafData = gmafDatas.get(0);
			assertEquals("0423.png", firstGmafData.getFile());
			DetObjects objects = firstGmafData.getDetObjects();
			List<DetObject> objectsList = objects.getDetObjectsList();
			DetObject firstObject = objectsList.get(0);
			assertEquals("Tortoise", firstObject.getTerm());
			BoundBox bb = firstObject.getBoundBox();
			assertEquals(195, bb.getX());
			assertEquals(74, bb.getY());
			assertEquals(1804, bb.getWidth());
			assertEquals(1168, bb.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown any exception");
		}

	}
	
	/**
	 * Liest eine Beispiel-CSV-Datei ein
	 */
	@Test
	public void testReadAnnotations() {
		try {
			HashMap<String, ImageWithObjects> annotations = Eval.readAnnotations("src/test/resources/inputFiles1/annotations.csv");
			assertEquals(10, annotations.size());
			ImageWithObjects firstImg = annotations.get("0003.png");
			assertEquals("0003.png", firstImg.getId());
			String[] objects = firstImg.getObjects();
			assertEquals(10, objects.length);
			assertEquals("palm tree", objects[0]);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown any exception");
		}
	}
	
	/**
	 * testet die Berechung von Durchschnitts-Precision und -Recall-Wert.
	 * Extremfall: Null Bilder
	 */
	@Test
	public void testEvaluate1() {
		HashMap<String, ImageWithObjects> annotations = new HashMap<String, ImageWithObjects>();
		HashMap<String, ImageWithObjects> detected = new HashMap<String, ImageWithObjects>();
		PrecisionAndRecall average = Eval.evaluate(annotations, detected);
		assertEquals(1f, average.getPrecision());
		assertEquals(1f, average.getRecall());
	}
	
	/**
	 * testet die Berechung von Durchschnitts-Precision und -Recall-Wert.
	 * Fall: 1 Bild, alles richtig erkannt.
	 */
	@Test
	public void testEvaluate2() {
		String filename = "img001.png";
		String[] classnames = { "Cat", "person", "surfboard" };
		HashMap<String, ImageWithObjects> annotations = new HashMap<String, ImageWithObjects>();
		annotations.put(filename, new ImageWithObjects(filename, classnames));
		HashMap<String, ImageWithObjects> detected = new HashMap<String, ImageWithObjects>();
		detected.put(filename, new ImageWithObjects(filename, classnames));
		PrecisionAndRecall average = Eval.evaluate(annotations, detected);
		assertEquals(1f, average.getPrecision());
		assertEquals(1f, average.getRecall());
	}
	
	/**
	 * testet die Berechung von Durchschnitts-Precision und -Recall-Wert.
	 * fall: 1 Bild, nichts richtig erkannt.
	 */
	@Test
	public void testEvaluate3() {
		String filename = "img001.png";
		String[] classnames1 = { "Cat", "person", "surfboard" };
		String[] classnames2 = { "Dog", "fish", "snowboard" };
		HashMap<String, ImageWithObjects> annotations = new HashMap<String, ImageWithObjects>();
		annotations.put(filename, new ImageWithObjects(filename, classnames1));
		HashMap<String, ImageWithObjects> detected = new HashMap<String, ImageWithObjects>();
		detected.put(filename, new ImageWithObjects(filename, classnames2));
		PrecisionAndRecall average = Eval.evaluate(annotations, detected);
		assertEquals(0f, average.getPrecision());
		assertEquals(0f, average.getRecall());
	}	
	
	/**
	 * testet die Berechung von Durchschnitts-Precision und -Recall-Wert.
	 * Fall: 2 Bilder, Hälfte richtig erkannt.
	 */
	@Test
	public void testEvaluate4() {
		String filename1 = "img001.png";
		String filename2 = "img002.png";
		String[] classnames1 = { "Cat", "person", "dog", "surfboard" };
		String[] classnames2 = { "Dog", "person", "snowboard", "bicycle" };
		HashMap<String, ImageWithObjects> annotations = new HashMap<String, ImageWithObjects>();
		annotations.put(filename1, new ImageWithObjects(filename1, classnames1));
		annotations.put(filename2, new ImageWithObjects(filename2, classnames2));
		HashMap<String, ImageWithObjects> detected = new HashMap<String, ImageWithObjects>();
		detected.put(filename1, new ImageWithObjects(filename1, classnames2));
		detected.put(filename2, new ImageWithObjects(filename2, classnames1));
		PrecisionAndRecall average = Eval.evaluate(annotations, detected);
		assertEquals(0.5f, average.getPrecision());
		assertEquals(0.5f, average.getRecall());
	}	

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
		String[] objects = { "bicyle", "surfboard", "cat" };
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
		String[] objects = { "bicyle", "surfboard", "cat" };
		ImageWithObjects img = new ImageWithObjects("test.png", objects);
		map1.put("test.png", img);
		assertFalse(Eval.areMapKeysEqual(map1, map2));
	}
}
