// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.entity.Sheep;
import java.util.Random;
import org.bukkit.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.Material;
import java.util.ArrayList;

public class EntitySheep extends EntityAnimal
{
    private final InventoryCrafting e;
    public static final float[][] d;
    private int f;
    private PathfinderGoalEatTile g;
    
    public EntitySheep(final World world) {
        super(world);
        this.e = new InventoryCrafting(new ContainerSheepBreed(this), 2, 1);
        this.g = new PathfinderGoalEatTile(this);
        this.texture = "/mob/sheep.png";
        this.a(0.9f, 1.3f);
        final float f = 0.23f;
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38f));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, f));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25f, Item.WHEAT.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.25f));
        this.goalSelector.a(5, this.g);
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, f));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.e.setItem(0, new ItemStack(Item.INK_SACK, 1, 0));
        this.e.setItem(1, new ItemStack(Item.INK_SACK, 1, 0));
        this.e.resultInventory = new InventoryCraftResult();
    }
    
    protected boolean bh() {
        return true;
    }
    
    protected void bo() {
        this.f = this.g.f();
        super.bo();
    }
    
    public void c() {
        if (this.world.isStatic) {
            this.f = Math.max(0, this.f - 1);
        }
        super.c();
    }
    
    public int getMaxHealth() {
        return 8;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte)0));
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        if (!this.isSheared()) {
            loot.add(new org.bukkit.inventory.ItemStack(Material.WOOL, 1, (short)0, Byte.valueOf((byte)this.getColor())));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    protected int getLootId() {
        return Block.WOOL.id;
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        final ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (itemstack != null && itemstack.id == Item.SHEARS.id && !this.isSheared() && !this.isBaby()) {
            if (!this.world.isStatic) {
                final PlayerShearEntityEvent event = new PlayerShearEntityEvent((Player)entityhuman.getBukkitEntity(), this.getBukkitEntity());
                this.world.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return false;
                }
                this.setSheared(true);
                for (int i = 1 + this.random.nextInt(3), j = 0; j < i; ++j) {
                    final EntityItem a;
                    final EntityItem entityitem = a = this.a(new ItemStack(Block.WOOL.id, 1, this.getColor()), 1.0f);
                    a.motY += this.random.nextFloat() * 0.05f;
                    final EntityItem entityItem = entityitem;
                    entityItem.motX += (this.random.nextFloat() - this.random.nextFloat()) * 0.1f;
                    final EntityItem entityItem2 = entityitem;
                    entityItem2.motZ += (this.random.nextFloat() - this.random.nextFloat()) * 0.1f;
                }
            }
            itemstack.damage(1, entityhuman);
            this.makeSound("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.a_(entityhuman);
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("Sheared", this.isSheared());
        nbttagcompound.setByte("Color", (byte)this.getColor());
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSheared(nbttagcompound.getBoolean("Sheared"));
        this.setColor(nbttagcompound.getByte("Color"));
    }
    
    protected String bb() {
        return "mob.sheep.say";
    }
    
    protected String bc() {
        return "mob.sheep.say";
    }
    
    protected String bd() {
        return "mob.sheep.say";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.sheep.step", 0.15f, 1.0f);
    }
    
    public int getColor() {
        return this.datawatcher.getByte(16) & 0xF;
    }
    
    public void setColor(final int i) {
        final byte b0 = this.datawatcher.getByte(16);
        this.datawatcher.watch(16, (byte)((b0 & 0xF0) | (i & 0xF)));
    }
    
    public boolean isSheared() {
        return (this.datawatcher.getByte(16) & 0x10) != 0x0;
    }
    
    public void setSheared(final boolean flag) {
        final byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, (byte)(b0 | 0x10));
        }
        else {
            this.datawatcher.watch(16, (byte)(b0 & 0xFFFFFFEF));
        }
    }
    
    public static int a(final Random random) {
        final int i = random.nextInt(100);
        return (i < 5) ? 15 : ((i < 10) ? 7 : ((i < 15) ? 8 : ((i < 18) ? 12 : ((random.nextInt(500) == 0) ? 6 : 0))));
    }
    
    public EntitySheep b(final EntityAgeable entityageable) {
        final EntitySheep entitysheep = (EntitySheep)entityageable;
        final EntitySheep entitysheep2 = new EntitySheep(this.world);
        final int i = this.a(this, entitysheep);
        entitysheep2.setColor(15 - i);
        return entitysheep2;
    }
    
    public void aK() {
        final SheepRegrowWoolEvent event = new SheepRegrowWoolEvent((Sheep)this.getBukkitEntity());
        this.world.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            this.setSheared(false);
        }
        if (this.isBaby()) {
            int i = this.getAge() + 1200;
            if (i > 0) {
                i = 0;
            }
            this.setAge(i);
        }
    }
    
    public void bJ() {
        this.setColor(a(this.world.random));
    }
    
    private int a(final EntityAnimal entityanimal, final EntityAnimal entityanimal1) {
        final int i = this.b(entityanimal);
        final int j = this.b(entityanimal1);
        this.e.getItem(0).setData(i);
        this.e.getItem(1).setData(j);
        final ItemStack itemstack = CraftingManager.getInstance().craft(this.e, ((EntitySheep)entityanimal).world);
        int k;
        if (itemstack != null && itemstack.getItem().id == Item.INK_SACK.id) {
            k = itemstack.getData();
        }
        else {
            k = (this.world.random.nextBoolean() ? i : j);
        }
        return k;
    }
    
    private int b(final EntityAnimal entityanimal) {
        return 15 - ((EntitySheep)entityanimal).getColor();
    }
    
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        return this.b(entityageable);
    }
    
    static {
        d = new float[][] { { 1.0f, 1.0f, 1.0f }, { 0.85f, 0.5f, 0.2f }, { 0.7f, 0.3f, 0.85f }, { 0.4f, 0.6f, 0.85f }, { 0.9f, 0.9f, 0.2f }, { 0.5f, 0.8f, 0.1f }, { 0.95f, 0.5f, 0.65f }, { 0.3f, 0.3f, 0.3f }, { 0.6f, 0.6f, 0.6f }, { 0.3f, 0.5f, 0.6f }, { 0.5f, 0.25f, 0.7f }, { 0.2f, 0.3f, 0.7f }, { 0.4f, 0.3f, 0.2f }, { 0.4f, 0.5f, 0.2f }, { 0.6f, 0.2f, 0.2f }, { 0.1f, 0.1f, 0.1f } };
    }
}
