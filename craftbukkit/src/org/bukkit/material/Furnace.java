// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Furnace extends FurnaceAndDispenser
{
    public Furnace() {
        super(Material.FURNACE);
    }
    
    public Furnace(final BlockFace direction) {
        this();
        this.setFacingDirection(direction);
    }
    
    public Furnace(final int type) {
        super(type);
    }
    
    public Furnace(final Material type) {
        super(type);
    }
    
    public Furnace(final int type, final byte data) {
        super(type, data);
    }
    
    public Furnace(final Material type, final byte data) {
        super(type, data);
    }
    
    public Furnace clone() {
        return (Furnace)super.clone();
    }
}
