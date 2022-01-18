// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import java.util.Collection;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import java.util.EnumSet;

public class Vine extends MaterialData
{
    private static final int VINE_NORTH = 4;
    private static final int VINE_EAST = 8;
    private static final int VINE_WEST = 2;
    private static final int VINE_SOUTH = 1;
    EnumSet<BlockFace> possibleFaces;
    
    public Vine() {
        super(Material.VINE);
        this.possibleFaces = EnumSet.of(BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST);
    }
    
    public Vine(final int type, final byte data) {
        super(type, data);
        this.possibleFaces = EnumSet.of(BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST);
    }
    
    public Vine(final byte data) {
        super(Material.VINE, data);
        this.possibleFaces = EnumSet.of(BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST);
    }
    
    public Vine(final BlockFace... faces) {
        this(EnumSet.copyOf(Arrays.asList(faces)));
    }
    
    public Vine(final EnumSet<BlockFace> faces) {
        this((byte)0);
        faces.retainAll(this.possibleFaces);
        byte data = 0;
        if (faces.contains(BlockFace.WEST)) {
            data |= 0x2;
        }
        if (faces.contains(BlockFace.NORTH)) {
            data |= 0x4;
        }
        if (faces.contains(BlockFace.SOUTH)) {
            data |= 0x1;
        }
        if (faces.contains(BlockFace.EAST)) {
            data |= 0x8;
        }
        this.setData(data);
    }
    
    public boolean isOnFace(final BlockFace face) {
        switch (face) {
            case WEST: {
                return (this.getData() & 0x2) == 0x2;
            }
            case NORTH: {
                return (this.getData() & 0x4) == 0x4;
            }
            case SOUTH: {
                return (this.getData() & 0x1) == 0x1;
            }
            case EAST: {
                return (this.getData() & 0x8) == 0x8;
            }
            case NORTH_EAST: {
                return this.isOnFace(BlockFace.EAST) && this.isOnFace(BlockFace.NORTH);
            }
            case NORTH_WEST: {
                return this.isOnFace(BlockFace.WEST) && this.isOnFace(BlockFace.NORTH);
            }
            case SOUTH_EAST: {
                return this.isOnFace(BlockFace.EAST) && this.isOnFace(BlockFace.SOUTH);
            }
            case SOUTH_WEST: {
                return this.isOnFace(BlockFace.WEST) && this.isOnFace(BlockFace.SOUTH);
            }
            case UP: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void putOnFace(final BlockFace face) {
        switch (face) {
            case WEST: {
                this.setData((byte)(this.getData() | 0x2));
                break;
            }
            case NORTH: {
                this.setData((byte)(this.getData() | 0x4));
                break;
            }
            case SOUTH: {
                this.setData((byte)(this.getData() | 0x1));
                break;
            }
            case EAST: {
                this.setData((byte)(this.getData() | 0x8));
                break;
            }
            case NORTH_WEST: {
                this.putOnFace(BlockFace.WEST);
                this.putOnFace(BlockFace.NORTH);
                break;
            }
            case SOUTH_WEST: {
                this.putOnFace(BlockFace.WEST);
                this.putOnFace(BlockFace.SOUTH);
                break;
            }
            case NORTH_EAST: {
                this.putOnFace(BlockFace.EAST);
                this.putOnFace(BlockFace.NORTH);
                break;
            }
            case SOUTH_EAST: {
                this.putOnFace(BlockFace.EAST);
                this.putOnFace(BlockFace.SOUTH);
                break;
            }
            case UP: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Vines can't go on face " + face.toString());
            }
        }
    }
    
    public void removeFromFace(final BlockFace face) {
        switch (face) {
            case WEST: {
                this.setData((byte)(this.getData() & 0xFFFFFFFD));
                break;
            }
            case NORTH: {
                this.setData((byte)(this.getData() & 0xFFFFFFFB));
                break;
            }
            case SOUTH: {
                this.setData((byte)(this.getData() & 0xFFFFFFFE));
                break;
            }
            case EAST: {
                this.setData((byte)(this.getData() & 0xFFFFFFF7));
                break;
            }
            case NORTH_WEST: {
                this.removeFromFace(BlockFace.WEST);
                this.removeFromFace(BlockFace.NORTH);
                break;
            }
            case SOUTH_WEST: {
                this.removeFromFace(BlockFace.WEST);
                this.removeFromFace(BlockFace.SOUTH);
                break;
            }
            case NORTH_EAST: {
                this.removeFromFace(BlockFace.EAST);
                this.removeFromFace(BlockFace.NORTH);
                break;
            }
            case SOUTH_EAST: {
                this.removeFromFace(BlockFace.EAST);
                this.removeFromFace(BlockFace.SOUTH);
                break;
            }
            case UP: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Vines can't go on face " + face.toString());
            }
        }
    }
    
    public String toString() {
        return "VINE";
    }
    
    public Vine clone() {
        return (Vine)super.clone();
    }
}
