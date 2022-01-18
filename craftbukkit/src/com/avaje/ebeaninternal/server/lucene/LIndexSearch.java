// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.util.logging.Level;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import java.util.logging.Logger;

public class LIndexSearch
{
    private static final Logger logger;
    private final IndexSearcher indexSearcher;
    private final IndexReader indexReader;
    private int refCount;
    private boolean markForClose;
    private boolean closed;
    
    public LIndexSearch(final IndexSearcher indexSearcher, final IndexReader indexReader) {
        this.indexSearcher = indexSearcher;
        this.indexReader = indexReader;
    }
    
    public IndexSearcher getIndexSearcher() {
        return this.indexSearcher;
    }
    
    public IndexReader getIndexReader() {
        return this.indexReader;
    }
    
    public boolean isOpenAcquire() {
        synchronized (this) {
            if (this.markForClose) {
                return false;
            }
            ++this.refCount;
            return true;
        }
    }
    
    public void releaseClose() {
        synchronized (this) {
            --this.refCount;
            this.closeIfMarked();
        }
    }
    
    public void markForClose() {
        synchronized (this) {
            this.markForClose = true;
            this.closeIfMarked();
        }
    }
    
    private void closeIfMarked() {
        if (this.markForClose && this.refCount <= 0 && !this.closed) {
            this.closed = true;
            try {
                this.indexSearcher.close();
            }
            catch (Exception e) {
                LIndexSearch.logger.log(Level.WARNING, "Error when closing indexSearcher", e);
            }
            try {
                this.indexReader.close();
            }
            catch (Exception e) {
                LIndexSearch.logger.log(Level.WARNING, "Error when closing indexReader", e);
            }
        }
    }
    
    static {
        logger = Logger.getLogger(LIndexSearch.class.getName());
    }
}
