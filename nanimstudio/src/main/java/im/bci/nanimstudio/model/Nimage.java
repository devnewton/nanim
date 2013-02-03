/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.model;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author bob
 */
public class Nimage {

    private BufferedImage image;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, name);
    }
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage newImage) {
        BufferedImage oldImage = image;
        this.image = newImage;
        propertyChangeSupport.firePropertyChange("image", oldImage, image);
    }
}
