// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import java.util.ArrayList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.craftbukkit.v1_5_R3.TrigMath;
import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import java.util.Random;
import java.util.HashMap;

public abstract class EntityLiving extends Entity
{
    private static final float[] b;
    private static final float[] c;
    private static final float[] d;
    public static final float[] au;
    public int maxNoDamageTicks;
    public float aw;
    public float ax;
    public float ay;
    public float az;
    public float aA;
    public float aB;
    protected float aC;
    protected float aD;
    protected float aE;
    protected float aF;
    protected boolean aG;
    protected String texture;
    protected boolean aI;
    protected float aJ;
    protected String aK;
    protected float aL;
    protected int aM;
    protected float aN;
    public float aO;
    public float aP;
    public float aQ;
    public float aR;
    protected int health;
    public int aT;
    protected int aU;
    public int aV;
    public int hurtTicks;
    public int aX;
    public float aY;
    public int deathTicks;
    public int attackTicks;
    public float bb;
    public float bc;
    protected boolean bd;
    protected int be;
    public int bf;
    public float bg;
    public float bh;
    public float bi;
    public float bj;
    public EntityHuman killer;
    protected int lastDamageByPlayerTime;
    public EntityLiving lastDamager;
    private int f;
    private EntityLiving g;
    public int bm;
    public HashMap effects;
    public boolean updateEffects;
    private int i;
    private ControllerLook lookController;
    private ControllerMove moveController;
    private ControllerJump jumpController;
    private EntityAIBodyControl senses;
    private Navigation navigation;
    protected final PathfinderGoalSelector goalSelector;
    protected final PathfinderGoalSelector targetSelector;
    private EntityLiving goalTarget;
    private EntitySenses bP;
    private float bQ;
    private ChunkCoordinates bR;
    private float bS;
    private ItemStack[] equipment;
    public float[] dropChances;
    private ItemStack[] bU;
    public boolean br;
    public int bs;
    public boolean canPickUpLoot;
    public boolean persistent;
    protected CombatTracker bt;
    protected int bu;
    protected double bv;
    protected double bw;
    protected double bx;
    protected double by;
    protected double bz;
    float bA;
    public int lastDamage;
    protected int bC;
    protected float bD;
    protected float bE;
    protected float bF;
    protected boolean bG;
    protected float bH;
    protected float bI;
    private int bX;
    private Entity bY;
    protected int bJ;
    public int expToDrop;
    public int maxAirTicks;
    public int maxHealth;
    
    public void inactiveTick() {
        super.inactiveTick();
        ++this.bC;
    }
    
    public EntityLiving(final World world) {
        super(world);
        this.maxNoDamageTicks = 20;
        this.ay = 0.0f;
        this.az = 0.0f;
        this.aA = 0.0f;
        this.aB = 0.0f;
        this.aG = true;
        this.texture = "/mob/char.png";
        this.aI = true;
        this.aJ = 0.0f;
        this.aK = null;
        this.aL = 1.0f;
        this.aM = 0;
        this.aN = 0.0f;
        this.aO = 0.1f;
        this.aP = 0.02f;
        this.health = this.getMaxHealth();
        this.aY = 0.0f;
        this.deathTicks = 0;
        this.attackTicks = 0;
        this.bd = false;
        this.bf = -1;
        this.bg = (float)(Math.random() * 0.8999999761581421 + 0.10000000149011612);
        this.killer = null;
        this.lastDamageByPlayerTime = 0;
        this.lastDamager = null;
        this.f = 0;
        this.g = null;
        this.bm = 0;
        this.effects = new HashMap();
        this.updateEffects = true;
        this.bR = new ChunkCoordinates(0, 0, 0);
        this.bS = -1.0f;
        this.equipment = new ItemStack[5];
        this.dropChances = new float[5];
        this.bU = new ItemStack[5];
        this.br = false;
        this.bs = 0;
        this.canPickUpLoot = false;
        this.persistent = !this.isTypeNotPersistent();
        this.bt = new CombatTracker(this);
        this.bA = 0.0f;
        this.lastDamage = 0;
        this.bC = 0;
        this.bG = false;
        this.bH = 0.0f;
        this.bI = 0.7f;
        this.bX = 0;
        this.bJ = 0;
        this.expToDrop = 0;
        this.maxAirTicks = 300;
        this.maxHealth = this.getMaxHealth();
        this.m = true;
        this.goalSelector = new PathfinderGoalSelector((world != null && world.methodProfiler != null) ? world.methodProfiler : null);
        this.targetSelector = new PathfinderGoalSelector((world != null && world.methodProfiler != null) ? world.methodProfiler : null);
        this.lookController = new ControllerLook(this);
        this.moveController = new ControllerMove(this);
        this.jumpController = new ControllerJump(this);
        this.senses = new EntityAIBodyControl(this);
        this.navigation = new Navigation(this, world, this.ay());
        this.bP = new EntitySenses(this);
        this.ax = (float)(Math.random() + 1.0) * 0.01f;
        this.setPosition(this.locX, this.locY, this.locZ);
        this.aw = (float)Math.random() * 12398.0f;
        this.yaw = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.aA = this.yaw;
        for (int i = 0; i < this.dropChances.length; ++i) {
            this.dropChances[i] = 0.085f;
        }
        this.Y = 0.5f;
    }
    
    protected int ay() {
        return 16;
    }
    
    public ControllerLook getControllerLook() {
        return this.lookController;
    }
    
    public ControllerMove getControllerMove() {
        return this.moveController;
    }
    
    public ControllerJump getControllerJump() {
        return this.jumpController;
    }
    
    public Navigation getNavigation() {
        return this.navigation;
    }
    
    public EntitySenses getEntitySenses() {
        return this.bP;
    }
    
    public Random aE() {
        return this.random;
    }
    
    public EntityLiving aF() {
        return this.lastDamager;
    }
    
    public EntityLiving aG() {
        return this.g;
    }
    
    public void l(final Entity entity) {
        if (entity instanceof EntityLiving) {
            this.g = (EntityLiving)entity;
        }
    }
    
    public int aH() {
        return this.bC;
    }
    
    public float getHeadRotation() {
        return this.aA;
    }
    
    public float aI() {
        return this.bQ;
    }
    
    public void e(final float f) {
        this.f(this.bQ = f);
    }
    
    public boolean m(final Entity entity) {
        this.l(entity);
        return false;
    }
    
    public EntityLiving getGoalTarget() {
        return this.goalTarget;
    }
    
    public void setGoalTarget(final EntityLiving entityliving) {
        this.goalTarget = entityliving;
    }
    
    public boolean a(final Class oclass) {
        return EntityCreeper.class != oclass && EntityGhast.class != oclass;
    }
    
    public void aK() {
    }
    
    protected void a(final double d0, final boolean flag) {
        if (!this.G()) {
            this.H();
        }
        if (flag && this.fallDistance > 0.0f) {
            final int i = MathHelper.floor(this.locX);
            final int j = MathHelper.floor(this.locY - 0.20000000298023224 - this.height);
            final int k = MathHelper.floor(this.locZ);
            int l = this.world.getTypeId(i, j, k);
            if (l == 0) {
                final int i2 = this.world.e(i, j - 1, k);
                if (i2 == 11 || i2 == 32 || i2 == 21) {
                    l = this.world.getTypeId(i, j - 1, k);
                }
            }
            if (l > 0) {
                Block.byId[l].a(this.world, i, j, k, this, this.fallDistance);
            }
        }
        super.a(d0, flag);
    }
    
