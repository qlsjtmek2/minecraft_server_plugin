// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.idgen;

import java.util.UUID;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.dbplatform.IdGenerator;

public class UuidIdGenerator implements IdGenerator
{
    public Object nextId(final Transaction t) {
        return UUID.randomUUID();
    }
    
    public String getName() {
        return "uuid";
    }
    
    public boolean isDbSequence() {
        return false;
    }
    
    public void preAllocateIds(final int allocateSize) {
    }
}
