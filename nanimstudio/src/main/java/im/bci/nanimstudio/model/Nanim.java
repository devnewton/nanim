/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
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

    private List<Nimage> images = Collections.emptyList();

    public List<Nimage> getImages() {
        return images;
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
}
