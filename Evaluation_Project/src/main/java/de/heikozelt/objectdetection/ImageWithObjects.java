package de.heikozelt.objectdetection;

import java.util.Arrays;

/**
 * Die Klasse beinhaltet einen Dateinamen/eine ID und die dazugehörigen
 * Klassennamen von erkannten Objekten oder Annotationen. Zur einfacheren
 * Vergleichbarkeit werden die Klassennamen auf Kleinschreibung normiert.
 * PyTorch/resnet50 verwendet Kleinschreibung, Beispiel "person".
 * Tensorflow/mobilenet_v2 verwendet Großschreibung, Beispiel "Person".
 * 
 * @author Heiko Zelt
 */
public class ImageWithObjects {
	/**
	 * image id, for example filename
	 */
	private String id;

	/**
	 * labels of detected objects or annotated class names
	 */
	private String[] objects;

	/**
	 * simple constructor
	 */
	public ImageWithObjects() {
		this.id = null;
		this.objects = null;
	}

	/**
	 * simple constructor
	 */
	public ImageWithObjects(String id, String[] objects) {
		this.id = id;
		this.setObjects(objects);
	}

	/**
	 * constructor, which parses a line of a CSV file
	 * 
	 * @param csvLine Example "image01.png","dog","surfboard","bicycle"
	 */
	public ImageWithObjects(String csvLine) {
		String[] fields = csvLine.strip().split(",");
		// Quotation Marks entfernen, falls vorhanden:
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].startsWith("\"") && fields[i].endsWith("\"")) {
				fields[i] = fields[i].substring(1, fields[i].length() - 1);
			}
		}
		this.id = fields[0];
		setObjects(Arrays.copyOfRange(fields, 1, fields.length));
	}

	/**
	 * simple getter method
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * class names of detected objects or annotations
	 * 
	 * @return
	 */
	public String[] getObjects() {
		return objects;
	}

	/**
	 * simple setter method
	 * 
	 * @param id Dateiname
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Setter-Methode für objects-Array Das Array wird nicht eins-zu-eins
	 * übernommen, sondern die Zeichenketten werden sortiert und auf Kleinschreibung
	 * normiert.
	 * 
	 * @param objects
	 */
	public void setObjects(String[] objects) {
		this.objects = new String[objects.length];
		for (int i = 0; i < objects.length; i++) {
			this.objects[i] = objects[i].toLowerCase();
		}
		Arrays.sort(this.objects);
	}
}
