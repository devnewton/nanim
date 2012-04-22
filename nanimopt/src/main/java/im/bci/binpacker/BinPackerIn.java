package im.bci.binpacker;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BinPackerIn {
	
	List<PackableImage> images = new ArrayList<PackableImage>();
	Dimension textureDimension = new Dimension(256, 256);
	
	public BinPackerIn addImage(Object id, int width, int height) {
		images.add(new PackableImage(id, width, height));
		return this;
	}
	
	public BinPackerIn addImage(PackableImage image) {
		images.add(image);
		return this;
	}
	
	public BinPackerIn setTextureDimension(Dimension dimension) {
		this.textureDimension = dimension;
		return this;
	}

	public BinPackerIn addImages(Collection<PackableImage> images) {
		this.images.addAll(images);
		return this;
	}	
	
	public BinPackerIn setImages(Collection<PackableImage> images) {
		this.images.clear();
		this.images.addAll(images);
		return this;
	}
	
}
