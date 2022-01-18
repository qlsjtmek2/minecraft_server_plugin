// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryView;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryBeacon;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;

public class ContainerBeacon extends Container
{
    private TileEntityBeacon a;
    private final SlotBeacon f;
    private int g;
    private int h;
    private int i;
    private CraftInventoryView bukkitEntity;
    private PlayerInventory player;
    
    public ContainerBeacon(final PlayerInventory playerinventory, final TileEntityBeacon tileentitybeacon) {
        this.bukkitEntity = null;
        this.player = playerinventory;
        this.a = tileentitybeacon;
        this.a(this.f = new SlotBeacon(this, tileentitybeacon, 0, 136, 110));
        final byte b0 = 36;
        final short short1 = 137;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + i * 9 + 9, b0 + j * 18, short1 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, b0 + i * 18, 58 + short1));
        }
        this.g = tileentitybeacon.l();
        this.h = tileentitybeacon.j();
        this.i = tileentitybeacon.k();
    }
    
    public void addSlotListener(final ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.g);
        icrafting.setContainerData(this, 1, this.h);
        icrafting.setContainerData(this, 2, this.i);
    }
    
    public void b() {
        super.b();
    }
    
    public TileEntityBeacon e() {
        return this.a;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.checkReachable || this.a.a(entityhuman);
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.c.get(i);
        if (slot != null && slot.d()) {
            final ItemStack itemstack2 = slot.getItem();
            itemstack = itemstack2.cloneItemStack();
            if (i == 0) {
                if (!this.a(itemstack2, 1, 37, true)) {
                    return null;
                }
                slot.a(itemstack2, itemstack);
            }
            else if (!this.f.d() && this.f.isAllowed(itemstack2) && itemstack2.count == 1) {
                if (!this.a(itemstack2, 0, 1, false)) {
                    return null;
                }
            }
            else if (i >= 1 && i < 28) {
                if (!this.a(itemstack2, 28, 37, false)) {
                    return null;
                }
            }
            else if (i >= 28 && i < 37) {
                if (!this.a(itemstack2, 1, 28, false)) {
                    return null;
                }
            }
            else if (!this.a(itemstack2, 1, 37, false)) {
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
    
    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        }
        final CraftInventory inventory = new CraftInventoryBeacon(this.a);
        return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    }
}
