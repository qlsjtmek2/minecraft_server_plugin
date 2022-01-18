// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class CocoaPlant extends MaterialData implements Directional, Attachable
{
    public CocoaPlant() {
        super(Material.COCOA);
    }
    
    public CocoaPlant(final int type) {
        super(type);
    }
    
    public CocoaPlant(final int type, final byte data) {
        super(type, data);
    }
    
    public CocoaPlant(final CocoaPlantSize sz) {
        this();
        this.setSize(sz);
    }
    
    public CocoaPlant(final CocoaPlantSize sz, final BlockFace dir) {
        this();
        this.setSize(sz);
        this.setFacingDirection(dir);
    }
    
    public CocoaPlantSize getSize() {
        switch (this.getData() & 0xC) {
            case 0: {
                return CocoaPlantSize.SMALL;
            }
            case 4: {
                return CocoaPlantSize.MEDIUM;
            }
            default: {
                return CocoaPlantSize.LARGE;
            }
        }
    }
    
    public void setSize(final CocoaPlantSize sz) {
        int dat = this.getData() & 0x3;
        switch (sz) {
            case MEDIUM: {
                dat |= 0x4;
                break;
            }
            case LARGE: {
                dat |= 0x8;
                break;
            }
        }
        this.setData((byte)dat);
    }
    
    public BlockFace getAttachedFace() {
        return this.getFacing().getOppositeFace();
    }
    
    public void setFacingDirection(final BlockFace face) {
        int dat = this.getData() & 0xC;
        switch (face) {
            case WEST: {
                dat |= 0x1;
                break;
            }
            case NORTH: {
                dat |= 0x2;
                break;
            }
            case EAST: {
                dat |= 0x3;
                break;
            }
        }
        this.setData((byte)dat);
    }
    
    public BlockFace getFacing() {
        switch (this.getData() & 0x3) {
            case 0: {
                return BlockFace.SOUTH;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.EAST;
            }
            default: {
                return null;
            }
        }
    }
    
    public CocoaPlant clone() {
        return (CocoaPlant)super.clone();
    }
    
    public String toString() {
        return super.toString() + " facing " + this.getFacing() + " " + this.getSize();
    }
    
    public enum CocoaPlantSize
    {
        SMALL, 
        MEDIUM, 
        LARGE;
    }
}
