package im.bci.nanim;

import im.bci.nanim.NanimParser.Image;

import java.awt.image.BufferedImage;

import com.google.protobuf.ByteString;

public class NanimParserUtils {
	public static byte[] getRGBAPixels(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		byte[] pixels = new byte[w * h * 4];
		int pixelIndex = 0;
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				int rgba = image.getRGB(x, y);
				byte a = (byte) ((rgba >> 24) & 0xff);
				byte r = (byte) ((rgba >> 16) & 0xff);
				byte g = (byte) ((rgba >> 8) & 0xff);
				byte b = (byte) (rgba & 0xff);
				pixels[pixelIndex++] = r;
				pixels[pixelIndex++] = g;
				pixels[pixelIndex++] = b;
				pixels[pixelIndex++] = a;
			}
		}
		return pixels;
	}
	
	public static byte[] getRGBPixels(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		byte[] pixels = new byte[w * h * 3];
		int pixelIndex = 0;
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				int rgb = image.getRGB(x, y);
				byte r = (byte) (((rgb & 0xff0000) >> 16) & 0xff);
				byte g = (byte) (((rgb & 0xff00) >> 8) & 0xff);
				byte b = (byte) (rgb & 0xff);
				pixels[pixelIndex++] = r;
				pixels[pixelIndex++] = g;
				pixels[pixelIndex++] = b;
			}
		}
		return pixels;
	}

	public static void setRgba(BufferedImage outputImage, Image image) {
		int w = image.getWidth();
		int h = image.getHeight();
		ByteString pixels = image.getPixels();
		int pixelIndex = 0;
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				byte r = pixels.byteAt(pixelIndex++);
				byte g = pixels.byteAt(pixelIndex++);
				byte b = pixels.byteAt(pixelIndex++);
				byte a = pixels.byteAt(pixelIndex++);
				
				//ImageIO is just plain stupid...
				if(a == 0) {
					a = 1;
				}
				
				int rgba = (a << 24) + (r << 16) + (g << 8) + b;
				outputImage.setRGB(x, y, rgba);
			}
		}
	}
	
	public static void setRgb(BufferedImage outputImage, Image image) {
		int w = image.getWidth();
		int h = image.getHeight();
		ByteString pixels = image.getPixels();
		int pixelIndex = 0;
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				int r = pixels.byteAt(pixelIndex++);
				int g = pixels.byteAt(pixelIndex++);
				int b = pixels.byteAt(pixelIndex++);
				int rgba = (r << 16) + (g << 8) + b;
				outputImage.setRGB(x, y, rgba);
			}
		}
	}
}
