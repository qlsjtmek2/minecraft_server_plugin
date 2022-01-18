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
import java.util.Collection;
import com.avaje.ebean.bean.BeanCollection;
import java.util.LinkedHashSet;
import com.avaje.ebean.common.BeanSet;
import com.avaje.ebean.bean.BeanCollectionAdd;
import java.util.Set;
import java.util.Iterator;
import com.avaje.ebean.bean.BeanCollectionLoader;

public final class BeanSetHelp<T> implements BeanCollectionHelp<T>
{
    private final BeanPropertyAssocMany<T> many;
    private final BeanDescriptor<T> targetDescriptor;
    private BeanCollectionLoader loader;
    
    public BeanSetHelp(final BeanPropertyAssocMany<T> many) {
        this.many = many;
        this.targetDescriptor = many.getTargetDescriptor();
    }
    
    public BeanSetHelp() {
        this.many = null;
        this.targetDescriptor = null;
    }
    
    public void setLoader(final BeanCollectionLoader loader) {
        this.loader = loader;
    }
    
    public Iterator<?> getIterator(final Object collection) {
        return ((Set)collection).iterator();
    }
    
    public BeanCollectionAdd getBeanCollectionAdd(final Object bc, final String mapKey) {
        if (bc instanceof BeanSet) {
            final BeanSet<?> beanSet = (BeanSet<?>)bc;
            if (beanSet.getActualSet() == null) {
                beanSet.setActualSet(new LinkedHashSet<Object>());
            }
            return beanSet;
        }
        if (bc instanceof Set) {
            return new VanillaAdd((Set)bc);
        }
        throw new RuntimeException("Unhandled type " + bc);
    }
    
    public void add(final BeanCollection<?> collection, final Object bean) {
        collection.internalAdd(bean);
    }
    
    public Object copyCollection(final Object source, final CopyContext ctx, final int maxDepth, final Object parentBean) {
        if (!(source instanceof Set)) {
            return null;
        }
        final Set<T> s = (Set<T>)(ctx.isVanillaMode() ? new LinkedHashSet<Object>() : new BeanSet<Object>());
        if (!(source instanceof BeanSet)) {
            s.addAll((Collection<? extends T>)source);
            return s;
        }
        final BeanSet<?> bc = (BeanSet<?>)source;
        if (bc.isPopulated()) {
            final Set<?> actual = bc.getActualSet();
            for (final Object sourceDetail : actual) {
                final Object destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
                s.add((T)destDetail);
            }
            return s;
        }
        if (ctx.isVanillaMode() || parentBean == null) {
            return null;
        }
        return this.createReference(parentBean, this.many.getName());
    }
    
    public Object createEmpty(final boolean vanilla) {
        return vanilla ? new LinkedHashSet<Object>() : new BeanSet<Object>();
    }
    
    public BeanCollection<T> createReference(final Object parentBean, final String propertyName) {
        return new BeanSet<T>(this.loader, parentBean, propertyName);
    }
    
    public ArrayList<InvalidValue> validate(final Object manyValue) {
        ArrayList<InvalidValue> errs = null;
        final Set<?> set = (Set<?>)manyValue;
        for (final Object detailBean : set) {
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
        final BeanSet<?> newBeanSet = (BeanSet<?>)(BeanSet)server.findSet(query, t);
        this.refresh(newBeanSet, parentBean);
    }
    
    public void refresh(final BeanCollection<?> bc, final Object parentBean) {
        final BeanSet<?> newBeanSet = (BeanSet<?>)(BeanSet)bc;
        final Set<?> current = (Set<?>)this.many.getValueUnderlying(parentBean);
        newBeanSet.setModifyListening(this.many.getModifyListenMode());
        if (current == null) {
            this.many.setValue(parentBean, newBeanSet);
        }
        else if (current instanceof BeanSet) {
            final BeanSet<?> currentBeanSet = (BeanSet<?>)(BeanSet)current;
            currentBeanSet.setActualSet(newBeanSet.getActualSet());
            currentBeanSet.setModifyListening(this.many.getModifyListenMode());
        }
        else {
            this.many.setValue(parentBean, newBeanSet);
        }
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final String name, final Object collection, final boolean explicitInclude) {
        Set<?> set;
        if (collection instanceof BeanCollection) {
            final BeanSet<?> bc = (BeanSet<?>)collection;
            if (!bc.isPopulated()) {
                if (!explicitInclude) {
                    return;
                }
                bc.size();
            }
            set = bc.getActualSet();
        }
        else {
            set = (Set<?>)collection;
        }
        int count = 0;
        ctx.beginAssocMany(name);
        for (final Object detailBean : set) {
            if (count++ > 0) {
                ctx.appendComma();
            }
            this.targetDescriptor.jsonWrite(ctx, detailBean);
        }
        ctx.endAssocMany();
    }
    
    static class VanillaAdd implements BeanCollectionAdd
    {
        private final Set set;
        
        private VanillaAdd(final Set<?> set) {
            this.set = set;
        }
        
        public void addBean(final Object bean) {
            this.set.add(bean);
        }
    }
}
