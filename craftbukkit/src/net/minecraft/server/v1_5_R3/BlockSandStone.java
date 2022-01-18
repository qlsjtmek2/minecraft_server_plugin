// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockSandStone extends Block
{
    public static final String[] a;
    private static final String[] b;
    
    public BlockSandStone(final int i) {
        super(i, Material.STONE);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropData(final int n) {
        return n;
    }
    
    static {
        a = new String[] { "default", "chiseled", "smooth" };
        b = new String[] { "sandstone_side", "sandstone_carved", "sandstone_smooth" };
    }
}
