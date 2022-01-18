// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Iterator;

public abstract class IterateBlock
{
    DatabaseMetaData.IteratorWithCleanup iteratorWithCleanup;
    Iterator javaIterator;
    boolean stopIterating;
    
    IterateBlock(final DatabaseMetaData.IteratorWithCleanup i) {
        this.stopIterating = false;
        this.iteratorWithCleanup = i;
        this.javaIterator = null;
    }
    
    IterateBlock(final Iterator i) {
        this.stopIterating = false;
        this.javaIterator = i;
        this.iteratorWithCleanup = null;
    }
    
    public void doForAll() throws SQLException {
        if (this.iteratorWithCleanup != null) {
            try {
                while (this.iteratorWithCleanup.hasNext()) {
                    this.forEach(this.iteratorWithCleanup.next());
                    if (this.stopIterating) {
                        break;
                    }
                }
            }
            finally {
                this.iteratorWithCleanup.close();
            }
        }
        else {
            while (this.javaIterator.hasNext()) {
                this.forEach(this.javaIterator.next());
                if (this.stopIterating) {
                    break;
                }
            }
        }
    }
    
    abstract void forEach(final Object p0) throws SQLException;
    
    public final boolean fullIteration() {
        return !this.stopIterating;
    }
}
