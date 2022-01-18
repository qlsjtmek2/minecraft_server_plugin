// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

public abstract class DispenseBehaviorProjectile extends DispenseBehaviorItem
{
    public ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final World world = isourceblock.k();
        final IPosition iposition = BlockDispenser.a(isourceblock);
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final IProjectile iprojectile = this.a(world, iposition);
        final ItemStack itemstack2 = itemstack.a(1);
        final Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack2);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(enumfacing.c(), enumfacing.d() + 0.1f, (double)enumfacing.e()));
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
        iprojectile.shoot(event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), this.b(), this.a());
        world.addEntity((Entity)iprojectile);
        return itemstack;
    }
    
    protected void a(final ISourceBlock isourceblock) {
        isourceblock.k().triggerEffect(1002, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
    }
    
    protected abstract IProjectile a(final World p0, final IPosition p1);
    
    protected float a() {
        return 6.0f;
    }
    
    protected float b() {
        return 1.1f;
    }
}
