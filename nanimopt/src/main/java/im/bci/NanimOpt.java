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

import im.bci.binpacker.BinPack;
import im.bci.binpacker.MultiBinPacker;
import im.bci.binpacker.MultiBinPackerIn;
import im.bci.binpacker.MultiBinPackerOut;
import im.bci.binpacker.PackedImage;
import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Nanim.Builder;
import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser.PixelFormat;
import im.bci.nanim.NanimParserUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.protobuf.ByteString;

/**
 * nanim optimizer
 * 
 */
public class NanimOpt {
	private CommandLine commandLine;
	private Nanim inputNanim;
	private Nanim outputNanim;

	public NanimOpt(CommandLine line) {
		this.commandLine = line;
	}

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new Options();
		options.addOption("i", true, "input nanim file");
		options.addOption("o", true, "output nanim file");
		options.addOption("debug", false, "enable debug mode");

		if (args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("nanimenc [args]", options);
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		NanimOpt nanimOpt = new NanimOpt(line);
		nanimOpt.decode();
		if (nanimOpt.isAlreadyOptimized()) {
			System.out.println("Input nanim file is already optimized.");
		}
		nanimOpt.optimize();
		nanimOpt.save();
	}

	private boolean isAlreadyOptimized() {
		for (Animation animation : inputNanim.getAnimationsList()) {
			for (Frame frame : animation.getFramesList()) {
				if (frame.getU1() != 0.0 || frame.getV1() != 0.0
						|| frame.getU2() != 1.0 || frame.getV2() != 1.0) {
					return true;
				}
			}
		}
		return false;
	}

	private void save() throws IOException {
		File outputFile = new File(commandLine.getOptionValue("o"));
		FileOutputStream os = new FileOutputStream(outputFile);
		try {
			outputNanim.writeTo(os);
		} finally {
			os.close();
		}
	}

	private void optimize() {

		MultiBinPackerIn in = new MultiBinPackerIn();
		for (Image image : inputNanim.getImagesList()) {
			in.addImage(image, image.getWidth(), image.getHeight());
		}
		in.setDebug(commandLine.hasOption("debug"));
		MultiBinPacker packer = new MultiBinPacker();
		MultiBinPackerOut out = packer.pack(in);
		Builder outputNanimBuilder = Nanim.newBuilder(inputNanim).clearImages()
				.clearAnimations();
		outputNanimBuilder
				.addAllImages(createImagesFromBinPacks(out.getPacks()));
		outputNanimBuilder
				.addAllAnimations(createAnimationsFromOldAnimationsAndBinPacks(out
						.getPacks()));
		outputNanim = outputNanimBuilder.build();
	}

	private List<Animation> createAnimationsFromOldAnimationsAndBinPacks(
			List<BinPack> packs) {
		List<Animation> animations = new ArrayList<NanimParser.Animation>();
		for (Animation oldAnimation : inputNanim.getAnimationsList()) {
			animations.add(createAnimationFromOldAnimationAndBinPacks(
					oldAnimation, packs));
		}
		return animations;
	}

	private Animation createAnimationFromOldAnimationAndBinPacks(
			Animation oldAnimation, List<BinPack> packs) {
		im.bci.nanim.NanimParser.Animation.Builder builder = Animation
				.newBuilder(oldAnimation).clearFrames();
		for (Frame oldFrame : oldAnimation.getFramesList()) {
			builder.addFrames(createFrameFromOldFrameAndBinPacks(oldFrame,
					packs));
		}
		return builder.build();
	}

	private Frame createFrameFromOldFrameAndBinPacks(Frame oldFrame,
			List<BinPack> packs) {
		for (int p = 0; p < packs.size(); ++p) {
			BinPack pack = packs.get(p);
			for (PackedImage packedImage : pack.getPackedImages()) {
				NanimParser.Image oldImage = (Image) packedImage.getId();
				if (oldImage.getName().equals(oldFrame.getImageName())) {
					return Frame
							.newBuilder(oldFrame)
							.setImageName("image_" + p)
							.setU1((float) packedImage.getX1()
									/ (float) pack.getTextureWidth())
							.setV1((float) packedImage.getY1()
									/ (float) pack.getTextureHeight())
							.setU2((float) packedImage.getX2()
									/ (float) pack.getTextureWidth())
							.setV2((float) packedImage.getY2()
									/ (float) pack.getTextureHeight()).build();
				}
			}
		}
		throw new RuntimeException(
				"Cannot find packed image for old frame, optimization failed");
	}

	private List<NanimParser.Image> createImagesFromBinPacks(List<BinPack> packs) {

		List<NanimParser.Image> images = new ArrayList<NanimParser.Image>();
		for (int i = 0; i < packs.size(); ++i) {
			images.add(createImageFromBinPack("image_" + i, packs.get(i)));
		}
		return images;
	}

	private NanimParser.Image createImageFromBinPack(String imageName,
			BinPack binPack) {
		im.bci.nanim.NanimParser.Image.Builder builder = NanimParser.Image
				.newBuilder();
		builder.setName(imageName);
		builder.setWidth(binPack.getTextureWidth());
		builder.setHeight(binPack.getTextureHeight());
		builder.setFormat(PixelFormat.RGBA_8888);// todo detect if RGB is enough
		byte[] pixels = new byte[binPack.getTextureWidth()
				* binPack.getTextureHeight() * 4];
		for (PackedImage packedImage : binPack.getPackedImages()) {
			copyPixels(packedImage, pixels, binPack.getTextureWidth());
		}
		builder.setPixels(ByteString.copyFrom(pixels));
		Image image = builder.build();

		if (commandLine.hasOption("debug")) {
			BufferedImage taisteImage = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			NanimParserUtils.setRgba(taisteImage, image);
			try {
				ImageIO.write(taisteImage, "png", new File("nanimopt_debug_" + image.getName() + ".png"));
			} catch (IOException e) {
				System.err.println(e);
			}
		}
		return image;
	}

	private void copyPixels(PackedImage packedImage, byte[] texturePixels,
			int textureWidth) {
		NanimParser.Image image = (Image) packedImage.getId();

		ByteString srcPixels = image.getPixels();
		int packedBpp = image.getFormat() == PixelFormat.RGBA_8888 ? 4 : 3;
		int srcIndex = 0;
		for (int y = packedImage.getY1(); y < packedImage.getY2(); ++y) {
			for (int x = packedImage.getX1(); x < packedImage.getX2(); ++x) {
				int destIndex = (x + y * textureWidth) * 4;
				for (int i = 0; i < packedBpp; ++i) {
					texturePixels[destIndex++] = srcPixels.byteAt(srcIndex++);
				}
			}
		}
	}

	private void decode() throws IOException {
		File inputFile = new File(commandLine.getOptionValue("i"));
		FileInputStream is = new FileInputStream(inputFile);
		try {
			inputNanim = NanimParser.Nanim.parseFrom(is);
		} finally {
			is.close();
		}

	}
}