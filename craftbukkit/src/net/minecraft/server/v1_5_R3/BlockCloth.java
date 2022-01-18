// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockCloth extends Block
{
    public BlockCloth() {
        super(35, Material.CLOTH);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropData(final int n) {
        return n;
    }
    
    public static int g_(final int n) {
        return ~n & 0xF;
    }
    
    public static int c(final int n) {
        return ~n & 0xF;
    }
}
