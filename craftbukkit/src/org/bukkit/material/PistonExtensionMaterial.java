// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class PistonExtensionMaterial extends MaterialData implements Attachable
{
    public PistonExtensionMaterial(final int type) {
        super(type);
    }
    
    public PistonExtensionMaterial(final Material type) {
        super(type);
    }
    
    public PistonExtensionMaterial(final int type, final byte data) {
        super(type, data);
    }
    
    public PistonExtensionMaterial(final Material type, final byte data) {
        super(type, data);
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0x8);
        switch (face) {
            case UP: {
                data |= 0x1;
                break;
            }
            case NORTH: {
                data |= 0x2;
                break;
            }
            case SOUTH: {
                data |= 0x3;
                break;
            }
            case WEST: {
                data |= 0x4;
                break;
            }
            case EAST: {
                data |= 0x5;
                break;
            }
        }
        this.setData(data);
    }
    
    public BlockFace getFacing() {
        final byte dir = (byte)(this.getData() & 0x7);
        switch (dir) {
            case 0: {
                return BlockFace.DOWN;
            }
            case 1: {
                return BlockFace.UP;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
            case 4: {
                return BlockFace.WEST;
            }
            case 5: {
                return BlockFace.EAST;
            }
            default: {
                return BlockFace.SELF;
            }
        }
    }
    
    public boolean isSticky() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setSticky(final boolean sticky) {
        this.setData((byte)(sticky ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    public BlockFace getAttachedFace() {
        return this.getFacing().getOppositeFace();
    }
    
    public PistonExtensionMaterial clone() {
        return (PistonExtensionMaterial)super.clone();
    }
}
