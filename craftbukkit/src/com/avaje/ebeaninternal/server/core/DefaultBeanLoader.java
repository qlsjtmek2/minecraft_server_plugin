// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.LoadBeanContext;
import com.avaje.ebeaninternal.api.LoadBeanRequest;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.api.LoadManyContext;
import java.util.logging.Level;
import com.avaje.ebean.Query;
import java.util.List;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.bean.BeanCollection;
import java.util.ArrayList;
import com.avaje.ebeaninternal.api.LoadManyRequest;
import com.avaje.ebean.Transaction;
import java.util.logging.Logger;

public class DefaultBeanLoader
{
    private static final Logger logger;
    private final DebugLazyLoad debugLazyLoad;
    private final DefaultServer server;
    
    protected DefaultBeanLoader(final DefaultServer server, final DebugLazyLoad debugLazyLoad) {
        this.server = server;
        this.debugLazyLoad = debugLazyLoad;
    }
    
    private int getBatchSize(final int batchListSize, final int requestedBatchSize) {
        if (batchListSize == requestedBatchSize) {
            return batchListSize;
        }
        if (batchListSize == 1) {
            return 1;
        }
        if (requestedBatchSize <= 5) {
            return 5;
        }
        if (batchListSize <= 10 || requestedBatchSize <= 10) {
            return 10;
        }
        if (batchListSize <= 20 || requestedBatchSize <= 20) {
            return 20;
        }
        if (batchListSize <= 50) {
            return 50;
        }
        return requestedBatchSize;
    }
    
    public void refreshMany(final Object parentBean, final String propertyName) {
        this.refreshMany(parentBean, propertyName, null);
    }
    
    public void loadMany(final LoadManyRequest loadRequest) {
        final List<BeanCollection<?>> batch = loadRequest.getBatch();
        final int batchSize = this.getBatchSize(batch.size(), loadRequest.getBatchSize());
        final LoadManyContext ctx = loadRequest.getLoadContext();
        final BeanPropertyAssocMany<?> many = ctx.getBeanProperty();
        final PersistenceContext pc = ctx.getPersistenceContext();
        final ArrayList<Object> idList = new ArrayList<Object>(batchSize);
        for (int i = 0; i < batch.size(); ++i) {
            final BeanCollection<?> bc = batch.get(i);
            final Object ownerBean = bc.getOwnerBean();
            final Object id = many.getParentId(ownerBean);
            idList.add(id);
        }
        final int extraIds = batchSize - batch.size();
        if (extraIds > 0) {
            final Object firstId = idList.get(0);
            for (int j = 0; j < extraIds; ++j) {
                idList.add(firstId);
            }
        }
        final BeanDescriptor<?> desc = ctx.getBeanDescriptor();
        final String idProperty = desc.getIdBinder().getIdProperty();
        final SpiQuery<?> query = (SpiQuery<?>)(SpiQuery)this.server.createQuery(desc.getBeanType());
        query.setMode(SpiQuery.Mode.LAZYLOAD_MANY);
        query.setLazyLoadManyPath(many.getName());
        query.setPersistenceContext(pc);
        query.select(idProperty);
        query.fetch(many.getName());
        if (idList.size() == 1) {
            query.where().idEq(idList.get(0));
        }
        else {
            query.where().idIn(idList);
        }
        final String mode = loadRequest.isLazy() ? "+lazy" : "+query";
        query.setLoadDescription(mode, loadRequest.getDescription());
        ctx.configureQuery(query);
        if (loadRequest.isOnlyIds()) {
            query.fetch(many.getName(), many.getTargetIdProperty());
        }
        this.server.findList(query, loadRequest.getTransaction());
        for (int k = 0; k < batch.size(); ++k) {
            if (batch.get(k).checkEmptyLazyLoad() && DefaultBeanLoader.logger.isLoggable(Level.FINE)) {
                DefaultBeanLoader.logger.fine("BeanCollection after load was empty. Owner:" + batch.get(k).getOwnerBean());
            }
        }
    }
    
