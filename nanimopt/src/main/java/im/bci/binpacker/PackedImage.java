package im.bci.binpacker;

import java.awt.Rectangle;

public class PackedImage {
	
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
