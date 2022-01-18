// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.server.persist.BeanPersister;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;

public class DmlBeanPersisterFactory implements BeanPersisterFactory
{
    private final MetaFactory metaFactory;
    
    public DmlBeanPersisterFactory(final DatabasePlatform dbPlatform) {
        this.metaFactory = new MetaFactory(dbPlatform);
    }
    
    public BeanPersister create(final BeanDescriptor<?> desc) {
        final UpdateMeta updMeta = this.metaFactory.createUpdate(desc);
        final DeleteMeta delMeta = this.metaFactory.createDelete(desc);
        final InsertMeta insMeta = this.metaFactory.createInsert(desc);
        return new DmlBeanPersister(updMeta, insMeta, delMeta);
    }
}
