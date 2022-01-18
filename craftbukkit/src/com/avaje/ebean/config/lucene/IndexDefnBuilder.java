// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.lucene;

import java.util.List;
import org.apache.lucene.document.Field;

public interface IndexDefnBuilder
{
    void addAllFields();
    
    IndexDefnBuilder assocOne(final String p0);
    
    IndexFieldDefn addField(final IndexFieldDefn p0);
    
    IndexFieldDefn addField(final String p0);
    
    IndexFieldDefn addField(final String p0, final IndexFieldDefn.Sortable p1);
    
    IndexFieldDefn addField(final String p0, final Field.Store p1, final Field.Index p2, final IndexFieldDefn.Sortable p3);
    
    IndexFieldDefn addFieldConcat(final String p0, final String... p1);
    
    IndexFieldDefn addFieldConcat(final String p0, final Field.Store p1, final Field.Index p2, final String... p3);
    
    IndexFieldDefn getField(final String p0);
    
    List<IndexFieldDefn> getFields();
}
