// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import java.util.Set;
import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import java.util.List;
import java.io.Serializable;

public class TransactionEvent implements Serializable
{
    private static final long serialVersionUID = 7230903304106097120L;
    private transient boolean local;
    private boolean invalidateAll;
    private TransactionEventTable eventTables;
    private transient TransactionEventBeans eventBeans;
    private transient List<BeanDelta> beanDeltas;
    private transient DeleteByIdMap deleteByIdMap;
    private transient Set<IndexInvalidate> indexInvalidations;
    private transient Set<String> pauseIndexInvalidate;
    
    public TransactionEvent() {
        this.local = true;
    }
    
    public void setInvalidateAll(final boolean isInvalidateAll) {
        this.invalidateAll = isInvalidateAll;
    }
    
    public boolean isInvalidateAll() {
        return this.invalidateAll;
    }
    
    public void pauseIndexInvalidate(final Class<?> beanType) {
        if (this.pauseIndexInvalidate == null) {
            this.pauseIndexInvalidate = new HashSet<String>();
        }
        this.pauseIndexInvalidate.add(beanType.getName());
    }
    
    public void resumeIndexInvalidate(final Class<?> beanType) {
        if (this.pauseIndexInvalidate != null) {
            this.pauseIndexInvalidate.remove(beanType.getName());
        }
    }
    
    public void addIndexInvalidate(final IndexInvalidate indexEvent) {
        if (this.pauseIndexInvalidate != null && this.pauseIndexInvalidate.contains(indexEvent.getIndexName())) {
            System.out.println("--- IGNORE Invalidate on " + indexEvent.getIndexName());
            return;
        }
        if (this.indexInvalidations == null) {
            this.indexInvalidations = new HashSet<IndexInvalidate>();
        }
        this.indexInvalidations.add(indexEvent);
    }
    
    public void addDeleteById(final BeanDescriptor<?> desc, final Object id) {
        if (this.deleteByIdMap == null) {
            this.deleteByIdMap = new DeleteByIdMap();
        }
        this.deleteByIdMap.add(desc, id);
    }
    
    public void addDeleteByIdList(final BeanDescriptor<?> desc, final List<Object> idList) {
        if (this.deleteByIdMap == null) {
            this.deleteByIdMap = new DeleteByIdMap();
        }
        this.deleteByIdMap.addList(desc, idList);
    }
    
    public DeleteByIdMap getDeleteByIdMap() {
        return this.deleteByIdMap;
    }
    
    public void addBeanDelta(final BeanDelta delta) {
        if (this.beanDeltas == null) {
            this.beanDeltas = new ArrayList<BeanDelta>();
        }
        this.beanDeltas.add(delta);
    }
    
    public List<BeanDelta> getBeanDeltas() {
        return this.beanDeltas;
    }
    
    public boolean isLocal() {
        return this.local;
    }
    
    public TransactionEventBeans getEventBeans() {
        return this.eventBeans;
    }
    
    public TransactionEventTable getEventTables() {
        return this.eventTables;
    }
    
    public Set<IndexInvalidate> getIndexInvalidations() {
        return this.indexInvalidations;
    }
    
    public void add(final String tableName, final boolean inserts, final boolean updates, final boolean deletes) {
        if (this.eventTables == null) {
            this.eventTables = new TransactionEventTable();
        }
        this.eventTables.add(tableName, inserts, updates, deletes);
    }
    
    public void add(final TransactionEventTable table) {
        if (this.eventTables == null) {
            this.eventTables = new TransactionEventTable();
        }
        this.eventTables.add(table);
    }
    
    public void add(final PersistRequestBean<?> request) {
        if (request.isNotify(this)) {
            if (this.eventBeans == null) {
                this.eventBeans = new TransactionEventBeans();
            }
            this.eventBeans.add(request);
        }
    }
    
    public void notifyCache() {
        if (this.eventBeans != null) {
            this.eventBeans.notifyCache();
        }
        if (this.deleteByIdMap != null) {
            this.deleteByIdMap.notifyCache();
        }
    }
}
