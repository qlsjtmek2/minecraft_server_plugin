// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import javax.naming.ldap.LdapName;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import java.util.Set;
import com.avaje.ebeaninternal.server.persist.PersistExecute;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public class LdapPersistBeanRequest<T> extends PersistRequestBean<T>
{
    private final DefaultLdapPersister persister;
    
    public LdapPersistBeanRequest(final SpiEbeanServer server, final T bean, final Object parentBean, final BeanManager<T> mgr, final DefaultLdapPersister persister) {
        super(server, bean, parentBean, mgr, null, null);
        this.persister = persister;
    }
    
    public LdapPersistBeanRequest(final SpiEbeanServer server, final T bean, final Object parentBean, final BeanManager<T> mgr, final DefaultLdapPersister persister, final Set<String> updateProps, final ConcurrencyMode concurrencyMode) {
        super(server, bean, parentBean, mgr, null, null, updateProps, concurrencyMode);
        this.persister = persister;
    }
    
    public LdapName createLdapName() {
        return this.beanDescriptor.createLdapName(this.bean);
    }
    
    public int executeNow() {
        return this.persister.persist(this);
    }
    
    public int executeOrQueue() {
        return this.executeNow();
    }
    
    public void initTransIfRequired() {
    }
    
    public void commitTransIfRequired() {
    }
    
    public void rollbackTransIfRequired() {
    }
}
