package de.heikozelt.objectdetection.xml;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * XML-Element object = ein erkanntes Objekt
 * @author Heiko Zelt
 */
public class DetObject {
	private static Logger logger = LogManager.getLogger(DetObject.class);
	private String term;
	private BoundBox boundBox;
	private double probability;

	// Die Annotation ist optional: @XmlElement(name = "term")
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		logger.debug("setTerm: " + term);
		this.term = term;
	}

	@XmlElement(name = "bounding-box")
	public BoundBox getBoundBox() {
		return boundBox;
	}

	public double getProbability() {
		return probability;
	}

	public void setBoundBox(BoundBox boundBox) {
		this.boundBox = boundBox;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

}
