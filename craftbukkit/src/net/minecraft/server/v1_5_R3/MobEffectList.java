// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.craftbukkit.v1_5_R3.potion.CraftPotionEffectType;

public class MobEffectList
{
    public static final MobEffectList[] byId;
    public static final MobEffectList b;
    public static final MobEffectList FASTER_MOVEMENT;
    public static final MobEffectList SLOWER_MOVEMENT;
    public static final MobEffectList FASTER_DIG;
    public static final MobEffectList SLOWER_DIG;
    public static final MobEffectList INCREASE_DAMAGE;
    public static final MobEffectList HEAL;
    public static final MobEffectList HARM;
    public static final MobEffectList JUMP;
    public static final MobEffectList CONFUSION;
    public static final MobEffectList REGENERATION;
    public static final MobEffectList RESISTANCE;
    public static final MobEffectList FIRE_RESISTANCE;
    public static final MobEffectList WATER_BREATHING;
    public static final MobEffectList INVISIBILITY;
    public static final MobEffectList BLINDNESS;
    public static final MobEffectList NIGHT_VISION;
    public static final MobEffectList HUNGER;
    public static final MobEffectList WEAKNESS;
    public static final MobEffectList POISON;
    public static final MobEffectList WITHER;
    public static final MobEffectList w;
    public static final MobEffectList x;
    public static final MobEffectList y;
    public static final MobEffectList z;
    public static final MobEffectList A;
    public static final MobEffectList B;
    public static final MobEffectList C;
    public static final MobEffectList D;
    public static final MobEffectList E;
    public static final MobEffectList F;
    public static final MobEffectList G;
    public final int id;
    private String I;
    private int J;
    private final boolean K;
    private double L;
    private boolean M;
    private final int N;
    
    protected MobEffectList(final int i, final boolean flag, final int j) {
        this.I = "";
        this.J = -1;
        this.id = i;
        MobEffectList.byId[i] = this;
        this.K = flag;
        if (flag) {
            this.L = 0.5;
        }
        else {
            this.L = 1.0;
        }
        this.N = j;
        PotionEffectType.registerPotionEffectType(new CraftPotionEffectType(this));
    }
    
