// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockRepeater extends BlockDiodeAbstract
{
    public static final double[] b;
    private static final int[] c;
    
    protected BlockRepeater(final int i, final boolean flag) {
        super(i, flag);
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
        final int data = world.getData(n, n2, n3);
        world.setData(n, n2, n3, (((data & 0xC) >> 2) + 1 << 2 & 0xC) | (data & 0x3), 3);
        return true;
    }
    
    protected int i_(final int n) {
        return BlockRepeater.c[(n & 0xC) >> 2] * 2;
    }
    
    protected BlockDiodeAbstract i() {
        return Block.DIODE_ON;
    }
    
    protected BlockDiodeAbstract j() {
        return Block.DIODE_OFF;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.DIODE.id;
    }
    
    public int d() {
        return 15;
    }
    
    public boolean e(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return this.f(iblockaccess, i, j, k, l) > 0;
    }
    
    protected boolean e(final int i) {
        return BlockDiodeAbstract.f(i);
    }
    
    public void remove(final World world, final int n, final int n2, final int n3, final int l, final int i1) {
        super.remove(world, n, n2, n3, l, i1);
        this.h_(world, n, n2, n3);
    }
    
    static {
        b = new double[] { -0.0625, 0.0625, 0.1875, 0.3125 };
        c = new int[] { 1, 2, 3, 4 };
    }
}
