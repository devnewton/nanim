package im.bci.binpacker;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class MultiBinPackerIn {
	List<Dimension> possibleTextureDimensions = null;
	boolean debug = false;
	
	public void setPossibleTextureDimensions(
			List<Dimension> possibleTextureDimensions) {
		this.possibleTextureDimensions = possibleTextureDimensions;
	}

	List<PackableImage> images = new ArrayList<PackableImage>();
	
	public MultiBinPackerIn addImage(Object id, int width, int height) {
		images.add(new PackableImage(id, width, height));
		return this;
	}

	public MultiBinPackerIn addImage(PackableImage image) {
		images.add(image);
		return this;
	}
	
	public MultiBinPackerIn setDebug(boolean enabled) {
		this.debug = enabled;
		return this;
	}
}
