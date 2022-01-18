// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockLog extends Block
{
    public static final String[] a;
    public static final String[] b;
    
    protected BlockLog(final int i) {
        super(i, Material.WOOD);
        this.a(CreativeModeTab.b);
    }
    
    public int d() {
        return 31;
    }
    
    public int a(final Random random) {
        return 1;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Block.LOG.id;
    }
    
    public void remove(final World world, final int n, final int n2, final int n3, final int n4, final int n5) {
        final int n6 = 4;
        final int n7 = n6 + 1;
        if (world.e(n - n7, n2 - n7, n3 - n7, n + n7, n2 + n7, n3 + n7)) {
            for (int i = -n6; i <= n6; ++i) {
                for (int j = -n6; j <= n6; ++j) {
                    for (int k = -n6; k <= n6; ++k) {
                        if (world.getTypeId(n + i, n2 + j, n3 + k) == Block.LEAVES.id) {
                            final int data = world.getData(n + i, n2 + j, n3 + k);
                            if ((data & 0x8) == 0x0) {
                                world.setData(n + i, n2 + j, n3 + k, data | 0x8, 4);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public int getPlacedData(final World world, final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7, final int n8) {
        final int n9 = n8 & 0x3;
        int n10 = 0;
        switch (n4) {
            case 2:
            case 3: {
                n10 = 8;
                break;
            }
            case 4:
            case 5: {
                n10 = 4;
                break;
            }
            case 0:
            case 1: {
                n10 = 0;
                break;
            }
        }
        return n9 | n10;
    }
    
    public int getDropData(final int n) {
        return n & 0x3;
    }
    
    public static int d(final int n) {
        return n & 0x3;
    }
    
    protected ItemStack c_(final int n) {
        return new ItemStack(this.id, 1, d(n));
    }
    
    static {
        a = new String[] { "oak", "spruce", "birch", "jungle" };
        b = new String[] { "tree_side", "tree_spruce", "tree_birch", "tree_jungle" };
    }
}
