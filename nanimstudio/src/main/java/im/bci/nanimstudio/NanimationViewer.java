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

import de.ailis.scilter.ScaleFilter;
import de.ailis.scilter.ScaleFilterFactory;
import im.bci.nanimstudio.model.Nanimation;
import im.bci.nanimstudio.model.Nframe;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author bob
 */
public class NanimationViewer extends javax.swing.JPanel {

    private Nanimation animation;
    private ScaleFilter scaleFilter = ScaleFilterFactory.createFilter("normal");
    private BufferedImage scaledImage;
    private Zoomer zoomer = new FixedScaleZoomer(1.0);

    private interface Zoomer {

        void zoom(Graphics g, BufferedImage image);
    }

    public class FixedScaleZoomer implements Zoomer {

        final double s;

        FixedScaleZoomer(double s) {
            this.s = s;
        }

        @Override
        public void zoom(Graphics g, BufferedImage image) {
            Graphics2D g2 = ((Graphics2D) g);
            g2.translate(getWidth() / 2.0 - image.getWidth() * s / 2.0, getHeight() / 2.0 - image.getHeight() * s / 2.0);
            g2.scale(s, s);
        }
    }

    public Nanimation getAnimation() {
        return animation;
    }

    public void setAnimation(Nanimation animation) {
        this.animation = animation;
    }

    private class RenderingPanel extends JPanel implements Runnable {

        int c = 1;

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, jCheckBoxMenuItem_pixelatedZoom.isSelected() ? RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR : RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            if (null != animation && !animation.getFrames().isEmpty()) {
                Nframe currentFrame = getCurrentFrame();
                BufferedImage image = currentFrame.getImage();
                if (null != image) {
                    if (image != scaledImage) {
                        scaledImage = scaleFilter.scale(image);
                    }
                    zoomer.zoom(g, scaledImage);
                    g.drawImage(scaledImage, 0, 0, null);
                }
            }
        }

        @Override
        public void run() {
            try {
                for (;;) {
                    repaint();
                    Thread.sleep(2);
                }
            } catch (InterruptedException ex) {
            }
        }

        private Nframe getCurrentFrame() {
            int animationDuration = animation.getTotalDuration();
            long currentTime = animationDuration != 0 ? System.currentTimeMillis() % animationDuration : 0;
            for (Nframe frame : animation.getFrames()) {
                currentTime -= frame.getDuration();
                if (currentTime <= 0) {
                    return frame;
                }
            }
            return animation.getFrames().get(0);
        }
    }

    /**
     * Creates new form NanimationViewer
     */
    public NanimationViewer() {
        initComponents();
        Thread animator = new Thread((RenderingPanel) jPanel_animation);
        animator.start();
        String[] filters = ScaleFilterFactory.getFilterNames();
        Arrays.sort(filters);
        for (final String filterName : filters) {
            jMenu_scale.add(new AbstractAction(filterName) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NanimationViewer.this.scaleFilter = ScaleFilterFactory.createFilter(filterName);
                    scaledImage = null;
                }
            });
        }

        ButtonGroup zoomButtonGroup = new ButtonGroup();
        for (final double s : Arrays.asList(25.0, 50.0, 100.0, 150.0, 200.0, 300.0, 500.0, 1000.0, 2000.0, 5000.0)) {
            final JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem();
            zoomButtonGroup.add(menuItem);
            menuItem.setAction(new AbstractAction(s + "%") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    zoomer = new FixedScaleZoomer(s / 100.0);
                }
            });
            if (s == 100.0) {
                menuItem.setSelected(true);
            }
            jMenu_zoom.add(menuItem);
        }
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

        jPopupMenu_animation = new javax.swing.JPopupMenu();
        jMenu_scale = new javax.swing.JMenu();
        jMenu_zoom = new javax.swing.JMenu();
        jCheckBoxMenuItem_pixelatedZoom = new javax.swing.JCheckBoxMenuItem();
        jPanel_animation = new RenderingPanel();

        jPopupMenu_animation.setComponentPopupMenu(jPopupMenu_animation);

        jMenu_scale.setText("Scale");
        jPopupMenu_animation.add(jMenu_scale);

        jMenu_zoom.setText("Zoom");
        jPopupMenu_animation.add(jMenu_zoom);

        jCheckBoxMenuItem_pixelatedZoom.setSelected(true);
        jCheckBoxMenuItem_pixelatedZoom.setText("Pixelated zoom");
        jPopupMenu_animation.add(jCheckBoxMenuItem_pixelatedZoom);

        setLayout(new java.awt.GridBagLayout());

        jPanel_animation.setBackground(new java.awt.Color(204, 204, 255));
        jPanel_animation.setComponentPopupMenu(jPopupMenu_animation);

        javax.swing.GroupLayout jPanel_animationLayout = new javax.swing.GroupLayout(jPanel_animation);
        jPanel_animation.setLayout(jPanel_animationLayout);
        jPanel_animationLayout.setHorizontalGroup(
            jPanel_animationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );
        jPanel_animationLayout.setVerticalGroup(
            jPanel_animationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel_animation, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem_pixelatedZoom;
    private javax.swing.JMenu jMenu_scale;
    private javax.swing.JMenu jMenu_zoom;
    private javax.swing.JPanel jPanel_animation;
    private javax.swing.JPopupMenu jPopupMenu_animation;
    // End of variables declaration//GEN-END:variables
}
