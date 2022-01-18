// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class DbType
{
    private final String name;
    private final int defaultLength;
    private final int defaultScale;
    private final boolean canHaveLength;
    
    public DbType(final String name) {
        this(name, 0, 0);
    }
    
    public DbType(final String name, final int defaultLength) {
        this(name, defaultLength, 0);
    }
    
    public DbType(final String name, final int defaultPrecision, final int defaultScale) {
        this.name = name;
        this.defaultLength = defaultPrecision;
        this.defaultScale = defaultScale;
        this.canHaveLength = true;
    }
    
    public DbType(final String name, final boolean canHaveLength) {
        this.name = name;
        this.defaultLength = 0;
        this.defaultScale = 0;
        this.canHaveLength = canHaveLength;
    }
    
    public String renderType(final int deployLength, final int deployScale) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        if (this.canHaveLength) {
            final int len = (deployLength != 0) ? deployLength : this.defaultLength;
            if (len > 0) {
                sb.append("(");
                sb.append(len);
                final int scale = (deployScale != 0) ? deployScale : this.defaultScale;
                if (scale > 0) {
                    sb.append(",");
                    sb.append(scale);
                }
                sb.append(")");
            }
        }
        return sb.toString();
    }
}
