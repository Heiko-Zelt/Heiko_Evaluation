package de.heikozelt.objectdetection;

import java.util.LinkedList;
import java.util.List;

/**
 * Beispiele:
 *   Set = {"a", "b", "c"}
 *   MultiSet = {"a", "b", "b", "c"}
 * @author Heiko Zelt
 *
 */
public class MultiSet {
  public String[] elements;
  
  public MultiSet(String[] elements) {
	this.elements = elements;
}

/**
   * Beispiel:
   *   set1 = {"a", "b", "b", "c"}
   *   set2 = {"a", "b"}
   *   set1.diffOccurences(set2) => {"b", "c"}
   * @param elements2
   */
  public List<String> diffOccurences(String[] elements2) {
	  List<String> result = new LinkedList<String>();
	  for(String e1: elements) {
		  result.add(e1);  
	  }
	  for(String e2: elements2) {
		  result.remove(e2);
	  }
	  return result;
  }
  
  /**
   * Beispiel:
   *   set1 = {"a", "b", "b", "c"}
   *   set2 = {"a", "b", "b", "d"}
   *   set1.intersectOccurences(set2) => {"a", "b", "b"}
   * @param elements2
   */  
  public List<String> intersectOccurences(String[] elements2) {
	  List<String> rest2 = new LinkedList<String>();
	  for(String e2: elements2) {
		  rest2.add(e2);  
	  }
	  List<String> result = new LinkedList<String>();
	  for(String e1: elements) {
		  if(rest2.remove(e1)) {
		    result.add(e1);
		  }
	  }
	  return result;
  }
  
}
