// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebean.event.BeanPersistController;
import java.util.List;
import java.util.logging.Logger;

public class PersistControllerManager
{
    private static final Logger logger;
    private final List<BeanPersistController> list;
    
    public PersistControllerManager(final BootupClasses bootupClasses) {
        this.list = bootupClasses.getBeanPersistControllers();
    }
    
    public int getRegisterCount() {
        return this.list.size();
    }
    
    public void addPersistControllers(final DeployBeanDescriptor<?> deployDesc) {
        for (int i = 0; i < this.list.size(); ++i) {
            final BeanPersistController c = this.list.get(i);
            if (c.isRegisterFor(deployDesc.getBeanType())) {
                PersistControllerManager.logger.fine("BeanPersistController on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
                deployDesc.addPersistController(c);
            }
        }
    }
    
    static {
        logger = Logger.getLogger(PersistControllerManager.class.getName());
    }
}
