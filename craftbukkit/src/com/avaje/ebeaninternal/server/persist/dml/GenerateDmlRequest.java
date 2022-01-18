// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.Set;

public class GenerateDmlRequest
{
    private static final String IS_NULL = " is null";
    private final boolean emptyStringAsNull;
    private final StringBuilder sb;
    private final Set<String> includeProps;
    private final Set<String> includeWhereProps;
    private final Object oldValues;
    private StringBuilder insertBindBuffer;
    private String prefix;
    private String prefix2;
    private int insertMode;
    private int bindColumnCount;
    
    public GenerateDmlRequest(final boolean emptyStringAsNull, final Set<String> includeProps, final Object oldValues) {
        this(emptyStringAsNull, includeProps, includeProps, oldValues);
    }
    
    public GenerateDmlRequest(final boolean emptyStringAsNull, final Set<String> includeProps, final Set<String> includeWhereProps, final Object oldValues) {
        this.sb = new StringBuilder(100);
        this.emptyStringAsNull = emptyStringAsNull;
        this.includeProps = includeProps;
        this.includeWhereProps = includeWhereProps;
        this.oldValues = oldValues;
    }
    
    public GenerateDmlRequest(final boolean emptyStringAsNull) {
        this(emptyStringAsNull, null, null, null);
    }
    
    public GenerateDmlRequest append(final String s) {
        this.sb.append(s);
        return this;
    }
    
    public boolean isDbNull(final Object v) {
        return v == null || (this.emptyStringAsNull && v instanceof String && ((String)v).length() == 0);
    }
    
    public boolean isIncluded(final BeanProperty prop) {
        return this.includeProps == null || this.includeProps.contains(prop.getName());
    }
    
    public boolean isIncludedWhere(final BeanProperty prop) {
        return this.includeWhereProps == null || this.includeWhereProps.contains(prop.getName());
    }
    
    public void appendColumnIsNull(final String column) {
        this.appendColumn(column, " is null");
    }
    
    public void appendColumn(final String column) {
        final String bind = (this.insertMode > 0) ? "?" : "=?";
        this.appendColumn(column, bind);
    }
    
    public void appendColumn(final String column, final String suffik) {
        this.appendColumn(column, "", suffik);
    }
    
    public void appendColumn(final String column, final String expr, final String suffik) {
        ++this.bindColumnCount;
        this.sb.append(this.prefix);
        this.sb.append(column);
        this.sb.append(expr);
        if (this.insertMode > 0) {
            if (this.insertMode++ > 1) {
                this.insertBindBuffer.append(",");
            }
            this.insertBindBuffer.append(suffik);
        }
        else {
            this.sb.append(suffik);
        }
        if (this.prefix2 != null) {
            this.prefix = this.prefix2;
            this.prefix2 = null;
        }
    }
    
    public int getBindColumnCount() {
        return this.bindColumnCount;
    }
    
    public String getInsertBindBuffer() {
        return this.insertBindBuffer.toString();
    }
    
    public String toString() {
        return this.sb.toString();
    }
    
    public void setWhereMode() {
        this.prefix = " and ";
        this.prefix2 = " and ";
    }
    
    public void setWhereIdMode() {
        this.prefix = "";
        this.prefix2 = " and ";
    }
    
    public void setInsertSetMode() {
        this.insertBindBuffer = new StringBuilder(100);
        this.insertMode = 1;
        this.prefix = "";
        this.prefix2 = ", ";
    }
    
    public void setUpdateSetMode() {
        this.prefix = "";
        this.prefix2 = ", ";
    }
    
    public Object getOldValues() {
        return this.oldValues;
    }
}
