// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface FutureIds<T> extends Future<List<Object>>
{
    Query<T> getQuery();
    
    List<Object> getPartialIds();
}
