// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.persist.BeanPersister;
import com.avaje.ebeaninternal.server.persist.dml.DmlBeanPersisterFactory;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;

public class BeanManagerFactory
{
    final BeanPersisterFactory peristerFactory;
    
    public BeanManagerFactory(final ServerConfig config, final DatabasePlatform dbPlatform) {
        this.peristerFactory = new DmlBeanPersisterFactory(dbPlatform);
    }
    
    public <T> BeanManager<T> create(final BeanDescriptor<T> desc) {
        final BeanPersister persister = this.peristerFactory.create(desc);
        return new BeanManager<T>(desc, persister);
    }
}
