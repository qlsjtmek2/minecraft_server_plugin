// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerHopper extends Container
{
    private final IInventory hopper;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }
        final CraftInventory inventory = new CraftInventory(this.hopper);
        return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    }
    
    public ContainerHopper(final PlayerInventory playerinventory, final IInventory iinventory) {
        this.bukkitEntity = null;
        this.hopper = iinventory;
        this.player = playerinventory;
        iinventory.startOpen();
        final byte b0 = 51;
        for (int i = 0; i < iinventory.getSize(); ++i) {
            this.a(new Slot(iinventory, i, 44 + i * 18, 20));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, 8 + i * 18, 58 + b0));
        }
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || this.hopper.a(entityhuman);
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if (i < this.hopper.getSize()) {
                if (!this.a(itemstack2, this.hopper.getSize(), this.c.size(), true)) {
                    return null;
                }
            }
            else if (!this.a(itemstack2, 0, this.hopper.getSize(), false)) {
                return null;
            }
            if (itemstack2.count == 0) {
                slot.set(null);
            }
            else {
                slot.e();
            }
        }
        return itemstack;
    }
    
    public void b(final EntityHuman entityhuman) {
        super.b(entityhuman);
        this.hopper.g();
    }
}
