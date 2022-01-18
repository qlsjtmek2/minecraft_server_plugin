// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

@Deprecated
public class Door extends MaterialData implements Directional, Openable
{
    public Door() {
        super(Material.WOODEN_DOOR);
    }
    
    public Door(final int type) {
        super(type);
    }
    
    public Door(final Material type) {
        super(type);
    }
    
    public Door(final int type, final byte data) {
        super(type, data);
    }
    
    public Door(final Material type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public boolean isOpen() {
        return (this.getData() & 0x4) == 0x4;
    }
    
    @Deprecated
    public void setOpen(final boolean isOpen) {
        this.setData((byte)(isOpen ? (this.getData() | 0x4) : (this.getData() & 0xFFFFFFFB)));
    }
    
    public boolean isTopHalf() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    @Deprecated
    public void setTopHalf(final boolean isTopHalf) {
        this.setData((byte)(isTopHalf ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    @Deprecated
    public BlockFace getHingeCorner() {
        final byte d = this.getData();
        if ((d & 0x3) == 0x3) {
            return BlockFace.NORTH_WEST;
        }
        if ((d & 0x1) == 0x1) {
            return BlockFace.SOUTH_EAST;
        }
        if ((d & 0x2) == 0x2) {
            return BlockFace.SOUTH_WEST;
        }
        return BlockFace.NORTH_EAST;
    }
    
    public String toString() {
        return (this.isTopHalf() ? "TOP" : "BOTTOM") + " half of " + super.toString();
    }
    
    @Deprecated
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0x12);
        switch (face) {
            case NORTH: {
                data |= 0x1;
                break;
            }
            case EAST: {
                data |= 0x2;
                break;
            }
            case SOUTH: {
                data |= 0x3;
                break;
            }
        }
        this.setData(data);
    }
    
    @Deprecated
    public BlockFace getFacing() {
        final byte data = (byte)(this.getData() & 0x3);
        switch (data) {
            case 0: {
                return BlockFace.WEST;
            }
            case 1: {
                return BlockFace.NORTH;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
            default: {
                return null;
            }
        }
    }
    
    public Door clone() {
        return (Door)super.clone();
    }
}
