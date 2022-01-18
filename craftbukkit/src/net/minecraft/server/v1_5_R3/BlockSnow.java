// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockSnow extends Block
{
    protected BlockSnow(final int i) {
        super(i, Material.SNOW_LAYER);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.b(true);
        this.a(CreativeModeTab.c);
        this.d(0);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k) & 0x7;
        final float f = 0.125f;
        return AxisAlignedBB.a().a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + l * f, k + this.maxZ);
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public void g() {
        this.d(0);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        this.d(iblockaccess.getData(i, j, k));
    }
    
    protected void d(final int i) {
        final int j = i & 0x7;
        final float f = 2 * (1 + j) / 16.0f;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        final int l = world.getTypeId(i, j - 1, k);
        return l != 0 && ((l == this.id && (world.getData(i, j - 1, k) & 0x7) == 0x7) || ((l == Block.LEAVES.id || Block.byId[l].c()) && world.getMaterial(i, j - 1, k).isSolid()));
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        this.m(world, i, j, k);
    }
    
    private boolean m(final World world, final int i, final int j, final int k) {
        if (!this.canPlace(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
            return false;
        }
        return true;
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int i, final int j, final int k, final int l) {
        final int i2 = Item.SNOW_BALL.id;
        final int j2 = l & 0x7;
        this.b(world, i, j, k, new ItemStack(i2, j2 + 1, 0));
        world.setAir(i, j, k);
        entityhuman.a(StatisticList.C[this.id], 1);
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Item.SNOW_BALL.id;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (world.b(EnumSkyBlock.BLOCK, i, j, k) > 11) {
            if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), 0).isCancelled()) {
                return;
            }
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
    }
}
