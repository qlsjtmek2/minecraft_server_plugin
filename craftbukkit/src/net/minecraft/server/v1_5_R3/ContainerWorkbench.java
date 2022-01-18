// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerWorkbench extends Container
{
    public InventoryCrafting craftInventory;
    public IInventory resultInventory;
    private World g;
    private int h;
    private int i;
    private int j;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public ContainerWorkbench(final PlayerInventory playerinventory, final World world, final int i, final int j, final int k) {
        this.bukkitEntity = null;
        this.resultInventory = new InventoryCraftResult();
        this.craftInventory = new InventoryCrafting(this, 3, 3, playerinventory.player);
        this.craftInventory.resultInventory = this.resultInventory;
        this.player = playerinventory;
        this.g = world;
        this.h = i;
        this.i = j;
        this.j = k;
        this.a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 124, 35));
        for (int l = 0; l < 3; ++l) {
            for (int i2 = 0; i2 < 3; ++i2) {
                this.a(new Slot(this.craftInventory, i2 + l * 3, 30 + i2 * 18, 17 + l * 18));
            }
        }
        for (int l = 0; l < 3; ++l) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.a(new Slot(playerinventory, i2 + l * 9 + 9, 8 + i2 * 18, 84 + l * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.a(new Slot(playerinventory, l, 8 + l * 18, 142));
        }
        this.a(this.craftInventory);
    }
    
    public void a(final IInventory iinventory) {
        CraftingManager.getInstance().lastCraftView = this.getBukkitView();
        final ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory, this.g);
        this.resultInventory.setItem(0, craftResult);
        if (super.listeners.size() < 1) {
            return;
        }
        final EntityPlayer player = super.listeners.get(0);
        player.playerConnection.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, 0, craftResult));
    }
    
    public void b(final EntityHuman entityhuman) {
        super.b(entityhuman);
        if (!this.g.isStatic) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
                if (itemstack != null) {
                    entityhuman.drop(itemstack);
                }
            }
        }
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || (this.g.getTypeId(this.h, this.i, this.j) == Block.WORKBENCH.id && entityhuman.e(this.h + 0.5, this.i + 0.5, this.j + 0.5) <= 64.0);
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if (i == 0) {
                if (!this.a(itemstack2, 10, 46, true)) {
                    return null;
                }
                slot.a(itemstack2, itemstack);
            }
            else if (i >= 10 && i < 37) {
                if (!this.a(itemstack2, 37, 46, false)) {
                    return null;
                }
            }
            else if (i >= 37 && i < 46) {
                if (!this.a(itemstack2, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.a(itemstack2, 10, 46, false)) {
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
    
    public boolean a(final ItemStack itemstack, final Slot slot) {
        return slot.inventory != this.resultInventory && super.a(itemstack, slot);
    }
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }
        final CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
        return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    }
}
