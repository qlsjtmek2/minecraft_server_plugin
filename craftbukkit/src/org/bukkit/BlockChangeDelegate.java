// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

public interface BlockChangeDelegate
{
    boolean setRawTypeId(final int p0, final int p1, final int p2, final int p3);
    
    boolean setRawTypeIdAndData(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    boolean setTypeId(final int p0, final int p1, final int p2, final int p3);
    
    boolean setTypeIdAndData(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    int getTypeId(final int p0, final int p1, final int p2);
    
    int getHeight();
    
    boolean isEmpty(final int p0, final int p1, final int p2);
}
