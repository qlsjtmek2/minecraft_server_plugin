// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.loadcontext;

import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import java.util.List;
import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.api.LoadBeanRequest;
import com.avaje.ebeaninternal.api.LoadContext;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.bean.BeanLoader;
import com.avaje.ebeaninternal.api.LoadBeanContext;

public class DLoadBeanContext implements LoadBeanContext, BeanLoader
{
    protected final DLoadContext parent;
    protected final BeanDescriptor<?> desc;
    protected final String path;
    protected final String fullPath;
    private final DLoadWeakList<EntityBeanIntercept> weakList;
    private final OrmQueryProperties queryProps;
    private int batchSize;
    
    public DLoadBeanContext(final DLoadContext parent, final BeanDescriptor<?> desc, final String path, final int batchSize, final OrmQueryProperties queryProps) {
        this.parent = parent;
        this.desc = desc;
        this.path = path;
        this.batchSize = batchSize;
        this.queryProps = queryProps;
        this.weakList = new DLoadWeakList<EntityBeanIntercept>();
        if (parent.getRelativePath() == null) {
            this.fullPath = path;
        }
        else {
            this.fullPath = parent.getRelativePath() + "." + path;
        }
    }
    
    public void configureQuery(final SpiQuery<?> query, final String lazyLoadProperty) {
        query.setParentState(this.parent.getParentState());
        query.setParentNode(this.getObjectGraphNode());
        query.setLazyLoadProperty(lazyLoadProperty);
        if (this.queryProps != null) {
            this.queryProps.configureBeanQuery(query);
        }
        if (this.parent.isUseAutofetchManager()) {
            query.setAutofetch(true);
        }
    }
    
    public String getFullPath() {
        return this.fullPath;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.parent.getPersistenceContext();
    }
    
    public OrmQueryProperties getQueryProps() {
        return this.queryProps;
    }
    
    public ObjectGraphNode getObjectGraphNode() {
        return this.parent.getObjectGraphNode(this.path);
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String getName() {
        return this.parent.getEbeanServer().getName();
    }
    
    public int getBatchSize() {
        return this.batchSize;
    }
    
    public void setBatchSize(final int batchSize) {
        this.batchSize = batchSize;
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }
    
    public LoadContext getGraphContext() {
        return this.parent;
    }
    
    public void register(final EntityBeanIntercept ebi) {
        final int pos = this.weakList.add(ebi);
        ebi.setBeanLoader(pos, this);
    }
    
    public void loadBean(final EntityBeanIntercept ebi) {
        if (this.desc.lazyLoadMany(ebi)) {
            return;
        }
        final int position = ebi.getBeanLoaderIndex();
        final List<EntityBeanIntercept> batch = this.weakList.getLoadBatch(position, this.batchSize);
        final LoadBeanRequest req = new LoadBeanRequest(this, batch, null, this.batchSize, true, ebi.getLazyLoadProperty());
        this.parent.getEbeanServer().loadBean(req);
    }
    
    public void loadSecondaryQuery(final OrmQueryRequest<?> parentRequest, final int requestedBatchSize, final boolean all) {
        do {
            final List<EntityBeanIntercept> batch = this.weakList.getNextBatch(requestedBatchSize);
            if (batch.size() == 0) {
                break;
            }
            final LoadBeanRequest req = new LoadBeanRequest(this, batch, parentRequest.getTransaction(), requestedBatchSize, false, null);
            this.parent.getEbeanServer().loadBean(req);
        } while (all);
    }
}
