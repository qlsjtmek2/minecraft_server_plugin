// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockRedstone extends BlockOreBlock
{
    public BlockRedstone(final int n) {
        super(n);
        this.a(CreativeModeTab.d);
    }
    
    public boolean isPowerSource() {
        return true;
    }
    
    public int b(final IBlockAccess blockAccess, final int n, final int n2, final int n3, final int n4) {
        return 15;
    }
}
