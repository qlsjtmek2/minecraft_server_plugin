// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Calendar;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntityZombie extends EntityMonster
{
    private int d;
    private int lastTick;
    
    public EntityZombie(final World world) {
        super(world);
        this.d = 0;
        this.lastTick = MinecraftServer.currentTick;
        this.texture = "/mob/zombie.png";
        this.bI = 0.23f;
        this.getNavigation().b(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalBreakDoor(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, this.bI, false));
        this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, this.bI, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, this.bI));
        this.goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, this.bI, false));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0f, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 16.0f, 0, false));
    }
    
    protected int ay() {
        return 40;
    }
    
    public float bE() {
        return super.bE() * (this.isBaby() ? 1.5f : 1.0f);
    }
    
    protected void a() {
        super.a();
        this.getDataWatcher().a(12, (Object)(byte)0);
        this.getDataWatcher().a(13, (Object)(byte)0);
        this.getDataWatcher().a(14, (Object)(byte)0);
    }
    
    public int getMaxHealth() {
        return 20;
    }
    
    public int aZ() {
        int i = super.aZ() + 2;
        if (i > 20) {
            i = 20;
        }
        return i;
    }
    
    protected boolean bh() {
        return true;
    }
    
    public boolean isBaby() {
        return this.getDataWatcher().getByte(12) == 1;
    }
    
    public void setBaby(final boolean flag) {
        this.getDataWatcher().watch(12, (byte)(byte)(flag ? 1 : 0));
    }
    
    public boolean isVillager() {
        return this.getDataWatcher().getByte(13) == 1;
    }
    
    public void setVillager(final boolean flag) {
        this.getDataWatcher().watch(13, (byte)(byte)(flag ? 1 : 0));
    }
    
    public void c() {
        if (this.world.v() && !this.world.isStatic && !this.isBaby()) {
            final float f = this.c(1.0f);
            if (f > 0.5f && this.random.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && this.world.l(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ))) {
                boolean flag = true;
                final ItemStack itemstack = this.getEquipment(4);
                if (itemstack != null) {
                    if (itemstack.g()) {
                        itemstack.setData(itemstack.j() + this.random.nextInt(2));
                        if (itemstack.j() >= itemstack.l()) {
                            this.a(itemstack);
                            this.setEquipment(4, null);
                        }
                    }
                    flag = false;
                }
                if (flag) {
                    final EntityCombustEvent event = new EntityCombustEvent(this.getBukkitEntity(), 8);
                    this.world.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        this.setOnFire(event.getDuration());
                    }
                }
            }
        }
        super.c();
    }
    
    public void l_() {
        if (!this.world.isStatic && this.o()) {
            int i = this.q();
            final int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
            this.lastTick = MinecraftServer.currentTick;
            i *= elapsedTicks;
            this.d -= i;
            if (this.d <= 0) {
                this.p();
            }
        }
        super.l_();
    }
    
    public boolean m(final Entity entity) {
        final boolean flag = super.m(entity);
        if (flag && this.bG() == null && this.isBurning() && this.random.nextFloat() < this.world.difficulty * 0.3f) {
            final EntityCombustByEntityEvent event = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 2 * this.world.difficulty);
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                entity.setOnFire(event.getDuration());
            }
        }
        return flag;
    }
    
    public int c(final Entity entity) {
        final ItemStack itemstack = this.bG();
        final float f = (((CraftLivingEntity)this.bukkitEntity).getMaxHealth() - this.getHealth()) / ((CraftLivingEntity)this.bukkitEntity).getMaxHealth();
        int i = 3 + MathHelper.d(f * 4.0f);
        if (itemstack != null) {
            i += itemstack.a((Entity)this);
        }
        return i;
    }
    
    protected String bb() {
        return "mob.zombie.say";
    }
    
    protected String bc() {
        return "mob.zombie.hurt";
    }
    
    protected String bd() {
        return "mob.zombie.death";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.zombie.step", 0.15f, 1.0f);
    }
    
    protected int getLootId() {
        return Item.ROTTEN_FLESH.id;
    }
    
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }
    
    protected ItemStack l(final int i) {
        switch (this.random.nextInt(3)) {
            case 0: {
                return new ItemStack(Item.IRON_INGOT.id, 1, 0);
            }
            case 1: {
                return new ItemStack(Item.CARROT.id, 1, 0);
            }
            case 2: {
                return new ItemStack(Item.POTATO.id, 1, 0);
            }
            default: {
                return null;
            }
        }
    }
    
    protected void bH() {
        super.bH();
        if (this.random.nextFloat() < ((this.world.difficulty == 3) ? 0.05f : 0.01f)) {
            final int i = this.random.nextInt(3);
            if (i == 0) {
                this.setEquipment(0, new ItemStack(Item.IRON_SWORD));
            }
            else {
                this.setEquipment(0, new ItemStack(Item.IRON_SPADE));
            }
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.isBaby()) {
            nbttagcompound.setBoolean("IsBaby", true);
        }
        if (this.isVillager()) {
            nbttagcompound.setBoolean("IsVillager", true);
        }
        nbttagcompound.setInt("ConversionTime", this.o() ? this.d : -1);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.getBoolean("IsBaby")) {
            this.setBaby(true);
        }
        if (nbttagcompound.getBoolean("IsVillager")) {
            this.setVillager(true);
        }
        if (nbttagcompound.hasKey("ConversionTime") && nbttagcompound.getInt("ConversionTime") > -1) {
            this.a(nbttagcompound.getInt("ConversionTime"));
        }
    }
    
    public void a(final EntityLiving entityliving) {
        super.a(entityliving);
        if (this.world.difficulty >= 2 && entityliving instanceof EntityVillager) {
            if (this.world.difficulty == 2 && this.random.nextBoolean()) {
                return;
            }
            final EntityZombie entityzombie = new EntityZombie(this.world);
            entityzombie.k(entityliving);
            this.world.kill(entityliving);
            entityzombie.bJ();
            entityzombie.setVillager(true);
            if (entityliving.isBaby()) {
                entityzombie.setBaby(true);
            }
            this.world.addEntity(entityzombie);
            this.world.a(null, 1016, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
        }
    }
    
    public void bJ() {
        this.h(this.random.nextFloat() < EntityZombie.au[this.world.difficulty]);
        if (this.world.random.nextFloat() < 0.05f) {
            this.setVillager(true);
        }
        this.bH();
        this.bI();
        if (this.getEquipment(4) == null) {
            final Calendar calendar = this.world.V();
            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25f) {
                this.setEquipment(4, new ItemStack((this.random.nextFloat() < 0.1f) ? Block.JACK_O_LANTERN : Block.PUMPKIN));
                this.dropChances[4] = 0.0f;
            }
        }
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        final ItemStack itemstack = entityhuman.cd();
        if (itemstack != null && itemstack.getItem() == Item.GOLDEN_APPLE && itemstack.getData() == 0 && this.isVillager() && this.hasEffect(MobEffectList.WEAKNESS)) {
            if (!entityhuman.abilities.canInstantlyBuild) {
                final ItemStack itemStack = itemstack;
                --itemStack.count;
            }
            if (itemstack.count <= 0) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
            }
            if (!this.world.isStatic) {
                this.a(this.random.nextInt(2401) + 3600);
            }
            return true;
        }
        return false;
    }
    
    protected void a(final int i) {
        this.d = i;
        this.getDataWatcher().watch(14, (byte)1);
        this.o(MobEffectList.WEAKNESS.id);
        this.addEffect(new MobEffect(MobEffectList.INCREASE_DAMAGE.id, i, Math.min(this.world.difficulty - 1, 0)));
        this.world.broadcastEntityEffect(this, (byte)16);
    }
    
    public boolean o() {
        return this.getDataWatcher().getByte(14) == 1;
    }
    
    protected void p() {
        final EntityVillager entityvillager = new EntityVillager(this.world);
        entityvillager.k(this);
        entityvillager.bJ();
        entityvillager.q();
        if (this.isBaby()) {
            entityvillager.setAge(-24000);
        }
        this.world.kill(this);
        this.world.addEntity(entityvillager);
        entityvillager.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 200, 0));
        this.world.a(null, 1017, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
    }
    
    protected int q() {
        int i = 1;
        if (this.random.nextFloat() < 0.01f) {
            for (int j = 0, k = (int)this.locX - 4; k < (int)this.locX + 4 && j < 14; ++k) {
                for (int l = (int)this.locY - 4; l < (int)this.locY + 4 && j < 14; ++l) {
                    for (int i2 = (int)this.locZ - 4; i2 < (int)this.locZ + 4 && j < 14; ++i2) {
                        final int j2 = this.world.getTypeId(k, l, i2);
                        if (j2 == Block.IRON_FENCE.id || j2 == Block.BED.id) {
                            if (this.random.nextFloat() < 0.3f) {
                                ++i;
                            }
                            ++j;
                        }
                    }
                }
            }
        }
        return i;
    }
}
