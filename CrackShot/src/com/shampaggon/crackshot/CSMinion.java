// 
// Decompiled by Procyon v0.5.29
// 

package com.shampaggon.crackshot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.block.Skull;
import org.bukkit.entity.FallingBlock;
import java.util.Random;
import java.util.Arrays;
import org.bukkit.World;
import org.bukkit.Effect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Item;
import org.bukkit.Bukkit;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.enchantments.Enchantment;
import java.io.InputStream;
import java.io.FileOutputStream;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import java.io.File;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import org.bukkit.inventory.Recipe;

public class CSMinion
{
    private final CSDirector plugin;
    public String heading;
    
    public CSMinion(final CSDirector plugin) {
        this.heading = "§7\u2591 §c[-§l¬§cº§lc§7§ls§7] §c- §7";
        this.plugin = plugin;
    }
    
    public void clearRecipes() {
        for (final String parent_node : this.plugin.parents.keySet()) {
            final boolean enabled = this.plugin.getBoolean(String.valueOf(parent_node) + ".Crafting.Enable");
            if (enabled) {
                final ItemStack weapon = this.vendingMachine(parent_node);
                for (final Recipe rec : this.plugin.getServer().getRecipesFor(weapon)) {
                    final Iterator<Recipe> it = (Iterator<Recipe>)this.plugin.getServer().recipeIterator();
                    while (it.hasNext()) {
                        final Recipe r = it.next();
                        if (r.getResult().isSimilar(rec.getResult())) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void customRecipes() {
        for (final String parent_node : this.plugin.parents.keySet()) {
            final boolean enabled = this.plugin.getBoolean(String.valueOf(parent_node) + ".Crafting.Enable");
            if (enabled) {
                final boolean shaped = this.plugin.getBoolean(String.valueOf(parent_node) + ".Crafting.Shaped");
                final String ingredients = this.plugin.getString(String.valueOf(parent_node) + ".Crafting.Ingredients");
                final String[] args = ingredients.split(",");
                final ItemStack weapon = this.vendingMachine(parent_node);
                try {
                    if (shaped) {
                        if (args.length == 9) {
                            final ShapedRecipe recipe = new ShapedRecipe(weapon);
                            recipe.shape(new String[] { "ABC", "DEF", "GHI" });
                            for (int c = 65, i = 0; c < 74; ++c, ++i) {
                                String[] ingrede = args[i].split("~");
                                if (ingrede.length < 2) {
                                    ingrede = new String[] { ingrede[0], "0" };
                                }
                                if (!ingrede[0].equals("0")) {
                                    recipe.setIngredient((char)c, Material.getMaterial((int)Integer.valueOf(ingrede[0])), (int)Integer.valueOf(ingrede[1]));
                                }
                            }
                            this.plugin.getServer().addRecipe((Recipe)recipe);
                        }
                        else {
                            if (args.length == 9) {
                                continue;
                            }
                            System.out.print("[CrackShot] The crafting recipe (" + ingredients + ") of weapon '" + parent_node + "' has " + args.length + " value(s) instead of 9.");
                        }
                    }
                    else {
                        try {
                            final ShapelessRecipe recipe2 = new ShapelessRecipe(weapon);
                            for (int j = 0; j < args.length; ++j) {
                                String[] ingrede2 = args[j].split("~");
                                if (ingrede2.length < 2) {
                                    ingrede2 = new String[] { ingrede2[0], "0" };
                                }
                                if (!ingrede2[0].equalsIgnoreCase("0")) {
                                    recipe2.addIngredient(1, Material.getMaterial((int)Integer.valueOf(ingrede2[0])), (int)Integer.valueOf(ingrede2[1]));
                                }
                            }
                            this.plugin.getServer().addRecipe((Recipe)recipe2);
                        }
                        catch (Exception ex) {
                            System.out.print("[CrackShot] The crafting recipe (" + ingredients + ") of weapon '" + parent_node + "' is incorrect and could not be loaded.");
                        }
                    }
                }
                catch (NumberFormatException ex2) {
                    System.out.print("[CrackShot] The crafting recipe (" + ingredients + ") of weapon '" + parent_node + "' appears to contain one or more invalid characters.");
                }
            }
        }
    }
    
    public ItemStack vendingMachine(final String parent_node) {
        boolean remote = false;
        boolean trap = false;
        String iName = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
        final String iLore = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Item_Lore");
        final boolean dualWield = this.plugin.getBoolean(String.valueOf(parent_node) + ".Shooting.Dual_Wield");
        final boolean reload = this.plugin.getBoolean(String.valueOf(parent_node) + ".Reload.Enable");
        final boolean rdeEnable = this.plugin.getBoolean(String.valueOf(parent_node) + ".Explosive_Devices.Enable");
        final String rdeInfo = this.plugin.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Type");
        final int reloadAmt = this.plugin.getInt(String.valueOf(parent_node) + ".Reload.Reload_Amount");
        final String actType = this.plugin.getString(String.valueOf(parent_node) + ".Firearm_Action.Type");
        final String itemInfo = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
        final String attachType = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
        final String attachInfo = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Info");
        if (itemInfo == null) {
            System.out.print("[CrackShot] The weapon '" + parent_node + "' has no value provided for Item_Type!");
            return null;
        }
        if (rdeInfo != null) {
            if (rdeInfo.toLowerCase().contains("remote")) {
                remote = true;
            }
            else if (rdeInfo.toLowerCase().contains("trap")) {
                trap = true;
            }
        }
        final ItemStack sniperID = this.parseItemStack(itemInfo);
        if (sniperID == null) {
            System.out.print("[CrackShot] The weapon '" + parent_node + "' has an invalid value for Item_Type!");
            return null;
        }
        final ItemMeta snipermeta = sniperID.getItemMeta();
        if (reload && !rdeEnable) {
            if (dualWield) {
                iName = String.valueOf(iName) + " «" + reloadAmt + " | " + reloadAmt + "»";
            }
            else if (attachType != null && attachType.equalsIgnoreCase("main")) {
                final boolean attachRelEnable = this.plugin.getBoolean(String.valueOf(attachInfo) + ".Reload.Enable");
                final int attachRelAmt = this.plugin.getInt(String.valueOf(attachInfo) + ".Reload.Reload_Amount");
                iName = (attachRelEnable ? (String.valueOf(iName) + " «" + reloadAmt + " " + '\u25c0' + '\u25b7' + " " + attachRelAmt + "»") : (String.valueOf(iName) + " «" + reloadAmt + " " + '\u25c0' + '\u25b7' + " " + '\u00d7' + "»"));
            }
            else {
                iName = String.valueOf(iName) + " «" + reloadAmt + "»";
            }
        }
        else if (remote) {
            String rdeCapacity = "N/A";
            final String[] refinedOre = this.returnRefinedOre(null, parent_node);
            if (refinedOre != null) {
                rdeCapacity = refinedOre[0];
            }
            iName = String.valueOf(iName) + " «" + rdeCapacity + "»";
        }
        else if (trap) {
            iName = String.valueOf(iName) + " «?»";
        }
        else if (dualWield) {
            iName = String.valueOf(iName) + " «" + '\u00d7' + " | " + '\u00d7' + "»";
        }
        else if (attachType != null && attachType.equalsIgnoreCase("main")) {
            final boolean attachRelEnable = this.plugin.getBoolean(String.valueOf(attachInfo) + ".Reload.Enable");
            final int attachRelAmt = this.plugin.getInt(String.valueOf(attachInfo) + ".Reload.Reload_Amount");
            iName = (attachRelEnable ? (String.valueOf(iName) + " «" + '\u00d7' + " " + '\u25c0' + '\u25b7' + " " + attachRelAmt + "»") : (String.valueOf(iName) + " «" + '\u00d7' + " " + '\u25c0' + '\u25b7' + " " + '\u00d7' + "»"));
        }
        else {
            iName = String.valueOf(iName) + " «" + '\u00d7' + "»";
        }
        if (actType != null && !dualWield && (actType.equalsIgnoreCase("bolt") || actType.equalsIgnoreCase("lever") || actType.equalsIgnoreCase("pump") || actType.equalsIgnoreCase("break") || actType.equalsIgnoreCase("revolver") || actType.equalsIgnoreCase("slide"))) {
            iName = iName.replaceAll("«", "\u25aa «");
        }
        if (iLore != null) {
            final ArrayList<String> lore = new ArrayList<String>();
            final String[] lines = iLore.split("\\|");
            String[] array;
            for (int length = (array = lines).length, i = 0; i < length; ++i) {
                final String line = array[i];
                lore.add(line);
            }
            snipermeta.setLore((List)lore);
        }
        snipermeta.setDisplayName(iName);
        sniperID.setItemMeta(snipermeta);
        return sniperID;
    }
    
    public String identifyWeapon(final String weapon) {
        for (final String parent_node : this.plugin.parents.keySet()) {
            if (weapon.equalsIgnoreCase(parent_node)) {
                return parent_node;
            }
        }
        for (final String parent_node : this.plugin.parents.keySet()) {
            if (parent_node.toUpperCase().startsWith(weapon.toUpperCase())) {
                return parent_node;
            }
        }
        return null;
    }
    
    public void oneTime(final Player player) {
        if (player.getItemInHand().getAmount() == 1) {
            player.getInventory().clear(player.getInventory().getHeldItemSlot());
        }
        else {
            player.getInventory().getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        this.plugin.unscopePlayer(player);
        player.updateInventory();
    }
    
    public void getWeaponCommand(final Player player, final String weapon, final boolean spawned, final String amount, final boolean given, final boolean byAPI) {
        final String parent_node = this.identifyWeapon(weapon);
        if (parent_node != null) {
            final String attachType = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
            if (attachType == null || !attachType.equalsIgnoreCase("accessory")) {
                this.getWeaponHelper(player, parent_node, spawned, amount, given, byAPI);
                return;
            }
        }
        player.sendMessage(String.valueOf(this.heading) + "No weapon matches '" + weapon + "'.");
    }
    
    public void getWeaponHelper(final Player player, final String parent_node, final boolean spawned, final String amount, final boolean given, final boolean byAPI) {
        if (spawned && !player.hasPermission("crackshot.get." + parent_node) && !player.hasPermission("crackshot.get.all")) {
            player.sendMessage(String.valueOf(this.heading) + "You do not have permission to get this item.");
            return;
        }
        final ItemStack sniperID = this.vendingMachine(parent_node);
        if (sniperID == null) {
            player.sendMessage(String.valueOf(this.heading) + "You have failed to provide a value for 'Item_Type'.");
            return;
        }
        int intAmount = 1;
        if (amount != null) {
            try {
                intAmount = Integer.valueOf(amount);
            }
            catch (NumberFormatException ex) {}
        }
        if (intAmount > 64) {
            intAmount = 64;
        }
        if (intAmount < 1) {
            player.sendMessage(String.valueOf(this.heading) + "'" + intAmount + "' is not a valid amount.");
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(String.valueOf(this.heading) + "Your inventory is full.");
            return;
        }
        String multiplier = "";
        if (intAmount > 1) {
            multiplier = " \u2715" + intAmount;
        }
        for (int count = 0; count < intAmount; ++count) {
            player.getInventory().addItem(new ItemStack[] { sniperID });
        }
        this.plugin.noShootPeriod(player, parent_node, 3);
        String publicName = parent_node;
        if (publicName.length() > 19) {
            publicName = String.valueOf(publicName.substring(0, 19)) + "...";
        }
        if (spawned) {
        }
        else if (given && !byAPI) {
            String itemName = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
            itemName = new StringBuffer(itemName).insert(2, "§r§7").toString();
        }
        if (!byAPI) {
            this.plugin.playSoundEffects((Entity)player, parent_node, ".Item_Information.Sounds_Acquired", false);
        }
    }
    
    public Vector getAlignedDirection(final Location locA, final Location locB) {
        final Vector vector = locB.toVector().subtract(locA.toVector()).normalize();
        return vector;
    }
    
    public void loadGeneralConfig() {
        final String path = this.plugin.getDataFolder() + "/general.yml";
        final File tag = new File(path);
        if (!tag.exists()) {
            final File dFile = this.grabGeneralConfig();
            if (dFile != null) {
                try {
                    dFile.createNewFile();
                }
                catch (IOException ex2) {}
            }
            System.out.print("[CrackShot] General configuration added!");
        }
        if (tag != null) {
            try {
                this.plugin.weaponConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(tag);
                if (this.plugin.weaponConfig.getList("Disabled_Worlds") != null) {
                    this.plugin.disWorlds = this.plugin.weaponConfig.getList("Disabled_Worlds").toArray(new String[0]);
                }
            }
            catch (Exception ex) {
                System.out.print("[CrackShot] " + tag.getName() + " could not be loaded.");
            }
        }
    }
    
    public File grabGeneralConfig() {
        final File file = new File(this.plugin.getDataFolder() + "/general.yml");
        final InputStream inputStream = CSDirector.class.getResourceAsStream("/general.yml");
        if (inputStream == null) {
            return null;
        }
        try {
            final FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[4096];
            int read = 0;
            while ((read = inputStream.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
            inputStream.close();
            output.close();
            return file;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void loadWeapons() {
        final String path = this.plugin.getDataFolder() + "/weapons";
        final File tag = new File(path);
        File[] fileList = tag.listFiles();
        if (fileList == null || fileList.length == 0) {
            final String[] specials = { "defaultWeapons.yml", "defaultExplosives.yml", "defaultAttachments.yml" };
            String[] array;
            for (int length = (array = specials).length, i = 0; i < length; ++i) {
                final String spec = array[i];
                final File dFile = this.grabDefaults(spec);
                if (dFile != null) {
                    try {
                        dFile.createNewFile();
                    }
                    catch (IOException ex2) {}
                }
            }
            fileList = tag.listFiles();
            System.out.print("[CrackShot] Default weapons added!");
        }
        if (fileList == null) {
            System.out.print("[CrackShot] No weapons were loaded!");
            return;
        }
        File[] array2;
        for (int length2 = (array2 = fileList).length, j = 0; j < length2; ++j) {
            final File file = array2[j];
            if (file.getName().endsWith(".yml")) {
                try {
                    this.plugin.weaponConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
                    this.plugin.fillHashMaps(this.plugin.weaponConfig);
                }
                catch (Exception ex) {
                    System.out.print("[CrackShot] " + file.getName() + " could not be loaded.");
                }
            }
        }
        this.completeList();
    }
    
    public void completeList() {
        int counter = 1;
        for (final String parent_node : this.plugin.parents.keySet()) {
            final String attachType = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
            if (!this.plugin.getBoolean(String.valueOf(parent_node) + ".Item_Information.Hidden_From_List") && (attachType == null || !attachType.equalsIgnoreCase("accessory"))) {
                this.plugin.wlist.put(counter, parent_node);
                ++counter;
            }
        }
        this.plugin.ints.put("totalPages", (int)Math.ceil((counter - 1) / 18.0));
    }
    
    public void listWeapons(final Player sender, final String[] args) {
        try {
            int start = 1;
            int page = 1;
            int finalChapter = this.plugin.getInt("totalPages");
            if (finalChapter == 0) {
                finalChapter = 1;
            }
            if (args.length == 2 && !args[1].equalsIgnoreCase("all") && Integer.valueOf(args[1]) instanceof Integer) {
                final int pageNumber = Integer.valueOf(args[1]);
                if (pageNumber < 1) {
                    return;
                }
                start = 1 + (pageNumber - 1) * 18;
                page = pageNumber;
                if (start >= finalChapter * 18) {
                    start = 1 + (finalChapter - 1) * 18;
                }
                if (page < 1) {
                    page = 1;
                }
                else if (page > finalChapter) {
                    page = finalChapter;
                }
            }
            int finish = start + 18;
            if (args.length == 2 && args[1].equalsIgnoreCase("all")) {
                finish = finalChapter * 18;
                sender.sendMessage("§7\u2591 §cWeapons [All pages]:");
            }
            else {
                sender.sendMessage("§7\u2591 §cWeapons [Page " + page + "/" + finalChapter + "]:");
            }
            for (int i = start; i < finish; i += 2) {
                final String weapon = this.plugin.wlist.get(i);
                if (weapon == null) {
                    break;
                }
                final String weapon2 = this.plugin.wlist.get(i + 1);
                sender.sendMessage(this.makePretty(weapon, weapon2));
            }
        }
        catch (NumberFormatException ex) {
            sender.sendMessage(String.valueOf(this.heading) + "You have provided an invalid page number.");
        }
    }
    
    public String makePretty(String weapon, String weapon2) {
        weapon = weapon.toUpperCase();
        if (weapon.length() > 18) {
            weapon = String.valueOf(weapon.substring(0, 18)) + "...";
        }
        String tripleDot = weapon.replace("...", "O");
        tripleDot = tripleDot.replace("I", "");
        final int officialLength = weapon.replace("...", "O").length();
        final int count = officialLength - tripleDot.length();
        String padding = " \u254e\u254e";
        if (officialLength % 2 == 0) {
            padding = "\u254e\u254e\u254e\u254e";
        }
        final int spaceLimit = 34 - (officialLength + 1) / 2;
        if (count != 0 && count % 2 != 0) {
            if (officialLength % 2 != 0) {
                padding = " \u254e\u254e\u254e\u254e";
            }
            else {
                padding = " \u254e\u254e";
            }
        }
        for (int a = officialLength + 1; a < spaceLimit + count / 2; ++a) {
            padding = " " + padding;
        }
        if (weapon2 != null) {
            if (weapon2.length() > 18) {
                weapon2 = String.valueOf(weapon2.substring(0, 18)) + "...";
            }
            weapon = "§7\u2591 §c - §7" + weapon + padding + "§7\u2591 §c - §7" + weapon2.toUpperCase();
        }
        else {
            weapon = "§7\u2591 §c - §7" + weapon + padding + "§7\u2591";
        }
        return weapon;
    }
    
    public File grabDefaults(final String defaultWeap) {
        final File file = new File(this.plugin.getDataFolder() + "/weapons/" + defaultWeap);
        final File directories = new File(this.plugin.getDataFolder() + "/weapons");
        if (!directories.exists()) {
            directories.mkdirs();
        }
        final InputStream inputStream = CSDirector.class.getResourceAsStream("/resources/" + defaultWeap);
        if (inputStream == null) {
            return null;
        }
        try {
            final FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[4096];
            int read = 0;
            while ((read = inputStream.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
            inputStream.close();
            output.close();
            return file;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void removeEnchantments(final ItemStack item) {
        for (final Enchantment e : item.getEnchantments().keySet()) {
            item.removeEnchantment(e);
        }
    }
    
    public boolean holdingEnchantedItem(final Player player, final String enchantment, final ItemStack provided) {
        if (enchantment == null) {
            return false;
        }
        final String[] args = enchantment.split("-");
        if (args.length == 2) {
            try {
                Enchantment.getByName(args[0].toUpperCase());
            }
            catch (IllegalArgumentException ex) {
                System.out.print("[CrackShot] '" + args[0] + "' is not a valid enchantment type!");
                return false;
            }
            try {
                Integer.valueOf(args[1]);
            }
            catch (NumberFormatException ex2) {
                System.out.print("[CrackShot] '" + args[1] + "' is not a valid enchantment level!");
                return false;
            }
            ItemStack item = provided;
            if (player != null) {
                item = player.getInventory().getItemInHand();
            }
            final Enchantment e = Enchantment.getByName(args[0].toUpperCase());
            if (item != null && item.getEnchantments() != null && item.getEnchantments().containsKey(e) && item.getEnchantmentLevel(e) == Integer.valueOf(args[1])) {
                return true;
            }
        }
        return false;
    }
    
    public String[] centrifugeAmmo(final String ammoReading) {
        final Pattern ammoBrackets = Pattern.compile("\\«([^»]*)\\»");
        final Matcher bracketChecker = ammoBrackets.matcher(ammoReading);
        if (bracketChecker.find()) {
            try {
                final String[] ammoSerum = bracketChecker.group(1).split(" ");
                return ammoSerum;
            }
            catch (Exception ex) {
                return null;
            }
        }
        return null;
    }
    
    public void replaceBrackets(final ItemStack item, String gapFiller, final String parent_node) {
        final String attachType = this.plugin.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
        if (attachType != null) {
            final String[] ammoReading = this.centrifugeAmmo(item.getItemMeta().getDisplayName());
            if (attachType.equalsIgnoreCase("main")) {
                gapFiller = String.valueOf(gapFiller) + " " + ammoReading[1] + " " + ammoReading[2];
            }
            else if (attachType.equalsIgnoreCase("accessory")) {
                gapFiller = String.valueOf(ammoReading[0]) + " " + ammoReading[1] + " " + gapFiller;
            }
        }
        final String refinedOre = item.getItemMeta().getDisplayName().replaceAll("(?<=«).*?(?=»)", gapFiller);
        this.setItemName(item, refinedOre);
    }
    
    public void setItemName(final ItemStack is, final String name) {
        final ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
    }
    
    public boolean getHeadshot(final Projectile projectile, final Entity e) {
        if (projectile == null) {
            return false;
        }
        if (e == null) {
            return false;
        }
        boolean HESH = false;
        if (e instanceof LivingEntity) {
            final double projY = projectile.getLocation().getY();
            final double eyeballs = ((LivingEntity)e).getEyeLocation().getY();
            final double totaldistance = Math.abs(projY - eyeballs);
            HESH = (totaldistance <= 0.5);
            return HESH;
        }
        return false;
    }
    
    public boolean hasAnItemNamed(final Player p, final String name, final ItemStack provided) {
        if (name == null) {
            return false;
        }
        ItemStack item = provided;
        if (p != null) {
            item = p.getItemInHand();
        }
        return item != null && this.plugin.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(name);
    }
    
    public boolean durabilityCheck(final String item) {
        final String[] list = { "346", "398", "359" };
        for (int i = 256; i <= 259; ++i) {
            if (item.contains(String.valueOf(i))) {
                return true;
            }
        }
        for (int i = 267; i <= 279; ++i) {
            if (item.contains(String.valueOf(i))) {
                return true;
            }
        }
        for (int i = 283; i <= 286; ++i) {
            if (item.contains(String.valueOf(i))) {
                return true;
            }
        }
        for (int i = 290; i <= 294; ++i) {
            if (item.contains(String.valueOf(i))) {
                return true;
            }
        }
        for (int i = 298; i <= 317; ++i) {
            if (item.contains(String.valueOf(i))) {
                return true;
            }
        }
        String[] array;
        for (int length = (array = list).length, j = 0; j < length; ++j) {
            final String it = array[j];
            if (item.contains(it)) {
                return true;
            }
        }
        return false;
    }
    
    public void projectileLightning(final Projectile obj_projectile, final boolean lightning_no_damage) {
        if (lightning_no_damage) {
            obj_projectile.getWorld().strikeLightningEffect(obj_projectile.getLocation());
        }
        else {
            obj_projectile.getWorld().strikeLightning(obj_projectile.getLocation());
        }
    }
    
    public void explosionPackage(final LivingEntity victim, final String parent_node, final Player player) {
        if (parent_node != null) {
            String vicName = victim.getType().getName();
            this.givePotionEffects(victim, parent_node, ".Explosions.Explosion_Potion_Effect", "explosion");
            final int inc = this.plugin.getInt(String.valueOf(parent_node) + ".Explosions.Ignite_Victims");
            if (inc != 0) {
                victim.setFireTicks(inc);
            }
            this.plugin.playSoundEffects((Entity)victim, parent_node, ".Explosions.Sounds_Victim", false);
            if (victim == player) {
                return;
            }
            if (victim instanceof Player) {
                vicName = ((Player)victim).getName();
                this.plugin.sendPlayerMessage(victim, parent_node, ".Explosions.Message_Victim", true, "<shooter>", vicName, "<flight>", "<damage>");
            }
            if (player != null) {
                this.plugin.sendPlayerMessage((LivingEntity)player, parent_node, ".Explosions.Message_Shooter", true, player.getName(), vicName, "<flight>", "<damage>");
                this.plugin.playSoundEffects((Entity)player, parent_node, ".Explosions.Sounds_Shooter", false);
            }
        }
    }
    
    public void callAndResponse(final Player victim, final Player fisherman, final Vehicle vehicle, final String[] seagull_info, final boolean shot) {
        if (victim.hasMetadata("CS_trigDelay")) {
            return;
        }
        if (fisherman == null) {
            if (vehicle == null) {
                this.detonateRDE(fisherman, victim, seagull_info, false);
            }
            else {
                this.mineAction(vehicle, seagull_info, fisherman, shot, null, (Entity)victim);
            }
            return;
        }
        victim.setMetadata("CS_trigDelay", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)false));
        this.tempVars(victim, "CS_trigDelay", 200L);
        victim.setMetadata("CS_singed", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)false));
        this.illegalSlap(fisherman, victim);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (victim.hasMetadata("CS_singed")) {
                    final boolean confirmed = victim.getMetadata("CS_singed").get(0).asBoolean();
                    if (confirmed) {
                        victim.removeMetadata("CS_singed", (Plugin)CSMinion.this.plugin);
                        victim.removeMetadata("CS_trigDelay", (Plugin)CSMinion.this.plugin);
                        if (vehicle == null) {
                            CSMinion.this.detonateRDE(fisherman, victim, seagull_info, false);
                        }
                        else {
                            CSMinion.this.mineAction(vehicle, seagull_info, fisherman, shot, victim.getName(), (Entity)victim);
                        }
                    }
                }
            }
        }, 1L);
    }
    
    public void happilyEverAfter(final Item item) {
        if (item.getVehicle() instanceof Entity) {
            return;
        }
        for (final Entity v : item.getNearbyEntities(1.0, 10.0, 1.0)) {
            if (v instanceof Minecart && !(v.getPassenger() instanceof Entity)) {
                v.setPassenger((Entity)item);
                break;
            }
        }
    }
    
    public void happilyEverAfter(final Vehicle vehicle) {
        if (vehicle.getPassenger() instanceof Entity) {
            return;
        }
        for (final Entity i : vehicle.getNearbyEntities(1.0, 10.0, 1.0)) {
            if (i instanceof Item && !(i.getVehicle() instanceof Entity)) {
                vehicle.setPassenger(i);
                break;
            }
        }
    }
    
    public void mineAction(final Vehicle vehicle, final String[] seagull_info, final Player fisherman, final boolean shot, final String vicName, final Entity victim) {
        if (fisherman != null && vicName != null) {
            this.plugin.sendPlayerMessage((LivingEntity)fisherman, seagull_info[2], ".Explosive_Devices.Message_Trigger_Placer", true, seagull_info[1], vicName, "<flight>", "<damage>");
            this.plugin.playSoundEffects((Entity)fisherman, seagull_info[2], ".Explosive_Devices.Sounds_Alert_Placer", false);
        }
        if (victim instanceof Player && !seagull_info[1].equals(((Player)victim).getName())) {
            this.plugin.sendPlayerMessage((LivingEntity)victim, seagull_info[2], ".Explosive_Devices.Message_Trigger_Victim", true, seagull_info[1], vicName, "<flight>", "<damage>");
        }
        this.plugin.projectileExplosion((Entity)vehicle, seagull_info[2], shot, fisherman, true, false, null, null, false, 0);
        if (!shot) {
            this.plugin.playSoundEffects((Entity)vehicle, seagull_info[2], ".Explosive_Devices.Sounds_Trigger", false);
        }
        vehicle.getPassenger().remove();
    }
    
    public void tempVars(final Player player, final String metakey, final Long delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                player.removeMetadata(metakey, (Plugin)CSMinion.this.plugin);
            }
        }, (long)delay);
    }
    
    public void illegalSlap(final Player player, final Player victim) {
        final PermissionAttachment attachment = player.addAttachment((Plugin)this.plugin);
        attachment.setPermission("nocheatplus", true);
        attachment.setPermission("anticheat.check.exempt", true);
        attachment.setPermission("nocheat", true);
        victim.damage(0, (Entity)player);
        player.removeAttachment(attachment);
    }
    
    public String[] fastenSeatbelts(final Item psngr) {
        if (this.plugin.itemIsSafe(psngr.getItemStack())) {
            final String meta_pg = psngr.getItemStack().getItemMeta().getDisplayName();
            if (meta_pg.contains("§cS3AGULLL")) {
                final String[] seagull_info = meta_pg.split("~");
                return seagull_info;
            }
        }
        return null;
    }
    
    public String[] returnRefinedOre(final Player player, final String parent_node) {
        final String rdeOre = this.plugin.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Info");
        if (rdeOre != null) {
            final String[] rdeRefined = rdeOre.split("-");
            if (rdeRefined.length == 3) {
                try {
                    if (Integer.valueOf(rdeRefined[0]) < 1) {
                        if (player != null) {
                            player.sendMessage(String.valueOf(this.heading) + "'" + rdeRefined[0] + "' in '" + rdeOre + "' of weapon '" + parent_node + "' must be a positive number.");
                            return null;
                        }
                        return null;
                    }
                    else {
                        if (rdeRefined[1].length() == 2) {
                            return rdeRefined;
                        }
                        if (player != null) {
                            player.sendMessage(String.valueOf(this.heading) + "'" + rdeRefined[1] + "' in '" + rdeOre + "' of weapon '" + parent_node + "' must be 2 characters long, not " + rdeRefined[1].length() + ".");
                            return null;
                        }
                        return null;
                    }
                }
                catch (NumberFormatException ex) {
                    if (player != null) {
                        player.sendMessage(String.valueOf(this.heading) + "'" + rdeRefined[0] + "' in '" + rdeOre + "' of weapon '" + parent_node + "' is not a valid number.");
                        return null;
                    }
                    return null;
                }
            }
            if (player != null) {
                player.sendMessage(String.valueOf(this.heading) + "'" + rdeOre + "' of weapon '" + parent_node + "' has an incorrect format! The correct format is: Amount-UniqueID-Headname!");
            }
        }
        return null;
    }
    
    public void displayFireworks(final Entity entity, final String parent_node, final String child_node) {
        final boolean fireworks_enable = this.plugin.getBoolean(String.valueOf(parent_node) + ".Fireworks.Enable");
        if (!fireworks_enable) {
            return;
        }
        if (this.plugin.getString(String.valueOf(parent_node) + child_node) == null) {
            return;
        }
        final String[] fireworks = this.plugin.getString(String.valueOf(parent_node) + child_node).split(",");
        String[] array;
        for (int length = (array = fireworks).length, i = 0; i < length; ++i) {
            final String firework = array[i];
            final String space_filtered = firework.replace(" ", "");
            final String[] args = space_filtered.split("-");
            if (args.length == 6) {
                try {
                    Firework fw;
                    if (entity instanceof LivingEntity) {
                        fw = (Firework)entity.getWorld().spawn(((LivingEntity)entity).getEyeLocation(), (Class)Firework.class);
                    }
                    else {
                        fw = (Firework)entity.getWorld().spawn(entity.getLocation(), (Class)Firework.class);
                    }
                    final FireworkMeta fwm = fw.getFireworkMeta();
                    final FireworkEffect effect = FireworkEffect.builder().trail(Boolean.parseBoolean(args[1])).flicker(Boolean.parseBoolean(args[2])).withColor(Color.fromRGB(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]))).with(FireworkEffect.Type.valueOf(args[0].toUpperCase())).build();
                    fwm.addEffects(new FireworkEffect[] { effect });
                    fw.setFireworkMeta(fwm);
                }
                catch (IllegalArgumentException ex) {
                    System.out.print("[CrackShot] '" + space_filtered + "' of weapon '" + parent_node + "' has an incorrect value for firework type, flicker, trail, or colour!");
                }
            }
            else {
                System.out.print("[CrackShot] '" + space_filtered + "' of weapon '" + parent_node + "' has an invalid format! The correct format is: Type-Trail-Flicker-Red-Blue-Green!");
            }
        }
    }
    
    public void removeNamedItem(final Player player, final String itemInfo, final int totalAmt, final String parent_node) {
        int taken = 0;
        final ItemStack item = this.parseItemStack(itemInfo);
        if (item == null) {
            return;
        }
        if (this.containsItemStack(player, itemInfo, 1)) {
            final ItemStack[] contents = player.getInventory().getContents();
            for (int i = 0; taken <= totalAmt && i < contents.length; ++i) {
                if (contents[i] != null && contents[i].getTypeId() == item.getTypeId() && contents[i].getDurability() == item.getDurability()) {
                    if (contents[i].getAmount() > totalAmt - taken) {
                        contents[i].setAmount(contents[i].getAmount() - (totalAmt - taken));
                        taken = totalAmt;
                    }
                    else {
                        taken += contents[i].getAmount();
                        contents[i] = null;
                    }
                }
            }
            player.getInventory().setContents(contents);
            player.updateInventory();
            if (!this.containsItemStack(player, itemInfo, 1)) {
                this.plugin.playSoundEffects((Entity)player, parent_node, ".Ammo.Sounds_Out_Of_Ammo", false);
            }
        }
    }
    
    public int countItemStacks(final Player player, final String itemInfo) {
        int amount = 0;
        final ItemStack item = this.parseItemStack(itemInfo);
        if (item == null) {
            return 0;
        }
        final ItemStack[] slots = player.getInventory().getContents();
        ItemStack[] array;
        for (int length = (array = slots).length, i = 0; i < length; ++i) {
            final ItemStack slot = array[i];
            if (slot != null && slot.getTypeId() == item.getTypeId() && slot.getDurability() == item.getDurability()) {
                amount += slot.getAmount();
            }
        }
        return amount;
    }
    
    public boolean containsItemStack(final Player player, final String itemInfo, final int minAmt) {
        int counter = 0;
        boolean retVal = true;
        final ItemStack item = this.parseItemStack(itemInfo);
        if (item == null) {
            return false;
        }
        final ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; counter < minAmt && i < contents.length; ++i) {
            if (contents[i] != null && contents[i].getTypeId() == item.getTypeId() && contents[i].getDurability() == item.getDurability()) {
                counter += contents[i].getAmount();
            }
        }
        if (counter < minAmt) {
            retVal = false;
        }
        return retVal;
    }
    
    public void givePotionEffects(final LivingEntity player, final String parent_node, final String child_node, final String event) {
        if (!event.equals("explosion")) {
            final String potion_events = this.plugin.getString(String.valueOf(parent_node) + ".Potion_Effects.Activation");
            if (potion_events == null) {
                return;
            }
            if (!potion_events.toLowerCase().contains(event)) {
                return;
            }
        }
        if (this.plugin.getString(String.valueOf(parent_node) + child_node) == null) {
            return;
        }
        final String[] potion_effects = this.plugin.getString(String.valueOf(parent_node) + child_node).split(",");
        String[] array;
        for (int length = (array = potion_effects).length, i = 0; i < length; ++i) {
            final String potion_effect = array[i];
            final String space_filtered = potion_effect.replace(" ", "");
            final String[] args = space_filtered.split("-");
            if (args.length == 3) {
                try {
                    final PotionEffectType potion_type = PotionEffectType.getByName(args[0].toUpperCase());
                    int duration = Integer.parseInt(args[1]);
                    if (potion_type.getDurationModifier() != 1.0) {
                        final double maths = duration * (1.0 / potion_type.getDurationModifier());
                        duration = (int)maths;
                    }
                    player.addPotionEffect(potion_type.createEffect(duration, Integer.parseInt(args[2]) - 1));
                }
                catch (Exception ex) {
                    System.out.print("[CrackShot] '" + space_filtered + "' of weapon '" + parent_node + "' has an incorrect potion type, duration or level!");
                }
            }
            else {
                System.out.print("[CrackShot] '" + space_filtered + "' of weapon '" + parent_node + "' has an invalid format! The correct format is: Potion-Duration-Level!");
            }
        }
    }
    
    public void giveParticleEffects(final Entity player, final String parent_node, final String child_node, final boolean b_shooter) {
        final boolean particle_enable = this.plugin.getBoolean(String.valueOf(parent_node) + ".Particles.Enable");
        if (!particle_enable) {
            return;
        }
        if (this.plugin.getString(String.valueOf(parent_node) + child_node) == null) {
            return;
        }
        Location loc = player.getLocation();
        if (b_shooter) {
            loc = ((LivingEntity)player).getEyeLocation().toVector().add(((LivingEntity)player).getEyeLocation().getDirection().multiply(1.5)).toLocation(player.getWorld());
        }
        final String[] particle_effects = this.plugin.getString(String.valueOf(parent_node) + child_node).split(",");
        String[] array;
        for (int length = (array = particle_effects).length, j = 0; j < length; ++j) {
            final String particle_effect = array[j];
            final String space_filtered = particle_effect.replace(" ", "");
            final String[] args = space_filtered.split("-");
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("smoke")) {
                    for (int i = 0; i < 8; ++i) {
                        player.getWorld().playEffect(loc, Effect.SMOKE, i);
                    }
                }
                else if (args[0].equalsIgnoreCase("lightning")) {
                    player.getWorld().strikeLightningEffect(player.getLocation());
                }
                else if (args[0].equalsIgnoreCase("explosion")) {
                    player.getWorld().createExplosion(loc, 0.0f);
                }
            }
            else if (args.length == 2 && args[1] != null) {
                if (args[0].equalsIgnoreCase("potion_splash")) {
                    player.getWorld().playEffect(loc, Effect.POTION_BREAK, Integer.parseInt(args[1]));
                }
                else if (args[0].equalsIgnoreCase("block_break")) {
                    player.getWorld().playEffect(loc, Effect.STEP_SOUND, Integer.parseInt(args[1]));
                }
                else if (args[0].equalsIgnoreCase("flames")) {
                    player.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, Integer.parseInt(args[1]));
                }
            }
        }
    }
    
    public void giveParticleEffects(final Location loc, final String parent_node, final String child_node) {
        if (this.plugin.getString(String.valueOf(parent_node) + child_node) == null) {
            return;
        }
        final String[] particle_effects = this.plugin.getString(String.valueOf(parent_node) + child_node).split(",");
        String[] array;
        for (int length = (array = particle_effects).length, j = 0; j < length; ++j) {
            final String particle_effect = array[j];
            final String space_filtered = particle_effect.replace(" ", "");
            final String[] args = space_filtered.split("-");
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("smoke")) {
                    for (int i = 0; i < 8; ++i) {
                        loc.getWorld().playEffect(loc, Effect.SMOKE, i);
                    }
                }
                else if (args[0].equalsIgnoreCase("lightning")) {
                    loc.getWorld().strikeLightningEffect(loc);
                }
                else if (args[0].equalsIgnoreCase("explosion")) {
                    loc.getWorld().createExplosion(loc, 0.0f);
                }
            }
            else if (args.length == 2 && args[1] != null) {
                if (args[0].equalsIgnoreCase("potion_splash")) {
                    loc.getWorld().playEffect(loc, Effect.POTION_BREAK, Integer.parseInt(args[1]));
                }
                else if (args[0].equalsIgnoreCase("block_break")) {
                    loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Integer.parseInt(args[1]));
                }
                else if (args[0].equalsIgnoreCase("flames")) {
                    loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, Integer.parseInt(args[1]));
                }
            }
        }
    }
    
    public boolean isInsideCuboid(final Entity player, final Location loc1, final Location loc2, final World world) {
        final double[] dim = new double[2];
        if (!player.getWorld().equals(world)) {
            return false;
        }
        dim[0] = loc1.getX();
        dim[1] = loc2.getX();
        Arrays.sort(dim);
        if (player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0]) {
            return false;
        }
        dim[0] = loc1.getY();
        dim[1] = loc2.getY();
        Arrays.sort(dim);
        if (player.getLocation().getY() > dim[1] || player.getLocation().getY() < dim[0]) {
            return false;
        }
        dim[0] = loc1.getZ();
        dim[1] = loc2.getZ();
        Arrays.sort(dim);
        return player.getLocation().getZ() <= dim[1] && player.getLocation().getZ() >= dim[0];
    }
    
    public boolean regionCheck(final Entity player, final String parent_node) {
        if (!this.plugin.getBoolean(String.valueOf(parent_node) + ".Region_Check.Enable")) {
            return true;
        }
        final String region_info = this.plugin.getString(String.valueOf(parent_node) + ".Region_Check.World_And_Coordinates");
        final String[] regions = region_info.split("\\|");
        boolean retVal = false;
        String[] array;
        for (int length = (array = regions).length, i = 0; i < length; ++i) {
            String region = array[i];
            region = region.replace(" ", "");
            final String[] args = region.split(",");
            if (args != null && (args.length == 7 || args.length == 8)) {
                boolean blackList = false;
                if (args.length == 8 && Boolean.valueOf(args[7])) {
                    blackList = true;
                }
                try {
                    final World regionWorld = Bukkit.getWorld(args[0]);
                    final Location locOne = new Location(regionWorld, (double)Double.valueOf(args[1]), (double)Double.valueOf(args[2]), (double)Double.valueOf(args[3]));
                    final Location locTwo = new Location(regionWorld, (double)Double.valueOf(args[4]), (double)Double.valueOf(args[5]), (double)Double.valueOf(args[6]));
                    if (this.isInsideCuboid(player, locOne, locTwo, regionWorld)) {
                        if (blackList) {
                            return false;
                        }
                        retVal = true;
                    }
                    else if (blackList && player.getWorld().equals(regionWorld)) {
                        retVal = true;
                    }
                }
                catch (NumberFormatException ex) {
                    if (player instanceof Player) {
                        ((Player)player).sendMessage(String.valueOf(this.heading) + "The value provided for the 'World_And_Coordinates' node of the weapon '" + parent_node + "' is incorrect. Double check the coordinates.");
                    }
                }
            }
            else if (player instanceof Player) {
                ((Player)player).sendMessage(String.valueOf(this.heading) + "The 'World_And_Coordinates' node of the weapon '" + parent_node + "' has an incorrect number of arguments.");
            }
        }
        return retVal;
    }
    
    public void weaponInteraction(final Player shooter, final String parent_node, final boolean leftClick) {
        final String projType = this.plugin.getString(String.valueOf(parent_node) + ".Shooting.Projectile_Type");
        final boolean underwater = this.plugin.getBoolean(String.valueOf(parent_node) + ".Extras.Disable_Underwater");
        final boolean exploDev = this.plugin.getBoolean(String.valueOf(parent_node) + ".Explosive_Devices.Enable");
        final String[] validTypes = { "arrow", "snowball", "egg", "grenade", "flare", "fireball", "witherskull" };
        if (underwater) {
            final Location loc = shooter.getEyeLocation();
            if (loc.getBlock().getType() == Material.WATER || loc.getBlock().getType() == Material.STATIONARY_WATER) {
                return;
            }
        }
        if (!exploDev) {
            if (projType != null) {
                String[] array;
                for (int length = (array = validTypes).length, i = 0; i < length; ++i) {
                    final String type = array[i];
                    if (projType.equalsIgnoreCase(type)) {
                        this.plugin.fireProjectile(shooter, parent_node, leftClick);
                        return;
                    }
                }
                System.out.print("[CrackShot] '" + projType + "' is not a valid type of projectile!");
            }
        }
        else {
            this.plugin.fireProjectile(shooter, parent_node, leftClick);
        }
    }
    
    public void callAirstrike(final Entity mark, final String parent_node, final Player player) {
        final int height = this.plugin.getInt(String.valueOf(parent_node) + ".Airstrikes.Height_Dropped");
        final int area = this.plugin.getInt(String.valueOf(parent_node) + ".Airstrikes.Area");
        final int spacing = this.plugin.getInt(String.valueOf(parent_node) + ".Airstrikes.Distance_Between_Bombs");
        int strikeNo = this.plugin.getInt(String.valueOf(parent_node) + ".Airstrikes.Multiple_Strikes.Number_Of_Strikes");
        int strikeDelay = this.plugin.getInt(String.valueOf(parent_node) + ".Airstrikes.Multiple_Strikes.Delay_Between_Strikes");
        final boolean multiStrike = this.plugin.getBoolean(String.valueOf(parent_node) + ".Airstrikes.Multiple_Strikes.Enable");
        final double coordinator = (area - 1) * (spacing / 2.0);
        final Location loc = mark.getLocation();
        final int y = loc.getBlockY();
        if (!multiStrike) {
            strikeNo = 1;
            strikeDelay = 1;
        }
        final Random r = new Random();
        final int vVar = this.plugin.getInt(String.valueOf(parent_node) + ".Airstrikes.Vertical_Variation");
        final int hVar = this.plugin.getInt(String.valueOf(parent_node) + ".Airstrikes.Horizontal_Variation");
        final String block = this.plugin.getString(String.valueOf(parent_node) + ".Airstrikes.Block_Type");
        if (block == null) {
            return;
        }
        String[] blockInfo = block.split("~");
        if (blockInfo.length < 2) {
            blockInfo = new String[] { blockInfo[0], "0" };
        }
        try {
            final Material blockMat = Material.getMaterial((int)Integer.valueOf(blockInfo[0]));
            final Byte secondaryData = Byte.valueOf(blockInfo[1]);
            this.plugin.sendPlayerMessage((LivingEntity)player, parent_node, ".Airstrikes.Message_Call_Airstrike", true, player.getName(), "<victim>", "<flight>", "<damage>");
            this.giveParticleEffects(loc, parent_node, ".Airstrikes.Particle_Call_Airstrike");
            for (int delay = 0; delay < strikeDelay * strikeNo; delay += strikeDelay) {
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CSMinion.this.plugin.playSoundEffects(mark, parent_node, ".Airstrikes.Sounds_Airstrike", false);
                        for (int iOne = 0; iOne < area; ++iOne) {
                            double x = loc.getBlockX() + iOne * spacing - coordinator;
                            for (int iTwo = 0; iTwo < area; ++iTwo) {
                                double z = loc.getBlockZ() + iTwo * spacing - coordinator;
                                int hD = y + height;
                                if (vVar != 0) {
                                    hD += r.nextInt(vVar);
                                }
                                if (hVar != 0) {
                                    x += r.nextInt(hVar) - r.nextInt(hVar);
                                    z += r.nextInt(hVar) - r.nextInt(hVar);
                                }
                                final FallingBlock bomb = loc.getWorld().spawnFallingBlock(new Location(loc.getWorld(), x, (double)hD, z), blockMat, (byte)secondaryData);
                                bomb.setDropItem(false);
                                bomb.setMetadata("CS_strike", (MetadataValue)new FixedMetadataValue((Plugin)CSMinion.this.plugin, (Object)(String.valueOf(parent_node) + "~" + player.getName())));
                            }
                        }
                    }
                }, (long)delay);
                if (!multiStrike) {
                    break;
                }
            }
        }
        catch (IllegalArgumentException ex) {
            player.sendMessage(String.valueOf(this.heading) + "'" + block + "' in the 'Airstrikes' module of weapon '" + parent_node + "' is not a valid block-type.");
        }
    }
    
    public void detonateRDE(final Player player, final Player victim, final String[] itemInfo, final boolean clacker) {
        final World world = Bukkit.getServer().getWorld(itemInfo[1]);
        final Location loc = new Location(world, Integer.valueOf(itemInfo[2]) + 0.5, Integer.valueOf(itemInfo[3]) + 0.5, Integer.valueOf(itemInfo[4]) + 0.5);
        final Block c4 = world.getBlockAt(loc);
        if (c4.getType() == Material.SKULL && c4.getState() instanceof Skull) {
            final Skull c4Block = (Skull)c4.getState();
            if (c4Block.hasOwner()) {
                final String grabInfo = c4Block.getOwner();
                final String[] blockInfo = grabInfo.split("\u060c");
                if (blockInfo.length < 1) {
                    return;
                }
                final String uniqueID = blockInfo[0];
                for (final String ids : this.plugin.rdelist.keySet()) {
                    if (ids.equals(uniqueID)) {
                        final String parent_node = this.plugin.rdelist.get(ids);
                        final String[] refinedOre = this.returnRefinedOre(player, parent_node);
                        if (refinedOre != null) {
                            c4Block.setOwner(refinedOre[2]);
                            c4Block.update(false);
                        }
                        if (!clacker) {
                            if (player != null) {
                                this.plugin.sendPlayerMessage((LivingEntity)player, parent_node, ".Explosive_Devices.Message_Trigger_Placer", true, blockInfo[1].replace(String.valueOf('\u0638'), "..."), victim.getName(), "<flight>", "<damage>");
                                this.plugin.playSoundEffects((Entity)player, parent_node, ".Explosive_Devices.Sounds_Alert_Placer", false);
                            }
                            this.plugin.sendPlayerMessage((LivingEntity)victim, parent_node, ".Explosive_Devices.Message_Trigger_Victim", true, blockInfo[1].replace(String.valueOf('\u0638'), "..."), victim.getName(), "<flight>", "<damage>");
                        }
                        c4Block.setMetadata("CS_transformers", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)true));
                        this.plugin.playSoundEffects(loc, parent_node, ".Explosive_Devices.Sounds_Trigger");
                        this.plugin.projectileExplosion(null, parent_node, false, player, false, true, loc, c4, false, 0);
                    }
                }
            }
        }
    }
    
    public boolean boobyAction(final Block block, final Entity victim, final ItemStack item) {
        for (final String name : this.plugin.boobs.keySet()) {
            if (this.plugin.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(name)) {
                final Pattern ammo_brackets = Pattern.compile("\\«([^»]*)\\»");
                final Matcher bracket_detector = ammo_brackets.matcher(item.getItemMeta().getDisplayName());
                if (!bracket_detector.find()) {
                    continue;
                }
                final String parent_node = this.plugin.boobs.get(name);
                String vicName = "Santa Claus";
                if (victim != null) {
                    if (victim instanceof Player) {
                        vicName = ((Player)victim).getName();
                    }
                    else {
                        vicName = victim.getType().getName();
                    }
                }
                if (!this.getBoobean(3, parent_node)) {
                    return false;
                }
                final String detectedName = bracket_detector.group(1);
                if (detectedName.equals("?")) {
                    return false;
                }
                final Player planter = Bukkit.getServer().getPlayer(detectedName);
                if (victim != null) {
                    if (planter != null) {
                        if (planter == victim) {
                            return false;
                        }
                        this.plugin.sendPlayerMessage((LivingEntity)planter, parent_node, ".Explosive_Devices.Message_Trigger_Placer", true, detectedName, vicName, "<flight>", "<damage>");
                        this.plugin.playSoundEffects((Entity)planter, parent_node, ".Explosive_Devices.Sounds_Alert_Placer", false);
                    }
                    if (victim instanceof Player) {
                        this.plugin.sendPlayerMessage((LivingEntity)victim, parent_node, ".Explosive_Devices.Message_Trigger_Victim", true, detectedName, vicName, "<flight>", "<damage>");
                    }
                }
                this.plugin.playSoundEffects(block.getLocation().add(0.5, 0.5, 0.5), parent_node, ".Explosive_Devices.Sounds_Trigger");
                this.plugin.projectileExplosion(null, parent_node, false, planter, false, true, null, block, true, 0);
                return true;
            }
        }
        return false;
    }
    
    public boolean getBoobean(final int entry, final String parent_node) {
        final String ore = this.plugin.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Info");
        if (ore == null) {
            return false;
        }
        final String[] refinedOre = ore.split("-");
        return refinedOre.length == 5 && Boolean.valueOf(refinedOre[entry - 1]);
    }
    
    public ItemStack parseItemStack(final String ore) {
        ItemStack item = null;
        if (ore != null) {
            String[] refinedOre = ore.split("~");
            if (refinedOre.length == 1) {
                refinedOre = new String[] { refinedOre[0], "0" };
            }
            try {
                item = new ItemStack(Material.getMaterial((int)Integer.valueOf(refinedOre[0])), 1, (short)Short.valueOf(refinedOre[1]));
            }
            catch (Exception ex) {}
        }
        return item;
    }
    
    public void convertOld(final ItemStack item) {
        if (item != null && this.plugin.itemIsSafe(item)) {
            String displayName = item.getItemMeta().getDisplayName();
            if (displayName.contains("»") && displayName.contains(" «")) {
                String colorCodes = ChatColor.getLastColors(displayName);
                if (colorCodes != null && colorCodes.length() > 1) {
                    colorCodes = "§" + colorCodes.substring(colorCodes.length() - 1);
                    String delimiter = " «";
                    if (displayName.contains(" \u25aa «")) {
                        delimiter = " \u25aa «";
                    }
                    else if (displayName.contains(" \u25ab «")) {
                        delimiter = " \u25ab «";
                    }
                    else if (displayName.contains(" \u06d4 «")) {
                        delimiter = " \u06d4 «";
                    }
                    final String[] refinedName = displayName.split(delimiter);
                    displayName = refinedName[0];
                    if (!displayName.substring(displayName.length() - 2).equals(colorCodes)) {
                        this.setItemName(item, String.valueOf(displayName) + colorCodes + delimiter + refinedName[1]);
                    }
                }
            }
        }
    }
}
