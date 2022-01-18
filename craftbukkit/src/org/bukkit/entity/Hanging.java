// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.block.BlockFace;
import org.bukkit.material.Attachable;

public interface Hanging extends Entity, Attachable
{
    boolean setFacingDirection(final BlockFace p0, final boolean p1);
}
