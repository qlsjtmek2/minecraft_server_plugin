// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.internal;

public final class Pair<FIRST, SECOND>
{
    public final FIRST first;
    public final SECOND second;
    
    public Pair(final FIRST first, final SECOND second) {
        this.first = first;
        this.second = second;
    }
    
    public int hashCode() {
        return 17 * ((this.first != null) ? this.first.hashCode() : 0) + 17 * ((this.second != null) ? this.second.hashCode() : 0);
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        final Pair<?, ?> that = (Pair<?, ?>)o;
        return equal(this.first, that.first) && equal(this.second, that.second);
    }
    
    private static boolean equal(final Object a, final Object b) {
        return a == b || (a != null && a.equals(b));
    }
    
    public String toString() {
        return String.format("{%s,%s}", this.first, this.second);
    }
}
