// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Update;

public interface SpiUpdate<T> extends Update<T>
{
    Class<?> getBeanType();
    
    OrmUpdateType getOrmUpdateType();
    
    String getBaseTable();
    
    String getUpdateStatement();
    
    int getTimeout();
    
    boolean isNotifyCache();
    
    BindParams getBindParams();
    
    void setGeneratedSql(final String p0);
    
    public enum OrmUpdateType
    {
        INSERT {
            public String toString() {
                return "Insert";
            }
        }, 
        UPDATE {
            public String toString() {
                return "Update";
            }
        }, 
        DELETE {
            public String toString() {
                return "Delete";
            }
        }, 
        UNKNOWN {
            public String toString() {
                return "Unknown";
            }
        };
    }
}
