// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

final class DispenseBehaviorMonsterEgg extends DispenseBehaviorItem
{
    public ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final double d0 = isourceblock.getX() + enumfacing.c();
        final double d2 = isourceblock.getBlockY() + 0.2f;
        final double d3 = isourceblock.getZ() + enumfacing.e();
        final World world = isourceblock.k();
        ItemStack itemstack2 = itemstack.a(1);
        final Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack2);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d2, d3));
        if (!BlockDispenser.eventFired) {
            world.getServer().getPluginManager().callEvent(event);
        }
        if (event.isCancelled()) {
            ++itemstack.count;
            return itemstack;
        }
        if (!event.getItem().equals(craftItem)) {
            ++itemstack.count;
            final ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            final IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.a(eventStack.getItem());
            if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
                idispensebehavior.a(isourceblock, eventStack);
                return itemstack;
            }
        }
        itemstack2 = CraftItemStack.asNMSCopy(event.getItem());
        final Entity entity = ItemMonsterEgg.a(isourceblock.k(), itemstack.getData(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
        if (entity instanceof EntityLiving && itemstack.hasName()) {
            ((EntityLiving)entity).setCustomName(itemstack.getName());
        }
        return itemstack;
    }
}
