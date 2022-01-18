// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.api;

import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;
import org.bukkit.inventory.ItemStack;

public class RPGItems
{
    public RPGItem toRPGItem(final ItemStack itemstack) {
        return ItemManager.toRPGItem(itemstack);
    }
}
