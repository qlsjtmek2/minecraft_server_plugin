// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerPlayer extends Container
{
    public InventoryCrafting craftInventory;
    public IInventory resultInventory;
    public boolean g;
    private final EntityHuman h;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public ContainerPlayer(final PlayerInventory playerinventory, final boolean flag, final EntityHuman entityhuman) {
        this.craftInventory = new InventoryCrafting(this, 2, 2);
        this.resultInventory = new InventoryCraftResult();
        this.g = false;
        this.bukkitEntity = null;
        this.g = flag;
        this.h = entityhuman;
        this.resultInventory = new InventoryCraftResult();
        this.craftInventory = new InventoryCrafting(this, 2, 2, playerinventory.player);
        this.craftInventory.resultInventory = this.resultInventory;
        this.player = playerinventory;
        this.a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 144, 36));
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.a(new Slot(this.craftInventory, j + i * 2, 88 + j * 18, 26 + i * 18));
            }
        }
        for (int i = 0; i < 4; ++i) {
            this.a(new SlotArmor(this, playerinventory, playerinventory.getSize() - 1 - i, 8, 8 + i * 18, i));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, 8 + i * 18, 142));
        }
    }
    
    public void a(final IInventory iinventory) {
        CraftingManager.getInstance().lastCraftView = this.getBukkitView();
        final ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory, this.h.world);
        this.resultInventory.setItem(0, craftResult);
        if (super.listeners.size() < 1) {
            return;
        }
        final EntityPlayer player = super.listeners.get(0);
        player.playerConnection.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, 0, craftResult));
    }
    
    public void b(final EntityHuman entityhuman) {
        super.b(entityhuman);
        for (int i = 0; i < 4; ++i) {
            final ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
            if (itemstack != null) {
                entityhuman.drop(itemstack);
            }
        }
        this.resultInventory.setItem(0, null);
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return true;
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if (i == 0) {
                if (!this.a(itemstack2, 9, 45, true)) {
                    return null;
                }
                slot.a(itemstack2, itemstack);
            }
            else if (i >= 1 && i < 5) {
                if (!this.a(itemstack2, 9, 45, false)) {
                    return null;
                }
            }
            else if (i >= 5 && i < 9) {
                if (!this.a(itemstack2, 9, 45, false)) {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor && !this.c.get(5 + ((ItemArmor)itemstack.getItem()).b).d()) {
                final int j = 5 + ((ItemArmor)itemstack.getItem()).b;
                if (!this.a(itemstack2, j, j + 1, false)) {
                    return null;
                }
            }
            else if (i >= 9 && i < 36) {
                if (!this.a(itemstack2, 36, 45, false)) {
                    return null;
                }
            }
            else if (i >= 36 && i < 45) {
                if (!this.a(itemstack2, 9, 36, false)) {
                    return null;
                }
            }
            else if (!this.a(itemstack2, 9, 45, false)) {
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
