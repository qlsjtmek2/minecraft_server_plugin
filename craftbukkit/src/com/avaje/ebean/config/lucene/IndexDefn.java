// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.lucene;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.Analyzer;
import java.util.List;

public interface IndexDefn<T>
{
    void initialise(final IndexDefnBuilder p0);
    
    String getDefaultField();
    
    List<IndexFieldDefn> getFields();
    
    boolean isUpdateSinceSupported();
    
    String[] getUpdateSinceProperties();
    
    Analyzer getAnalyzer();
    
    IndexWriter.MaxFieldLength getMaxFieldLength();
    
    int getMaxBufferedDocs();
    
    double getRAMBufferSizeMB();
    
    int getTermIndexInterval();
}
