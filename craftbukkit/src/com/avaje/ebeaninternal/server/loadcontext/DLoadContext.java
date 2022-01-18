// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.loadcontext;

import com.avaje.ebeaninternal.api.LoadBeanContext;
import com.avaje.ebeaninternal.api.LoadManyContext;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.LoadSecondaryQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import java.util.HashMap;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.util.List;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import com.avaje.ebean.bean.PersistenceContext;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.LoadContext;

public class DLoadContext implements LoadContext
{
    private final SpiEbeanServer ebeanServer;
    private final BeanDescriptor<?> rootDescriptor;
    private final Map<String, DLoadBeanContext> beanMap;
    private final Map<String, DLoadManyContext> manyMap;
    private final DLoadBeanContext rootBeanContext;
    private final int parentState;
    private final int defaultBatchSize;
    private PersistenceContext persistenceContext;
    private String relativePath;
    private ObjectGraphOrigin origin;
    private Map<String, ObjectGraphNode> nodePathMap;
    private boolean useAutofetchManager;
    private List<OrmQueryProperties> secQuery;
    
    public DLoadContext(final SpiEbeanServer ebeanServer, final BeanDescriptor<?> rootDescriptor, final int defaultBatchSize, final int parentState, final SpiQuery<?> query) {
        this.beanMap = new HashMap<String, DLoadBeanContext>();
        this.manyMap = new HashMap<String, DLoadManyContext>();
        this.nodePathMap = new HashMap<String, ObjectGraphNode>();
        this.defaultBatchSize = defaultBatchSize;
        this.ebeanServer = ebeanServer;
        this.rootDescriptor = rootDescriptor;
        this.rootBeanContext = new DLoadBeanContext(this, rootDescriptor, null, defaultBatchSize, null);
        this.parentState = parentState;
        final ObjectGraphNode node = query.getParentNode();
        if (node != null) {
            this.origin = node.getOriginQueryPoint();
            this.relativePath = node.getPath();
        }
        this.useAutofetchManager = (query.getAutoFetchManager() != null);
    }
    
    public int getSecondaryQueriesMinBatchSize(final OrmQueryRequest<?> parentRequest, final int defaultQueryBatch) {
        if (this.secQuery == null) {
            return -1;
        }
        int maxBatch = 0;
        for (int i = 0; i < this.secQuery.size(); ++i) {
            int batchSize = this.secQuery.get(i).getQueryFetchBatch();
            if (batchSize == 0) {
                batchSize = defaultQueryBatch;
            }
            maxBatch = Math.max(maxBatch, batchSize);
        }
        return maxBatch;
    }
    
    public void executeSecondaryQueries(final OrmQueryRequest<?> parentRequest, final int defaultQueryBatch) {
        if (this.secQuery != null) {
            for (int i = 0; i < this.secQuery.size(); ++i) {
                final OrmQueryProperties properties = this.secQuery.get(i);
                int batchSize = properties.getQueryFetchBatch();
                if (batchSize == 0) {
                    batchSize = defaultQueryBatch;
                }
                final LoadSecondaryQuery load = this.getLoadSecondaryQuery(properties.getPath());
                load.loadSecondaryQuery(parentRequest, batchSize, properties.isQueryFetchAll());
            }
        }
    }
    
    private LoadSecondaryQuery getLoadSecondaryQuery(final String path) {
        LoadSecondaryQuery beanLoad = this.beanMap.get(path);
        if (beanLoad == null) {
            beanLoad = this.manyMap.get(path);
        }
        return beanLoad;
    }
    
    public void registerSecondaryQueries(final SpiQuery<?> query) {
        this.secQuery = query.removeQueryJoins();
        if (this.secQuery != null) {
            for (int i = 0; i < this.secQuery.size(); ++i) {
                final OrmQueryProperties props = this.secQuery.get(i);
                this.registerSecondaryQuery(props);
            }
        }
        final List<OrmQueryProperties> lazyQueries = query.removeLazyJoins();
        if (lazyQueries != null) {
            for (int j = 0; j < lazyQueries.size(); ++j) {
                final OrmQueryProperties lazyProps = lazyQueries.get(j);
                this.registerSecondaryQuery(lazyProps);
            }
        }
    }
    
