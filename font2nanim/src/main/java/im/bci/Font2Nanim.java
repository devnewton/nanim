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

import im.bci.nanim.NanimParser.Image.Builder;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;


import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser.PixelFormat;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParserUtils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.protobuf.ByteString;
import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Frame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import javax.imageio.ImageIO;

public class Font2Nanim {

    private Nanim nanim;
    private CommandLine commandLine;
    private String chars;
    private Font font;
    private int charwidth, charheight;

    public Font2Nanim(CommandLine line) {
        this.commandLine = line;
        chars = commandLine.getOptionValue("additionnalchars", "");
        for (int i = 0; i < 128; ++i) {
            chars += (char) i;
        }
        font = createFont();
        charwidth = Integer.parseInt(commandLine.getOptionValue("charwidth", "32"));
        charheight = Integer.parseInt(commandLine.getOptionValue("charheight", "32"));
    }

    public static void main(String[] args) throws ParseException, IOException {
        Options options = new Options();
        options.addOption("font", true, "font name (examples: monospaced, sansserif)");
        options.addOption("antialiasing", false, "activate antialiasing");
        options.addOption("style", true, "style (plain, bold or italic)");
        options.addOption("fontsize", true, "font size");
        options.addOption("additionnalchars", true, "add characters to ascii base");
        options.addOption("custom", true, "custom character c:imagefilename");
        options.addOption("charwidth", true, "character rendered width (default 32)");
        options.addOption("charheight", true, "character rendered height (default 32)");
        options.addOption("o", true, "output file (default output.nanim)");

        if (args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("font2nanim [args]", options);
            return;
        }
        GnuParser parser = new GnuParser();
        CommandLine line = parser.parse(options, args);

        Font2Nanim nanimEnc = new Font2Nanim(line);
        nanimEnc.encode();
        nanimEnc.save();
    }

    private void save() throws IOException {
        String output = commandLine.getOptionValue("o", "output.nanim");
        NanimParserUtils.writeTo(nanim, new File(output));
    }

    private void encode() throws IOException {
        Nanim.Builder nanimBuilder = Nanim.newBuilder();
        for (char c : chars.toCharArray()) {
            Image.Builder image = createImageForChar(c);
            if (null != image) {
                image.setName("char_" + (int)c);
                nanimBuilder.addAnimations(Animation.newBuilder().setName(String.valueOf(c)).addFrames(Frame.newBuilder().setImageName(image.getName()).setDuration(1000).setU1(0.0f).setV1(0.0f).setU2(1.0f).setV2(1.0f)));
                nanimBuilder.addImages(image);
            }
        }
        nanim = nanimBuilder.build();
    }

    private Builder createImageForChar(char c) throws IOException {
        return encodeImage(getCharacterImage(c));
    }

    private Image.Builder encodeImage(BufferedImage bufferedImage) throws IOException {
        if (null != bufferedImage) {
            Image.Builder image = Image.newBuilder();
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
        } else {
            return null;
        }
    }

    private BufferedImage getCharacterImage(char c) throws IOException {
        BufferedImage image = getCustomImage(c);
        if (null != image) {
            return image;
        }
        return getStandardImage(c);
    }

    private BufferedImage getCustomImage(char c) throws IOException {
        final String[] customs = commandLine.getOptionValues("custom");
        if (null != customs) {
            for (String custom : customs) {
                String[] splittedCustom = custom.split(":");
                if (splittedCustom.length == 2) {
                    String ch = splittedCustom[0];
                    if (ch.equals(String.valueOf(c))) {
                        String filename = splittedCustom[1];
                        return ImageIO.read(new File(filename));
                    }
                }
            }
        }
        return null;
    }

    private BufferedImage getStandardImage(char c) {
        if (font.canDisplay(c) && !Character.isISOControl(c) && !Character.isSpaceChar(c)) {
            BufferedImage fontImage = new BufferedImage(charwidth, charheight,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D gt = (Graphics2D) fontImage.getGraphics();
            if (commandLine.hasOption("antialiasing")) {
                gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
            }
            gt.setFont(font);
            gt.setColor(Color.WHITE);
            int charx = 3;
            int chary = 1;
            gt.drawString(String.valueOf(c), charx,
                    (chary) + gt.getFontMetrics().getAscent());
            return fontImage;
        } else {
            return null;
        }
    }

    private Font createFont() {
        String name = commandLine.getOptionValue("font", "monospaced");
        int style = Font.PLAIN;
        if ("bold".equals(commandLine.getOptionValue("style"))) {
            style = Font.BOLD;
        } else if ("italic".equals(commandLine.getOptionValue("style"))) {
            style = Font.ITALIC;
        }
        int size = Integer.parseInt(commandLine.getOptionValue("fontsize", "24"));
        return new Font(name, style, size);
    }
}
