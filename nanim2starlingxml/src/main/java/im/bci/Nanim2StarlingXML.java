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
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParserUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author devnewton
 *
 */
public class Nanim2StarlingXML {

    @Option(name = "--output-name", usage = "output base name for json data and png images")
    private String outputBaseName;

    @Option(name = "-o", usage = "output directory")
    private File outputDir;

    @Argument(required = true, usage = "input nanim file", metaVar = "foo.nanim")
    private File inputFile;

    private Nanim nanim;
    private int nbImageDecoded;
    
    static class ImageData {
        File file;
        int width;
        int height;
    }
    
    private final Map<String, ImageData> imagesData = new HashMap<String, ImageData>();

    public Nanim2StarlingXML() {
    }

    public Nanim2StarlingXML(Nanim nanim, File outputDir, String outputBaseName) {
        this.outputBaseName = outputBaseName;
        this.outputDir = outputDir;
        this.nanim = nanim;
    }

    public static void main(String[] args) throws Exception {
        Nanim2StarlingXML Nanim2StarlingXML = new Nanim2StarlingXML();
        CmdLineParser parser = new CmdLineParser(Nanim2StarlingXML);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("nanim2starlingxml [args] foo.nanim");
            parser.printUsage(System.err);
            return;
        }
        Nanim2StarlingXML.decode();
        Nanim2StarlingXML.save();
    }

    public void save() throws Exception {
        if (null == outputDir) {
            outputDir = inputFile.getParentFile();
        }
        if (null == outputBaseName) {
            outputBaseName = inputFile.getName().replace(".gz", "").replace(".nanim", "");
        }
        for (Image image : nanim.getImagesList()) {
            saveImage(image);
        }
        saveXML();
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
            ImageData imageData = new ImageData();
            imageData.file = outputImageFile;
            imageData.width = image.getWidth();
            imageData.height = image.getHeight();
            imagesData.put(image.getName(), imageData);
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

    private void saveXML() throws IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        boolean firstAtlas = true;
        for (Map.Entry<String, ImageData> image : imagesData.entrySet()) {
            Document doc = docBuilder.newDocument();
            Element textureAtlas = doc.createElement("TextureAtlas");
            doc.appendChild(textureAtlas);
            textureAtlas.setAttribute("imagePath", image.getValue().file.getName());
            for (NanimParser.Animation animation : nanim.getAnimationsList()) {
                for (int f = 0; f < animation.getFramesList().size(); ++f) {
                    NanimParser.Frame frame = animation.getFramesList().get(f);
                    if (image.getKey().equals(frame.getImageName())) {
                        Element subTexture = doc.createElement("SubTexture");
                        int imageWidth = image.getValue().width;
                        int imageHeight = image.getValue().height;
                        subTexture.setAttribute("name", animation.getName() + f);
                        subTexture.setAttribute("x", String.valueOf((int) (frame.getU1() * imageWidth)));
                        subTexture.setAttribute("y", String.valueOf((int) (frame.getV1() * imageHeight)));
                        subTexture.setAttribute("width", String.valueOf((int) ((frame.getU2() - frame.getU1()) * imageWidth)));
                        subTexture.setAttribute("height", String.valueOf((int) ((frame.getV2() - frame.getV1()) * imageWidth)));
                        textureAtlas.appendChild(subTexture);
                    }
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
                String filename;
                if(firstAtlas) {
                    filename = outputBaseName + ".xml";
                    firstAtlas = false;
                } else {
                    filename = outputBaseName + "_"  + image.getKey() + ".xml";
                }
		StreamResult result = new StreamResult(new File(outputDir, filename));
		transformer.transform(source, result);
        }
    }

    private void decode() throws IOException {
        nanim = NanimParserUtils.decode(inputFile);
    }

}
