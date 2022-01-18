// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

public interface Sign extends BlockState
{
    String[] getLines();
    
    String getLine(final int p0) throws IndexOutOfBoundsException;
    
    void setLine(final int p0, final String p1) throws IndexOutOfBoundsException;
}
