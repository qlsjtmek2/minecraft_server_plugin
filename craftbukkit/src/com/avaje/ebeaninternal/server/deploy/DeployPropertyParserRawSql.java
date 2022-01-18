// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Set;

public final class DeployPropertyParserRawSql extends DeployParser
{
    private final DRawSqlSelect rawSqlSelect;
    
    public DeployPropertyParserRawSql(final DRawSqlSelect rawSqlSelect) {
        this.rawSqlSelect = rawSqlSelect;
    }
    
    public Set<String> getIncludes() {
        return null;
    }
    
    public String convertWord() {
        final String r = this.getDeployWord(this.word);
        return (r == null) ? this.word : r;
    }
    
    public String getDeployWord(final String expression) {
        final DRawSqlColumnInfo columnInfo = this.rawSqlSelect.getRawSqlColumnInfo(expression);
        if (columnInfo == null) {
            return null;
        }
        return columnInfo.getName();
    }
}
