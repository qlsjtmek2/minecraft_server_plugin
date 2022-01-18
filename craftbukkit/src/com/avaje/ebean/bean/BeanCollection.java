// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

import java.util.Set;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import com.avaje.ebean.ExpressionList;
import java.io.Serializable;

public interface BeanCollection<E> extends Serializable
{
    public static final int DEFAULT = 0;
    public static final int READONLY = 2;
    public static final int SHARED = 3;
    
    Object getOwnerBean();
    
    String getPropertyName();
    
    int getLoaderIndex();
    
    boolean checkEmptyLazyLoad();
    
    ExpressionList<?> getFilterMany();
    
    void setFilterMany(final ExpressionList<?> p0);
    
    void setBackgroundFetch(final Future<Integer> p0);
    
    void backgroundFetchWait(final long p0, final TimeUnit p1);
    
    void backgroundFetchWait();
    
    boolean isSharedInstance();
    
    void setSharedInstance();
    
    void setBeanCollectionTouched(final BeanCollectionTouched p0);
    
    void setLoader(final int p0, final BeanCollectionLoader p1);
    
    void setReadOnly(final boolean p0);
    
    boolean isReadOnly();
    
    void internalAdd(final Object p0);
    
    Object getActualCollection();
    
    int size();
    
    boolean isEmpty();
    
    Collection<E> getActualDetails();
    
    boolean hasMoreRows();
    
    void setHasMoreRows(final boolean p0);
    
    boolean isFinishedFetch();
    
    void setFinishedFetch(final boolean p0);
    
    boolean isPopulated();
    
    boolean isReference();
    
    void setModifyListening(final ModifyListenMode p0);
    
    void modifyAddition(final E p0);
    
    void modifyRemoval(final Object p0);
    
    Set<E> getModifyAdditions();
    
    Set<E> getModifyRemovals();
    
    void modifyReset();
    
    public enum ModifyListenMode
    {
        NONE, 
        REMOVALS, 
        ALL;
    }
}
