// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class MySqlBlob extends DbType
{
    private static final int POWER_2_16 = 65536;
    private static final int POWER_2_24 = 16777216;
    
    public MySqlBlob() {
        super("blob");
    }
    
    public String renderType(final int deployLength, final int deployScale) {
        if (deployLength >= 16777216) {
            return "longblob";
        }
        if (deployLength >= 65536) {
            return "mediumblob";
        }
        if (deployLength < 1) {
            return "longblob";
        }
        return "blob";
    }
}
