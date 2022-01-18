// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import javax.persistence.PersistenceException;
import com.avaje.ebean.Page;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import java.util.ArrayList;
import java.util.concurrent.Future;
import com.avaje.ebeaninternal.api.Monitor;
import java.util.List;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.PagingList;

public class LimitOffsetPagingQuery<T> implements PagingList<T>
{
    private transient EbeanServer server;
    private final SpiQuery<T> query;
    private final List<LimitOffsetPage<T>> pages;
    private final Monitor monitor;
    private final int pageSize;
    private boolean fetchAhead;
    private Future<Integer> futureRowCount;
    
    public LimitOffsetPagingQuery(final EbeanServer server, final SpiQuery<T> query, final int pageSize) {
        this.pages = new ArrayList<LimitOffsetPage<T>>();
        this.monitor = new Monitor();
        this.fetchAhead = true;
        this.query = query;
        this.pageSize = pageSize;
        this.server = server;
    }
    
    public EbeanServer getServer() {
        return this.server;
    }
    
    public void setServer(final EbeanServer server) {
        this.server = server;
    }
    
    public SpiQuery<T> getSpiQuery() {
        return this.query;
    }
    
    public PagingList<T> setFetchAhead(final boolean fetchAhead) {
        this.fetchAhead = fetchAhead;
        return this;
    }
    
    public List<T> getAsList() {
        return new LimitOffsetList<T>(this);
    }
    
    public Future<Integer> getFutureRowCount() {
        synchronized (this.monitor) {
            if (this.futureRowCount == null) {
                this.futureRowCount = this.server.findFutureRowCount(this.query, null);
            }
            return this.futureRowCount;
        }
    }
    
    private LimitOffsetPage<T> internalGetPage(final int i) {
        synchronized (this.monitor) {
            final int ps = this.pages.size();
            if (ps <= i) {
                for (int j = ps; j <= i; ++j) {
                    final LimitOffsetPage<T> p = new LimitOffsetPage<T>(j, this);
                    this.pages.add(p);
                }
            }
            return this.pages.get(i);
        }
    }
    
    protected void fetchAheadIfRequired(final int pageIndex) {
        synchronized (this.monitor) {
            if (this.fetchAhead) {
                final LimitOffsetPage<T> nextPage = this.internalGetPage(pageIndex + 1);
                nextPage.getFutureList();
            }
        }
    }
    
    public void refresh() {
        synchronized (this.monitor) {
            this.futureRowCount = null;
            this.pages.clear();
        }
    }
    
    public Page<T> getPage(final int i) {
        return this.internalGetPage(i);
    }
    
    protected boolean hasNext(final int position) {
        return position < this.getTotalRowCount();
    }
    
    protected T get(final int rowIndex) {
        final int pg = rowIndex / this.pageSize;
        final int offset = rowIndex % this.pageSize;
        final Page<T> page = this.getPage(pg);
        return page.getList().get(offset);
    }
    
    public int getTotalPageCount() {
        final int rowCount = this.getTotalRowCount();
        if (rowCount == 0) {
            return 0;
        }
        return (rowCount - 1) / this.pageSize + 1;
    }
    
    public int getPageSize() {
        return this.pageSize;
    }
    
    public int getTotalRowCount() {
        try {
            return this.getFutureRowCount().get();
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
