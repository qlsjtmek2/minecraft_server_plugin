// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Gate extends MaterialData implements Directional, Openable
{
    private static final byte OPEN_BIT = 4;
    private static final byte DIR_BIT = 3;
    private static final byte GATE_SOUTH = 0;
    private static final byte GATE_WEST = 1;
    private static final byte GATE_NORTH = 2;
    private static final byte GATE_EAST = 3;
    
    public Gate() {
        super(Material.FENCE_GATE);
    }
    
    public Gate(final int type, final byte data) {
        super(type, data);
    }
    
    public Gate(final byte data) {
        super(Material.FENCE_GATE, data);
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0xFFFFFFFC);
        switch (face) {
            default: {
                data |= 0x0;
                break;
            }
            case SOUTH: {
                data |= 0x1;
                break;
            }
            case WEST: {
                data |= 0x2;
                break;
            }
            case NORTH: {
                data |= 0x3;
                break;
            }
        }
        this.setData(data);
    }
    
    public BlockFace getFacing() {
        switch (this.getData() & 0x3) {
            case 0: {
                return BlockFace.EAST;
            }
            case 1: {
                return BlockFace.SOUTH;
            }
            case 2: {
                return BlockFace.WEST;
            }
            case 3: {
                return BlockFace.NORTH;
            }
            default: {
                return BlockFace.EAST;
            }
        }
    }
    
    public boolean isOpen() {
        return (this.getData() & 0x4) > 0;
    }
    
    public void setOpen(final boolean isOpen) {
        byte data = this.getData();
        if (isOpen) {
            data |= 0x4;
        }
        else {
            data &= 0xFFFFFFFB;
        }
        this.setData(data);
    }
    
    public String toString() {
        return (this.isOpen() ? "OPEN " : "CLOSED ") + " facing and opening " + this.getFacing();
    }
    
    public Gate clone() {
        return (Gate)super.clone();
    }
}
