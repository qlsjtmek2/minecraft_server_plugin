// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.NoSuchElementException;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public abstract class DiscreteDomain<C extends Comparable>
{
    public abstract C next(final C p0);
    
    public abstract C previous(final C p0);
    
    public abstract long distance(final C p0, final C p1);
    
    public C minValue() {
        throw new NoSuchElementException();
    }
    
    public C maxValue() {
        throw new NoSuchElementException();
    }
}
