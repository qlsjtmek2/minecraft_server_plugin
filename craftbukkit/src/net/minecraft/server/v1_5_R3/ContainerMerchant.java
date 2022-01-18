// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryMerchant;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerMerchant extends Container
{
    private IMerchant merchant;
    private InventoryMerchant f;
    private final World g;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity == null) {
            this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), new CraftInventoryMerchant(this.getMerchantInventory()), this);
        }
        return this.bukkitEntity;
    }
    
    public ContainerMerchant(final PlayerInventory playerinventory, final IMerchant imerchant, final World world) {
        this.bukkitEntity = null;
        this.merchant = imerchant;
        this.g = world;
        this.f = new InventoryMerchant(playerinventory.player, imerchant);
        this.a(new Slot(this.f, 0, 36, 53));
        this.a(new Slot(this.f, 1, 62, 53));
        this.a(new SlotMerchantResult(playerinventory.player, imerchant, this.f, 2, 120, 53));
        this.player = playerinventory;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, 8 + i * 18, 142));
        }
    }
    
    public InventoryMerchant getMerchantInventory() {
        return this.f;
    }
    
    public void addSlotListener(final ICrafting icrafting) {
        super.addSlotListener(icrafting);
    }
    
    public void b() {
        super.b();
    }
    
    public void a(final IInventory iinventory) {
        this.f.h();
        super.a(iinventory);
    }
    
    public void e(final int i) {
        this.f.c(i);
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.merchant.m_() == entityhuman;
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
                if (i >= 3 && i < 30) {
                    if (!this.a(itemstack2, 30, 39, false)) {
                        return null;
                    }
                }
                else if (i >= 30 && i < 39 && !this.a(itemstack2, 3, 30, false)) {
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
    
    public void b(final EntityHuman entityhuman) {
        super.b(entityhuman);
        this.merchant.a((EntityHuman)null);
        super.b(entityhuman);
        if (!this.g.isStatic) {
            ItemStack itemstack = this.f.splitWithoutUpdate(0);
            if (itemstack != null) {
                entityhuman.drop(itemstack);
            }
            itemstack = this.f.splitWithoutUpdate(1);
            if (itemstack != null) {
                entityhuman.drop(itemstack);
            }
        }
    }
}
