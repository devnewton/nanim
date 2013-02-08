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
import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class NanimMerge {

    private Nanim.Builder mergedNanim = Nanim.newBuilder();
    private HashSet<String> imageNames = new HashSet<String>();
    private HashSet<String> animationNames = new HashSet<String>();
    private HashMap<String, String> oldToNewImageNameMap = new HashMap<String, String>();
    @Option(name = "-author", usage = "set author metadata")
    private String author;
    @Option(name = "-license", usage = "set license metadata")
    private String license;
    @Option(name = "-o", usage = "output file name")
    private File outputFile;
    @Argument(required = true, multiValued = true, usage = "input files")
    private List<File> inputFiles;

    public static void main(String[] args) throws IOException {
        NanimMerge nanimMerge = new NanimMerge();
        CmdLineParser parser = new CmdLineParser(nanimMerge);
        try {

            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("nanimMerge [args] input1.nanim input2.nanim ...");
            parser.printUsage(System.err);
            return;
        }

        for (File inputFile : nanimMerge.inputFiles) {
            Nanim nanim = decode(inputFile);
            nanimMerge.merge(nanim);
        }
        nanimMerge.completeMerge();
        nanimMerge.save();
    }
    
    public Nanim getMergedNanim() {
        return mergedNanim.build();
    }

    private static Nanim decode(File inputFile) throws IOException {
        FileInputStream is = new FileInputStream(inputFile);
        try {
            return NanimParser.Nanim.parseFrom(is);
        } finally {
            is.close();
        }
    }

    public void merge(Nanim nanim) {
        for (Image image : nanim.getImagesList()) {
            final String imageName = image.getName();
            boolean isNew = imageNames.add(imageName);
            if (isNew) {
                mergedNanim.addImages(image);
                oldToNewImageNameMap.put(imageName, imageName);
            } else {
                String newImageName = computeNewImageName(imageName);
                System.out.println("image '" + imageName + "' renamed to '" + newImageName + "'");
                imageNames.add(newImageName);
                oldToNewImageNameMap.put(imageName, newImageName);
                mergedNanim.addImages(Image.newBuilder(image).setName(newImageName).build());
            }
        }
        for (Animation animation : nanim.getAnimationsList()) {
            Animation.Builder animationBuilder = Animation.newBuilder(animation);
            final String animationName = animation.getName();
            boolean isNew = animationNames.add(animationName);
            if (!isNew) {
                final String newAnimationName = computeNewAnimationName(animationName);
                animationBuilder.setName(newAnimationName);
                System.out.println("animation '" + animationName + "' renamed to '" + newAnimationName + "'");
                animationNames.add(newAnimationName);
            }
            for (int i = 0; i < animationBuilder.getFramesCount(); ++i) {
                Frame frame = animationBuilder.getFrames(i);
                animationBuilder.setFrames(i, Frame.newBuilder(frame).setImageName(oldToNewImageNameMap.get(frame.getImageName())));
            }
            mergedNanim.addAnimations(animationBuilder);
        }
        mergedNanim.setAuthor(nanim.getAuthor());
        mergedNanim.setLicense(nanim.getLicense());
    }

    private String computeNewImageName(String name) {
        for (int i = 0;; ++i) {
            String newName = name + i;
            if (!imageNames.contains(newName)) {
                return newName;
            }
        }
    }

    private String computeNewAnimationName(String name) {
        for (int i = 0;; ++i) {
            String newName = name + i;
            if (!animationNames.contains(newName)) {
                return newName;
            }
        }
    }

    private void completeMerge() {
        if (null != author) {
            mergedNanim.setAuthor(author);
        }
        if (null != license) {
            mergedNanim.setLicense(license);
        }
    }

    private void save() throws IOException {
        if(null == outputFile) {
            outputFile = new File("output.nanim");
        }
        FileOutputStream os = new FileOutputStream(outputFile);
        try {
            mergedNanim.build().writeTo(os);
            System.out.println("nanim successfully written to " + outputFile);
        } finally {
            os.flush();
            os.close();
        }
    }

    public void merge(File f) throws IOException {
        merge(decode(f));
    }
}
