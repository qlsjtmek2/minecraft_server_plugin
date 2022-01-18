// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.EntityShootBowEvent;
import java.util.Calendar;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.Material;
import java.util.ArrayList;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntitySkeleton extends EntityMonster implements IRangedEntity
{
    private PathfinderGoalArrowAttack d;
    private PathfinderGoalMeleeAttack e;
    
    public EntitySkeleton(final World world) {
        super(world);
        this.d = new PathfinderGoalArrowAttack(this, 0.25f, 20, 60, 15.0f);
        this.e = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 0.31f, false);
        this.texture = "/mob/skeleton.png";
        this.bI = 0.25f;
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
        this.goalSelector.a(3, new PathfinderGoalFleeSun(this, this.bI));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0f, 0, true));
        if (world != null && !world.isStatic) {
            this.m();
        }
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(13, new Byte((byte)0));
    }
    
    public boolean bh() {
        return true;
    }
    
    public int getMaxHealth() {
        return 20;
    }
    
    protected String bb() {
        return "mob.skeleton.say";
    }
    
    protected String bc() {
        return "mob.skeleton.hurt";
    }
    
    protected String bd() {
        return "mob.skeleton.death";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.skeleton.step", 0.15f, 1.0f);
    }
    
    public boolean m(final Entity entity) {
        if (super.m(entity)) {
            if (this.getSkeletonType() == 1 && entity instanceof EntityLiving) {
                ((EntityLiving)entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 200));
            }
            return true;
        }
        return false;
    }
    
    public int c(final Entity entity) {
        if (this.getSkeletonType() == 1) {
            final ItemStack itemstack = this.bG();
            int i = 4;
            if (itemstack != null) {
                i += itemstack.a((Entity)this);
            }
            return i;
        }
        return super.c(entity);
    }
    
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }
    
    public void c() {
        if (this.world.v() && !this.world.isStatic) {
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
        if (this.world.isStatic && this.getSkeletonType() == 1) {
            this.a(0.72f, 2.34f);
        }
        super.c();
    }
    
    public void die(final DamageSource damagesource) {
        super.die(damagesource);
        if (damagesource.h() instanceof EntityArrow && damagesource.getEntity() instanceof EntityHuman) {
            final EntityHuman entityhuman = (EntityHuman)damagesource.getEntity();
            final double d0 = entityhuman.locX - this.locX;
            final double d2 = entityhuman.locZ - this.locZ;
            if (d0 * d0 + d2 * d2 >= 2500.0) {
                entityhuman.a(AchievementList.v);
            }
        }
    }
    
    protected int getLootId() {
        return Item.ARROW.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        if (this.getSkeletonType() == 1) {
            final int count = this.random.nextInt(3 + i) - 1;
            if (count > 0) {
                loot.add(new org.bukkit.inventory.ItemStack(Material.COAL, count));
            }
        }
        else {
            final int count = this.random.nextInt(3 + i);
            if (count > 0) {
                loot.add(new org.bukkit.inventory.ItemStack(Material.ARROW, count));
            }
        }
        final int count = this.random.nextInt(3 + i);
        if (count > 0) {
            loot.add(new org.bukkit.inventory.ItemStack(Material.BONE, count));
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
    
    protected ItemStack l(final int i) {
        if (this.getSkeletonType() == 1) {
            return new ItemStack(Item.SKULL.id, 1, 1);
        }
        return null;
    }
    
    protected void bH() {
        super.bH();
        this.setEquipment(0, new ItemStack(Item.BOW));
    }
    
    public void bJ() {
        if (this.world.worldProvider instanceof WorldProviderHell && this.aE().nextInt(5) > 0) {
            this.goalSelector.a(4, this.e);
            this.setSkeletonType(1);
            this.setEquipment(0, new ItemStack(Item.STONE_SWORD));
        }
        else {
            this.goalSelector.a(4, this.d);
            this.bH();
            this.bI();
        }
        this.h(this.random.nextFloat() < EntitySkeleton.au[this.world.difficulty]);
        if (this.getEquipment(4) == null) {
            final Calendar calendar = this.world.V();
            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25f) {
                this.setEquipment(4, new ItemStack((this.random.nextFloat() < 0.1f) ? Block.JACK_O_LANTERN : Block.PUMPKIN));
                this.dropChances[4] = 0.0f;
            }
        }
    }
    
    public void m() {
        this.goalSelector.a(this.e);
        this.goalSelector.a(this.d);
        final ItemStack itemstack = this.bG();
        if (itemstack != null && itemstack.id == Item.BOW.id) {
            this.goalSelector.a(4, this.d);
        }
        else {
            this.goalSelector.a(4, this.e);
        }
    }
    
    public void a(final EntityLiving entityliving, final float f) {
        final EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6f, 14 - this.world.difficulty * 4);
        final int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, this.bG());
        final int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, this.bG());
        entityarrow.b(f * 2.0f + this.random.nextGaussian() * 0.25 + this.world.difficulty * 0.11f);
        if (i > 0) {
            entityarrow.b(entityarrow.c() + i * 0.5 + 0.5);
        }
        if (j > 0) {
            entityarrow.a(j);
        }
        if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bG()) > 0 || this.getSkeletonType() == 1) {
            entityarrow.setOnFire(100);
        }
        final EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, this.bG(), entityarrow, 1.6f);
        if (event.isCancelled()) {
            event.getProjectile().remove();
            return;
        }
        if (event.getProjectile() == entityarrow.getBukkitEntity()) {
            this.world.addEntity(entityarrow);
        }
        this.makeSound("random.bow", 1.0f, 1.0f / (this.aE().nextFloat() * 0.4f + 0.8f));
    }
    
    public int getSkeletonType() {
        return this.datawatcher.getByte(13);
    }
    
    public void setSkeletonType(final int i) {
        this.datawatcher.watch(13, (byte)i);
        this.fireProof = (i == 1);
        if (i == 1) {
            this.a(0.72f, 2.34f);
        }
        else {
            this.a(0.6f, 1.8f);
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("SkeletonType")) {
            final byte b0 = nbttagcompound.getByte("SkeletonType");
            this.setSkeletonType(b0);
        }
        this.m();
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setByte("SkeletonType", (byte)this.getSkeletonType());
    }
    
    public void setEquipment(final int i, final ItemStack itemstack) {
        super.setEquipment(i, itemstack);
        if (!this.world.isStatic && i == 0) {
            this.m();
        }
    }
}
