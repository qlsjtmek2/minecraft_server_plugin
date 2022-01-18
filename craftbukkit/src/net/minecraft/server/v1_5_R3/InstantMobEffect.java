// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class InstantMobEffect extends MobEffectList
{
    public InstantMobEffect(final int i, final boolean flag, final int j) {
        super(i, flag, j);
    }
    
    public boolean isInstant() {
        return true;
    }
    
    public boolean a(final int n, final int n2) {
        return n >= 1;
    }
}
