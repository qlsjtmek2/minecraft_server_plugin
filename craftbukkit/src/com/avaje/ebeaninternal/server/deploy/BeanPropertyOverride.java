// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.core.InternString;

public class BeanPropertyOverride
{
    private final String dbColumn;
    private final String sqlFormulaSelect;
    private final String sqlFormulaJoin;
    
    public BeanPropertyOverride(final String dbColumn) {
        this(dbColumn, null, null);
    }
    
    public BeanPropertyOverride(final String dbColumn, final String sqlFormulaSelect, final String sqlFormulaJoin) {
        this.dbColumn = InternString.intern(dbColumn);
        this.sqlFormulaSelect = InternString.intern(sqlFormulaSelect);
        this.sqlFormulaJoin = InternString.intern(sqlFormulaJoin);
    }
    
    public String getDbColumn() {
        return this.dbColumn;
    }
    
    public String getSqlFormulaSelect() {
        return this.sqlFormulaSelect;
    }
    
    public String getSqlFormulaJoin() {
        return this.sqlFormulaJoin;
    }
    
    public String replace(final String src, final String srcDbColumn) {
        return StringHelper.replaceString(src, srcDbColumn, this.dbColumn);
    }
}
