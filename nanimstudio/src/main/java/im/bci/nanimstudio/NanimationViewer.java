/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.bci.nanimstudio;

import im.bci.nanimstudio.model.Nanimation;
import im.bci.nanimstudio.model.Nframe;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author bob
 */
public class NanimationViewer extends javax.swing.JPanel {

    private Nanimation animation;

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
            if (null != animation && !animation.getFrames().isEmpty()) {
                Nframe currentFrame = getCurrentFrame();
                BufferedImage image = currentFrame.getImage();
                if(null != image) {
                    g.translate(getWidth() / 2 - image.getWidth() / 2, getHeight()/2 - image.getHeight() / 2);
                    g.drawImage(image, 0, 0, null);
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

        jPanel_animation = new RenderingPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanel_animation.setBackground(new java.awt.Color(204, 204, 255));

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
    private javax.swing.JPanel jPanel_animation;
    // End of variables declaration//GEN-END:variables
}