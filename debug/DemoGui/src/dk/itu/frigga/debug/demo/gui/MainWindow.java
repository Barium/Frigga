/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on Mar 1, 2011, 11:35:55 AM
 */
package dk.itu.frigga.debug.demo.gui;

import dk.itu.frigga.device.Device;
import dk.itu.frigga.device.DeviceCategory;
import dk.itu.frigga.device.DeviceManager;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.log.LogService;

/**
 *
 * @author phylock
 */
public class MainWindow extends JFrame implements WindowListener {

    private volatile DeviceManager devicemanager;
    private volatile LogService log;
    private BundleContext context;

    /** Creates new form MainWindow */
    public MainWindow(BundleContext context) {
        this.context = context;
        this.addWindowListener(this);
        initComponents();
        devicemanager.toString();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lst_devices = new javax.swing.JList();
        btn_refresh = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        device_name = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lst_devices.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lst_devicesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lst_devices);

        btn_refresh.setText("Search");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dk/itu/frigga/debug/demo/gui/lightbulb_off.png"))); // NOI18N
        jToggleButton1.setRolloverEnabled(false);
        jToggleButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/dk/itu/frigga/debug/demo/gui/lightbulb_on.png"))); // NOI18N
        jToggleButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton1ItemStateChanged(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("State:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(device_name, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                    .addComponent(jLabel2)
                    .addComponent(jToggleButton1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_refresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(device_name, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton1)
                        .addGap(105, 105, 105)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        lst_devices.removeAll();

        final String category = "Tv";

        Vector<Device> devices = new Vector<Device>();
        Iterable<Device> iter = devicemanager.getDevicesByType(category);
        for (Device device : iter) {
            devices.add(device);
        }
        lst_devices.setListData(devices);
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void lst_devicesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lst_devicesValueChanged
        Object obj = lst_devices.getSelectedValue();
        if (obj instanceof Device) {
            updateCurrentDeviceInfo((Device) obj);
        }
    }//GEN-LAST:event_lst_devicesValueChanged

    private void jToggleButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton1ItemStateChanged
        int state = evt.getStateChange();

        Object selections[] = lst_devices.getSelectedValues();
        ArrayList<Device> devices = new ArrayList<Device>();

        for (Object obj : selections) {
            if (obj instanceof Device) {
                devices.add((Device) obj);
            }
        }

        int length = devices.size();
        String[] calldevices = new String[length];

        for (int i = 0; i < length; i++) {
            calldevices[i] = devices.get(i).getId().toString();
        }

        if (!devices.isEmpty()) {
            if (state == ItemEvent.SELECTED) {
                devicemanager.callFunction("on", calldevices);
            } else {
                devicemanager.callFunction("off", calldevices);
            }
        }
    }//GEN-LAST:event_jToggleButton1ItemStateChanged

    private void updateCurrentDeviceInfo(Device device) {
        device_name.setText(device.getId().toString());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_refresh;
    private javax.swing.JLabel device_name;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JList lst_devices;
    // End of variables declaration//GEN-END:variables

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        try {
            context.getBundle().stop();
            context = null;
        } catch (BundleException ex) {
            log.log(LogService.LOG_ERROR, "Unable to stop bundle", ex);
        }
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    private void start() {
        this.setVisible(true);
    }

    private void stop() {
        this.dispose();
    }
}
