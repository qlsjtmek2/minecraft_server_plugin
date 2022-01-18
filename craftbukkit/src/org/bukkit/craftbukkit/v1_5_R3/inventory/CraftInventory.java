// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import java.util.Iterator;
import org.bukkit.inventory.InventoryHolder;
import net.minecraft.server.v1_5_R3.IHopper;
import net.minecraft.server.v1_5_R3.ContainerAnvilInventory;
import net.minecraft.server.v1_5_R3.TileEntityBeacon;
import net.minecraft.server.v1_5_R3.InventoryMerchant;
import net.minecraft.server.v1_5_R3.InventoryEnderChest;
import net.minecraft.server.v1_5_R3.TileEntityBrewingStand;
import net.minecraft.server.v1_5_R3.ContainerEnchantTableInventory;
import net.minecraft.server.v1_5_R3.TileEntityFurnace;
import net.minecraft.server.v1_5_R3.TileEntityDispenser;
import net.minecraft.server.v1_5_R3.TileEntityDropper;
import net.minecraft.server.v1_5_R3.PlayerInventory;
import net.minecraft.server.v1_5_R3.InventoryCrafting;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.entity.HumanEntity;
import java.util.List;
import java.util.ListIterator;
import java.util.HashMap;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.inventory.Inventory;

public class CraftInventory implements Inventory
{
    protected final IInventory inventory;
    
    public CraftInventory(final IInventory inventory) {
        this.inventory = inventory;
    }
    
    public IInventory getInventory() {
        return this.inventory;
    }
    
    public int getSize() {
        return this.getInventory().getSize();
    }
    
    public String getName() {
        return this.getInventory().getName();
    }
    
    public ItemStack getItem(final int index) {
        final net.minecraft.server.v1_5_R3.ItemStack item = this.getInventory().getItem(index);
        return (item == null) ? null : CraftItemStack.asCraftMirror(item);
    }
    
    public ItemStack[] getContents() {
        final ItemStack[] items = new ItemStack[this.getSize()];
        final net.minecraft.server.v1_5_R3.ItemStack[] mcItems = this.getInventory().getContents();
        for (int size = Math.min(items.length, mcItems.length), i = 0; i < size; ++i) {
            items[i] = ((mcItems[i] == null) ? null : CraftItemStack.asCraftMirror(mcItems[i]));
        }
        return items;
    }
    
