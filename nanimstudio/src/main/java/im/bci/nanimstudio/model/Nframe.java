/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.model;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bob
 */
public class Nframe {

    private int index;
    private int duration;
    private float u1 = 0f, v1 = 0f, u2 = 1f, v2 = 1f;
    private Nimage nimage;
    private BufferedImage image;

    public Nimage getNimage() {
        return nimage;
    }

    public void setNimage(Nimage nimage) {
        this.nimage = nimage;
        updateImage();
    }

    public float getU1() {
        return u1;
    }

    public void setU1(float u1) {
        this.u1 = u1;
        updateImage();
    }

    public float getV1() {
        return v1;
    }

    public void setV1(float v1) {
        this.v1 = v1;
        updateImage();
    }

    public float getU2() {
        return u2;
    }

    public void setU2(float u2) {
        this.u2 = u2;
        updateImage();
    }

    public float getV2() {
        return v2;
    }

    public void setV2(float v2) {
        this.v2 = v2;
        updateImage();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BufferedImage getImage() {
        return image;
    }

    private void updateImage() {
        if (null != nimage && null != nimage.getImage()) {
            int x = (int) (u1 * nimage.getImage().getWidth());
            int y = (int) (v1 * nimage.getImage().getHeight());
            int w = (int) ((u2 - u1) * nimage.getImage().getWidth());
            int h = (int) ((v2 - v1) * nimage.getImage().getHeight());
            try {
                image = nimage.getImage().getSubimage(x, y, w, h);
            } catch (RasterFormatException ex) {
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "Cannot get frame image", ex);
                image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }
        }
    }
}
