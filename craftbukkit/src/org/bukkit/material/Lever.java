// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Lever extends SimpleAttachableMaterialData implements Redstone
{
    public Lever() {
        super(Material.LEVER);
    }
    
    public Lever(final int type) {
        super(type);
    }
    
    public Lever(final Material type) {
        super(type);
    }
    
    public Lever(final int type, final byte data) {
        super(type, data);
    }
    
    public Lever(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isPowered() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setPowered(final boolean isPowered) {
        this.setData((byte)(isPowered ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    public BlockFace getAttachedFace() {
        final byte data = (byte)(this.getData() & 0x7);
        switch (data) {
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.NORTH;
            }
            case 4: {
                return BlockFace.SOUTH;
            }
            case 5:
            case 6: {
                return BlockFace.DOWN;
            }
            case 0:
            case 7: {
                return BlockFace.UP;
            }
            default: {
                return null;
            }
        }
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0x8);
        final BlockFace attach = this.getAttachedFace();
        if (attach == BlockFace.DOWN) {
            switch (face) {
                case SOUTH:
                case NORTH: {
                    data |= 0x5;
                    break;
                }
                case EAST:
                case WEST: {
                    data |= 0x6;
                    break;
                }
            }
        }
        else if (attach == BlockFace.UP) {
            switch (face) {
                case SOUTH:
                case NORTH: {
                    data |= 0x7;
                    break;
                }
                case EAST:
                case WEST: {
                    data |= 0x0;
                    break;
                }
            }
        }
        else {
            switch (face) {
                case EAST: {
                    data |= 0x1;
                    break;
                }
                case WEST: {
                    data |= 0x2;
                    break;
                }
                case SOUTH: {
                    data |= 0x3;
                    break;
                }
                case NORTH: {
                    data |= 0x4;
                    break;
                }
            }
        }
        this.setData(data);
    }
    
    public String toString() {
        return super.toString() + " facing " + this.getFacing() + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    public Lever clone() {
        return (Lever)super.clone();
    }
}
