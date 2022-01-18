// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockDaylightDetector extends BlockContainer
{
    private IIcon[] a;
    
    public BlockDaylightDetector(final int i) {
        super(i, Material.WOOD);
        this.a = new IIcon[2];
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.a(CreativeModeTab.d);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return iblockaccess.getData(i, j, k);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
    }
    
    public void i_(final World world, final int i, final int j, final int k) {
        if (!world.worldProvider.f) {
            final int l = world.getData(i, j, k);
            int i2 = world.b(EnumSkyBlock.SKY, i, j, k) - world.j;
            float f = world.d(1.0f);
            if (f < 3.1415927f) {
                f += (0.0f - f) * 0.2f;
            }
            else {
                f += (6.2831855f - f) * 0.2f;
            }
            i2 = Math.round(i2 * MathHelper.cos(f));
            if (i2 < 0) {
                i2 = 0;
            }
            if (i2 > 15) {
                i2 = 15;
            }
            if (l != i2) {
                i2 = CraftEventFactory.callRedstoneChange(world, i, j, k, l, i2).getNewCurrent();
                world.setData(i, j, k, i2, 3);
            }
        }
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean isPowerSource() {
        return true;
    }
    
    public TileEntity b(final World world) {
        return new TileEntityLightDetector();
    }
}
