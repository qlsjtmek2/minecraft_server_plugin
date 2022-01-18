// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Button extends SimpleAttachableMaterialData implements Redstone
{
    public Button() {
        super(Material.STONE_BUTTON);
    }
    
    public Button(final int type) {
        super(type);
    }
    
    public Button(final Material type) {
        super(type);
    }
    
    public Button(final int type, final byte data) {
        super(type, data);
    }
    
    public Button(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isPowered() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setPowered(final boolean bool) {
        this.setData((byte)(bool ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
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
            default: {
                return null;
            }
        }
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0x8);
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
        this.setData(data);
    }
    
    public String toString() {
        return super.toString() + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    public Button clone() {
        return (Button)super.clone();
    }
}
