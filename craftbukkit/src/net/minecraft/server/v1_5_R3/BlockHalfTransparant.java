// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockHalfTransparant extends Block
{
    private boolean a;
    private String b;
    
    protected BlockHalfTransparant(final int i, final String b, final Material material, final boolean a) {
        super(i, material);
        this.a = a;
        this.b = b;
    }
    
    public boolean c() {
        return false;
    }
}
