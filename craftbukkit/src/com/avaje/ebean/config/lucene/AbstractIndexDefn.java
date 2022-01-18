// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.lucene;

import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.Analyzer;

public abstract class AbstractIndexDefn<T> implements IndexDefn<T>
{
    public Analyzer getAnalyzer() {
        return null;
    }
    
    public int getMaxBufferedDocs() {
        return 0;
    }
    
    public IndexWriter.MaxFieldLength getMaxFieldLength() {
        return IndexWriter.MaxFieldLength.UNLIMITED;
    }
    
    public double getRAMBufferSizeMB() {
        return 0.0;
    }
    
    public int getTermIndexInterval() {
        return 0;
    }
    
    public void visitCreatedField(final Fieldable field) {
    }
}
