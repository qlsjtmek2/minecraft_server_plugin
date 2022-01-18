// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockCommand extends BlockContainer
{
    public BlockCommand(final int i) {
        super(i, Material.ORE);
    }
    
    public TileEntity b(final World world) {
        return new TileEntityCommand();
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!world.isStatic) {
            final boolean flag = world.isBlockIndirectlyPowered(i, j, k);
            final int i2 = world.getData(i, j, k);
            final boolean flag2 = (i2 & 0x1) != 0x0;
            final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            final int old = flag2 ? 15 : 0;
            final int current = flag ? 15 : 0;
            final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
            world.getServer().getPluginManager().callEvent(eventRedstone);
            if (eventRedstone.getNewCurrent() > 0 && eventRedstone.getOldCurrent() <= 0) {
                world.setData(i, j, k, i2 | 0x1, 4);
                world.a(i, j, k, this.id, this.a(world));
            }
            else if (eventRedstone.getNewCurrent() <= 0 && eventRedstone.getOldCurrent() > 0) {
                world.setData(i, j, k, i2 & 0xFFFFFFFE, 4);
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof TileEntityCommand) {
            final TileEntityCommand tileentitycommand = (TileEntityCommand)tileentity;
            tileentitycommand.a(tileentitycommand.a(world));
            world.m(i, j, k, this.id);
        }
    }
    
    public int a(final World world) {
        return 1;
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        final TileEntityCommand tileentitycommand = (TileEntityCommand)world.getTileEntity(i, j, k);
        if (tileentitycommand != null) {
            entityhuman.a(tileentitycommand);
        }
        return true;
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int l) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        return (tileentity != null && tileentity instanceof TileEntityCommand) ? ((TileEntityCommand)tileentity).d() : 0;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final TileEntityCommand tileentitycommand = (TileEntityCommand)world.getTileEntity(i, j, k);
        if (itemstack.hasName()) {
            tileentitycommand.c(itemstack.getName());
        }
    }
}
