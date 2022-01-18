// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;
import java.util.List;

public class EntityPigZombie extends EntityZombie
{
    public int angerLevel;
    private int soundDelay;
    
    public EntityPigZombie(final World world) {
        super(world);
        this.angerLevel = 0;
        this.soundDelay = 0;
        this.texture = "/mob/pigzombie.png";
        this.bI = 0.5f;
        this.fireProof = true;
    }
    
    protected boolean bh() {
        return false;
    }
    
    public void l_() {
        this.bI = ((this.target != null) ? 0.95f : 0.5f);
        if (this.soundDelay > 0 && --this.soundDelay == 0) {
            this.makeSound("mob.zombiepig.zpigangry", this.ba() * 2.0f, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.l_();
    }
    
    public boolean canSpawn() {
        return this.world.difficulty > 0 && this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)this.angerLevel);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.angerLevel = nbttagcompound.getShort("Anger");
    }
    
    protected Entity findTarget() {
        return (this.angerLevel == 0) ? null : super.findTarget();
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        final Entity entity = damagesource.getEntity();
        if (entity instanceof EntityHuman) {
            final List list = this.world.getEntities(this, this.boundingBox.grow(32.0, 32.0, 32.0));
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity2 = list.get(j);
                if (entity2 instanceof EntityPigZombie) {
                    final EntityPigZombie entitypigzombie = (EntityPigZombie)entity2;
                    entitypigzombie.p(entity);
                }
            }
            this.p(entity);
        }
        return super.damageEntity(damagesource, i);
    }
    
    private void p(Entity entity) {
        final org.bukkit.entity.Entity bukkitTarget = (entity == null) ? null : entity.getBukkitEntity();
        final EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET);
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        if (event.getTarget() == null) {
            this.target = null;
            return;
        }
        entity = ((CraftEntity)event.getTarget()).getHandle();
        this.target = entity;
        this.angerLevel = 400 + this.random.nextInt(400);
        this.soundDelay = this.random.nextInt(40);
    }
    
    protected String bb() {
        return "mob.zombiepig.zpig";
    }
    
    protected String bc() {
        return "mob.zombiepig.zpighurt";
    }
    
    protected String bd() {
        return "mob.zombiepig.zpigdeath";
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        int j = this.random.nextInt(2 + i);
        if (j > 0) {
            loot.add(CraftItemStack.asNewCraftStack(Item.ROTTEN_FLESH, j));
        }
        j = this.random.nextInt(2 + i);
        if (j > 0) {
            loot.add(CraftItemStack.asNewCraftStack(Item.GOLD_NUGGET, j));
        }
        if (this.lastDamageByPlayerTime > 0) {
            final int k = this.random.nextInt(200) - i;
            if (k < 5) {
                final net.minecraft.server.v1_5_R3.ItemStack itemstack = this.l((k <= 0) ? 1 : 0);
                if (itemstack != null) {
                    loot.add(CraftItemStack.asCraftMirror(itemstack));
                }
            }
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        return false;
    }
    
    protected net.minecraft.server.v1_5_R3.ItemStack l(final int i) {
        return new net.minecraft.server.v1_5_R3.ItemStack(Item.GOLD_INGOT.id, 1, 0);
    }
    
    protected int getLootId() {
        return Item.ROTTEN_FLESH.id;
    }
    
    protected void bH() {
        this.setEquipment(0, new net.minecraft.server.v1_5_R3.ItemStack(Item.GOLD_SWORD));
    }
    
    public void bJ() {
        super.bJ();
        this.setVillager(false);
    }
    
    public int c(final Entity entity) {
        final net.minecraft.server.v1_5_R3.ItemStack itemstack = this.bG();
        int i = 5;
        if (itemstack != null) {
            i += itemstack.a((Entity)this);
        }
        return i;
    }
}
