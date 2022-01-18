// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockSmoothBrick extends Block
{
    public static final String[] a;
    public static final String[] b;
    
    public BlockSmoothBrick(final int i) {
        super(i, Material.STONE);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropData(final int n) {
        return n;
    }
    
    static {
        a = new String[] { "default", "mossy", "cracked", "chiseled" };
        b = new String[] { "stonebricksmooth", "stonebricksmooth_mossy", "stonebricksmooth_cracked", "stonebricksmooth_carved" };
    }
}
