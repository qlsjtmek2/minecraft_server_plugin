// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityHanging;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Hanging;

public class CraftHanging extends CraftEntity implements Hanging
{
    public CraftHanging(final CraftServer server, final EntityHanging entity) {
        super(server, entity);
    }
    
    public BlockFace getAttachedFace() {
        return this.getFacing().getOppositeFace();
    }
    
    public void setFacingDirection(final BlockFace face) {
        this.setFacingDirection(face, false);
    }
    
    public boolean setFacingDirection(final BlockFace face, final boolean force) {
        final Block block = this.getLocation().getBlock().getRelative(this.getAttachedFace()).getRelative(face.getOppositeFace()).getRelative(this.getFacing());
        final EntityHanging hanging = this.getHandle();
        final int x = hanging.x;
        final int y = hanging.y;
        final int z = hanging.z;
        final int dir = hanging.direction;
        hanging.x = block.getX();
        hanging.y = block.getY();
        hanging.z = block.getZ();
        switch (face) {
            default: {
                this.getHandle().setDirection(0);
                break;
            }
            case WEST: {
                this.getHandle().setDirection(1);
                break;
            }
            case NORTH: {
                this.getHandle().setDirection(2);
                break;
            }
            case EAST: {
                this.getHandle().setDirection(3);
                break;
            }
        }
        if (!force && !hanging.survives()) {
            hanging.x = x;
            hanging.y = y;
            hanging.z = z;
            hanging.setDirection(dir);
            return false;
        }
        return true;
    }
    
    public BlockFace getFacing() {
        switch (this.getHandle().direction) {
            default: {
                return BlockFace.SOUTH;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.EAST;
            }
        }
    }
    
    public EntityHanging getHandle() {
        return (EntityHanging)this.entity;
    }
    
    public String toString() {
        return "CraftHanging";
    }
    
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
