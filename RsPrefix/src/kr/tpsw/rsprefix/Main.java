// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix;

import kr.tpsw.rsprefix.api.FileAPI;
import kr.tpsw.rsprefix.api.RanPreAPI;
import java.io.File;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import kr.tpsw.rsprefix.command.CommandPrefix;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    public void onEnable() {
        this.getCommand("prefix").setExecutor((CommandExecutor)new CommandPrefix());
        this.getCommand("\uce6d\ud638").setExecutor((CommandExecutor)new CommandPrefix());
        Bukkit.getPluginManager().registerEvents((Listener)new EventListener(), (Plugin)this);
        File f = new File("plugins\\RsPrefix\\Users");
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File("plugins\\RsPrefix\\RanPrefixs");
        if (!f.exists()) {
            f.mkdirs();
        }
        if (!new File("plugins\\RsPrefix\\RanPrefixs\\basic.txt").exists()) {
            RanPreAPI.initSetting();
        }
        RanPreAPI.initLoad();
        RanPreAPI.Pclass[] values;
        for (int length = (values = RanPreAPI.Pclass.values()).length, i = 0; i < length; ++i) {
            final RanPreAPI.Pclass pc = values[i];
            pc.loadMap();
        }
        FileAPI.initLoad();
    }
    
    public void onDisable() {
        FileAPI.endSave();
        RanPreAPI.Pclass[] values;
        for (int length = (values = RanPreAPI.Pclass.values()).length, i = 0; i < length; ++i) {
            final RanPreAPI.Pclass pc = values[i];
            pc.saveMap();
        }
    }
}
