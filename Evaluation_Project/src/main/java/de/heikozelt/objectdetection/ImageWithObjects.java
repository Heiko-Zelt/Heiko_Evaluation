package de.heikozelt.objectdetection;

import java.util.Arrays;

public class ImageWithObjects {
	// filename for example
	private String id;

	// labels or detected objects
	private String[] objects;

	public ImageWithObjects() {
			this.id = null;
			this.objects = null;
		}

	public ImageWithObjects(String id, String[] objects) {
			this.id = id;
			this.setObjects(objects);
		}
	
	/**
	 * 
	 * @param csvLine Example "image01.png","dog","surfboard","bicycle"
	 */
	public ImageWithObjects(String csvLine) {
		String[] fields = csvLine.strip().split(",");
		// Quotation Marks entfernen, falls vorhanden:
		for(int i = 0; i < fields.length; i++) {
			if(fields[i].startsWith("\"") && fields[i].endsWith("\"")) {
				fields[i] = fields[i].substring(1, fields[i].length() - 1);
			}
		}
		this.id = fields[0];
		this.objects = Arrays.copyOfRange(fields, 1, fields.length);
	}

	public String getId() {
		return id;
	}

	public String[] getObjects() {
		return objects;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setObjects(String[] objects) {
		String[] sorted = objects;
		Arrays.sort(sorted);
		this.objects = sorted;
	}
}
