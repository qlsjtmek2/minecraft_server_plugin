// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem;

import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.inventory.ItemStack;
import com.onikur.changebackitem.util.ConfigUtil;
import com.onikur.changebackitem.util.Msg;
import com.onikur.changebackitem.config.ExTConfig;

public class BlockStorage
{
    private ExTConfig data;
    
    public BlockStorage() {
        (this.data = new ExTConfig(ChangeBackItem.get(), "data.bdata")).saveDefaultConfig();
        this.loadAllData();
    }
    
    public void loadAllData() {
        try {
            Msg.sendConsole("&bLoading data...");
            final ConfigurationSection cs = this.data.getConfig().getConfigurationSection("Blocks");
            for (final String locStr : cs.getKeys(false)) {
                final Location loc = ConfigUtil.formattedStringToLocation(locStr);
                final Block b = loc.getBlock();
                if (b == null) {
                    continue;
                }
                b.setMetadata("ChangeBackItem", (MetadataValue)new FixedMetadataValue((Plugin)ChangeBackItem.get(), (Object)cs.get(locStr)));
            }
        }
        catch (Exception ex) {
            Msg.sendConsole("&cError when load the data! #################");
        }
    }
    
    public void addBlock(final Block b, final ItemStack itemStack) {
        this.data.getConfig().set("Blocks." + ConfigUtil.locationToFormattedString(b.getLocation()), (Object)itemStack);
    }
    
    public void removeBlock(final Block b) {
        this.data.getConfig().set("Blocks." + ConfigUtil.locationToFormattedString(b.getLocation()), (Object)null);
    }
    
    public void saveData() {
        this.data.saveConfig();
    }
}
