/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio;

import im.bci.nanimstudio.model.NanimStudioModel;
import im.bci.nanimstudio.model.Nimage;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author bob
 */
public class NimagesEditor extends javax.swing.JPanel {

    private final NanimStudioModel nanimStudio;

    /**
     * Creates new form NimagesEditor
     */
    public NimagesEditor() {
        nanimStudio = NanimStudioModel.getInstance();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        nanim = nanimStudio.getNanim();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_images = new javax.swing.JList();
        jButton_add = new javax.swing.JButton();
        jButton_remove = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton_load = new javax.swing.JButton();
        nimageViewer1 = new im.bci.nanimstudio.NimageViewer();

        setLayout(new java.awt.GridBagLayout());

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${images}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nanim, eLProperty, jList_images);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        bindingGroup.addBinding(jListBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nimageViewer1, org.jdesktop.beansbinding.ELProperty.create("${image}"), jList_images, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jList_images);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        add(jScrollPane1, gridBagConstraints);

        jButton_add.setText("add");
        jButton_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        add(jButton_add, gridBagConstraints);

        jButton_remove.setText("remove");
        jButton_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_removeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        add(jButton_remove, gridBagConstraints);

        jLabel1.setText("name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(jLabel1, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jList_images, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.name}"), jTextField1, org.jdesktop.beansbinding.BeanProperty.create("text"), "");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jTextField1, gridBagConstraints);

        jLabel2.setText("Format:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(jLabel2, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jComboBox1, gridBagConstraints);

        jButton_load.setText("change image");
        jButton_load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        add(jButton_load, gridBagConstraints);

        nimageViewer1.setMaximumSize(new java.awt.Dimension(1100, 1100));
        nimageViewer1.setMinimumSize(new java.awt.Dimension(200, 200));
        nimageViewer1.setPreferredSize(new java.awt.Dimension(512, 512));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(nimageViewer1, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addActionPerformed
        Nimage nimage = nanim.addNewImage();
        boolean loaded = false;
        try {
            JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastImageDirectory", null));
            chooser.setFileFilter(new FileNameExtensionFilter("Images", "png", "jpg"));
            if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
                nanimStudio.getPreferences().put("lastImageDirectory", chooser.getCurrentDirectory().getAbsolutePath());
                nanim.loadImage(nimage.getName(), chooser.getSelectedFile());
                jList_images.clearSelection();
                jList_images.setSelectedValue(nimage.getName(), true);
                loaded = true;
            }
        } finally {
            if (!loaded) {
                nanim.removeImages(Arrays.asList(nimage.getName()));
            }
        }
    }//GEN-LAST:event_jButton_addActionPerformed

    private void jButton_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removeActionPerformed
        List<String> images = jList_images.getSelectedValuesList();
        nanim.removeImages(images);
    }//GEN-LAST:event_jButton_removeActionPerformed

    private void jButton_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loadActionPerformed
        if (!jList_images.isSelectionEmpty()) {
            JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastImageDirectory", null));
            chooser.setFileFilter(new FileNameExtensionFilter("Images", "png", "jpg"));
            if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
                nanimStudio.getPreferences().put("lastImageDirectory", chooser.getCurrentDirectory().getAbsolutePath());
                String selected = jList_images.getSelectedValue().toString();
                nanim.loadImage(selected, chooser.getSelectedFile());
                jList_images.clearSelection();
                jList_images.setSelectedValue(selected, true);
            }
        }
    }//GEN-LAST:event_jButton_loadActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_add;
    private javax.swing.JButton jButton_load;
    private javax.swing.JButton jButton_remove;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList_images;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private im.bci.nanimstudio.model.Nanim nanim;
    private im.bci.nanimstudio.NimageViewer nimageViewer1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
