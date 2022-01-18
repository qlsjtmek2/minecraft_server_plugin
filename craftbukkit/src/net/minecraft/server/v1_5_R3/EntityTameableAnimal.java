// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class EntityTameableAnimal extends EntityAnimal
{
    protected PathfinderGoalSit d;
    
    public EntityTameableAnimal(final World world) {
        super(world);
        this.d = new PathfinderGoalSit(this);
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, (Object)(byte)0);
        this.datawatcher.a(17, "");
    }
    
    public void b(final NBTTagCompound nbtTagCompound) {
        super.b(nbtTagCompound);
        if (this.getOwnerName() == null) {
            nbtTagCompound.setString("Owner", "");
        }
        else {
            nbtTagCompound.setString("Owner", this.getOwnerName());
        }
        nbtTagCompound.setBoolean("Sitting", this.isSitting());
    }
    
    public void a(final NBTTagCompound nbtTagCompound) {
        super.a(nbtTagCompound);
        final String string = nbtTagCompound.getString("Owner");
        if (string.length() > 0) {
            this.setOwnerName(string);
            this.setTamed(true);
        }
        this.d.setSitting(nbtTagCompound.getBoolean("Sitting"));
        this.setSitting(nbtTagCompound.getBoolean("Sitting"));
    }
    
    protected void i(final boolean b) {
        String s = "heart";
        if (!b) {
            s = "smoke";
        }
        for (int i = 0; i < 7; ++i) {
            this.world.addParticle(s, this.locX + this.random.nextFloat() * this.width * 2.0f - this.width, this.locY + 0.5 + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0f - this.width, this.random.nextGaussian() * 0.02, this.random.nextGaussian() * 0.02, this.random.nextGaussian() * 0.02);
        }
    }
    
    public boolean isTamed() {
        return (this.datawatcher.getByte(16) & 0x4) != 0x0;
    }
    
    public void setTamed(final boolean b) {
        final byte byte1 = this.datawatcher.getByte(16);
        if (b) {
            this.datawatcher.watch(16, (byte)(byte1 | 0x4));
        }
        else {
            this.datawatcher.watch(16, (byte)(byte1 & 0xFFFFFFFB));
        }
    }
    
    public boolean isSitting() {
        return (this.datawatcher.getByte(16) & 0x1) != 0x0;
    }
    
    public void setSitting(final boolean b) {
        final byte byte1 = this.datawatcher.getByte(16);
        if (b) {
            this.datawatcher.watch(16, (byte)(byte1 | 0x1));
        }
        else {
            this.datawatcher.watch(16, (byte)(byte1 & 0xFFFFFFFE));
        }
    }
    
    public String getOwnerName() {
        return this.datawatcher.getString(17);
    }
    
    public void setOwnerName(final String s) {
        this.datawatcher.watch(17, s);
    }
    
    public EntityLiving getOwner() {
        return this.world.a(this.getOwnerName());
    }
    
    public PathfinderGoalSit getGoalSit() {
        return this.d;
    }
}
