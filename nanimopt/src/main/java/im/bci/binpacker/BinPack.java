package im.bci.binpacker;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class BinPack {

	protected Dimension textureDimension;
	protected List<PackedImage> packedImages = new ArrayList<PackedImage>();

	public BinPack() {
		super();
	}

	public List<PackedImage> getPackedImages() {
		return packedImages;
	}

	public int getTextureWidth() {
		return textureDimension.width;
	}

	public int getTextureHeight() {
		return textureDimension.height;
	}

}