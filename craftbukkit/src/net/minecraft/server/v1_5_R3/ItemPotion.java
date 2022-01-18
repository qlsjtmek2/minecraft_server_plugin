// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ItemPotion extends Item
{
    private HashMap a;
    private static final Map b;
    
    public ItemPotion(final int n) {
        super(n);
        this.a = new HashMap();
        this.d(1);
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.k);
    }
    
    public List g(final ItemStack itemStack) {
        if (!itemStack.hasTag() || !itemStack.getTag().hasKey("CustomPotionEffects")) {
            List effects = this.a.get(itemStack.getData());
            if (effects == null) {
                effects = PotionBrewer.getEffects(itemStack.getData(), false);
                this.a.put(itemStack.getData(), effects);
            }
            return effects;
        }
        final ArrayList<MobEffect> list = new ArrayList<MobEffect>();
        final NBTTagList list2 = itemStack.getTag().getList("CustomPotionEffects");
        for (int i = 0; i < list2.size(); ++i) {
            list.add(MobEffect.b((NBTTagCompound)list2.get(i)));
        }
        return list;
    }
    
    public List c(final int n) {
        List effects = this.a.get(n);
        if (effects == null) {
            effects = PotionBrewer.getEffects(n, false);
            this.a.put(n, effects);
        }
        return effects;
    }
    
    public ItemStack b(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        if (!entityHuman.abilities.canInstantlyBuild) {
            --itemStack.count;
        }
        if (!world.isStatic) {
            final List g = this.g(itemStack);
            if (g != null) {
                final Iterator<MobEffect> iterator = g.iterator();
                while (iterator.hasNext()) {
                    entityHuman.addEffect(new MobEffect(iterator.next()));
                }
            }
        }
        if (!entityHuman.abilities.canInstantlyBuild) {
            if (itemStack.count <= 0) {
                return new ItemStack(Item.GLASS_BOTTLE);
            }
            entityHuman.inventory.pickup(new ItemStack(Item.GLASS_BOTTLE));
        }
        return itemStack;
    }
    
    public int c_(final ItemStack itemStack) {
        return 32;
    }
    
    public EnumAnimation b_(final ItemStack itemStack) {
        return EnumAnimation.DRINK;
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        if (f(itemStack.getData())) {
            if (!entityHuman.abilities.canInstantlyBuild) {
                --itemStack.count;
            }
            world.makeSound(entityHuman, "random.bow", 0.5f, 0.4f / (ItemPotion.e.nextFloat() * 0.4f + 0.8f));
            if (!world.isStatic) {
                world.addEntity(new EntityPotion(world, entityHuman, itemStack));
            }
            return itemStack;
        }
        entityHuman.a(itemStack, this.c_(itemStack));
        return itemStack;
    }
    
    public boolean interactWith(final ItemStack itemStack, final EntityHuman entityHuman, final World world, final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7) {
        return false;
    }
    
    public static boolean f(final int n) {
        return (n & 0x4000) != 0x0;
    }
    
    public String l(final ItemStack itemStack) {
        if (itemStack.getData() == 0) {
            return LocaleI18n.get("item.emptyPotion.name").trim();
        }
        String string = "";
        if (f(itemStack.getData())) {
            string = LocaleI18n.get("potion.prefix.grenade").trim() + " ";
        }
        final List g = Item.POTION.g(itemStack);
        if (g != null && !g.isEmpty()) {
            return string + LocaleI18n.get(g.get(0).f() + ".postfix").trim();
        }
        return LocaleI18n.get(PotionBrewer.c(itemStack.getData())).trim() + " " + super.l(itemStack);
    }
    
    static {
        b = new LinkedHashMap();
    }
}
