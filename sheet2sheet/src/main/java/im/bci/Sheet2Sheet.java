/*
 * Copyright (c) 2014 devnewton <devnewton@bci.im>
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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Sheet2Sheet {

    @Option(name = "-is", required = false, usage = "input tile spacing")
    private int inputSpacing = 0;
    @Option(name = "-im", required = false, usage = "input margin")
    private int inputMargin = 0;
    @Option(name = "-iw", required = true, usage = "input tile width")
    private int inputTileWidth;
    @Option(name = "-ih", required = true, usage = "input tile height")
    private int inputTileHeight;

    @Option(name = "-os", required = false, usage = "output tile spacing")
    private int outputSpacing = 0;
    @Option(name = "-om", required = false, usage = "output margin")
    private int outputMargin = 0;

    @Option(name = "-o", usage = "output file name")
    private File outputFile = new File("output.png");
    @Argument(required = true)
    private File inputSpriteSheetFile;

    private BufferedImage outputImage;

    public static void main(String[] args) throws IOException {
        Sheet2Sheet sheet2nanim = new Sheet2Sheet();
        CmdLineParser parser = new CmdLineParser(sheet2nanim);
        try {

            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("sheet2sheet [args] spritesheet.png");
            parser.printUsage(System.err);
            return;
        }
        sheet2nanim.convert();
        sheet2nanim.save();
    }

    private void save() throws IOException {
        ImageIO.write(outputImage, "png", outputFile);
    }

    public void convert() throws IOException {
        BufferedImage inputImage = ImageIO.read(inputSpriteSheetFile);
        final int maxX = inputImage.getWidth() - inputMargin;
        final int maxY = inputImage.getHeight() - inputMargin;
        int nbTilesX = 0;
        for (int x = inputMargin; x < maxX; x += inputTileWidth + inputSpacing) {
            ++nbTilesX;
        }
        int nbTilesY = 0;
        for (int y = inputMargin; y < maxY; y += inputTileHeight + inputSpacing) {
            ++nbTilesY;
        }
        outputImage = new BufferedImage(2 * outputMargin + nbTilesX * inputTileWidth + (nbTilesX - 1) * outputSpacing, 2 * outputMargin + nbTilesY * inputTileHeight + (nbTilesY - 1) * outputSpacing, BufferedImage.TYPE_INT_ARGB);
        Graphics g = outputImage.getGraphics();
        for (int y = inputMargin, oy = outputMargin; y < maxY; y += inputTileHeight + inputSpacing) {
            for (int x = inputMargin, ox = outputMargin; x < maxX; x += inputTileWidth + inputSpacing) {
                BufferedImage inputTileImage = inputImage.getSubimage(x, y, inputTileWidth, inputTileHeight);
                g.drawImage(inputTileImage, ox, oy, null);
                ox += inputTileWidth + outputSpacing;
            }
            oy += inputTileHeight + outputSpacing;
        }
    }

}
