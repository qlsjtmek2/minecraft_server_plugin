// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import java.util.List;

public class DeleteUnloadedForeignKeys
{
    private final List<BeanPropertyAssocOne<?>> propList;
    private final SpiEbeanServer server;
    private final PersistRequestBean<?> request;
    private Object beanWithForeignKeys;
    
    public DeleteUnloadedForeignKeys(final SpiEbeanServer server, final PersistRequestBean<?> request) {
        this.propList = new ArrayList<BeanPropertyAssocOne<?>>(4);
        this.server = server;
        this.request = request;
    }
    
    public boolean isEmpty() {
        return this.propList.isEmpty();
    }
    
    public void add(final BeanPropertyAssocOne<?> prop) {
        this.propList.add(prop);
    }
    
    public void queryForeignKeys() {
        final BeanDescriptor<?> descriptor = this.request.getBeanDescriptor();
        final SpiQuery<?> q = (SpiQuery<?>)(SpiQuery)this.server.createQuery(descriptor.getBeanType());
        final Object id = this.request.getBeanId();
        final StringBuilder sb = new StringBuilder(30);
        for (int i = 0; i < this.propList.size(); ++i) {
            sb.append(this.propList.get(i).getName()).append(",");
        }
        q.setPersistenceContext(new DefaultPersistenceContext());
        q.setAutofetch(false);
        q.select(sb.toString());
        q.where().idEq(id);
        final SpiTransaction t = this.request.getTransaction();
        if (t.isLogSummary()) {
            t.logInternal("-- Ebean fetching foreign key values for delete of " + descriptor.getName() + " id:" + id);
        }
        this.beanWithForeignKeys = this.server.findUnique(q, t);
    }
    
    public void deleteCascade() {
        for (int i = 0; i < this.propList.size(); ++i) {
            final BeanPropertyAssocOne<?> prop = this.propList.get(i);
            final Object detailBean = prop.getValue(this.beanWithForeignKeys);
            if (detailBean != null && prop.hasId(detailBean)) {
                this.server.delete(detailBean, this.request.getTransaction());
            }
        }
    }
}
