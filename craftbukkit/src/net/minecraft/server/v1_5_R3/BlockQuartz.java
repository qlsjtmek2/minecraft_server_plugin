// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockQuartz extends Block
{
    public static final String[] a;
    private static final String[] b;
    
    public BlockQuartz(final int i) {
        super(i, Material.STONE);
        this.a(CreativeModeTab.b);
    }
    
    public int getPlacedData(final World world, final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7, int n8) {
        if (n8 == 2) {
            switch (n4) {
                case 2:
                case 3: {
                    n8 = 4;
                    break;
                }
                case 4:
                case 5: {
                    n8 = 3;
                    break;
                }
                case 0:
                case 1: {
                    n8 = 2;
                    break;
                }
            }
        }
        return n8;
    }
    
    public int getDropData(final int n) {
        if (n == 3 || n == 4) {
            return 2;
        }
        return n;
    }
    
    protected ItemStack c_(final int i) {
        if (i == 3 || i == 4) {
            return new ItemStack(this.id, 1, 2);
        }
        return super.c_(i);
    }
    
    public int d() {
        return 39;
    }
    
    static {
        a = new String[] { "default", "chiseled", "lines" };
        b = new String[] { "quartzblock_side", "quartzblock_chiseled", "quartzblock_lines", null, null };
    }
}
