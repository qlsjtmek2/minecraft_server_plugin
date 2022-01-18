// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface BstPathFactory<N extends BstNode<?, N>, P extends BstPath<N, P>>
{
    P extension(final P p0, final BstSide p1);
    
    P initialPath(final N p0);
}
