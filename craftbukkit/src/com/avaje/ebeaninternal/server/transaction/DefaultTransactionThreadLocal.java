// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.api.SpiTransaction;

public final class DefaultTransactionThreadLocal
{
    private static ThreadLocal<TransactionMap> local;
    
    private static TransactionMap.State getState(final String serverName) {
        return DefaultTransactionThreadLocal.local.get().getState(serverName);
    }
    
    public static SpiTransaction get(final String serverName) {
        return getState(serverName).transaction;
    }
    
    public static void set(final String serverName, final SpiTransaction trans) {
        getState(serverName).set(trans);
    }
    
    public static void commit(final String serverName) {
        getState(serverName).commit();
    }
    
    public static void rollback(final String serverName) {
        getState(serverName).rollback();
    }
    
    public static void end(final String serverName) {
        getState(serverName).end();
    }
    
    public static void replace(final String serverName, final SpiTransaction trans) {
        getState(serverName).replace(trans);
    }
    
    static {
        DefaultTransactionThreadLocal.local = new ThreadLocal<TransactionMap>() {
            protected synchronized TransactionMap initialValue() {
                return new TransactionMap();
            }
        };
    }
}
