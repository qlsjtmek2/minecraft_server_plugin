// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.WorldServer;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.BlockFace;
import net.minecraft.server.v1_5_R3.EnumArt;
import org.bukkit.craftbukkit.v1_5_R3.CraftArt;
import org.bukkit.Art;
import net.minecraft.server.v1_5_R3.EntityHanging;
import net.minecraft.server.v1_5_R3.EntityPainting;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Painting;

public class CraftPainting extends CraftHanging implements Painting
{
    public CraftPainting(final CraftServer server, final EntityPainting entity) {
        super(server, entity);
    }
    
    public Art getArt() {
        final EnumArt art = this.getHandle().art;
        return CraftArt.NotchToBukkit(art);
    }
    
    public boolean setArt(final Art art) {
        return this.setArt(art, false);
    }
    
    public boolean setArt(final Art art, final boolean force) {
        final EntityPainting painting = this.getHandle();
        final EnumArt oldArt = painting.art;
        painting.art = CraftArt.BukkitToNotch(art);
        painting.setDirection(painting.direction);
        if (!force && !painting.survives()) {
            painting.art = oldArt;
            painting.setDirection(painting.direction);
            return false;
        }
        this.update();
        return true;
    }
    
    public boolean setFacingDirection(final BlockFace face, final boolean force) {
        if (super.setFacingDirection(face, force)) {
            this.update();
            return true;
        }
        return false;
    }
    
    private void update() {
        final WorldServer world = ((CraftWorld)this.getWorld()).getHandle();
        final EntityPainting painting = new EntityPainting(world);
        painting.x = this.getHandle().x;
        painting.y = this.getHandle().y;
        painting.z = this.getHandle().z;
        painting.art = this.getHandle().art;
        painting.setDirection(this.getHandle().direction);
        this.getHandle().die();
        this.getHandle().velocityChanged = true;
        world.addEntity(painting);
        this.entity = painting;
    }
    
    public EntityPainting getHandle() {
        return (EntityPainting)this.entity;
    }
    
    public String toString() {
        return "CraftPainting{art=" + this.getArt() + "}";
    }
    
    public EntityType getType() {
        return EntityType.PAINTING;
    }
}
