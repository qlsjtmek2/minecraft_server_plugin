// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

import java.util.Map;

public interface JsonReadBeanVisitor<T>
{
    void visit(final T p0, final Map<String, JsonElement> p1);
}
