package im.bci.binpacker;

import java.util.ArrayList;
import java.util.List;

public class BinPackerOut extends BinPack {
	
	List<PackableImage> nonPackedImages = new ArrayList<PackableImage>();

	public List<PackableImage> getNonPackedImages() {
		return nonPackedImages;
	}

}
