package de.heikozelt.objectdetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.heikozelt.objectdetection.xml.GmafCollection;

/**
 * Vergleicht Ergebnis der Objekt-Erkennung (XML-Datei) mit den Annotationen
 * (CSV-Datei). Zu jedem Bild wird der Precision- und Recall-Wert berechnet. Als
 * Gesamt-Ergebnis wird der Durchschnitts-Precision- und Recall-Wert berechnet.
 * 
 * @author Heiko Zelt
 */
public class Eval {
	private static String resultXmlFilename = "detected/result.xml";
	private static String annotationsFilename = "annotations.csv";

	private static Logger logger = LogManager.getLogger(Eval.class);

	/**
	 * Liest eine XML-Datei mit den Bilder-Dateinamen, erkannten Objekten und
	 * weiteren Infos. Es wird die JAXB-Bibliothek verwendet und die Java-Klassen im
	 * Package de.heikozelt.objectdetection.xml.
	 * 
	 * @param xmlFilename input filename
	 * @return GmafCollection, baumartige Struktur von Objekten (Document Object
	 *         Tree)
	 * @throws JAXBException
	 */
	public static GmafCollection readResults(String xmlFilename) throws JAXBException {
		File file = new File(xmlFilename);
		JAXBContext jaxbContext = JAXBContext.newInstance(GmafCollection.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (GmafCollection) jaxbUnmarshaller.unmarshal(file);
	}

	/**
	 * Liest Annotationen aus einer CSV-Datei. Erste Spalte enthält den Dateinamen.
	 * Weitere Spalten die Objektklassen. Beispiel:
	 * "img001.png","Cat","Bicycle","Surfboard","Palm tree"
	 * 
	 * @param csvFilename Beispiel: "annotations.csv"
	 * @return HashMap mit Dateinamen als Key
	 * @throws IOException
	 */
	public static HashMap<String, ImageWithObjects> readAnnotations(String csvFilename) throws IOException {
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
	 * Checks, if keys of 2 maps are equal.
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

	/**
	 * Berechnet aus den Annotationen und den erkannten Objekten die Precision und
	 * Recall-Werte. Die beiden HashMaps verwenden Dateinamen als Keys.
	 * 
	 * @param annotations HashMap mit Annotationen
	 * @param detected    HashMap mit erkannten Objekten
	 */
	public static PrecisionAndRecall evaluate(HashMap<String, ImageWithObjects> annotations,
			HashMap<String, ImageWithObjects> detected) {
		SortedSet<String> sortedKeys = new TreeSet<String>();
		sortedKeys.addAll(detected.keySet());
		float averagePrecision;
		float averageRecall;
		if (sortedKeys.size() == 0) {
			averagePrecision = 1f;
			averageRecall = 1f;
		} else {
			float sumRecall = 0f;
			float sumPrecision = 0f;
			PrecisionAndRecall preAndRe = new PrecisionAndRecall();
			for (String key : sortedKeys) {
				logger.info("key: " + key);
				String[] annos = annotations.get(key).getObjects();
				String[] dets = detected.get(key).getObjects();
				preAndRe.calculate(annos, dets);
				sumPrecision += preAndRe.getPrecision();
				sumRecall += preAndRe.getRecall();
			}
			averagePrecision = sumPrecision / sortedKeys.size();
			averageRecall = sumRecall / sortedKeys.size();
		}
		return new PrecisionAndRecall(averagePrecision, averageRecall);
	}

	/**
	 * Hauptprogramm. Liest 2 Dateien. Beide enthalten Dateinamen von Bildern. Eine
	 * enthält Annotationen. Die andere enthält erkannte Objekte. Die Annotationen
	 * werden mit den erkannten Objekten verglichen.
	 * 
	 * @param args 1. Kommandozeilenparameter z.B. "result.xml", 2.
	 *             Kommandozeilenparameter z.B. "annotations.csv".
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.info("Evaluation batch job started.");

		switch (args.length) {
		case 0:
			break;
		case 2:
			resultXmlFilename = args[0];
			annotationsFilename = args[1];
			break;
		default:
			logger.fatal(
					"usage: java -cp ... de.heikozelt.objectdetection.BatchJob [<collections directory> <results file>]");
			System.exit(1);
		}

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
		if (annotations.size() == 0) {
			logger.error("Keine Eingabedaten!");
			return;
		}
		PrecisionAndRecall average = evaluate(annotations, detected);
		logger.info("********* overall average **********");
		logger.info("** precision: " + average.getPrecision());
		logger.info("** recall   : " + average.getRecall());
		logger.info("************************************");
		logger.info("Evaluation batch job finished.");
	}

}
