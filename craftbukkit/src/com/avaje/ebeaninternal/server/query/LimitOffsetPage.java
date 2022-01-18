// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import javax.persistence.PersistenceException;
import java.util.List;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.bean.BeanCollectionTouched;
import com.avaje.ebean.Page;

public class LimitOffsetPage<T> implements Page<T>, BeanCollectionTouched
{
    private final int pageIndex;
    private final LimitOffsetPagingQuery<T> owner;
    private FutureList<T> futureList;
    
    public LimitOffsetPage(final int pageIndex, final LimitOffsetPagingQuery<T> owner) {
        this.pageIndex = pageIndex;
        this.owner = owner;
    }
    
    public FutureList<T> getFutureList() {
        if (this.futureList == null) {
            final SpiQuery<T> originalQuery = this.owner.getSpiQuery();
            final SpiQuery<T> copy = originalQuery.copy();
            copy.setPersistenceContext(originalQuery.getPersistenceContext());
            final int pageSize = this.owner.getPageSize();
            copy.setFirstRow(this.pageIndex * pageSize);
            copy.setMaxRows(pageSize);
            copy.setBeanCollectionTouched(this);
            this.futureList = this.owner.getServer().findFutureList(copy, null);
        }
        return this.futureList;
    }
    
    public void notifyTouched(final BeanCollection<?> c) {
        if (c.hasMoreRows()) {
            this.owner.fetchAheadIfRequired(this.pageIndex);
        }
    }
    
    public List<T> getList() {
        try {
            return this.getFutureList().get();
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
    
    public boolean hasNext() {
        return ((BeanCollection)this.getList()).hasMoreRows();
    }
    
    public boolean hasPrev() {
        return this.pageIndex > 0;
    }
    
    public Page<T> next() {
        return this.owner.getPage(this.pageIndex + 1);
    }
    
    public Page<T> prev() {
        return this.owner.getPage(this.pageIndex - 1);
    }
    
    public int getPageIndex() {
        return this.pageIndex;
    }
    
    public int getTotalPageCount() {
        return this.owner.getTotalPageCount();
    }
    
    public int getTotalRowCount() {
        return this.owner.getTotalRowCount();
    }
    
    public String getDisplayXtoYofZ(final String to, final String of) {
        final int first = this.pageIndex * this.owner.getPageSize() + 1;
        final int last = first + this.getList().size() - 1;
        final int total = this.getTotalRowCount();
        return first + to + last + of + total;
    }
}
