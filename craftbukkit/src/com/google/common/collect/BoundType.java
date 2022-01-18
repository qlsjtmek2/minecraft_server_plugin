// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible
public enum BoundType
{
    OPEN, 
    CLOSED;
    
    static BoundType forBoolean(final boolean inclusive) {
        return inclusive ? BoundType.CLOSED : BoundType.OPEN;
    }
}
