// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class EntityCow extends EntityAnimal
{
    public EntityCow(final World world) {
        super(world);
        this.texture = "/mob/cow.png";
        this.a(0.9f, 1.3f);
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38f));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 0.2f));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25f, Item.WHEAT.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.25f));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.2f));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    }
    
    public boolean bh() {
        return true;
    }
    
    public int getMaxHealth() {
        return 10;
    }
    
    protected String bb() {
        return "mob.cow.say";
    }
    
    protected String bc() {
        return "mob.cow.hurt";
    }
    
    protected String bd() {
        return "mob.cow.hurt";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.cow.step", 0.15f, 1.0f);
    }
    
    protected float ba() {
        return 0.4f;
    }
    
    protected int getLootId() {
        return Item.LEATHER.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
        if (j > 0) {
            loot.add(new ItemStack(Item.LEATHER.id, j));
        }
        j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
        if (j > 0) {
            loot.add(new ItemStack(this.isBurning() ? Item.COOKED_BEEF.id : Item.RAW_BEEF.id, j));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        final net.minecraft.server.v1_5_R3.ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (itemstack == null || itemstack.id != Item.BUCKET.id) {
            return super.a_(entityhuman);
        }
        final Location loc = this.getBukkitEntity().getLocation();
        final PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Item.MILK_BUCKET);
        if (event.isCancelled()) {
            return false;
        }
        final net.minecraft.server.v1_5_R3.ItemStack itemStack = itemstack;
        if (--itemStack.count <= 0) {
            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, CraftItemStack.asNMSCopy(event.getItemStack()));
        }
        else if (!entityhuman.inventory.pickup(new net.minecraft.server.v1_5_R3.ItemStack(Item.MILK_BUCKET))) {
            entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()));
        }
        return true;
    }
    
    public EntityCow b(final EntityAgeable entityageable) {
        return new EntityCow(this.world);
    }
    
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
