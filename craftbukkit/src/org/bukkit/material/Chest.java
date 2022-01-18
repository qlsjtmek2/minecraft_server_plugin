// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Chest extends DirectionalContainer
{
    public Chest() {
        super(Material.CHEST);
    }
    
    public Chest(final BlockFace direction) {
        this();
        this.setFacingDirection(direction);
    }
    
    public Chest(final int type) {
        super(type);
    }
    
    public Chest(final Material type) {
        super(type);
    }
    
    public Chest(final int type, final byte data) {
        super(type, data);
    }
    
    public Chest(final Material type, final byte data) {
        super(type, data);
    }
    
    public Chest clone() {
        return (Chest)super.clone();
    }
}
