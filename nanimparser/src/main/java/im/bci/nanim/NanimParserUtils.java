package im.bci.nanim;

import im.bci.nanim.NanimParser.Image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.protobuf.ByteString;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

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

    private static final CompressorStreamFactory compressorStreamFactory = new CompressorStreamFactory();

    public static NanimParser.Nanim decode(File inputFile) throws IOException {
        InputStream is = new FileInputStream(inputFile);
        try {
            String compressionName = getCompressionName(inputFile);
            if (null != compressionName) {
                CompressorInputStream cis;
                cis = compressorStreamFactory.createCompressorInputStream(compressionName, is);
                try {
                    return NanimParser.Nanim.parseFrom(cis);
                } finally {
                    cis.close();
                }
            } else {
                return NanimParser.Nanim.parseFrom(is);
            }
        } catch (CompressorException e) {
            throw new IOException(e);
        } finally {
            is.close();
        }
    }

    public static void writeTo(NanimParser.Nanim nanim, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        try {
            try {
                String compressionName = getCompressionName(file);
                if (null != compressionName) {
                    os = compressorStreamFactory.createCompressorOutputStream(compressionName, os);
                }
                nanim.writeTo(os);
            } catch (CompressorException ex) {
                Logger.getLogger(NanimParserUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        } finally {
            os.flush();
            os.close();
        }
    }

    public static String getCompressionName(File file) {
        final String name = file.getName();
        if (name.endsWith(".gz") || file.getName().endsWith(".nanimz")) {
            return CompressorStreamFactory.GZIP;
        }
        if (name.endsWith(".bz2")) {
            return CompressorStreamFactory.BZIP2;
        }
        if (name.endsWith(".xz")) {
            return CompressorStreamFactory.XZ;
        }
        if (name.endsWith(".lzma")) {
            return CompressorStreamFactory.LZMA;
        }
        return null;
    }
}
