// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.item;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.io.UnsupportedEncodingException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import java.io.PrintStream;
import org.bukkit.util.FileUtil;
import think.rpgitems.power.Power;
import org.bukkit.configuration.InvalidConfigurationException;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.FileInputStream;
import java.io.File;
import think.rpgitems.Plugin;
import java.util.HashMap;
import think.rpgitems.lib.gnu.trove.map.hash.TIntObjectHashMap;

public class ItemManager
{
    public static TIntObjectHashMap<RPGItem> itemById;
    public static HashMap<String, RPGItem> itemByName;
    public static HashMap<String, ItemGroup> groups;
    public static int currentPos;
    
    static {
        ItemManager.itemById = new TIntObjectHashMap<RPGItem>();
        ItemManager.itemByName = new HashMap<String, RPGItem>();
        ItemManager.groups = new HashMap<String, ItemGroup>();
        ItemManager.currentPos = 0;
    }
    
    public static void load(final Plugin plugin) {
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
            catch (FileNotFoundException ex) {}
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (InvalidConfigurationException e2) {
                e2.printStackTrace();
            }
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
            ItemManager.currentPos = itemStorage.getInt("pos", 0);
            final ConfigurationSection section = itemStorage.getConfigurationSection("items");
            if (section == null) {
                return;
            }
            for (final String key : section.getKeys(false)) {
                final RPGItem item = new RPGItem(section.getConfigurationSection(key));
                ItemManager.itemById.put(item.getID(), item);
                ItemManager.itemByName.put(item.getName(), item);
                for (final Power power : item.powers) {
                    Power.powerUsage.put(power.getName(), Power.powerUsage.get(power.getName()) + 1);
                }
            }
            if (itemStorage.contains("groups")) {
                final ConfigurationSection gSection = itemStorage.getConfigurationSection("groups");
                for (final String key2 : gSection.getKeys(false)) {
                    final ItemGroup group = new ItemGroup(gSection.getConfigurationSection(key2));
                    ItemManager.groups.put(group.getName(), group);
                }
            }
        }
        catch (Exception e4) {
            plugin.getLogger().severe("Error loading items.yml. Creating backup");
            final File file = new File(plugin.getDataFolder(), "items.yml");
            final long time = System.currentTimeMillis();
            final File backup = new File(plugin.getDataFolder(), String.valueOf(time) + "-items.yml");
            FileUtil.copy(file, backup);
            final File log = new File(plugin.getDataFolder(), String.valueOf(time) + "-log.txt");
            PrintStream ps = null;
            try {
                ps = new PrintStream(log);
                ps.printf("RPGItems (%s) ItemManager.load\r\n", plugin.getDescription().getVersion());
                e4.printStackTrace(ps);
            }
            catch (FileNotFoundException e5) {
                e5.printStackTrace();
                return;
            }
            finally {
                ps.close();
            }
            ps.close();
        }
    }
    
    public static void save(final Plugin plugin) {
        final YamlConfiguration itemStorage = new YamlConfiguration();
        itemStorage.set("items", (Object)null);
        itemStorage.set("pos", (Object)ItemManager.currentPos);
        final ConfigurationSection newSection = itemStorage.createSection("items");
        for (final RPGItem item : ItemManager.itemById.valueCollection()) {
            ConfigurationSection itemSection = newSection.getConfigurationSection(item.getName());
            if (itemSection == null) {
                itemSection = newSection.createSection(item.getName());
            }
            item.save(itemSection);
        }
        final ConfigurationSection groupsSection = itemStorage.createSection("groups");
        for (final Map.Entry<String, ItemGroup> group : ItemManager.groups.entrySet()) {
            final ConfigurationSection groupSection = groupsSection.createSection((String)group.getKey());
            group.getValue().save(groupSection);
        }
        FileOutputStream out = null;
        try {
            final File f = new File(plugin.getDataFolder(), "items.yml");
            out = new FileOutputStream(f);
            out.write(itemStorage.saveToString().getBytes("UTF-8"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e4) {
                e4.printStackTrace();
            }
        }
        try {
            if (out != null) {
                out.close();
            }
        }
        catch (IOException e4) {
            e4.printStackTrace();
        }
    }
    
    public static RPGItem toRPGItem(final ItemStack item) {
        if (item == null) {
            return null;
        }
        if (!item.hasItemMeta()) {
            return null;
        }
        final ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) {
            return null;
        }
        try {
            final int id = decodeId(meta.getDisplayName());
            final RPGItem rItem = getItemById(id);
            return rItem;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static RPGItem newItem(final String name) {
        if (ItemManager.itemByName.containsKey(name)) {
            return null;
        }
        int free = 0;
        do {
            free = ItemManager.currentPos++;
        } while (ItemManager.itemById.containsKey(free));
        final RPGItem item = new RPGItem(name, free);
        ItemManager.itemById.put(free, item);
        ItemManager.itemByName.put(name, item);
        return item;
    }
    
    public static RPGItem getItemById(final int id) {
        return ItemManager.itemById.get(id);
    }
    
    public static RPGItem getItemByName(final String uid) {
        return ItemManager.itemByName.get(uid);
    }
    
    public static int decodeId(final String str) throws Exception {
        if (str.length() < 16) {
            throw new Exception();
        }
        final StringBuilder out = new StringBuilder();
        for (int i = 0; i < 16; ++i) {
            if (str.charAt(i) != '§') {
                throw new Exception();
            }
            ++i;
            out.append(str.charAt(i));
        }
        return Integer.parseInt(out.toString(), 16);
    }
    
    public static void remove(final RPGItem item) {
        ItemManager.itemByName.remove(item.getName());
        ItemManager.itemById.remove(item.getID());
    }
}
