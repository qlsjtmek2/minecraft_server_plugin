// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import java.util.Random;

public class BlockSign extends BlockContainer
{
    private Class a;
    private boolean b;
    
    protected BlockSign(final int i, final Class oclass, final boolean flag) {
        super(i, Material.WOOD);
        this.b = flag;
        this.a = oclass;
        final float f = 0.25f;
        final float f2 = 1.0f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f2, 0.5f + f);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        if (!this.b) {
            final int l = iblockaccess.getData(i, j, k);
            final float f = 0.28125f;
            final float f2 = 0.78125f;
            final float f3 = 0.0f;
            final float f4 = 1.0f;
            final float f5 = 0.125f;
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            if (l == 2) {
                this.a(f3, f, 1.0f - f5, f4, f2, 1.0f);
            }
            if (l == 3) {
                this.a(f3, f, 0.0f, f4, f2, f5);
            }
            if (l == 4) {
                this.a(1.0f - f5, f, f3, 1.0f, f2, f4);
            }
            if (l == 5) {
                this.a(0.0f, f, f3, f5, f2, f4);
            }
        }
    }
    
    public int d() {
        return -1;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean b(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return true;
    }
    
    public boolean c() {
        return false;
    }
    
    public TileEntity b(final World world) {
        try {
            return this.a.newInstance();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Item.SIGN.id;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        boolean flag = false;
        if (this.b) {
            if (!world.getMaterial(i, j - 1, k).isBuildable()) {
                flag = true;
            }
        }
        else {
            final int i2 = world.getData(i, j, k);
            flag = true;
            if (i2 == 2 && world.getMaterial(i, j, k + 1).isBuildable()) {
                flag = false;
            }
            if (i2 == 3 && world.getMaterial(i, j, k - 1).isBuildable()) {
                flag = false;
            }
            if (i2 == 4 && world.getMaterial(i + 1, j, k).isBuildable()) {
                flag = false;
            }
            if (i2 == 5 && world.getMaterial(i - 1, j, k).isBuildable()) {
                flag = false;
            }
        }
        if (flag) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
        super.doPhysics(world, i, j, k, l);
        if (Block.byId[l] != null && Block.byId[l].isPowerSource()) {
            final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            final int power = block.getBlockPower();
            final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(eventRedstone);
        }
    }
}
