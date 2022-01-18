// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityWolf extends EntityTameableAnimal
{
    private float e;
    private float f;
    private boolean g;
    private boolean h;
    private float i;
    private float j;
    
    public EntityWolf(final World world) {
        super(world);
        this.texture = "/mob/wolf.png";
        this.a(0.6f, 0.8f);
        this.bI = 0.3f;
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.d);
        this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4f));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, this.bI, true));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, this.bI, 10.0f, 2.0f));
        this.goalSelector.a(6, new PathfinderGoalBreed(this, this.bI));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(8, new PathfinderGoalBeg(this, 8.0f));
        this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
        this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(4, new PathfinderGoalRandomTargetNonTamed(this, EntitySheep.class, 16.0f, 200, false));
    }
    
    public boolean bh() {
        return true;
    }
    
    public void setGoalTarget(final EntityLiving entityliving) {
        super.setGoalTarget(entityliving);
        if (entityliving instanceof EntityHuman) {
            this.setAngry(true);
        }
    }
    
    protected void bp() {
        this.datawatcher.watch(18, this.getScaledHealth());
    }
    
    public int getMaxHealth() {
        return this.isTamed() ? 20 : 8;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(18, new Integer(this.getHealth()));
        this.datawatcher.a(19, new Byte((byte)0));
        this.datawatcher.a(20, new Byte((byte)BlockCloth.g_(1)));
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.wolf.step", 0.15f, 1.0f);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("Angry", this.isAngry());
        nbttagcompound.setByte("CollarColor", (byte)this.getCollarColor());
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setAngry(nbttagcompound.getBoolean("Angry"));
        if (nbttagcompound.hasKey("CollarColor")) {
            this.setCollarColor(nbttagcompound.getByte("CollarColor"));
        }
    }
    
    protected boolean isTypeNotPersistent() {
        return this.isAngry() && !this.isTamed();
    }
    
    protected String bb() {
        return this.isAngry() ? "mob.wolf.growl" : ((this.random.nextInt(3) == 0) ? ((this.isTamed() && this.datawatcher.getInt(18) < this.maxHealth / 2) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }
    
    protected String bc() {
        return "mob.wolf.hurt";
    }
    
    protected String bd() {
        return "mob.wolf.death";
    }
    
    protected float ba() {
        return 0.4f;
    }
    
    protected int getLootId() {
        return -1;
    }
    
    public void c() {
        super.c();
        if (!this.world.isStatic && this.g && !this.h && !this.k() && this.onGround) {
            this.h = true;
            this.i = 0.0f;
            this.j = 0.0f;
            this.world.broadcastEntityEffect(this, (byte)8);
        }
    }
    
    public void l_() {
        super.l_();
        this.f = this.e;
        if (this.bY()) {
            this.e += (1.0f - this.e) * 0.4f;
        }
        else {
            this.e += (0.0f - this.e) * 0.4f;
        }
        if (this.bY()) {
            this.bJ = 10;
        }
        if (this.F()) {
            this.g = true;
            this.h = false;
            this.i = 0.0f;
            this.j = 0.0f;
        }
        else if ((this.g || this.h) && this.h) {
            if (this.i == 0.0f) {
                this.makeSound("mob.wolf.shake", this.ba(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            }
            this.j = this.i;
            this.i += 0.05f;
            if (this.j >= 2.0f) {
                this.g = false;
                this.h = false;
                this.j = 0.0f;
                this.i = 0.0f;
            }
            if (this.i > 0.4f) {
                final float f = (float)this.boundingBox.b;
                for (int i = (int)(MathHelper.sin((this.i - 0.4f) * 3.1415927f) * 7.0f), j = 0; j < i; ++j) {
                    final float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    final float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    this.world.addParticle("splash", this.locX + f2, f + 0.8f, this.locZ + f3, this.motX, this.motY, this.motZ);
                }
            }
        }
    }
    
    public float getHeadHeight() {
        return this.length * 0.8f;
    }
    
    public int bs() {
        return this.isSitting() ? 20 : super.bs();
    }
    
    public boolean damageEntity(final DamageSource damagesource, int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        final Entity entity = damagesource.getEntity();
        this.d.setSitting(false);
        if (entity != null && !(entity instanceof EntityHuman) && !(entity instanceof EntityArrow)) {
            i = (i + 1) / 2;
        }
        return super.damageEntity(damagesource, i);
    }
    
    public boolean m(final Entity entity) {
        final int i = this.isTamed() ? 4 : 2;
        return entity.damageEntity(DamageSource.mobAttack(this), i);
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        final ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (this.isTamed()) {
            if (itemstack != null) {
                if (Item.byId[itemstack.id] instanceof ItemFood) {
                    final ItemFood itemfood = (ItemFood)Item.byId[itemstack.id];
                    if (itemfood.i() && this.datawatcher.getInt(18) < 20) {
                        if (!entityhuman.abilities.canInstantlyBuild) {
                            final ItemStack itemStack = itemstack;
                            --itemStack.count;
                        }
                        this.heal(itemfood.getNutrition());
                        if (itemstack.count <= 0) {
                            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
                        }
                        return true;
                    }
                }
                else if (itemstack.id == Item.INK_SACK.id) {
                    final int i = BlockCloth.g_(itemstack.getData());
                    if (i != this.getCollarColor()) {
                        this.setCollarColor(i);
                        if (!entityhuman.abilities.canInstantlyBuild) {
                            final ItemStack itemStack2 = itemstack;
                            if (--itemStack2.count <= 0) {
                                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
                            }
                        }
                        return true;
                    }
                }
            }
            if (entityhuman.name.equalsIgnoreCase(this.getOwnerName()) && !this.world.isStatic && !this.c(itemstack)) {
                this.d.setSitting(!this.isSitting());
                this.bG = false;
                this.setPathEntity(null);
            }
        }
        else if (itemstack != null && itemstack.id == Item.BONE.id && !this.isAngry()) {
            if (!entityhuman.abilities.canInstantlyBuild) {
                final ItemStack itemStack3 = itemstack;
                --itemStack3.count;
            }
            if (itemstack.count <= 0) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
            }
            if (!this.world.isStatic) {
                if (this.random.nextInt(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
                    final boolean updateMaxHealth = this.getMaxHealth() == this.maxHealth;
                    this.setTamed(true);
                    this.setPathEntity(null);
                    this.setGoalTarget(null);
                    this.d.setSitting(true);
                    if (updateMaxHealth) {
                        this.maxHealth = this.getMaxHealth();
                    }
                    this.setHealth(this.maxHealth);
                    this.setOwnerName(entityhuman.name);
                    this.i(true);
                    this.world.broadcastEntityEffect(this, (byte)7);
                }
                else {
                    this.i(false);
                    this.world.broadcastEntityEffect(this, (byte)6);
                }
            }
            return true;
        }
        return super.a_(entityhuman);
    }
    
    public boolean c(final ItemStack itemstack) {
        return itemstack != null && Item.byId[itemstack.id] instanceof ItemFood && ((ItemFood)Item.byId[itemstack.id]).i();
    }
    
    public int by() {
        return 8;
    }
    
    public boolean isAngry() {
        return (this.datawatcher.getByte(16) & 0x2) != 0x0;
    }
    
    public void setAngry(final boolean flag) {
        final byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, (byte)(b0 | 0x2));
        }
        else {
            this.datawatcher.watch(16, (byte)(b0 & 0xFFFFFFFD));
        }
    }
    
    public int getCollarColor() {
        return this.datawatcher.getByte(20) & 0xF;
    }
    
    public void setCollarColor(final int i) {
        this.datawatcher.watch(20, (byte)(i & 0xF));
    }
    
    public EntityWolf b(final EntityAgeable entityageable) {
        final EntityWolf entitywolf = new EntityWolf(this.world);
        final String s = this.getOwnerName();
        if (s != null && s.trim().length() > 0) {
            entitywolf.setOwnerName(s);
            entitywolf.setTamed(true);
        }
        return entitywolf;
    }
    
    public void m(final boolean flag) {
        final byte b0 = this.datawatcher.getByte(19);
        if (flag) {
            this.datawatcher.watch(19, (byte)1);
        }
        else {
            this.datawatcher.watch(19, (byte)0);
        }
    }
    
    public boolean mate(final EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(entityanimal instanceof EntityWolf)) {
            return false;
        }
        final EntityWolf entitywolf = (EntityWolf)entityanimal;
        return entitywolf.isTamed() && !entitywolf.isSitting() && (this.r() && entitywolf.r());
    }
    
    public boolean bY() {
        return this.datawatcher.getByte(19) == 1;
    }
    
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
