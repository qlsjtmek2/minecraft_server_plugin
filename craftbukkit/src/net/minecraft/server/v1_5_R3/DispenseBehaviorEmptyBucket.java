// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

final class DispenseBehaviorEmptyBucket extends DispenseBehaviorItem
{
    private final DispenseBehaviorItem b;
    
    DispenseBehaviorEmptyBucket() {
        this.b = new DispenseBehaviorItem();
    }
    
    public ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final World world = isourceblock.k();
        final int i = isourceblock.getBlockX() + enumfacing.c();
        final int j = isourceblock.getBlockY() + enumfacing.d();
        final int k = isourceblock.getBlockZ() + enumfacing.e();
        final Material material = world.getMaterial(i, j, k);
        final int l = world.getData(i, j, k);
        Item item;
        if (Material.WATER.equals(material) && l == 0) {
            item = Item.WATER_BUCKET;
        }
        else {
            if (!Material.LAVA.equals(material) || l != 0) {
                return super.b(isourceblock, itemstack);
            }
            item = Item.LAVA_BUCKET;
        }
        final Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(i, j, k));
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
        world.setAir(i, j, k);
        if (--itemstack.count == 0) {
            itemstack.id = item.id;
            itemstack.count = 1;
        }
        else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
            this.b.a(isourceblock, new ItemStack(item));
        }
        return itemstack;
    }
}
