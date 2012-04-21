package im.bci.binpacker;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class MultiBinPacker {
	
	public MultiBinPackerOut pack(MultiBinPackerIn in) {
		List<Dimension> possibleTextureDimensions = in.possibleTextureDimensions;
		if(null == in.possibleTextureDimensions) {
			in.possibleTextureDimensions = createDefaultPossibleTextureDimensions(in.images);
		}
		MultiBinPackerOut out = new MultiBinPackerOut();
		BinPacker packer = new BinPacker();
		BinPackerIn packerIn = new BinPackerIn();
		List<PackableImage> packableImages = new ArrayList<PackableImage>(in.images);
		while(!packableImages.isEmpty()) {
			BinPackerOut packerOut = null;
			
			//try to pack all input images in the smallest possible texture
			for(Dimension textureDimension : possibleTextureDimensions) {
				packerOut = packer.pack(packerIn.setTextureDimension(textureDimension).addImages(packableImages));
				if(packerOut.getNonPackedImages().isEmpty()) {
					break;
				}
			}
			
			//if there is no result, we cannot do any packing at all
			if(null == packerOut) {
				break;				
			}
			
			//add the last pack and continue with the remaining unpacked images
			out.packs.add(packerOut);
			packableImages = packerOut.getNonPackedImages();
		}
		return out;
	}

	private static List<Dimension> createDefaultPossibleTextureDimensions(List<PackableImage> images) {
		List<Dimension> dimensions = new ArrayList<Dimension>();
		for(int n=16; n<=1024; n *= 2 ) {
			dimensions.add(new Dimension(n, n));
		}
		return dimensions;
	}

}
