// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

public class TransactionIsolation
{
    public static int getLevel(String level) {
        level = level.toUpperCase();
        if (level.startsWith("TRANSACTION")) {
            level = level.substring("TRANSACTION".length());
        }
        level = level.replace("_", "");
        if ("NONE".equalsIgnoreCase(level)) {
            return 0;
        }
        if ("READCOMMITTED".equalsIgnoreCase(level)) {
            return 2;
        }
        if ("READUNCOMMITTED".equalsIgnoreCase(level)) {
            return 1;
        }
        if ("REPEATABLEREAD".equalsIgnoreCase(level)) {
            return 4;
        }
        if ("SERIALIZABLE".equalsIgnoreCase(level)) {
            return 8;
        }
        throw new RuntimeException("Transaction Isolaction level [" + level + "] is not known.");
    }
    
    public static String getLevelDescription(final int level) {
        switch (level) {
            case 0: {
                return "NONE";
            }
            case 2: {
                return "READ_COMMITTED";
            }
            case 1: {
                return "READ_UNCOMMITTED";
            }
            case 4: {
                return "REPEATABLE_READ";
            }
            case 8: {
                return "SERIALIZABLE";
            }
            case -1: {
                return "NotSet";
            }
            default: {
                throw new RuntimeException("Transaction Isolaction level [" + level + "] is not defined.");
            }
        }
    }
}
