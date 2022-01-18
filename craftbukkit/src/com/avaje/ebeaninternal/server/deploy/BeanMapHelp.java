// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.InvalidValue;
import java.util.ArrayList;
import com.avaje.ebean.bean.BeanCollection;
import java.util.LinkedHashMap;
import com.avaje.ebean.common.BeanMap;
import com.avaje.ebean.bean.BeanCollectionAdd;
import java.util.Map;
import java.util.Iterator;
import com.avaje.ebean.bean.BeanCollectionLoader;

public final class BeanMapHelp<T> implements BeanCollectionHelp<T>
{
    private final BeanPropertyAssocMany<T> many;
    private final BeanDescriptor<T> targetDescriptor;
    private final BeanProperty beanProperty;
    private BeanCollectionLoader loader;
    
    public BeanMapHelp(final BeanDescriptor<T> targetDescriptor, final String mapKey) {
        this(null, targetDescriptor, mapKey);
    }
    
    public BeanMapHelp(final BeanPropertyAssocMany<T> many) {
        this((BeanPropertyAssocMany<Object>)many, many.getTargetDescriptor(), many.getMapKey());
    }
    
    private BeanMapHelp(final BeanPropertyAssocMany<T> many, final BeanDescriptor<T> targetDescriptor, final String mapKey) {
        this.many = many;
        this.targetDescriptor = targetDescriptor;
        this.beanProperty = targetDescriptor.getBeanProperty(mapKey);
    }
    
    public Iterator<?> getIterator(final Object collection) {
        return ((Map)collection).values().iterator();
    }
    
    public void setLoader(final BeanCollectionLoader loader) {
        this.loader = loader;
    }
    
    public BeanCollectionAdd getBeanCollectionAdd(final Object bc, String mapKey) {
        if (mapKey == null) {
            mapKey = this.many.getMapKey();
        }
        final BeanProperty beanProp = this.targetDescriptor.getBeanProperty(mapKey);
        if (bc instanceof BeanMap) {
            final BeanMap<Object, Object> bm = (BeanMap<Object, Object>)bc;
            Map<Object, Object> actualMap = bm.getActualMap();
            if (actualMap == null) {
                actualMap = new LinkedHashMap<Object, Object>();
                bm.setActualMap(actualMap);
            }
            return new Adder(beanProp, actualMap);
        }
        if (bc instanceof Map) {
            return new Adder(beanProp, (Map<Object, Object>)bc);
        }
        throw new RuntimeException("Unhandled type " + bc);
    }
    
    public Object copyCollection(final Object source, final CopyContext ctx, final int maxDepth, final Object parentBean) {
        if (!(source instanceof Map)) {
            return null;
        }
        final Map<Object, Object> m = (Map<Object, Object>)(ctx.isVanillaMode() ? new LinkedHashMap<Object, Object>() : new BeanMap<Object, Object>());
        final Map<?, ?> sourceMap = (Map<?, ?>)source;
        if (!(source instanceof BeanMap)) {
            for (final Map.Entry<?, ?> entry : sourceMap.entrySet()) {
                m.put(entry.getKey(), entry.getValue());
            }
            return m;
        }
        final BeanMap<?, ?> bc = (BeanMap<?, ?>)source;
        if (bc.isPopulated()) {
            final Map<?, ?> actual = bc.getActualMap();
            for (final Map.Entry<?, ?> entry2 : actual.entrySet()) {
                final Object sourceDetail = entry2.getValue();
                final Object destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
                m.put(entry2.getKey(), destDetail);
            }
            return m;
        }
        if (ctx.isVanillaMode() || parentBean == null) {
            return null;
        }
        return this.createReference(parentBean, this.many.getName());
    }
    
    public Object createEmpty(final boolean vanilla) {
        return vanilla ? new LinkedHashMap<Object, Object>() : new BeanMap<Object, Object>();
    }
    
    public void add(final BeanCollection<?> collection, final Object bean) {
        final Object keyValue = this.beanProperty.getValueIntercept(bean);
        final Map<Object, Object> map = (Map<Object, Object>)(Map)collection;
        map.put(keyValue, bean);
    }
    
    public BeanCollection<T> createReference(final Object parentBean, final String propertyName) {
        return (BeanCollection<T>)new BeanMap(this.loader, parentBean, propertyName);
    }
    
    public ArrayList<InvalidValue> validate(final Object manyValue) {
        ArrayList<InvalidValue> errs = null;
        final Map<?, ?> m = (Map<?, ?>)manyValue;
        for (final Object detailBean : m.values()) {
            final InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
            if (invalid != null) {
                if (errs == null) {
                    errs = new ArrayList<InvalidValue>();
                }
                errs.add(invalid);
            }
        }
        return errs;
    }
    
    public void refresh(final EbeanServer server, final Query<?> query, final Transaction t, final Object parentBean) {
        final BeanMap<?, ?> newBeanMap = (BeanMap<?, ?>)(BeanMap)server.findMap(query, t);
        this.refresh(newBeanMap, parentBean);
    }
    
    public void refresh(final BeanCollection<?> bc, final Object parentBean) {
        final BeanMap<?, ?> newBeanMap = (BeanMap<?, ?>)(BeanMap)bc;
        final Map<?, ?> current = (Map<?, ?>)this.many.getValueUnderlying(parentBean);
        newBeanMap.setModifyListening(this.many.getModifyListenMode());
        if (current == null) {
            this.many.setValue(parentBean, newBeanMap);
        }
        else if (current instanceof BeanMap) {
            final BeanMap<?, ?> currentBeanMap = (BeanMap<?, ?>)(BeanMap)current;
            currentBeanMap.setActualMap(newBeanMap.getActualMap());
            currentBeanMap.setModifyListening(this.many.getModifyListenMode());
        }
        else {
            this.many.setValue(parentBean, newBeanMap);
        }
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final String name, final Object collection, final boolean explicitInclude) {
        Map<?, ?> map;
        if (collection instanceof BeanCollection) {
            final BeanMap<?, ?> bc = (BeanMap<?, ?>)collection;
            if (!bc.isPopulated()) {
                if (!explicitInclude) {
                    return;
                }
                bc.size();
            }
            map = bc.getActualMap();
        }
        else {
            map = (Map<?, ?>)collection;
        }
        int count = 0;
        ctx.beginAssocMany(name);
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            if (count++ > 0) {
                ctx.appendComma();
            }
            final Object detailBean = entry.getValue();
            this.targetDescriptor.jsonWrite(ctx, detailBean);
        }
        ctx.endAssocMany();
    }
    
    static class Adder implements BeanCollectionAdd
    {
        private final BeanProperty beanProperty;
        private final Map<Object, Object> map;
        
        Adder(final BeanProperty beanProperty, final Map<Object, Object> map) {
            this.beanProperty = beanProperty;
            this.map = map;
        }
        
        public void addBean(final Object bean) {
            final Object keyValue = this.beanProperty.getValue(bean);
            this.map.put(keyValue, bean);
        }
    }
}
