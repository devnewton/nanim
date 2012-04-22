package im.bci.binpacker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class BinPacker {

	private ArrayList<Rectangle> freeRectangles;
	private ArrayList<Rectangle> nonFreeRectangles;
	private ArrayList<PackableImage> packableImages;
	private BinPackerOut out;
	private boolean debug;
	private int step;

	public BinPackerOut pack(BinPackerIn in) {
		startPacking(in);
		while (!packableImages.isEmpty()) {
			packNext();
		}
		return out;
	}

	private void startPacking(BinPackerIn in) {
		out = new BinPackerOut();
		out.textureDimension = in.textureDimension;
		debug = in.debug;
		packableImages = new ArrayList<PackableImage>();
		packableImages.addAll(in.images);
		Collections.sort(packableImages, PackableImage.biggerFirstComparator);

		// add the whole texture as the first free rectangle
		freeRectangles = new ArrayList<Rectangle>();
		Rectangle initialFreeRectangle = new Rectangle(in.textureDimension);
		freeRectangles.add(initialFreeRectangle);

		// add surrounding non free rectangles to avoid packing outside the
		// texture
		nonFreeRectangles = new ArrayList<Rectangle>();
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.x
				+ initialFreeRectangle.width, 0, initialFreeRectangle.width,
				initialFreeRectangle.height));
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.y
				+ initialFreeRectangle.height, 0, initialFreeRectangle.width,
				initialFreeRectangle.height));
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.x
				- initialFreeRectangle.width, 0, initialFreeRectangle.width,
				initialFreeRectangle.height));
		nonFreeRectangles.add(new Rectangle(initialFreeRectangle.y
				- initialFreeRectangle.height, 0, initialFreeRectangle.width,
				initialFreeRectangle.height));

		step = 0;
	}

	private void packNext() {
		PackableImage packableImage = packableImages.remove(0);
		Rectangle packableRectangle = new Rectangle(packableImage.width,
				packableImage.height);

		// search for a fitting free space to pack the image
		for (Iterator<Rectangle> freeRectangleIterator = freeRectangles
				.iterator(); freeRectangleIterator.hasNext();) {
			Rectangle freeRectangle = freeRectangleIterator.next();
			packableRectangle.setLocation(freeRectangle.getLocation());
			if (fitsIn(packableRectangle, freeRectangle) && isFree(packableRectangle)) {
				// store packed image
				out.packedImages.add(new PackedImage(packableImage,
						packableRectangle));

				// update free and non free rectangle lists
				freeRectangleIterator.remove();
				nonFreeRectangles.add(packableRectangle);
				freeRectangles.addAll(split(freeRectangle,
						packableRectangle.width, packableRectangle.height));
				Collections.sort(freeRectangles,
						freeRectangleComparatorLeftThenTopFirst);

				++step;
				if (debug) {
					saveDebugImage();
				}
				return;
			}
		}

		// cannot pack this image
		out.nonPackedImages.add(packableImage);
	}

	private boolean fitsIn(Rectangle packableRectangle, Rectangle freeRectangle) {
		return packableRectangle.width <= freeRectangle.width && packableRectangle.height <= freeRectangle.height;
	}

	private void saveDebugImage() {
		BufferedImage debugImage = new BufferedImage(out.getTextureWidth(),
				out.getTextureHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = debugImage.createGraphics();
		for (Rectangle r : freeRectangles) {
			g.setColor(Color.green);
			g.drawRect(r.x, r.y, r.width-1, r.height-1);
		}
		for (Rectangle r : nonFreeRectangles) {
			g.setColor(Color.red);
			g.drawRect(r.x, r.y, r.width-1, r.height-1);
		}
		g.dispose();
		try {
			ImageIO.write(debugImage, "png",
					new File("nanimopt_debug_image_" + out.getTextureWidth() + 'x'
							+ out.getTextureHeight() + "_step_" + step + ".png"));
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	private static Comparator<Rectangle> freeRectangleComparatorLeftThenTopFirst = new Comparator<Rectangle>() {

		@Override
		public int compare(Rectangle o1, Rectangle o2) {
			int result = Double.compare(o1.getMinX(), o2.getMinX());
			if (result != 0)
				return result;
			else
				return Double.compare(o1.getMinY(), o2.getMinY());
		}

	};

	private static List<Rectangle> split(Rectangle oldFreeRectangle, int width,
			int height) {
		ArrayList<Rectangle> newFreeRectangles = new ArrayList<Rectangle>();
		int remainingWidth = oldFreeRectangle.width - width;
		int remainingHeight = oldFreeRectangle.height - height;
		if (remainingWidth > 0) {
			newFreeRectangles.add(new Rectangle(oldFreeRectangle.x + width, oldFreeRectangle.y + 0, remainingWidth,
					height));
		}
		if (remainingHeight > 0) {
			newFreeRectangles.add(new Rectangle(oldFreeRectangle.x + 0, oldFreeRectangle.y + height, width,
					remainingHeight));
		}
		if (remainingWidth > 0 && remainingHeight > 0) {
			newFreeRectangles.add(new Rectangle(oldFreeRectangle.x + width, oldFreeRectangle.y + height, remainingWidth,
					remainingHeight));
		}
		return newFreeRectangles;
	}

	private boolean isFree(Rectangle packableRectangle) {
		for (Rectangle nonFreeRectangle : nonFreeRectangles) {
			if (nonFreeRectangle.intersects(packableRectangle))
				return false;
		}
		return true;
	}

}