    public void loadMany(final BeanCollection<?> bc, final LoadManyContext ctx, final boolean onlyIds) {
        final Object parentBean = bc.getOwnerBean();
        final String propertyName = bc.getPropertyName();
        final ObjectGraphNode node = (ctx == null) ? null : ctx.getObjectGraphNode();
        this.loadManyInternal(parentBean, propertyName, null, false, node, onlyIds);
        if (this.server.getAdminLogging().isDebugLazyLoad()) {
            final Class<?> cls = parentBean.getClass();
            final BeanDescriptor<?> desc = this.server.getBeanDescriptor(cls);
            final BeanPropertyAssocMany<?> many = (BeanPropertyAssocMany<?>)desc.getBeanProperty(propertyName);
            final StackTraceElement cause = this.debugLazyLoad.getStackTraceElement(cls);
            String msg = "debug.lazyLoad " + many.getManyType() + " [" + desc + "][" + propertyName + "]";
            if (cause != null) {
                msg = msg + " at: " + cause;
            }
            System.err.println(msg);
        }
    }
    
    public void refreshMany(final Object parentBean, final String propertyName, final Transaction t) {
        this.loadManyInternal(parentBean, propertyName, t, true, null, false);
    }
    
    private void loadManyInternal(final Object parentBean, final String propertyName, final Transaction t, final boolean refresh, final ObjectGraphNode node, final boolean onlyIds) {
        final boolean vanilla = !(parentBean instanceof EntityBean);
        EntityBeanIntercept ebi = null;
        PersistenceContext pc = null;
        BeanCollection<?> beanCollection = null;
        ExpressionList<?> filterMany = null;
        if (!vanilla) {
            ebi = ((EntityBean)parentBean)._ebean_getIntercept();
            pc = ebi.getPersistenceContext();
        }
        final BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
        final BeanPropertyAssocMany<?> many = (BeanPropertyAssocMany<?>)parentDesc.getBeanProperty(propertyName);
        final Object currentValue = many.getValueUnderlying(parentBean);
        if (currentValue instanceof BeanCollection) {
            beanCollection = (BeanCollection<?>)currentValue;
            filterMany = beanCollection.getFilterMany();
        }
        final Object parentId = parentDesc.getId(parentBean);
        if (pc == null) {
            pc = new DefaultPersistenceContext();
            pc.put(parentId, parentBean);
        }
        final SpiQuery<?> query = (SpiQuery<?>)(SpiQuery)this.server.createQuery(parentDesc.getBeanType());
        if (refresh) {
            final Object emptyCollection = many.createEmpty(vanilla);
            many.setValue(parentBean, emptyCollection);
            if (!vanilla && ebi != null && ebi.isSharedInstance()) {
                ((BeanCollection)emptyCollection).setSharedInstance();
            }
            query.setLoadDescription("+refresh", null);
        }
        else {
            query.setLoadDescription("+lazy", null);
        }
        if (node != null) {
            query.setParentNode(node);
        }
        final String idProperty = parentDesc.getIdBinder().getIdProperty();
        query.select(idProperty);
        if (onlyIds) {
            query.fetch(many.getName(), many.getTargetIdProperty());
        }
        else {
            query.fetch(many.getName());
        }
        if (filterMany != null) {
            query.setFilterMany(many.getName(), filterMany);
        }
        query.where().idEq(parentId);
        query.setMode(SpiQuery.Mode.LAZYLOAD_MANY);
        query.setLazyLoadManyPath(many.getName());
        query.setPersistenceContext(pc);
        query.setVanillaMode(vanilla);
        if (ebi != null) {
            if (ebi.isSharedInstance()) {
                query.setSharedInstance();
            }
            else if (ebi.isReadOnly()) {
                query.setReadOnly(true);
            }
        }
        this.server.findUnique(query, t);
        if (beanCollection != null && beanCollection.checkEmptyLazyLoad() && DefaultBeanLoader.logger.isLoggable(Level.FINE)) {
            DefaultBeanLoader.logger.fine("BeanCollection after load was empty. Owner:" + beanCollection.getOwnerBean());
        }
    }
    
