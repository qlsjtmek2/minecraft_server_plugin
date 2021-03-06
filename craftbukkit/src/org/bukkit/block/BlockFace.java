// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

public enum BlockFace
{
    NORTH(0, 0, -1), 
    EAST(1, 0, 0), 
    SOUTH(0, 0, 1), 
    WEST(-1, 0, 0), 
    UP(0, 1, 0), 
    DOWN(0, -1, 0), 
    NORTH_EAST(BlockFace.NORTH, BlockFace.EAST), 
    NORTH_WEST(BlockFace.NORTH, BlockFace.WEST), 
    SOUTH_EAST(BlockFace.SOUTH, BlockFace.EAST), 
    SOUTH_WEST(BlockFace.SOUTH, BlockFace.WEST), 
    WEST_NORTH_WEST(BlockFace.WEST, BlockFace.NORTH_WEST), 
    NORTH_NORTH_WEST(BlockFace.NORTH, BlockFace.NORTH_WEST), 
    NORTH_NORTH_EAST(BlockFace.NORTH, BlockFace.NORTH_EAST), 
    EAST_NORTH_EAST(BlockFace.EAST, BlockFace.NORTH_EAST), 
    EAST_SOUTH_EAST(BlockFace.EAST, BlockFace.SOUTH_EAST), 
    SOUTH_SOUTH_EAST(BlockFace.SOUTH, BlockFace.SOUTH_EAST), 
    SOUTH_SOUTH_WEST(BlockFace.SOUTH, BlockFace.SOUTH_WEST), 
    WEST_SOUTH_WEST(BlockFace.WEST, BlockFace.SOUTH_WEST), 
    SELF(0, 0, 0);
    
    private final int modX;
    private final int modY;
    private final int modZ;
    
    private BlockFace(final int modX, final int modY, final int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }
    
    private BlockFace(final BlockFace face1, final BlockFace face2) {
        this.modX = face1.getModX() + face2.getModX();
        this.modY = face1.getModY() + face2.getModY();
        this.modZ = face1.getModZ() + face2.getModZ();
    }
    
    public int getModX() {
        return this.modX;
    }
    
    public int getModY() {
        return this.modY;
    }
    
    public int getModZ() {
        return this.modZ;
    }
    
    public BlockFace getOppositeFace() {
        switch (this) {
            case NORTH: {
                return BlockFace.SOUTH;
            }
            case SOUTH: {
                return BlockFace.NORTH;
            }
            case EAST: {
                return BlockFace.WEST;
            }
            case WEST: {
                return BlockFace.EAST;
            }
            case UP: {
                return BlockFace.DOWN;
            }
            case DOWN: {
                return BlockFace.UP;
            }
            case NORTH_EAST: {
                return BlockFace.SOUTH_WEST;
            }
            case NORTH_WEST: {
                return BlockFace.SOUTH_EAST;
            }
            case SOUTH_EAST: {
                return BlockFace.NORTH_WEST;
            }
            case SOUTH_WEST: {
                return BlockFace.NORTH_EAST;
            }
            case WEST_NORTH_WEST: {
                return BlockFace.EAST_SOUTH_EAST;
            }
            case NORTH_NORTH_WEST: {
                return BlockFace.SOUTH_SOUTH_EAST;
            }
            case NORTH_NORTH_EAST: {
                return BlockFace.SOUTH_SOUTH_WEST;
            }
            case EAST_NORTH_EAST: {
                return BlockFace.WEST_SOUTH_WEST;
            }
            case EAST_SOUTH_EAST: {
                return BlockFace.WEST_NORTH_WEST;
            }
            case SOUTH_SOUTH_EAST: {
                return BlockFace.NORTH_NORTH_WEST;
            }
            case SOUTH_SOUTH_WEST: {
                return BlockFace.NORTH_NORTH_EAST;
            }
            case WEST_SOUTH_WEST: {
                return BlockFace.EAST_NORTH_EAST;
            }
            case SELF: {
                return BlockFace.SELF;
            }
            default: {
                return BlockFace.SELF;
            }
        }
    }
}
