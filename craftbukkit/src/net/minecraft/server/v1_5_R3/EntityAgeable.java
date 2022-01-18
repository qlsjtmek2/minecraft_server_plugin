// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.CreatureSpawnEvent;

public abstract class EntityAgeable extends EntityCreature
{
    private float d;
    private float e;
    public boolean ageLocked;
    
    public void inactiveTick() {
        super.inactiveTick();
        if (this.world.isStatic || this.ageLocked) {
            this.a(this.isBaby());
        }
        else {
            int i = this.getAge();
            if (i < 0) {
                ++i;
                this.setAge(i);
            }
            else if (i > 0) {
                --i;
                this.setAge(i);
            }
        }
    }
    
    public EntityAgeable(final World world) {
        super(world);
        this.d = -1.0f;
        this.ageLocked = false;
    }
    
    public abstract EntityAgeable createChild(final EntityAgeable p0);
    
    public boolean a_(final EntityHuman entityhuman) {
        final ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (itemstack != null && itemstack.id == Item.MONSTER_EGG.id && !this.world.isStatic) {
            final Class oclass = EntityTypes.a(itemstack.getData());
            if (oclass != null && oclass.isAssignableFrom(this.getClass())) {
                final EntityAgeable entityageable = this.createChild(this);
                if (entityageable != null) {
                    entityageable.setAge(-24000);
                    entityageable.setPositionRotation(this.locX, this.locY, this.locZ, 0.0f, 0.0f);
                    this.world.addEntity(entityageable, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
                    if (itemstack.hasName()) {
                        entityageable.setCustomName(itemstack.getName());
                    }
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        final ItemStack itemStack = itemstack;
                        --itemStack.count;
                        if (itemstack.count == 0) {
                            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
                        }
                    }
                }
            }
        }
        return super.a_(entityhuman);
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(12, new Integer(0));
    }
    
    public int getAge() {
        return this.datawatcher.getInt(12);
    }
    
    public void setAge(final int i) {
        this.datawatcher.watch(12, i);
        this.a(this.isBaby());
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Age", this.getAge());
        nbttagcompound.setBoolean("AgeLocked", this.ageLocked);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setAge(nbttagcompound.getInt("Age"));
        this.ageLocked = nbttagcompound.getBoolean("AgeLocked");
    }
    
    public void c() {
        super.c();
        if (this.world.isStatic || this.ageLocked) {
            this.a(this.isBaby());
        }
        else {
            int i = this.getAge();
            if (i < 0) {
                ++i;
                this.setAge(i);
            }
            else if (i > 0) {
                --i;
                this.setAge(i);
            }
        }
    }
    
    public boolean isBaby() {
        return this.getAge() < 0;
    }
    
    public void a(final boolean flag) {
        this.j(flag ? 0.5f : 1.0f);
    }
    
    protected final void a(final float f, final float f1) {
        final boolean flag = this.d > 0.0f;
        this.d = f;
        this.e = f1;
        if (!flag) {
            this.j(1.0f);
        }
    }
    
    private void j(final float f) {
        super.a(this.d * f, this.e * f);
    }
}
