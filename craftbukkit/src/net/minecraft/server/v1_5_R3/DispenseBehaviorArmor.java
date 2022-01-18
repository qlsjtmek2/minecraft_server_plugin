// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

final class DispenseBehaviorArmor extends DispenseBehaviorItem
{
    protected ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final int i = isourceblock.getBlockX() + enumfacing.c();
        final int j = isourceblock.getBlockY() + enumfacing.d();
        final int k = isourceblock.getBlockZ() + enumfacing.e();
        final AxisAlignedBB axisalignedbb = AxisAlignedBB.a().a(i, j, k, i + 1, j + 1, k + 1);
        final List list = isourceblock.k().a(EntityLiving.class, axisalignedbb, new EntitySelectorEquipable(itemstack));
        if (list.size() <= 0) {
            return super.b(isourceblock, itemstack);
        }
        final EntityLiving entityliving = list.get(0);
        final int l = (entityliving instanceof EntityHuman) ? 1 : 0;
        final int i2 = EntityLiving.b(itemstack);
        final ItemStack itemstack2 = itemstack.a(1);
        final World world = isourceblock.k();
        final Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack2);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
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
        itemstack2.count = 1;
        entityliving.setEquipment(i2 - l, itemstack2);
        entityliving.a(i2, 2.0f);
        return itemstack;
    }
}
