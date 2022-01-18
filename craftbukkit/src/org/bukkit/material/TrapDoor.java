// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class TrapDoor extends SimpleAttachableMaterialData implements Openable
{
    public TrapDoor() {
        super(Material.TRAP_DOOR);
    }
    
    public TrapDoor(final int type) {
        super(type);
    }
    
    public TrapDoor(final Material type) {
        super(type);
    }
    
    public TrapDoor(final int type, final byte data) {
        super(type, data);
    }
    
    public TrapDoor(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isOpen() {
        return (this.getData() & 0x4) == 0x4;
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
    
    public boolean isInverted() {
        return (this.getData() & 0x8) != 0x0;
    }
    
    public void setInverted(final boolean inv) {
        int dat = this.getData() & 0x7;
        if (inv) {
            dat |= 0x8;
        }
        this.setData((byte)dat);
    }
    
    public BlockFace getAttachedFace() {
        final byte data = (byte)(this.getData() & 0x3);
        switch (data) {
            case 0: {
                return BlockFace.SOUTH;
            }
            case 1: {
                return BlockFace.NORTH;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.WEST;
            }
            default: {
                return null;
            }
        }
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0xC);
        switch (face) {
            case SOUTH: {
                data |= 0x1;
                break;
            }
            case WEST: {
                data |= 0x2;
                break;
            }
            case EAST: {
                data |= 0x3;
                break;
            }
        }
        this.setData(data);
    }
    
    public String toString() {
        return (this.isOpen() ? "OPEN " : "CLOSED ") + super.toString() + " with hinges set " + this.getAttachedFace() + (this.isInverted() ? " inverted" : "");
    }
    
    public TrapDoor clone() {
        return (TrapDoor)super.clone();
    }
}
