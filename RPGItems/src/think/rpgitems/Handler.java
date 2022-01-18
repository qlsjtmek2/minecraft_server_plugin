// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems;

import java.util.Set;
import java.util.HashSet;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import think.rpgitems.power.Power;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.Material;
import think.rpgitems.item.Quality;
import think.rpgitems.support.WorldGuard;
import think.rpgitems.data.Locale;
import org.bukkit.entity.Player;
import think.rpgitems.commands.CommandGroup;
import think.rpgitems.commands.CommandDocumentation;
import think.rpgitems.commands.CommandString;
import java.util.Iterator;
import think.rpgitems.item.RPGItem;
import think.rpgitems.item.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import think.rpgitems.commands.CommandHandler;

public class Handler implements CommandHandler
{
    @CommandString("rpgitem list")
    @CommandDocumentation("$command.rpgitem.list")
    @CommandGroup("list")
    public void listItems(final CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "RPGItems:");
        for (final RPGItem item : ItemManager.itemByName.values()) {
            sender.sendMessage(ChatColor.GREEN + item.getName() + " - " + item.getDisplay());
        }
    }
    
    @CommandString("rpgitem option worldguard")
    @CommandDocumentation("$command.rpgitem.worldguard")
    @CommandGroup("option_worldguard")
    public void toggleWorldGuard(final CommandSender sender) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (!WorldGuard.isEnabled()) {
            sender.sendMessage(ChatColor.RED + Locale.get("message.worldguard.error", locale));
            return;
        }
        if (WorldGuard.useWorldGuard) {
            sender.sendMessage(ChatColor.AQUA + Locale.get("message.worldguard.disable", locale));
        }
        else {
            sender.sendMessage(ChatColor.AQUA + Locale.get("message.worldguard.enable", locale));
        }
        WorldGuard.useWorldGuard = !WorldGuard.useWorldGuard;
        Plugin.plugin.getConfig().set("support.worldguard", (Object)WorldGuard.useWorldGuard);
        Plugin.plugin.saveConfig();
    }
    
    @CommandString("rpgitem $n[]")
    @CommandDocumentation("$command.rpgitem.print")
    @CommandGroup("item")
    public void printItem(final CommandSender sender, final RPGItem item) {
        item.print(sender);
    }
    
    @CommandString("rpgitem $name:s[] create")
    @CommandDocumentation("$command.rpgitem.create")
    @CommandGroup("item")
    public void createItem(final CommandSender sender, final String itemName) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (ItemManager.newItem(itemName.toLowerCase()) != null) {
            sender.sendMessage(String.format(ChatColor.GREEN + Locale.get("message.create.ok", locale), itemName));
            ItemManager.save(Plugin.plugin);
        }
        else {
            sender.sendMessage(ChatColor.RED + Locale.get("message.create.fail", locale));
        }
    }
    
    @CommandString("rpgitem option giveperms")
    @CommandDocumentation("$command.rpgitem.giveperms")
    @CommandGroup("option_giveperms")
    public void givePerms(final CommandSender sender) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        Plugin.plugin.getConfig().set("give-perms", (Object)!Plugin.plugin.getConfig().getBoolean("give-perms", false));
        if (Plugin.plugin.getConfig().getBoolean("give-perms", false)) {
            sender.sendMessage(ChatColor.AQUA + Locale.get("message.giveperms.true", locale));
        }
        else {
            sender.sendMessage(ChatColor.AQUA + Locale.get("message.giveperms.false", locale));
        }
        Plugin.plugin.saveConfig();
    }
    
    @CommandString(value = "rpgitem $n[] give", handlePermissions = true)
    @CommandDocumentation("$command.rpgitem.give")
    @CommandGroup("item_give")
    public void giveItem(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (sender instanceof Player) {
            if ((!Plugin.plugin.getConfig().getBoolean("give-perms", false) && sender.hasPermission("rpgitem")) || (Plugin.plugin.getConfig().getBoolean("give-perms", false) && sender.hasPermission("rpgitem.give." + item.getName()))) {
                item.give((Player)sender);
                sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.give.ok", locale), item.getDisplay()));
            }
            else {
                sender.sendMessage(ChatColor.RED + Locale.get("message.error.permission", locale));
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + Locale.get("message.give.console", locale));
        }
    }
    
    @CommandString("rpgitem $n[] give $p[]")
    @CommandDocumentation("$command.rpgitem.give.player")
    @CommandGroup("item_give")
    public void giveItemPlayer(final CommandSender sender, final RPGItem item, final Player player) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.give(player);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.give.to", locale), String.valueOf(item.getDisplay()) + ChatColor.AQUA, player.getName()));
        player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.give.ok", locale), item.getDisplay()));
    }
    
    @CommandString("rpgitem $n[] give $p[] $count:i[]")
    @CommandDocumentation("$command.rpgitem.give.player.count")
    @CommandGroup("item_give")
    public void giveItemPlayerCount(final CommandSender sender, final RPGItem item, final Player player, final int count) {
        for (int i = 0; i < count; ++i) {
            item.give(player);
        }
    }
    
    @CommandString("rpgitem $n[] remove")
    @CommandDocumentation("$command.rpgitem.remove")
    @CommandGroup("item_remove")
    public void removeItem(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        ItemManager.remove(item);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.remove.ok", locale), item.getName()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] display")
    @CommandDocumentation("$command.rpgitem.display")
    @CommandGroup("item_display")
    public void getItemDisplay(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.display.get", locale), item.getName(), item.getDisplay()));
    }
    
    @CommandString("rpgitem $n[] display $display:s[]")
    @CommandDocumentation("$command.rpgitem.display.set")
    @CommandGroup("item_display")
    public void setItemDisplay(final CommandSender sender, final RPGItem item, final String display) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setDisplay(display);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.display.set", locale), item.getName(), item.getDisplay()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] quality")
    @CommandDocumentation("$command.rpgitem.quality")
    @CommandGroup("item_quality")
    public void getItemQuality(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.quality.get", locale), item.getName(), item.getQuality().toString().toLowerCase()));
    }
    
    @CommandString("rpgitem $n[] quality $e[think.rpgitems.item.Quality]")
    @CommandDocumentation("$command.rpgitem.quality.set")
    @CommandGroup("item_quality")
    public void setItemQuality(final CommandSender sender, final RPGItem item, final Quality quality) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setQuality(quality);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.quality.set", locale), item.getName(), item.getQuality().toString().toLowerCase()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] damage")
    @CommandDocumentation("$command.rpgitem.damage")
    @CommandGroup("item_damage")
    public void getItemDamage(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.damage.get", locale), item.getName(), item.getDamageMin(), item.getDamageMax()));
    }
    
    @CommandString("rpgitem $n[] damage $damage:i[]")
    @CommandDocumentation("$command.rpgitem.damage.set")
    @CommandGroup("item_damage")
    public void setItemDamage(final CommandSender sender, final RPGItem item, final int damage) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setDamage(damage, damage);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.damage.set", locale), item.getName(), item.getDamageMin()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] damage $min:i[] $max:i[]")
    @CommandDocumentation("$command.rpgitem.damage.set.range")
    @CommandGroup("item_damage")
    public void setItemDamage(final CommandSender sender, final RPGItem item, final int min, final int max) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setDamage(min, max);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.damage.set.range", locale), item.getName(), item.getDamageMin(), item.getDamageMax()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] armour")
    @CommandDocumentation("$command.rpgitem.armour")
    @CommandGroup("item_armour")
    public void getItemArmour(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.armour.get", locale), item.getName(), item.getArmour()));
    }
    
    @CommandString("rpgitem $n[] armour $armour:i[0,100]")
    @CommandDocumentation("$command.rpgitem.armour.set")
    @CommandGroup("item_armour")
    public void setItemArmour(final CommandSender sender, final RPGItem item, final int armour) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setArmour(armour);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.armour.set", locale), item.getName(), item.getArmour()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] type")
    @CommandDocumentation("$command.rpgitem.type")
    @CommandGroup("item_type")
    public void getItemType(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.type.get", locale), item.getName(), item.getType()));
    }
    
    @CommandString("rpgitem $n[] type $type:s[]")
    @CommandDocumentation("$command.rpgitem.type.set")
    @CommandGroup("item_type")
    public void setItemType(final CommandSender sender, final RPGItem item, final String type) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setType(type);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.type.set", locale), item.getName(), item.getType()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] hand")
    @CommandDocumentation("$command.rpgitem.hand")
    @CommandGroup("item_hand")
    public void getItemHand(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.hand.get", locale), item.getName(), item.getHand()));
    }
    
    @CommandString("rpgitem $n[] hand $hand:s[]")
    @CommandDocumentation("$command.rpgitem.hand.set")
    @CommandGroup("item_hand")
    public void setItemHand(final CommandSender sender, final RPGItem item, final String hand) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setHand(hand);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.hand.set", locale), item.getName(), item.getHand()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] lore")
    @CommandDocumentation("$command.rpgitem.lore")
    @CommandGroup("item_lore")
    public void getItemLore(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.lore.get", locale), item.getName(), item.getLore()));
    }
    
    @CommandString("rpgitem $n[] lore $lore:s[]")
    @CommandDocumentation("$command.rpgitem.lore.set")
    @CommandGroup("item_lore")
    public void setItemLore(final CommandSender sender, final RPGItem item, final String lore) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setLore(lore);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.lore.set", locale), item.getName(), item.getLore()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] item")
    @CommandDocumentation("$command.rpgitem.item")
    @CommandGroup("item_item")
    public void getItemItem(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.item.get", locale), item.getName(), item.getItem().toString()));
    }
    
    @CommandString("rpgitem $n[] item $m[]")
    @CommandDocumentation("$command.rpgitem.item.set")
    @CommandGroup("item_item")
    public void setItemItem(final CommandSender sender, final RPGItem item, final Material material) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setItem(material);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.item.set", locale), item.getName(), item.getItem(), item.getDataValue()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] item $m[] $data:i[]")
    @CommandDocumentation("$command.rpgitem.item.set.data")
    @CommandGroup("item_item")
    public void setItemItem(final CommandSender sender, final RPGItem item, final Material material, final int data) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setItem(material, false);
        final ItemMeta meta = item.getLocaleMeta(locale);
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB(data));
        }
        else {
            item.setDataValue((short)data);
        }
        for (final String locales : Locale.getLocales()) {
            item.setLocaleMeta(locales, meta.clone());
        }
        item.rebuild();
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.item.set", locale), item.getName(), item.getItem(), item.getDataValue()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] item $m[] hex $hexcolour:s[]")
    @CommandDocumentation("$command.rpgitem.item.set.data.hex")
    @CommandGroup("item_item")
    public void setItemItem(final CommandSender sender, final RPGItem item, final Material material, final String hexColour) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        int dam;
        try {
            dam = Integer.parseInt(hexColour, 16);
        }
        catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Failed to parse " + hexColour);
            return;
        }
        item.setItem(material, true);
        final ItemMeta meta = item.getLocaleMeta(locale);
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB(dam));
        }
        else {
            item.setDataValue((short)dam);
        }
        for (final String locales : Locale.getLocales()) {
            item.setLocaleMeta(locales, meta.clone());
        }
        item.rebuild();
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.item.set", locale), item.getName(), item.getItem(), item.getDataValue()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] item $itemid:i[]")
    @CommandDocumentation("$command.rpgitem.item.set.id")
    @CommandGroup("item_item")
    public void setItemItem(final CommandSender sender, final RPGItem item, final int id) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final Material mat = Material.getMaterial(id);
        if (mat == null) {
            sender.sendMessage(ChatColor.RED + "Cannot find item");
            return;
        }
        item.setItem(mat);
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.item.set", locale), item.getName(), item.getItem(), item.getDataValue()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] item $itemid:i[] $data:i[]")
    @CommandDocumentation("$command.rpgitem.item.set.id.data")
    @CommandGroup("item_item")
    public void setItemItem(final CommandSender sender, final RPGItem item, final int id, final int data) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final Material mat = Material.getMaterial(id);
        if (mat == null) {
            sender.sendMessage(ChatColor.RED + Locale.get("message.item.cant.find", locale));
            return;
        }
        item.setItem(mat, true);
        final ItemMeta meta = item.toItemStack(locale).getItemMeta();
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB(data));
        }
        else {
            item.setDataValue((short)data);
        }
        for (final String locales : Locale.getLocales()) {
            item.setLocaleMeta(locales, meta);
        }
        item.rebuild();
        sender.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.item.set", locale), item.getName(), item.getItem(), item.getDataValue()));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] removepower $power:s[]")
    @CommandDocumentation("$command.rpgitem.removepower")
    @CommandGroup("item_removepower")
    public void itemRemovePower(final CommandSender sender, final RPGItem item, final String power) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (item.removePower(power)) {
            Power.powerUsage.put(power, Power.powerUsage.get(power) - 1);
            sender.sendMessage(ChatColor.GREEN + String.format(Locale.get("message.power.removed", locale), power));
            ItemManager.save(Plugin.plugin);
        }
        else {
            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.power.unknown", locale), power));
        }
    }
    
    @CommandString("rpgitem $n[] description add $descriptionline:s[]")
    @CommandDocumentation("$command.rpgitem.description.add")
    @CommandGroup("item_description")
    public void itemAddDescription(final CommandSender sender, final RPGItem item, final String line) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.addDescription(ChatColor.WHITE + line);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.description.ok", locale));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] description set $lineno:i[] $descriptionline:s[]")
    @CommandDocumentation("$command.rpgitem.description.set")
    @CommandGroup("item_description")
    public void itemSetDescription(final CommandSender sender, final RPGItem item, final int lineNo, final String line) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (lineNo < 0 || lineNo >= item.description.size()) {
            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.description.out.of.range", locale), line));
            return;
        }
        item.description.set(lineNo, ChatColor.translateAlternateColorCodes('&', ChatColor.WHITE + line));
        item.rebuild();
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.description.change", locale));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] description remove $lineno:i[]")
    @CommandDocumentation("$command.rpgitem.description.remove")
    @CommandGroup("item_description")
    public void itemRemoveDescription(final CommandSender sender, final RPGItem item, final int lineNo) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (lineNo < 0 || lineNo >= item.description.size()) {
            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.description.out.of.range", locale), lineNo));
            return;
        }
        item.description.remove(lineNo);
        item.rebuild();
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.description.remove", locale));
        ItemManager.save(Plugin.plugin);
    }
    
    @CommandString("rpgitem $n[] worldguard")
    @CommandDocumentation("$command.rpgitem.item.worldguard")
    @CommandGroup("item_worldguard")
    public void itemToggleWorldGuard(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (!WorldGuard.isEnabled()) {
            sender.sendMessage(ChatColor.AQUA + Locale.get("message.worldguard.error", locale));
            return;
        }
        item.ignoreWorldGuard = !item.ignoreWorldGuard;
        if (item.ignoreWorldGuard) {
            sender.sendMessage(ChatColor.AQUA + Locale.get("message.worldguard.override.active", locale));
        }
        else {
            sender.sendMessage(ChatColor.AQUA + Locale.get("message.worldguard.override.disabled", locale));
        }
    }
    
    @CommandString("rpgitem $n[] removerecipe")
    @CommandDocumentation("$command.rpgitem.removerecipe")
    @CommandGroup("item_recipe")
    public void itemRemoveRecipe(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.hasRecipe = false;
        item.resetRecipe(true);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.recipe.removed", locale));
    }
    
    @CommandString("rpgitem $n[] recipe")
    @CommandDocumentation("$command.rpgitem.recipe")
    @CommandGroup("item_recipe")
    public void itemSetRecipe(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            String title = "RPGItems - " + item.getDisplay();
            if (title.length() > 32) {
                title = title.substring(0, 32);
            }
            final Inventory recipeInventory = Bukkit.createInventory((InventoryHolder)player, 27, title);
            if (item.hasRecipe) {
                final ItemStack blank = new ItemStack(Material.WALL_SIGN);
                final ItemMeta meta = blank.getItemMeta();
                meta.setDisplayName(ChatColor.RED + Locale.get("message.recipe.1", locale));
                final ArrayList<String> lore = new ArrayList<String>();
                lore.add(ChatColor.WHITE + Locale.get("message.recipe.2", locale));
                lore.add(ChatColor.WHITE + Locale.get("message.recipe.3", locale));
                lore.add(ChatColor.WHITE + Locale.get("message.recipe.4", locale));
                lore.add(ChatColor.WHITE + Locale.get("message.recipe.5", locale));
                meta.setLore((List)lore);
                blank.setItemMeta(meta);
                for (int i = 0; i < 27; ++i) {
                    recipeInventory.setItem(i, blank);
                }
                for (int x = 0; x < 3; ++x) {
                    for (int y = 0; y < 3; ++y) {
                        final int j = x + y * 9;
                        final ItemStack it = item.recipe.get(x + y * 3);
                        if (it != null) {
                            recipeInventory.setItem(j, it);
                        }
                        else {
                            recipeInventory.setItem(j, (ItemStack)null);
                        }
                    }
                }
            }
            player.openInventory(recipeInventory);
            Events.recipeWindows.put(player.getName(), item.getID());
        }
        else {
            sender.sendMessage(ChatColor.RED + Locale.get("message.error.only.player", locale));
        }
    }
    
    @CommandString("rpgitem $n[] drop $e[org.bukkit.entity.EntityType]")
    @CommandDocumentation("$command.rpgitem.drop")
    @CommandGroup("item_drop")
    public void getItemDropChance(final CommandSender sender, final RPGItem item, final EntityType type) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        sender.sendMessage(String.format(ChatColor.AQUA + Locale.get("message.drop.get", locale), String.valueOf(item.getDisplay()) + ChatColor.AQUA, type.toString().toLowerCase(), item.dropChances.get(type.toString())));
    }
    
    @CommandString("rpgitem $n[] drop $e[org.bukkit.entity.EntityType] $chance:f[]")
    @CommandDocumentation("$command.rpgitem.drop.set")
    @CommandGroup("item_drop")
    public void setItemDropChance(final CommandSender sender, final RPGItem item, final EntityType type, double chance) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        chance = Math.min(chance, 100.0);
        final String typeS = type.toString();
        if (chance > 0.0) {
            item.dropChances.put(typeS, chance);
            if (!Events.drops.containsKey(typeS)) {
                Events.drops.put(typeS, new HashSet<Integer>());
            }
            final Set<Integer> set = Events.drops.get(typeS);
            set.add(item.getID());
        }
        else {
            item.dropChances.remove(typeS);
            if (Events.drops.containsKey(typeS)) {
                final Set<Integer> set = Events.drops.get(typeS);
                set.remove(item.getID());
            }
        }
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(String.format(ChatColor.AQUA + Locale.get("message.drop.set", locale), String.valueOf(item.getDisplay()) + ChatColor.AQUA, typeS.toLowerCase(), item.dropChances.get(typeS)));
    }
    
    @CommandString("rpgitem $n[] durability $durability:i[]")
    @CommandDocumentation("$command.rpgitem.durability")
    @CommandGroup("item_durability")
    public void setItemDurability(final CommandSender sender, final RPGItem item, final int newValue) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setMaxDurability(newValue);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(Locale.get("message.durability.change", locale));
    }
    
    @CommandString("rpgitem $n[] durability infinite")
    @CommandDocumentation("$command.rpgitem.durability.infinite")
    @CommandGroup("item_durability")
    public void setItemDurabilityInfinite(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.setMaxDurability(-1);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(Locale.get("message.durability.change", locale));
    }
    
    @CommandString("rpgitem $n[] durability togglebar")
    @CommandDocumentation("$command.rpgitem.durability.togglebar")
    @CommandGroup("item_durability")
    public void toggleItemDurabilityBar(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        item.toggleBar();
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(Locale.get("message.durability.toggle", locale));
    }
}