    protected MobEffectList b(final int i, final int j) {
        this.J = i + j * 8;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void tick(final EntityLiving entityliving, final int i) {
        if (this.id == MobEffectList.REGENERATION.id) {
            if (entityliving.getHealth() < entityliving.maxHealth) {
                entityliving.heal(1, EntityRegainHealthEvent.RegainReason.MAGIC_REGEN);
            }
        }
        else if (this.id == MobEffectList.POISON.id) {
            if (entityliving.getHealth() > 1) {
                entityliving.damageEntity(CraftEventFactory.POISON, 1);
            }
        }
        else if (this.id == MobEffectList.WITHER.id) {
            entityliving.damageEntity(DamageSource.WITHER, 1);
        }
        else if (this.id == MobEffectList.HUNGER.id && entityliving instanceof EntityHuman) {
            ((EntityHuman)entityliving).j(0.025f * (i + 1));
        }
        else if ((this.id != MobEffectList.HEAL.id || entityliving.bD()) && (this.id != MobEffectList.HARM.id || !entityliving.bD())) {
            if ((this.id == MobEffectList.HARM.id && !entityliving.bD()) || (this.id == MobEffectList.HEAL.id && entityliving.bD())) {
                entityliving.damageEntity(DamageSource.MAGIC, 6 << i);
            }
        }
        else {
            entityliving.heal(6 << i, EntityRegainHealthEvent.RegainReason.MAGIC);
        }
    }
    
    public void applyInstantEffect(final EntityLiving entityliving, final EntityLiving entityliving1, final int i, final double d0) {
        this.applyInstantEffect(entityliving, entityliving1, i, d0, null);
    }
    
    public void applyInstantEffect(final EntityLiving entityliving, final EntityLiving entityliving1, final int i, final double d0, final EntityPotion potion) {
        if ((this.id != MobEffectList.HEAL.id || entityliving1.bD()) && (this.id != MobEffectList.HARM.id || !entityliving1.bD())) {
            if ((this.id == MobEffectList.HARM.id && !entityliving1.bD()) || (this.id == MobEffectList.HEAL.id && entityliving1.bD())) {
                final int j = (int)(d0 * (6 << i) + 0.5);
                if (entityliving == null) {
                    entityliving1.damageEntity(DamageSource.MAGIC, j);
                }
                else {
                    entityliving1.damageEntity(DamageSource.b((potion != null) ? potion : entityliving1, entityliving), j);
                }
            }
        }
        else {
            final int j = (int)(d0 * (6 << i) + 0.5);
            entityliving1.heal(j, EntityRegainHealthEvent.RegainReason.MAGIC);
        }
    }
    
    public boolean isInstant() {
        return false;
    }
    
    public boolean a(final int i, final int j) {
        if (this.id == MobEffectList.REGENERATION.id || this.id == MobEffectList.POISON.id) {
            final int k = 25 >> j;
            return k <= 0 || i % k == 0;
        }
        if (this.id == MobEffectList.WITHER.id) {
            final int k = 40 >> j;
            return k <= 0 || i % k == 0;
        }
        return this.id == MobEffectList.HUNGER.id;
    }
    
    public MobEffectList b(final String s) {
        this.I = s;
        return this;
    }
    
    public String a() {
        return this.I;
    }
    
    protected MobEffectList a(final double d0) {
        this.L = d0;
        return this;
    }
    
    public double getDurationModifier() {
        return this.L;
    }
    
    public boolean i() {
        return this.M;
    }
    
    public int j() {
        return this.N;
    }
    
    static {
        byId = new MobEffectList[32];
        b = null;
        FASTER_MOVEMENT = new MobEffectList(1, false, 8171462).b("potion.moveSpeed").b(0, 0);
        SLOWER_MOVEMENT = new MobEffectList(2, true, 5926017).b("potion.moveSlowdown").b(1, 0);
        FASTER_DIG = new MobEffectList(3, false, 14270531).b("potion.digSpeed").b(2, 0).a(1.5);
        SLOWER_DIG = new MobEffectList(4, true, 4866583).b("potion.digSlowDown").b(3, 0);
        INCREASE_DAMAGE = new MobEffectList(5, false, 9643043).b("potion.damageBoost").b(4, 0);
        HEAL = new InstantMobEffect(6, false, 16262179).b("potion.heal");
        HARM = new InstantMobEffect(7, true, 4393481).b("potion.harm");
        JUMP = new MobEffectList(8, false, 7889559).b("potion.jump").b(2, 1);
        CONFUSION = new MobEffectList(9, true, 5578058).b("potion.confusion").b(3, 1).a(0.25);
        REGENERATION = new MobEffectList(10, false, 13458603).b("potion.regeneration").b(7, 0).a(0.25);
        RESISTANCE = new MobEffectList(11, false, 10044730).b("potion.resistance").b(6, 1);
        FIRE_RESISTANCE = new MobEffectList(12, false, 14981690).b("potion.fireResistance").b(7, 1);
        WATER_BREATHING = new MobEffectList(13, false, 3035801).b("potion.waterBreathing").b(0, 2);
        INVISIBILITY = new MobEffectList(14, false, 8356754).b("potion.invisibility").b(0, 1);
        BLINDNESS = new MobEffectList(15, true, 2039587).b("potion.blindness").b(5, 1).a(0.25);
        NIGHT_VISION = new MobEffectList(16, false, 2039713).b("potion.nightVision").b(4, 1);
        HUNGER = new MobEffectList(17, true, 5797459).b("potion.hunger").b(1, 1);
        WEAKNESS = new MobEffectList(18, true, 4738376).b("potion.weakness").b(5, 0);
        POISON = new MobEffectList(19, true, 5149489).b("potion.poison").b(6, 0).a(0.25);
        WITHER = new MobEffectList(20, true, 3484199).b("potion.wither").b(1, 2).a(0.25);
        w = null;
        x = null;
        y = null;
        z = null;
        A = null;
        B = null;
        C = null;
        D = null;
        E = null;
        F = null;
        G = null;
    }
}
