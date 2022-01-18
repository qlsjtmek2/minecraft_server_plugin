// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerChest extends Container
{
    public IInventory container;
    private int f;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }
        CraftInventory inventory;
        if (this.container instanceof PlayerInventory) {
            inventory = new CraftInventoryPlayer((PlayerInventory)this.container);
        }
        else if (this.container instanceof InventoryLargeChest) {
            inventory = new CraftInventoryDoubleChest((InventoryLargeChest)this.container);
        }
        else {
            inventory = new CraftInventory(this.container);
        }
        return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    }
    
    public ContainerChest(final IInventory iinventory, final IInventory iinventory1) {
        this.bukkitEntity = null;
        this.container = iinventory1;
        this.f = iinventory1.getSize() / 9;
        iinventory1.startOpen();
        final int i = (this.f - 4) * 18;
        this.player = (PlayerInventory)iinventory;
        for (int j = 0; j < this.f; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.a(new Slot(iinventory1, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.a(new Slot(iinventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.a(new Slot(iinventory, j, 8 + j * 18, 161 + i));
        }
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || this.container.a(entityhuman);
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if (i < this.f * 9) {
                if (!this.a(itemstack2, this.f * 9, this.c.size(), true)) {
                    return null;
                }
            }
            else if (!this.a(itemstack2, 0, this.f * 9, false)) {
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
        this.container.g();
    }
    
    public IInventory e() {
        return this.container;
    }
}
