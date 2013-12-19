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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParserUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * @author devnewton
 *
 */
public class Nanim2JSON {
    
    @Option(name="--output-name", usage="output base name for json data and png images")
    private String outputBaseName;

    @Option(name = "-o", usage = "output directory")
    private File outputDir;

    @Argument(required = true, usage = "input nanim file")
    private File inputFile;

    private Nanim nanim;
    private int nbImageDecoded;
    private final Map<String, File> imageNamesToFiles = new HashMap<String, File>();
    
    public Nanim2JSON() {
    }
    public Nanim2JSON(Nanim nanim, File outputDir, String outputBaseName) {
        this.outputBaseName = outputBaseName;
        this.outputDir = outputDir;
        this.nanim = nanim;
    }   

    public static void main(String[] args) throws IOException {
        Nanim2JSON nanim2json = new Nanim2JSON();
        CmdLineParser parser = new CmdLineParser(nanim2json);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("nanimMerge [args] foo.nanim");
            parser.printUsage(System.err);
            return;
        }
        nanim2json.decode();
        nanim2json.save();
    }

    public void save() throws IOException {
        if (null == outputDir) {
            outputDir = inputFile.getParentFile();
        }
        if(null == outputBaseName) {
            outputBaseName = inputFile.getName().replace(".gz", "").replace(".nanim", "");
        }
        for (Image image : nanim.getImagesList()) {
            saveImage(image);
        }
        saveJson();
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
            if (!imageName.endsWith(".png")) {
                imageName += ".png";
            }
            File outputImageFile = new File(outputDir, imageName);
            if (!isFilenameValid(outputImageFile)) {
                outputImageFile = new File(outputDir, outputBaseName + nbImageDecoded++ + ".png");
            }
            imageNamesToFiles.put(image.getName(), outputImageFile);
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

    private void saveJson() throws IOException {
        JsonObject jsonNanim = new JsonObject();
        if (null != nanim.getAuthor() && !nanim.getAuthor().isEmpty()) {
            jsonNanim.addProperty("author", nanim.getAuthor());
        }
        if (null != nanim.getLicense() && !nanim.getLicense().isEmpty()) {
            jsonNanim.addProperty("license", nanim.getLicense());
        }
        JsonArray jsonAnimations = new JsonArray();
        for (NanimParser.Animation animation : nanim.getAnimationsList()) {
            JsonObject jsonAnimation = new JsonObject();
            jsonAnimation.addProperty("name", animation.getName());
            JsonArray jsonFrames = new JsonArray();
            for (NanimParser.Frame frame : animation.getFramesList()) {
                JsonObject jsonFrame = new JsonObject();
                jsonFrame.addProperty("duration", frame.getDuration());
                jsonFrame.addProperty("image", imageNamesToFiles.get(frame.getImageName()).getName());
                jsonFrame.addProperty("u1", frame.getU1());
                jsonFrame.addProperty("v1", frame.getV1());
                jsonFrame.addProperty("u2", frame.getU2());
                jsonFrame.addProperty("v2", frame.getV2());
                jsonFrames.add(jsonFrame);
            }
            jsonAnimation.add("frames", jsonFrames);
            jsonAnimations.add(jsonAnimation);
        }
        jsonNanim.add("animations", jsonAnimations);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(new File(outputDir, outputBaseName + ".json"));
        try {
            gson.toJson(jsonNanim, writer);
        } finally {
            writer.close();
        }
    }

    private void decode() throws IOException {
         nanim = NanimParserUtils.decode(inputFile);
    }

}
