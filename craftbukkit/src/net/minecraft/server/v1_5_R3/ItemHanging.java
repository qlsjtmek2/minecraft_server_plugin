// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.entity.Painting;
import org.bukkit.event.Event;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.entity.Hanging;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;
import org.bukkit.entity.Player;

public class ItemHanging extends Item
{
    private final Class a;
    
    public ItemHanging(final int i, final Class oclass) {
        super(i);
        this.a = oclass;
        this.a(CreativeModeTab.c);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        if (l == 0) {
            return false;
        }
        if (l == 1) {
            return false;
        }
        final int i2 = Direction.e[l];
        final EntityHanging entityhanging = this.a(world, i, j, k, i2);
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        if (entityhanging != null && entityhanging.survives()) {
            if (!world.isStatic) {
                final Player who = (entityhuman == null) ? null : ((Player)entityhuman.getBukkitEntity());
                final Block blockClicked = world.getWorld().getBlockAt(i, j, k);
                final BlockFace blockFace = CraftBlock.notchToBlockFace(l);
                final HangingPlaceEvent event = new HangingPlaceEvent((Hanging)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
                world.getServer().getPluginManager().callEvent(event);
                PaintingPlaceEvent paintingEvent = null;
                if (entityhanging instanceof EntityPainting) {
                    paintingEvent = new PaintingPlaceEvent((Painting)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
                    paintingEvent.setCancelled(event.isCancelled());
                    world.getServer().getPluginManager().callEvent(paintingEvent);
                }
                if (event.isCancelled() || (paintingEvent != null && paintingEvent.isCancelled())) {
                    return false;
                }
                world.addEntity(entityhanging);
            }
            --itemstack.count;
        }
        return true;
    }
    
    private EntityHanging a(final World world, final int i, final int j, final int k, final int l) {
        return (this.a == EntityPainting.class) ? new EntityPainting(world, i, j, k, l) : ((this.a == EntityItemFrame.class) ? new EntityItemFrame(world, i, j, k, l) : null);
    }
}
