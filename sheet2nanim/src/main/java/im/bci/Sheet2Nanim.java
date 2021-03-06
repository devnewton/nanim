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

import com.google.protobuf.ByteString;
import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser.PixelFormat;
import im.bci.nanim.NanimParserUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Sheet2Nanim {

    private Nanim nanim;
    @Option(name = "-s", required = false, usage = "tile spacing")
    private int spacing = 0;
    @Option(name = "-m", required = false, usage = "margin")
    private int margin = 0;
    @Option(name = "-w", required = true, usage = "tile width")
    private int tileWidth;
    @Option(name = "-h", required = true, usage = "tile height")
    private int tileHeight;
    @Option(name = "-a", usage = "animation name")
    private String animationName = "animation0";
    @Option(name = "-d", usage = "frame duration in ms")
    private int frameDuration = 100;
    @Option(name = "-author", usage = "set author metadata")
    private String author;
    @Option(name = "-license", usage = "set license metadata")
    private String license;

    public Sheet2Nanim() {
    }

    public Sheet2Nanim(File inputSpriteSheetFile, int frameWidth, int frameHeight, int frameDuration) {
        this.tileWidth = frameWidth;
        this.tileHeight = frameHeight;
        this.frameDuration = frameDuration;
        this.inputSpriteSheetFile = inputSpriteSheetFile;
    }
    @Option(name = "-o", usage = "output file name")
    private File outputFile = new File("output.nanim");
    @Argument(required = true)
    private File inputSpriteSheetFile;

    public static void main(String[] args) throws IOException {
        Sheet2Nanim sheet2nanim = new Sheet2Nanim();
        CmdLineParser parser = new CmdLineParser(sheet2nanim);
        try {

            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("sheet2nanim [args] spritesheet.png");
            parser.printUsage(System.err);
            return;
        }
        sheet2nanim.convert();
        sheet2nanim.save();
    }

    private void save() throws IOException {
        NanimParserUtils.writeTo(nanim, outputFile);
    }

    public void convert() throws IOException {
        Nanim.Builder nanimBuilder = Nanim.newBuilder();
        if (null != author) {
            nanimBuilder.setAuthor(author);
        }
        if (null != license) {
            nanimBuilder.setLicense(license);
        }
        Image image = encodeImage(inputSpriteSheetFile).build();
        nanimBuilder.addImages(image);
        Animation.Builder animation = Animation.newBuilder();
        animation.setName(animationName);
        final int maxX = image.getWidth() - margin;
        final int maxY = image.getHeight() - margin;
        int id = 0;
        for (int y = margin; y < maxY; y += tileHeight + spacing) {
            for (int x = margin; x < maxX; x += tileWidth + spacing) {
                float u1 = x / (float)image.getWidth();
                float v1= y / (float)image.getHeight();
                float u2 = (x + tileWidth) / (float)image.getWidth();
                float v2 = (y + tileHeight) / (float)image.getHeight();
                Frame.Builder frame = Frame.newBuilder().setDuration(frameDuration).setImageName(image.getName()).setU1(u1).setU2(u2).setV1(v1).setV2(v2);
                animation.addFrames(frame);
            }
        }
        nanimBuilder.addAnimations(animation);
        nanim = nanimBuilder.build();
    }

    public Nanim getNanim() {
        return nanim;
    }

    private Image.Builder encodeImage(File imageFile) throws IOException {
        Image.Builder image = Image.newBuilder();
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        image.setName(imageFile.getName());
        image.setWidth(bufferedImage.getWidth());
        image.setHeight(bufferedImage.getHeight());

        if (bufferedImage.getColorModel().hasAlpha()) {
            image.setFormat(PixelFormat.RGBA_8888);
            image.setPixels(ByteString.copyFrom(NanimParserUtils.getRGBAPixels(bufferedImage)));
        } else {
            image.setFormat(PixelFormat.RGB_888);
            image.setPixels(ByteString.copyFrom(NanimParserUtils.getRGBPixels(bufferedImage)));
        }
        return image;
    }
}
