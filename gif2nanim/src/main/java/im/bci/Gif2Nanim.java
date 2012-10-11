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

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Image.Builder;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser.PixelFormat;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParserUtils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.protobuf.ByteString;

public class Gif2Nanim {

	private Nanim nanim;
	private CommandLine commandLine;
	private im.bci.GifDecoder gif;
	private String animationName;
	private String gifFilename;

	public Gif2Nanim(CommandLine line) {
		this.commandLine = line;
		this.gifFilename = commandLine.getArgList().get(0).toString();
		if(commandLine.hasOption("name")) {
			animationName = commandLine.getOptionValue("name");
		} else {
			animationName = gifFilename;
		}
	}

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new Options();
		options.addOption("name", true, "set animation name");
		options.addOption("author", true, "set author metadata");
		options.addOption("license", true, "set license metadata");

		if (args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("gif2nanim [args] foo.gif output.nanim", options);
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		Gif2Nanim nanimEnc = new Gif2Nanim(line);
		nanimEnc.decode();
		nanimEnc.encode();
		nanimEnc.save();
	}

	private void decode() throws FileNotFoundException {
		gif = new GifDecoder();
		gif.read(new FileInputStream(gifFilename));
	}

	private void save() throws IOException {
		String output;
		if(commandLine.getArgList().size() >= 2) {
			output = commandLine.getArgList().get(1).toString();
		} else {
			output = gifFilename.replace(".gif", ".nanim");
		}
		FileOutputStream os = new FileOutputStream(output);
		try {
			nanim.writeTo(os);
			System.out.println("nanim successfully written to " + output);
		} finally {
			os.flush();
			os.close();
		}
	}

	private void encode() throws IOException {
		Nanim.Builder animationCollection = Nanim.newBuilder();
		Animation.Builder animation =  Animation.newBuilder();
		animation.setName(animationName);
		
		int n = gif.getFrameCount();
		for (int i = 0; i < n; i++) {
			final String imageName = animationName + i;
			Image.Builder image = encodeImage(gif.getFrame(i), imageName);
			animationCollection.addImages(image);
			Frame.Builder frame = Frame.newBuilder()
					.setDuration(gif.getDelay(i))
					.setImageName(imageName).setU1(0.0f)
					.setU2(1.0f).setV1(0.0f).setV2(1.0f);
			animation.addFrames(frame);
		}
		animationCollection.addAnimations(animation);

		String author = commandLine.getOptionValue("author");
		if (null != author)
			animationCollection.setAuthor(author);
		String license = commandLine.getOptionValue("license");
		if (null != license)
			animationCollection.setLicense(license);

		nanim = animationCollection.build();
	}

	private Builder encodeImage(BufferedImage bufferedImage, String name) {
		Image.Builder image = Image.newBuilder();
		image.setName(name);
		image.setWidth(bufferedImage.getWidth());
		image.setHeight(bufferedImage.getHeight());

		if (bufferedImage.getColorModel().hasAlpha()) {
			image.setFormat(PixelFormat.RGBA_8888);
			image.setPixels(ByteString.copyFrom(NanimParserUtils
					.getRGBAPixels(bufferedImage)));
		} else {
			image.setFormat(PixelFormat.RGB_888);
			image.setPixels(ByteString.copyFrom(NanimParserUtils
					.getRGBPixels(bufferedImage)));
		}
		return image;
	}
}
