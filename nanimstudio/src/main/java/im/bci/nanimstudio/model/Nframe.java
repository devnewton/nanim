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
package im.bci.nanimstudio.model;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bob
 */
public class Nframe implements PropertyChangeListener {

    private int index;
    private int duration;
    private float u1 = 0f, v1 = 0f, u2 = 1f, v2 = 1f;
    private Nimage nimage;
    private BufferedImage image;
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public Nimage getNimage() {
        return nimage;
    }

    public void setNimage(Nimage nimage) {
        Nimage oldNimage = this.nimage;
        if (null != nimage) {
            nimage.removePropertyChangeListener(this);
        }
        this.nimage = nimage;
        if (null != nimage) {
            nimage.addPropertyChangeListener(this);
        }
        updateImage();
        propertyChangeSupport.firePropertyChange("nimage", oldNimage, this.nimage);
    }

    public float getU1() {
        return u1;
    }

    public void setU1(float u1) {
        float oldU1 = this.u1;
        this.u1 = u1;
        updateImage();
        propertyChangeSupport.firePropertyChange("u1", oldU1, this.u1);
    }

    public float getV1() {
        return v1;
    }

    public void setV1(float v1) {
        float oldV1 = this.v1;
        this.v1 = v1;
        updateImage();
        propertyChangeSupport.firePropertyChange("v1", oldV1, this.v1);
    }

    public float getU2() {
        return u2;
    }

    public void setU2(float u2) {
        float oldU2 = this.u2;
        this.u2 = u2;
        updateImage();
        propertyChangeSupport.firePropertyChange("u2", oldU2, this.u2);
    }

    public float getV2() {
        return v2;
    }

    public void setV2(float v2) {
        float oldV2 = this.v2;
        this.v2 = v2;
        updateImage();
        propertyChangeSupport.firePropertyChange("v2", oldV2, this.v2);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        float oldDuration = this.duration;
        this.duration = duration;
        propertyChangeSupport.firePropertyChange("duration", oldDuration, this.duration);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        float old = this.index;
        this.index = index;
        propertyChangeSupport.firePropertyChange("index", old, this.index);
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

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (nimage == event.getSource()) {
            updateImage();
        }
    }
}
