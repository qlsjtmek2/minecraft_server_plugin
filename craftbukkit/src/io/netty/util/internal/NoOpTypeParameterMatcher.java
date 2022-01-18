// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.internal;

public final class NoOpTypeParameterMatcher extends TypeParameterMatcher
{
    @Override
    public boolean match(final Object msg) {
        return true;
    }
}
