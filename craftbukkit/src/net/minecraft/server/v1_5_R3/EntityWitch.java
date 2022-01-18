// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityWitch extends EntityMonster implements IRangedEntity
{
    private static final int[] d;
    private int e;
    
    public EntityWitch(final World world) {
        super(world);
        this.e = 0;
        this.texture = "/mob/villager/witch.png";
        this.bI = 0.25f;
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.bI, 60, 10.0f));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0f, 0, true));
    }
    
    protected void a() {
        super.a();
        this.getDataWatcher().a(21, (Object)(byte)0);
    }
    
    protected String bb() {
        return "mob.witch.idle";
    }
    
    protected String bc() {
        return "mob.witch.hurt";
    }
    
    protected String bd() {
        return "mob.witch.death";
    }
    
    public void a(final boolean flag) {
        this.getDataWatcher().watch(21, (byte)(byte)(flag ? 1 : 0));
    }
    
    public boolean m() {
        return this.getDataWatcher().getByte(21) == 1;
    }
    
    public int getMaxHealth() {
        return 26;
    }
    
    public boolean bh() {
        return true;
    }
    
    public void c() {
        if (!this.world.isStatic) {
            if (this.m()) {
                if (this.e-- <= 0) {
                    this.a(false);
                    final ItemStack itemstack = this.bG();
                    this.setEquipment(0, null);
                    if (itemstack != null && itemstack.id == Item.POTION.id) {
                        final List list = Item.POTION.g(itemstack);
                        if (list != null) {
                            for (final MobEffect mobeffect : list) {
                                this.addEffect(new MobEffect(mobeffect));
                            }
                        }
                    }
                }
            }
            else {
                short short1 = -1;
                if (this.random.nextFloat() < 0.15f && this.isBurning() && !this.hasEffect(MobEffectList.FIRE_RESISTANCE)) {
                    short1 = 16307;
                }
                else if (this.random.nextFloat() < 0.05f && this.health < this.maxHealth) {
                    short1 = 16341;
                }
                else if (this.random.nextFloat() < 0.25f && this.getGoalTarget() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.getGoalTarget().e(this) > 121.0) {
                    short1 = 16274;
                }
                else if (this.random.nextFloat() < 0.25f && this.getGoalTarget() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.getGoalTarget().e(this) > 121.0) {
                    short1 = 16274;
                }
                if (short1 > -1) {
                    this.setEquipment(0, new ItemStack(Item.POTION, 1, short1));
                    this.e = this.bG().n();
                    this.a(true);
                }
            }
            if (this.random.nextFloat() < 7.5E-4f) {
                this.world.broadcastEntityEffect(this, (byte)15);
            }
        }
        super.c();
    }
    
    protected int c(final DamageSource damagesource, int i) {
        i = super.c(damagesource, i);
        if (damagesource.getEntity() == this) {
            i = 0;
        }
        if (damagesource.q()) {
            i *= (int)0.15;
        }
        return i;
    }
    
    public float bE() {
        float f = super.bE();
        if (this.m()) {
            f *= 0.75f;
        }
        return f;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        for (int j = this.random.nextInt(3) + 1, k = 0; k < j; ++k) {
            int l = this.random.nextInt(3);
            final int i2 = EntityWitch.d[this.random.nextInt(EntityWitch.d.length)];
            if (i > 0) {
                l += this.random.nextInt(i + 1);
            }
            loot.add(new org.bukkit.inventory.ItemStack(i2, l));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public void a(final EntityLiving entityliving, final float f) {
        if (!this.m()) {
            final EntityPotion entityPotion;
            final EntityPotion entitypotion = entityPotion = new EntityPotion(this.world, this, 32732);
            entityPotion.pitch += 20.0f;
            final double d0 = entityliving.locX + entityliving.motX - this.locX;
            final double d2 = entityliving.locY + entityliving.getHeadHeight() - 1.100000023841858 - this.locY;
            final double d3 = entityliving.locZ + entityliving.motZ - this.locZ;
            final float f2 = MathHelper.sqrt(d0 * d0 + d3 * d3);
            if (f2 >= 8.0f && !entityliving.hasEffect(MobEffectList.SLOWER_MOVEMENT)) {
                entitypotion.setPotionValue(32698);
            }
            else if (entityliving.getHealth() >= 8 && !entityliving.hasEffect(MobEffectList.POISON)) {
                entitypotion.setPotionValue(32660);
            }
            else if (f2 <= 3.0f && !entityliving.hasEffect(MobEffectList.WEAKNESS) && this.random.nextFloat() < 0.25f) {
                entitypotion.setPotionValue(32696);
            }
            entitypotion.shoot(d0, d2 + f2 * 0.2f, d3, 0.75f, 8.0f);
            this.world.addEntity(entitypotion);
        }
    }
    
    static {
        d = new int[] { Item.GLOWSTONE_DUST.id, Item.SUGAR.id, Item.REDSTONE.id, Item.SPIDER_EYE.id, Item.GLASS_BOTTLE.id, Item.SULPHUR.id, Item.STICK.id, Item.STICK.id };
    }
}
