// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockGlass extends BlockHalfTransparant
{
    public BlockGlass(final int n, final Material material, final boolean b) {
        super(n, "glass", material, b);
        this.a(CreativeModeTab.b);
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    protected boolean r_() {
        return true;
    }
}
