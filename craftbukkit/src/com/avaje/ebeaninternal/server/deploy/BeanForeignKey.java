// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.core.InternString;

public class BeanForeignKey
{
    private final String dbColumn;
    private final int dbType;
    
    public BeanForeignKey(final String dbColumn, final int dbType) {
        this.dbColumn = InternString.intern(dbColumn);
        this.dbType = dbType;
    }
    
    public String getDbColumn() {
        return this.dbColumn;
    }
    
    public int getDbType() {
        return this.dbType;
    }
    
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof BeanForeignKey && obj.hashCode() == this.hashCode();
    }
    
    public int hashCode() {
        int hc = this.getClass().hashCode();
        hc = hc * 31 + ((this.dbColumn != null) ? this.dbColumn.hashCode() : 0);
        return hc;
    }
    
    public String toString() {
        return this.dbColumn;
    }
}
