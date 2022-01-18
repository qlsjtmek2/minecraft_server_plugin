// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.CreeperPowerEvent;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityCreeper extends EntityMonster
{
    private int d;
    private int fuseTicks;
    private int maxFuseTicks;
    private int explosionRadius;
    private int record;
    
    public EntityCreeper(final World world) {
        super(world);
        this.maxFuseTicks = 30;
        this.explosionRadius = 3;
        this.record = -1;
        this.texture = "/mob/creeper.png";
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
        this.goalSelector.a(3, new PathfinderGoalAvoidPlayer(this, EntityOcelot.class, 6.0f, 0.25f, 0.3f));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 0.25f, false));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.2f));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0f, 0, true));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
    }
    
    public boolean bh() {
        return true;
    }
    
    public int ar() {
        return (this.getGoalTarget() == null) ? 3 : (3 + (this.health - 1));
    }
    
    protected void a(final float f) {
        super.a(f);
        this.fuseTicks += (int)(f * 1.5f);
        if (this.fuseTicks > this.maxFuseTicks - 5) {
            this.fuseTicks = this.maxFuseTicks - 5;
        }
    }
    
    public int getMaxHealth() {
        return 20;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, (Object)(byte)(-1));
        this.datawatcher.a(17, (Object)(byte)0);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.datawatcher.getByte(17) == 1) {
            nbttagcompound.setBoolean("powered", true);
        }
        nbttagcompound.setShort("Fuse", (short)this.maxFuseTicks);
        nbttagcompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.datawatcher.watch(17, (byte)(byte)(nbttagcompound.getBoolean("powered") ? 1 : 0));
        if (nbttagcompound.hasKey("Fuse")) {
            this.maxFuseTicks = nbttagcompound.getShort("Fuse");
        }
        if (nbttagcompound.hasKey("ExplosionRadius")) {
            this.explosionRadius = nbttagcompound.getByte("ExplosionRadius");
        }
    }
    
    public void l_() {
        if (this.isAlive()) {
            this.d = this.fuseTicks;
            final int i = this.o();
            if (i > 0 && this.fuseTicks == 0) {
                this.makeSound("random.fuse", 1.0f, 0.5f);
            }
            this.fuseTicks += i;
            if (this.fuseTicks < 0) {
                this.fuseTicks = 0;
            }
            if (this.fuseTicks >= this.maxFuseTicks) {
                this.fuseTicks = this.maxFuseTicks;
                if (!this.world.isStatic) {
                    final boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
                    final float radius = this.isPowered() ? 6.0f : 3.0f;
                    final ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), radius, false);
                    this.world.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), flag);
                        this.die();
                    }
                    else {
                        this.fuseTicks = 0;
                    }
                }
            }
        }
        super.l_();
    }
    
    protected String bc() {
        return "mob.creeper.say";
    }
    
    protected String bd() {
        return "mob.creeper.death";
    }
    
    public void die(final DamageSource damagesource) {
        if (damagesource.getEntity() instanceof EntitySkeleton) {
            final int i = Item.RECORD_1.id + this.random.nextInt(Item.RECORD_12.id - Item.RECORD_1.id + 1);
            this.record = i;
        }
        super.die(damagesource);
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final int j = this.getLootId();
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        if (j > 0) {
            int k = this.random.nextInt(3);
            if (i > 0) {
                k += this.random.nextInt(i + 1);
            }
            if (k > 0) {
                loot.add(new ItemStack(j, k));
            }
        }
        if (this.record != -1) {
            loot.add(new ItemStack(this.record, 1));
            this.record = -1;
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public boolean m(final Entity entity) {
        return true;
    }
    
    public boolean isPowered() {
        return this.datawatcher.getByte(17) == 1;
    }
    
    protected int getLootId() {
        return Item.SULPHUR.id;
    }
    
    public int o() {
        return this.datawatcher.getByte(16);
    }
    
    public void a(final int i) {
        this.datawatcher.watch(16, (byte)i);
    }
    
    public void a(final EntityLightning entitylightning) {
        super.a(entitylightning);
        if (CraftEventFactory.callCreeperPowerEvent(this, entitylightning, CreeperPowerEvent.PowerCause.LIGHTNING).isCancelled()) {
            return;
        }
        this.setPowered(true);
    }
    
    public void setPowered(final boolean powered) {
        if (!powered) {
            this.datawatcher.watch(17, (byte)0);
        }
        else {
            this.datawatcher.watch(17, (byte)1);
        }
    }
}
