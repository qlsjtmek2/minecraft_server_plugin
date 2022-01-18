// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import java.util.Iterator;
import java.util.List;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.entity.Painting;
import org.bukkit.event.Event;
import org.bukkit.entity.Hanging;
import org.bukkit.event.hanging.HangingBreakEvent;

public abstract class EntityHanging extends Entity
{
    private int e;
    public int direction;
    public int x;
    public int y;
    public int z;
    
    public EntityHanging(final World world) {
        super(world);
        this.e = 0;
        this.direction = 0;
        this.height = 0.0f;
        this.a(0.5f, 0.5f);
    }
    
    public EntityHanging(final World world, final int i, final int j, final int k, final int l) {
        this(world);
        this.x = i;
        this.y = j;
        this.z = k;
    }
    
    protected void a() {
    }
    
    public void setDirection(final int i) {
        this.direction = i;
        final float n = i * 90;
        this.yaw = n;
        this.lastYaw = n;
        float f = this.d();
        float f2 = this.g();
        float f3 = this.d();
        if (i != 2 && i != 0) {
            f = 0.5f;
        }
        else {
            f3 = 0.5f;
            final float n2 = Direction.f[i] * 90;
            this.lastYaw = n2;
            this.yaw = n2;
        }
        f /= 32.0f;
        f2 /= 32.0f;
        f3 /= 32.0f;
        float f4 = this.x + 0.5f;
        float f5 = this.y + 0.5f;
        float f6 = this.z + 0.5f;
        final float f7 = 0.5625f;
        if (i == 2) {
            f6 -= f7;
        }
        if (i == 1) {
            f4 -= f7;
        }
        if (i == 0) {
            f6 += f7;
        }
        if (i == 3) {
            f4 += f7;
        }
        if (i == 2) {
            f4 -= this.b(this.d());
        }
        if (i == 1) {
            f6 += this.b(this.d());
        }
        if (i == 0) {
            f4 += this.b(this.d());
        }
        if (i == 3) {
            f6 -= this.b(this.d());
        }
        f5 += this.b(this.g());
        this.setPosition(f4, f5, f6);
        final float f8 = -0.03125f;
        this.boundingBox.b(f4 - f - f8, f5 - f2 - f8, f6 - f3 - f8, f4 + f + f8, f5 + f2 + f8, f6 + f3 + f8);
    }
    
    private float b(final int i) {
        return (i == 32) ? 0.5f : ((i == 64) ? 0.5f : 0.0f);
    }
    
    public void l_() {
        if (this.e++ == 100 && !this.world.isStatic) {
            this.e = 0;
            if (!this.dead && !this.survives()) {
                final Material material = this.world.getMaterial((int)this.locX, (int)this.locY, (int)this.locZ);
                HangingBreakEvent.RemoveCause cause;
                if (!material.equals(Material.AIR)) {
                    cause = HangingBreakEvent.RemoveCause.OBSTRUCTION;
                }
                else {
                    cause = HangingBreakEvent.RemoveCause.PHYSICS;
                }
                final HangingBreakEvent event = new HangingBreakEvent((Hanging)this.getBukkitEntity(), cause);
                this.world.getServer().getPluginManager().callEvent(event);
                PaintingBreakEvent paintingEvent = null;
                if (this instanceof EntityPainting) {
                    paintingEvent = new PaintingBreakEvent((Painting)this.getBukkitEntity(), PaintingBreakEvent.RemoveCause.valueOf(cause.name()));
                    paintingEvent.setCancelled(event.isCancelled());
                    this.world.getServer().getPluginManager().callEvent(paintingEvent);
                }
                if (this.dead || event.isCancelled() || (paintingEvent != null && paintingEvent.isCancelled())) {
                    return;
                }
                this.die();
                this.h();
            }
        }
    }
    
    public boolean survives() {
        if (!this.world.getCubes(this, this.boundingBox).isEmpty()) {
            return false;
        }
        final int i = Math.max(1, this.d() / 16);
        final int j = Math.max(1, this.g() / 16);
        int k = this.x;
        int l = this.y;
        int i2 = this.z;
        if (this.direction == 2) {
            k = MathHelper.floor(this.locX - this.d() / 32.0f);
        }
        if (this.direction == 1) {
            i2 = MathHelper.floor(this.locZ - this.d() / 32.0f);
        }
        if (this.direction == 0) {
            k = MathHelper.floor(this.locX - this.d() / 32.0f);
        }
        if (this.direction == 3) {
            i2 = MathHelper.floor(this.locZ - this.d() / 32.0f);
        }
        l = MathHelper.floor(this.locY - this.g() / 32.0f);
        for (int j2 = 0; j2 < i; ++j2) {
            for (int k2 = 0; k2 < j; ++k2) {
                Material material;
                if (this.direction != 2 && this.direction != 0) {
                    material = this.world.getMaterial(this.x, l + k2, i2 + j2);
                }
                else {
                    material = this.world.getMaterial(k + j2, l + k2, this.z);
                }
                if (!material.isBuildable()) {
                    return false;
                }
            }
        }
        final List list = this.world.getEntities(this, this.boundingBox);
        for (final Entity entity : list) {
            if (entity instanceof EntityHanging) {
                return false;
            }
        }
        return true;
    }
    
