// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Ladder extends SimpleAttachableMaterialData
{
    public Ladder() {
        super(Material.LADDER);
    }
    
    public Ladder(final int type) {
        super(type);
    }
    
    public Ladder(final Material type) {
        super(type);
    }
    
    public Ladder(final int type, final byte data) {
        super(type, data);
    }
    
    public Ladder(final Material type, final byte data) {
        super(type, data);
    }
    
    public BlockFace getAttachedFace() {
        final byte data = this.getData();
        switch (data) {
            case 2: {
                return BlockFace.SOUTH;
            }
            case 3: {
                return BlockFace.NORTH;
            }
            case 4: {
                return BlockFace.EAST;
            }
            case 5: {
                return BlockFace.WEST;
            }
            default: {
                return null;
            }
        }
    }
    
    public void setFacingDirection(final BlockFace face) {
        byte data = 0;
        switch (face) {
            case SOUTH: {
                data = 2;
                break;
            }
            case NORTH: {
                data = 3;
                break;
            }
            case EAST: {
                data = 4;
                break;
            }
            case WEST: {
                data = 5;
                break;
            }
        }
        this.setData(data);
    }
    
    public Ladder clone() {
        return (Ladder)super.clone();
    }
}
