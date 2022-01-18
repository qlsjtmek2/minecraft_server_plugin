// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityWither extends EntityMonster implements IRangedEntity
{
    private float[] d;
    private float[] e;
    private float[] f;
    private float[] g;
    private int[] h;
    private int[] i;
    private int j;
    private static final IEntitySelector bK;
    
    public EntityWither(final World world) {
        super(world);
        this.d = new float[2];
        this.e = new float[2];
        this.f = new float[2];
        this.g = new float[2];
        this.h = new int[2];
        this.i = new int[2];
        this.setHealth(this.getMaxHealth());
        this.texture = "/mob/wither.png";
        this.a(0.9f, 4.0f);
        this.fireProof = true;
        this.bI = 0.6f;
        this.getNavigation().e(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.bI, 40, 20.0f));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 30.0f, 0, false, false, EntityWither.bK));
        this.be = 50;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Integer(100));
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(0));
        this.datawatcher.a(19, new Integer(0));
        this.datawatcher.a(20, new Integer(0));
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Invul", this.n());
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.t(nbttagcompound.getInt("Invul"));
        this.datawatcher.watch(16, this.health);
    }
    
    protected String bb() {
        return "mob.wither.idle";
    }
    
    protected String bc() {
        return "mob.wither.hurt";
    }
    
    protected String bd() {
        return "mob.wither.death";
    }
    
    public void c() {
        if (!this.world.isStatic) {
            this.datawatcher.watch(16, this.getScaledHealth());
        }
        this.motY *= 0.6000000238418579;
        if (!this.world.isStatic && this.u(0) > 0) {
            final Entity entity = this.world.getEntity(this.u(0));
            if (entity != null) {
                if (this.locY < entity.locY || (!this.o() && this.locY < entity.locY + 5.0)) {
                    if (this.motY < 0.0) {
                        this.motY = 0.0;
                    }
                    this.motY += (0.5 - this.motY) * 0.6000000238418579;
                }
                final double d3 = entity.locX - this.locX;
                final double d4 = entity.locZ - this.locZ;
                final double d5 = d3 * d3 + d4 * d4;
                if (d5 > 9.0) {
                    final double d6 = MathHelper.sqrt(d5);
                    this.motX += (d3 / d6 * 0.5 - this.motX) * 0.6000000238418579;
                    this.motZ += (d4 / d6 * 0.5 - this.motZ) * 0.6000000238418579;
                }
            }
        }
        if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806) {
            this.yaw = (float)Math.atan2(this.motZ, this.motX) * 57.295776f - 90.0f;
        }
        super.c();
        for (int i = 0; i < 2; ++i) {
            this.g[i] = this.e[i];
            this.f[i] = this.d[i];
        }
        for (int i = 0; i < 2; ++i) {
            final int j = this.u(i + 1);
            Entity entity2 = null;
            if (j > 0) {
                entity2 = this.world.getEntity(j);
            }
            if (entity2 != null) {
                final double d4 = this.v(i + 1);
                final double d5 = this.w(i + 1);
                final double d6 = this.x(i + 1);
                final double d7 = entity2.locX - d4;
                final double d8 = entity2.locY + entity2.getHeadHeight() - d5;
                final double d9 = entity2.locZ - d6;
                final double d10 = MathHelper.sqrt(d7 * d7 + d9 * d9);
                final float f = (float)(Math.atan2(d9, d7) * 180.0 / 3.1415927410125732) - 90.0f;
                final float f2 = (float)(-(Math.atan2(d8, d10) * 180.0 / 3.1415927410125732));
                this.d[i] = this.b(this.d[i], f2, 40.0f);
                this.e[i] = this.b(this.e[i], f, 10.0f);
            }
            else {
                this.e[i] = this.b(this.e[i], this.ay, 10.0f);
            }
        }
        final boolean flag = this.o();
        for (int j = 0; j < 3; ++j) {
            final double d11 = this.v(j);
            final double d12 = this.w(j);
            final double d13 = this.x(j);
            this.world.addParticle("smoke", d11 + this.random.nextGaussian() * 0.30000001192092896, d12 + this.random.nextGaussian() * 0.30000001192092896, d13 + this.random.nextGaussian() * 0.30000001192092896, 0.0, 0.0, 0.0);
            if (flag && this.world.random.nextInt(4) == 0) {
                this.world.addParticle("mobSpell", d11 + this.random.nextGaussian() * 0.30000001192092896, d12 + this.random.nextGaussian() * 0.30000001192092896, d13 + this.random.nextGaussian() * 0.30000001192092896, 0.699999988079071, 0.699999988079071, 0.5);
            }
        }
        if (this.n() > 0) {
            for (int j = 0; j < 3; ++j) {
                this.world.addParticle("mobSpell", this.locX + this.random.nextGaussian() * 1.0, this.locY + this.random.nextFloat() * 3.3f, this.locZ + this.random.nextGaussian() * 1.0, 0.699999988079071, 0.699999988079071, 0.8999999761581421);
            }
        }
    }
    
    protected void bo() {
        if (this.n() > 0) {
            final int i = this.n() - 1;
            if (i <= 0) {
                final ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), 7.0f, false);
                this.world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    this.world.createExplosion(this, this.locX, this.locY + this.getHeadHeight(), this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
                }
                this.world.d(1013, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
            }
            this.t(i);
            if (this.ticksLived % 10 == 0) {
                this.heal(10, EntityRegainHealthEvent.RegainReason.WITHER_SPAWN);
            }
        }
        else {
            super.bo();
            for (int i = 1; i < 3; ++i) {
                if (this.ticksLived >= this.h[i - 1]) {
                    this.h[i - 1] = this.ticksLived + 10 + this.random.nextInt(10);
                    if (this.world.difficulty >= 2) {
                        final int i2 = i - 1;
                        final int i3 = this.i[i - 1];
                        this.i[i2] = this.i[i - 1] + 1;
                        if (i3 > 15) {
                            final float f = 10.0f;
                            final float f2 = 5.0f;
                            final double d0 = MathHelper.a(this.random, this.locX - f, this.locX + f);
                            final double d2 = MathHelper.a(this.random, this.locY - f2, this.locY + f2);
                            final double d3 = MathHelper.a(this.random, this.locZ - f, this.locZ + f);
                            this.a(i + 1, d0, d2, d3, true);
                            this.i[i - 1] = 0;
                        }
                    }
                    final int j = this.u(i);
                    if (j > 0) {
                        final Entity entity = this.world.getEntity(j);
                        if (entity != null && entity.isAlive() && this.e(entity) <= 900.0 && this.n(entity)) {
                            this.a(i + 1, (EntityLiving)entity);
                            this.h[i - 1] = this.ticksLived + 40 + this.random.nextInt(20);
                            this.i[i - 1] = 0;
                        }
                        else {
                            this.c(i, 0);
                        }
                    }
                    else {
                        final List list = this.world.a(EntityLiving.class, this.boundingBox.grow(20.0, 8.0, 20.0), EntityWither.bK);
                        int i4 = 0;
                        while (i4 < 10 && !list.isEmpty()) {
                            final EntityLiving entityliving = list.get(this.random.nextInt(list.size()));
                            if (entityliving != this && entityliving.isAlive() && this.n(entityliving)) {
                                if (!(entityliving instanceof EntityHuman)) {
                                    this.c(i, entityliving.id);
                                    break;
                                }
                                if (!((EntityHuman)entityliving).abilities.isInvulnerable) {
                                    this.c(i, entityliving.id);
                                    break;
                                }
                                break;
                            }
                            else {
                                list.remove(entityliving);
                                ++i4;
                            }
                        }
                    }
                }
            }
            if (this.getGoalTarget() != null) {
                this.c(0, this.getGoalTarget().id);
            }
            else {
                this.c(0, 0);
            }
            if (this.j > 0) {
                --this.j;
                if (this.j == 0 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    final int i = MathHelper.floor(this.locY);
                    final int j = MathHelper.floor(this.locX);
                    final int j2 = MathHelper.floor(this.locZ);
                    boolean flag = false;
                    for (int k1 = -1; k1 <= 1; ++k1) {
                        for (int l1 = -1; l1 <= 1; ++l1) {
                            for (int i5 = 0; i5 <= 3; ++i5) {
                                final int j3 = j + k1;
                                final int k2 = i + i5;
                                final int l2 = j2 + l1;
                                final int i6 = this.world.getTypeId(j3, k2, l2);
                                if (i6 > 0 && i6 != Block.BEDROCK.id && i6 != Block.ENDER_PORTAL.id && i6 != Block.ENDER_PORTAL_FRAME.id) {
                                    if (!CraftEventFactory.callEntityChangeBlockEvent(this, j3, k2, l2, 0, 0).isCancelled()) {
                                        flag = (this.world.setAir(j3, k2, l2, true) || flag);
                                    }
                                }
                            }
                        }
                    }
                    if (flag) {
                        this.world.a(null, 1012, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                    }
                }
            }
            if (this.ticksLived % 20 == 0) {
                this.heal(1);
            }
        }
    }
    
    public void m() {
        this.t(220);
        this.setHealth(this.getMaxHealth() / 3);
    }
    
    public void al() {
    }
    
    public int aZ() {
        return 4;
    }
    
    private double v(final int i) {
        if (i <= 0) {
            return this.locX;
        }
        final float f = (this.ay + 180 * (i - 1)) / 180.0f * 3.1415927f;
        final float f2 = MathHelper.cos(f);
        return this.locX + f2 * 1.3;
    }
    
    private double w(final int i) {
        return (i <= 0) ? (this.locY + 3.0) : (this.locY + 2.2);
    }
    
    private double x(final int i) {
        if (i <= 0) {
            return this.locZ;
        }
        final float f = (this.ay + 180 * (i - 1)) / 180.0f * 3.1415927f;
        final float f2 = MathHelper.sin(f);
        return this.locZ + f2 * 1.3;
    }
    
    private float b(final float f, final float f1, final float f2) {
        float f3 = MathHelper.g(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }
    
    private void a(final int i, final EntityLiving entityliving) {
        this.a(i, entityliving.locX, entityliving.locY + entityliving.getHeadHeight() * 0.5, entityliving.locZ, i == 0 && this.random.nextFloat() < 0.001f);
    }
    
    private void a(final int i, final double d0, final double d1, final double d2, final boolean flag) {
        this.world.a(null, 1014, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
        final double d3 = this.v(i);
        final double d4 = this.w(i);
        final double d5 = this.x(i);
        final double d6 = d0 - d3;
        final double d7 = d1 - d4;
        final double d8 = d2 - d5;
        final EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, this, d6, d7, d8);
        if (flag) {
            entitywitherskull.a(true);
        }
        entitywitherskull.locY = d4;
        entitywitherskull.locX = d3;
        entitywitherskull.locZ = d5;
        this.world.addEntity(entitywitherskull);
    }
    
    public void a(final EntityLiving entityliving, final float f) {
        this.a(0, entityliving);
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (damagesource == DamageSource.DROWN) {
            return false;
        }
        if (this.n() > 0) {
            return false;
        }
        if (this.o()) {
            final Entity entity = damagesource.h();
            if (entity instanceof EntityArrow) {
                return false;
            }
        }
        final Entity entity = damagesource.getEntity();
        if (entity != null && !(entity instanceof EntityHuman) && entity instanceof EntityLiving && ((EntityLiving)entity).getMonsterType() == this.getMonsterType()) {
            return false;
        }
        if (this.j <= 0) {
            this.j = 20;
        }
        for (int j = 0; j < this.i.length; ++j) {
            final int[] k = this.i;
            final int n = j;
            k[n] += 3;
        }
        return super.damageEntity(damagesource, i);
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        loot.add(new ItemStack(Item.NETHER_STAR.id, 1));
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    protected void bn() {
        this.bC = 0;
    }
    
    public boolean K() {
        return !this.dead;
    }
    
    public int b() {
        return this.datawatcher.getInt(16);
    }
    
    protected void a(final float f) {
    }
    
    public void addEffect(final MobEffect mobeffect) {
    }
    
    protected boolean bh() {
        return true;
    }
    
    public int getMaxHealth() {
        return 300;
    }
    
    public int n() {
        return this.datawatcher.getInt(20);
    }
    
    public void t(final int i) {
        this.datawatcher.watch(20, i);
    }
    
    public int u(final int i) {
        return this.datawatcher.getInt(17 + i);
    }
    
    public void c(final int i, final int j) {
        this.datawatcher.watch(17 + i, j);
    }
    
    public boolean o() {
        return this.b() <= this.maxHealth / 2;
    }
    
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }
    
    public void mount(final Entity entity) {
        this.vehicle = null;
    }
    
    static {
        bK = new EntitySelectorNotUndead();
    }
}
