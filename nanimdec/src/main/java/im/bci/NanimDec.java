package im.bci;

import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParserUtils;

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
		options.addOption("o", true, "output directory");
		
		if(args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "nanimenc [args] foo.nanim", options );
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		NanimDec nanimDec = new NanimDec(line);
		nanimDec.decode();
		nanimDec.save();
    }
	
	private void decode() throws IOException {
		File inputFile = new File(commandLine.getArgList().get(0).toString());
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
			NanimParserUtils.setRgba(outputImage, image);
			break;
		case RGB_888:
			outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			NanimParserUtils.setRgb(outputImage, image);
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

}