    public boolean K() {
        return true;
    }
    
    public boolean j(final Entity entity) {
        return entity instanceof EntityHuman && this.damageEntity(DamageSource.playerAttack((EntityHuman)entity), 0);
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (!this.dead && !this.world.isStatic) {
            HangingBreakEvent event = new HangingBreakEvent((Hanging)this.getBukkitEntity(), HangingBreakEvent.RemoveCause.DEFAULT);
            PaintingBreakEvent paintingEvent = null;
            if (damagesource.getEntity() != null) {
                event = new HangingBreakByEntityEvent((Hanging)this.getBukkitEntity(), (damagesource.getEntity() == null) ? null : damagesource.getEntity().getBukkitEntity());
                if (this instanceof EntityPainting) {
                    paintingEvent = new PaintingBreakByEntityEvent((Painting)this.getBukkitEntity(), (damagesource.getEntity() == null) ? null : damagesource.getEntity().getBukkitEntity());
                }
            }
            else if (damagesource.c()) {
                event = new HangingBreakEvent((Hanging)this.getBukkitEntity(), HangingBreakEvent.RemoveCause.EXPLOSION);
            }
            this.world.getServer().getPluginManager().callEvent(event);
            if (paintingEvent != null) {
                paintingEvent.setCancelled(event.isCancelled());
                this.world.getServer().getPluginManager().callEvent(paintingEvent);
            }
            if (this.dead || event.isCancelled() || (paintingEvent != null && paintingEvent.isCancelled())) {
                return true;
            }
            this.die();
            this.J();
            EntityHuman entityhuman = null;
            if (damagesource.getEntity() instanceof EntityHuman) {
                entityhuman = (EntityHuman)damagesource.getEntity();
            }
            if (entityhuman != null && entityhuman.abilities.canInstantlyBuild) {
                return true;
            }
            this.h();
        }
        return true;
    }
    
    public void move(final double d0, final double d1, final double d2) {
        if (!this.world.isStatic && !this.dead && d0 * d0 + d1 * d1 + d2 * d2 > 0.0) {
            if (this.dead) {
                return;
            }
            final HangingBreakEvent event = new HangingBreakEvent((Hanging)this.getBukkitEntity(), HangingBreakEvent.RemoveCause.PHYSICS);
            this.world.getServer().getPluginManager().callEvent(event);
            PaintingBreakEvent paintingEvent = null;
            if (this instanceof EntityPainting) {
                paintingEvent = new PaintingBreakEvent((Painting)this.getBukkitEntity(), PaintingBreakEvent.RemoveCause.valueOf(event.getCause().name()));
                paintingEvent.setCancelled(event.isCancelled());
                this.world.getServer().getPluginManager().callEvent(paintingEvent);
            }
            if (event.isCancelled() || (paintingEvent != null && paintingEvent.isCancelled())) {
                return;
            }
            this.die();
            this.h();
        }
    }
    
    public void g(final double d0, final double d1, final double d2) {
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setByte("Direction", (byte)this.direction);
        nbttagcompound.setInt("TileX", this.x);
        nbttagcompound.setInt("TileY", this.y);
        nbttagcompound.setInt("TileZ", this.z);
        switch (this.direction) {
            case 0: {
                nbttagcompound.setByte("Dir", (byte)2);
                break;
            }
            case 1: {
                nbttagcompound.setByte("Dir", (byte)1);
                break;
            }
            case 2: {
                nbttagcompound.setByte("Dir", (byte)0);
                break;
            }
            case 3: {
                nbttagcompound.setByte("Dir", (byte)3);
                break;
            }
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("Direction")) {
            this.direction = nbttagcompound.getByte("Direction");
        }
        else {
            switch (nbttagcompound.getByte("Dir")) {
                case 0: {
                    this.direction = 2;
                    break;
                }
                case 1: {
                    this.direction = 1;
                    break;
                }
                case 2: {
                    this.direction = 0;
                    break;
                }
                case 3: {
                    this.direction = 3;
                    break;
                }
            }
        }
        this.x = nbttagcompound.getInt("TileX");
        this.y = nbttagcompound.getInt("TileY");
        this.z = nbttagcompound.getInt("TileZ");
        this.setDirection(this.direction);
    }
    
    public abstract int d();
    
    public abstract int g();
    
    public abstract void h();
}
