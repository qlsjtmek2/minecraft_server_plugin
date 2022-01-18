// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityComplexPart extends Entity
{
    public final IComplex owner;
    public final String b;
    
    public EntityComplexPart(final IComplex owner, final String b, final float f, final float f2) {
        super(owner.d());
        this.a(f, f2);
        this.owner = owner;
        this.b = b;
    }
    
    protected void a() {
    }
    
    protected void a(final NBTTagCompound nbtTagCompound) {
    }
    
    protected void b(final NBTTagCompound nbtTagCompound) {
    }
    
    public boolean K() {
        return true;
    }
    
    public boolean damageEntity(final DamageSource damageSource, final int n) {
        return !this.isInvulnerable() && this.owner.a(this, damageSource, n);
    }
    
    public boolean i(final Entity entity) {
        return this == entity || this.owner == entity;
    }
}
