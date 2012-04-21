package im.bci.binpacker;

import java.util.ArrayList;
import java.util.List;

public class BinPack {

	protected int textureWidth;
	protected int textureHeight;
	protected List<PackedImage> packedImages = new ArrayList<PackedImage>();

	public BinPack() {
		super();
	}

	public List<PackedImage> getPackedImages() {
		return packedImages;
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

}