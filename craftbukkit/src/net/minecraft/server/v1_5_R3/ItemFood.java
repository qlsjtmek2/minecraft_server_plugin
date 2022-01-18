// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class ItemFood extends Item
{
    public final int a;
    private final int b;
    private final float c;
    private final boolean d;
    private boolean cu;
    private int cv;
    private int cw;
    private int cx;
    private float cy;
    
    public ItemFood(final int i, final int j, final float f, final boolean flag) {
        super(i);
        this.a = 32;
        this.b = j;
        this.d = flag;
        this.c = f;
        this.a(CreativeModeTab.h);
    }
    
    public ItemFood(final int i, final int j, final boolean flag) {
        this(i, j, 0.6f, flag);
    }
    
    public ItemStack b(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        --itemstack.count;
        final int oldFoodLevel = entityhuman.getFoodData().foodLevel;
        final FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, this.getNutrition() + oldFoodLevel);
        if (!event.isCancelled()) {
            entityhuman.getFoodData().eat(event.getFoodLevel() - oldFoodLevel, this.getSaturationModifier());
        }
        ((EntityPlayer)entityhuman).playerConnection.sendPacket(new Packet8UpdateHealth(((EntityPlayer)entityhuman).getScaledHealth(), entityhuman.getFoodData().foodLevel, entityhuman.getFoodData().saturationLevel));
        world.makeSound(entityhuman, "random.burp", 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
        this.c(itemstack, world, entityhuman);
        return itemstack;
    }
    
    protected void c(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        if (!world.isStatic && this.cv > 0 && world.random.nextFloat() < this.cy) {
            entityhuman.addEffect(new MobEffect(this.cv, this.cw * 20, this.cx));
        }
    }
    
    public int c_(final ItemStack itemstack) {
        return 32;
    }
    
    public EnumAnimation b_(final ItemStack itemstack) {
        return EnumAnimation.EAT;
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        if (entityhuman.i(this.cu)) {
            entityhuman.a(itemstack, this.c_(itemstack));
        }
        return itemstack;
    }
    
    public int getNutrition() {
        return this.b;
    }
    
    public float getSaturationModifier() {
        return this.c;
    }
    
    public boolean i() {
        return this.d;
    }
    
    public ItemFood a(final int i, final int j, final int k, final float f) {
        this.cv = i;
        this.cw = j;
        this.cx = k;
        this.cy = f;
        return this;
    }
    
    public ItemFood j() {
        this.cu = true;
        return this;
    }
}
