// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryEnchanting;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.enchantments.Enchantment;
import java.util.HashMap;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryView;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;
import java.util.Random;

public class ContainerEnchantTable extends Container
{
    public ContainerEnchantTableInventory enchantSlots;
    private World world;
    private int x;
    private int y;
    private int z;
    private Random l;
    public long f;
    public int[] costs;
    private CraftInventoryView bukkitEntity;
    private Player player;
    
    public ContainerEnchantTable(final PlayerInventory playerinventory, final World world, final int i, final int j, final int k) {
        this.enchantSlots = new ContainerEnchantTableInventory(this, "Enchant", true, 1);
        this.l = new Random();
        this.costs = new int[3];
        this.bukkitEntity = null;
        this.world = world;
        this.x = i;
        this.y = j;
        this.z = k;
        this.a(new SlotEnchant(this, this.enchantSlots, 0, 25, 47));
        for (int l = 0; l < 3; ++l) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.a(new Slot(playerinventory, i2 + l * 9 + 9, 8 + i2 * 18, 84 + l * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.a(new Slot(playerinventory, l, 8 + l * 18, 142));
        }
        this.player = (Player)playerinventory.player.getBukkitEntity();
        this.enchantSlots.player = this.player;
    }
    
    public void addSlotListener(final ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.costs[0]);
        icrafting.setContainerData(this, 1, this.costs[1]);
        icrafting.setContainerData(this, 2, this.costs[2]);
    }
    
    public void b() {
        super.b();
        for (int i = 0; i < this.listeners.size(); ++i) {
            final ICrafting icrafting = this.listeners.get(i);
            icrafting.setContainerData(this, 0, this.costs[0]);
            icrafting.setContainerData(this, 1, this.costs[1]);
            icrafting.setContainerData(this, 2, this.costs[2]);
        }
    }
    
