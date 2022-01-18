// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.Event;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.entity.Slime;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntitySlime extends EntityLiving implements IMonster
{
    private static final float[] e;
    public float b;
    public float c;
    public float d;
    private int jumpDelay;
    private Entity lastTarget;
    
    public EntitySlime(final World world) {
        super(world);
        this.jumpDelay = 0;
        this.texture = "/mob/slime.png";
        final int i = 1 << this.random.nextInt(3);
        this.height = 0.0f;
        this.jumpDelay = this.random.nextInt(20) + 10;
        this.setSize(i);
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte)1));
    }
    
    public void setSize(final int i) {
        final boolean updateMaxHealth = this.getMaxHealth() == this.maxHealth;
        this.datawatcher.watch(16, new Byte((byte)i));
        this.a(0.6f * i, 0.6f * i);
        this.setPosition(this.locX, this.locY, this.locZ);
        if (updateMaxHealth) {
            this.maxHealth = this.getMaxHealth();
        }
        this.setHealth(this.maxHealth);
        this.be = i;
    }
    
    public int getMaxHealth() {
        final int i = this.getSize();
        return i * i;
    }
    
    public int getSize() {
        return this.datawatcher.getByte(16);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Size", this.getSize() - 1);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSize(nbttagcompound.getInt("Size") + 1);
    }
    
    protected String h() {
        return "slime";
    }
    
    protected String n() {
        return "mob.slime." + ((this.getSize() > 1) ? "big" : "small");
    }
    
    public void l_() {
        if (!this.world.isStatic && this.world.difficulty == 0 && this.getSize() > 0) {
            this.dead = true;
        }
        this.c += (this.b - this.c) * 0.5f;
        this.d = this.c;
        final boolean flag = this.onGround;
        super.l_();
        if (this.onGround && !flag) {
            for (int i = this.getSize(), j = 0; j < i * 8; ++j) {
                final float f = this.random.nextFloat() * 3.1415927f * 2.0f;
                final float f2 = this.random.nextFloat() * 0.5f + 0.5f;
                final float f3 = MathHelper.sin(f) * i * 0.5f * f2;
                final float f4 = MathHelper.cos(f) * i * 0.5f * f2;
                this.world.addParticle(this.h(), this.locX + f3, this.boundingBox.b, this.locZ + f4, 0.0, 0.0, 0.0);
            }
            if (this.o()) {
                this.makeSound(this.n(), this.ba(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.b = -0.5f;
        }
        else if (!this.onGround && flag) {
            this.b = 1.0f;
        }
        this.k();
        if (this.world.isStatic) {
            final int i = this.getSize();
            this.a(0.6f * i, 0.6f * i);
        }
    }
    
    protected void bq() {
        this.bn();
        Entity entityhuman = this.world.findNearbyVulnerablePlayer(this, 16.0);
        EntityTargetEvent event = null;
        if (entityhuman != null && !entityhuman.equals(this.lastTarget)) {
            event = CraftEventFactory.callEntityTargetEvent(this, entityhuman, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
        }
        else if (this.lastTarget != null && entityhuman == null) {
            event = CraftEventFactory.callEntityTargetEvent(this, entityhuman, EntityTargetEvent.TargetReason.FORGOT_TARGET);
        }
        if (event != null && !event.isCancelled()) {
            entityhuman = ((event.getTarget() == null) ? null : ((CraftEntity)event.getTarget()).getHandle());
        }
        if ((this.lastTarget = entityhuman) != null) {
            this.a(entityhuman, 10.0f, 20.0f);
        }
        if (this.onGround && this.jumpDelay-- <= 0) {
            this.jumpDelay = this.j();
            if (entityhuman != null) {
                this.jumpDelay /= 3;
            }
            this.bG = true;
            if (this.q()) {
                this.makeSound(this.n(), this.ba(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.bD = 1.0f - this.random.nextFloat() * 2.0f;
            this.bE = 1 * this.getSize();
        }
        else {
            this.bG = false;
            if (this.onGround) {
                final float n = 0.0f;
                this.bE = n;
                this.bD = n;
            }
        }
    }
    
    protected void k() {
        this.b *= 0.6f;
    }
    
    protected int j() {
        return this.random.nextInt(20) + 10;
    }
    
    protected EntitySlime i() {
        return new EntitySlime(this.world);
    }
    
    public void die() {
        final int i = this.getSize();
        if (!this.world.isStatic && i > 1 && this.getHealth() <= 0) {
            int j = 2 + this.random.nextInt(3);
            final SlimeSplitEvent event = new SlimeSplitEvent((Slime)this.getBukkitEntity(), j);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled() || event.getCount() <= 0) {
                super.die();
                return;
            }
            j = event.getCount();
            for (int k = 0; k < j; ++k) {
                final float f = (k % 2 - 0.5f) * i / 4.0f;
                final float f2 = (k / 2 - 0.5f) * i / 4.0f;
                final EntitySlime entityslime = this.i();
                entityslime.setSize(i / 2);
                entityslime.setPositionRotation(this.locX + f, this.locY + 0.5, this.locZ + f2, this.random.nextFloat() * 360.0f, 0.0f);
                this.world.addEntity(entityslime, CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
            }
        }
        super.die();
    }
    
    public void b_(final EntityHuman entityhuman) {
        if (this.l()) {
            final int i = this.getSize();
            if (this.n(entityhuman) && this.e(entityhuman) < 0.6 * i * 0.6 * i && entityhuman.damageEntity(DamageSource.mobAttack(this), this.m())) {
                this.makeSound("mob.attack", 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }
    
    protected boolean l() {
        return this.getSize() > 1;
    }
    
    protected int m() {
        return this.getSize();
    }
    
    protected String bc() {
        return "mob.slime." + ((this.getSize() > 1) ? "big" : "small");
    }
    
    protected String bd() {
        return "mob.slime." + ((this.getSize() > 1) ? "big" : "small");
    }
    
    protected int getLootId() {
        return (this.getSize() == 1) ? Item.SLIME_BALL.id : 0;
    }
    
    public boolean canSpawn() {
        final Chunk chunk = this.world.getChunkAtWorldCoords(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
        if (this.world.getWorldData().getType() == WorldType.FLAT && this.random.nextInt(4) != 1) {
            return false;
        }
        if (this.getSize() == 1 || this.world.difficulty > 0) {
            final BiomeBase biomebase = this.world.getBiome(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
            if (biomebase == BiomeBase.SWAMPLAND && this.locY > 50.0 && this.locY < 70.0 && this.random.nextFloat() < 0.5f && this.random.nextFloat() < EntitySlime.e[this.world.w()] && this.world.getLightLevel(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) <= this.random.nextInt(8)) {
                return super.canSpawn();
            }
            if (this.random.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.locY < 40.0) {
                return super.canSpawn();
            }
        }
        return false;
    }
    
    protected float ba() {
        return 0.4f * this.getSize();
    }
    
    public int bs() {
        return 0;
    }
    
    protected boolean q() {
        return this.getSize() > 0;
    }
    
    protected boolean o() {
        return this.getSize() > 2;
    }
    
    static {
        e = new float[] { 1.0f, 0.75f, 0.5f, 0.25f, 0.0f, 0.25f, 0.5f, 0.75f };
    }
}
