package im.bci.binpacker;

import java.util.ArrayList;
import java.util.List;

public class BinPackerIn {
	
	int textureWidth = 256;
	int textureHeight = 256;
	List<PackableImage> images = new ArrayList<PackableImage>();
	
	public BinPackerIn addImage(Object id, int width, int height) {
		images.add(new PackableImage(id, width, height));
		return this;
	}
	
	public BinPackerIn addImage(PackableImage image) {
		images.add(image);
		return this;
	}
	
	public int getTextureWidth() {
		return textureWidth;
	}
	public BinPackerIn setTextureWidth(int textureWidth) {
		this.textureWidth = textureWidth;
		return this;
	}
	public int getTextureHeight() {
		return textureHeight;
	}
	public BinPackerIn setTextureHeight(int textureHeight) {
		this.textureHeight = textureHeight;
		return this;
	}	
	
}
