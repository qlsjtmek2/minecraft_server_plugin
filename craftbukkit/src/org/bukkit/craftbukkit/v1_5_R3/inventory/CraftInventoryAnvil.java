// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.inventory.AnvilInventory;

public class CraftInventoryAnvil extends CraftInventory implements AnvilInventory
{
    private final IInventory resultInventory;
    
    public CraftInventoryAnvil(final IInventory inventory, final IInventory resultInventory) {
        super(inventory);
        this.resultInventory = resultInventory;
    }
    
    public IInventory getResultInventory() {
        return this.resultInventory;
    }
    
    public IInventory getIngredientsInventory() {
        return this.inventory;
    }
    
    public int getSize() {
        return this.getResultInventory().getSize() + this.getIngredientsInventory().getSize();
    }
}
