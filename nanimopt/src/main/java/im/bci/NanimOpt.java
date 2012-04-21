/*
 * Copyright (c) 2012 devnewton <devnewton@bci.im>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'devnewton <devnewton@bci.im>' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package im.bci;

import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParserUtils;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * nanim optimizer
 *
 */
public class NanimOpt 
{
    private CommandLine commandLine;
	private Nanim nanim;
	private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	public NanimOpt(CommandLine line) {
		this.commandLine = line;
	}

	public static void main( String[] args ) throws ParseException, IOException
    {
		Options options = new Options();
		options.addOption("i", true, "input nanim file");
		options.addOption("o", true, "output nanim file");
		
		if(args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "nanimenc [args]", options );
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		NanimOpt nanimOpt = new NanimOpt(line);
		nanimOpt.decode();
		nanimOpt.optimize();
		nanimOpt.loadImages();
		nanimOpt.reencode();
		nanimOpt.save();
    }
	
	private void reencode() {
		// TODO Auto-generated method stub
		
	}

	private void save() throws IOException {
		File outputFile = new File(commandLine.getOptionValue("o"));
		FileOutputStream os = new FileOutputStream(outputFile);
		try {
			nanim.writeTo(os);
		} finally {
			os.close();
		}
		
	}

	private void optimize() {
		// TODO Auto-generated method stub
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
	
	private void loadImages() throws IOException {
		for(Image image : nanim.getImagesList()) {
			loadImage(image);
		}		
	}

	private void loadImage(Image image) throws IOException {
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
		images.put(image.getName(), outputImage);
	}
}
