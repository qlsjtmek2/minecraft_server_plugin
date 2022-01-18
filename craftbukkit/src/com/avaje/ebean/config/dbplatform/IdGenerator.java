// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.Transaction;

public interface IdGenerator
{
    public static final String AUTO_UUID = "auto.uuid";
    
    String getName();
    
    boolean isDbSequence();
    
    Object nextId(final Transaction p0);
    
    void preAllocateIds(final int p0);
}
