// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Pumpkin extends MaterialData implements Directional
{
    public Pumpkin() {
        super(Material.PUMPKIN);
    }
    
    public Pumpkin(final BlockFace direction) {
        this();
        this.setFacingDirection(direction);
    }
    
    public Pumpkin(final int type) {
        super(type);
    }
    
    public Pumpkin(final Material type) {
        super(type);
    }
    
    public Pumpkin(final int type, final byte data) {
        super(type, data);
    }
    
    public Pumpkin(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isLit() {
        return this.getItemType() == Material.JACK_O_LANTERN;
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = 0;
        switch (face) {
            case NORTH: {
                data = 0;
                break;
            }
            case EAST: {
                data = 1;
                break;
            }
            case SOUTH: {
                data = 2;
                break;
            }
            default: {
                data = 3;
                break;
            }
        }
        this.setData(data);
    }
    
    public BlockFace getFacing() {
        final byte data = this.getData();
        switch (data) {
            case 0: {
                return BlockFace.NORTH;
            }
            case 1: {
                return BlockFace.EAST;
            }
            case 2: {
                return BlockFace.SOUTH;
            }
            default: {
                return BlockFace.EAST;
            }
        }
    }
    
    public String toString() {
        return super.toString() + " facing " + this.getFacing() + " " + (this.isLit() ? "" : "NOT ") + "LIT";
    }
    
    public Pumpkin clone() {
        return (Pumpkin)super.clone();
    }
}
