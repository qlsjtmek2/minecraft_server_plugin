// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface FutureList<T> extends Future<List<T>>
{
    Query<T> getQuery();
}
