// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.store.Directory;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.index.IndexCommit;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.lucene.index.IndexWriter;
import java.util.logging.Logger;

public class LIndexIoSearcherDefault implements LIndexIoSearcher
{
    private static final Logger logger;
    private final String name;
    private final IndexWriter indexWriter;
    private volatile LIndexSearch indexSearch;
    
    public LIndexIoSearcherDefault(final IndexWriter indexWriter, final String name) {
        this.name = name;
        this.indexWriter = indexWriter;
        this.indexSearch = this.refreshIndexSearch();
    }
    
    public void postCommit() {
        try {
            this.refreshIndexSearch();
        }
        catch (Exception e) {
            final String msg = "Error postCommit() refreshing IndexSearcher";
            LIndexIoSearcherDefault.logger.log(Level.SEVERE, msg, e);
        }
    }
    
    public void refresh(final boolean nearRealTime) {
        try {
            this.refreshIndexSearch();
        }
        catch (Exception e) {
            final String msg = "Error refreshing IndexSearch";
            LIndexIoSearcherDefault.logger.log(Level.SEVERE, msg, e);
        }
    }
    
    public LIndexVersion getLastestVersion() {
        final LIndexSearch s = this.indexSearch;
        try {
            final IndexCommit c = s.getIndexReader().getIndexCommit();
            return new LIndexVersion(c.getGeneration(), c.getVersion());
        }
        catch (IOException e) {
            throw new PersistenceLuceneException(e);
        }
    }
    
    public LIndexSearch getIndexSearch() {
        final LIndexSearch s = this.indexSearch;
        if (s.isOpenAcquire()) {
            return s;
        }
        return this.refreshIndexSearch();
    }
    
    private LIndexSearch refreshIndexSearch() {
        synchronized (this) {
            try {
                final LIndexSearch currentSearch = this.indexSearch;
                IndexReader newReader;
                if (currentSearch == null) {
                    newReader = this.createIndexReader();
                }
                else {
                    newReader = currentSearch.getIndexReader().reopen();
                    if (newReader == currentSearch.getIndexReader()) {
                        return currentSearch;
                    }
                }
                final IndexSearcher searcher = new IndexSearcher(newReader);
                final LIndexSearch newSearch = new LIndexSearch(searcher, newReader);
                if (currentSearch != null) {
                    currentSearch.markForClose();
                }
                LIndexIoSearcherDefault.logger.info("Lucene Searcher refreshed " + this.name);
                return this.indexSearch = newSearch;
            }
            catch (IOException e) {
                throw new PersistenceLuceneException(e);
            }
        }
    }
    
    private IndexReader createIndexReader() {
        try {
            final Directory directory = this.indexWriter.getDirectory();
            return IndexReader.open(directory);
        }
        catch (IOException e) {
            throw new PersistenceLuceneException(e);
        }
    }
    
    static {
        logger = Logger.getLogger(LIndexIoSearcherDefault.class.getName());
    }
}
