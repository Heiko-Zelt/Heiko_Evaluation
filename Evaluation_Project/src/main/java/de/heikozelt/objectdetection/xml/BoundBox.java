package de.heikozelt.objectdetection.xml;

/**
 * XML-Element bounding-box, Properties x, y, width und height in Pixel
 * @author Heiko Zelt
 */
public class BoundBox {
	private int x;
	private int y;
	private int width;
	private int height;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
