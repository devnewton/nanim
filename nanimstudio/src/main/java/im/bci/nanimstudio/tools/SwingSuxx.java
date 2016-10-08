/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio.tools;

import java.text.DecimalFormat;
import javax.swing.JSpinner;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author bcolombi
 */
public class SwingSuxx {

    public static void fixJspinner(JSpinner spinner) {
        if (spinner.getEditor() instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
            if (editor.getTextField().getFormatter() instanceof DefaultFormatter) {
                DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
                formatter.setCommitsOnValidEdit(true);
            }

        }
    }

    public static void setPreciseJSpinner(JSpinner spinner) {
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMaximumFractionDigits(340);
    }

}
