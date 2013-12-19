package im.bci.nanim;

import im.bci.nanim.NanimParser.Image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.protobuf.ByteString;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NanimParserUtils {

    public static byte[] getRGBAPixels(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        byte[] pixels = new byte[w * h * 4];
        int pixelIndex = 0;
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
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
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
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
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {

                int r = pixels.byteAt(pixelIndex++) & 0xff;
                int g = pixels.byteAt(pixelIndex++) & 0xff;
                int b = pixels.byteAt(pixelIndex++) & 0xff;
                int a = pixels.byteAt(pixelIndex++) & 0xff;

                Color c = new Color(r, g, b, a);
                outputImage.setRGB(x, y, c.getRGB());
            }
        }
    }

    public static void setRgb(BufferedImage outputImage, Image image) {
        int w = image.getWidth();
        int h = image.getHeight();
        ByteString pixels = image.getPixels();
        int pixelIndex = 0;
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int r = pixels.byteAt(pixelIndex++) & 0xff;
                int g = pixels.byteAt(pixelIndex++) & 0xff;
                int b = pixels.byteAt(pixelIndex++) & 0xff;

                Color c = new Color(r, g, b);
                outputImage.setRGB(x, y, c.getRGB());
            }
        }
    }

    public static boolean isGZipped(File f) throws FileNotFoundException, IOException {
        RandomAccessFile raf = new RandomAccessFile(f, "r");
        try {
            int magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);
            return magic == GZIPInputStream.GZIP_MAGIC;
        } finally {
            raf.close();
        }
    }

    public static NanimParser.Nanim decode(File inputFile) throws IOException {
        boolean gzipped = isGZipped(inputFile);
        InputStream is = new FileInputStream(inputFile);
        try {
            if (gzipped) {
                is = new GZIPInputStream(is);
            }
            return NanimParser.Nanim.parseFrom(is);
        } finally {
            is.close();
        }
    }

    public static void writeTo(NanimParser.Nanim nanim, File file) throws IOException {
        boolean gzipped = file.getName().endsWith(".gz") || file.getName().endsWith(".nanimz");
        OutputStream os = new FileOutputStream(file);
        try {
            if (gzipped) {
                os = new GZIPOutputStream(os);
            }
            nanim.writeTo(os);
            System.out.println("nanim successfully written to " + file.getAbsolutePath());
        } finally {
            os.flush();
            os.close();
        }
    }
}
