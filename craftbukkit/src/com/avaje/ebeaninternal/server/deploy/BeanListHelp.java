// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.InvalidValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import com.avaje.ebean.common.BeanList;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionLoader;

public final class BeanListHelp<T> implements BeanCollectionHelp<T>
{
    private final BeanPropertyAssocMany<T> many;
    private final BeanDescriptor<T> targetDescriptor;
    private BeanCollectionLoader loader;
    
    public BeanListHelp(final BeanPropertyAssocMany<T> many) {
        this.many = many;
        this.targetDescriptor = many.getTargetDescriptor();
    }
    
    public BeanListHelp() {
        this.many = null;
        this.targetDescriptor = null;
    }
    
    public void setLoader(final BeanCollectionLoader loader) {
        this.loader = loader;
    }
    
    public void add(final BeanCollection<?> collection, final Object bean) {
        collection.internalAdd(bean);
    }
    
    public BeanCollectionAdd getBeanCollectionAdd(final Object bc, final String mapKey) {
        if (bc instanceof BeanList) {
            final BeanList<?> bl = (BeanList<?>)bc;
            if (bl.getActualList() == null) {
                bl.setActualList(new ArrayList<Object>());
            }
            return bl;
        }
        if (bc instanceof List) {
            return new VanillaAdd((List)bc);
        }
        throw new RuntimeException("Unhandled type " + bc);
    }
    
    public Iterator<?> getIterator(final Object collection) {
        return ((List)collection).iterator();
    }
    
    public Object copyCollection(final Object source, final CopyContext ctx, final int maxDepth, final Object parentBean) {
        if (!(source instanceof List)) {
            return null;
        }
        final List<T> l = (List<T>)(ctx.isVanillaMode() ? new ArrayList<Object>() : new BeanList<Object>());
        if (!(source instanceof BeanList)) {
            l.addAll((Collection<? extends T>)source);
            return l;
        }
        final BeanList<?> bl = (BeanList<?>)source;
        if (bl.isPopulated()) {
            final List<?> actualList = bl.getActualList();
            for (int i = 0; i < actualList.size(); ++i) {
                final Object sourceDetail = actualList.get(i);
                final Object destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
                l.add((T)destDetail);
            }
            return l;
        }
        if (ctx.isVanillaMode() || parentBean == null) {
            return null;
        }
        return this.createReference(parentBean, this.many.getName());
    }
    
    public Object createEmpty(final boolean vanilla) {
        return vanilla ? new ArrayList<Object>() : new BeanList<Object>();
    }
    
    public BeanCollection<T> createReference(final Object parentBean, final String propertyName) {
        return new BeanList<T>(this.loader, parentBean, propertyName);
    }
    
    public ArrayList<InvalidValue> validate(final Object manyValue) {
        ArrayList<InvalidValue> errs = null;
        final List<?> l = (List<?>)manyValue;
        for (int i = 0; i < l.size(); ++i) {
            final Object detailBean = l.get(i);
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
        final BeanList<?> newBeanList = (BeanList<?>)(BeanList)server.findList(query, t);
        this.refresh(newBeanList, parentBean);
    }
    
    public void refresh(final BeanCollection<?> bc, final Object parentBean) {
        final BeanList<?> newBeanList = (BeanList<?>)(BeanList)bc;
        final List<?> currentList = (List<?>)this.many.getValueUnderlying(parentBean);
        newBeanList.setModifyListening(this.many.getModifyListenMode());
        if (currentList == null) {
            this.many.setValue(parentBean, newBeanList);
        }
        else if (currentList instanceof BeanList) {
            final BeanList<?> currentBeanList = (BeanList<?>)(BeanList)currentList;
            currentBeanList.setActualList(newBeanList.getActualList());
            currentBeanList.setModifyListening(this.many.getModifyListenMode());
        }
        else {
            this.many.setValue(parentBean, newBeanList);
        }
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final String name, final Object collection, final boolean explicitInclude) {
        List<?> list;
        if (collection instanceof BeanCollection) {
            final BeanList<?> beanList = (BeanList<?>)collection;
            if (!beanList.isPopulated()) {
                if (!explicitInclude) {
                    return;
                }
                beanList.size();
            }
            list = beanList.getActualList();
        }
        else {
            list = (List<?>)collection;
        }
        ctx.beginAssocMany(name);
        for (int j = 0; j < list.size(); ++j) {
            if (j > 0) {
                ctx.appendComma();
            }
            final Object detailBean = list.get(j);
            this.targetDescriptor.jsonWrite(ctx, detailBean);
        }
        ctx.endAssocMany();
    }
    
    static class VanillaAdd implements BeanCollectionAdd
    {
        private final List list;
        
        private VanillaAdd(final List<?> list) {
            this.list = list;
        }
        
        public void addBean(final Object bean) {
            this.list.add(bean);
        }
    }
}
