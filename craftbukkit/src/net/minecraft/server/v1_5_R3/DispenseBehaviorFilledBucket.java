// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

final class DispenseBehaviorFilledBucket extends DispenseBehaviorItem
{
    private final DispenseBehaviorItem b;
    
    DispenseBehaviorFilledBucket() {
        this.b = new DispenseBehaviorItem();
    }
    
    public ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        ItemBucket itembucket = (ItemBucket)itemstack.getItem();
        final int i = isourceblock.getBlockX();
        final int j = isourceblock.getBlockY();
        final int k = isourceblock.getBlockZ();
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final World world = isourceblock.k();
        final int x = i + enumfacing.c();
        final int y = j + enumfacing.d();
        final int z = k + enumfacing.e();
        if (world.isEmpty(x, y, z) || !world.getMaterial(x, y, z).isBuildable()) {
            final Block block = world.getWorld().getBlockAt(i, j, k);
            final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
            final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(x, y, z));
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
            itembucket = (ItemBucket)CraftItemStack.asNMSCopy(event.getItem()).getItem();
        }
        if (itembucket.a(isourceblock.k(), i, j, k, i + enumfacing.c(), j + enumfacing.d(), k + enumfacing.e())) {
            final Item item = Item.BUCKET;
            if (--itemstack.count == 0) {
                itemstack.id = item.id;
                itemstack.count = 1;
            }
            else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
                this.b.a(isourceblock, new ItemStack(item));
            }
            return itemstack;
        }
        return this.b.a(isourceblock, itemstack);
    }
}
