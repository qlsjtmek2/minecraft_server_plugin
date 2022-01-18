// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityFireworks extends Entity
{
    private int ticksFlown;
    public int expectedLifespan;
    
    public EntityFireworks(final World world) {
        super(world);
        this.a(0.25f, 0.25f);
    }
    
    protected void a() {
        this.datawatcher.a(8, 5);
    }
    
    public EntityFireworks(final World world, final double d0, final double d1, final double d2, final ItemStack itemstack) {
        super(world);
        this.ticksFlown = 0;
        this.a(0.25f, 0.25f);
        this.setPosition(d0, d1, d2);
        this.height = 0.0f;
        int i = 1;
        if (itemstack != null && itemstack.hasTag()) {
            this.datawatcher.watch(8, itemstack);
            final NBTTagCompound nbttagcompound = itemstack.getTag();
            final NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("Fireworks");
            if (nbttagcompound2 != null) {
                i += nbttagcompound2.getByte("Flight");
            }
        }
        this.motX = this.random.nextGaussian() * 0.001;
        this.motZ = this.random.nextGaussian() * 0.001;
        this.motY = 0.05;
        this.expectedLifespan = 10 * i + this.random.nextInt(6) + this.random.nextInt(7);
    }
    
    public void l_() {
        this.U = this.locX;
        this.V = this.locY;
        this.W = this.locZ;
        super.l_();
        this.motX *= 1.15;
        this.motZ *= 1.15;
        this.motY += 0.04;
        this.move(this.motX, this.motY, this.motZ);
        final float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0 / 3.1415927410125732);
        this.pitch = (float)(Math.atan2(this.motY, f) * 180.0 / 3.1415927410125732);
        while (this.pitch - this.lastPitch < -180.0f) {
            this.lastPitch -= 360.0f;
        }
        while (this.pitch - this.lastPitch >= 180.0f) {
            this.lastPitch += 360.0f;
        }
        while (this.yaw - this.lastYaw < -180.0f) {
            this.lastYaw -= 360.0f;
        }
        while (this.yaw - this.lastYaw >= 180.0f) {
            this.lastYaw += 360.0f;
        }
        this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2f;
        this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2f;
        if (this.ticksFlown == 0) {
            this.world.makeSound(this, "fireworks.launch", 3.0f, 1.0f);
        }
        ++this.ticksFlown;
        if (this.world.isStatic && this.ticksFlown % 2 < 2) {
            this.world.addParticle("fireworksSpark", this.locX, this.locY - 0.3, this.locZ, this.random.nextGaussian() * 0.05, -this.motY * 0.5, this.random.nextGaussian() * 0.05);
        }
        if (!this.world.isStatic && this.ticksFlown > this.expectedLifespan) {
            this.world.broadcastEntityEffect(this, (byte)17);
            this.die();
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("Life", this.ticksFlown);
        nbttagcompound.setInt("LifeTime", this.expectedLifespan);
        final ItemStack itemstack = this.datawatcher.getItemStack(8);
        if (itemstack != null) {
            final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            itemstack.save(nbttagcompound2);
            nbttagcompound.setCompound("FireworksItem", nbttagcompound2);
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.ticksFlown = nbttagcompound.getInt("Life");
        this.expectedLifespan = nbttagcompound.getInt("LifeTime");
        final NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("FireworksItem");
        if (nbttagcompound2 != null) {
            final ItemStack itemstack = ItemStack.createStack(nbttagcompound2);
            if (itemstack != null) {
                this.datawatcher.watch(8, itemstack);
            }
        }
    }
    
    public float c(final float f) {
        return super.c(f);
    }
    
    public boolean ap() {
        return false;
    }
}
