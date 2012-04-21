package im.bci.binpacker;

import java.util.Comparator;

public class PackableImage {
	Object id;
	int width, height;
	int area;
	
	public PackableImage(Object id, int width, int height) {
		super();
		this.id = id;
		this.width = width;
		this.height = height;
		this.area = width * height;
	}

	public static Comparator<PackableImage> biggerFirstComparator = new Comparator<PackableImage>() {
		
		@Override
		public int compare(PackableImage o1, PackableImage o2) {
			if(o1.area > o2.area)
				return -1;
			else if(o1.area < o2.area)
				return 1;
			else
				return 0;
		}
	};


}
