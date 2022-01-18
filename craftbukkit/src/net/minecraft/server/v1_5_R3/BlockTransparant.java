// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockTransparant extends Block
{
    protected boolean d;
    
    protected BlockTransparant(final int i, final Material material, final boolean d) {
        super(i, material);
        this.d = d;
    }
    
    public boolean c() {
        return false;
    }
}
