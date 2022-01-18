// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityOcelot extends EntityTameableAnimal
{
    private PathfinderGoalTempt e;
    
    public EntityOcelot(final World world) {
        super(world);
        this.texture = "/mob/ozelot.png";
        this.a(0.6f, 0.8f);
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.d);
        this.goalSelector.a(3, this.e = new PathfinderGoalTempt(this, 0.18f, Item.RAW_FISH.id, true));
        this.goalSelector.a(4, new PathfinderGoalAvoidPlayer(this, EntityHuman.class, 16.0f, 0.23f, 0.4f));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 0.3f, 10.0f, 5.0f));
        this.goalSelector.a(6, new PathfinderGoalJumpOnBlock(this, 0.4f));
        this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3f));
        this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
        this.goalSelector.a(9, new PathfinderGoalBreed(this, 0.23f));
        this.goalSelector.a(10, new PathfinderGoalRandomStroll(this, 0.23f));
        this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0f));
        this.targetSelector.a(1, new PathfinderGoalRandomTargetNonTamed(this, EntityChicken.class, 14.0f, 750, false));
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(18, (Object)(byte)0);
    }
    
    public void bp() {
        if (this.getControllerMove().a()) {
            final float f = this.getControllerMove().b();
            if (f == 0.18f) {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (f == 0.4f) {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }
    
    protected boolean isTypeNotPersistent() {
        return !this.isTamed();
    }
    
    public boolean bh() {
        return true;
    }
    
    public int getMaxHealth() {
        return 10;
    }
    
    protected void a(final float f) {
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("CatType", this.getCatType());
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setCatType(nbttagcompound.getInt("CatType"));
    }
    
    protected String bb() {
        return this.isTamed() ? (this.r() ? "mob.cat.purr" : ((this.random.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }
    
    protected String bc() {
        return "mob.cat.hitt";
    }
    
    protected String bd() {
        return "mob.cat.hitt";
    }
    
    protected float ba() {
        return 0.4f;
    }
    
    protected int getLootId() {
        return Item.LEATHER.id;
    }
    
    public boolean m(final Entity entity) {
        return entity.damageEntity(DamageSource.mobAttack(this), 3);
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        this.d.setSitting(false);
        return super.damageEntity(damagesource, i);
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        CraftEventFactory.callEntityDeathEvent(this);
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        final ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (this.isTamed()) {
            if (entityhuman.name.equalsIgnoreCase(this.getOwnerName()) && !this.world.isStatic && !this.c(itemstack)) {
                this.d.setSitting(!this.isSitting());
            }
        }
        else if (this.e.f() && itemstack != null && itemstack.id == Item.RAW_FISH.id && entityhuman.e(this) < 9.0) {
            if (!entityhuman.abilities.canInstantlyBuild) {
                final ItemStack itemStack = itemstack;
                --itemStack.count;
            }
            if (itemstack.count <= 0) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
            }
            if (!this.world.isStatic) {
                if (this.random.nextInt(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
                    this.setTamed(true);
                    this.setCatType(1 + this.world.random.nextInt(3));
                    this.setOwnerName(entityhuman.name);
                    this.i(true);
                    this.d.setSitting(true);
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
    
    public EntityOcelot b(final EntityAgeable entityageable) {
        final EntityOcelot entityocelot = new EntityOcelot(this.world);
        if (this.isTamed()) {
            entityocelot.setOwnerName(this.getOwnerName());
            entityocelot.setTamed(true);
            entityocelot.setCatType(this.getCatType());
        }
        return entityocelot;
    }
    
    public boolean c(final ItemStack itemstack) {
        return itemstack != null && itemstack.id == Item.RAW_FISH.id;
    }
    
    public boolean mate(final EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(entityanimal instanceof EntityOcelot)) {
            return false;
        }
        final EntityOcelot entityocelot = (EntityOcelot)entityanimal;
        return entityocelot.isTamed() && (this.r() && entityocelot.r());
    }
    
    public int getCatType() {
        return this.datawatcher.getByte(18);
    }
    
    public void setCatType(final int i) {
        this.datawatcher.watch(18, (byte)i);
    }
    
    public boolean canSpawn() {
        if (this.world.random.nextInt(3) == 0) {
            return false;
        }
        if (this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox)) {
            final int i = MathHelper.floor(this.locX);
            final int j = MathHelper.floor(this.boundingBox.b);
            final int k = MathHelper.floor(this.locZ);
            if (j < 63) {
                return false;
            }
            final int l = this.world.getTypeId(i, j - 1, k);
            if (l == Block.GRASS.id || l == Block.LEAVES.id) {
                return true;
            }
        }
        return false;
    }
    
    public String getLocalizedName() {
        return this.hasCustomName() ? this.getCustomName() : (this.isTamed() ? "entity.Cat.name" : super.getLocalizedName());
    }
    
    public void bJ() {
        if (this.world.random.nextInt(7) == 0) {
            for (int i = 0; i < 2; ++i) {
                final EntityOcelot entityocelot = new EntityOcelot(this.world);
                entityocelot.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0f);
                entityocelot.setAge(-24000);
                this.world.addEntity(entityocelot);
            }
        }
    }
    
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
