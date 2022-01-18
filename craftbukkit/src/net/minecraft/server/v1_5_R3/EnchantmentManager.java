// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class EnchantmentManager
{
    private static final Random random;
    private static final EnchantmentModifierProtection b;
    private static final EnchantmentModifierDamage c;
    
    public static int getEnchantmentLevel(final int n, final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final NBTTagList enchantments = itemStack.getEnchantments();
        if (enchantments == null) {
            return 0;
        }
        for (int i = 0; i < enchantments.size(); ++i) {
            final short short1 = ((NBTTagCompound)enchantments.get(i)).getShort("id");
            final short short2 = ((NBTTagCompound)enchantments.get(i)).getShort("lvl");
            if (short1 == n) {
                return short2;
            }
        }
        return 0;
    }
    
    public static Map a(final ItemStack itemStack) {
        final LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();
        final NBTTagList list = (itemStack.id == Item.ENCHANTED_BOOK.id) ? Item.ENCHANTED_BOOK.g(itemStack) : itemStack.getEnchantments();
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                linkedHashMap.put((int)((NBTTagCompound)list.get(i)).getShort("id"), (int)((NBTTagCompound)list.get(i)).getShort("lvl"));
            }
        }
        return linkedHashMap;
    }
    
    public static void a(final Map map, final ItemStack itemStack) {
        final NBTTagList nbtbase = new NBTTagList();
        for (final int intValue : map.keySet()) {
            final NBTTagCompound paramNBTBase = new NBTTagCompound();
            paramNBTBase.setShort("id", (short)intValue);
            paramNBTBase.setShort("lvl", (short)(int)map.get(intValue));
            nbtbase.add(paramNBTBase);
            if (itemStack.id == Item.ENCHANTED_BOOK.id) {
                Item.ENCHANTED_BOOK.a(itemStack, new EnchantmentInstance(intValue, (int)map.get(intValue)));
            }
        }
        if (nbtbase.size() > 0) {
            if (itemStack.id != Item.ENCHANTED_BOOK.id) {
                itemStack.a("ench", nbtbase);
            }
        }
        else if (itemStack.hasTag()) {
            itemStack.getTag().remove("ench");
        }
    }
    
    public static int getEnchantmentLevel(final int n, final ItemStack[] array) {
        if (array == null) {
            return 0;
        }
        int n2 = 0;
        for (int length = array.length, i = 0; i < length; ++i) {
            final int enchantmentLevel = getEnchantmentLevel(n, array[i]);
            if (enchantmentLevel > n2) {
                n2 = enchantmentLevel;
            }
        }
        return n2;
    }
    
    private static void a(final EnchantmentModifier enchantmentModifier, final ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        final NBTTagList enchantments = itemStack.getEnchantments();
        if (enchantments == null) {
            return;
        }
        for (int i = 0; i < enchantments.size(); ++i) {
            final short short1 = ((NBTTagCompound)enchantments.get(i)).getShort("id");
            final short short2 = ((NBTTagCompound)enchantments.get(i)).getShort("lvl");
            if (Enchantment.byId[short1] != null) {
                enchantmentModifier.a(Enchantment.byId[short1], short2);
            }
        }
    }
    
    private static void a(final EnchantmentModifier enchantmentModifier, final ItemStack[] array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            a(enchantmentModifier, array[i]);
        }
    }
    
    public static int a(final ItemStack[] array, final DamageSource b) {
        EnchantmentManager.b.a = 0;
        EnchantmentManager.b.b = b;
        a(EnchantmentManager.b, array);
        if (EnchantmentManager.b.a > 25) {
            EnchantmentManager.b.a = 25;
        }
        return (EnchantmentManager.b.a + 1 >> 1) + EnchantmentManager.random.nextInt((EnchantmentManager.b.a >> 1) + 1);
    }
    
    public static int a(final EntityLiving entityLiving, final EntityLiving b) {
        EnchantmentManager.c.a = 0;
        EnchantmentManager.c.b = b;
        a(EnchantmentManager.c, entityLiving.bG());
        if (EnchantmentManager.c.a > 0) {
            return 1 + EnchantmentManager.random.nextInt(EnchantmentManager.c.a);
        }
        return 0;
    }
    
    public static int getKnockbackEnchantmentLevel(final EntityLiving entityLiving, final EntityLiving entityLiving2) {
        return getEnchantmentLevel(Enchantment.KNOCKBACK.id, entityLiving.bG());
    }
    
    public static int getFireAspectEnchantmentLevel(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.FIRE_ASPECT.id, entityLiving.bG());
    }
    
    public static int getOxygenEnchantmentLevel(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.OXYGEN.id, entityLiving.getEquipment());
    }
    
    public static int getDigSpeedEnchantmentLevel(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.DIG_SPEED.id, entityLiving.bG());
    }
    
    public static boolean hasSilkTouchEnchantment(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.SILK_TOUCH.id, entityLiving.bG()) > 0;
    }
    
    public static int getBonusBlockLootEnchantmentLevel(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS.id, entityLiving.bG());
    }
    
    public static int getBonusMonsterLootEnchantmentLevel(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS.id, entityLiving.bG());
    }
    
    public static boolean hasWaterWorkerEnchantment(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.WATER_WORKER.id, entityLiving.getEquipment()) > 0;
    }
    
    public static int getThornsEnchantmentLevel(final EntityLiving entityLiving) {
        return getEnchantmentLevel(Enchantment.THORNS.id, entityLiving.getEquipment());
    }
    
    public static ItemStack a(final Enchantment enchantment, final EntityLiving entityLiving) {
        for (final ItemStack itemStack : entityLiving.getEquipment()) {
            if (itemStack != null && getEnchantmentLevel(enchantment.id, itemStack) > 0) {
                return itemStack;
            }
        }
        return null;
    }
    
    public static int a(final Random random, final int n, int n2, final ItemStack itemStack) {
        if (itemStack.getItem().c() <= 0) {
            return 0;
        }
        if (n2 > 15) {
            n2 = 15;
        }
        final int n3 = random.nextInt(8) + 1 + (n2 >> 1) + random.nextInt(n2 + 1);
        if (n == 0) {
            return Math.max(n3 / 3, 1);
        }
        if (n == 1) {
            return n3 * 2 / 3 + 1;
        }
        return Math.max(n3, n2 * 2);
    }
    
    public static ItemStack a(final Random random, final ItemStack itemStack, final int n) {
        final List b = b(random, itemStack, n);
        final boolean b2 = itemStack.id == Item.BOOK.id;
        if (b2) {
            itemStack.id = Item.ENCHANTED_BOOK.id;
        }
        if (b != null) {
            for (final EnchantmentInstance enchantmentInstance : b) {
                if (b2) {
                    Item.ENCHANTED_BOOK.a(itemStack, enchantmentInstance);
                }
                else {
                    itemStack.addEnchantment(enchantmentInstance.enchantment, enchantmentInstance.level);
                }
            }
        }
        return itemStack;
    }
    
    public static List b(final Random random, final ItemStack itemStack, final int n) {
        final int c = itemStack.getItem().c();
        if (c <= 0) {
            return null;
        }
        final int n2 = c / 2;
        int n3 = (int)((1 + random.nextInt((n2 >> 1) + 1) + random.nextInt((n2 >> 1) + 1) + n) * (1.0f + (random.nextFloat() + random.nextFloat() - 1.0f) * 0.15f) + 0.5f);
        if (n3 < 1) {
            n3 = 1;
        }
        List<EnchantmentInstance> list = null;
        final Map b = b(n3, itemStack);
        if (b != null && !b.isEmpty()) {
            final EnchantmentInstance enchantmentInstance = (EnchantmentInstance)WeightedRandom.a(random, b.values());
            if (enchantmentInstance != null) {
                list = new ArrayList<EnchantmentInstance>();
                list.add(enchantmentInstance);
                for (int n4 = n3; random.nextInt(50) <= n4; n4 >>= 1) {
                    final Iterator<Integer> iterator = b.keySet().iterator();
                    while (iterator.hasNext()) {
                        final Integer n5 = iterator.next();
                        boolean b2 = true;
                        final Iterator<EnchantmentInstance> iterator2 = list.iterator();
                        while (iterator2.hasNext()) {
                            if (!iterator2.next().enchantment.a(Enchantment.byId[n5])) {
                                b2 = false;
                                break;
                            }
                        }
                        if (!b2) {
                            iterator.remove();
                        }
                    }
                    if (!b.isEmpty()) {
                        list.add((EnchantmentInstance)WeightedRandom.a(random, b.values()));
                    }
                }
            }
        }
        return list;
    }
    
    public static Map b(final int n, final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        Map<Integer, EnchantmentInstance> map = null;
        final boolean b = itemStack.id == Item.BOOK.id;
        for (final Enchantment enchantment : Enchantment.byId) {
            if (enchantment != null) {
                if (enchantment.slot.canEnchant(item) || b) {
                    for (int j = enchantment.getStartLevel(); j <= enchantment.getMaxLevel(); ++j) {
                        if (n >= enchantment.a(j) && n <= enchantment.b(j)) {
                            if (map == null) {
                                map = new HashMap<Integer, EnchantmentInstance>();
                            }
                            map.put(enchantment.id, new EnchantmentInstance(enchantment, j));
                        }
                    }
                }
            }
        }
        return map;
    }
    
    static {
        random = new Random();
        b = new EnchantmentModifierProtection(null);
        c = new EnchantmentModifierDamage(null);
    }
}
