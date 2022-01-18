// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerFurnace extends Container
{
    private TileEntityFurnace furnace;
    private int f;
    private int g;
    private int h;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }
        final CraftInventoryFurnace inventory = new CraftInventoryFurnace(this.furnace);
        return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    }
    
    public ContainerFurnace(final PlayerInventory playerinventory, final TileEntityFurnace tileentityfurnace) {
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.bukkitEntity = null;
        this.furnace = tileentityfurnace;
        this.a(new Slot(tileentityfurnace, 0, 56, 17));
        this.a(new Slot(tileentityfurnace, 1, 56, 53));
        this.a(new SlotFurnaceResult(playerinventory.player, tileentityfurnace, 2, 116, 35));
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
    
    public void addSlotListener(final ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.furnace.cookTime);
        icrafting.setContainerData(this, 1, this.furnace.burnTime);
        icrafting.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
    }
    
    public void b() {
        super.b();
        for (int i = 0; i < this.listeners.size(); ++i) {
            final ICrafting icrafting = this.listeners.get(i);
            if (this.f != this.furnace.cookTime) {
                icrafting.setContainerData(this, 0, this.furnace.cookTime);
            }
            if (this.g != this.furnace.burnTime) {
                icrafting.setContainerData(this, 1, this.furnace.burnTime);
            }
            if (this.h != this.furnace.ticksForCurrentFuel) {
                icrafting.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
            }
        }
        this.f = this.furnace.cookTime;
        this.g = this.furnace.burnTime;
        this.h = this.furnace.ticksForCurrentFuel;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || this.furnace.a(entityhuman);
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
            else if (i != 1 && i != 0) {
                if (RecipesFurnace.getInstance().getResult(itemstack2.getItem().id) != null) {
                    if (!this.a(itemstack2, 0, 1, false)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isFuel(itemstack2)) {
                    if (!this.a(itemstack2, 1, 2, false)) {
                        return null;
                    }
                }
                else if (i >= 3 && i < 30) {
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
}
