// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebean.event.BeanQueryAdapter;
import java.util.List;
import java.util.logging.Logger;

public class BeanQueryAdapterManager
{
    private static final Logger logger;
    private final List<BeanQueryAdapter> list;
    
    public BeanQueryAdapterManager(final BootupClasses bootupClasses) {
        this.list = bootupClasses.getBeanQueryAdapters();
    }
    
    public int getRegisterCount() {
        return this.list.size();
    }
    
    public void addQueryAdapter(final DeployBeanDescriptor<?> deployDesc) {
        for (int i = 0; i < this.list.size(); ++i) {
            final BeanQueryAdapter c = this.list.get(i);
            if (c.isRegisterFor(deployDesc.getBeanType())) {
                BeanQueryAdapterManager.logger.fine("BeanQueryAdapter on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
                deployDesc.addQueryAdapter(c);
            }
        }
    }
    
    static {
        logger = Logger.getLogger(BeanQueryAdapterManager.class.getName());
    }
}
