/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.tools;

import javax.swing.JSpinner;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author bcolombi
 */
public class SwingSuxx {
    public static void fixJspinner(JSpinner spinner) {
        if(spinner.getEditor() instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor)spinner.getEditor();
            if(editor.getTextField().getFormatter() instanceof DefaultFormatter) {
                DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
                formatter.setCommitsOnValidEdit(true);
            }
        
        }
    }
    
}