    public void a(final IInventory iinventory) {
        if (iinventory == this.enchantSlots) {
            final net.minecraft.server.v1_5_R3.ItemStack itemstack = iinventory.getItem(0);
            if (itemstack != null) {
                this.f = this.l.nextLong();
                if (!this.world.isStatic) {
                    int i = 0;
                    for (int j = -1; j <= 1; ++j) {
                        for (int k = -1; k <= 1; ++k) {
                            if ((j != 0 || k != 0) && this.world.isEmpty(this.x + k, this.y, this.z + j) && this.world.isEmpty(this.x + k, this.y + 1, this.z + j)) {
                                if (this.world.getTypeId(this.x + k * 2, this.y, this.z + j * 2) == Block.BOOKSHELF.id) {
                                    ++i;
                                }
                                if (this.world.getTypeId(this.x + k * 2, this.y + 1, this.z + j * 2) == Block.BOOKSHELF.id) {
                                    ++i;
                                }
                                if (k != 0 && j != 0) {
                                    if (this.world.getTypeId(this.x + k * 2, this.y, this.z + j) == Block.BOOKSHELF.id) {
                                        ++i;
                                    }
                                    if (this.world.getTypeId(this.x + k * 2, this.y + 1, this.z + j) == Block.BOOKSHELF.id) {
                                        ++i;
                                    }
                                    if (this.world.getTypeId(this.x + k, this.y, this.z + j * 2) == Block.BOOKSHELF.id) {
                                        ++i;
                                    }
                                    if (this.world.getTypeId(this.x + k, this.y + 1, this.z + j * 2) == Block.BOOKSHELF.id) {
                                        ++i;
                                    }
                                }
                            }
                        }
                    }
                    for (int j = 0; j < 3; ++j) {
                        this.costs[j] = EnchantmentManager.a(this.l, j, i, itemstack);
                    }
                    final CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
                    final PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(this.player, this.getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, this.costs, i);
                    event.setCancelled(!itemstack.w());
                    this.world.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        for (i = 0; i < 3; ++i) {
                            this.costs[i] = 0;
                        }
                        return;
                    }
                    this.b();
                }
            }
            else {
                for (int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                }
            }
        }
    }
    
    public boolean a(final EntityHuman entityhuman, final int i) {
        final net.minecraft.server.v1_5_R3.ItemStack itemstack = this.enchantSlots.getItem(0);
        if (this.costs[i] > 0 && itemstack != null && (entityhuman.expLevel >= this.costs[i] || entityhuman.abilities.canInstantlyBuild)) {
            if (!this.world.isStatic) {
                final List list = EnchantmentManager.b(this.l, itemstack, this.costs[i]);
                final boolean flag = itemstack.id == Item.BOOK.id;
                if (list != null) {
                    final Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
                    for (final Object obj : list) {
                        final EnchantmentInstance instance = (EnchantmentInstance)obj;
                        enchants.put(Enchantment.getById(instance.enchantment.id), instance.level);
                    }
                    final CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
                    final EnchantItemEvent event = new EnchantItemEvent((Player)entityhuman.getBukkitEntity(), this.getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, this.costs[i], enchants, i);
                    this.world.getServer().getPluginManager().callEvent(event);
                    final int level = event.getExpLevelCost();
                    if (event.isCancelled() || (level > entityhuman.expLevel && !entityhuman.abilities.canInstantlyBuild) || enchants.isEmpty()) {
                        return false;
                    }
                    boolean applied = !flag;
                    for (final Map.Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
                        try {
                            if (flag) {
                                final int enchantId = entry.getKey().getId();
                                if (net.minecraft.server.v1_5_R3.Enchantment.byId[enchantId] == null) {
                                    continue;
                                }
                                final EnchantmentInstance enchantment = new EnchantmentInstance(enchantId, entry.getValue());
                                Item.ENCHANTED_BOOK.a(itemstack, enchantment);
                                applied = true;
                                itemstack.id = Item.ENCHANTED_BOOK.id;
                                break;
                            }
                            else {
                                item.addEnchantment(entry.getKey(), entry.getValue());
                            }
                        }
                        catch (IllegalArgumentException ex) {}
                    }
                    if (applied) {
                        entityhuman.levelDown(-level);
                    }
                    this.a(this.enchantSlots);
                }
            }
            return true;
        }
        return false;
    }
    
    public void b(final EntityHuman entityhuman) {
        super.b(entityhuman);
        if (!this.world.isStatic) {
            final net.minecraft.server.v1_5_R3.ItemStack itemstack = this.enchantSlots.splitWithoutUpdate(0);
            if (itemstack != null) {
                entityhuman.drop(itemstack);
            }
        }
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || (this.world.getTypeId(this.x, this.y, this.z) == Block.ENCHANTMENT_TABLE.id && entityhuman.e(this.x + 0.5, this.y + 0.5, this.z + 0.5) <= 64.0);
    }
    
    public net.minecraft.server.v1_5_R3.ItemStack b(final EntityHuman entityhuman, final int i) {
        net.minecraft.server.v1_5_R3.ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final net.minecraft.server.v1_5_R3.ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if (i == 0) {
                if (!this.a(itemstack2, 1, 37, true)) {
                    return null;
                }
            }
            else {
                if (this.c.get(0).d() || !this.c.get(0).isAllowed(itemstack2)) {
                    return null;
                }
                if (itemstack2.hasTag() && itemstack2.count == 1) {
                    this.c.get(0).set(itemstack2.cloneItemStack());
                    itemstack2.count = 0;
                }
                else if (itemstack2.count >= 1) {
                    this.c.get(0).set(new net.minecraft.server.v1_5_R3.ItemStack(itemstack2.id, 1, itemstack2.getData()));
                    final net.minecraft.server.v1_5_R3.ItemStack itemStack = itemstack2;
                    --itemStack.count;
                }
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
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }
        final CraftInventoryEnchanting inventory = new CraftInventoryEnchanting(this.enchantSlots);
        return this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
    }
}
