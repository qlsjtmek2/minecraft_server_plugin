// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Rails extends MaterialData
{
    public Rails() {
        super(Material.RAILS);
    }
    
    public Rails(final int type) {
        super(type);
    }
    
    public Rails(final Material type) {
        super(type);
    }
    
    public Rails(final int type, final byte data) {
        super(type, data);
    }
    
    public Rails(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isOnSlope() {
        final byte d = this.getConvertedData();
        return d == 2 || d == 3 || d == 4 || d == 5;
    }
    
    public boolean isCurve() {
        final byte d = this.getConvertedData();
        return d == 6 || d == 7 || d == 8 || d == 9;
    }
    
    public BlockFace getDirection() {
        final byte d = this.getConvertedData();
        switch (d) {
            default: {
                return BlockFace.SOUTH;
            }
            case 1: {
                return BlockFace.EAST;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.WEST;
            }
            case 4: {
                return BlockFace.NORTH;
            }
            case 5: {
                return BlockFace.SOUTH;
            }
            case 6: {
                return BlockFace.NORTH_WEST;
            }
            case 7: {
                return BlockFace.NORTH_EAST;
            }
            case 8: {
                return BlockFace.SOUTH_EAST;
            }
            case 9: {
                return BlockFace.SOUTH_WEST;
            }
        }
    }
    
    public String toString() {
        return super.toString() + " facing " + this.getDirection() + (this.isCurve() ? " on a curve" : (this.isOnSlope() ? " on a slope" : ""));
    }
    
    protected byte getConvertedData() {
        return this.getData();
    }
    
    public void setDirection(final BlockFace face, final boolean isOnSlope) {
        switch (face) {
            case EAST: {
                this.setData((byte)(isOnSlope ? 2 : 1));
                break;
            }
            case WEST: {
                this.setData((byte)(isOnSlope ? 3 : 1));
                break;
            }
            case NORTH: {
                this.setData((byte)(isOnSlope ? 4 : 0));
                break;
            }
            case SOUTH: {
                this.setData((byte)(isOnSlope ? 5 : 0));
                break;
            }
            case NORTH_WEST: {
                this.setData((byte)6);
                break;
            }
            case NORTH_EAST: {
                this.setData((byte)7);
                break;
            }
            case SOUTH_EAST: {
                this.setData((byte)8);
                break;
            }
            case SOUTH_WEST: {
                this.setData((byte)9);
                break;
            }
        }
    }
    
    public Rails clone() {
        return (Rails)super.clone();
    }
}