    public void setContents(final ItemStack[] items) {
        if (this.getInventory().getContents().length < items.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + this.getInventory().getContents().length + " or less");
        }
        final net.minecraft.server.v1_5_R3.ItemStack[] mcItems = this.getInventory().getContents();
        for (int i = 0; i < mcItems.length; ++i) {
            if (i >= items.length) {
                mcItems[i] = null;
            }
            else {
                mcItems[i] = CraftItemStack.asNMSCopy(items[i]);
            }
        }
    }
    
    public void setItem(final int index, final ItemStack item) {
        this.getInventory().setItem(index, (item == null || item.getTypeId() == 0) ? null : CraftItemStack.asNMSCopy(item));
    }
    
    public boolean contains(final int materialId) {
        for (final ItemStack item : this.getContents()) {
            if (item != null && item.getTypeId() == materialId) {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(final Material material) {
        Validate.notNull(material, "Material cannot be null");
        return this.contains(material.getId());
    }
    
    public boolean contains(final ItemStack item) {
        if (item == null) {
            return false;
        }
        for (final ItemStack i : this.getContents()) {
            if (item.equals(i)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(final int materialId, int amount) {
        if (amount <= 0) {
            return true;
        }
        for (final ItemStack item : this.getContents()) {
            if (item != null && item.getTypeId() == materialId && (amount -= item.getAmount()) <= 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(final Material material, final int amount) {
        Validate.notNull(material, "Material cannot be null");
        return this.contains(material.getId(), amount);
    }
    
    public boolean contains(final ItemStack item, int amount) {
        if (item == null) {
            return false;
        }
        if (amount <= 0) {
            return true;
        }
        for (final ItemStack i : this.getContents()) {
            if (item.equals(i) && --amount <= 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsAtLeast(final ItemStack item, int amount) {
        if (item == null) {
            return false;
        }
        if (amount <= 0) {
            return true;
        }
        for (final ItemStack i : this.getContents()) {
            if (item.isSimilar(i) && (amount -= i.getAmount()) <= 0) {
                return true;
            }
        }
        return false;
    }
    
    public HashMap<Integer, ItemStack> all(final int materialId) {
        final HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
        final ItemStack[] inventory = this.getContents();
        for (int i = 0; i < inventory.length; ++i) {
            final ItemStack item = inventory[i];
            if (item != null && item.getTypeId() == materialId) {
                slots.put(i, item);
            }
        }
        return slots;
    }
    
    public HashMap<Integer, ItemStack> all(final Material material) {
        Validate.notNull(material, "Material cannot be null");
        return this.all(material.getId());
    }
    
    public HashMap<Integer, ItemStack> all(final ItemStack item) {
        final HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
        if (item != null) {
            final ItemStack[] inventory = this.getContents();
            for (int i = 0; i < inventory.length; ++i) {
                if (item.equals(inventory[i])) {
                    slots.put(i, inventory[i]);
                }
            }
        }
        return slots;
    }
    
    public int first(final int materialId) {
        final ItemStack[] inventory = this.getContents();
        for (int i = 0; i < inventory.length; ++i) {
            final ItemStack item = inventory[i];
            if (item != null && item.getTypeId() == materialId) {
                return i;
            }
        }
        return -1;
    }
    
    public int first(final Material material) {
        Validate.notNull(material, "Material cannot be null");
        return this.first(material.getId());
    }
    
    public int first(final ItemStack item) {
        return this.first(item, true);
    }
    
    private int first(final ItemStack item, final boolean withAmount) {
        if (item == null) {
            return -1;
        }
        final ItemStack[] inventory = this.getContents();
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                if (withAmount) {
                    if (!item.equals(inventory[i])) {
                        continue;
                    }
                }
                else if (!item.isSimilar(inventory[i])) {
                    continue;
                }
                return i;
            }
        }
        return -1;
    }
    
    public int firstEmpty() {
        final ItemStack[] inventory = this.getContents();
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public int firstPartial(final int materialId) {
        final ItemStack[] inventory = this.getContents();
        for (int i = 0; i < inventory.length; ++i) {
            final ItemStack item = inventory[i];
            if (item != null && item.getTypeId() == materialId && item.getAmount() < item.getMaxStackSize()) {
                return i;
            }
        }
        return -1;
    }
    
    public int firstPartial(final Material material) {
        Validate.notNull(material, "Material cannot be null");
        return this.firstPartial(material.getId());
    }
    
    private int firstPartial(final ItemStack item) {
        final ItemStack[] inventory = this.getContents();
        final ItemStack filteredItem = CraftItemStack.asCraftCopy(item);
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < inventory.length; ++i) {
            final ItemStack cItem = inventory[i];
            if (cItem != null && cItem.getAmount() < cItem.getMaxStackSize() && cItem.isSimilar(filteredItem)) {
                return i;
            }
        }
        return -1;
    }
    
    public HashMap<Integer, ItemStack> addItem(final ItemStack... items) {
        Validate.noNullElements(items, "Item cannot be null");
        final HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
        for (int i = 0; i < items.length; ++i) {
            final ItemStack item = items[i];
            while (true) {
                final int firstPartial = this.firstPartial(item);
                if (firstPartial == -1) {
                    final int firstFree = this.firstEmpty();
                    if (firstFree == -1) {
                        leftover.put(i, item);
                        break;
                    }
                    if (item.getAmount() <= this.getMaxItemStack()) {
                        this.setItem(firstFree, item);
                        break;
                    }
                    final CraftItemStack stack = CraftItemStack.asCraftCopy(item);
                    stack.setAmount(this.getMaxItemStack());
                    this.setItem(firstFree, stack);
                    item.setAmount(item.getAmount() - this.getMaxItemStack());
                }
                else {
                    final ItemStack partialItem = this.getItem(firstPartial);
                    final int amount = item.getAmount();
                    final int partialAmount = partialItem.getAmount();
                    final int maxAmount = partialItem.getMaxStackSize();
                    if (amount + partialAmount <= maxAmount) {
                        partialItem.setAmount(amount + partialAmount);
                        break;
                    }
                    partialItem.setAmount(maxAmount);
                    item.setAmount(amount + partialAmount - maxAmount);
                }
            }
        }
        return leftover;
    }
    
    public HashMap<Integer, ItemStack> removeItem(final ItemStack... items) {
        Validate.notNull(items, "Items cannot be null");
        final HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
        for (int i = 0; i < items.length; ++i) {
            final ItemStack item = items[i];
            int toDelete = item.getAmount();
            do {
                final int first = this.first(item, false);
                if (first == -1) {
                    item.setAmount(toDelete);
                    leftover.put(i, item);
                    break;
                }
                final ItemStack itemStack = this.getItem(first);
                final int amount = itemStack.getAmount();
                if (amount <= toDelete) {
                    toDelete -= amount;
                    this.clear(first);
                }
                else {
                    itemStack.setAmount(amount - toDelete);
                    this.setItem(first, itemStack);
                    toDelete = 0;
                }
            } while (toDelete > 0);
        }
        return leftover;
    }
    
    private int getMaxItemStack() {
        return this.getInventory().getMaxStackSize();
    }
    
    public void remove(final int materialId) {
        final ItemStack[] items = this.getContents();
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].getTypeId() == materialId) {
                this.clear(i);
            }
        }
    }
    
    public void remove(final Material material) {
        Validate.notNull(material, "Material cannot be null");
        this.remove(material.getId());
    }
    
    public void remove(final ItemStack item) {
        final ItemStack[] items = this.getContents();
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].equals(item)) {
                this.clear(i);
            }
        }
    }
    
    public void clear(final int index) {
        this.setItem(index, null);
    }
    
    public void clear() {
        for (int i = 0; i < this.getSize(); ++i) {
            this.clear(i);
        }
    }
    
    public ListIterator<ItemStack> iterator() {
        return new InventoryIterator(this);
    }
    
    public ListIterator<ItemStack> iterator(int index) {
        if (index < 0) {
            index += this.getSize() + 1;
        }
        return new InventoryIterator(this, index);
    }
    
    public List<HumanEntity> getViewers() {
        return this.inventory.getViewers();
    }
    
    public String getTitle() {
        return this.inventory.getName();
    }
    
    public InventoryType getType() {
        if (this.inventory instanceof InventoryCrafting) {
            return (this.inventory.getSize() >= 9) ? InventoryType.WORKBENCH : InventoryType.CRAFTING;
        }
        if (this.inventory instanceof PlayerInventory) {
            return InventoryType.PLAYER;
        }
        if (this.inventory instanceof TileEntityDropper) {
            return InventoryType.DROPPER;
        }
        if (this.inventory instanceof TileEntityDispenser) {
            return InventoryType.DISPENSER;
        }
        if (this.inventory instanceof TileEntityFurnace) {
            return InventoryType.FURNACE;
        }
        if (this.inventory instanceof ContainerEnchantTableInventory) {
            return InventoryType.ENCHANTING;
        }
        if (this.inventory instanceof TileEntityBrewingStand) {
            return InventoryType.BREWING;
        }
        if (this.inventory instanceof CraftInventoryCustom.MinecraftInventory) {
            return ((CraftInventoryCustom.MinecraftInventory)this.inventory).getType();
        }
        if (this.inventory instanceof InventoryEnderChest) {
            return InventoryType.ENDER_CHEST;
        }
        if (this.inventory instanceof InventoryMerchant) {
            return InventoryType.MERCHANT;
        }
        if (this.inventory instanceof TileEntityBeacon) {
            return InventoryType.BEACON;
        }
        if (this.inventory instanceof ContainerAnvilInventory) {
            return InventoryType.ANVIL;
        }
        if (this.inventory instanceof IHopper) {
            return InventoryType.HOPPER;
        }
        return InventoryType.CHEST;
    }
    
    public InventoryHolder getHolder() {
        return this.inventory.getOwner();
    }
    
    public int getMaxStackSize() {
        return this.inventory.getMaxStackSize();
    }
    
    public void setMaxStackSize(final int size) {
        this.inventory.setMaxStackSize(size);
    }
}
