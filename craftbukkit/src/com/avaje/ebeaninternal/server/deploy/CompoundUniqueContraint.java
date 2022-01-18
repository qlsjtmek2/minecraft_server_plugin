// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

public class CompoundUniqueContraint
{
    private final String[] columns;
    
    public CompoundUniqueContraint(final String[] columns) {
        this.columns = columns;
    }
    
    public String[] getColumns() {
        return this.columns;
    }
}
