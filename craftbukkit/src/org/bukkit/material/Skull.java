// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Skull extends MaterialData implements Directional
{
    public Skull() {
        super(Material.SKULL);
    }
    
    public Skull(final BlockFace direction) {
        this();
        this.setFacingDirection(direction);
    }
    
    public Skull(final int type) {
        super(type);
    }
    
    public Skull(final Material type) {
        super(type);
    }
    
    public Skull(final int type, final byte data) {
        super(type, data);
    }
    
    public Skull(final Material type, final byte data) {
        super(type, data);
    }
    
    public void setFacingDirection(final BlockFace face) {
        int data = 0;
        switch (face) {
            default: {
                data = 1;
                break;
            }
            case NORTH: {
                data = 2;
                break;
            }
            case EAST: {
                data = 4;
                break;
            }
            case SOUTH: {
                data = 3;
                break;
            }
            case WEST: {
                data = 5;
                break;
            }
        }
        this.setData((byte)data);
    }
    
    public BlockFace getFacing() {
        final int data = this.getData();
        switch (data) {
            default: {
                return BlockFace.SELF;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
            case 4: {
                return BlockFace.EAST;
            }
            case 5: {
                return BlockFace.WEST;
            }
        }
    }
    
    public String toString() {
        return super.toString() + " facing " + this.getFacing();
    }
    
    public Skull clone() {
        return (Skull)super.clone();
    }
}