    private void registerSecondaryQuery(final OrmQueryProperties props) {
        final String propName = props.getPath();
        final ElPropertyValue elGetValue = this.rootDescriptor.getElGetValue(propName);
        final boolean many = elGetValue.getBeanProperty().containsMany();
        this.registerSecondaryNode(many, props);
    }
    
    public ObjectGraphNode getObjectGraphNode(final String path) {
        ObjectGraphNode node = this.nodePathMap.get(path);
        if (node == null) {
            node = this.createObjectGraphNode(path);
            this.nodePathMap.put(path, node);
        }
        return node;
    }
    
    private ObjectGraphNode createObjectGraphNode(String path) {
        if (this.relativePath != null) {
            if (path == null) {
                path = this.relativePath;
            }
            else {
                path = this.relativePath + "." + path;
            }
        }
        return new ObjectGraphNode(this.origin, path);
    }
    
    public boolean isUseAutofetchManager() {
        return this.useAutofetchManager;
    }
    
    public String getRelativePath() {
        return this.relativePath;
    }
    
    protected SpiEbeanServer getEbeanServer() {
        return this.ebeanServer;
    }
    
    public int getParentState() {
        return this.parentState;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }
    
    public void setPersistenceContext(final PersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }
    
    public void register(final String path, final EntityBeanIntercept ebi) {
        this.getBeanContext(path).register(ebi);
    }
    
    public void register(final String path, final BeanCollection<?> bc) {
        this.getManyContext(path).register(bc);
    }
    
    public DLoadBeanContext getBeanContext(final String path) {
        if (path == null) {
            return this.rootBeanContext;
        }
        DLoadBeanContext beanContext = this.beanMap.get(path);
        if (beanContext == null) {
            beanContext = this.createBeanContext(path, this.defaultBatchSize, null);
            this.beanMap.put(path, beanContext);
        }
        return beanContext;
    }
    
    private void registerSecondaryNode(final boolean many, final OrmQueryProperties props) {
        final String path = props.getPath();
        final int lazyJoinBatch = props.getLazyFetchBatch();
        final int batchSize = (lazyJoinBatch > 0) ? lazyJoinBatch : this.defaultBatchSize;
        if (many) {
            final DLoadManyContext manyContext = this.createManyContext(path, batchSize, props);
            this.manyMap.put(path, manyContext);
        }
        else {
            final DLoadBeanContext beanContext = this.createBeanContext(path, batchSize, props);
            this.beanMap.put(path, beanContext);
        }
    }
    
    public DLoadManyContext getManyContext(final String path) {
        if (path == null) {
            throw new RuntimeException("path is null?");
        }
        DLoadManyContext ctx = this.manyMap.get(path);
        if (ctx == null) {
            ctx = this.createManyContext(path, this.defaultBatchSize, null);
            this.manyMap.put(path, ctx);
        }
        return ctx;
    }
    
    private DLoadManyContext createManyContext(final String path, final int batchSize, final OrmQueryProperties queryProps) {
        final BeanPropertyAssocMany<?> p = (BeanPropertyAssocMany<?>)this.getBeanProperty(this.rootDescriptor, path);
        return new DLoadManyContext(this, p, path, batchSize, queryProps);
    }
    
    private DLoadBeanContext createBeanContext(final String path, final int batchSize, final OrmQueryProperties queryProps) {
        final BeanPropertyAssoc<?> p = (BeanPropertyAssoc<?>)this.getBeanProperty(this.rootDescriptor, path);
        final BeanDescriptor<?> targetDescriptor = p.getTargetDescriptor();
        return new DLoadBeanContext(this, targetDescriptor, path, batchSize, queryProps);
    }
    
    private BeanProperty getBeanProperty(final BeanDescriptor<?> desc, final String path) {
        return desc.getBeanPropertyFromPath(path);
    }
}
