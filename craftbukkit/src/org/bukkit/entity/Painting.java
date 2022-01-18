// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.Art;

public interface Painting extends Hanging
{
    Art getArt();
    
    boolean setArt(final Art p0);
    
    boolean setArt(final Art p0, final boolean p1);
}
