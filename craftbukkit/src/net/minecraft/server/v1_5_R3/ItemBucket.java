// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class ItemBucket extends Item
{
    private int a;
    
    public ItemBucket(final int i, final int j) {
        super(i);
        this.maxStackSize = 1;
        this.a = j;
        this.a(CreativeModeTab.f);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        final float f = 1.0f;
        final double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
        final double d2 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + 1.62 - entityhuman.height;
        final double d3 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
        final boolean flag = this.a == 0;
        final MovingObjectPosition movingobjectposition = this.a(world, entityhuman, flag);
        if (movingobjectposition == null) {
            return itemstack;
        }
        if (movingobjectposition.type == EnumMovingObjectType.TILE) {
            int i = movingobjectposition.b;
            int j = movingobjectposition.c;
            int k = movingobjectposition.d;
            if (!world.a(entityhuman, i, j, k)) {
                return itemstack;
            }
            if (this.a == 0) {
                if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
                    return itemstack;
                }
                if (world.getMaterial(i, j, k) == Material.WATER && world.getData(i, j, k) == 0) {
                    final PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Item.WATER_BUCKET);
                    if (event.isCancelled()) {
                        return itemstack;
                    }
                    world.setAir(i, j, k);
                    if (entityhuman.abilities.canInstantlyBuild) {
                        return itemstack;
                    }
                    final ItemStack result = CraftItemStack.asNMSCopy(event.getItemStack());
                    if (--itemstack.count <= 0) {
                        return result;
                    }
                    if (!entityhuman.inventory.pickup(result)) {
                        entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()));
                    }
                    return itemstack;
                }
                else if (world.getMaterial(i, j, k) == Material.LAVA && world.getData(i, j, k) == 0) {
                    final PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Item.LAVA_BUCKET);
                    if (event.isCancelled()) {
                        return itemstack;
                    }
                    world.setAir(i, j, k);
                    if (entityhuman.abilities.canInstantlyBuild) {
                        return itemstack;
                    }
                    final ItemStack result = CraftItemStack.asNMSCopy(event.getItemStack());
                    if (--itemstack.count <= 0) {
                        return result;
                    }
                    if (!entityhuman.inventory.pickup(result)) {
                        entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()));
                    }
                    return itemstack;
                }
            }
            else if (this.a < 0) {
                final PlayerBucketEmptyEvent event2 = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, i, j, k, movingobjectposition.face, itemstack);
                if (event2.isCancelled()) {
                    return itemstack;
                }
                return CraftItemStack.asNMSCopy(event2.getItemStack());
            }
            else {
                final int clickedX = i;
                final int clickedY = j;
                final int clickedZ = k;
                if (movingobjectposition.face == 0) {
                    --j;
                }
                if (movingobjectposition.face == 1) {
                    ++j;
                }
                if (movingobjectposition.face == 2) {
                    --k;
                }
                if (movingobjectposition.face == 3) {
                    ++k;
                }
                if (movingobjectposition.face == 4) {
                    --i;
                }
                if (movingobjectposition.face == 5) {
                    ++i;
                }
                if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
                    return itemstack;
                }
                final PlayerBucketEmptyEvent event3 = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, clickedX, clickedY, clickedZ, movingobjectposition.face, itemstack);
                if (event3.isCancelled()) {
                    return itemstack;
                }
                if (this.a(world, d0, d2, d3, i, j, k) && !entityhuman.abilities.canInstantlyBuild) {
                    return CraftItemStack.asNMSCopy(event3.getItemStack());
                }
            }
        }
        else if (this.a == 0 && movingobjectposition.entity instanceof EntityCow) {
            final Location loc = movingobjectposition.entity.getBukkitEntity().getLocation();
            final PlayerBucketFillEvent event4 = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Item.MILK_BUCKET);
            if (event4.isCancelled()) {
                return itemstack;
            }
            return CraftItemStack.asNMSCopy(event4.getItemStack());
        }
        return itemstack;
    }
    
    public boolean a(final World world, final double d0, final double d1, final double d2, final int i, final int j, final int k) {
        if (this.a <= 0) {
            return false;
        }
        if (!world.isEmpty(i, j, k) && world.getMaterial(i, j, k).isBuildable()) {
            return false;
        }
        if (world.worldProvider.e && this.a == Block.WATER.id) {
            world.makeSound(d0 + 0.5, d1 + 0.5, d2 + 0.5, "random.fizz", 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
            for (int l = 0; l < 8; ++l) {
                world.addParticle("largesmoke", i + Math.random(), j + Math.random(), k + Math.random(), 0.0, 0.0, 0.0);
            }
        }
        else {
            world.setTypeIdAndData(i, j, k, this.a, 0, 3);
        }
        return true;
    }
}