    public boolean aL() {
        return this.d(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
    }
    
    public boolean d(final int i, final int j, final int k) {
        return this.bS == -1.0f || this.bR.e(i, j, k) < this.bS * this.bS;
    }
    
    public void b(final int i, final int j, final int k, final int l) {
        this.bR.b(i, j, k);
        this.bS = l;
    }
    
    public ChunkCoordinates aM() {
        return this.bR;
    }
    
    public float aN() {
        return this.bS;
    }
    
    public void aO() {
        this.bS = -1.0f;
    }
    
    public boolean aP() {
        return this.bS != -1.0f;
    }
    
    public void c(final EntityLiving entityliving) {
        this.lastDamager = entityliving;
        this.f = ((this.lastDamager != null) ? 100 : 0);
    }
    
    protected void a() {
        this.datawatcher.a(8, (Object)this.i);
        this.datawatcher.a(9, (Object)(byte)0);
        this.datawatcher.a(10, (Object)(byte)0);
        this.datawatcher.a(6, (Object)(byte)0);
        this.datawatcher.a(5, "");
    }
    
    public boolean n(final Entity entity) {
        return this.world.a(this.world.getVec3DPool().create(this.locX, this.locY + this.getHeadHeight(), this.locZ), this.world.getVec3DPool().create(entity.locX, entity.locY + entity.getHeadHeight(), entity.locZ)) == null;
    }
    
    public boolean K() {
        return !this.dead;
    }
    
    public boolean L() {
        return !this.dead;
    }
    
    public float getHeadHeight() {
        return this.length * 0.85f;
    }
    
    public int aQ() {
        return 80;
    }
    
    public void aR() {
        final String s = this.bb();
        if (s != null) {
            this.makeSound(s, this.ba(), this.aY());
        }
    }
    
    public void x() {
        this.aQ = this.aR;
        super.x();
        this.world.methodProfiler.a("mobBaseTick");
        if (this.isAlive() && this.random.nextInt(1000) < this.aV++) {
            this.aV = -this.aQ();
            this.aR();
        }
        if (this.isAlive() && this.inBlock()) {
            this.damageEntity(DamageSource.STUCK, 1);
        }
        if (this.isFireproof() || this.world.isStatic) {
            this.extinguish();
        }
        final boolean flag = this instanceof EntityHuman && ((EntityHuman)this).abilities.isInvulnerable;
        if (this.isAlive() && this.a(Material.WATER) && !this.bf() && !this.effects.containsKey(MobEffectList.WATER_BREATHING.id) && !flag) {
            this.setAirTicks(this.h(this.getAirTicks()));
            if (this.getAirTicks() == -20) {
                this.setAirTicks(0);
                for (int i = 0; i < 8; ++i) {
                    final float f = this.random.nextFloat() - this.random.nextFloat();
                    final float f2 = this.random.nextFloat() - this.random.nextFloat();
                    final float f3 = this.random.nextFloat() - this.random.nextFloat();
                    this.world.addParticle("bubble", this.locX + f, this.locY + f2, this.locZ + f3, this.motX, this.motY, this.motZ);
                }
                this.damageEntity(DamageSource.DROWN, 2);
            }
            this.extinguish();
        }
        else if (this.getAirTicks() != 300) {
            this.setAirTicks(this.maxAirTicks);
        }
        this.bb = this.bc;
        if (this.attackTicks > 0) {
            --this.attackTicks;
        }
        if (this.hurtTicks > 0) {
            --this.hurtTicks;
        }
        if (this.noDamageTicks > 0) {
            --this.noDamageTicks;
        }
        if (this.health <= 0) {
            this.aS();
        }
        if (this.lastDamageByPlayerTime > 0) {
            --this.lastDamageByPlayerTime;
        }
        else {
            this.killer = null;
        }
        if (this.g != null && !this.g.isAlive()) {
            this.g = null;
        }
        if (this.lastDamager != null) {
            if (!this.lastDamager.isAlive()) {
                this.c((EntityLiving)null);
            }
            else if (this.f > 0) {
                --this.f;
            }
            else {
                this.c((EntityLiving)null);
            }
        }
        this.bA();
        this.aF = this.aE;
        this.az = this.ay;
        this.aB = this.aA;
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        this.world.methodProfiler.b();
    }
    
    public int getExpReward() {
        final int exp = this.getExpValue(this.killer);
        if (!this.world.isStatic && (this.lastDamageByPlayerTime > 0 || this.alwaysGivesExp()) && !this.isBaby()) {
            return exp;
        }
        return 0;
    }
    
    public int getScaledHealth() {
        if (this.maxHealth != this.getMaxHealth() && this.getHealth() > 0) {
            final int health = this.getHealth() / this.maxHealth * this.getMaxHealth();
            return (health > 0) ? health : 1;
        }
        return this.getHealth();
    }
    
    protected void aS() {
        ++this.deathTicks;
        if (this.deathTicks >= 20 && !this.dead) {
            int i = this.expToDrop;
            while (i > 0) {
                final int j = EntityExperienceOrb.getOrbValue(i);
                i -= j;
                this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
            }
            this.expToDrop = 0;
            this.die();
            for (i = 0; i < 20; ++i) {
                final double d0 = this.random.nextGaussian() * 0.02;
                final double d2 = this.random.nextGaussian() * 0.02;
                final double d3 = this.random.nextGaussian() * 0.02;
                this.world.addParticle("explode", this.locX + this.random.nextFloat() * this.width * 2.0f - this.width, this.locY + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0f - this.width, d0, d2, d3);
            }
        }
    }
    
    protected int h(final int i) {
        final int j = EnchantmentManager.getOxygenEnchantmentLevel(this);
        return (j > 0 && this.random.nextInt(j + 1) > 0) ? i : (i - 1);
    }
    
    protected int getExpValue(final EntityHuman entityhuman) {
        if (this.be > 0) {
            int i = this.be;
            final ItemStack[] aitemstack = this.getEquipment();
            for (int j = 0; j < aitemstack.length; ++j) {
                if (aitemstack[j] != null && this.dropChances[j] <= 1.0f) {
                    i += 1 + this.random.nextInt(3);
                }
            }
            return i;
        }
        return this.be;
    }
    
    protected boolean alwaysGivesExp() {
        return false;
    }
    
    public void aU() {
        for (int i = 0; i < 20; ++i) {
            final double d0 = this.random.nextGaussian() * 0.02;
            final double d2 = this.random.nextGaussian() * 0.02;
            final double d3 = this.random.nextGaussian() * 0.02;
            final double d4 = 10.0;
            this.world.addParticle("explode", this.locX + this.random.nextFloat() * this.width * 2.0f - this.width - d0 * d4, this.locY + this.random.nextFloat() * this.length - d2 * d4, this.locZ + this.random.nextFloat() * this.width * 2.0f - this.width - d3 * d4, d0, d2, d3);
        }
    }
    
    public void T() {
        super.T();
        this.aC = this.aD;
        this.aD = 0.0f;
        this.fallDistance = 0.0f;
    }
    
    public void l_() {
        SpigotTimings.timerEntityBaseTick.startTiming();
        super.l_();
        if (!this.world.isStatic) {
            for (int i = 0; i < 5; ++i) {
                final ItemStack itemstack = this.getEquipment(i);
                if (!ItemStack.matches(itemstack, this.bU[i])) {
                    ((WorldServer)this.world).getTracker().a(this, new Packet5EntityEquipment(this.id, i, itemstack));
                    this.bU[i] = ((itemstack == null) ? null : itemstack.cloneItemStack());
                }
            }
            int i = this.bM();
            if (i > 0) {
                if (this.bm <= 0) {
                    this.bm = 20 * (30 - i);
                }
                --this.bm;
                if (this.bm <= 0) {
                    this.r(i - 1);
                }
            }
        }
        SpigotTimings.timerEntityBaseTick.stopTiming();
        this.c();
        SpigotTimings.timerEntityTickRest.startTiming();
        final double d0 = this.locX - this.lastX;
        final double d2 = this.locZ - this.lastZ;
        final float f = (float)(d0 * d0 + d2 * d2);
        float f2 = this.ay;
        float f3 = 0.0f;
        this.aC = this.aD;
        float f4 = 0.0f;
        if (f > 0.0025000002f) {
            f4 = 1.0f;
            f3 = (float)Math.sqrt(f) * 3.0f;
            f2 = (float)TrigMath.atan2(d2, d0) * 180.0f / 3.1415927f - 90.0f;
        }
        if (this.aR > 0.0f) {
            f2 = this.yaw;
        }
        if (!this.onGround) {
            f4 = 0.0f;
        }
        this.aD += (f4 - this.aD) * 0.3f;
        this.world.methodProfiler.a("headTurn");
        if (this.bh()) {
            this.senses.a();
        }
        else {
            final float f5 = MathHelper.g(f2 - this.ay);
            this.ay += f5 * 0.3f;
            float f6 = MathHelper.g(this.yaw - this.ay);
            final boolean flag = f6 < -90.0f || f6 >= 90.0f;
            if (f6 < -75.0f) {
                f6 = -75.0f;
            }
            if (f6 >= 75.0f) {
                f6 = 75.0f;
            }
            this.ay = this.yaw - f6;
            if (f6 * f6 > 2500.0f) {
                this.ay += f6 * 0.2f;
            }
            if (flag) {
                f3 *= -1.0f;
            }
        }
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("rangeChecks");
        while (this.yaw - this.lastYaw < -180.0f) {
            this.lastYaw -= 360.0f;
        }
        while (this.yaw - this.lastYaw >= 180.0f) {
            this.lastYaw += 360.0f;
        }
        while (this.ay - this.az < -180.0f) {
            this.az -= 360.0f;
        }
        while (this.ay - this.az >= 180.0f) {
            this.az += 360.0f;
        }
        while (this.pitch - this.lastPitch < -180.0f) {
            this.lastPitch -= 360.0f;
        }
        while (this.pitch - this.lastPitch >= 180.0f) {
            this.lastPitch += 360.0f;
        }
        while (this.aA - this.aB < -180.0f) {
            this.aB -= 360.0f;
        }
        while (this.aA - this.aB >= 180.0f) {
            this.aB += 360.0f;
        }
        this.world.methodProfiler.b();
        this.aE += f3;
        SpigotTimings.timerEntityTickRest.stopTiming();
    }
    
    public void heal(final int i) {
        this.heal(i, EntityRegainHealthEvent.RegainReason.CUSTOM);
    }
    
    public void heal(final int i, final EntityRegainHealthEvent.RegainReason regainReason) {
        if (this.health > 0) {
            final EntityRegainHealthEvent event = new EntityRegainHealthEvent(this.getBukkitEntity(), i, regainReason);
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.setHealth(this.getHealth() + event.getAmount());
            }
            if (this.health > this.maxHealth) {
                this.setHealth(this.maxHealth);
            }
            this.noDamageTicks = this.maxNoDamageTicks / 2;
        }
    }
    
