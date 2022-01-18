// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import java.util.EnumSet;
import java.util.Set;
import org.bukkit.block.BlockFace;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;

public class Mushroom extends MaterialData
{
    private static final byte SHROOM_NONE = 0;
    private static final byte SHROOM_STEM = 10;
    private static final byte NORTH_LIMIT = 4;
    private static final byte SOUTH_LIMIT = 6;
    private static final byte EAST_WEST_LIMIT = 3;
    private static final byte EAST_REMAINDER = 0;
    private static final byte WEST_REMAINDER = 1;
    private static final byte NORTH_SOUTH_MOD = 3;
    private static final byte EAST_WEST_MOD = 1;
    
    public Mushroom(final Material shroom) {
        super(shroom);
        Validate.isTrue(shroom == Material.HUGE_MUSHROOM_1 || shroom == Material.HUGE_MUSHROOM_2, "Not a mushroom!");
    }
    
    public Mushroom(final Material shroom, final byte data) {
        super(shroom, data);
        Validate.isTrue(shroom == Material.HUGE_MUSHROOM_1 || shroom == Material.HUGE_MUSHROOM_2, "Not a mushroom!");
    }
    
    public Mushroom(final int type, final byte data) {
        super(type, data);
        Validate.isTrue(type == Material.HUGE_MUSHROOM_1.getId() || type == Material.HUGE_MUSHROOM_2.getId(), "Not a mushroom!");
    }
    
    public boolean isStem() {
        return this.getData() == 10;
    }
    
    public void setStem() {
        this.setData((byte)10);
    }
    
    public boolean isFacePainted(final BlockFace face) {
        final byte data = this.getData();
        if (data == 0 || data == 10) {
            return false;
        }
        switch (face) {
            case WEST: {
                return data < 4;
            }
            case EAST: {
                return data > 6;
            }
            case NORTH: {
                return data % 3 == 0;
            }
            case SOUTH: {
                return data % 3 == 1;
            }
            case UP: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void setFacePainted(final BlockFace face, final boolean painted) {
        if (painted == this.isFacePainted(face)) {
            return;
        }
        byte data = this.getData();
        if (data == 10) {
            data = 5;
        }
        switch (face) {
            case WEST: {
                if (painted) {
                    data -= 3;
                    break;
                }
                data += 3;
                break;
            }
            case EAST: {
                if (painted) {
                    data += 3;
                    break;
                }
                data -= 3;
                break;
            }
            case NORTH: {
                if (painted) {
                    ++data;
                    break;
                }
                --data;
                break;
            }
            case SOUTH: {
                if (painted) {
                    --data;
                    break;
                }
                ++data;
                break;
            }
            case UP: {
                if (!painted) {
                    data = 0;
                    break;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Can't paint that face of a mushroom!");
            }
        }
        this.setData(data);
    }
    
    public Set<BlockFace> getPaintedFaces() {
        final EnumSet<BlockFace> faces = EnumSet.noneOf(BlockFace.class);
        if (this.isFacePainted(BlockFace.WEST)) {
            faces.add(BlockFace.WEST);
        }
        if (this.isFacePainted(BlockFace.NORTH)) {
            faces.add(BlockFace.NORTH);
        }
        if (this.isFacePainted(BlockFace.SOUTH)) {
            faces.add(BlockFace.SOUTH);
        }
        if (this.isFacePainted(BlockFace.EAST)) {
            faces.add(BlockFace.EAST);
        }
        if (this.isFacePainted(BlockFace.UP)) {
            faces.add(BlockFace.UP);
        }
        return faces;
    }
    
    public String toString() {
        return Material.getMaterial(this.getItemTypeId()).toString() + (this.isStem() ? "{STEM}" : this.getPaintedFaces());
    }
    
    public Mushroom clone() {
        return (Mushroom)super.clone();
    }
}
