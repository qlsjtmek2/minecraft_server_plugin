// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public enum TxIsolation
{
    READ_COMMITED(2), 
    READ_UNCOMMITTED(1), 
    REPEATABLE_READ(4), 
    SERIALIZABLE(8), 
    NONE(0), 
    DEFAULT(-1);
    
    final int level;
    
    private TxIsolation(final int level) {
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public static TxIsolation fromLevel(final int connectionIsolationLevel) {
        switch (connectionIsolationLevel) {
            case 1: {
                return TxIsolation.READ_UNCOMMITTED;
            }
            case 2: {
                return TxIsolation.READ_COMMITED;
            }
            case 4: {
                return TxIsolation.REPEATABLE_READ;
            }
            case 8: {
                return TxIsolation.SERIALIZABLE;
            }
            case 0: {
                return TxIsolation.NONE;
            }
            case -1: {
                return TxIsolation.DEFAULT;
            }
            default: {
                throw new RuntimeException("Unknown isolation level " + connectionIsolationLevel);
            }
        }
    }
}
