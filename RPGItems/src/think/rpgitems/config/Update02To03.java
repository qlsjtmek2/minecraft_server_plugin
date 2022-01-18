// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.config;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import think.rpgitems.item.ItemManager;
import think.rpgitems.data.Locale;
import think.rpgitems.item.RPGItem;
import think.rpgitems.power.Power;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import think.rpgitems.item.Quality;
import org.bukkit.ChatColor;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.configuration.InvalidConfigurationException;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.FileInputStream;
import java.io.File;
import think.rpgitems.Plugin;
import org.bukkit.configuration.ConfigurationSection;

public class Update02To03 implements Updater
{
    @Override
    public void update(final ConfigurationSection section) {
        final Plugin plugin = Plugin.plugin;
        try {
            FileInputStream in = null;
            YamlConfiguration itemStorage = null;
            try {
                final File f = new File(plugin.getDataFolder(), "items.yml");
                in = new FileInputStream(f);
                final byte[] data = new byte[(int)f.length()];
                in.read(data);
                itemStorage = new YamlConfiguration();
                final String str = new String(data, "UTF-8");
                itemStorage.loadFromString(str);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
            catch (InvalidConfigurationException e3) {
                e3.printStackTrace();
            }
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                }
                catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException e4) {
                e4.printStackTrace();
            }
            final int currentPos = itemStorage.getInt("pos", 0);
            final ConfigurationSection itemSection = itemStorage.getConfigurationSection("items");
            if (itemSection != null) {
                for (final String itemKey : itemSection.getKeys(false)) {
                    final ConfigurationSection s = itemSection.getConfigurationSection(itemKey);
                    final String name = s.getString("name");
                    final int id = s.getInt("id");
                    String displayName = null;
                    String type = null;
                    String hand = null;
                    String lore = null;
                    try {
                        if (s.contains("display")) {
                            displayName = s.getString("display");
                        }
                        else {
                            displayName = new String(byte[].class.cast(s.get("display_bin", (Object)"")), "UTF-8");
                        }
                        if (s.contains("type")) {
                            type = s.getString("type", Plugin.plugin.getConfig().getString("defaults.sword", "Sword"));
                        }
                        else if (s.contains("type_bin")) {
                            type = new String(byte[].class.cast(s.get("type_bin", (Object)"")), "UTF-8");
                        }
                        else {
                            type = Plugin.plugin.getConfig().getString("defaults.sword", "Sword");
                        }
                        if (s.contains("hand")) {
                            hand = s.getString("hand", Plugin.plugin.getConfig().getString("defaults.hand", "One handed"));
                        }
                        else if (s.contains("hand_bin")) {
                            hand = new String(byte[].class.cast(s.get("hand_bin", (Object)"")), "UTF-8");
                        }
                        else {
                            hand = Plugin.plugin.getConfig().getString("defaults.hand", "One handed");
                        }
                        if (s.contains("lore")) {
                            lore = s.getString("lore");
                        }
                        else if (s.contains("lore_bin")) {
                            lore = new String(byte[].class.cast(s.get("lore_bin", (Object)"")), "UTF-8");
                        }
                        else {
                            lore = "";
                        }
                    }
                    catch (Exception e5) {
                        e5.printStackTrace();
                    }
                    final List<String> description = (List<String>)s.getList("description", (List)new ArrayList());
                    for (int i = 0; i < description.size(); ++i) {
                        description.set(i, ChatColor.translateAlternateColorCodes('&', (String)description.get(i)));
                    }
                    final Quality quality = Quality.valueOf(s.getString("quality"));
                    final int damageMin = s.getInt("damageMin");
                    final int damageMax = s.getInt("damageMax");
                    final int armour = s.getInt("armour", 0);
                    final ItemStack item = new ItemStack(Material.valueOf(s.getString("item")));
                    final ItemMeta meta = item.getItemMeta();
                    if (meta instanceof LeatherArmorMeta) {
                        ((LeatherArmorMeta)meta).setColor(Color.fromRGB(s.getInt("item_colour", 0)));
                    }
                    else {
                        item.setDurability((short)s.getInt("item_data", 0));
                    }
                    final boolean ignoreWorldGuard = s.getBoolean("ignoreWorldGuard", false);
                    final ConfigurationSection powerSection = s.getConfigurationSection("powers");
                    final ArrayList<Power> powers = new ArrayList<Power>();
                    if (powerSection != null) {
                        for (final String key : powerSection.getKeys(false)) {
                            try {
                                if (!Power.powers.containsKey(key)) {
                                    continue;
                                }
                                final Power pow = (Power)Power.powers.get(key).newInstance();
                                pow.init(powerSection.getConfigurationSection(key));
                                powers.add(pow);
                            }
                            catch (InstantiationException e6) {
                                e6.printStackTrace();
                            }
                            catch (IllegalAccessException e7) {
                                e7.printStackTrace();
                            }
                        }
                    }
                    final RPGItem newItem = new RPGItem(name, id);
                    newItem.setDisplay(displayName, false);
                    newItem.setType(type, false);
                    newItem.setHand(hand, false);
                    newItem.setLore(lore, false);
                    newItem.setItem(item.getType());
                    for (final String locales : Locale.getLocales()) {
                        newItem.setLocaleMeta(locales, meta);
                    }
                    newItem.setItem(item.getType(), false);
                    newItem.setDataValue(item.getDurability(), false);
                    newItem.setArmour(armour, false);
                    newItem.setDamage(damageMin, damageMax);
                    newItem.setQuality(quality, false);
                    newItem.ignoreWorldGuard = ignoreWorldGuard;
                    newItem.description = description;
                    for (final Power power : powers) {
                        newItem.addPower(power, false);
                    }
                    ItemManager.itemById.put(newItem.getID(), newItem);
                    ItemManager.itemByName.put(newItem.getName(), newItem);
                }
            }
            ItemManager.currentPos = currentPos;
            ItemManager.save(plugin);
            ItemManager.itemByName.clear();
            ItemManager.itemById.clear();
        }
        catch (Exception e8) {
            e8.printStackTrace();
        }
        section.set("version", (Object)"0.3");
    }
}
