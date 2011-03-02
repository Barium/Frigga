/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.frigga.device;

import dk.itu.frigga.device.manager.DeviceManager;
import dk.itu.frigga.device.manager.impl.DeviceManagerImpl;
import org.apache.felix.dependencymanager.DependencyActivatorBase;
import org.apache.felix.dependencymanager.DependencyManager;
import org.osgi.framework.BundleContext;

/**
 *
 * @author phylock
 */
public class DeviceApiActivator extends DependencyActivatorBase {

  @Override
  public void init(BundleContext bc, DependencyManager dm) throws Exception {
    dm.add(createService()
            .setInterface(DeviceManager.class.getName(), null)
            .setImplementation(DeviceManagerImpl.getInstance()));
  }

  @Override
  public void destroy(BundleContext bc, DependencyManager dm) throws Exception {
  }
}
