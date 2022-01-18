// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import org.bukkit.Material;

public interface Jukebox extends BlockState
{
    Material getPlaying();
    
    void setPlaying(final Material p0);
    
    boolean isPlaying();
    
    boolean eject();
}
