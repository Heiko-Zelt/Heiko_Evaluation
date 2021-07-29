package de.heikozelt.objectdetection.xml;

import javax.xml.bind.annotation.XmlElement;

public class GmafData {
	private String file;
	private String date;
	
	private DetObjects detObjects;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@XmlElement(name = "objects")
	public DetObjects getDetObjects() {
		return detObjects;
	}
	
	public String[] getObjectsNames() {
		return detObjects.getObjectsNames();
	}

	public void setDetObjects(DetObjects detObjects) {
		this.detObjects = detObjects;
	}
}
