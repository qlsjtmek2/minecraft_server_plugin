// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class Hashing
{
    static int smear(int hashCode) {
        hashCode ^= (hashCode >>> 20 ^ hashCode >>> 12);
        return hashCode ^ hashCode >>> 7 ^ hashCode >>> 4;
    }
}
