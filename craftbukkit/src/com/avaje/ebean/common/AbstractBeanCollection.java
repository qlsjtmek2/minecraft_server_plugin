// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.persistence.PersistenceException;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.EntityBean;
import java.util.concurrent.Future;
import com.avaje.ebean.bean.BeanCollectionTouched;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.BeanCollection;

public abstract class AbstractBeanCollection<E> implements BeanCollection<E>
{
    private static final long serialVersionUID = 3365725236140187588L;
    protected int state;
    protected transient BeanCollectionLoader loader;
    protected transient ExpressionList<?> filterMany;
    protected int loaderIndex;
    protected String ebeanServerName;
    protected transient BeanCollectionTouched beanCollectionTouched;
    protected transient Future<Integer> fetchFuture;
    protected final Object ownerBean;
    protected final String propertyName;
    protected boolean finishedFetch;
    protected boolean hasMoreRows;
    protected ModifyHolder<E> modifyHolder;
    protected ModifyListenMode modifyListenMode;
    protected boolean modifyAddListening;
    protected boolean modifyRemoveListening;
    protected boolean modifyListening;
    
    public AbstractBeanCollection() {
        this.finishedFetch = true;
        this.ownerBean = null;
        this.propertyName = null;
    }
    
    public AbstractBeanCollection(final BeanCollectionLoader loader, final Object ownerBean, final String propertyName) {
        this.finishedFetch = true;
        this.loader = loader;
        this.ebeanServerName = loader.getName();
        this.ownerBean = ownerBean;
        this.propertyName = propertyName;
        if (ownerBean instanceof EntityBean) {
            final EntityBeanIntercept ebi = ((EntityBean)ownerBean)._ebean_getIntercept();
            final int parentState = ebi.getState();
            if (parentState != 0) {
                this.state = parentState;
            }
        }
    }
    
    public Object getOwnerBean() {
        return this.ownerBean;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public int getLoaderIndex() {
        return this.loaderIndex;
    }
    
    public ExpressionList<?> getFilterMany() {
        return this.filterMany;
    }
    
    public void setFilterMany(final ExpressionList<?> filterMany) {
        this.filterMany = filterMany;
    }
    
    protected void lazyLoadCollection(final boolean onlyIds) {
        if (this.loader == null) {
            this.loader = (BeanCollectionLoader)Ebean.getServer(this.ebeanServerName);
        }
        if (this.loader == null) {
            final String msg = "Lazy loading but LazyLoadEbeanServer is null? The LazyLoadEbeanServer needs to be set after deserialization to support lazy loading.";
            throw new PersistenceException(msg);
        }
        this.loader.loadMany(this, onlyIds);
        this.checkEmptyLazyLoad();
    }
    
    protected void touched() {
        if (this.beanCollectionTouched != null) {
            this.beanCollectionTouched.notifyTouched(this);
            this.beanCollectionTouched = null;
        }
    }
    
    public void setBeanCollectionTouched(final BeanCollectionTouched notify) {
        this.beanCollectionTouched = notify;
    }
    
    public void setLoader(final int beanLoaderIndex, final BeanCollectionLoader loader) {
        this.loaderIndex = beanLoaderIndex;
        this.loader = loader;
        this.ebeanServerName = loader.getName();
    }
    
    public boolean isSharedInstance() {
        return this.state == 3;
    }
    
    public void setSharedInstance() {
        this.state = 3;
    }
    
    public boolean isReadOnly() {
        return this.state >= 2;
    }
    
    public void setReadOnly(final boolean readOnly) {
        if (this.state == 3) {
            if (!readOnly) {
                final String msg = "This collection is a sharedInstance and must always be read only";
                throw new IllegalStateException(msg);
            }
        }
        else {
            this.state = (readOnly ? 2 : 0);
        }
    }
    
    public boolean hasMoreRows() {
        return this.hasMoreRows;
    }
    
    public void setHasMoreRows(final boolean hasMoreRows) {
        this.hasMoreRows = hasMoreRows;
    }
    
    public boolean isFinishedFetch() {
        return this.finishedFetch;
    }
    
    public void setFinishedFetch(final boolean finishedFetch) {
        this.finishedFetch = finishedFetch;
    }
    
    public void setBackgroundFetch(final Future<Integer> fetchFuture) {
        this.fetchFuture = fetchFuture;
    }
    
    public void backgroundFetchWait(final long wait, final TimeUnit timeUnit) {
        if (this.fetchFuture != null) {
            try {
                this.fetchFuture.get(wait, timeUnit);
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
    }
    
    public void backgroundFetchWait() {
        if (this.fetchFuture != null) {
            try {
                this.fetchFuture.get();
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
    }
    
    protected void checkReadOnly() {
        if (this.state >= 2) {
            final String msg = "This collection is in ReadOnly mode";
            throw new IllegalStateException(msg);
        }
    }
    
    public void setModifyListening(final ModifyListenMode mode) {
        this.modifyListenMode = mode;
        this.modifyAddListening = ModifyListenMode.ALL.equals(mode);
        this.modifyRemoveListening = (this.modifyAddListening || ModifyListenMode.REMOVALS.equals(mode));
        this.modifyListening = (this.modifyRemoveListening || this.modifyAddListening);
        if (this.modifyListening) {
            this.modifyHolder = null;
        }
    }
    
    public ModifyListenMode getModifyListenMode() {
        return this.modifyListenMode;
    }
    
    protected ModifyHolder<E> getModifyHolder() {
        if (this.modifyHolder == null) {
            this.modifyHolder = new ModifyHolder<E>();
        }
        return this.modifyHolder;
    }
    
    public void modifyAddition(final E bean) {
        if (this.modifyAddListening) {
            this.getModifyHolder().modifyAddition(bean);
        }
    }
    
    public void modifyRemoval(final Object bean) {
        if (this.modifyRemoveListening) {
            this.getModifyHolder().modifyRemoval(bean);
        }
    }
    
    public void modifyReset() {
        if (this.modifyHolder != null) {
            this.modifyHolder.reset();
        }
    }
    
    public Set<E> getModifyAdditions() {
        if (this.modifyHolder == null) {
            return null;
        }
        return this.modifyHolder.getModifyAdditions();
    }
    
    public Set<E> getModifyRemovals() {
        if (this.modifyHolder == null) {
            return null;
        }
        return this.modifyHolder.getModifyRemovals();
    }
}
