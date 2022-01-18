// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.ArrayList;

public class EntityPig extends EntityAnimal
{
    private final PathfinderGoalPassengerCarrotStick d;
    
    public EntityPig(final World world) {
        super(world);
        this.texture = "/mob/pig.png";
        this.a(0.9f, 0.9f);
        this.getNavigation().a(true);
        final float f = 0.25f;
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38f));
        this.goalSelector.a(2, this.d = new PathfinderGoalPassengerCarrotStick(this, 0.34f));
        this.goalSelector.a(3, new PathfinderGoalBreed(this, f));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 0.3f, Item.CARROT_STICK.id, false));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 0.3f, Item.CARROT.id, false));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 0.28f));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, f));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }
    
    public boolean bh() {
        return true;
    }
    
    public int getMaxHealth() {
        return 10;
    }
    
    protected void bo() {
        super.bo();
    }
    
    public boolean bL() {
        final ItemStack itemstack = ((EntityHuman)this.passenger).bG();
        return itemstack != null && itemstack.id == Item.CARROT_STICK.id;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, (Object)(byte)0);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", this.hasSaddle());
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSaddle(nbttagcompound.getBoolean("Saddle"));
    }
    
    protected String bb() {
        return "mob.pig.say";
    }
    
    protected String bc() {
        return "mob.pig.say";
    }
    
    protected String bd() {
        return "mob.pig.death";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.pig.step", 0.15f, 1.0f);
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        if (super.a_(entityhuman)) {
            return true;
        }
        if (this.hasSaddle() && !this.world.isStatic && (this.passenger == null || this.passenger == entityhuman)) {
            entityhuman.mount(this);
            return true;
        }
        return false;
    }
    
    protected int getLootId() {
        return this.isBurning() ? Item.GRILLED_PORK.id : Item.PORK.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        final int j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
        if (j > 0) {
            if (this.isBurning()) {
                loot.add(new org.bukkit.inventory.ItemStack(Item.GRILLED_PORK.id, j));
            }
            else {
                loot.add(new org.bukkit.inventory.ItemStack(Item.PORK.id, j));
            }
        }
        if (this.hasSaddle()) {
            loot.add(new org.bukkit.inventory.ItemStack(Item.SADDLE.id, 1));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public boolean hasSaddle() {
        return (this.datawatcher.getByte(16) & 0x1) != 0x0;
    }
    
    public void setSaddle(final boolean flag) {
        if (flag) {
            this.datawatcher.watch(16, (byte)1);
        }
        else {
            this.datawatcher.watch(16, (byte)0);
        }
    }
    
    public void a(final EntityLightning entitylightning) {
        if (!this.world.isStatic) {
            final EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
            if (CraftEventFactory.callPigZapEvent(this, entitylightning, entitypigzombie).isCancelled()) {
                return;
            }
            entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.world.addEntity(entitypigzombie, CreatureSpawnEvent.SpawnReason.LIGHTNING);
            this.die();
        }
    }
    
    protected void a(final float f) {
        super.a(f);
        if (f > 5.0f && this.passenger instanceof EntityHuman) {
            ((EntityHuman)this.passenger).a(AchievementList.u);
        }
    }
    
    public EntityPig b(final EntityAgeable entityageable) {
        return new EntityPig(this.world);
    }
    
    public boolean c(final ItemStack itemstack) {
        return itemstack != null && itemstack.id == Item.CARROT.id;
    }
    
    public PathfinderGoalPassengerCarrotStick n() {
        return this.d;
    }
    
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
