/*
 * Copyright (c) 2013 devnewton <devnewton@bci.im>
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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * nanim optimizer
 *
 */
public class NimageOpt {

    @Argument
    private List<File> inputs = new ArrayList<File>();

    public static void main(String[] args) throws IOException {
        NimageOpt nimageOpt = new NimageOpt();
        CmdLineParser parser = new CmdLineParser(nimageOpt);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("nimageopt [args] foo1.png foo2.png ...");
            parser.printUsage(System.err);
            return;
        }
        nimageOpt.optimize();
    }

    private void optimize() {
        for (File input : inputs) {
            if (input.isFile()) {
                optimizeImage(input);
            } else if (input.isDirectory()) {
                Iterator it = FileUtils.iterateFiles(input, new String[]{"png"}, true);
                while (it.hasNext()) {
                    File file = (File) it.next();
                    optimizeImage(file);
                }
            }
        }
    }

    private void optimizeImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            System.out.println("analyze " + file);
            if(image.getColorModel().hasAlpha() && !hasUsefullAlphaChannel(image)) {
                BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(image, 0, 0, null);
                ImageIO.write(newImage, "png", file);
                System.out.println(file + " => useless alpha channel removed");
            }
        } catch (IOException ex) {
            Logger.getLogger(NimageOpt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private static boolean hasUsefullAlphaChannel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int rgba = image.getRGB(x, y);
                byte a = (byte) ((rgba >> 24) & 0xff);
                if (a != -1) {
                    return true;
                }
            }
        }
        return false;
    }

}
