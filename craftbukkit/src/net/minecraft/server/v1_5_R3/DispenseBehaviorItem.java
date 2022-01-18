// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

public class DispenseBehaviorItem implements IDispenseBehavior
{
    public final ItemStack a(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final ItemStack itemstack2 = this.b(isourceblock, itemstack);
        this.a(isourceblock);
        this.a(isourceblock, BlockDispenser.j_(isourceblock.h()));
        return itemstack2;
    }
    
    protected ItemStack b(final ISourceBlock isourceblock, final ItemStack itemstack) {
        final EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());
        final IPosition iposition = BlockDispenser.a(isourceblock);
        final ItemStack itemstack2 = itemstack.a(1);
        if (!a(isourceblock.k(), itemstack2, 6, enumfacing, isourceblock)) {
            ++itemstack.count;
        }
        return itemstack;
    }
    
    public static boolean a(final World world, final ItemStack itemstack, final int i, final EnumFacing enumfacing, final ISourceBlock isourceblock) {
        final IPosition iposition = BlockDispenser.a(isourceblock);
        final double d0 = iposition.getX();
        final double d2 = iposition.getY();
        final double d3 = iposition.getZ();
        final EntityItem entityitem = new EntityItem(world, d0, d2 - 0.3, d3, itemstack);
        final double d4 = world.random.nextDouble() * 0.1 + 0.2;
        entityitem.motX = enumfacing.c() * d4;
        entityitem.motY = 0.20000000298023224;
        entityitem.motZ = enumfacing.e() * d4;
        final EntityItem entityItem = entityitem;
        entityItem.motX += world.random.nextGaussian() * 0.007499999832361937 * i;
        final EntityItem entityItem2 = entityitem;
        entityItem2.motY += world.random.nextGaussian() * 0.007499999832361937 * i;
        final EntityItem entityItem3 = entityitem;
        entityItem3.motZ += world.random.nextGaussian() * 0.007499999832361937 * i;
        final Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        final CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
        final BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(entityitem.motX, entityitem.motY, entityitem.motZ));
        if (!BlockDispenser.eventFired) {
            world.getServer().getPluginManager().callEvent(event);
        }
        if (event.isCancelled()) {
            return false;
        }
        entityitem.setItemStack(CraftItemStack.asNMSCopy(event.getItem()));
        entityitem.motX = event.getVelocity().getX();
        entityitem.motY = event.getVelocity().getY();
        entityitem.motZ = event.getVelocity().getZ();
        if (!event.getItem().equals(craftItem)) {
            final ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            final IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.a.a(eventStack.getItem());
            if (idispensebehavior != IDispenseBehavior.a && idispensebehavior.getClass() != DispenseBehaviorItem.class) {
                idispensebehavior.a(isourceblock, eventStack);
            }
            else {
                world.addEntity(entityitem);
            }
            return false;
        }
        world.addEntity(entityitem);
        return true;
    }
    
    protected void a(final ISourceBlock isourceblock) {
        isourceblock.k().triggerEffect(1000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
    }
    
    protected void a(final ISourceBlock isourceblock, final EnumFacing enumfacing) {
        isourceblock.k().triggerEffect(2000, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), this.a(enumfacing));
    }
    
    private int a(final EnumFacing enumfacing) {
        return enumfacing.c() + 1 + (enumfacing.e() + 1) * 3;
    }
}
