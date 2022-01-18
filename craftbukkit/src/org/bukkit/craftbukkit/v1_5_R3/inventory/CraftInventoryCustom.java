// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import net.minecraft.server.v1_5_R3.EntityHuman;
import java.util.ArrayList;
import org.bukkit.entity.HumanEntity;
import java.util.List;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class CraftInventoryCustom extends CraftInventory
{
    public CraftInventoryCustom(final InventoryHolder owner, final InventoryType type) {
        super(new MinecraftInventory(owner, type));
    }
    
    public CraftInventoryCustom(final InventoryHolder owner, final int size) {
        super(new MinecraftInventory(owner, size));
    }
    
    public CraftInventoryCustom(final InventoryHolder owner, final int size, final String title) {
        super(new MinecraftInventory(owner, size, title));
    }
    
    static class MinecraftInventory implements IInventory
    {
        private final ItemStack[] items;
        private int maxStack;
        private final List<HumanEntity> viewers;
        private final String title;
        private InventoryType type;
        private final InventoryHolder owner;
        
        public MinecraftInventory(final InventoryHolder owner, final InventoryType type) {
            this(owner, type.getDefaultSize(), type.getDefaultTitle());
            this.type = type;
        }
        
        public MinecraftInventory(final InventoryHolder owner, final int size) {
            this(owner, size, "Chest");
        }
        
        public MinecraftInventory(final InventoryHolder owner, final int size, final String title) {
            this.maxStack = 64;
            this.items = new ItemStack[size];
            this.title = title;
            this.viewers = new ArrayList<HumanEntity>();
            this.owner = owner;
            this.type = InventoryType.CHEST;
        }
        
        public int getSize() {
            return this.items.length;
        }
        
        public ItemStack getItem(final int i) {
            return this.items[i];
        }
        
        public ItemStack splitStack(final int i, final int j) {
            final ItemStack stack = this.getItem(i);
            if (stack == null) {
                return null;
            }
            ItemStack result;
            if (stack.count <= j) {
                this.setItem(i, null);
                result = stack;
            }
            else {
                result = CraftItemStack.copyNMSStack(stack, j);
                final ItemStack itemStack = stack;
                itemStack.count -= j;
            }
            this.update();
            return result;
        }
        
        public ItemStack splitWithoutUpdate(final int i) {
            final ItemStack stack = this.getItem(i);
            if (stack == null) {
                return null;
            }
            ItemStack result;
            if (stack.count <= 1) {
                this.setItem(i, null);
                result = stack;
            }
            else {
                result = CraftItemStack.copyNMSStack(stack, 1);
                final ItemStack itemStack = stack;
                --itemStack.count;
            }
            return result;
        }
        
        public void setItem(final int i, final ItemStack itemstack) {
            this.items[i] = itemstack;
            if (itemstack != null && this.getMaxStackSize() > 0 && itemstack.count > this.getMaxStackSize()) {
                itemstack.count = this.getMaxStackSize();
            }
        }
        
        public String getName() {
            return this.title;
        }
        
        public int getMaxStackSize() {
            return this.maxStack;
        }
        
        public void setMaxStackSize(final int size) {
            this.maxStack = size;
        }
        
        public void update() {
        }
        
        public boolean a(final EntityHuman entityhuman) {
            return true;
        }
        
        public ItemStack[] getContents() {
            return this.items;
        }
        
        public void onOpen(final CraftHumanEntity who) {
            this.viewers.add(who);
        }
        
        public void onClose(final CraftHumanEntity who) {
            this.viewers.remove(who);
        }
        
        public List<HumanEntity> getViewers() {
            return this.viewers;
        }
        
        public InventoryType getType() {
            return this.type;
        }
        
        public void g() {
        }
        
        public InventoryHolder getOwner() {
            return this.owner;
        }
        
        public void startOpen() {
        }
        
        public boolean c() {
            return false;
        }
        
        public boolean b(final int i, final ItemStack itemstack) {
            return true;
        }
    }
}
