package im.bci.binpacker;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class BinPackerOut extends BinPack {
	
	List<PackableImage> nonPackedImages = new ArrayList<PackableImage>();
	Dimension textureDimension;
	
	public Dimension getTextureDimension() {
		return textureDimension;
	}

	public List<PackableImage> getNonPackedImages() {
		return nonPackedImages;
	}

}
