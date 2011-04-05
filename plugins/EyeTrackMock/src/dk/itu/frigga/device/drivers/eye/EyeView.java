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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author phylock
 */
public class EyeView extends javax.swing.JFrame {

  final JFileChooser fc = new JFileChooser();

  /** Creates new form EyeView */
  public EyeView() {
    initComponents();
    fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
    fc.setAcceptAllFileFilterUsed(false);
    fc.setFileFilter(new XMLFilter());
    setBounds(getX(), getY(), 640, 480 + jMenuBar1.getHeight());
    setResizable(false);
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
    jMenuBar1 = new javax.swing.JMenuBar();
    file = new javax.swing.JMenu();
    file_open = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    javax.swing.GroupLayout viewPanel1Layout = new javax.swing.GroupLayout(viewPanel1);
    viewPanel1.setLayout(viewPanel1Layout);
    viewPanel1Layout.setHorizontalGroup(
      viewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    viewPanel1Layout.setVerticalGroup(
      viewPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 273, Short.MAX_VALUE)
    );

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

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(viewPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(viewPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void file_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_file_openActionPerformed
      int returnVal = fc.showOpenDialog(this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        try {
          File f = fc.getSelectedFile();
          //This is where a real application would open the file.
          System.out.println("Opening: " + f.getName() + ".");
          LookLoader.load(f, viewPanel1);
        } catch (IOException ex) {
          Logger.getLogger(EyeView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
          Logger.getLogger(EyeView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
          Logger.getLogger(EyeView.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else {
        System.out.println("Open command cancelled by user.");
      }
    }//GEN-LAST:event_file_openActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu file;
  private javax.swing.JMenuItem file_open;
  private javax.swing.JMenuBar jMenuBar1;
  private dk.itu.frigga.device.drivers.eye.ViewPanel viewPanel1;
  // End of variables declaration//GEN-END:variables
  private class XMLFilter extends FileFilter {

    public boolean accept(File f) {
      if (f.isDirectory()) {
        return true;
      }
      return true;
      /*String extension = FileHelper.getExtension(f);
      if (extension != null) {
        if (extension.equals("xml")) {
          return true;
        } else {
          return false;
        }
      }

      return false;*/
    }

    //The description of this filter
    public String getDescription() {
      return "look at files (*.xml)";
    }
  }
}
