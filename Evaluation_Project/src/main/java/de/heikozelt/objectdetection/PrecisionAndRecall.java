package de.heikozelt.objectdetection;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Diese Klasse implementiert den Algorithmus zur Berechnung des Presision- und des Recall-Wertes
 * @see https://en.wikipedia.org/wiki/Precision_and_recall
 * @author Heiko Zelt
 *
 */
public class PrecisionAndRecall {
	private float precision;
	private float recall;
	private static Logger logger = LogManager.getLogger(PrecisionAndRecall.class);

	public PrecisionAndRecall() {
		this.precision = 0f;
		this.recall = 0f;
	}
	
	public PrecisionAndRecall(float precision, float recall) {
		this.precision = precision;
		this.recall = recall;
	}
	
	public PrecisionAndRecall(String[] annotations, String[] detectedObjects) {
		calculate(annotations, detectedObjects);
	}
	
	public void calculate(String[] annotations, String[] detectedObjects) {
		MultiSet annosMulti = new MultiSet(annotations);
		MultiSet detsMulti = new MultiSet(detectedObjects);
		
		logger.debug("  annotations: " + String.join(",", annotations));
		logger.debug("  detected: " + String.join(",", detectedObjects));
		List<String> truePositives = annosMulti.intersectOccurences(detectedObjects);
		logger.debug("  true positives: " + truePositives);
		List<String> falsePositives = detsMulti.diffOccurences(detectedObjects);
		logger.debug("  false positives: " + falsePositives);
		List<String> falseNegatives = annosMulti.diffOccurences(detectedObjects);
		logger.debug("  false negatives: " + falseNegatives);
		if (detectedObjects.length == 0) {
			// nichts erkannt, also auch keine falsch erkannt
			precision = 1;
		} else {
			precision = truePositives.size() / (float) detectedObjects.length;
		}
		logger.info("  precision: " + precision);
		if (annotations.length == 0) {
			recall = (detectedObjects.length == 0) ? 1 : 0;
		} else {
			recall = truePositives.size() / (float) annotations.length;
		}
		logger.info("  recall: " + recall);
	}

	public float getPrecision() {
		return precision;
	}

	public float getRecall() {
		return recall;
	}


}
