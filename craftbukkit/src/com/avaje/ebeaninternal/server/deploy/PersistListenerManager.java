// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebean.event.BeanPersistListener;
import java.util.List;
import java.util.logging.Logger;

public class PersistListenerManager
{
    private static final Logger logger;
    private final List<BeanPersistListener<?>> list;
    
    public PersistListenerManager(final BootupClasses bootupClasses) {
        this.list = bootupClasses.getBeanPersistListeners();
    }
    
    public int getRegisterCount() {
        return this.list.size();
    }
    
    public <T> void addPersistListeners(final DeployBeanDescriptor<T> deployDesc) {
        for (int i = 0; i < this.list.size(); ++i) {
            final BeanPersistListener<?> c = this.list.get(i);
            if (isRegisterFor(deployDesc.getBeanType(), c)) {
                PersistListenerManager.logger.fine("BeanPersistListener on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
                deployDesc.addPersistListener((BeanPersistListener<T>)c);
            }
        }
    }
    
    public static boolean isRegisterFor(final Class<?> beanType, final BeanPersistListener<?> c) {
        final Class<?> listenerEntity = getEntityClass(c.getClass());
        return beanType.equals(listenerEntity);
    }
    
    private static Class<?> getEntityClass(final Class<?> controller) {
        final Class<?> cls = ParamTypeUtil.findParamType(controller, BeanPersistListener.class);
        if (cls == null) {
            final String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
            throw new PersistenceException(msg);
        }
        return cls;
    }
    
    static {
        logger = Logger.getLogger(PersistListenerManager.class.getName());
    }
}
