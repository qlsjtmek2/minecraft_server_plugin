// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.entity.HumanEntity;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet16BlockItemSwitch;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.PlayerInventory;

public class CraftInventoryPlayer extends CraftInventory implements PlayerInventory, EntityEquipment
{
    public CraftInventoryPlayer(final net.minecraft.server.v1_5_R3.PlayerInventory inventory) {
        super(inventory);
    }
    
    public net.minecraft.server.v1_5_R3.PlayerInventory getInventory() {
        return (net.minecraft.server.v1_5_R3.PlayerInventory)this.inventory;
    }
    
    public int getSize() {
        return super.getSize() - 4;
    }
    
    public ItemStack getItemInHand() {
        return CraftItemStack.asCraftMirror(this.getInventory().getItemInHand());
    }
    
    public void setItemInHand(final ItemStack stack) {
        this.setItem(this.getHeldItemSlot(), stack);
    }
    
    public int getHeldItemSlot() {
        return this.getInventory().itemInHandIndex;
    }
    
    public void setHeldItemSlot(final int slot) {
        Validate.isTrue(slot >= 0 && slot < net.minecraft.server.v1_5_R3.PlayerInventory.getHotbarSize(), "Slot is not between 0 and 8 inclusive");
        this.getInventory().itemInHandIndex = slot;
        ((CraftPlayer)this.getHolder()).getHandle().playerConnection.sendPacket(new Packet16BlockItemSwitch(slot));
    }
    
    public ItemStack getHelmet() {
        return this.getItem(this.getSize() + 3);
    }
    
    public ItemStack getChestplate() {
        return this.getItem(this.getSize() + 2);
    }
    
    public ItemStack getLeggings() {
        return this.getItem(this.getSize() + 1);
    }
    
    public ItemStack getBoots() {
        return this.getItem(this.getSize() + 0);
    }
    
    public void setHelmet(final ItemStack helmet) {
        this.setItem(this.getSize() + 3, helmet);
    }
    
    public void setChestplate(final ItemStack chestplate) {
        this.setItem(this.getSize() + 2, chestplate);
    }
    
    public void setLeggings(final ItemStack leggings) {
        this.setItem(this.getSize() + 1, leggings);
    }
    
    public void setBoots(final ItemStack boots) {
        this.setItem(this.getSize() + 0, boots);
    }
    
    public ItemStack[] getArmorContents() {
        final net.minecraft.server.v1_5_R3.ItemStack[] mcItems = this.getInventory().getArmorContents();
        final ItemStack[] ret = new ItemStack[mcItems.length];
        for (int i = 0; i < mcItems.length; ++i) {
            ret[i] = CraftItemStack.asCraftMirror(mcItems[i]);
        }
        return ret;
    }
    
    public void setArmorContents(ItemStack[] items) {
        int cnt = this.getSize();
        if (items == null) {
            items = new ItemStack[4];
        }
        for (final ItemStack item : items) {
            if (item == null || item.getTypeId() == 0) {
                this.clear(cnt++);
            }
            else {
                this.setItem(cnt++, item);
            }
        }
    }
    
    public int clear(final int id, final int data) {
        int count = 0;
        final ItemStack[] items = this.getContents();
        final ItemStack[] armor = this.getArmorContents();
        int armorSlot = this.getSize();
        for (int i = 0; i < items.length; ++i) {
            final ItemStack item = items[i];
            if (item != null) {
                if (id <= -1 || item.getTypeId() == id) {
                    if (data <= -1 || item.getData().getData() == data) {
                        count += item.getAmount();
                        this.setItem(i, null);
                    }
                }
            }
        }
        for (final ItemStack item2 : armor) {
            if (item2 != null) {
                if (id <= -1 || item2.getTypeId() == id) {
                    if (data <= -1 || item2.getData().getData() == data) {
                        count += item2.getAmount();
                        this.setItem(armorSlot++, null);
                    }
                }
            }
        }
        return count;
    }
    
    public HumanEntity getHolder() {
        return (HumanEntity)this.inventory.getOwner();
    }
    
    public float getItemInHandDropChance() {
        return 1.0f;
    }
    
    public void setItemInHandDropChance(final float chance) {
        throw new UnsupportedOperationException();
    }
    
    public float getHelmetDropChance() {
        return 1.0f;
    }
    
    public void setHelmetDropChance(final float chance) {
        throw new UnsupportedOperationException();
    }
    
    public float getChestplateDropChance() {
        return 1.0f;
    }
    
    public void setChestplateDropChance(final float chance) {
        throw new UnsupportedOperationException();
    }
    
    public float getLeggingsDropChance() {
        return 1.0f;
    }
    
    public void setLeggingsDropChance(final float chance) {
        throw new UnsupportedOperationException();
    }
    
    public float getBootsDropChance() {
        return 1.0f;
    }
    
    public void setBootsDropChance(final float chance) {
        throw new UnsupportedOperationException();
    }
}
