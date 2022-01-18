// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class EntityChicken extends EntityAnimal
{
    public boolean d;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public int j;
    
    public EntityChicken(final World world) {
        super(world);
        this.d = false;
        this.e = 0.0f;
        this.f = 0.0f;
        this.i = 1.0f;
        this.texture = "/mob/chicken.png";
        this.a(0.3f, 0.7f);
        this.j = this.random.nextInt(6000) + 6000;
        final float f = 0.25f;
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38f));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, f));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25f, Item.SEEDS.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.28f));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, f));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    }
    
    public boolean bh() {
        return true;
    }
    
    public int getMaxHealth() {
        return 4;
    }
    
    public void c() {
        super.c();
        this.h = this.e;
        this.g = this.f;
        this.f += (float)((this.onGround ? -1 : 4) * 0.3);
        if (this.f < 0.0f) {
            this.f = 0.0f;
        }
        if (this.f > 1.0f) {
            this.f = 1.0f;
        }
        if (!this.onGround && this.i < 1.0f) {
            this.i = 1.0f;
        }
        this.i *= 0.9;
        if (!this.onGround && this.motY < 0.0) {
            this.motY *= 0.6;
        }
        this.e += this.i * 2.0f;
        if (!this.isBaby() && !this.world.isStatic && --this.j <= 0) {
            this.makeSound("mob.chicken.plop", 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            this.b(Item.EGG.id, 1);
            this.j = this.random.nextInt(6000) + 6000;
        }
    }
    
    protected void a(final float f) {
    }
    
    protected String bb() {
        return "mob.chicken.say";
    }
    
    protected String bc() {
        return "mob.chicken.hurt";
    }
    
    protected String bd() {
        return "mob.chicken.hurt";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.chicken.step", 0.15f, 1.0f);
    }
    
    protected int getLootId() {
        return Item.FEATHER.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        final int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
        if (j > 0) {
            loot.add(new ItemStack(Item.FEATHER.id, j));
        }
        if (this.isBurning()) {
            loot.add(new ItemStack(Item.COOKED_CHICKEN.id, 1));
        }
        else {
            loot.add(new ItemStack(Item.RAW_CHICKEN.id, 1));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public EntityChicken b(final EntityAgeable entityageable) {
        return new EntityChicken(this.world);
    }
    
    public boolean c(final net.minecraft.server.v1_5_R3.ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() instanceof ItemSeeds;
    }
    
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
