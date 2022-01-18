// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.calladmin;

import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.EventHandler;
import java.util.ArrayList;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.lang.reflect.Method;
import java.io.IOException;
import java.net.URLClassLoader;
import java.net.URL;
import com.bpo.service.JarUtils;
import java.io.File;
import org.bukkit.plugin.Plugin;
import com.bpo.IO.CheckData;
import com.bpo.IO.CheckKey;
import org.bukkit.Bukkit;
import com.bpo.service.ReadInfo;
import com.bpo.IO.IODataFile;
import com.bpo.service.ApplicationServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class CallAdminEntry extends JavaPlugin implements Listener
{
    private ApplicationServer server;
    public static IODataFile io;
    boolean keyCheck;
    private String serverName;
    
    public CallAdminEntry() {
        this.server = new ApplicationServer();
        this.keyCheck = false;
    }
    
    public void onEnable() {
        this.serverName = ReadInfo.GetServer().get((Object)"Name").toString();
        Bukkit.getConsoleSender().sendMessage("BPo's CallAdmin Plugin Loading...!");
        final CheckKey key = new CheckKey();
        this.keyCheck = key.run();
        final CheckData d = new CheckData();
        d.run();
        CallAdminEntry.io = new IODataFile(d.getObj(), key.getObj());
        if (!this.keyCheck) {
            Bukkit.getConsoleSender().sendMessage("BPo's CallAdmin Plugin : Config를 설정해 주세요!");
        }
        else {
            this.server.start();
            this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
            try {
                final File[] libs = { new File(this.getDataFolder(), "gcm-server.jar") };
                File[] arrayOfFile1;
                for (int j = (arrayOfFile1 = libs).length, i = 0; i < j; ++i) {
                    final File lib = arrayOfFile1[i];
                    if (!lib.exists()) {
                        JarUtils.extractFromJar(lib.getName(), lib.getAbsolutePath());
                    }
                }
                for (int j = (arrayOfFile1 = libs).length, i = 0; i < j; ++i) {
                    final File lib = arrayOfFile1[i];
                    if (!lib.exists()) {
                        this.getLogger().warning("There was a critical error loading My plugin! Could not find lib: " + lib.getName());
                        Bukkit.getServer().getPluginManager().disablePlugin((Plugin)this);
                        return;
                    }
                    this.addClassPath(JarUtils.getJarUrl(lib));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void addClassPath(final URL url) throws IOException {
        final URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        try {
            final Method method = sysclass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysloader, url);
        }
        catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error adding " + url + " to system classloader");
        }
    }
    
    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        if (e.getMessage().split(" ")[0].equals("/후원하기")) {
            final String command = e.getMessage().replace("/", "");
            if (command.split(" ").length == 1) {
                e.getPlayer().sendMessage("§6/후원 <후원할 가격> <핀번호> (할 말) §f- 후원을 할 수 있습니다.");
                e.setCancelled(true);
                return;
            }
            else {
                new CallValue(e, "후원").Call();
                this.server.send();
                e.getPlayer().sendMessage("§6후원이 성공적으로 §c접수§6되었습니다.");
                e.getPlayer().sendMessage("§6내용: §f" + command);
            }
            e.setCancelled(true);
        }
        else if (e.getMessage().split(" ")[0].equals("/신고하기")) {
            final String command = e.getMessage().replace("/", "");
            if (command.split(" ").length == 1) {
                e.getPlayer().sendMessage("§6/신고 <내용> §f- 신고를 할 수 있습니다.");
                e.setCancelled(true);
                return;
            }
            new CallValue(e, "신고").Call();
            this.server.send();
            e.getPlayer().sendMessage("§6신고가 성공적으로 §c접수§6되었습니다.");
            e.getPlayer().sendMessage("§6내용: §f" + command);
            e.setCancelled(true);
        }
        else if (e.getMessage().split(" ")[0].equals("/목록삭제")) {
            if (e.getPlayer().isOp()) {
                final String command = e.getMessage().replace("/", "");
                if (command.split(" ").length != 2) {
                    e.getPlayer().sendMessage("§6/목록삭제 번호 §f- 해당 번호의 목록을 삭제합니다.");
                }
                else {
                    CallAdminEntry.io.removeList(command.split(" ")[1], e.getPlayer());
                    this.server.send();
                }
                e.setCancelled(true);
            }
        }
        else if (e.getMessage().split(" ")[0].equals("/키설정")) {
            if (e.getPlayer().isOp()) {
                final String command = e.getMessage().replace("/", "");
                if (command.split(" ").length < 2 || command.split(" ").length > 2) {
                    e.getPlayer().sendMessage("§6/키설정 §f- 앱 접속 키를 설정합니다.");
                }
                else {
                    CallAdminEntry.io.setKey(command.split(" ")[1]);
                    e.getPlayer().sendMessage("§6성공적으로 보안키를 §c" + command.split(" ")[1] + " §6로 설정하였습니다.");
                }
                e.setCancelled(true);
            }
        }
        else if (e.getMessage().split(" ")[0].equals("/포트설정")) {
            if (e.getPlayer().isOp()) {
                final String command = e.getMessage().replace("/", "");
                if (command.split(" ").length < 2 || command.split(" ").length > 2) {
                    e.getPlayer().sendMessage("§6/포트설정 포트 §f- 앱 접속 포트를 설정합니다.");
                }
                else {
                    CallAdminEntry.io.setPort(command.split(" ")[1]);
                    e.getPlayer().sendMessage("§6성공적으로 포트를 §c" + command.split(" ")[1] + " §6로 설정하였습니다.");
                }
                e.setCancelled(true);
            }
        }
        else if (e.getMessage().split(" ")[0].equals("/목록확인") && e.getPlayer().isOp()) {
            final ArrayList<String> list = CallAdminEntry.io.getList();
            for (int i = 0; i < list.size(); ++i) {
                e.getPlayer().sendMessage((String)list.get(i));
            }
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onChat(final PlayerChatEvent event) {
        this.server.sendMessage(String.valueOf(event.getPlayer().getName()) + " : " + event.getMessage());
    }
    
    @EventHandler
    public void joinPlayer(final PlayerJoinEvent event) {
        this.server.playerReset();
    }
    
    @EventHandler
    public void quitPlayer(final PlayerQuitEvent event) {
        this.server.playerReset();
    }
    
    @EventHandler
    public void gmPlayer(final PlayerGameModeChangeEvent event) {
        this.server.playerReset();
    }
    
    public void onDisable() {
        ApplicationServer.close();
    }
}
