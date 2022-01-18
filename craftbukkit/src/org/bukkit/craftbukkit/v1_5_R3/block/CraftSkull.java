// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import com.google.common.base.Strings;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Block;
import org.bukkit.SkullType;
import net.minecraft.server.v1_5_R3.TileEntitySkull;
import org.bukkit.block.Skull;

public class CraftSkull extends CraftBlockState implements Skull
{
    private final TileEntitySkull skull;
    private String player;
    private SkullType skullType;
    private byte rotation;
    private final int MAX_OWNER_LENGTH = 16;
    
    public CraftSkull(final Block block) {
        super(block);
        final CraftWorld world = (CraftWorld)block.getWorld();
        this.skull = (TileEntitySkull)world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
        this.player = this.skull.getExtraType();
        this.skullType = getSkullType(this.skull.getSkullType());
        this.rotation = (byte)this.skull.getRotation();
    }
    
    static SkullType getSkullType(final int id) {
        switch (id) {
            case 0: {
                return SkullType.SKELETON;
            }
            case 1: {
                return SkullType.WITHER;
            }
            case 2: {
                return SkullType.ZOMBIE;
            }
            case 3: {
                return SkullType.PLAYER;
            }
            case 4: {
                return SkullType.CREEPER;
            }
            default: {
                throw new AssertionError(id);
            }
        }
    }
    
    static int getSkullType(final SkullType type) {
        switch (type) {
            case SKELETON: {
                return 0;
            }
            case WITHER: {
                return 1;
            }
            case ZOMBIE: {
                return 2;
            }
            case PLAYER: {
                return 3;
            }
            case CREEPER: {
                return 4;
            }
            default: {
                throw new AssertionError(type);
            }
        }
    }
    
    static byte getBlockFace(final BlockFace rotation) {
        switch (rotation) {
            case NORTH: {
                return 0;
            }
            case NORTH_NORTH_EAST: {
                return 1;
            }
            case NORTH_EAST: {
                return 2;
            }
            case EAST_NORTH_EAST: {
                return 3;
            }
            case EAST: {
                return 4;
            }
            case EAST_SOUTH_EAST: {
                return 5;
            }
            case SOUTH_EAST: {
                return 6;
            }
            case SOUTH_SOUTH_EAST: {
                return 7;
            }
            case SOUTH: {
                return 8;
            }
            case SOUTH_SOUTH_WEST: {
                return 9;
            }
            case SOUTH_WEST: {
                return 10;
            }
            case WEST_SOUTH_WEST: {
                return 11;
            }
            case WEST: {
                return 12;
            }
            case WEST_NORTH_WEST: {
                return 13;
            }
            case NORTH_WEST: {
                return 14;
            }
            case NORTH_NORTH_WEST: {
                return 15;
            }
            default: {
                throw new IllegalArgumentException("Invalid BlockFace rotation: " + rotation);
            }
        }
    }
    
    static BlockFace getBlockFace(final byte rotation) {
        switch (rotation) {
            case 0: {
                return BlockFace.NORTH;
            }
            case 1: {
                return BlockFace.NORTH_NORTH_EAST;
            }
            case 2: {
                return BlockFace.NORTH_EAST;
            }
            case 3: {
                return BlockFace.EAST_NORTH_EAST;
            }
            case 4: {
                return BlockFace.EAST;
            }
            case 5: {
                return BlockFace.EAST_SOUTH_EAST;
            }
            case 6: {
                return BlockFace.SOUTH_EAST;
            }
            case 7: {
                return BlockFace.SOUTH_SOUTH_EAST;
            }
            case 8: {
                return BlockFace.SOUTH;
            }
            case 9: {
                return BlockFace.SOUTH_SOUTH_WEST;
            }
            case 10: {
                return BlockFace.SOUTH_WEST;
            }
            case 11: {
                return BlockFace.WEST_SOUTH_WEST;
            }
            case 12: {
                return BlockFace.WEST;
            }
            case 13: {
                return BlockFace.WEST_NORTH_WEST;
            }
            case 14: {
                return BlockFace.NORTH_WEST;
            }
            case 15: {
                return BlockFace.NORTH_NORTH_WEST;
            }
            default: {
                throw new AssertionError(rotation);
            }
        }
    }
    
    public boolean hasOwner() {
        return !Strings.isNullOrEmpty(this.player);
    }
    
    public String getOwner() {
        return this.player;
    }
    
    public boolean setOwner(final String name) {
        if (name == null || name.length() > 16) {
            return false;
        }
        this.player = name;
        if (this.skullType != SkullType.PLAYER) {
            this.skullType = SkullType.PLAYER;
        }
        return true;
    }
    
    public BlockFace getRotation() {
        return getBlockFace(this.rotation);
    }
    
    public void setRotation(final BlockFace rotation) {
        this.rotation = getBlockFace(rotation);
    }
    
    public SkullType getSkullType() {
        return this.skullType;
    }
    
    public void setSkullType(final SkullType skullType) {
        this.skullType = skullType;
        if (skullType != SkullType.PLAYER) {
            this.player = "";
        }
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.skull.setSkullType(getSkullType(this.skullType), this.player);
            this.skull.setRotation(this.rotation);
            this.skull.update();
        }
        return result;
    }
}
