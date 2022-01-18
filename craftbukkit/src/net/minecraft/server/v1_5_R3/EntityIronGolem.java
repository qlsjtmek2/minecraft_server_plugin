// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class EntityIronGolem extends EntityGolem
{
    private int e;
    Village d;
    private int f;
    private int g;
    
    public EntityIronGolem(final World world) {
        super(world);
        this.e = 0;
        this.d = null;
        this.texture = "/mob/villager_golem.png";
        this.a(1.4f, 2.9f);
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 0.25f, true));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.22f, 32.0f));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.16f, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 0.16f));
        this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.16f));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 16.0f, 0, false, true, IMonster.a));
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, (Object)(byte)0);
    }
    
    public boolean bh() {
        return true;
    }
    
    protected void bp() {
        final int e = this.e - 1;
        this.e = e;
        if (e <= 0) {
            this.e = 70 + this.random.nextInt(50);
            this.d = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);
            if (this.d == null) {
                this.aO();
            }
            else {
                final ChunkCoordinates chunkcoordinates = this.d.getCenter();
                this.b(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, (int)(this.d.getSize() * 0.6f));
            }
        }
        super.bp();
    }
    
    public int getMaxHealth() {
        return 100;
    }
    
    protected int h(final int i) {
        return i;
    }
    
    protected void o(final Entity entity) {
        if (entity instanceof IMonster && this.aE().nextInt(20) == 0) {
            this.setGoalTarget((EntityLiving)entity);
        }
        super.o(entity);
    }
    
    public void c() {
        super.c();
        if (this.f > 0) {
            --this.f;
        }
        if (this.g > 0) {
            --this.g;
        }
        if (this.motX * this.motX + this.motZ * this.motZ > 2.500000277905201E-7 && this.random.nextInt(5) == 0) {
            final int i = MathHelper.floor(this.locX);
            final int j = MathHelper.floor(this.locY - 0.20000000298023224 - this.height);
            final int k = MathHelper.floor(this.locZ);
            final int l = this.world.getTypeId(i, j, k);
            if (l > 0) {
                this.world.addParticle("tilecrack_" + l + "_" + this.world.getData(i, j, k), this.locX + (this.random.nextFloat() - 0.5) * this.width, this.boundingBox.b + 0.1, this.locZ + (this.random.nextFloat() - 0.5) * this.width, 4.0 * (this.random.nextFloat() - 0.5), 0.5, (this.random.nextFloat() - 0.5) * 4.0);
            }
        }
    }
    
    public boolean a(final Class oclass) {
        return (!this.p() || !EntityHuman.class.isAssignableFrom(oclass)) && super.a(oclass);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("PlayerCreated", this.p());
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setPlayerCreated(nbttagcompound.getBoolean("PlayerCreated"));
    }
    
    public boolean m(final Entity entity) {
        this.f = 10;
        this.world.broadcastEntityEffect(this, (byte)4);
        final boolean flag = entity.damageEntity(DamageSource.mobAttack(this), 7 + this.random.nextInt(15));
        if (flag) {
            entity.motY += 0.4000000059604645;
        }
        this.makeSound("mob.irongolem.throw", 1.0f, 1.0f);
        return flag;
    }
    
    public Village m() {
        return this.d;
    }
    
    public void a(final boolean flag) {
        this.g = (flag ? 400 : 0);
        this.world.broadcastEntityEffect(this, (byte)11);
    }
    
    protected String bb() {
        return "none";
    }
    
    protected String bc() {
        return "mob.irongolem.hit";
    }
    
    protected String bd() {
        return "mob.irongolem.death";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.irongolem.walk", 1.0f, 1.0f);
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        final int j = this.random.nextInt(3);
        if (j > 0) {
            loot.add(CraftItemStack.asNewCraftStack(Item.byId[Block.RED_ROSE.id], j));
        }
        final int k = 3 + this.random.nextInt(3);
        if (k > 0) {
            loot.add(CraftItemStack.asNewCraftStack(Item.IRON_INGOT, k));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public int o() {
        return this.g;
    }
    
    public boolean p() {
        return (this.datawatcher.getByte(16) & 0x1) != 0x0;
    }
    
    public void setPlayerCreated(final boolean flag) {
        final byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, (byte)(b0 | 0x1));
        }
        else {
            this.datawatcher.watch(16, (byte)(b0 & 0xFFFFFFFE));
        }
    }
    
    public void die(final DamageSource damagesource) {
        if (!this.p() && this.killer != null && this.d != null) {
            this.d.a(this.killer.getName(), -5);
        }
        super.die(damagesource);
    }
}
