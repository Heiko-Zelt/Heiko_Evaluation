package de.heikozelt.objectdetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.heikozelt.objectdetection.xml.GmafCollection;

public class Eval {
	private static String resultXmlFilename = "result.xml";
	private static String annotationsFilename = "annotations.csv";

	private static Logger logger = LogManager.getLogger(Eval.class);

	/**
	 * Liest die XML-Datei mit den Bilder-Dateinamen, erkannten Objekten und
	 * weiteren Infos. Es wird die JAXB-Bibiothek verwendet und die Java-Klassen im
	 * Package de.heikozelt.objectdetection.xml.
	 * 
	 * @param resultXmlFilename
	 * @return
	 * @throws JAXBException
	 */
	private static GmafCollection readResults(String xmlFilename) throws JAXBException {
		File file = new File(xmlFilename);
		JAXBContext jaxbContext = JAXBContext.newInstance(GmafCollection.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (GmafCollection) jaxbUnmarshaller.unmarshal(file);
	}

	/**
	 * Liest die Annotationen aus der CSV-Datei Erste Spalte enthält den Dateinamen.
	 * Weitere Spalten die Objekt-Klassen
	 * 
	 * @param csvFilename
	 * @return
	 * @throws IOException
	 */
	private static HashMap<String, ImageWithObjects> readAnnotations(String csvFilename) throws IOException {
		File file = new File(csvFilename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		HashMap<String, ImageWithObjects> map = new HashMap<String, ImageWithObjects>();
		String line;
		while ((line = br.readLine()) != null) {
			ImageWithObjects img = new ImageWithObjects(line);
			map.put(img.getId(), img);
		}
		br.close();
		return map;
	}

	/**
	 * Checks, if keys of 2 maps are the same
	 * 
	 * @param map1
	 * @param map2
	 * @return true, if same
	 */
	public static Boolean areMapKeysEqual(HashMap<String, ImageWithObjects> map1,
			HashMap<String, ImageWithObjects> map2) {
		if (map1.size() != map2.size()) {
			logger.debug("Maps have different size.");
			return false;
		}
		for (String key1 : map1.keySet())
			if (!map2.containsKey(key1)) {
				logger.debug("Second map doesn't contain key '" + key1 + "'.");
				return false;
			}
		return true;
	}

	public static void evaluate(HashMap<String, ImageWithObjects> annotations,
			HashMap<String, ImageWithObjects> detected) {
		SortedSet<String> sortedKeys = new TreeSet<String>();
		sortedKeys.addAll(detected.keySet());
		float sumRecall = 0f;
		float sumPrecision = 0f;
		for (String key : sortedKeys) {
			String[] annos = annotations.get(key).getObjects();
			String[] dets = detected.get(key).getObjects();
			MultiSet annosMulti = new MultiSet(annos);
			MultiSet detsMulti = new MultiSet(dets);
			logger.info("key: " + key);
			logger.debug("  annotations: " + String.join(",", annos));
			logger.debug("  detected: " + String.join(",", dets));
			List<String> truePositives = annosMulti.intersectOccurences(dets);
			logger.debug("  true positives: " + truePositives);
			List<String> falsePositives = detsMulti.diffOccurences(dets);
			logger.debug("  false positives: " + falsePositives);
			List<String> falseNegatives = annosMulti.diffOccurences(dets);
			logger.debug("  false negatives: " + falseNegatives);
			float precision;
			if (dets.length == 0) {
				// nichts erkannt, also auch keine falsch erkannt
				precision = 1;
			} else {
				precision = truePositives.size() / (float) dets.length;
			}
			logger.info("  precision: " + precision);
			float recall;
			if (annos.length == 0) {
				recall = (dets.length == 0) ? 1 : 0;
			} else {
				recall = truePositives.size() / (float) annos.length;
			}
			logger.info("  recall: " + recall);
			sumPrecision += precision;
			sumRecall += recall;
		}
		float overallPrecision = sumPrecision / sortedKeys.size();
		float overallRecall = sumRecall / sortedKeys.size();
		logger.info("overall precision: " + overallPrecision);
		logger.info("overall recall: " + overallRecall);
	}

	/**
	 * Hauptprogramm. Liest 2 Dateien. Beide enthalten Dateinamen von Bildern. Eine
	 * enthält Annotationen. Die andere enthält erkannte Objekte. Die Annotationen
	 * werden mit den erkannten Objekten verglichen.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.info("Evaluation batch job started.");

		GmafCollection gmafCollection = readResults(resultXmlFilename);
		HashMap<String, ImageWithObjects> annotations = readAnnotations(annotationsFilename);
		HashMap<String, ImageWithObjects> detected = gmafCollection.asImagesWithObjects();
		Boolean eq = areMapKeysEqual(detected, annotations);
		logger.debug("Keys equal:" + eq);
		if (!eq) {
			logger.error(
					"Anzahl und/oder Dateinamen der Bilder mit Anotationen und erkannten Objekten ist unterschiedlich!");
			return;
		}
		if(annotations.size() == 0) {
			logger.error("Keine Eingabedaten!");
			return;
		}
		evaluate(annotations, detected);
		logger.info("Evaluation batch job finished.");
	}

}
