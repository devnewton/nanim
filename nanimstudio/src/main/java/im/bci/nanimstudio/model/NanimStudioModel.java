/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.model;

import java.util.prefs.Preferences;

/**
 *
 * @author bob
 */
public class NanimStudioModel {
    
    private final static NanimStudioModel instance = new NanimStudioModel();  
    private Nanim nanim = new Nanim();
    
    public static NanimStudioModel getInstance() {
        return instance;
    }

    /**
     * Get the value of nanim
     *
     * @return the value of nanim
     */
    public Nanim getNanim() {
        return nanim;
    }
    
    public Preferences getPreferences() {
        return Preferences.userRoot().node("nanimstudio");
    }

}
