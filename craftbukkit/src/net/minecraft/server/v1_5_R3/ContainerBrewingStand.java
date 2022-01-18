// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerBrewingStand extends Container
{
    private TileEntityBrewingStand brewingStand;
    private final Slot f;
    private int g;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public ContainerBrewingStand(final PlayerInventory playerinventory, final TileEntityBrewingStand tileentitybrewingstand) {
        this.g = 0;
        this.bukkitEntity = null;
        this.player = playerinventory;
        this.brewingStand = tileentitybrewingstand;
        this.a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 0, 56, 46));
        this.a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 1, 79, 53));
        this.a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 2, 102, 46));
        this.f = this.a(new SlotBrewing(this, tileentitybrewingstand, 3, 79, 17));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, 8 + i * 18, 142));
        }
    }
    
    public void addSlotListener(final ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.brewingStand.x_());
    }
    
    public void b() {
        super.b();
        for (int i = 0; i < this.listeners.size(); ++i) {
            final ICrafting icrafting = this.listeners.get(i);
            if (this.g != this.brewingStand.x_()) {
                icrafting.setContainerData(this, 0, this.brewingStand.x_());
            }
        }
        this.g = this.brewingStand.x_();
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || this.brewingStand.a(entityhuman);
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if ((i < 0 || i > 2) && i != 3) {
                if (!this.f.d() && this.f.isAllowed(itemstack2)) {
                    if (!this.a(itemstack2, 3, 4, false)) {
                        return null;
                    }
                }
                else if (SlotPotionBottle.a_(itemstack)) {
                    if (!this.a(itemstack2, 0, 3, false)) {
                        return null;
                    }
                }
                else if (i >= 4 && i < 31) {
                    if (!this.a(itemstack2, 31, 40, false)) {
                        return null;
                    }
                }
                else if (i >= 31 && i < 40) {
                    if (!this.a(itemstack2, 4, 31, false)) {
                        return null;
                    }
                }
                else if (!this.a(itemstack2, 4, 40, false)) {
                    return null;
                }
            }
            else {
                if (!this.a(itemstack2, 4, 40, true)) {
                    return null;
                }
                slot.a(itemstack2, itemstack);
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
        final CraftInventoryBrewer inventory = new CraftInventoryBrewer(this.brewingStand);
        return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    }
}
