// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Dispenser extends FurnaceAndDispenser
{
    public Dispenser() {
        super(Material.DISPENSER);
    }
    
    public Dispenser(final BlockFace direction) {
        this();
        this.setFacingDirection(direction);
    }
    
    public Dispenser(final int type) {
        super(type);
    }
    
    public Dispenser(final Material type) {
        super(type);
    }
    
    public Dispenser(final int type, final byte data) {
        super(type, data);
    }
    
    public Dispenser(final Material type, final byte data) {
        super(type, data);
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = 0;
        switch (face) {
            case DOWN: {
                data = 0;
                break;
            }
            case UP: {
                data = 1;
                break;
            }
            case NORTH: {
                data = 2;
                break;
            }
            case SOUTH: {
                data = 3;
                break;
            }
            case WEST: {
                data = 4;
                break;
            }
            default: {
                data = 5;
                break;
            }
        }
        this.setData(data);
    }
    
    public BlockFace getFacing() {
        final int data = this.getData() & 0x7;
        switch (data) {
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
            default: {
                return BlockFace.EAST;
            }
        }
    }
    
    public Dispenser clone() {
        return (Dispenser)super.clone();
    }
}
