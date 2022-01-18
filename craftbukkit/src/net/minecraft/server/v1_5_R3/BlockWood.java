// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockWood extends Block
{
    public static final String[] a;
    public static final String[] b;
    
    public BlockWood(final int i) {
        super(i, Material.WOOD);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropData(final int n) {
        return n;
    }
    
    static {
        a = new String[] { "oak", "spruce", "birch", "jungle" };
        b = new String[] { "wood", "wood_spruce", "wood_birch", "wood_jungle" };
    }
}
