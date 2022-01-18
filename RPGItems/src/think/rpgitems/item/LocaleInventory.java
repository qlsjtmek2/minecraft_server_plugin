// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.item;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import think.rpgitems.data.Locale;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class LocaleInventory extends InventoryView
{
    private Player player;
    private InventoryView view;
    private Inventory real;
    private Inventory fake;
    private String locale;
    
    public LocaleInventory(final Player player, final InventoryView inventoryView) {
        this.locale = Locale.getPlayerLocale(player);
        this.player = player;
        this.real = inventoryView.getTopInventory();
        this.view = inventoryView;
        this.fake = Bukkit.createInventory(this.real.getHolder(), this.real.getSize(), this.real.getTitle());
        this.reload();
    }
    
    public InventoryView getView() {
        return this.view;
    }
    
    public void reload() {
        this.fake.setContents(this.real.getContents());
        for (final ItemStack item : this.fake) {
            final RPGItem rItem = ItemManager.toRPGItem(item);
            if (rItem == null) {
                continue;
            }
            item.setType(rItem.getItem());
            final ItemMeta meta = rItem.getLocaleMeta(this.locale);
            if (!(meta instanceof LeatherArmorMeta) && rItem.getItem().isBlock()) {
                item.setDurability(rItem.getDataValue());
            }
            item.setItemMeta(meta);
        }
        this.fake.setContents(this.fake.getContents());
    }
    
    public Inventory getReal() {
        return this.real;
    }
    
    public Inventory getBottomInventory() {
        return (Inventory)this.player.getInventory();
    }
    
    public HumanEntity getPlayer() {
        return (HumanEntity)this.player;
    }
    
    public Inventory getTopInventory() {
        return this.fake;
    }
    
    public InventoryType getType() {
        return InventoryType.CHEST;
    }
    
    public void sumbitChanges() {
        this.real.setContents(this.fake.getContents());
    }
}
