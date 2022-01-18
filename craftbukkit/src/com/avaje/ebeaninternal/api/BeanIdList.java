// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import javax.persistence.PersistenceException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.FutureTask;
import java.util.List;

public class BeanIdList
{
    private final List<Object> idList;
    private boolean hasMore;
    private FutureTask<Integer> fetchFuture;
    
    public BeanIdList(final List<Object> idList) {
        this.hasMore = true;
        this.idList = idList;
    }
    
    public boolean isFetchingInBackground() {
        return this.fetchFuture != null;
    }
    
    public void setBackgroundFetch(final FutureTask<Integer> fetchFuture) {
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
    
    public void add(final Object id) {
        this.idList.add(id);
    }
    
    public List<Object> getIdList() {
        return this.idList;
    }
    
    public boolean isHasMore() {
        return this.hasMore;
    }
    
    public void setHasMore(final boolean hasMore) {
        this.hasMore = hasMore;
    }
}
