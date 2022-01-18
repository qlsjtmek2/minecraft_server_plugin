// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import java.util.Random;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

final class DispenseBehaviorFireball extends DispenseBehaviorItem
{
    public ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final IPosition iposition = BlockDispenser.a(isourceblock);
        final double d0 = iposition.getX() + enumfacing.c() * 0.3f;
        final double d2 = iposition.getY() + enumfacing.c() * 0.3f;
        final double d3 = iposition.getZ() + enumfacing.e() * 0.3f;
        final World world = isourceblock.k();
        final Random random = world.random;
        final double d4 = random.nextGaussian() * 0.05 + enumfacing.c();
        final double d5 = random.nextGaussian() * 0.05 + enumfacing.d();
        final double d6 = random.nextGaussian() * 0.05 + enumfacing.e();
        final ItemStack itemstack2 = itemstack.a(1);
        final Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack2);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d4, d5, d6));
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
        world.addEntity(new EntitySmallFireball(world, d0, d2, d3, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ()));
        return itemstack;
    }
    
    protected void a(final ISourceBlock isourceblock) {
        isourceblock.k().triggerEffect(1009, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
    }
}
