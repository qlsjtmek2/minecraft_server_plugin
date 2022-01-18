// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

import com.avaje.ebean.Query;

public class BeanCollectionParams
{
    private final Query.Type manyType;
    
    public BeanCollectionParams(final Query.Type manyType) {
        this.manyType = manyType;
    }
    
    public Query.Type getManyType() {
        return this.manyType;
    }
}
