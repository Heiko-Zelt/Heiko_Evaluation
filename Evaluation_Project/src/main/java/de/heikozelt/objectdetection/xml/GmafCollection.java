package de.heikozelt.objectdetection.xml;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.heikozelt.objectdetection.ImageWithObjects;

@XmlRootElement(name = "gmaf-collection")
public class GmafCollection {

	@XmlElement(name = "gmaf-data")
	private List<GmafData> gmafDatas;

	public List<GmafData> getGmafDatas() {
		return gmafDatas;
	}

	public HashMap<String, ImageWithObjects> asImagesWithObjects() {
		HashMap<String, ImageWithObjects> map = new HashMap<String, ImageWithObjects>();
		for (GmafData gd : gmafDatas) {
			ImageWithObjects img = new ImageWithObjects(gd.getFile(), gd.getObjectsNames());
			map.put(gd.getFile(), img);
		}
		return map;
	}
}
