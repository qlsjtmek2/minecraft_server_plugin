// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.material.Colorable;

public interface Sheep extends Animals, Colorable
{
    boolean isSheared();
    
    void setSheared(final boolean p0);
}
