// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.util.BeanCollectionFactory;
import com.avaje.ebeaninternal.server.util.BeanCollectionParams;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.core.RelationalQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Map;
import java.util.Collection;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.Query;

public final class BeanCollectionWrapper
{
    private final boolean isMap;
    private final Query.Type queryType;
    private final String mapKey;
    private final BeanCollection<?> beanCollection;
    private final Collection<Object> collection;
    private final Map<Object, Object> map;
    private final BeanDescriptor<?> desc;
    private int rowCount;
    
    public BeanCollectionWrapper(final RelationalQueryRequest request) {
        this.desc = null;
        this.queryType = request.getQueryType();
        this.mapKey = request.getQuery().getMapKey();
        this.isMap = Query.Type.MAP.equals(this.queryType);
        this.beanCollection = this.createBeanCollection(this.queryType);
        this.collection = this.getCollection(this.isMap);
        this.map = this.getMap(this.isMap);
    }
    
    public BeanCollectionWrapper(final OrmQueryRequest<?> request) {
        this.desc = request.getBeanDescriptor();
        this.queryType = request.getQueryType();
        this.mapKey = request.getQuery().getMapKey();
        this.isMap = Query.Type.MAP.equals(this.queryType);
        this.beanCollection = this.createBeanCollection(this.queryType);
        this.collection = this.getCollection(this.isMap);
        this.map = this.getMap(this.isMap);
    }
    
    public BeanCollectionWrapper(final BeanPropertyAssocMany<?> manyProp) {
        this.queryType = manyProp.getManyType().getQueryType();
        this.mapKey = manyProp.getMapKey();
        this.desc = manyProp.getTargetDescriptor();
        this.isMap = Query.Type.MAP.equals(this.queryType);
        this.beanCollection = this.createBeanCollection(this.queryType);
        this.collection = this.getCollection(this.isMap);
        this.map = this.getMap(this.isMap);
    }
    
    private Map<Object, Object> getMap(final boolean isMap) {
        return (Map<Object, Object>)(isMap ? ((Map)this.beanCollection) : null);
    }
    
    private Collection<Object> getCollection(final boolean isMap) {
        return (Collection<Object>)(isMap ? null : ((Collection)this.beanCollection));
    }
    
    public BeanCollection<?> getBeanCollection() {
        return this.beanCollection;
    }
    
    private BeanCollection<?> createBeanCollection(final Query.Type manyType) {
        final BeanCollectionParams p = new BeanCollectionParams(manyType);
        return BeanCollectionFactory.create(p);
    }
    
    public boolean isMap() {
        return this.isMap;
    }
    
    public int size() {
        return this.rowCount;
    }
    
    public void add(final Object bean) {
        this.add(bean, this.beanCollection);
    }
    
    public void add(final Object bean, final Object collection) {
        if (bean == null) {
            return;
        }
        ++this.rowCount;
        if (this.isMap) {
            Object keyValue = null;
            if (this.mapKey != null) {
                keyValue = this.desc.getValue(bean, this.mapKey);
            }
            else {
                keyValue = this.desc.getId(bean);
            }
            final Map mapColl = (Map)collection;
            mapColl.put(keyValue, bean);
        }
        else {
            ((Collection)collection).add(bean);
        }
    }
    
    public void addToCollection(final Object bean) {
        this.collection.add(bean);
    }
    
    public void addToMap(final Object bean, final Object key) {
        this.map.put(key, bean);
    }
}
