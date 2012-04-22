package im.bci.binpacker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BinPacker {
	
	private ArrayList<Rectangle> freeRectangles;
	private ArrayList<Rectangle> nonFreeRectangles;
	private ArrayList<PackableImage> packableImages;
	private BinPackerOut out;
	
	public BinPackerOut pack(BinPackerIn in) {
		startPacking(in);
		while(!packableImages.isEmpty()) {
			packNext();
		}
		return out;
	}
	
	private void startPacking(BinPackerIn in) {
		out = new BinPackerOut();
		out.textureDimension = in.textureDimension;
		packableImages = new ArrayList<PackableImage>();
		packableImages.addAll(in.images);
		Collections.sort(packableImages, PackableImage.biggerFirstComparator);
		
		//add the whole texture as the first free rectangle
		freeRectangles =  new ArrayList<Rectangle>();
		Rectangle initialFreeRectangle = new Rectangle(in.textureDimension);
		freeRectangles.add(initialFreeRectangle);
		
		//add surrounding non free rectangles to avoid packing outside the texture
		nonFreeRectangles = new ArrayList<Rectangle>();
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.x + initialFreeRectangle.width, 0, initialFreeRectangle.width, initialFreeRectangle.height));
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.y + initialFreeRectangle.height, 0, initialFreeRectangle.width, initialFreeRectangle.height));
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.x - initialFreeRectangle.width, 0, initialFreeRectangle.width, initialFreeRectangle.height));
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.y - initialFreeRectangle.height, 0, initialFreeRectangle.width, initialFreeRectangle.height));
	}
	
	private void packNext() {
		PackableImage packableImage = packableImages.remove(0);
		Rectangle packableRectangle = new Rectangle(packableImage.width, packableImage.height);
		
		//search for a fitting free space to pack the image
		for(Iterator<Rectangle> freeRectangleIterator = freeRectangles.iterator(); freeRectangleIterator.hasNext(); ) {
			Rectangle freeRectangle = freeRectangleIterator.next();
			packableRectangle.setLocation(freeRectangle.getLocation());
			if(isFree(packableRectangle)) {
				//store packed image
				out.packedImages.add(new PackedImage(packableImage, packableRectangle));
				
				//update free and non free rectangle lists
				freeRectangleIterator.remove();
				nonFreeRectangles.add(packableRectangle);
				freeRectangles.addAll(split(freeRectangle, packableRectangle.width, packableRectangle.height));
				Collections.sort(freeRectangles, freeRectangleComparatorLeftThenTopFirst);
				return;
			}
		}
		
		//cannot pack this image
		out.nonPackedImages.add(packableImage);
	}
	
	private static Comparator<Rectangle> freeRectangleComparatorLeftThenTopFirst = new Comparator<Rectangle>() {

		@Override
		public int compare(Rectangle o1, Rectangle o2) {
			int result = Double.compare(o1.getMinX(), o2.getMinX());
			if(result != 0)
				return result;
			else
				return Double.compare(o1.getMinY(), o2.getMinY());
		}
		
	};


	private static List<Rectangle> split(Rectangle oldFreeRectangle, int width, int height) {
		ArrayList<Rectangle> newFreeRectangles = new ArrayList<Rectangle>();
		int remainingWidth = oldFreeRectangle.width - width;
		int remainingHeight = oldFreeRectangle.height - height;
		if(remainingWidth > 0) {
			newFreeRectangles.add(new Rectangle(width, 0, remainingWidth, height));
		}
		if(remainingHeight > 0) {
			newFreeRectangles.add(new Rectangle(0, height, width, remainingHeight));
		}		
		if(remainingWidth > 0 && remainingHeight > 0) {
			newFreeRectangles.add(new Rectangle(width, height, remainingWidth, remainingHeight));
		}
		return newFreeRectangles;		
	}

	private boolean isFree(Rectangle packableRectangle) {
		for(Rectangle nonFreeRectangle : nonFreeRectangles) {
			if(nonFreeRectangle.intersects(packableRectangle))
				return false;
		}
		return true;
	}

}
