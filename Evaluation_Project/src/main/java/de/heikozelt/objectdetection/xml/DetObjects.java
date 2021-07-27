package de.heikozelt.objectdetection.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DetObjects {
	@XmlElement(name="object")
	public List<DetObject> detObjectsList;
	
	public String[] getObjectsNames() {
		if(detObjectsList == null) {
			return new String[0];
		}
		String[] names = new String[detObjectsList.size()];
		for(int i = 0; i < detObjectsList.size(); i++) {
		  DetObject obj = detObjectsList.get(i);
		  names[i] = obj.term;
		}
		return names;
	}
}
