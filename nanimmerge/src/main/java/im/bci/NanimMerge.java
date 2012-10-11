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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class NanimMerge {

	private Nanim.Builder nanimOut = Nanim.newBuilder();
	private CommandLine commandLine;
	private HashSet<String> imageNames = new HashSet<String>();
	private HashSet<String> animationNames = new HashSet<String>();

	public NanimMerge(CommandLine line) {
		this.commandLine = line;
	}

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new Options();
		options.addOption("o", true, "output file name");
		options.addOption("author", true, "set author metadata");
		options.addOption("license", true, "set license metadata");

		if (args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("nanimmerge [args] foo.nanim foo2.nanim foo3.nanim ...", options);
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);
		
		if (!line.hasOption("o")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("nanimmerge [args] foo.nanim foo2.nanim foo3.nanim ...", options);
			return;
		}

		NanimMerge nanimMerge = new NanimMerge(line);
		for(Object filename : line.getArgList()) {
			Nanim nanim = nanimMerge.decode(filename.toString());
			nanimMerge.merge(nanim);
		}
		nanimMerge.completeMerge();
		nanimMerge.save();
	}

	private Nanim decode(String filename) throws IOException {
		File inputFile = new File(filename);
		FileInputStream is = new FileInputStream(inputFile);
		try {
			return NanimParser.Nanim.parseFrom(is);
		} finally {
			is.close();
		}
	}

	private void merge(Nanim nanim) {
		HashMap<String, String> oldToNewImageNameMap = new HashMap<String, String>();
		for(Image image : nanim.getImagesList()) {
			final String imageName = image.getName();
			boolean isNew = imageNames.add(imageName);
			if(isNew) {
				nanimOut.addImages(image);
				oldToNewImageNameMap.put(imageName, imageName);
			} else {
				String newImageName = computeNewImageName(imageName);
				System.out.println("image '" + imageName + "' renamed to '" + newImageName + "'");
				oldToNewImageNameMap.put(imageName, newImageName);				
				nanimOut.addImages(Image.newBuilder(image).setName(newImageName).build());
			}
		}
		for(Animation animation : nanim.getAnimationsList()) {
			Animation.Builder animationBuilder = Animation.newBuilder(animation);
			final String animationName = animation.getName();
			boolean isNew = animationNames.add(animationName);
			if(!isNew) {
				final String newAnimationName = computeNewAnimationName(animationName);
				animationBuilder.setName(newAnimationName);
				System.out.println("animation '" + animationName + "' renamed to '" + newAnimationName + "'");
			}
			for(int i = 0; i<animationBuilder.getFramesCount(); ++i) {
				Frame frame = animationBuilder.getFrames(i);
				animationBuilder.setFrames(i, Frame.newBuilder(frame).setImageName(oldToNewImageNameMap.get(frame.getImageName())));
			}
			nanimOut.addAnimations(animationBuilder);
		}
		nanimOut.setAuthor(nanim.getAuthor());
		nanimOut.setLicense(nanim.getLicense());
	}
	
	private String computeNewImageName(String name) {
		for(int i=0; ; ++i) {
			String newName = name + i;
			if(!imageNames.contains(newName)) {
				return newName;
			}
		}
	}

	private String computeNewAnimationName(String name) {
		for(int i=0; ; ++i) {
			String newName = name + i;
			if(!animationNames.contains(newName)) {
				return newName;
			}
		}
	}
	
	private void completeMerge() {
		if(commandLine.hasOption("author")) {
			nanimOut.setAuthor(commandLine.getOptionValue("author"));
		}
		if(commandLine.hasOption("license")) {
			nanimOut.setLicense(commandLine.getOptionValue("license"));
		}
	}

	private void save() throws IOException {
		String output = commandLine.getOptionValue("o", "output.nanim");
		FileOutputStream os = new FileOutputStream(output);
		try {
			nanimOut.build().writeTo(os);
			System.out.println("nanim successfully written to " + output);
		} finally {
			os.flush();
			os.close();
		}
	}

}
