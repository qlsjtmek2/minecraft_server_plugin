// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CombatEntry
{
    private final DamageSource a;
    private final int b;
    private final int c;
    private final int d;
    private final String e;
    private final float f;
    
    public CombatEntry(final DamageSource a, final int b, final int d, final int c, final String e, final float f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }
    
    public DamageSource a() {
        return this.a;
    }
    
    public int c() {
        return this.c;
    }
    
    public boolean f() {
        return this.a.getEntity() instanceof EntityLiving;
    }
    
    public String g() {
        return this.e;
    }
    
    public String h() {
        return (this.a().getEntity() == null) ? null : this.a().getEntity().getScoreboardDisplayName();
    }
    
    public float i() {
        if (this.a == DamageSource.OUT_OF_WORLD) {
            return Float.MAX_VALUE;
        }
        return this.f;
    }
}
