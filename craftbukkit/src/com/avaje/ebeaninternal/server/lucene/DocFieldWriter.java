// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.Document;

public interface DocFieldWriter
{
    void writeValue(final Object p0, final Document p1);
}
