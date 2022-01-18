// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.InventoryMerchant;
import org.bukkit.inventory.MerchantInventory;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory
{
    public CraftInventoryMerchant(final InventoryMerchant merchant) {
        super(merchant);
    }
}
