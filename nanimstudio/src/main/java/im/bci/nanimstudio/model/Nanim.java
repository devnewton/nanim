/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.model;

import com.google.protobuf.ByteString;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParserUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author bob
 */
public class Nanim {

    private List<Nanimation> animations = Collections.emptyList();
    private List<Nimage> images = Collections.emptyList();

    public List<Nimage> getImages() {
        return images;
    }

    public List<Nanimation> getAnimations() {
        return animations;
    }
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public Nimage addNewImage() {
        Nimage nimage = new Nimage();
        nimage.setName(generateNewNimageName());
        List<Nimage> oldImages = images;
        images = new ArrayList<Nimage>(images);
        images.add(nimage);
        propertyChangeSupport.firePropertyChange("images", oldImages, Collections.unmodifiableList(images));
        return nimage;
    }

    private String generateNewNimageName() {
        for (int i = 0;; ++i) {
            String newName = "image_" + i;
            if (!isNimageNameAlreadyUsed(newName)) {
                return newName;
            }
        }
    }

    private boolean isNimageNameAlreadyUsed(String newName) {
        for (Nimage nimage : images) {
            if (newName.equals(nimage.getName())) {
                return true;
            }
        }
        return false;
    }

    public void removeImages(List<String> imagesToRemove) {
        List<Nimage> oldImages = images;
        images = new ArrayList<Nimage>();
        for (Nimage image : oldImages) {
            if (!imagesToRemove.contains(image.getName())) {
                images.add(image);
            }
        }
        propertyChangeSupport.firePropertyChange("images", oldImages, Collections.unmodifiableList(images));
    }

