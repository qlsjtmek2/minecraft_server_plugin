// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import javax.persistence.PersistenceException;

public class PersistenceLuceneException extends PersistenceException
{
    private static final long serialVersionUID = 1495423311592521260L;
    
    public PersistenceLuceneException(final Throwable e) {
        super(e);
    }
    
    public PersistenceLuceneException(final String msg, final Throwable e) {
        super(msg, e);
    }
    
    public PersistenceLuceneException(final String msg) {
        super(msg);
    }
}
