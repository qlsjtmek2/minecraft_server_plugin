// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.service;

import java.util.Iterator;
import org.bukkit.GameMode;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ReadInfo
{
    public static JSONObject getPlayers() {
        final JSONArray arr = new JSONArray();
        int playersOnline = 0;
        int check = -1;
        Collection<?> list = null;
        Player[] player = null;
        try {
            if (Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).getReturnType() == Collection.class) {
                playersOnline = ((Collection)Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0])).size();
                list = (Collection<?>)Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                check = 1;
            }
            else {
                playersOnline = ((Player[])Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0])).length;
                player = (Player[])Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                check = 2;
            }
        }
        catch (NoSuchMethodException ex) {}
        catch (InvocationTargetException ex2) {}
        catch (IllegalAccessException ex3) {}
        arr.add((Object)(String.valueOf(playersOnline) + "/" + Bukkit.getMaxPlayers()));
        final JSONObject playerObj = new JSONObject();
        if (check == 1) {
            for (final Object p : list) {
                final Player ps = (Player)p;
                playerObj.put((Object)"Name", (Object)ps.getName());
                if (ps.getGameMode() == GameMode.CREATIVE) {
                    playerObj.put((Object)"GameMode", (Object)true);
                }
                else {
                    playerObj.put((Object)"GameMode", (Object)false);
                }
                if (ps.isOp()) {
                    playerObj.put((Object)"OP", (Object)true);
                }
                else {
                    playerObj.put((Object)"OP", (Object)false);
                }
                arr.add((Object)playerObj);
            }
        }
        else if (check == 2) {
            Player[] array;
            for (int length = (array = player).length, i = 0; i < length; ++i) {
                final Player p2 = array[i];
                playerObj.put((Object)"Name", (Object)p2.getName());
                if (p2.getGameMode() == GameMode.CREATIVE) {
                    playerObj.put((Object)"GameMode", (Object)true);
                }
                else {
                    playerObj.put((Object)"GameMode", (Object)false);
                }
                if (p2.isOp()) {
                    playerObj.put((Object)"OP", (Object)true);
                }
                else {
                    playerObj.put((Object)"OP", (Object)false);
                }
                arr.add((Object)playerObj);
            }
        }
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Command", (Object)"Players");
        obj.put((Object)"Data", (Object)arr);
        return obj;
    }
    
    public static JSONObject memory() {
        final Runtime runtime = Runtime.getRuntime();
        final String str = String.valueOf((runtime.totalMemory() - runtime.freeMemory()) / 1048576L) + " MB / " + runtime.totalMemory() / 1048576L + " MB";
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Command", (Object)"Memory");
        obj.put((Object)"Persent", (Object)str);
        return obj;
    }
    
    public static String memoryString() {
        final Runtime runtime = Runtime.getRuntime();
        final String str = String.valueOf((runtime.totalMemory() - runtime.freeMemory()) / 1048576L) + "MB";
        return str;
    }
    
    public static JSONObject GetServer() {
        final String name = Bukkit.getServer().getName().toString();
        final String motd = Bukkit.getServer().getMotd().toString();
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Command", (Object)"Server");
        obj.put((Object)"Name", (Object)name);
        obj.put((Object)"Motd", (Object)motd);
        return obj;
    }
}
