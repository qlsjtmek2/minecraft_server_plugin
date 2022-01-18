// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryAnvil;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerAnvil extends Container
{
    private IInventory f;
    private IInventory g;
    private World h;
    private int i;
    private int j;
    private int k;
    public int a;
    private int l;
    private String m;
    private final EntityHuman n;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public ContainerAnvil(final PlayerInventory playerinventory, final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
        this.f = new InventoryCraftResult();
        this.g = new ContainerAnvilInventory(this, "Repair", true, 2);
        this.a = 0;
        this.l = 0;
        this.bukkitEntity = null;
        this.player = playerinventory;
        this.h = world;
        this.i = i;
        this.j = j;
        this.k = k;
        this.n = entityhuman;
        this.a(new Slot(this.g, 0, 27, 47));
        this.a(new Slot(this.g, 1, 76, 47));
        this.a(new SlotAnvilResult(this, this.f, 2, 134, 47, world, i, j, k));
        for (int l = 0; l < 3; ++l) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.a(new Slot(playerinventory, i2 + l * 9 + 9, 8 + i2 * 18, 84 + l * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.a(new Slot(playerinventory, l, 8 + l * 18, 142));
        }
    }
    
    public void a(final IInventory iinventory) {
        super.a(iinventory);
        if (iinventory == this.g) {
            this.e();
        }
    }
    
    public void e() {
        final ItemStack itemstack = this.g.getItem(0);
        this.a = 0;
        int i = 0;
        final byte b0 = 0;
        int j = 0;
        if (itemstack == null) {
            this.f.setItem(0, null);
            this.a = 0;
        }
        else {
            ItemStack itemstack2 = itemstack.cloneItemStack();
            final ItemStack itemstack3 = this.g.getItem(1);
            final Map map = EnchantmentManager.a(itemstack2);
            boolean flag = false;
            int k = b0 + itemstack.getRepairCost() + ((itemstack3 == null) ? 0 : itemstack3.getRepairCost());
            this.l = 0;
            if (itemstack3 != null) {
                flag = (itemstack3.id == Item.ENCHANTED_BOOK.id && Item.ENCHANTED_BOOK.g(itemstack3).size() > 0);
                if (itemstack2.g() && Item.byId[itemstack2.id].a(itemstack, itemstack3)) {
                    int l = Math.min(itemstack2.j(), itemstack2.l() / 4);
                    if (l <= 0) {
                        this.f.setItem(0, null);
                        this.a = 0;
                        return;
                    }
                    int i2;
                    for (i2 = 0; l > 0 && i2 < itemstack3.count; l = Math.min(itemstack2.j(), itemstack2.l() / 4), ++i2) {
                        final int j2 = itemstack2.j() - l;
                        itemstack2.setData(j2);
                        i += Math.max(1, l / 100) + map.size();
                    }
                    this.l = i2;
                }
                else {
                    if (!flag && (itemstack2.id != itemstack3.id || !itemstack2.g())) {
                        this.f.setItem(0, null);
                        this.a = 0;
                        return;
                    }
                    if (itemstack2.g() && !flag) {
                        final int l = itemstack.l() - itemstack.j();
                        final int i2 = itemstack3.l() - itemstack3.j();
                        final int j2 = i2 + itemstack2.l() * 12 / 100;
                        final int i3 = l + j2;
                        int k2 = itemstack2.l() - i3;
                        if (k2 < 0) {
                            k2 = 0;
                        }
                        if (k2 < itemstack2.getData()) {
                            itemstack2.setData(k2);
                            i += Math.max(1, j2 / 100);
                        }
                    }
                    final Map map2 = EnchantmentManager.a(itemstack3);
                    final Iterator iterator = map2.keySet().iterator();
                    while (iterator.hasNext()) {
                        final int j2 = iterator.next();
                        final Enchantment enchantment = Enchantment.byId[j2];
                        final int k2 = map.containsKey(j2) ? map.get(j2) : 0;
                        int l2 = map2.get(j2);
                        int j3;
                        if (k2 == l2) {
                            j3 = ++l2;
                        }
                        else {
                            j3 = Math.max(l2, k2);
                        }
                        l2 = j3;
                        final int k3 = l2 - k2;
                        boolean flag2 = enchantment.canEnchant(itemstack);
                        if (this.n.abilities.canInstantlyBuild || itemstack.id == ItemEnchantedBook.ENCHANTED_BOOK.id) {
                            flag2 = true;
                        }
                        for (final int l3 : map.keySet()) {
                            if (l3 != j2 && !enchantment.a(Enchantment.byId[l3])) {
                                flag2 = false;
                                i += k3;
                            }
                        }
                        if (flag2) {
                            if (l2 > enchantment.getMaxLevel()) {
                                l2 = enchantment.getMaxLevel();
                            }
                            map.put(j2, l2);
                            int i4 = 0;
                            switch (enchantment.getRandomWeight()) {
                                case 1: {
                                    i4 = 8;
                                    break;
                                }
                                case 2: {
                                    i4 = 4;
                                    break;
                                }
                                case 5: {
                                    i4 = 2;
                                    break;
                                }
                                case 10: {
                                    i4 = 1;
                                    break;
                                }
                            }
                            if (flag) {
                                i4 = Math.max(1, i4 / 2);
                            }
                            i += i4 * k3;
                        }
                    }
                }
            }
            if (this.m != null && this.m.length() > 0 && !this.m.equalsIgnoreCase(this.n.getLocale().c(itemstack.a())) && !this.m.equals(itemstack.getName())) {
                j = (itemstack.g() ? 7 : (itemstack.count * 5));
                i += j;
                if (itemstack.hasName()) {
                    k += j / 2;
                }
                itemstack2.c(this.m);
            }
            int l = 0;
            final Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                final int j2 = iterator.next();
                final Enchantment enchantment = Enchantment.byId[j2];
                final int k2 = map.get(j2);
                int l2 = 0;
                ++l;
                switch (enchantment.getRandomWeight()) {
                    case 1: {
                        l2 = 8;
                        break;
                    }
                    case 2: {
                        l2 = 4;
                        break;
                    }
                    case 5: {
                        l2 = 2;
                        break;
                    }
                    case 10: {
                        l2 = 1;
                        break;
                    }
                }
                if (flag) {
                    l2 = Math.max(1, l2 / 2);
                }
                k += l + k2 * l2;
            }
            if (flag) {
                k = Math.max(1, k / 2);
            }
            this.a = k + i;
            if (i <= 0) {
                itemstack2 = null;
            }
            if (j == i && j > 0 && this.a >= 40) {
                this.a = 39;
            }
            if (this.a >= 40 && !this.n.abilities.canInstantlyBuild) {
                itemstack2 = null;
            }
            if (itemstack2 != null) {
                int i2 = itemstack2.getRepairCost();
                if (itemstack3 != null && i2 < itemstack3.getRepairCost()) {
                    i2 = itemstack3.getRepairCost();
                }
                if (itemstack2.hasName()) {
                    i2 -= 9;
                }
                if (i2 < 0) {
                    i2 = 0;
                }
                i2 += 2;
                itemstack2.setRepairCost(i2);
                EnchantmentManager.a(map, itemstack2);
            }
            this.f.setItem(0, itemstack2);
            this.b();
        }
    }
    
    public void addSlotListener(final ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.a);
    }
    
    public void b(final EntityHuman entityhuman) {
        super.b(entityhuman);
        if (!this.h.isStatic) {
            for (int i = 0; i < this.g.getSize(); ++i) {
                final ItemStack itemstack = this.g.splitWithoutUpdate(i);
                if (itemstack != null) {
                    entityhuman.drop(itemstack);
                }
            }
        }
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || (this.h.getTypeId(this.i, this.j, this.k) == Block.ANVIL.id && entityhuman.e(this.i + 0.5, this.j + 0.5, this.k + 0.5) <= 64.0);
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if (i == 2) {
                if (!this.a(itemstack2, 3, 39, true)) {
                    return null;
                }
                slot.a(itemstack2, itemstack);
            }
            else if (i != 0 && i != 1) {
                if (i >= 3 && i < 39 && !this.a(itemstack2, 0, 2, false)) {
                    return null;
                }
            }
            else if (!this.a(itemstack2, 3, 39, false)) {
                return null;
            }
            if (itemstack2.count == 0) {
                slot.set(null);
            }
            else {
                slot.e();
            }
            if (itemstack2.count == itemstack.count) {
                return null;
            }
            slot.a(entityhuman, itemstack2);
        }
        return itemstack;
    }
    
    public void a(final String s) {
        this.m = s;
        if (this.getSlot(2).d()) {
            this.getSlot(2).getItem().c(this.m);
        }
        this.e();
    }
    
    static IInventory a(final ContainerAnvil containeranvil) {
        return containeranvil.g;
    }
    
    static int b(final ContainerAnvil containeranvil) {
        return containeranvil.l;
    }
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }
        final CraftInventory inventory = new CraftInventoryAnvil(this.g, this.f);
        return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    }
}
