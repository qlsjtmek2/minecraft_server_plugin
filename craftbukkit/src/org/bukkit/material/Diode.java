// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Diode extends MaterialData implements Directional
{
    public Diode() {
        super(Material.DIODE_BLOCK_ON);
    }
    
    public Diode(final int type) {
        super(type);
    }
    
    public Diode(final Material type) {
        super(type);
    }
    
    public Diode(final int type, final byte data) {
        super(type, data);
    }
    
    public Diode(final Material type, final byte data) {
        super(type, data);
    }
    
    public void setDelay(int delay) {
        if (delay > 4) {
            delay = 4;
        }
        if (delay < 1) {
            delay = 1;
        }
        final byte newData = (byte)(this.getData() & 0x3);
        this.setData((byte)(newData | delay - 1 << 2));
    }
    
    public int getDelay() {
        return (this.getData() >> 2) + 1;
    }
    
    public void setFacingDirection(final BlockFace face) {
        final int delay = this.getDelay();
        byte data = 0;
        switch (face) {
            case EAST: {
                data = 1;
                break;
            }
            case SOUTH: {
                data = 2;
                break;
            }
            case WEST: {
                data = 3;
                break;
            }
            default: {
                data = 0;
                break;
            }
        }
        this.setData(data);
        this.setDelay(delay);
    }
    
    public BlockFace getFacing() {
        final byte data = (byte)(this.getData() & 0x3);
        switch (data) {
            default: {
                return BlockFace.NORTH;
            }
            case 1: {
                return BlockFace.EAST;
            }
            case 2: {
                return BlockFace.SOUTH;
            }
            case 3: {
                return BlockFace.WEST;
            }
        }
    }
    
    public String toString() {
        return super.toString() + " facing " + this.getFacing() + " with " + this.getDelay() + " ticks delay";
    }
    
    public Diode clone() {
        return (Diode)super.clone();
    }
}
