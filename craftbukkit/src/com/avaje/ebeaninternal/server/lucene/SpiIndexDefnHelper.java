// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexFieldDefn;
import org.apache.lucene.document.Field;
import com.avaje.ebean.config.lucene.IndexDefnBuilder;

public interface SpiIndexDefnHelper extends IndexDefnBuilder
{
    IndexFieldDefn addPrefixField(final String p0, final String p1, final Field.Store p2, final Field.Index p3, final IndexFieldDefn.Sortable p4);
    
    IndexFieldDefn addPrefixFieldConcat(final String p0, final String p1, final Field.Store p2, final Field.Index p3, final String[] p4);
}
