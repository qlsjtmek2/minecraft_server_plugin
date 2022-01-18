// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.service;

import java.io.IOException;
import com.bpo.secure.AES128Cipher;
import java.io.DataOutputStream;
import com.bpo.calladmin.CallAdminEntry;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import java.net.Socket;

public class Widget
{
    public void work(final Socket client) {
        final JSONObject obj = new JSONObject();
        int playersOnline = 0;
        try {
            if (Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).getReturnType() == Collection.class) {
                playersOnline = ((Collection)Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0])).size();
            }
            else {
                playersOnline = ((Player[])Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0])).length;
            }
        }
        catch (NoSuchMethodException ex) {}
        catch (InvocationTargetException ex2) {}
        catch (IllegalAccessException ex3) {}
        obj.put((Object)"List", (Object)("\ubaa9\ub85d :" + CallAdminEntry.io.getList().size() + "\uba85"));
        obj.put((Object)"Player", (Object)(String.valueOf(playersOnline) + "/" + Bukkit.getMaxPlayers()));
        obj.put((Object)"Memory", (Object)ReadInfo.memoryString());
        try {
            new DataOutputStream(client.getOutputStream()).writeUTF(AES128Cipher.encrypt(obj.toString()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
