// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.stream;

final class StringPool
{
    private final String[] pool;
    
    StringPool() {
        this.pool = new String[512];
    }
    
    public String get(final char[] array, final int start, final int length) {
        int hashCode = 0;
        for (int i = start; i < start + length; ++i) {
            hashCode = hashCode * 31 + array[i];
        }
        hashCode ^= (hashCode >>> 20 ^ hashCode >>> 12);
        hashCode ^= (hashCode >>> 7 ^ hashCode >>> 4);
        final int index = hashCode & this.pool.length - 1;
        final String pooled = this.pool[index];
        if (pooled == null || pooled.length() != length) {
            final String result = new String(array, start, length);
            return this.pool[index] = result;
        }
        for (int j = 0; j < length; ++j) {
            if (pooled.charAt(j) != array[start + j]) {
                final String result2 = new String(array, start, length);
                return this.pool[index] = result2;
            }
        }
        return pooled;
    }
}
