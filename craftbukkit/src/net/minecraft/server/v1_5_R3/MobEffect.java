// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class MobEffect
{
    private int effectId;
    private int duration;
    private int amplification;
    private boolean splash;
    private boolean ambient;
    
    public MobEffect(final int n, final int n2) {
        this(n, n2, 0);
    }
    
    public MobEffect(final int n, final int n2, final int n3) {
        this(n, n2, n3, false);
    }
    
    public MobEffect(final int effectId, final int duration, final int amplification, final boolean ambient) {
        this.effectId = effectId;
        this.duration = duration;
        this.amplification = amplification;
        this.ambient = ambient;
    }
    
    public MobEffect(final MobEffect mobEffect) {
        this.effectId = mobEffect.effectId;
        this.duration = mobEffect.duration;
        this.amplification = mobEffect.amplification;
    }
    
    public void a(final MobEffect mobEffect) {
        if (this.effectId != mobEffect.effectId) {
            System.err.println("This method should only be called for matching effects!");
        }
        if (mobEffect.amplification > this.amplification) {
            this.amplification = mobEffect.amplification;
            this.duration = mobEffect.duration;
        }
        else if (mobEffect.amplification == this.amplification && this.duration < mobEffect.duration) {
            this.duration = mobEffect.duration;
        }
        else if (!mobEffect.ambient && this.ambient) {
            this.ambient = mobEffect.ambient;
        }
    }
    
    public int getEffectId() {
        return this.effectId;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public int getAmplifier() {
        return this.amplification;
    }
    
    public boolean isSplash() {
        return this.splash;
    }
    
    public void setSplash(final boolean splash) {
        this.splash = splash;
    }
    
    public boolean isAmbient() {
        return this.ambient;
    }
    
    public boolean tick(final EntityLiving entityLiving) {
        if (this.duration > 0) {
            if (MobEffectList.byId[this.effectId].a(this.duration, this.amplification)) {
                this.b(entityLiving);
            }
            this.h();
        }
        return this.duration > 0;
    }
    
    private int h() {
        return --this.duration;
    }
    
    public void b(final EntityLiving entityliving) {
        if (this.duration > 0) {
            MobEffectList.byId[this.effectId].tick(entityliving, this.amplification);
        }
    }
    
    public String f() {
        return MobEffectList.byId[this.effectId].a();
    }
    
    public int hashCode() {
        return this.effectId;
    }
    
    public String toString() {
        String s;
        if (this.getAmplifier() > 0) {
            s = this.f() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else {
            s = this.f() + ", Duration: " + this.getDuration();
        }
        if (this.splash) {
            s += ", Splash: true";
        }
        if (MobEffectList.byId[this.effectId].i()) {
            return "(" + s + ")";
        }
        return s;
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof MobEffect)) {
            return false;
        }
        final MobEffect mobEffect = (MobEffect)o;
        return this.effectId == mobEffect.effectId && this.amplification == mobEffect.amplification && this.duration == mobEffect.duration && this.splash == mobEffect.splash && this.ambient == mobEffect.ambient;
    }
    
    public NBTTagCompound a(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Id", (byte)this.getEffectId());
        nbtTagCompound.setByte("Amplifier", (byte)this.getAmplifier());
        nbtTagCompound.setInt("Duration", this.getDuration());
        nbtTagCompound.setBoolean("Ambient", this.isAmbient());
        return nbtTagCompound;
    }
    
    public static MobEffect b(final NBTTagCompound nbtTagCompound) {
        return new MobEffect(nbtTagCompound.getByte("Id"), nbtTagCompound.getInt("Duration"), nbtTagCompound.getByte("Amplifier"), nbtTagCompound.getBoolean("Ambient"));
    }
}
