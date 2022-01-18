// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Torch extends SimpleAttachableMaterialData
{
    public Torch() {
        super(Material.TORCH);
    }
    
    public Torch(final int type) {
        super(type);
    }
    
    public Torch(final Material type) {
        super(type);
    }
    
    public Torch(final int type, final byte data) {
        super(type, data);
    }
    
    public Torch(final Material type, final byte data) {
        super(type, data);
    }
    
    public BlockFace getAttachedFace() {
        final byte data = this.getData();
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
                return BlockFace.DOWN;
            }
        }
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = 0;
        switch (face) {
            case EAST: {
                data = 1;
                break;
            }
            case WEST: {
                data = 2;
                break;
            }
            case SOUTH: {
                data = 3;
                break;
            }
            case NORTH: {
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
    
    public Torch clone() {
        return (Torch)super.clone();
    }
}
