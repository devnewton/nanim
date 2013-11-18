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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser.PixelFormat;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParserUtils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.protobuf.ByteString;

public class NanimEnc {

	private Nanim nanim;
	private CommandLine commandLine;

	public NanimEnc(CommandLine line) {
		this.commandLine = line;
	}

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new Options();
		options.addOption("a", true, "add animation");
		options.addOption("d", true, "set next frames duration");
		options.addOption("f", true, "add frame animation");
		options.addOption("author", true, "set author metadata");
		options.addOption("license", true, "set license metadata");
		options.addOption("o", true, "ouput file name");

		if (args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("nanimenc [args]", options);
			System.out.println("Supported image formats: " + Arrays.toString(ImageIO.getReaderFormatNames()));						
			return;
		}
		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		NanimEnc nanimEnc = new NanimEnc(line);
		nanimEnc.encode();
		nanimEnc.save();
	}

	private void save() throws IOException {
		String output = commandLine.getOptionValue("o", "output.nanim");
                NanimParserUtils.writeTo(nanim, new File(output));
	}

	private void encode() throws IOException {
		Nanim.Builder animationCollectionBuilder = Nanim.newBuilder();
		Animation.Builder currentAnimationBuilder = null;
		int currentDuration = 0;
		LinkedHashMap<String, Image.Builder> images = new LinkedHashMap<String, Image.Builder>();
		for (Option option : commandLine.getOptions()) {
			String optName = option.getOpt();
			if (optName.equals("a")) {
				if (null != currentAnimationBuilder) {
					animationCollectionBuilder
							.addAnimations(currentAnimationBuilder);
				}
				currentAnimationBuilder = Animation.newBuilder();
				currentAnimationBuilder.setName(option.getValue());
			} else if (optName.equals("d")) {
				currentDuration = Integer.parseInt(option.getValue());
			} else if (optName.equals("f")) {
				if (null != currentAnimationBuilder) {
					File imageFile = new File(option.getValue());
					Image.Builder imageBuilder = encodeImage(imageFile);
					images.put(imageFile.getName(), imageBuilder);
					Frame.Builder frame = Frame.newBuilder()
							.setDuration(currentDuration)
							.setImageName(imageFile.getName()).setU1(0.0f)
							.setU2(1.0f).setV1(0.0f).setV2(1.0f);
					currentAnimationBuilder.addFrames(frame);
				}
			}
		}
		if (null != currentAnimationBuilder) {
			animationCollectionBuilder.addAnimations(currentAnimationBuilder);
		}
		for (Image.Builder image : images.values()) {
			animationCollectionBuilder.addImages(image);
		}
		String author = commandLine.getOptionValue("author");
		if (null != author)
			animationCollectionBuilder.setAuthor(author);
		String license = commandLine.getOptionValue("license");
		if (null != license)
			animationCollectionBuilder.setLicense(license);

		nanim = animationCollectionBuilder.build();
	}

	private Image.Builder encodeImage(File imageFile) throws IOException {
		Image.Builder image = Image.newBuilder();
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		image.setName(imageFile.getName());
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
