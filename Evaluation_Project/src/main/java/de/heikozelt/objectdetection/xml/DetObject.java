package de.heikozelt.objectdetection.xml;

import javax.xml.bind.annotation.XmlElement;

public class DetObject {
  public String term;
  @XmlElement(name="bounding-box")
  public BoundBox boundBox;
  public double probability;
}