    public void loadBean(final LoadBeanRequest loadRequest) {
        final List<EntityBeanIntercept> batch = loadRequest.getBatch();
        if (batch.size() == 0) {
            throw new RuntimeException("Nothing in batch?");
        }
        final int batchSize = this.getBatchSize(batch.size(), loadRequest.getBatchSize());
        final LoadBeanContext ctx = loadRequest.getLoadContext();
        final BeanDescriptor<?> desc = ctx.getBeanDescriptor();
        final Class<?> beanType = desc.getBeanType();
        final EntityBeanIntercept[] ebis = batch.toArray(new EntityBeanIntercept[batch.size()]);
        final ArrayList<Object> idList = new ArrayList<Object>(batchSize);
        for (int i = 0; i < batch.size(); ++i) {
            final Object bean = batch.get(i).getOwner();
            final Object id = desc.getId(bean);
            idList.add(id);
        }
        final int extraIds = batchSize - batch.size();
        if (extraIds > 0) {
            final Object firstId = idList.get(0);
            for (int j = 0; j < extraIds; ++j) {
                idList.add(firstId);
            }
        }
        final PersistenceContext persistenceContext = ctx.getPersistenceContext();
        for (int j = 0; j < ebis.length; ++j) {
            final Object parentBean = ebis[j].getParentBean();
            if (parentBean != null) {
                final BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
                final Object parentId = parentDesc.getId(parentBean);
                persistenceContext.put(parentId, parentBean);
            }
        }
        final SpiQuery<?> query = (SpiQuery<?>)(SpiQuery)this.server.createQuery(beanType);
        query.setMode(SpiQuery.Mode.LAZYLOAD_BEAN);
        query.setPersistenceContext(persistenceContext);
        final String mode = loadRequest.isLazy() ? "+lazy" : "+query";
        query.setLoadDescription(mode, loadRequest.getDescription());
        ctx.configureQuery(query, loadRequest.getLazyLoadProperty());
        query.setUseCache(false);
        if (idList.size() == 1) {
            query.where().idEq(idList.get(0));
        }
        else {
            query.where().idIn(idList);
        }
        final List<?> list = this.server.findList(query, loadRequest.getTransaction());
        if (desc.calculateUseCache(null)) {
            for (int k = 0; k < list.size(); ++k) {
                desc.cachePutObject(list.get(k));
            }
        }
    }
    
    public void refresh(final Object bean) {
        this.refreshBeanInternal(bean, SpiQuery.Mode.REFRESH_BEAN);
    }
    
    public void loadBean(final EntityBeanIntercept ebi) {
        this.refreshBeanInternal(ebi.getOwner(), SpiQuery.Mode.LAZYLOAD_BEAN);
    }
    
    private void refreshBeanInternal(final Object bean, final SpiQuery.Mode mode) {
        final boolean vanilla = !(bean instanceof EntityBean);
        EntityBeanIntercept ebi = null;
        PersistenceContext pc = null;
        if (!vanilla) {
            ebi = ((EntityBean)bean)._ebean_getIntercept();
            pc = ebi.getPersistenceContext();
        }
        final BeanDescriptor<?> desc = this.server.getBeanDescriptor(bean.getClass());
        final Object id = desc.getId(bean);
        if (pc == null) {
            pc = new DefaultPersistenceContext();
            pc.put(id, bean);
            if (ebi != null) {
                ebi.setPersistenceContext(pc);
            }
        }
        final SpiQuery<?> query = (SpiQuery<?>)(SpiQuery)this.server.createQuery(desc.getBeanType());
        if (ebi != null) {
            if (desc.refreshFromCache(ebi, id)) {
                return;
            }
            if (desc.lazyLoadMany(ebi)) {
                return;
            }
            final Object parentBean = ebi.getParentBean();
            if (parentBean != null) {
                final BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
                final Object parentId = parentDesc.getId(parentBean);
                pc.putIfAbsent(parentId, parentBean);
            }
            query.setLazyLoadProperty(ebi.getLazyLoadProperty());
        }
        query.setUsageProfiling(false);
        query.setPersistenceContext(pc);
        query.setMode(mode);
        query.setId(id);
        query.setUseCache(false);
        query.setVanillaMode(vanilla);
        if (ebi != null) {
            if (ebi.isSharedInstance()) {
                query.setSharedInstance();
            }
            else if (ebi.isReadOnly()) {
                query.setReadOnly(true);
            }
        }
        final Object dbBean = query.findUnique();
        if (dbBean == null) {
            final String msg = "Bean not found during lazy load or refresh. id[" + id + "] type[" + desc.getBeanType() + "]";
            throw new PersistenceException(msg);
        }
        if (desc.calculateUseCache(null) && !vanilla) {
            desc.cachePutObject(dbBean);
        }
    }
    
    static {
        logger = Logger.getLogger(DefaultBeanLoader.class.getName());
    }
}