    public abstract int getMaxHealth();
    
    public int getHealth() {
        return this.health;
    }
    
    public void setHealth(int i) {
        this.health = i;
        if (i > this.getMaxHealth()) {
            i = this.getMaxHealth();
        }
    }
    
    public boolean damageEntity(final DamageSource damagesource, int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (this.world.isStatic) {
            return false;
        }
        this.bC = 0;
        if (this.health <= 0) {
            return false;
        }
        if (damagesource.m() && this.hasEffect(MobEffectList.FIRE_RESISTANCE)) {
            return false;
        }
        if ((damagesource == DamageSource.ANVIL || damagesource == DamageSource.FALLING_BLOCK) && this.getEquipment(4) != null) {
            this.getEquipment(4).damage(i * 4 + this.random.nextInt(i * 2), this);
            i *= (int)0.75f;
        }
        this.bi = 1.5f;
        boolean flag = true;
        final EntityDamageEvent event = CraftEventFactory.handleEntityDamageEvent(this, damagesource, i);
        if (event != null) {
            if (event.isCancelled()) {
                return false;
            }
            i = event.getDamage();
        }
        if (this.noDamageTicks > this.maxNoDamageTicks / 2.0f) {
            if (i <= this.lastDamage) {
                return false;
            }
            this.d(damagesource, i - this.lastDamage);
            this.lastDamage = i;
            flag = false;
        }
        else {
            this.lastDamage = i;
            this.aT = this.health;
            this.noDamageTicks = this.maxNoDamageTicks;
            this.d(damagesource, i);
            final int n = 10;
            this.aX = n;
            this.hurtTicks = n;
        }
        this.aY = 0.0f;
        final Entity entity = damagesource.getEntity();
        if (entity != null) {
            if (entity instanceof EntityLiving) {
                this.c((EntityLiving)entity);
            }
            if (entity instanceof EntityHuman) {
                this.lastDamageByPlayerTime = 100;
                this.killer = (EntityHuman)entity;
            }
            else if (entity instanceof EntityWolf) {
                final EntityWolf entitywolf = (EntityWolf)entity;
                if (entitywolf.isTamed()) {
                    this.lastDamageByPlayerTime = 100;
                    this.killer = null;
                }
            }
        }
        if (flag) {
            this.world.broadcastEntityEffect(this, (byte)2);
            if (damagesource != DamageSource.DROWN) {
                this.J();
            }
            if (entity != null) {
                double d0;
                double d2;
                for (d0 = entity.locX - this.locX, d2 = entity.locZ - this.locZ; d0 * d0 + d2 * d2 < 1.0E-4; d0 = (Math.random() - Math.random()) * 0.01, d2 = (Math.random() - Math.random()) * 0.01) {}
                this.aY = (float)(Math.atan2(d2, d0) * 180.0 / 3.1415927410125732) - this.yaw;
                this.a(entity, i, d0, d2);
            }
            else {
                this.aY = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (flag) {
                this.makeSound(this.bd(), this.ba(), this.aY());
            }
            this.die(damagesource);
        }
        else if (flag) {
            this.makeSound(this.bc(), this.ba(), this.aY());
        }
        return true;
    }
    
    protected float aY() {
        return this.isBaby() ? ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.5f) : ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
    }
    
