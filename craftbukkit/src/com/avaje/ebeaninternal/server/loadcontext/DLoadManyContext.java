// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.loadcontext;

import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import java.util.List;
import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.api.LoadManyRequest;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebeaninternal.api.LoadManyContext;

public class DLoadManyContext implements LoadManyContext, BeanCollectionLoader
{
    protected final DLoadContext parent;
    protected final String fullPath;
    private final BeanDescriptor<?> desc;
    private final BeanPropertyAssocMany<?> property;
    private final String path;
    private final int batchSize;
    private final OrmQueryProperties queryProps;
    private final DLoadWeakList<BeanCollection<?>> weakList;
    
    public DLoadManyContext(final DLoadContext parent, final BeanPropertyAssocMany<?> p, final String path, final int batchSize, final OrmQueryProperties queryProps) {
        this.parent = parent;
        this.property = p;
        this.desc = p.getBeanDescriptor();
        this.path = path;
        this.batchSize = batchSize;
        this.queryProps = queryProps;
        this.weakList = new DLoadWeakList<BeanCollection<?>>();
        if (parent.getRelativePath() == null) {
            this.fullPath = path;
        }
        else {
            this.fullPath = parent.getRelativePath() + "." + path;
        }
    }
    
    public void configureQuery(final SpiQuery<?> query) {
        query.setParentState(this.parent.getParentState());
        query.setParentNode(this.getObjectGraphNode());
        if (this.queryProps != null) {
            this.queryProps.configureManyQuery(query);
        }
        if (this.parent.isUseAutofetchManager()) {
            query.setAutofetch(true);
        }
    }
    
    public ObjectGraphNode getObjectGraphNode() {
        final int pos = this.path.lastIndexOf(46);
        if (pos == -1) {
            return this.parent.getObjectGraphNode(null);
        }
        final String parentPath = this.path.substring(0, pos);
        return this.parent.getObjectGraphNode(parentPath);
    }
    
    public String getFullPath() {
        return this.fullPath;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.parent.getPersistenceContext();
    }
    
    public int getBatchSize() {
        return this.batchSize;
    }
    
    public BeanPropertyAssocMany<?> getBeanProperty() {
        return this.property;
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String getName() {
        return this.parent.getEbeanServer().getName();
    }
    
    public void register(final BeanCollection<?> bc) {
        final int pos = this.weakList.add(bc);
        bc.setLoader(pos, this);
    }
    
    public void loadMany(final BeanCollection<?> bc, final boolean onlyIds) {
        final int position = bc.getLoaderIndex();
        final List<BeanCollection<?>> loadBatch = this.weakList.getLoadBatch(position, this.batchSize);
        final LoadManyRequest req = new LoadManyRequest(this, loadBatch, null, this.batchSize, true, onlyIds);
        this.parent.getEbeanServer().loadMany(req);
    }
    
    public void loadSecondaryQuery(final OrmQueryRequest<?> parentRequest, final int requestedBatchSize, final boolean all) {
        do {
            final List<BeanCollection<?>> batch = this.weakList.getNextBatch(requestedBatchSize);
            if (batch.size() == 0) {
                break;
            }
            final LoadManyRequest req = new LoadManyRequest(this, batch, parentRequest.getTransaction(), requestedBatchSize, false, false);
            this.parent.getEbeanServer().loadMany(req);
        } while (all);
    }
}