    public void loadImage(String name, File file) {
        Nimage nimage = findImageByName(name);
        if (null != nimage) {
            try {
                nimage.setImage(ImageIO.read(file));
            } catch (IOException ex) {
                Logger.getLogger(Nanim.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Nimage findImageByName(String name) {
        for (Nimage image : images) {
            if (image.getName().equals(name)) {
                return image;
            }
        }
        return null;
    }

    public Nanimation addNewAnimation() {
        Nanimation nanimation = new Nanimation();
        nanimation.setName(generateNewAnimationName());
        List<Nanimation> oldAnimations = animations;
        animations = new ArrayList<Nanimation>(animations);
        animations.add(nanimation);
        propertyChangeSupport.firePropertyChange("animations", oldAnimations, Collections.unmodifiableList(animations));
        return nanimation;
    }

    private String generateNewAnimationName() {
        for (int i = 0;; ++i) {
            String newName = "animation_" + i;
            if (!isAnimationNameAlreadyUsed(newName)) {
                return newName;
            }
        }
    }

    private boolean isAnimationNameAlreadyUsed(String newName) {

        for (Nanimation animation : animations) {
            if (newName.equals(animation.getName())) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        clearAnimations();
        clearImages();
    }

    private void clearAnimations() {
        List<Nanimation> oldAnimations = animations;
        animations = Collections.emptyList();
        propertyChangeSupport.firePropertyChange("animations", oldAnimations, animations);
    }

    private void clearImages() {
        List<Nimage> oldImages = images;
        images = Collections.emptyList();
        propertyChangeSupport.firePropertyChange("images", oldImages, images);
    }

    public void removeAnimations(List<String> animationsToRemove) {
        List<Nanimation> oldAnimations = animations;
        animations = new ArrayList<Nanimation>();
        for (Nanimation animation : oldAnimations) {
            if (!animationsToRemove.contains(animation.getName())) {
                animations.add(animation);
            }
        }
        propertyChangeSupport.firePropertyChange("animations", oldAnimations, Collections.unmodifiableList(animations));
    }

    public void saveAs(File file) {
        try {
            FileOutputStream os = new FileOutputStream(file);
            try {
                buildProtobufNanim().writeTo(os);
                System.out.println("nanim successfully written to " + file.getAbsolutePath());
            } finally {
                os.flush();
                os.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Nanim.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private NanimParser.Animation.Builder encodeAnimation(Nanimation nanimation) {
        NanimParser.Animation.Builder animation = NanimParser.Animation.newBuilder();
        animation.setName(nanimation.getName());
        for (Nframe nframe : nanimation.getFrames()) {
            animation.addFrames(encodeFrame(nframe));
        }
        return animation;
    }

    private NanimParser.Frame.Builder encodeFrame(Nframe nframe) {
        NanimParser.Frame.Builder frame = NanimParser.Frame.newBuilder();
        frame.setDuration(nframe.getDuration());
        frame.setImageName(nframe.getNimage().getName());
        frame.setU1(nframe.getU1());
        frame.setV1(nframe.getV1());
        frame.setU2(nframe.getU2());
        frame.setV2(nframe.getV2());
        return frame;

    }

    private NanimParser.Image.Builder encodeImage(Nimage nimage) {
        NanimParser.Image.Builder image = NanimParser.Image.newBuilder();
        BufferedImage bufferedImage = nimage.getImage();
        image.setName(nimage.getName());
        image.setWidth(bufferedImage.getWidth());
        image.setHeight(bufferedImage.getHeight());

        if (bufferedImage.getColorModel().hasAlpha()) {
            image.setFormat(NanimParser.PixelFormat.RGBA_8888);
            image.setPixels(ByteString.copyFrom(NanimParserUtils
                    .getRGBAPixels(bufferedImage)));
        } else {
            image.setFormat(NanimParser.PixelFormat.RGB_888);
            image.setPixels(ByteString.copyFrom(NanimParserUtils
                    .getRGBPixels(bufferedImage)));
        }
        return image;
    }

    public void open(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            try {
                loadProtobufNanim(NanimParser.Nanim.parseFrom(is));
            } finally {
                is.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Nanim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProtobufNanim(NanimParser.Nanim nanim) {
        clear();
        for (NanimParser.Image image : nanim.getImagesList()) {
            Nimage nimage = addNewImage();
            nimage.setName(image.getName());
            nimage.setImage(decodeImage(image));
        }
        for (NanimParser.Animation animation : nanim.getAnimationsList()) {
            Nanimation nanimation = addNewAnimation();
            nanimation.setName(animation.getName());
            for (NanimParser.Frame frame : animation.getFramesList()) {
                Nframe nframe = nanimation.addNewFrame();
                nframe.setDuration(frame.getDuration());
                nframe.setU1(frame.getU1());
                nframe.setV1(frame.getV1());
                nframe.setU2(frame.getU2());
                nframe.setV2(frame.getV2());
                nframe.setNimage(findImageByName(frame.getImageName()));
            }
        }
    }

    private BufferedImage decodeImage(Image image) {
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
        return outputImage;
    }

    public void openGIF(File file) {
        clear();
        GifDecoder decoder = new GifDecoder();
        decoder.read(file.getAbsolutePath());
        Nanimation nanimation = addNewAnimation();
        for (int i = 0; i < decoder.getFrameCount(); ++i) {
            Nimage nimage = addNewImage();
            nimage.setImage(decoder.getFrame(i));
            Nframe nframe = nanimation.addNewFrame();
            nframe.setDuration(decoder.getDelay(i));
            nframe.setNimage(nimage);
        }
        System.out.println("gif successfully written to " + file.getAbsolutePath());
    }

    public void saveGIF(File file) {
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(file.getAbsolutePath());
        encoder.setRepeat(0);
        for (Nanimation animation : animations) {
            for (Nframe nframe : animation.getFrames()) {
                encoder.setDelay(nframe.getDuration());
                encoder.addFrame(nframe.getImage());
            }
        }
        encoder.finish();
    }

    public im.bci.nanim.NanimParser.Nanim buildProtobufNanim() {
        NanimParser.Nanim.Builder nanimBuilder = NanimParser.Nanim.newBuilder();
        for (Nimage nimage : images) {
            nanimBuilder.addImages(encodeImage(nimage));
        }
        for (Nanimation nanimation : animations) {
            nanimBuilder.addAnimations(encodeAnimation(nanimation));
        }
        return nanimBuilder.build();
    }

    public void generateSpriteSheet(File file, SpriteSheetType type) {
        int maxWidth = 0, maxHeight = 0, totalWidth = 0, totalHeight = 0;
        for (Nanimation animation : animations) {
            for (Nframe nframe : animation.getFrames()) {
                maxWidth = Math.max(maxWidth, nframe.getImage().getWidth());
                maxHeight = Math.max(maxHeight, nframe.getImage().getHeight());
                totalWidth += nframe.getImage().getWidth();
                totalHeight += nframe.getImage().getWidth();
            }
        }
        BufferedImage spriteSheet;
        switch (type) {
            case HORIZONTAL:
                spriteSheet = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
                break;
            case VERTICAL:
                spriteSheet = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
                break;
            default:
                return;
        }
        int x = 0, y = 0;
        Graphics g = spriteSheet.getGraphics();
        try {
            for (Nanimation animation : animations) {
                for (Nframe nframe : animation.getFrames()) {
                    g.drawImage(nframe.getImage(), x, y, null);
                    switch (type) {
                        case HORIZONTAL:
                            x += nframe.getImage().getWidth();
                            break;
                        case VERTICAL:
                            y += nframe.getImage().getHeight();
                            break;
                    }
                }
            }
        } finally {
            g.dispose();
        }
        try {
            ImageIO.write(spriteSheet, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(Nanim.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}