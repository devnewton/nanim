package im.bci.binpacker;

import java.awt.Rectangle;

public class PackedImage {
	
	public Object getId() {
		return id;
	}
	public int getX1() {
		return x1;
	}
	public int getY1() {
		return y1;
	}
	public int getX2() {
		return x2;
	}
	public int getY2() {
		return y2;
	}
	public int getWidth() {
		return x2 - x1;
	}
	
	public PackedImage(PackableImage packableImage, Rectangle packableRectangle) {
		this.id = packableImage.id;
		this.x1 = packableRectangle.x;
		this.y1 = packableRectangle.y;
		this.x2 = packableRectangle.x + packableRectangle.width;
		this.y2 = packableRectangle.y + packableRectangle.height;
	}
	Object id;
	int x1, y1, x2, y2;
}
