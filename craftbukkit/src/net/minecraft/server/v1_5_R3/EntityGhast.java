// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityGhast extends EntityFlying implements IMonster
{
    public int b;
    public double c;
    public double d;
    public double e;
    private Entity target;
    private int i;
    public int f;
    public int g;
    private int explosionPower;
    
    public EntityGhast(final World world) {
        super(world);
        this.b = 0;
        this.target = null;
        this.i = 0;
        this.f = 0;
        this.g = 0;
        this.explosionPower = 1;
        this.texture = "/mob/ghast.png";
        this.a(4.0f, 4.0f);
        this.fireProof = true;
        this.be = 5;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if ("fireball".equals(damagesource.n()) && damagesource.getEntity() instanceof EntityHuman) {
            super.damageEntity(damagesource, 1000);
            ((EntityHuman)damagesource.getEntity()).a(AchievementList.y);
            return true;
        }
        return super.damageEntity(damagesource, i);
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, (Object)(byte)0);
    }
    
    public int getMaxHealth() {
        return 10;
    }
    
    public void l_() {
        super.l_();
        final byte b0 = this.datawatcher.getByte(16);
        this.texture = ((b0 == 1) ? "/mob/ghast_fire.png" : "/mob/ghast.png");
    }
    
    protected void bq() {
        if (!this.world.isStatic && this.world.difficulty == 0) {
            this.die();
        }
        this.bn();
        this.f = this.g;
        final double d0 = this.c - this.locX;
        final double d2 = this.d - this.locY;
        final double d3 = this.e - this.locZ;
        double d4 = d0 * d0 + d2 * d2 + d3 * d3;
        if (d4 < 1.0 || d4 > 3600.0) {
            this.c = this.locX + (this.random.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.d = this.locY + (this.random.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.e = this.locZ + (this.random.nextFloat() * 2.0f - 1.0f) * 16.0f;
        }
        if (this.b-- <= 0) {
            this.b += this.random.nextInt(5) + 2;
            d4 = MathHelper.sqrt(d4);
            if (this.a(this.c, this.d, this.e, d4)) {
                this.motX += d0 / d4 * 0.1;
                this.motY += d2 / d4 * 0.1;
                this.motZ += d3 / d4 * 0.1;
            }
            else {
                this.c = this.locX;
                this.d = this.locY;
                this.e = this.locZ;
            }
        }
        if (this.target != null && this.target.dead) {
            final EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
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
        if (this.target == null || this.i-- <= 0) {
            final Entity target = this.world.findNearbyVulnerablePlayer(this, 100.0);
            if (target != null) {
                final EntityTargetEvent event2 = new EntityTargetEvent(this.getBukkitEntity(), target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
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
            if (this.target != null) {
                this.i = 20;
            }
        }
        final double d5 = 64.0;
        if (this.target != null && this.target.e(this) < d5 * d5) {
            final double d6 = this.target.locX - this.locX;
            final double d7 = this.target.boundingBox.b + this.target.length / 2.0f - (this.locY + this.length / 2.0f);
            final double d8 = this.target.locZ - this.locZ;
            final float n = -(float)Math.atan2(d6, d8) * 180.0f / 3.1415927f;
            this.yaw = n;
            this.ay = n;
            if (this.n(this.target)) {
                if (this.g == 10) {
                    this.world.a(null, 1007, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                }
                ++this.g;
                if (this.g == 20) {
                    this.world.a(null, 1008, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                    final EntityLargeFireball entityLargeFireball2;
                    final EntityLargeFireball entityLargeFireball;
                    final EntityLargeFireball entitylargefireball = entityLargeFireball = (entityLargeFireball2 = new EntityLargeFireball(this.world, this, d6, d7, d8));
                    final int explosionPower = this.explosionPower;
                    entityLargeFireball.e = explosionPower;
                    entityLargeFireball2.yield = explosionPower;
                    final double d9 = 4.0;
                    final Vec3D vec3d = this.i(1.0f);
                    entitylargefireball.locX = this.locX + vec3d.c * d9;
                    entitylargefireball.locY = this.locY + this.length / 2.0f + 0.5;
                    entitylargefireball.locZ = this.locZ + vec3d.e * d9;
                    this.world.addEntity(entitylargefireball);
                    this.g = -40;
                }
            }
            else if (this.g > 0) {
                --this.g;
            }
        }
        else {
            final float n2 = -(float)Math.atan2(this.motX, this.motZ) * 180.0f / 3.1415927f;
            this.yaw = n2;
            this.ay = n2;
            if (this.g > 0) {
                --this.g;
            }
        }
        if (!this.world.isStatic) {
            final byte b0 = this.datawatcher.getByte(16);
            final byte b2 = (byte)((this.g > 10) ? 1 : 0);
            if (b0 != b2) {
                this.datawatcher.watch(16, b2);
            }
        }
    }
    
    private boolean a(final double d0, final double d1, final double d2, final double d3) {
        final double d4 = (this.c - this.locX) / d3;
        final double d5 = (this.d - this.locY) / d3;
        final double d6 = (this.e - this.locZ) / d3;
        final AxisAlignedBB axisalignedbb = this.boundingBox.clone();
        for (int i = 1; i < d3; ++i) {
            axisalignedbb.d(d4, d5, d6);
            if (!this.world.getCubes(this, axisalignedbb).isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    protected String bb() {
        return "mob.ghast.moan";
    }
    
    protected String bc() {
        return "mob.ghast.scream";
    }
    
    protected String bd() {
        return "mob.ghast.death";
    }
    
    protected int getLootId() {
        return Item.SULPHUR.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        int j = this.random.nextInt(2) + this.random.nextInt(1 + i);
        if (j > 0) {
            loot.add(CraftItemStack.asNewCraftStack(Item.GHAST_TEAR, j));
        }
        j = this.random.nextInt(3) + this.random.nextInt(1 + i);
        if (j > 0) {
            loot.add(CraftItemStack.asNewCraftStack(Item.SULPHUR, j));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    protected float ba() {
        return 10.0f;
    }
    
    public boolean canSpawn() {
        return this.random.nextInt(20) == 0 && super.canSpawn() && this.world.difficulty > 0;
    }
    
    public int by() {
        return 1;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("ExplosionPower", this.explosionPower);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("ExplosionPower")) {
            this.explosionPower = nbttagcompound.getInt("ExplosionPower");
        }
    }
}
