// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

final class DispenseBehaviorMinecart extends DispenseBehaviorItem
{
    private final DispenseBehaviorItem b;
    
    DispenseBehaviorMinecart() {
        this.b = new DispenseBehaviorItem();
    }
    
    public ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final World world = isourceblock.k();
        final double d0 = isourceblock.getX() + enumfacing.c() * 1.125f;
        final double d2 = isourceblock.getY() + enumfacing.d() * 1.125f;
        final double d3 = isourceblock.getZ() + enumfacing.e() * 1.125f;
        final int i = isourceblock.getBlockX() + enumfacing.c();
        final int j = isourceblock.getBlockY() + enumfacing.d();
        final int k = isourceblock.getBlockZ() + enumfacing.e();
        final int l = world.getTypeId(i, j, k);
        double d4;
        if (BlockMinecartTrackAbstract.d_(l)) {
            d4 = 0.0;
        }
        else {
            if (l != 0 || !BlockMinecartTrackAbstract.d_(world.getTypeId(i, j - 1, k))) {
                return this.b.a(isourceblock, itemstack);
            }
            d4 = -1.0;
        }
        ItemStack itemstack2 = itemstack.a(1);
        final Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack2);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d2 + d4, d3));
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
        final EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), ((ItemMinecart)itemstack2.getItem()).a);
        world.addEntity(entityminecartabstract);
        return itemstack;
    }
    
    protected void a(final ISourceBlock isourceblock) {
        isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
    }
}
