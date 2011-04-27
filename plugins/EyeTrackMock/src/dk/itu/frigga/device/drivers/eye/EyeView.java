/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EyeView.java
 *
 * Created on Apr 2, 2011, 2:34:22 PM
 */
package dk.itu.frigga.device.drivers.eye;

import dk.itu.frigga.utility.FileExtensionFilter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author phylock
 */
public class EyeView extends javax.swing.JFrame {

  private static final String FORMAT_TITLE = "Frigga Eye Tracking (Mock): %s";
  final JFileChooser fc = new JFileChooser();
  private final List<EyeChange> listeners;
  private final EyeTrackListener panellistener;

  /** Creates new form EyeView */
  public EyeView() {
    initComponents();
    fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
    fc.setAcceptAllFileFilterUsed(false);
    fc.setFileFilter(new FileExtensionFilter(new String[]{"xml"}, "lookat file(*.xml)"));

    panellistener = new EyeTrackListener(jLabel1, viewPanel1);

    viewPanel1.addMouseMotionListener(panellistener);
    pack();
    setTitle(String.format(FORMAT_TITLE, "None"));
    listeners = Collections.synchronizedList(new LinkedList<EyeChange>());
    viewPanel1.setListener(panellistener);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    viewPanel1 = new dk.itu.frigga.device.drivers.eye.ViewPanel();
    jPanel2 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jMenuBar1 = new javax.swing.JMenuBar();
    file = new javax.swing.JMenu();
    file_open = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setMinimumSize(new java.awt.Dimension(640, 480));
    setResizable(false);

    viewPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    viewPanel1.setMinimumSize(new java.awt.Dimension(640, 480));

    javax.swing.GroupLayout viewPanel1Layout = new javax.swing.GroupLayout(viewPanel1);
    viewPanel1.setLayout(viewPanel1Layout);
    viewPanel1Layout.setHorizontalGroup(
      viewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 638, Short.MAX_VALUE)
    );
    viewPanel1Layout.setVerticalGroup(
      viewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 485, Short.MAX_VALUE)
    );

    getContentPane().add(viewPanel1, java.awt.BorderLayout.CENTER);

    jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    jPanel2.setPreferredSize(new java.awt.Dimension(412, 25));

    jLabel1.setText("0");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
        .addContainerGap(629, Short.MAX_VALUE)
        .addComponent(jLabel1))
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addComponent(jLabel1)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

    file.setText("File");

    file_open.setText("Open");
    file_open.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        file_openActionPerformed(evt);
      }
    });
    file.add(file_open);

    jMenuBar1.add(file);

    setJMenuBar(jMenuBar1);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void file_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_file_openActionPerformed
      int returnVal = fc.showOpenDialog(this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        try {
          File f = fc.getSelectedFile();
          //This is where a real application would open the file.
          setTitle(String.format(FORMAT_TITLE, f.getName()));
          LookLoader.load(f, viewPanel1);
        } catch (IOException ex) {
          Logger.getLogger(EyeView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
          Logger.getLogger(EyeView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
          Logger.getLogger(EyeView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }//GEN-LAST:event_file_openActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu file;
  private javax.swing.JMenuItem file_open;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JPanel jPanel2;
  private dk.itu.frigga.device.drivers.eye.ViewPanel viewPanel1;
  // End of variables declaration//GEN-END:variables

  public void addListener(EyeChange listener) {
    listeners.add(listener);
  }

  public void removeListener(EyeChange listener) {
    listeners.remove(listener);
  }

  private class EyeTrackListener implements MouseMotionListener, EyeChange {

    private static final String FORMAT_GLOBAL = "Global (%d,%d)";
    private static final String FORMAT_LOCAL = "Local (%d,%d) " + FORMAT_GLOBAL;
    private JLabel label;
    private ViewPanel viewpanel;

    public EyeTrackListener(JLabel label, ViewPanel viewpanel) {
      this.viewpanel = viewpanel;
      this.label = label;
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
      Device d = viewpanel.getOver();
      if (d != null) {
        Point p = d.toLocal(e.getX(), e.getY());
        if (d.equals(viewpanel.getCurrent())) {
          for (EyeChange c : listeners) {
            c.localChanged(p);
          }
        }
        label.setText(String.format(FORMAT_LOCAL, p.getX(), p.getY(), e.getX(), e.getY()));
      } else {
        label.setText(String.format(FORMAT_GLOBAL, e.getX(), e.getY()));
      }

    }

    public void selectionChanged(String selection) {
      for (EyeChange c : listeners) {
        c.selectionChanged(selection);
      }
    }

    public void localChanged(Point p) {
    }
  }
}
