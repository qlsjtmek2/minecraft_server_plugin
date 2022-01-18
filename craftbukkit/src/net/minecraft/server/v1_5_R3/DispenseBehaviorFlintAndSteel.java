// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

final class DispenseBehaviorFlintAndSteel extends DispenseBehaviorItem
{
    private boolean b;
    
    DispenseBehaviorFlintAndSteel() {
        this.b = true;
    }
    
    protected ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final World world = isourceblock.k();
        final int i = isourceblock.getBlockX() + enumfacing.c();
        final int j = isourceblock.getBlockY() + enumfacing.d();
        final int k = isourceblock.getBlockZ() + enumfacing.e();
        final org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
        if (!BlockDispenser.eventFired) {
            world.getServer().getPluginManager().callEvent(event);
        }
        if (event.isCancelled()) {
            return itemstack;
        }
        if (!event.getItem().equals(craftItem)) {
            final ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            final IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.a(eventStack.getItem());
            if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
                idispensebehavior.a(isourceblock, eventStack);
                return itemstack;
            }
        }
        if (world.isEmpty(i, j, k)) {
            if (!CraftEventFactory.callBlockIgniteEvent(world, i, j, k, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ()).isCancelled()) {
                world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
                if (itemstack.isDamaged(1, world.random)) {
                    itemstack.count = 0;
                }
            }
        }
        else if (world.getTypeId(i, j, k) == Block.TNT.id) {
            Block.TNT.postBreak(world, i, j, k, 1);
            world.setAir(i, j, k);
        }
        else {
            this.b = false;
        }
        return itemstack;
    }
    
    protected void a(final ISourceBlock isourceblock) {
        if (this.b) {
            isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
        }
        else {
            isourceblock.k().triggerEffect(1001, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
        }
    }
}
