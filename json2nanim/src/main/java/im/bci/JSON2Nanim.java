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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser.PixelFormat;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParserUtils;

import com.google.protobuf.ByteString;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class JSON2Nanim {

    @Option(name = "-o", usage = "output file")
    private File outputFile;

    @Argument(required = true, usage = "input json file", metaVar = "foo.json")
    private File inputFile;

    private Nanim nanim;

    public JSON2Nanim() {
    }

    public JSON2Nanim(File inputFile) {
        this.inputFile = inputFile;
    }

    public static void main(String[] args) throws IOException {
        JSON2Nanim json2nanim = new JSON2Nanim();
        CmdLineParser parser = new CmdLineParser(json2nanim);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("json2nanim [args] foo.json");
            parser.printUsage(System.err);
            return;
        }

        json2nanim.loadJson();
        json2nanim.save();
    }
    
    public Nanim load() throws IOException {
        loadJson();
        return nanim;
    }

    private void save() throws IOException {
        NanimParserUtils.writeTo(nanim, outputFile);
    }

    private void loadJson() throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(inputFile);
        try {
            Map<String, File> imageNameToFiles = new HashMap<String, File>();
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);
            Nanim.Builder nanimBuilder = Nanim.newBuilder();
            for (JsonElement jsonAnimationElement : json.getAsJsonArray("animations")) {
                JsonObject jsonAnimation = jsonAnimationElement.getAsJsonObject();
                final Animation.Builder animationBuilder = Animation.newBuilder();
                animationBuilder.setName(jsonAnimation.get("name").getAsString());
                for (JsonElement jsonFrameElement : jsonAnimation.getAsJsonArray("frames")) {
                    JsonObject jsonFrame = jsonFrameElement.getAsJsonObject();
                    final Frame.Builder frameBuilder = Frame.newBuilder();
                    frameBuilder.setDuration(jsonFrame.get("duration").getAsInt());
                    frameBuilder.setU1(jsonFrame.get("u1").getAsFloat());
                    frameBuilder.setV1(jsonFrame.get("v1").getAsFloat());
                    frameBuilder.setU2(jsonFrame.get("u2").getAsFloat());
                    frameBuilder.setV2(jsonFrame.get("v2").getAsFloat());
                    final String imageFilename = jsonFrame.get("image").getAsString();
                    final String imageName = imageFilename.replace(".png", "");
                    imageNameToFiles.put(imageName, new File(inputFile.getParentFile(), imageFilename));
                    frameBuilder.setImageName(imageName);
                    animationBuilder.addFrames(frameBuilder);
                }
                nanimBuilder.addAnimations(animationBuilder);
            }
            for(Map.Entry<String, File> entry : imageNameToFiles.entrySet()) {
                nanimBuilder.addImages(encodeImage(entry.getKey(), entry.getValue()));
            }
            nanim = nanimBuilder.build();
        } finally {
            reader.close();
        }
    }

    private Image.Builder encodeImage(String imageName, File imageFile) throws IOException {
        Image.Builder image = Image.newBuilder();
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        image.setName(imageName);
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
