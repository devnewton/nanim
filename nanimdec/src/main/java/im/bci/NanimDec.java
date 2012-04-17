package im.bci;

import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.protobuf.ByteString;

/**
 * Hello world!
 *
 */
public class NanimDec 
{
    private CommandLine commandLine;
	private Nanim nanim;
	private int nbImageDecoded = 0;

	public NanimDec(CommandLine line) {
		this.commandLine = line;
	}

	public static void main( String[] args ) throws ParseException, IOException
    {
		Options options = new Options();
		options.addOption("i", true, "input nanim file");
		options.addOption("o", true, "output directory");
		
		if(args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "nanimenc [args]", options );
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		NanimDec nanimDec = new NanimDec(line);
		nanimDec.decode();
		nanimDec.save();
    }
	
	private void decode() throws IOException {
		File inputFile = new File(commandLine.getOptionValue("i"));
		FileInputStream is = new FileInputStream(inputFile);
		try {
			nanim = NanimParser.Nanim.parseFrom(is);
		} finally {
			is.close();
		}
		
	}
	
	private void save() throws IOException {
		for(Image image : nanim.getImagesList()) {
			saveImage(image);
		}		
	}

	private void saveImage(Image image) throws IOException {
		BufferedImage outputImage = null;
		switch(image.getFormat()) {
		case RGBA_8888:
			outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			setRgba(outputImage, image);
			break;
		case RGB_888:
			outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			setRgb(outputImage, image);
			break;		
		}
		if(null != outputImage) {
			File inputFile = new File(commandLine.getOptionValue("o", "."));
			String outputFilename = inputFile.getCanonicalPath() + '/' + image.getName() + ".png";
			if(!isFilenameValid(outputFilename)) {
				outputFilename = inputFile.getCanonicalPath() + '/' + "nanim_decoded_" + nbImageDecoded++ + ".png";
			}
			ImageIO.write(outputImage, "png", new File(outputFilename));
		}
	}
	
	public static boolean isFilenameValid(String pathname) {
		  try {
		    new File(pathname).getCanonicalPath();
		    return true;
		  } catch (IOException e) {
		    return false;
		  }
		}


	private void setRgba(BufferedImage outputImage, Image image) {
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
	
	private void setRgb(BufferedImage outputImage, Image image) {
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
