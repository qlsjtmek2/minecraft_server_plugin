// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockCocoa extends BlockDirectional
{
    public static final String[] a;
    
    public BlockCocoa(final int i) {
        super(i, Material.PLANT);
        this.b(true);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!this.f(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
        else if (world.random.nextInt(5) == 0) {
            final int l = world.getData(i, j, k);
            int i2 = c(l);
            if (i2 < 2) {
                ++i2;
                CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this.id, i2 << 2 | BlockDirectional.j(l));
            }
        }
    }
    
    public boolean f(final World world, int i, final int j, int k) {
        final int l = BlockDirectional.j(world.getData(i, j, k));
        i += Direction.a[l];
        k += Direction.b[l];
        final int i2 = world.getTypeId(i, j, k);
        return i2 == Block.LOG.id && BlockLog.d(world.getData(i, j, k)) == 3;
    }
    
    public int d() {
        return 28;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        this.updateShape(world, i, j, k);
        return super.b(world, i, j, k);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k);
        final int i2 = BlockDirectional.j(l);
        final int j2 = c(l);
        final int k2 = 4 + j2 * 2;
        final int l2 = 5 + j2 * 2;
        final float f = k2 / 2.0f;
        switch (i2) {
            case 0: {
                this.a((8.0f - f) / 16.0f, (12.0f - l2) / 16.0f, (15.0f - k2) / 16.0f, (8.0f + f) / 16.0f, 0.75f, 0.9375f);
                break;
            }
            case 1: {
                this.a(0.0625f, (12.0f - l2) / 16.0f, (8.0f - f) / 16.0f, (1.0f + k2) / 16.0f, 0.75f, (8.0f + f) / 16.0f);
                break;
            }
            case 2: {
                this.a((8.0f - f) / 16.0f, (12.0f - l2) / 16.0f, 0.0625f, (8.0f + f) / 16.0f, 0.75f, (1.0f + k2) / 16.0f);
                break;
            }
            case 3: {
                this.a((15.0f - k2) / 16.0f, (12.0f - l2) / 16.0f, (8.0f - f) / 16.0f, 0.9375f, 0.75f, (8.0f + f) / 16.0f);
                break;
            }
        }
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final int l = ((MathHelper.floor(entityliving.yaw * 4.0f / 360.0f + 0.5) & 0x3) + 0) % 4;
        world.setData(i, j, k, l, 2);
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, int l, final float f, final float f1, final float f2, final int i1) {
        if (l == 1 || l == 0) {
            l = 2;
        }
        return Direction.f[Direction.e[l]];
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!this.f(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
    }
    
    public static int c(final int i) {
        return (i & 0xC) >> 2;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        final int j2 = c(l);
        byte b0 = 1;
        if (j2 >= 2) {
            b0 = 3;
        }
        for (int k2 = 0; k2 < b0; ++k2) {
            this.b(world, i, j, k, new ItemStack(Item.INK_SACK, 1, 3));
        }
    }
    
    public int getDropData(final World world, final int i, final int j, final int k) {
        return 3;
    }
    
    static {
        a = new String[] { "cocoa_0", "cocoa_1", "cocoa_2" };
    }
}
