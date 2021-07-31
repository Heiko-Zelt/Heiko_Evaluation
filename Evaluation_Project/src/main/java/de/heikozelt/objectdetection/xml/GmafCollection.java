package de.heikozelt.objectdetection.xml;

import java.util.HashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import de.heikozelt.objectdetection.ImageWithObjects;

/**
 * Root-Element gmaf-collection des Document Object Trees (DOM)/der XML-Datei mit den Objekt-Erkennungs-Ergebnissen.  
 * @author Heiko Zelt
 */
@XmlRootElement(name = "gmaf-collection")
public class GmafCollection {

	/**
	 * GmafCollection/gmaf-collection besteht aus Liste von GmafData/gmaf-data-Elementen 
	 */
	@XmlElement(name = "gmaf-data")
	private List<GmafData> gmafDatas;

	/**
	 * einfache Getter-Methode
	 * @return Liste mit Bildern
	 */
	public List<GmafData> getGmafDatas() {
		return gmafDatas;
	}

	/**
	 * Liefert die Namen der Bilder und die erkannten Objekte als HashMap
	 * @return
	 */
	public HashMap<String, ImageWithObjects> asImagesWithObjects() {
		HashMap<String, ImageWithObjects> map = new HashMap<String, ImageWithObjects>();
		for (GmafData gd : gmafDatas) {
			ImageWithObjects img = new ImageWithObjects(gd.getFile(), gd.getObjectsNames());
			map.put(gd.getFile(), img);
		}
		return map;
	}
}
