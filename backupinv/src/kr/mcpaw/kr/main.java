// 
// Decompiled by Procyon v0.5.30
// 

package kr.mcpaw.kr;

import org.bukkit.plugin.PluginManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
    public void onEnable() {
        Bukkit.getLogger().info("����� �ּż� �����մϴ�.");
        final backupsystemcommand command24 = new backupsystemcommand(this);
        this.getCommand("idplb").setExecutor((CommandExecutor)command24);
        File d = new File("plugins/idpld");
        if (!d.exists()) {
            d.mkdirs();
            Bukkit.getLogger().info("������� ���� ����");
        }
        d = new File("plugins/idpld/folder");
        if (!d.exists()) {
            d.mkdirs();
            Bukkit.getLogger().info("������� ���� ����");
        }
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)this, (Plugin)this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    final SimpleDateFormat format = new SimpleDateFormat("MM��dd��HH��mm��");
                    final Date ds = new Date();
                    final String date = format.format(ds);
                    final File f = new File("plugins/idpld/folder/" + date);
                    if (!f.exists()) {
                        f.mkdir();
                    }
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, j = 0; j < length; ++j) {
                        final Player p = onlinePlayers[j];
                        final String db = p.getName();
                        final FileConfiguration d = (FileConfiguration)new YamlConfiguration();
                        try {
                            d.set("BOOTS", (Object)p.getInventory().getBoots());
                            d.set("HElMET", (Object)p.getInventory().getHelmet());
                            d.set("CHEST", (Object)p.getInventory().getChestplate());
                            d.set("LEGGINGS", (Object)p.getInventory().getLeggings());
                            for (int i = 0; i < 36; ++i) {
                                d.set(new StringBuilder().append(i).toString(), (Object)p.getInventory().getItem(i));
                            }
                            d.save(new File("plugins/idpld/folder/" + date + "/" + db.replace("\\", "").replace(":", "").replace("/", "").replace(".", "").replace("\"", "").replace("<", "").replace(">", "").replace("*", "").replace("|", "")));
                        }
                        catch (FileNotFoundException ex) {}
                        catch (IOException ex2) {}
                        
                        Bukkit.getLogger().info("[�˸�] �κ��丮 ����� �Ϸ��߽��ϴ�.");
                    }
                }
                catch (Exception ex3) {}
            }
        }, 18000L, 18000L);
    }
    
    public void onDisable() {
        Bukkit.getLogger().info("����� �ּż� �����մϴ�.");
    }
}
