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

import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParser.Nanim.Builder;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class NanimRename {

	private CommandLine commandLine;
	private final String oldName;
	private final String newName;

	public NanimRename(CommandLine line) {
		this.commandLine = line;
		this.oldName = line.getArgList().get(0).toString();
		this.newName = line.getArgList().get(1).toString();
	}

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new Options();
		options.addOption("a", false, "rename animations");
		options.addOption("i", false, "rename images");

		if (args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("nanimrename [args] oldname newname foo.nanim foo2.nanim foo3.nanim ...", options);
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);
		
		if ((!line.hasOption("a") && !line.hasOption("i")) || line.getArgList().size()<3 ) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("nanimrename [args] oldname newname foo.nanim foo2.nanim foo3.nanim ...", options);
			return;
		}

		NanimRename nanimRename = new NanimRename(line);
		for(int i=2; i<line.getArgList().size(); ++i) {
			String filename = line.getArgList().get(i).toString();
			Nanim nanim = nanimRename.decode(filename);
			nanim = nanimRename.rename(nanim);
			nanimRename.save(filename, nanim);
		}
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

	private Nanim rename(Nanim oldNanim) {
		Nanim.Builder newNanim = Nanim.newBuilder(oldNanim);
		if(commandLine.hasOption("a")) {
			renameAnimations(newNanim);
		}
		if(commandLine.hasOption("i")) {
			renameImages(newNanim);
		}
		return newNanim.build();
	}

	private void renameImages(Builder nanim) {
		for(int i=0; i<nanim.getImagesCount(); ++i) {
			Image oldImage = nanim.getImages(i);
			if(oldImage.getName().equals(oldName)) {
				nanim.setImages(i, Image.newBuilder(oldImage).setName(newName));
			}
		}
		for(int a=0; a<nanim.getAnimationsCount(); ++a) {
			Animation animation = nanim.getAnimations(a);
			Animation.Builder newAnimation = Animation.newBuilder(animation);
			for(int f=0; f<newAnimation.getFramesCount(); ++f) {
				Frame oldFrame = newAnimation.getFrames(f);
				if(oldFrame.getImageName().equals(oldName)) {
					newAnimation.setFrames(f, Frame.newBuilder(oldFrame).setImageName(newName));
				}
			}
			nanim.setAnimations(a, newAnimation);
		}		
	}

	private void renameAnimations(Builder nanim) {
		for(int i=0; i<nanim.getAnimationsCount(); ++i) {
			Animation oldAnimation = nanim.getAnimations(i);
			if(oldAnimation.getName().equals(oldName)) {
				nanim.setAnimations(i, Animation.newBuilder(oldAnimation).setName(newName));
			}
		}
	}

	private void save(String filename, Nanim nanim) throws IOException {
		FileOutputStream os = new FileOutputStream(filename);
		try {
			nanim.writeTo(os);
			System.out.println("nanim successfully written to " + filename);
		} finally {
			os.flush();
			os.close();
		}
	}

}
