// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.TrigMath;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;

public abstract class EntityCreature extends EntityLiving
{
    public PathEntity pathEntity;
    public Entity target;
    protected boolean b;
    protected int c;
    
    public EntityCreature(final World world) {
        super(world);
        this.b = false;
        this.c = 0;
    }
    
    protected boolean h() {
        return false;
    }
    
    protected void bq() {
        this.world.methodProfiler.a("ai");
        if (this.c > 0) {
            --this.c;
        }
        this.b = this.h();
        final float f = 16.0f;
        if (this.target == null) {
            final Entity target = this.findTarget();
            if (target != null) {
                final EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
                this.world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    if (event.getTarget() == null) {
                        this.target = null;
                    }
                    else {
                        this.target = ((CraftEntity)event.getTarget()).getHandle();
                    }
                }
            }
            if (this.target != null) {
                this.pathEntity = this.world.findPath(this, this.target, f, true, false, false, true);
            }
        }
        else if (this.target.isAlive()) {
            final float f2 = this.target.d(this);
            if (this.n(this.target)) {
                this.a(this.target, f2);
            }
        }
        else {
            final EntityTargetEvent event2 = new EntityTargetEvent(this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
            this.world.getServer().getPluginManager().callEvent(event2);
            if (!event2.isCancelled()) {
                if (event2.getTarget() == null) {
                    this.target = null;
                }
                else {
                    this.target = ((CraftEntity)event2.getTarget()).getHandle();
                }
            }
        }
        this.world.methodProfiler.b();
        if (!this.b && this.target != null && (this.pathEntity == null || this.random.nextInt(20) == 0)) {
            this.pathEntity = this.world.findPath(this, this.target, f, true, false, false, true);
        }
        else if (!this.b && ((this.pathEntity == null && this.random.nextInt(180) == 0) || this.random.nextInt(120) == 0 || this.c > 0) && this.bC < 100) {
            this.i();
        }
        final int i = MathHelper.floor(this.boundingBox.b + 0.5);
        final boolean flag = this.G();
        final boolean flag2 = this.I();
        this.pitch = 0.0f;
        if (this.pathEntity != null && this.random.nextInt(100) != 0) {
            this.world.methodProfiler.a("followpath");
            Vec3D vec3d = this.pathEntity.a(this);
            final double d0 = this.width * 2.0f;
            while (vec3d != null && vec3d.d(this.locX, vec3d.d, this.locZ) < d0 * d0) {
                this.pathEntity.a();
                if (this.pathEntity.b()) {
                    vec3d = null;
                    this.pathEntity = null;
                }
                else {
                    vec3d = this.pathEntity.a(this);
                }
            }
            this.bG = false;
            if (vec3d != null) {
                final double d2 = vec3d.c - this.locX;
                final double d3 = vec3d.e - this.locZ;
                final double d4 = vec3d.d - i;
                final float f3 = (float)(TrigMath.atan2(d3, d2) * 180.0 / 3.1415927410125732) - 90.0f;
                float f4 = MathHelper.g(f3 - this.yaw);
                this.bE = this.bI;
                if (f4 > 30.0f) {
                    f4 = 30.0f;
                }
                if (f4 < -30.0f) {
                    f4 = -30.0f;
                }
                this.yaw += f4;
                if (this.b && this.target != null) {
                    final double d5 = this.target.locX - this.locX;
                    final double d6 = this.target.locZ - this.locZ;
                    final float f5 = this.yaw;
                    this.yaw = (float)(Math.atan2(d6, d5) * 180.0 / 3.1415927410125732) - 90.0f;
                    f4 = (f5 - this.yaw + 90.0f) * 3.1415927f / 180.0f;
                    this.bD = -MathHelper.sin(f4) * this.bE * 1.0f;
                    this.bE = MathHelper.cos(f4) * this.bE * 1.0f;
                }
                if (d4 > 0.0) {
                    this.bG = true;
                }
            }
            if (this.target != null) {
                this.a(this.target, 30.0f, 30.0f);
            }
            if (this.positionChanged && !this.k()) {
                this.bG = true;
            }
            if (this.random.nextFloat() < 0.8f && (flag || flag2)) {
                this.bG = true;
            }
            this.world.methodProfiler.b();
        }
        else {
            super.bq();
            this.pathEntity = null;
        }
    }
    
    protected void i() {
        this.world.methodProfiler.a("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0f;
        for (int l = 0; l < 10; ++l) {
            final int i2 = MathHelper.floor(this.locX + this.random.nextInt(13) - 6.0);
            final int j2 = MathHelper.floor(this.locY + this.random.nextInt(7) - 3.0);
            final int k2 = MathHelper.floor(this.locZ + this.random.nextInt(13) - 6.0);
            final float f2 = this.a(i2, j2, k2);
            if (f2 > f) {
                f = f2;
                i = i2;
                j = j2;
                k = k2;
                flag = true;
            }
        }
        if (flag) {
            this.pathEntity = this.world.a(this, i, j, k, 10.0f, true, false, false, true);
        }
        this.world.methodProfiler.b();
    }
    
    protected void a(final Entity entity, final float f) {
    }
    
    public float a(final int i, final int j, final int k) {
        return 0.0f;
    }
    
    protected Entity findTarget() {
        return null;
    }
    
    public boolean canSpawn() {
        final int i = MathHelper.floor(this.locX);
        final int j = MathHelper.floor(this.boundingBox.b);
        final int k = MathHelper.floor(this.locZ);
        return super.canSpawn() && this.a(i, j, k) >= 0.0f;
    }
    
    public boolean k() {
        return this.pathEntity != null;
    }
    
    public void setPathEntity(final PathEntity pathentity) {
        this.pathEntity = pathentity;
    }
    
    public Entity l() {
        return this.target;
    }
    
    public void setTarget(final Entity entity) {
        this.target = entity;
    }
    
    public float bE() {
        float f = super.bE();
        if (this.c > 0 && !this.bh()) {
            f *= 2.0f;
        }
        return f;
    }
}