    public int aZ() {
        int i = 0;
        for (final ItemStack itemstack : this.getEquipment()) {
            if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
                final int l = ((ItemArmor)itemstack.getItem()).c;
                i += l;
            }
        }
        return i;
    }
    
    protected void k(final int i) {
    }
    
    protected int b(final DamageSource damagesource, int i) {
        if (!damagesource.ignoresArmor()) {
            final int j = 25 - this.aZ();
            final int k = i * j + this.aU;
            this.k(i);
            i = k / 25;
            this.aU = k % 25;
        }
        return i;
    }
    
    protected int c(final DamageSource damagesource, int i) {
        if (this.hasEffect(MobEffectList.RESISTANCE)) {
            final int j = (this.getEffect(MobEffectList.RESISTANCE).getAmplifier() + 1) * 5;
            final int k = 25 - j;
            final int l = i * k + this.aU;
            i = l / 25;
            this.aU = l % 25;
        }
        if (i <= 0) {
            return 0;
        }
        int j = EnchantmentManager.a(this.getEquipment(), damagesource);
        if (j > 20) {
            j = 20;
        }
        if (j > 0 && j <= 20) {
            final int k = 25 - j;
            final int l = i * k + this.aU;
            i = l / 25;
            this.aU = l % 25;
        }
        return i;
    }
    
    protected void d(final DamageSource damagesource, int i) {
        if (!this.isInvulnerable()) {
            i = this.b(damagesource, i);
            i = this.c(damagesource, i);
            final int j = this.getHealth();
            this.health -= i;
            this.bt.a(damagesource, j, i);
        }
    }
    
    protected float ba() {
        return 1.0f;
    }
    
    protected String bb() {
        return null;
    }
    
    protected String bc() {
        return "damage.hit";
    }
    
    protected String bd() {
        return "damage.hit";
    }
    
    public void a(final Entity entity, final int i, final double d0, final double d1) {
        this.an = true;
        final float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
        final float f2 = 0.4f;
        this.motX /= 2.0;
        this.motY /= 2.0;
        this.motZ /= 2.0;
        this.motX -= d0 / f * f2;
        this.motY += f2;
        this.motZ -= d1 / f * f2;
        if (this.motY > 0.4000000059604645) {
            this.motY = 0.4000000059604645;
        }
    }
    
    public void die(final DamageSource damagesource) {
        final Entity entity = damagesource.getEntity();
        final EntityLiving entityliving = this.bN();
        if (this.aM >= 0 && entityliving != null) {
            entityliving.c(this, this.aM);
        }
        if (entity != null) {
            entity.a(this);
        }
        this.bd = true;
        if (!this.world.isStatic) {
            int i = 0;
            if (entity instanceof EntityHuman) {
                i = EnchantmentManager.getBonusMonsterLootEnchantmentLevel((EntityLiving)entity);
            }
            if (!this.isBaby() && this.world.getGameRules().getBoolean("doMobLoot")) {
                this.dropDeathLoot(this.lastDamageByPlayerTime > 0, i);
                this.dropEquipment(this.lastDamageByPlayerTime > 0, i);
            }
            else {
                CraftEventFactory.callEntityDeathEvent(this);
            }
        }
        this.world.broadcastEntityEffect(this, (byte)3);
    }
    
    protected ItemStack l(final int i) {
        return null;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        final int j = this.getLootId();
        if (j > 0) {
            int k = this.random.nextInt(3);
            if (i > 0) {
                k += this.random.nextInt(i + 1);
            }
            if (k > 0) {
                loot.add(new org.bukkit.inventory.ItemStack(j, k));
            }
        }
        if (this.lastDamageByPlayerTime > 0) {
            final int k = this.random.nextInt(200) - i;
            if (k < 5) {
                final ItemStack itemstack = this.l((k <= 0) ? 1 : 0);
                if (itemstack != null) {
                    loot.add(CraftItemStack.asCraftMirror(itemstack));
                }
            }
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    protected int getLootId() {
        return 0;
    }
    
    protected void a(final float f) {
        super.a(f);
        int i = MathHelper.f(f - 3.0f);
        if (i > 0) {
            final EntityDamageEvent event = CraftEventFactory.callEntityDamageEvent(null, this, EntityDamageEvent.DamageCause.FALL, i);
            if (event.isCancelled()) {
                return;
            }
            i = event.getDamage();
            if (i > 0) {
                this.getBukkitEntity().setLastDamageCause(event);
            }
        }
        if (i > 0) {
            if (i > 4) {
                this.makeSound("damage.fallbig", 1.0f, 1.0f);
            }
            else {
                this.makeSound("damage.fallsmall", 1.0f, 1.0f);
            }
            this.damageEntity(DamageSource.FALL, i);
            final int j = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.locY - 0.20000000298023224 - this.height), MathHelper.floor(this.locZ));
            if (j > 0) {
                final StepSound stepsound = Block.byId[j].stepSound;
                this.makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.5f, stepsound.getVolume2() * 0.75f);
            }
        }
    }
    
    public void e(final float f, final float f1) {
        if (this.G() && (!(this instanceof EntityHuman) || !((EntityHuman)this).abilities.isFlying)) {
            final double d0 = this.locY;
            this.a(f, f1, this.bh() ? 0.04f : 0.02f);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.800000011920929;
            this.motY *= 0.800000011920929;
            this.motZ *= 0.800000011920929;
            this.motY -= 0.02;
            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579 - this.locY + d0, this.motZ)) {
                this.motY = 0.30000001192092896;
            }
        }
        else if (this.I() && (!(this instanceof EntityHuman) || !((EntityHuman)this).abilities.isFlying)) {
            final double d0 = this.locY;
            this.a(f, f1, 0.02f);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.5;
            this.motY *= 0.5;
            this.motZ *= 0.5;
            this.motY -= 0.02;
            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579 - this.locY + d0, this.motZ)) {
                this.motY = 0.30000001192092896;
            }
        }
        else {
            float f2 = 0.91f;
            if (this.onGround) {
                f2 = 0.54600006f;
                final int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
                if (i > 0) {
                    f2 = Block.byId[i].frictionFactor * 0.91f;
                }
            }
            final float f3 = 0.16277136f / (f2 * f2 * f2);
            float f4;
            if (this.onGround) {
                if (this.bh()) {
                    f4 = this.aI();
                }
                else {
                    f4 = this.aO;
                }
                f4 *= f3;
            }
            else {
                f4 = this.aP;
            }
            this.a(f, f1, f4);
            f2 = 0.91f;
            if (this.onGround) {
                f2 = 0.54600006f;
                final int j = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
                if (j > 0) {
                    f2 = Block.byId[j].frictionFactor * 0.91f;
                }
            }
            if (this.g_()) {
                final float f5 = 0.15f;
                if (this.motX < -f5) {
                    this.motX = -f5;
                }
                if (this.motX > f5) {
                    this.motX = f5;
                }
                if (this.motZ < -f5) {
                    this.motZ = -f5;
                }
                if (this.motZ > f5) {
                    this.motZ = f5;
                }
                this.fallDistance = 0.0f;
                if (this.motY < -0.15) {
                    this.motY = -0.15;
                }
                final boolean flag = this.isSneaking() && this instanceof EntityHuman;
                if (flag && this.motY < 0.0) {
                    this.motY = 0.0;
                }
            }
            this.move(this.motX, this.motY, this.motZ);
            if (this.positionChanged && this.g_()) {
                this.motY = 0.2;
            }
            if (this.world.isStatic && (!this.world.isLoaded((int)this.locX, 0, (int)this.locZ) || !this.world.getChunkAtWorldCoords((int)this.locX, (int)this.locZ).d)) {
                if (this.locY > 0.0) {
                    this.motY = -0.1;
                }
                else {
                    this.motY = 0.0;
                }
            }
            else {
                this.motY -= 0.08;
            }
            this.motY *= 0.9800000190734863;
            this.motX *= f2;
            this.motZ *= f2;
        }
        this.bh = this.bi;
        final double d0 = this.locX - this.lastX;
        final double d2 = this.locZ - this.lastZ;
        float f6 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 4.0f;
        if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        this.bi += (f6 - this.bi) * 0.4f;
        this.bj += this.bi;
    }
    
    public boolean g_() {
        final int i = MathHelper.floor(this.locX);
        final int j = MathHelper.floor(this.boundingBox.b);
        final int k = MathHelper.floor(this.locZ);
        final int l = this.world.getTypeId(i, j, k);
        return l == Block.LADDER.id || l == Block.VINE.id;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        if (this.health < -32768) {
            this.health = -32768;
        }
        nbttagcompound.setShort("Health", (short)this.health);
        nbttagcompound.setShort("HurtTime", (short)this.hurtTicks);
        nbttagcompound.setShort("DeathTime", (short)this.deathTicks);
        nbttagcompound.setShort("AttackTime", (short)this.attackTicks);
        nbttagcompound.setBoolean("CanPickUpLoot", this.bT());
        nbttagcompound.setBoolean("PersistenceRequired", this.persistent);
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.equipment.length; ++i) {
            final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            if (this.equipment[i] != null) {
                this.equipment[i].save(nbttagcompound2);
            }
            nbttaglist.add(nbttagcompound2);
        }
        nbttagcompound.set("Equipment", nbttaglist);
        if (!this.effects.isEmpty()) {
            final NBTTagList nbttaglist2 = new NBTTagList();
            for (final MobEffect mobeffect : this.effects.values()) {
                nbttaglist2.add(mobeffect.a(new NBTTagCompound()));
            }
            nbttagcompound.set("ActiveEffects", nbttaglist2);
        }
        final NBTTagList nbttaglist2 = new NBTTagList();
        for (int j = 0; j < this.dropChances.length; ++j) {
            nbttaglist2.add(new NBTTagFloat(j + "", this.dropChances[j]));
        }
        nbttagcompound.set("DropChances", nbttaglist2);
        nbttagcompound.setString("CustomName", this.getCustomName());
        nbttagcompound.setBoolean("CustomNameVisible", this.getCustomNameVisible());
        nbttagcompound.setInt("Bukkit.MaxHealth", this.maxHealth);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.health = nbttagcompound.getShort("Health");
        if (nbttagcompound.hasKey("Bukkit.MaxHealth")) {
            this.maxHealth = nbttagcompound.getInt("Bukkit.MaxHealth");
        }
        if (!nbttagcompound.hasKey("Health")) {
            this.health = this.maxHealth;
        }
        this.hurtTicks = nbttagcompound.getShort("HurtTime");
        this.deathTicks = nbttagcompound.getShort("DeathTime");
        this.attackTicks = nbttagcompound.getShort("AttackTime");
        boolean data = nbttagcompound.getBoolean("CanPickUpLoot");
        if (Entity.isLevelAtLeast(nbttagcompound, 1) || data) {
            this.canPickUpLoot = data;
        }
        data = nbttagcompound.getBoolean("PersistenceRequired");
        if (Entity.isLevelAtLeast(nbttagcompound, 1) || data) {
            this.persistent = data;
        }
        if (nbttagcompound.hasKey("CustomName") && nbttagcompound.getString("CustomName").length() > 0) {
            this.setCustomName(nbttagcompound.getString("CustomName"));
        }
        this.setCustomNameVisible(nbttagcompound.getBoolean("CustomNameVisible"));
        if (nbttagcompound.hasKey("Equipment")) {
            final NBTTagList nbttaglist = nbttagcompound.getList("Equipment");
            for (int i = 0; i < this.equipment.length; ++i) {
                this.equipment[i] = ItemStack.createStack((NBTTagCompound)nbttaglist.get(i));
            }
        }
        if (nbttagcompound.hasKey("ActiveEffects")) {
            final NBTTagList nbttaglist = nbttagcompound.getList("ActiveEffects");
            for (int i = 0; i < nbttaglist.size(); ++i) {
                final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(i);
                final MobEffect mobeffect = MobEffect.b(nbttagcompound2);
                this.effects.put(mobeffect.getEffectId(), mobeffect);
            }
        }
        if (nbttagcompound.hasKey("DropChances")) {
            final NBTTagList nbttaglist = nbttagcompound.getList("DropChances");
            for (int i = 0; i < nbttaglist.size(); ++i) {
                this.dropChances[i] = ((NBTTagFloat)nbttaglist.get(i)).data;
            }
        }
    }
    
    public boolean isAlive() {
        return !this.dead && this.health > 0;
    }
    
    public boolean bf() {
        return false;
    }
    
    public void f(final float f) {
        this.bE = f;
    }
    
    public void f(final boolean flag) {
        this.bG = flag;
    }
    
    public void c() {
        SpigotTimings.timerEntityAI.startTiming();
        if (this.bX > 0) {
            --this.bX;
        }
        if (this.bu > 0) {
            final double d0 = this.locX + (this.bv - this.locX) / this.bu;
            final double d2 = this.locY + (this.bw - this.locY) / this.bu;
            final double d3 = this.locZ + (this.bx - this.locZ) / this.bu;
            final double d4 = MathHelper.g(this.by - this.yaw);
            this.yaw += (float)(d4 / this.bu);
            this.pitch += (float)((this.bz - this.pitch) / this.bu);
            --this.bu;
            this.setPosition(d0, d2, d3);
            this.b(this.yaw, this.pitch);
        }
        else if (!this.bi()) {
            this.motX *= 0.98;
            this.motY *= 0.98;
            this.motZ *= 0.98;
        }
        if (Math.abs(this.motX) < 0.005) {
            this.motX = 0.0;
        }
        if (Math.abs(this.motY) < 0.005) {
            this.motY = 0.0;
        }
        if (Math.abs(this.motZ) < 0.005) {
            this.motZ = 0.0;
        }
        this.world.methodProfiler.a("ai");
        if (this.bj()) {
            this.bG = false;
            this.bD = 0.0f;
            this.bE = 0.0f;
            this.bF = 0.0f;
        }
        else if (this.bi()) {
            if (this.bh()) {
                this.world.methodProfiler.a("newAi");
                this.bo();
                this.world.methodProfiler.b();
            }
            else {
                this.world.methodProfiler.a("oldAi");
                this.bq();
                this.world.methodProfiler.b();
                this.aA = this.yaw;
            }
        }
        SpigotTimings.timerEntityAI.stopTiming();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("jump");
        if (this.bG) {
            if (!this.G() && !this.I()) {
                if (this.onGround && this.bX == 0) {
                    this.bl();
                    this.bX = 10;
                }
            }
            else {
                this.motY += 0.03999999910593033;
            }
        }
        else {
            this.bX = 0;
        }
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("travel");
        SpigotTimings.timerEntityAIMove.startTiming();
        this.bD *= 0.98f;
        this.bE *= 0.98f;
        this.bF *= 0.9f;
        final float f = this.aO;
        this.aO *= this.bE();
        this.e(this.bD, this.bE);
        this.aO = f;
        SpigotTimings.timerEntityAIMove.stopTiming();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("push");
        SpigotTimings.timerEntityAICollision.startTiming();
        if (!this.world.isStatic) {
            this.bg();
        }
        SpigotTimings.timerEntityAICollision.stopTiming();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("looting");
        if (!this.world.isStatic && !(this instanceof EntityPlayer) && this.bT() && !this.bd && this.world.getGameRules().getBoolean("mobGriefing")) {
            final List list = this.world.a(EntityItem.class, this.boundingBox.grow(1.0, 0.0, 1.0));
            for (final EntityItem entityitem : list) {
                if (!entityitem.dead && entityitem.getItemStack() != null) {
                    final ItemStack itemstack = entityitem.getItemStack();
                    final int i = b(itemstack);
                    if (i <= -1) {
                        continue;
                    }
                    boolean flag = true;
                    final ItemStack itemstack2 = this.getEquipment(i);
                    if (itemstack2 != null) {
                        if (i == 0) {
                            if (itemstack.getItem() instanceof ItemSword && !(itemstack2.getItem() instanceof ItemSword)) {
                                flag = true;
                            }
                            else if (itemstack.getItem() instanceof ItemSword && itemstack2.getItem() instanceof ItemSword) {
                                final ItemSword itemsword = (ItemSword)itemstack.getItem();
                                final ItemSword itemsword2 = (ItemSword)itemstack2.getItem();
                                if (itemsword.g() == itemsword2.g()) {
                                    flag = (itemstack.getData() > itemstack2.getData() || (itemstack.hasTag() && !itemstack2.hasTag()));
                                }
                                else {
                                    flag = (itemsword.g() > itemsword2.g());
                                }
                            }
                            else {
                                flag = false;
                            }
                        }
                        else if (itemstack.getItem() instanceof ItemArmor && !(itemstack2.getItem() instanceof ItemArmor)) {
                            flag = true;
                        }
                        else if (itemstack.getItem() instanceof ItemArmor && itemstack2.getItem() instanceof ItemArmor) {
                            final ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
                            final ItemArmor itemarmor2 = (ItemArmor)itemstack2.getItem();
                            if (itemarmor.c == itemarmor2.c) {
                                flag = (itemstack.getData() > itemstack2.getData() || (itemstack.hasTag() && !itemstack2.hasTag()));
                            }
                            else {
                                flag = (itemarmor.c > itemarmor2.c);
                            }
                        }
                        else {
                            flag = false;
                        }
                    }
                    if (!flag) {
                        continue;
                    }
                    if (itemstack2 != null && this.random.nextFloat() - 0.1f < this.dropChances[i]) {
                        this.a(itemstack2, 0.0f);
                    }
                    this.setEquipment(i, itemstack);
                    this.dropChances[i] = 2.0f;
                    this.persistent = true;
                    this.receive(entityitem, 1);
                    entityitem.die();
                }
            }
        }
        this.world.methodProfiler.b();
    }
    
    protected void bg() {
        final List list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224, 0.0, 0.20000000298023224));
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity = list.get(i);
                if (!(entity instanceof EntityLiving) || this instanceof EntityPlayer || this.ticksLived % 2 != 0) {
                    if (entity.L()) {
                        this.o(entity);
                    }
                }
            }
        }
    }
    
    protected void o(final Entity entity) {
        entity.collide(this);
    }
    
    protected boolean bh() {
        return false;
    }
    
    protected boolean bi() {
        return !this.world.isStatic;
    }
    
    protected boolean bj() {
        return this.health <= 0;
    }
    
    public boolean isBlocking() {
        return false;
    }
    
    protected void bl() {
        this.motY = 0.41999998688697815;
        if (this.hasEffect(MobEffectList.JUMP)) {
            this.motY += (this.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1f;
        }
        if (this.isSprinting()) {
            final float f = this.yaw * 0.017453292f;
            this.motX -= MathHelper.sin(f) * 0.2f;
            this.motZ += MathHelper.cos(f) * 0.2f;
        }
        this.an = true;
    }
    
    protected boolean isTypeNotPersistent() {
        return true;
    }
    
    protected void bn() {
        if (!this.persistent) {
            final EntityHuman entityhuman = this.world.findNearbyPlayer(this, -1.0);
            if (entityhuman != null) {
                final double d0 = entityhuman.locX - this.locX;
                final double d2 = entityhuman.locY - this.locY;
                final double d3 = entityhuman.locZ - this.locZ;
                final double d4 = d0 * d0 + d2 * d2 + d3 * d3;
                if (d4 > 16384.0) {
                    this.die();
                }
                if (this.bC > 600 && this.random.nextInt(800) == 0 && d4 > 1024.0) {
                    this.die();
                }
                else if (d4 < 1024.0) {
                    this.bC = 0;
                }
            }
        }
        else {
            this.bC = 0;
        }
    }
    
    protected void bo() {
        ++this.bC;
        this.world.methodProfiler.a("checkDespawn");
        this.bn();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("sensing");
        this.bP.a();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("targetSelector");
        this.targetSelector.a();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("goalSelector");
        this.goalSelector.a();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("navigation");
        this.navigation.e();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("mob tick");
        this.bp();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("controls");
        this.world.methodProfiler.a("move");
        this.moveController.c();
        this.world.methodProfiler.c("look");
        this.lookController.a();
        this.world.methodProfiler.c("jump");
        this.jumpController.b();
        this.world.methodProfiler.b();
        this.world.methodProfiler.b();
    }
    
    protected void bp() {
    }
    
    protected void bq() {
        ++this.bC;
        this.bn();
        this.bD = 0.0f;
        this.bE = 0.0f;
        final float f = 8.0f;
        if (this.random.nextFloat() < 0.02f) {
            final EntityHuman entityhuman = this.world.findNearbyPlayer(this, f);
            if (entityhuman != null) {
                this.bY = entityhuman;
                this.bJ = 10 + this.random.nextInt(20);
            }
            else {
                this.bF = (this.random.nextFloat() - 0.5f) * 20.0f;
            }
        }
        if (this.bY != null) {
            this.a(this.bY, 10.0f, this.bs());
            if (this.bJ-- <= 0 || this.bY.dead || this.bY.e(this) > f * f) {
                this.bY = null;
            }
        }
        else {
            if (this.random.nextFloat() < 0.05f) {
                this.bF = (this.random.nextFloat() - 0.5f) * 20.0f;
            }
            this.yaw += this.bF;
            this.pitch = this.bH;
        }
        final boolean flag = this.G();
        final boolean flag2 = this.I();
        if (flag || flag2) {
            this.bG = (this.random.nextFloat() < 0.8f);
        }
    }
    
    protected void br() {
        final int i = this.h();
        if (this.br) {
            ++this.bs;
            if (this.bs >= i) {
                this.bs = 0;
                this.br = false;
            }
        }
        else {
            this.bs = 0;
        }
        this.aR = this.bs / i;
    }
    
    public int bs() {
        return 40;
    }
    
    public void a(final Entity entity, final float f, final float f1) {
        final double d0 = entity.locX - this.locX;
        final double d2 = entity.locZ - this.locZ;
        double d3;
        if (entity instanceof EntityLiving) {
            final EntityLiving entityliving = (EntityLiving)entity;
            d3 = entityliving.locY + entityliving.getHeadHeight() - (this.locY + this.getHeadHeight());
        }
        else {
            d3 = (entity.boundingBox.b + entity.boundingBox.e) / 2.0 - (this.locY + this.getHeadHeight());
        }
        final double d4 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        final float f2 = (float)(Math.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0f;
        final float f3 = (float)(-(Math.atan2(d3, d4) * 180.0 / 3.1415927410125732));
        this.pitch = this.b(this.pitch, f3, f1);
        this.yaw = this.b(this.yaw, f2, f);
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
    
    public boolean canSpawn() {
        return this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }
    
    protected void B() {
        this.damageEntity(DamageSource.OUT_OF_WORLD, 4);
    }
    
    public Vec3D Y() {
        return this.i(1.0f);
    }
    
    public Vec3D i(final float f) {
        if (f == 1.0f) {
            final float f2 = MathHelper.cos(-this.yaw * 0.017453292f - 3.1415927f);
            final float f3 = MathHelper.sin(-this.yaw * 0.017453292f - 3.1415927f);
            final float f4 = -MathHelper.cos(-this.pitch * 0.017453292f);
            final float f5 = MathHelper.sin(-this.pitch * 0.017453292f);
            return this.world.getVec3DPool().create(f3 * f4, f5, f2 * f4);
        }
        final float f2 = this.lastPitch + (this.pitch - this.lastPitch) * f;
        final float f3 = this.lastYaw + (this.yaw - this.lastYaw) * f;
        final float f4 = MathHelper.cos(-f3 * 0.017453292f - 3.1415927f);
        final float f5 = MathHelper.sin(-f3 * 0.017453292f - 3.1415927f);
        final float f6 = -MathHelper.cos(-f2 * 0.017453292f);
        final float f7 = MathHelper.sin(-f2 * 0.017453292f);
        return this.world.getVec3DPool().create(f5 * f6, f7, f4 * f6);
    }
    
    public int by() {
        return 4;
    }
    
    public boolean isSleeping() {
        return false;
    }
    
    protected void bA() {
        final Iterator iterator = this.effects.keySet().iterator();
        while (iterator.hasNext()) {
            final Integer integer = iterator.next();
            final MobEffect mobeffect = this.effects.get(integer);
            try {
                if (!mobeffect.tick(this)) {
                    if (this.world.isStatic) {
                        continue;
                    }
                    iterator.remove();
                    this.c(mobeffect);
                }
                else {
                    if (mobeffect.getDuration() % 600 != 0) {
                        continue;
                    }
                    this.b(mobeffect);
                }
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.a(throwable, "Ticking mob effect instance");
                final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Mob effect being ticked");
                crashreportsystemdetails.a("Effect Name", new CrashReportEffectName(this, mobeffect));
                crashreportsystemdetails.a("Effect ID", new CrashReportEffectID(this, mobeffect));
                crashreportsystemdetails.a("Effect Duration", new CrashReportEffectDuration(this, mobeffect));
                crashreportsystemdetails.a("Effect Amplifier", new CrashReportEffectAmplifier(this, mobeffect));
                crashreportsystemdetails.a("Effect is Splash", new CrashReportEffectSplash(this, mobeffect));
                crashreportsystemdetails.a("Effect is Ambient", new CrashReportEffectAmbient(this, mobeffect));
                throw new ReportedException(crashreport);
            }
        }
        if (this.updateEffects) {
            if (!this.world.isStatic) {
                if (this.effects.isEmpty()) {
                    this.datawatcher.watch(9, (byte)0);
                    this.datawatcher.watch(8, 0);
                    this.setInvisible(false);
                }
                else {
                    final int i = PotionBrewer.a(this.effects.values());
                    this.datawatcher.watch(9, (byte)(byte)(PotionBrewer.b(this.effects.values()) ? 1 : 0));
                    this.datawatcher.watch(8, i);
                    this.setInvisible(this.hasEffect(MobEffectList.INVISIBILITY.id));
                }
            }
            this.updateEffects = false;
        }
        final int i = this.datawatcher.getInt(8);
        final boolean flag = this.datawatcher.getByte(9) > 0;
        if (i > 0) {
            boolean flag2 = false;
            if (!this.isInvisible()) {
                flag2 = this.random.nextBoolean();
            }
            else {
                flag2 = (this.random.nextInt(15) == 0);
            }
            if (flag) {
                flag2 &= (this.random.nextInt(5) == 0);
            }
            if (flag2 && i > 0) {
                final double d0 = (i >> 16 & 0xFF) / 255.0;
                final double d2 = (i >> 8 & 0xFF) / 255.0;
                final double d3 = (i >> 0 & 0xFF) / 255.0;
                this.world.addParticle(flag ? "mobSpellAmbient" : "mobSpell", this.locX + (this.random.nextDouble() - 0.5) * this.width, this.locY + this.random.nextDouble() * this.length - this.height, this.locZ + (this.random.nextDouble() - 0.5) * this.width, d0, d2, d3);
            }
        }
    }
    
    public void bB() {
        final Iterator iterator = this.effects.keySet().iterator();
        while (iterator.hasNext()) {
            final Integer integer = iterator.next();
            final MobEffect mobeffect = this.effects.get(integer);
            if (!this.world.isStatic) {
                iterator.remove();
                this.c(mobeffect);
            }
        }
    }
    
    public Collection getEffects() {
        return this.effects.values();
    }
    
    public boolean hasEffect(final int i) {
        return this.effects.size() != 0 && this.effects.containsKey(i);
    }
    
    public boolean hasEffect(final MobEffectList mobeffectlist) {
        return this.effects.size() != 0 && this.effects.containsKey(mobeffectlist.id);
    }
    
    public MobEffect getEffect(final MobEffectList mobeffectlist) {
        return this.effects.get(mobeffectlist.id);
    }
    
    public void addEffect(final MobEffect mobeffect) {
        if (this.e(mobeffect)) {
            if (this.effects.containsKey(mobeffect.getEffectId())) {
                this.effects.get(mobeffect.getEffectId()).a(mobeffect);
                this.b(this.effects.get(mobeffect.getEffectId()));
            }
            else {
                this.effects.put(mobeffect.getEffectId(), mobeffect);
                this.a(mobeffect);
            }
        }
    }
    
    public boolean e(final MobEffect mobeffect) {
        if (this.getMonsterType() == EnumMonsterType.UNDEAD) {
            final int i = mobeffect.getEffectId();
            if (i == MobEffectList.REGENERATION.id || i == MobEffectList.POISON.id) {
                return false;
            }
        }
        return true;
    }
    
    public boolean bD() {
        return this.getMonsterType() == EnumMonsterType.UNDEAD;
    }
    
    public void o(final int i) {
        final MobEffect mobeffect = this.effects.remove(i);
        if (mobeffect != null) {
            this.c(mobeffect);
        }
    }
    
    protected void a(final MobEffect mobeffect) {
        this.updateEffects = true;
    }
    
    protected void b(final MobEffect mobeffect) {
        this.updateEffects = true;
    }
    
    protected void c(final MobEffect mobeffect) {
        this.updateEffects = true;
    }
    
    public float bE() {
        float f = 1.0f;
        if (this.hasEffect(MobEffectList.FASTER_MOVEMENT)) {
            f *= 1.0f + 0.2f * (this.getEffect(MobEffectList.FASTER_MOVEMENT).getAmplifier() + 1);
        }
        if (this.hasEffect(MobEffectList.SLOWER_MOVEMENT)) {
            f *= 1.0f - 0.15f * (this.getEffect(MobEffectList.SLOWER_MOVEMENT).getAmplifier() + 1);
        }
        if (f < 0.0f) {
            f = 0.0f;
        }
        return f;
    }
    
    public void enderTeleportTo(final double d0, final double d1, final double d2) {
        this.setPositionRotation(d0, d1, d2, this.yaw, this.pitch);
    }
    
    public boolean isBaby() {
        return false;
    }
    
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEFINED;
    }
    
    public void a(final ItemStack itemstack) {
        this.makeSound("random.break", 0.8f, 0.8f + this.world.random.nextFloat() * 0.4f);
        for (int i = 0; i < 5; ++i) {
            final Vec3D vec3d = this.world.getVec3DPool().create((this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            vec3d.a(-this.pitch * 3.1415927f / 180.0f);
            vec3d.b(-this.yaw * 3.1415927f / 180.0f);
            Vec3D vec3d2 = this.world.getVec3DPool().create((this.random.nextFloat() - 0.5) * 0.3, -this.random.nextFloat() * 0.6 - 0.3, 0.6);
            vec3d2.a(-this.pitch * 3.1415927f / 180.0f);
            vec3d2.b(-this.yaw * 3.1415927f / 180.0f);
            vec3d2 = vec3d2.add(this.locX, this.locY + this.getHeadHeight(), this.locZ);
            this.world.addParticle("iconcrack_" + itemstack.getItem().id, vec3d2.c, vec3d2.d, vec3d2.e, vec3d.c, vec3d.d + 0.05, vec3d.e);
        }
    }
    
    public int ar() {
        if (this.getGoalTarget() == null) {
            return 3;
        }
        int i = (int)(this.health - this.maxHealth * 0.33f);
        i -= (3 - this.world.difficulty) * 4;
        if (i < 0) {
            i = 0;
        }
        return i + 3;
    }
    
    public ItemStack bG() {
        return this.equipment[0];
    }
    
    public ItemStack getEquipment(final int i) {
        return this.equipment[i];
    }
    
    public ItemStack q(final int i) {
        return this.equipment[i + 1];
    }
    
    public void setEquipment(final int i, final ItemStack itemstack) {
        this.equipment[i] = itemstack;
    }
    
    public ItemStack[] getEquipment() {
        return this.equipment;
    }
    
    protected void dropEquipment(final boolean flag, final int i) {
        for (int j = 0; j < this.getEquipment().length; ++j) {
            final ItemStack itemstack = this.getEquipment(j);
            final boolean flag2 = this.dropChances[j] > 1.0f;
            if (itemstack != null && (flag || flag2) && this.random.nextFloat() - i * 0.01f < this.dropChances[j]) {
                if (!flag2 && itemstack.g()) {
                    final int k = Math.max(itemstack.l() - 25, 1);
                    int l = itemstack.l() - this.random.nextInt(this.random.nextInt(k) + 1);
                    if (l > k) {
                        l = k;
                    }
                    if (l < 1) {
                        l = 1;
                    }
                    itemstack.setData(l);
                }
                this.a(itemstack, 0.0f);
            }
        }
    }
    
    protected void bH() {
        if (this.random.nextFloat() < EntityLiving.d[this.world.difficulty]) {
            int i = this.random.nextInt(2);
            final float f = (this.world.difficulty == 3) ? 0.1f : 0.25f;
            if (this.random.nextFloat() < 0.095f) {
                ++i;
            }
            if (this.random.nextFloat() < 0.095f) {
                ++i;
            }
            if (this.random.nextFloat() < 0.095f) {
                ++i;
            }
            for (int j = 3; j >= 0; --j) {
                final ItemStack itemstack = this.q(j);
                if (j < 3 && this.random.nextFloat() < f) {
                    break;
                }
                if (itemstack == null) {
                    final Item item = a(j + 1, i);
                    if (item != null) {
                        this.setEquipment(j + 1, new ItemStack(item));
                    }
                }
            }
        }
    }
    
    public void receive(final Entity entity, final int i) {
        if (!entity.dead && !this.world.isStatic) {
            final EntityTracker entitytracker = ((WorldServer)this.world).getTracker();
            if (entity instanceof EntityItem) {
                entitytracker.a(entity, new Packet22Collect(entity.id, this.id));
            }
            if (entity instanceof EntityArrow) {
                entitytracker.a(entity, new Packet22Collect(entity.id, this.id));
            }
            if (entity instanceof EntityExperienceOrb) {
                entitytracker.a(entity, new Packet22Collect(entity.id, this.id));
            }
        }
    }
    
    public static int b(final ItemStack itemstack) {
        if (itemstack.id != Block.PUMPKIN.id && itemstack.id != Item.SKULL.id) {
            if (itemstack.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)itemstack.getItem()).b) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 4;
    }
    
    public static Item a(final int i, final int j) {
        switch (i) {
            case 4: {
                if (j == 0) {
                    return Item.LEATHER_HELMET;
                }
                if (j == 1) {
                    return Item.GOLD_HELMET;
                }
                if (j == 2) {
                    return Item.CHAINMAIL_HELMET;
                }
                if (j == 3) {
                    return Item.IRON_HELMET;
                }
                if (j == 4) {
                    return Item.DIAMOND_HELMET;
                }
            }
            case 3: {
                if (j == 0) {
                    return Item.LEATHER_CHESTPLATE;
                }
                if (j == 1) {
                    return Item.GOLD_CHESTPLATE;
                }
                if (j == 2) {
                    return Item.CHAINMAIL_CHESTPLATE;
                }
                if (j == 3) {
                    return Item.IRON_CHESTPLATE;
                }
                if (j == 4) {
                    return Item.DIAMOND_CHESTPLATE;
                }
            }
            case 2: {
                if (j == 0) {
                    return Item.LEATHER_LEGGINGS;
                }
                if (j == 1) {
                    return Item.GOLD_LEGGINGS;
                }
                if (j == 2) {
                    return Item.CHAINMAIL_LEGGINGS;
                }
                if (j == 3) {
                    return Item.IRON_LEGGINGS;
                }
                if (j == 4) {
                    return Item.DIAMOND_LEGGINGS;
                }
            }
            case 1: {
                if (j == 0) {
                    return Item.LEATHER_BOOTS;
                }
                if (j == 1) {
                    return Item.GOLD_BOOTS;
                }
                if (j == 2) {
                    return Item.CHAINMAIL_BOOTS;
                }
                if (j == 3) {
                    return Item.IRON_BOOTS;
                }
                if (j == 4) {
                    return Item.DIAMOND_BOOTS;
                }
                break;
            }
        }
        return null;
    }
    
    protected void bI() {
        if (this.bG() != null && this.random.nextFloat() < EntityLiving.b[this.world.difficulty]) {
            EnchantmentManager.a(this.random, this.bG(), 5 + this.world.difficulty * this.random.nextInt(6));
        }
        for (int i = 0; i < 4; ++i) {
            final ItemStack itemstack = this.q(i);
            if (itemstack != null && this.random.nextFloat() < EntityLiving.c[this.world.difficulty]) {
                EnchantmentManager.a(this.random, itemstack, 5 + this.world.difficulty * this.random.nextInt(6));
            }
        }
    }
    
    public void bJ() {
    }
    
    private int h() {
        return this.hasEffect(MobEffectList.FASTER_DIG) ? (6 - (1 + this.getEffect(MobEffectList.FASTER_DIG).getAmplifier()) * 1) : (this.hasEffect(MobEffectList.SLOWER_DIG) ? (6 + (1 + this.getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) * 2) : 6);
    }
    
    public void bK() {
        if (!this.br || this.bs >= this.h() / 2 || this.bs < 0) {
            this.bs = -1;
            this.br = true;
            if (this.world instanceof WorldServer) {
                ((WorldServer)this.world).getTracker().a(this, new Packet18ArmAnimation(this, 1));
            }
        }
    }
    
    public boolean bL() {
        return false;
    }
    
    public final int bM() {
        return this.datawatcher.getByte(10);
    }
    
    public final void r(final int i) {
        this.datawatcher.watch(10, (byte)i);
    }
    
    public EntityLiving bN() {
        return (this.bt.c() != null) ? this.bt.c() : ((this.killer != null) ? this.killer : ((this.lastDamager != null) ? this.lastDamager : null));
    }
    
    public String getLocalizedName() {
        return this.hasCustomName() ? this.getCustomName() : super.getLocalizedName();
    }
    
    public void setCustomName(final String s) {
        this.datawatcher.watch(5, s);
    }
    
    public String getCustomName() {
        return this.datawatcher.getString(5);
    }
    
    public boolean hasCustomName() {
        return this.datawatcher.getString(5).length() > 0;
    }
    
    public void setCustomNameVisible(final boolean flag) {
        this.datawatcher.watch(6, (byte)(byte)(flag ? 1 : 0));
    }
    
    public boolean getCustomNameVisible() {
        return this.datawatcher.getByte(6) == 1;
    }
    
    public void a(final int i, final float f) {
        this.dropChances[i] = f;
    }
    
    public boolean bT() {
        return this.canPickUpLoot;
    }
    
    public void h(final boolean flag) {
        this.canPickUpLoot = flag;
    }
    
    public boolean bU() {
        return this.persistent;
    }
    
    static {
        b = new float[] { 0.0f, 0.0f, 0.1f, 0.2f };
        c = new float[] { 0.0f, 0.0f, 0.25f, 0.5f };
        d = new float[] { 0.0f, 0.0f, 0.05f, 0.07f };
        au = new float[] { 0.0f, 0.1f, 0.15f, 0.45f };
    }
}
