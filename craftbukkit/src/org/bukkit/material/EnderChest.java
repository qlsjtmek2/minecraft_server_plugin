// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class EnderChest extends DirectionalContainer
{
    public EnderChest() {
        super(Material.ENDER_CHEST);
    }
    
    public EnderChest(final BlockFace direction) {
        this();
        this.setFacingDirection(direction);
    }
    
    public EnderChest(final int type) {
        super(type);
    }
    
    public EnderChest(final Material type) {
        super(type);
    }
    
    public EnderChest(final int type, final byte data) {
        super(type, data);
    }
    
    public EnderChest(final Material type, final byte data) {
        super(type, data);
    }
    
    public EnderChest clone() {
        return (EnderChest)super.clone();
    }
}
