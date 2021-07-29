package de.heikozelt.objectdetection;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementiert ein Multiset, also eine Menge, welche Duplikate enthalten kann
 * und Mengenoperationen. Beispiele: Set = {"a", "b", "c"}, MultiSet =
 * {"a", "b", "b", "c"}
 * @see https://de.wikipedia.org/wiki/Multimenge
 * 
 * @author Heiko Zelt
 */
public class MultiSet {
	public String[] elements;

	public MultiSet(String[] elements) {
		this.elements = elements;
	}

	/**
	 * Bildet die Differenzmultimenge, wobei Elemente nur so häufig abgezogen werden, wie
	 * Sie im zweiten Multiset enthalten sind. Beispiel: set1 = {"a", "b", "b",
	 * "c"}, set2 = {"a", "b"}, set1.diffOccurences(set2) liefert {"b", "c"}
	 * 
	 * @param elements2 MultiSet, welches angezogen wird.
	 * @return MultiSet Ergebnis der Differenzmengen-Operation
	 */
	public List<String> diffOccurences(String[] elements2) {
		List<String> result = new LinkedList<String>();
		for (String e1 : elements) {
			result.add(e1);
		}
		for (String e2 : elements2) {
			result.remove(e2);
		}
		return result;
	}

	/**
	 * Bildet die Schnittmultimenge zwischen 2 MultiSets. Das Ergebnis-Multiset enthält
	 * von einem Element, jeweils nur so viele Vorkommnisse, wie es in beiden
	 * Multisets vorkommt. Beispiel: set1 = {"a", "b", "b", "c"}, set2 = {"a", "b",
	 * "b", "d"}, set1.intersectOccurences(set2) liefert {"a", "b", "b"}
	 * 
	 * @param elements2
	 */
	public List<String> intersectOccurences(String[] elements2) {
		List<String> rest2 = new LinkedList<String>();
		for (String e2 : elements2) {
			rest2.add(e2);
		}
		List<String> result = new LinkedList<String>();
		for (String e1 : elements) {
			if (rest2.remove(e1)) {
				result.add(e1);
			}
		}
		return result;
	}

}
