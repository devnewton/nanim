/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author bob
 */
public class Nanimation {

    private String name;
    private List<Nframe> frames = Collections.emptyList();

    public List<Nframe> getFrames() {
        return frames;
    }

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

    public Nframe addNewFrame() {
        List<Nframe> oldFrames = frames;
        frames = new ArrayList<Nframe>(frames);
        Nframe frame = new Nframe();
        frame.setIndex(oldFrames.size());
        frames.add(frame);
        propertyChangeSupport.firePropertyChange("frames", oldFrames, frames);
        return frame;
    }

    public void removeFrames(int[] selectedIndices) {
        List<Nframe> oldFrames = frames;
        frames = new ArrayList<Nframe>(oldFrames);
        for(int i : selectedIndices) {
            frames.remove(oldFrames.get(i));
        }
        for(int i=0; i<frames.size(); ++i) {
            frames.get(i).setIndex(i);
        }
        propertyChangeSupport.firePropertyChange("frames", oldFrames, frames);
    }
}
