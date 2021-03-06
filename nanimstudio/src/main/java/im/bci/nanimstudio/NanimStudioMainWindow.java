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
package im.bci.nanimstudio;

import de.ailis.scilter.ScaleFilterFactory;
import im.bci.nanimstudio.model.NanimChangedListener;
import im.bci.nanimstudio.model.NanimStudioModel;
import im.bci.nanimstudio.tools.GenerateSpriteSheetDialog;
import im.bci.nanimstudio.tools.ImportSpriteSheetDialog;
import im.bci.nanimstudio.tools.NanimFileFilter;
import im.bci.nanimstudio.tools.OptimizeDialog;
import im.bci.nanimstudio.tools.RenameUtils;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author bob
 */
public class NanimStudioMainWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private final NanimStudioModel nanimStudio;
    private File currentFile;
    private final List<File> lastFiles = new LinkedList<File>() {
        @Override
        public boolean add(File o) {
            remove(o);
            addFirst(o);
            while (size() > 5) {
                super.removeLast();
            }
            updateRecentsMenu();
            StringBuilder sb = new StringBuilder();
            String separator = "";
            for (File file : this) {
                sb.append(separator).append(file.getAbsolutePath());
                separator = ";";
            }
            nanimStudio.getPreferences().put("recentNanims", sb.toString());
            return true;
        }
    };

    /**
     * Creates new form NanimStudioMainWindow
     */
    public NanimStudioMainWindow() {
        nanimStudio = NanimStudioModel.getInstance();
        nanimStudio.getNanim().addChangedListener(new NanimChangedListener() {
            @Override
            public void nanimChanged() {
                String title = NanimStudioMainWindow.this.getTitle();
                if (!title.endsWith("*")) {
                    NanimStudioMainWindow.this.setTitle(title + "*");
                }
            }
        });
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        initComponents();

        String[] filters = ScaleFilterFactory.getFilterNames();
        Arrays.sort(filters);
        for (final String filterName : filters) {
            jMenu_scale.add(new AbstractAction(filterName) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nanimStudio.getNanim().scale(ScaleFilterFactory.createFilter(filterName));
                }
            });
        }
        for (String recent : nanimStudio.getPreferences().get("recentNanims", "").split(";")) {
            final File file = new File(recent);
            if (file.exists()) {
                lastFiles.add(file);
            }
        }
        updateRecentsMenu();

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
        nimagesEditor = new im.bci.nanimstudio.NimagesEditor();
        nanimationEditor = new im.bci.nanimstudio.NanimationEditor();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nFramesEditor1 = new im.bci.nanimstudio.NframesEditor();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem_new = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        jMenu_openRecents = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_importGIF = new javax.swing.JMenuItem();
        jMenuItem_exportGIF = new javax.swing.JMenuItem();
        jMenuItem_exportGIF1 = new javax.swing.JMenuItem();
        jMenuItem_importJsonAndPng = new javax.swing.JMenuItem();
        jMenuItem_exportJsonAndPng = new javax.swing.JMenuItem();
        jMenuItem_importStarlingXML = new javax.swing.JMenuItem();
        jMenuItem_exportStarlingXML = new javax.swing.JMenuItem();
        jMenuItem_import_png_spritesheet = new javax.swing.JMenuItem();
        jMenuItem_export_png_spritesheet = new javax.swing.JMenuItem();
        jMenuItem_quit = new javax.swing.JMenuItem();
        jMenu_Tools = new javax.swing.JMenu();
        jMenuItem_optimize = new javax.swing.JMenuItem();
        jMenuItem_merge_with = new javax.swing.JMenuItem();
        jMenu_scale = new javax.swing.JMenu();
        jMenuItem_rename_images_nicely = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("nanimstudio");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                onClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(nimagesEditor, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(nanimationEditor, gridBagConstraints);

        jLabel1.setText("Images");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Animations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setText("Frames");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabel3, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, nanimationEditor, org.jdesktop.beansbinding.ELProperty.create("${selectedAnimation}"), nFramesEditor1, org.jdesktop.beansbinding.BeanProperty.create("animation"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(nFramesEditor1, gridBagConstraints);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        jMenuItem_new.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_new.setText("New");
        jMenuItem_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_newActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_new);

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        jMenu_openRecents.setText("Open recents");
        fileMenu.add(jMenu_openRecents);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(jSeparator1);

        jMenuItem_importGIF.setText("Import GIF...");
        jMenuItem_importGIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_importGIFActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_importGIF);

        jMenuItem_exportGIF.setText("Export GIF...");
        jMenuItem_exportGIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_exportGIFActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_exportGIF);

        jMenuItem_exportGIF1.setText("Export APNG...");
        jMenuItem_exportGIF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_exportAPNGActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_exportGIF1);

        jMenuItem_importJsonAndPng.setText("Import JSON and PNG...");
        jMenuItem_importJsonAndPng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_importJsonAndPngActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_importJsonAndPng);

        jMenuItem_exportJsonAndPng.setText("Export JSON and PNG...");
        jMenuItem_exportJsonAndPng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_exportJsonAndPngActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_exportJsonAndPng);

        jMenuItem_importStarlingXML.setText("Import Starling XML");
        fileMenu.add(jMenuItem_importStarlingXML);

        jMenuItem_exportStarlingXML.setText("Export Starling XML");
        jMenuItem_exportStarlingXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_exportStarlingXMLActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_exportStarlingXML);

        jMenuItem_import_png_spritesheet.setText("Import PNG spritesheet...");
        jMenuItem_import_png_spritesheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_import_png_spritesheetActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_import_png_spritesheet);

        jMenuItem_export_png_spritesheet.setText("Export PNG spritesheet...");
        jMenuItem_export_png_spritesheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_export_png_spritesheetActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_export_png_spritesheet);

        jMenuItem_quit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_quit.setText("Quit");
        jMenuItem_quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_quitActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_quit);

        menuBar.add(fileMenu);

        jMenu_Tools.setText("Tools");

        jMenuItem_optimize.setText("Optimize");
        jMenuItem_optimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_optimizeActionPerformed(evt);
            }
        });
        jMenu_Tools.add(jMenuItem_optimize);

        jMenuItem_merge_with.setText("Merge with...");
        jMenuItem_merge_with.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_merge_withActionPerformed(evt);
            }
        });
        jMenu_Tools.add(jMenuItem_merge_with);

        jMenu_scale.setText("Scale");
        jMenu_Tools.add(jMenu_scale);

        jMenuItem_rename_images_nicely.setText("Rename images nicely");
        jMenuItem_rename_images_nicely.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_rename_images_nicelyActionPerformed(evt);
            }
        });
        jMenu_Tools.add(jMenuItem_rename_images_nicely);

        menuBar.add(jMenu_Tools);

        setJMenuBar(menuBar);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_newActionPerformed
        nanim.clear();
        currentFile = null;
        this.setTitle("nanimstudio - new");
    }//GEN-LAST:event_jMenuItem_newActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastNanimDirectory", null));
        chooser.setFileFilter(new NanimFileFilter());
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
            nanimStudio.getPreferences().put("lastNanimDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.saveAs(chooser.getSelectedFile());
            currentFile = chooser.getSelectedFile();
            lastFiles.add(currentFile);
            this.setTitle("nanimstudio - " + currentFile.getName());
        }
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (null != currentFile) {
            nanim.saveAs(currentFile);
            lastFiles.add(currentFile);
            this.setTitle("nanimstudio - " + currentFile.getName());
        } else {
            saveAsMenuItemActionPerformed(evt);
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastNanimDirectory", null));
        chooser.setFileFilter(new NanimFileFilter());
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            nanimStudio.getPreferences().put("lastNanimDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.open(chooser.getSelectedFile());
            currentFile = chooser.getSelectedFile();
            lastFiles.add(currentFile);
            this.setTitle("nanimstudio - " + currentFile.getName());
            nanimationEditor.selectFirstAnimation();
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void jMenuItem_importGIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_importGIFActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastGifDirectory", null));
        chooser.setFileFilter(new FileNameExtensionFilter("gif animations", "gif", "gif"));
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            nanimStudio.getPreferences().put("lastGifDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.openGIF(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_jMenuItem_importGIFActionPerformed

    private void jMenuItem_exportGIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_exportGIFActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastGifDirectory", null));
        chooser.setFileFilter(new FileNameExtensionFilter("gif animations", "gif", "gif"));
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
            nanimStudio.getPreferences().put("lastGifDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.saveGIF(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_jMenuItem_exportGIFActionPerformed

    private void jMenuItem_optimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_optimizeActionPerformed
        OptimizeDialog dialog = new OptimizeDialog(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem_optimizeActionPerformed

    private void jMenuItem_export_png_spritesheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_export_png_spritesheetActionPerformed
        GenerateSpriteSheetDialog dialog = new GenerateSpriteSheetDialog(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem_export_png_spritesheetActionPerformed

    private void jMenuItem_merge_withActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_merge_withActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastNanimDirectory", null));
        chooser.setFileFilter(new NanimFileFilter());
        chooser.setMultiSelectionEnabled(true);
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            nanimStudio.getPreferences().put("lastNanimDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.mergeWith(chooser.getSelectedFiles());
        }
    }//GEN-LAST:event_jMenuItem_merge_withActionPerformed

    private void jMenuItem_import_png_spritesheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_import_png_spritesheetActionPerformed
        ImportSpriteSheetDialog dialog = new ImportSpriteSheetDialog(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem_import_png_spritesheetActionPerformed

    private void jMenuItem_exportAPNGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_exportAPNGActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastApngDirectory", null));
        chooser.setFileFilter(new FileNameExtensionFilter("png animations", "png", "png"));
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
            nanimStudio.getPreferences().put("lastApngDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.saveAPNG(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_jMenuItem_exportAPNGActionPerformed

    private void jMenuItem_quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_quitActionPerformed
        warnOrClose();
    }//GEN-LAST:event_jMenuItem_quitActionPerformed

    private void onClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onClosing
        warnOrClose();
    }//GEN-LAST:event_onClosing

    private void jMenuItem_exportJsonAndPngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_exportJsonAndPngActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastJsonAndPngDirectory", null));
        chooser.setFileFilter(new FileNameExtensionFilter("json files", "json", "json"));
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
            nanimStudio.getPreferences().put("lastJsonAndPngDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.saveJsonAndPng(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_jMenuItem_exportJsonAndPngActionPerformed

    private void jMenuItem_importJsonAndPngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_importJsonAndPngActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastJsonAndPngDirectory", null));
        chooser.setFileFilter(new FileNameExtensionFilter("json files", "json", "json"));
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            nanimStudio.getPreferences().put("lastJsonAndPngDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.openJsonAndPng(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_jMenuItem_importJsonAndPngActionPerformed

    private void jMenuItem_rename_images_nicelyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_rename_images_nicelyActionPerformed
        String baseName;
        if (null != currentFile) {
            baseName = currentFile.getName();
            int index = baseName.indexOf(".");
            baseName = baseName.substring(0, index);
        } else {
            baseName = "image";
        }
        RenameUtils.renameImagesNicely(baseName, nanim);
    }//GEN-LAST:event_jMenuItem_rename_images_nicelyActionPerformed

    private void jMenuItem_exportStarlingXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_exportStarlingXMLActionPerformed
        JFileChooser chooser = new JFileChooser(nanimStudio.getPreferences().get("lastStarlingXMLDirectory", null));
        chooser.setFileFilter(new FileNameExtensionFilter("xml files", "xml", "xml"));
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
            nanimStudio.getPreferences().put("lastStarlingXMLDirectory", chooser.getCurrentDirectory().getAbsolutePath());
            nanim.saveStarlingXML(chooser.getSelectedFile());
        }    }//GEN-LAST:event_jMenuItem_exportStarlingXMLActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem_exportGIF;
    private javax.swing.JMenuItem jMenuItem_exportGIF1;
    private javax.swing.JMenuItem jMenuItem_exportJsonAndPng;
    private javax.swing.JMenuItem jMenuItem_exportStarlingXML;
    private javax.swing.JMenuItem jMenuItem_export_png_spritesheet;
    private javax.swing.JMenuItem jMenuItem_importGIF;
    private javax.swing.JMenuItem jMenuItem_importJsonAndPng;
    private javax.swing.JMenuItem jMenuItem_importStarlingXML;
    private javax.swing.JMenuItem jMenuItem_import_png_spritesheet;
    private javax.swing.JMenuItem jMenuItem_merge_with;
    private javax.swing.JMenuItem jMenuItem_new;
    private javax.swing.JMenuItem jMenuItem_optimize;
    private javax.swing.JMenuItem jMenuItem_quit;
    private javax.swing.JMenuItem jMenuItem_rename_images_nicely;
    private javax.swing.JMenu jMenu_Tools;
    private javax.swing.JMenu jMenu_openRecents;
    private javax.swing.JMenu jMenu_scale;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private im.bci.nanimstudio.NframesEditor nFramesEditor1;
    private im.bci.nanimstudio.model.Nanim nanim;
    private im.bci.nanimstudio.NanimationEditor nanimationEditor;
    private im.bci.nanimstudio.NimagesEditor nimagesEditor;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void warnOrClose() {
        if (getTitle().endsWith("*")) {
            int confirm = JOptionPane.showOptionDialog(this,
                    "Current nanim has unsaved modification. Are You Sure to exit nanimstudio?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public void openNanimFile(File inputFile) {
        inputFile = inputFile.getAbsoluteFile();
        if (inputFile.exists() && inputFile.isFile()) {
            nanimStudio.getPreferences().put("lastNanimDirectory", inputFile.getParentFile().getAbsolutePath());
            nanim.open(inputFile);
            currentFile = inputFile;
            lastFiles.add(currentFile);
            this.setTitle("nanimstudio - " + currentFile.getName());
            nanimationEditor.selectFirstAnimation();
        }
    }

    private void updateRecentsMenu() {
        jMenu_openRecents.removeAll();
        for (final File file : lastFiles) {
            if (file.exists()) {
                jMenu_openRecents.add(new AbstractAction(file.getAbsolutePath()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nanim.open(file);
                        currentFile = file;
                        lastFiles.add(file);
                        NanimStudioMainWindow.this.setTitle("nanimstudio - " + currentFile.getName());
                        nanimationEditor.selectFirstAnimation();
                    }
                });
            }
        }
    }
}
