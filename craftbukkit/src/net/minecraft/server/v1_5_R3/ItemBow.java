// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class ItemBow extends Item
{
    public static final String[] a;
    
    public ItemBow(final int i) {
        super(i);
        this.maxStackSize = 1;
        this.setMaxDurability(384);
        this.a(CreativeModeTab.j);
    }
    
    public void a(final ItemStack itemstack, final World world, final EntityHuman entityhuman, final int i) {
        final boolean flag = entityhuman.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, itemstack) > 0;
        if (flag || entityhuman.inventory.e(Item.ARROW.id)) {
            final int j = this.c_(itemstack) - i;
            float f = j / 20.0f;
            f = (f * f + f * 2.0f) / 3.0f;
            if (f < 0.1) {
                return;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
            final EntityArrow entityarrow = new EntityArrow(world, entityhuman, f * 2.0f);
            if (f == 1.0f) {
                entityarrow.a(true);
            }
            final int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack);
            if (k > 0) {
                entityarrow.b(entityarrow.c() + k * 0.5 + 0.5);
            }
            final int l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack);
            if (l > 0) {
                entityarrow.a(l);
            }
            if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack) > 0) {
                entityarrow.setOnFire(100);
            }
            final EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(entityhuman, itemstack, entityarrow, f);
            if (event.isCancelled()) {
                event.getProjectile().remove();
                return;
            }
            if (event.getProjectile() == entityarrow.getBukkitEntity()) {
                world.addEntity(entityarrow);
            }
            itemstack.damage(1, entityhuman);
            world.makeSound(entityhuman, "random.bow", 1.0f, 1.0f / (ItemBow.e.nextFloat() * 0.4f + 1.2f) + f * 0.5f);
            if (flag) {
                entityarrow.fromPlayer = 2;
            }
            else {
                entityhuman.inventory.d(Item.ARROW.id);
            }
        }
    }
    
    public ItemStack b(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        return itemstack;
    }
    
    public int c_(final ItemStack itemstack) {
        return 72000;
    }
    
    public EnumAnimation b_(final ItemStack itemstack) {
        return EnumAnimation.BOW;
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        if (entityhuman.abilities.canInstantlyBuild || entityhuman.inventory.e(Item.ARROW.id)) {
            entityhuman.a(itemstack, this.c_(itemstack));
        }
        return itemstack;
    }
    
    public int c() {
        return 1;
    }
    
    static {
        a = new String[] { "bow_pull_0", "bow_pull_1", "bow_pull_2" };
    }
}
