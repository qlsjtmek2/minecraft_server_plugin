// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.InventoryLargeChest;
import org.bukkit.inventory.DoubleChestInventory;

public class CraftInventoryDoubleChest extends CraftInventory implements DoubleChestInventory
{
    private final CraftInventory left;
    private final CraftInventory right;
    
    public CraftInventoryDoubleChest(final CraftInventory left, final CraftInventory right) {
        super(new InventoryLargeChest("Large chest", left.getInventory(), right.getInventory()));
        this.left = left;
        this.right = right;
    }
    
    public CraftInventoryDoubleChest(final InventoryLargeChest largeChest) {
        super(largeChest);
        if (largeChest.left instanceof InventoryLargeChest) {
            this.left = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.left);
        }
        else {
            this.left = new CraftInventory(largeChest.left);
        }
        if (largeChest.right instanceof InventoryLargeChest) {
            this.right = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.right);
        }
        else {
            this.right = new CraftInventory(largeChest.right);
        }
    }
    
    public Inventory getLeftSide() {
        return this.left;
    }
    
    public Inventory getRightSide() {
        return this.right;
    }
    
    public void setContents(final ItemStack[] items) {
        if (this.getInventory().getContents().length < items.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + this.getInventory().getContents().length + " or less");
        }
        final ItemStack[] leftItems = new ItemStack[this.left.getSize()];
        final ItemStack[] rightItems = new ItemStack[this.right.getSize()];
        System.arraycopy(items, 0, leftItems, 0, Math.min(this.left.getSize(), items.length));
        this.left.setContents(leftItems);
        if (items.length >= this.left.getSize()) {
            System.arraycopy(items, this.left.getSize(), rightItems, 0, Math.min(this.right.getSize(), items.length - this.left.getSize()));
            this.right.setContents(rightItems);
        }
    }
    
    public DoubleChest getHolder() {
        return new DoubleChest(this);
    }
}
