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

import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParserUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * @author devnewton
 *
 */
public class NanimDec {

    @Option(name = "-o", usage = "output directory")
    private File outputDir;

    @Argument(required = true, usage = "input nanim file")
    private File inputFile;

    private Nanim nanim;
    private int nbImageDecoded = 0;

    public static void main(String[] args) throws IOException {
        NanimDec nanimDec = new NanimDec();
        CmdLineParser parser = new CmdLineParser(nanimDec);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("nanimMerge [args] foo.nanim");
            parser.printUsage(System.err);
            return;
        }
        nanimDec.decode();
        nanimDec.save();
    }

    private void decode() throws IOException {
        nanim = NanimParserUtils.decode(inputFile);
    }

    private void save() throws IOException {
        if(null == outputDir ) {
            outputDir = inputFile.getParentFile();
        }
        for (Image image : nanim.getImagesList()) {
            saveImage(image);
        }
    }

    private void saveImage(Image image) throws IOException {
        BufferedImage outputImage = null;
        switch (image.getFormat()) {
            case RGBA_8888:
                outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
                NanimParserUtils.setRgba(outputImage, image);
                break;
            case RGB_888:
                outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                NanimParserUtils.setRgb(outputImage, image);
                break;
        }
        if (null != outputImage) {
            String imageName = image.getName();
            if(!imageName.endsWith(".png")) {
                imageName += ".png";
            }
            File outputImageFile = new File(outputDir, imageName);
            if (!isFilenameValid(outputImageFile)) {
                outputImageFile = new File(outputDir, "nanim_decoded_" + nbImageDecoded++ + ".png");
            }
            ImageIO.write(outputImage, "png", outputImageFile);
        }
    }

    public static boolean isFilenameValid(File file) {
        try {
            file.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
