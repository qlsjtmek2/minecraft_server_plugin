// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import net.minecraft.server.v1_5_R3.Slot;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet100OpenWindow;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import net.minecraft.server.v1_5_R3.EntityHuman;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import net.minecraft.server.v1_5_R3.Container;

public class CraftContainer extends Container
{
    private final InventoryView view;
    private InventoryType cachedType;
    private String cachedTitle;
    private final int cachedSize;
    
    public CraftContainer(final InventoryView view, final int id) {
        this.view = view;
        this.windowId = id;
        final IInventory top = ((CraftInventory)view.getTopInventory()).getInventory();
        final IInventory bottom = ((CraftInventory)view.getBottomInventory()).getInventory();
        this.cachedType = view.getType();
        this.cachedTitle = view.getTitle();
        this.cachedSize = this.getSize();
        this.setupSlots(top, bottom);
    }
    
    public CraftContainer(final Inventory inventory, final HumanEntity player, final int id) {
        this(new InventoryView() {
            public Inventory getTopInventory() {
                return inventory;
            }
            
            public Inventory getBottomInventory() {
                return player.getInventory();
            }
            
            public HumanEntity getPlayer() {
                return player;
            }
            
            public InventoryType getType() {
                return inventory.getType();
            }
        }, id);
    }
    
    public InventoryView getBukkitView() {
        return this.view;
    }
    
    private int getSize() {
        return this.view.getTopInventory().getSize();
    }
    
    public boolean c(final EntityHuman entityhuman) {
        if (this.cachedType == this.view.getType() && this.cachedSize == this.getSize() && this.cachedTitle.equals(this.view.getTitle())) {
            return true;
        }
        final boolean typeChanged = this.cachedType != this.view.getType();
        this.cachedType = this.view.getType();
        this.cachedTitle = this.view.getTitle();
        if (this.view.getPlayer() instanceof CraftPlayer) {
            final CraftPlayer player = (CraftPlayer)this.view.getPlayer();
            final int type = getNotchInventoryType(this.cachedType);
            final IInventory top = ((CraftInventory)this.view.getTopInventory()).getInventory();
            final IInventory bottom = ((CraftInventory)this.view.getBottomInventory()).getInventory();
            this.b.clear();
            this.c.clear();
            if (typeChanged) {
                this.setupSlots(top, bottom);
            }
            final int size = this.getSize();
            player.getHandle().playerConnection.sendPacket(new Packet100OpenWindow(this.windowId, type, this.cachedTitle, size, true));
            player.updateInventory();
        }
        return true;
    }
    
    public static int getNotchInventoryType(final InventoryType type) {
        int typeID = 0;
        switch (type) {
            case WORKBENCH: {
                typeID = 1;
                break;
            }
            case FURNACE: {
                typeID = 2;
                break;
            }
            case DISPENSER: {
                typeID = 3;
                break;
            }
            case ENCHANTING: {
                typeID = 4;
                break;
            }
            case BREWING: {
                typeID = 5;
                break;
            }
            case BEACON: {
                typeID = 7;
                break;
            }
            case ANVIL: {
                typeID = 8;
                break;
            }
            default: {
                typeID = 0;
                break;
            }
        }
        return typeID;
    }
    
    private void setupSlots(final IInventory top, final IInventory bottom) {
        switch (this.cachedType) {
            case PLAYER:
            case CHEST: {
                this.setupChest(top, bottom);
                break;
            }
            case DISPENSER: {
                this.setupDispenser(top, bottom);
                break;
            }
            case FURNACE: {
                this.setupFurnace(top, bottom);
                break;
            }
            case WORKBENCH:
            case CRAFTING: {
                this.setupWorkbench(top, bottom);
                break;
            }
            case ENCHANTING: {
                this.setupEnchanting(top, bottom);
                break;
            }
            case BREWING: {
                this.setupBrewing(top, bottom);
                break;
            }
        }
    }
    
    private void setupChest(final IInventory top, final IInventory bottom) {
        final int rows = top.getSize() / 9;
        final int i = (rows - 4) * 18;
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.a(new Slot(top, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 103 + row * 18 + i));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col, 8 + col * 18, 161 + i));
        }
    }
    
    private void setupWorkbench(final IInventory top, final IInventory bottom) {
        this.a(new Slot(top, 0, 124, 35));
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.a(new Slot(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col, 8 + col * 18, 142));
        }
    }
    
    private void setupFurnace(final IInventory top, final IInventory bottom) {
        this.a(new Slot(top, 0, 56, 17));
        this.a(new Slot(top, 1, 56, 53));
        this.a(new Slot(top, 2, 116, 35));
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col, 8 + col * 18, 142));
        }
    }
    
    private void setupDispenser(final IInventory top, final IInventory bottom) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.a(new Slot(top, col + row * 3, 61 + col * 18, 17 + row * 18));
            }
        }
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.a(new Slot(bottom, col, 8 + col * 18, 142));
        }
    }
    
    private void setupEnchanting(final IInventory top, final IInventory bottom) {
        this.a(new Slot(top, 0, 25, 47));
        for (int row = 0; row < 3; ++row) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(bottom, i1 + row * 9 + 9, 8 + i1 * 18, 84 + row * 18));
            }
        }
        for (int row = 0; row < 9; ++row) {
            this.a(new Slot(bottom, row, 8 + row * 18, 142));
        }
    }
    
    private void setupBrewing(final IInventory top, final IInventory bottom) {
        this.a(new Slot(top, 0, 56, 46));
        this.a(new Slot(top, 1, 79, 53));
        this.a(new Slot(top, 2, 102, 46));
        this.a(new Slot(top, 3, 79, 17));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(bottom, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.a(new Slot(bottom, i, 8 + i * 18, 142));
        }
    }
    
    public boolean a(final EntityHuman entity) {
        return true;
    }
}
