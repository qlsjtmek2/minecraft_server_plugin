// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.ArrayList;

public final class TxScope
{
    TxType type;
    String serverName;
    TxIsolation isolation;
    boolean readOnly;
    ArrayList<Class<? extends Throwable>> rollbackFor;
    ArrayList<Class<? extends Throwable>> noRollbackFor;
    
    public static TxScope required() {
        return new TxScope(TxType.REQUIRED);
    }
    
    public static TxScope requiresNew() {
        return new TxScope(TxType.REQUIRES_NEW);
    }
    
    public static TxScope mandatory() {
        return new TxScope(TxType.MANDATORY);
    }
    
    public static TxScope supports() {
        return new TxScope(TxType.SUPPORTS);
    }
    
    public static TxScope notSupported() {
        return new TxScope(TxType.NOT_SUPPORTED);
    }
    
    public static TxScope never() {
        return new TxScope(TxType.NEVER);
    }
    
    public TxScope() {
        this.type = TxType.REQUIRED;
    }
    
    public TxScope(final TxType type) {
        this.type = type;
    }
    
    public String toString() {
        return "TxScope[" + this.type + "] readOnly[" + this.readOnly + "] isolation[" + this.isolation + "] serverName[" + this.serverName + "] rollbackFor[" + this.rollbackFor + "] noRollbackFor[" + this.noRollbackFor + "]";
    }
    
    public TxType getType() {
        return this.type;
    }
    
    public TxScope setType(final TxType type) {
        this.type = type;
        return this;
    }
    
    public boolean isReadonly() {
        return this.readOnly;
    }
    
    public TxScope setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }
    
    public TxIsolation getIsolation() {
        return this.isolation;
    }
    
    public TxScope setIsolation(final TxIsolation isolation) {
        this.isolation = isolation;
        return this;
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public TxScope setServerName(final String serverName) {
        this.serverName = serverName;
        return this;
    }
    
    public ArrayList<Class<? extends Throwable>> getRollbackFor() {
        return this.rollbackFor;
    }
    
    public TxScope setRollbackFor(final Class<? extends Throwable> rollbackThrowable) {
        if (this.rollbackFor == null) {
            this.rollbackFor = new ArrayList<Class<? extends Throwable>>(2);
        }
        this.rollbackFor.add(rollbackThrowable);
        return this;
    }
    
    public TxScope setRollbackFor(final Class<?>[] rollbackThrowables) {
        if (this.rollbackFor == null) {
            this.rollbackFor = new ArrayList<Class<? extends Throwable>>(rollbackThrowables.length);
        }
        for (int i = 0; i < rollbackThrowables.length; ++i) {
            this.rollbackFor.add((Class<? extends Throwable>)rollbackThrowables[i]);
        }
        return this;
    }
    
    public ArrayList<Class<? extends Throwable>> getNoRollbackFor() {
        return this.noRollbackFor;
    }
    
    public TxScope setNoRollbackFor(final Class<? extends Throwable> noRollback) {
        if (this.noRollbackFor == null) {
            this.noRollbackFor = new ArrayList<Class<? extends Throwable>>(2);
        }
        this.noRollbackFor.add(noRollback);
        return this;
    }
    
    public TxScope setNoRollbackFor(final Class<?>[] noRollbacks) {
        if (this.noRollbackFor == null) {
            this.noRollbackFor = new ArrayList<Class<? extends Throwable>>(noRollbacks.length);
        }
        for (int i = 0; i < noRollbacks.length; ++i) {
            this.noRollbackFor.add((Class<? extends Throwable>)noRollbacks[i]);
        }
        return this;
    }
}
