package im.bci.binpacker;

import java.util.ArrayList;
import java.util.List;

public class BinPackerOut {
	
	int textureWidth = 256;
	int textureHeight = 256;
	List<PackedImage> packedImages = new ArrayList<PackedImage>();
	List<PackableImage> nonPackedImages = new ArrayList<PackableImage>();
	
	public List<PackedImage> getPackedImages() {
		return packedImages;
	}
	public List<PackableImage> getNonPackedImages() {
		return nonPackedImages;
	}
	public int getTextureWidth() {
		return textureWidth;
	}
	public int getTextureHeight() {
		return textureHeight;
	}

}
