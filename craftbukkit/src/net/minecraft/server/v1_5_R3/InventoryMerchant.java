// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class InventoryMerchant implements IInventory
{
    private final IMerchant merchant;
    private ItemStack[] itemsInSlots;
    private final EntityHuman player;
    private MerchantRecipe recipe;
    private int e;
    public List<HumanEntity> transaction;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return this.itemsInSlots;
    }
    
    public void onOpen(final CraftHumanEntity who) {
        this.transaction.add(who);
    }
    
    public void onClose(final CraftHumanEntity who) {
        this.transaction.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
        return this.transaction;
    }
    
    public void setMaxStackSize(final int i) {
        this.maxStack = i;
    }
    
    public InventoryHolder getOwner() {
        return this.player.getBukkitEntity();
    }
    
    public InventoryMerchant(final EntityHuman entityhuman, final IMerchant imerchant) {
        this.itemsInSlots = new ItemStack[3];
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
        this.player = entityhuman;
        this.merchant = imerchant;
    }
    
    public int getSize() {
        return this.itemsInSlots.length;
    }
    
    public ItemStack getItem(final int i) {
        return this.itemsInSlots[i];
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (this.itemsInSlots[i] == null) {
            return null;
        }
        if (i == 2) {
            final ItemStack itemstack = this.itemsInSlots[i];
            this.itemsInSlots[i] = null;
            return itemstack;
        }
        if (this.itemsInSlots[i].count <= j) {
            final ItemStack itemstack = this.itemsInSlots[i];
            this.itemsInSlots[i] = null;
            if (this.d(i)) {
                this.h();
            }
            return itemstack;
        }
        final ItemStack itemstack = this.itemsInSlots[i].a(j);
        if (this.itemsInSlots[i].count == 0) {
            this.itemsInSlots[i] = null;
        }
        if (this.d(i)) {
            this.h();
        }
        return itemstack;
    }
    
    private boolean d(final int i) {
        return i == 0 || i == 1;
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (this.itemsInSlots[i] != null) {
            final ItemStack itemstack = this.itemsInSlots[i];
            this.itemsInSlots[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.itemsInSlots[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }
        if (this.d(i)) {
            this.h();
        }
    }
    
    public String getName() {
        return "mob.villager";
    }
    
    public boolean c() {
        return false;
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.merchant.m_() == entityhuman;
    }
    
    public void startOpen() {
    }
    
    public void g() {
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public void update() {
        this.h();
    }
    
    public void h() {
        this.recipe = null;
        ItemStack itemstack = this.itemsInSlots[0];
        ItemStack itemstack2 = this.itemsInSlots[1];
        if (itemstack == null) {
            itemstack = itemstack2;
            itemstack2 = null;
        }
        if (itemstack == null) {
            this.setItem(2, null);
        }
        else {
            final MerchantRecipeList merchantrecipelist = this.merchant.getOffers(this.player);
            if (merchantrecipelist != null) {
                MerchantRecipe merchantrecipe = merchantrecipelist.a(itemstack, itemstack2, this.e);
                if (merchantrecipe != null && !merchantrecipe.g()) {
                    this.recipe = merchantrecipe;
                    this.setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
                }
                else if (itemstack2 != null) {
                    merchantrecipe = merchantrecipelist.a(itemstack2, itemstack, this.e);
                    if (merchantrecipe != null && !merchantrecipe.g()) {
                        this.recipe = merchantrecipe;
                        this.setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
                    }
                    else {
                        this.setItem(2, null);
                    }
                }
                else {
                    this.setItem(2, null);
                }
            }
        }
    }
    
    public MerchantRecipe getRecipe() {
        return this.recipe;
    }
    
    public void c(final int i) {
        this.e = i;
        this.h();
    }
}
