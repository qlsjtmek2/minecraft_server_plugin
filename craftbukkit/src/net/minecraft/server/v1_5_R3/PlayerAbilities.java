// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PlayerAbilities
{
    public boolean isInvulnerable;
    public boolean isFlying;
    public boolean canFly;
    public boolean canInstantlyBuild;
    public boolean mayBuild;
    public float flySpeed;
    public float walkSpeed;
    
    public PlayerAbilities() {
        this.isInvulnerable = false;
        this.isFlying = false;
        this.canFly = false;
        this.canInstantlyBuild = false;
        this.mayBuild = true;
        this.flySpeed = 0.05f;
        this.walkSpeed = 0.1f;
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        nbttagcompound2.setBoolean("invulnerable", this.isInvulnerable);
        nbttagcompound2.setBoolean("flying", this.isFlying);
        nbttagcompound2.setBoolean("mayfly", this.canFly);
        nbttagcompound2.setBoolean("instabuild", this.canInstantlyBuild);
        nbttagcompound2.setBoolean("mayBuild", this.mayBuild);
        nbttagcompound2.setFloat("flySpeed", this.flySpeed);
        nbttagcompound2.setFloat("walkSpeed", this.walkSpeed);
        nbttagcompound.set("abilities", nbttagcompound2);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("abilities")) {
            final NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("abilities");
            this.isInvulnerable = nbttagcompound2.getBoolean("invulnerable");
            this.isFlying = nbttagcompound2.getBoolean("flying");
            this.canFly = nbttagcompound2.getBoolean("mayfly");
            this.canInstantlyBuild = nbttagcompound2.getBoolean("instabuild");
            if (nbttagcompound2.hasKey("flySpeed")) {
                this.flySpeed = nbttagcompound2.getFloat("flySpeed");
                this.walkSpeed = nbttagcompound2.getFloat("walkSpeed");
            }
            if (nbttagcompound2.hasKey("mayBuild")) {
                this.mayBuild = nbttagcompound2.getBoolean("mayBuild");
            }
        }
    }
    
    public float a() {
        return this.flySpeed;
    }
    
    public float b() {
        return this.walkSpeed;
    }
}
