// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.IO;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IODataFile
{
    private JSONObject obj;
    private JSONObject key;
    private JSONObject port;
    
    public IODataFile(final JSONObject obj) {
        this.obj = obj;
    }
    
    public IODataFile(final JSONObject data, final JSONArray config) {
        this.obj = data;
        this.key = (JSONObject)config.get(0);
        this.port = (JSONObject)config.get(1);
        this.save();
        this.saveKey();
    }
    
    public void addAdmin(final String uuid, final String regID) {
        final JSONArray uuId = (JSONArray)this.obj.get((Object)"UUID");
        final JSONArray regId = (JSONArray)this.obj.get((Object)"regID");
        for (int i = 0; i < uuId.size(); ++i) {
            if (uuId.get(i).equals(uuid)) {
                return;
            }
        }
        uuId.add((Object)uuid);
        regId.add((Object)regID);
        this.obj.put((Object)"UUID", (Object)uuId);
        this.obj.put((Object)"regID", (Object)regId);
        this.save();
    }
    
    public boolean checkAdmin(final String uuid) {
        final JSONArray uuId = (JSONArray)this.obj.get((Object)"UUID");
        for (int i = 0; i < uuId.size(); ++i) {
            if (uuId.get(i).equals(uuid)) {
                return true;
            }
        }
        return false;
    }
    
    public void addCall(final JSONObject data) {
        final JSONArray list = (JSONArray)this.obj.get((Object)"List");
        list.add((Object)data);
        this.obj.put((Object)"List", (Object)list);
        this.save();
    }
    
    public JSONArray getRegID() {
        final JSONArray regID = (JSONArray)this.obj.get((Object)"regID");
        return regID;
    }
    
    public void removeAdmin(final String uuid) {
        final JSONArray regID = (JSONArray)this.obj.get((Object)"regID");
        final JSONArray uuId = (JSONArray)this.obj.get((Object)"UUID");
        for (int i = 0; i < uuId.size(); ++i) {
            if (uuId.get(i).toString().equals(uuid)) {
                uuId.remove(i);
                regID.remove(i);
                this.obj.put((Object)"UUID", (Object)uuId);
                this.obj.put((Object)"regID", (Object)regID);
                break;
            }
        }
        this.save();
    }
    
    public void removeRegID(final String regid) {
        final JSONArray regID = (JSONArray)this.obj.get((Object)"regID");
        final JSONArray uuId = (JSONArray)this.obj.get((Object)"UUID");
        for (int i = 0; i < regID.size(); ++i) {
            if (regID.get(i).toString().equals(regid)) {
                regID.remove(i);
                uuId.remove(i);
                this.obj.put((Object)"UUID", (Object)uuId);
                this.obj.put((Object)"regID", (Object)regID);
                break;
            }
        }
        this.save();
    }
    
    private void save() {
        final File f = new File("plugins\\Bpo_CallAdmin\\Data.txt");
        try {
            final FileWriter fw = new FileWriter(f);
            fw.write(this.obj.toString());
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void saveKey() {
        final File f = new File("plugins\\Bpo_CallAdmin\\Config.txt");
        try {
            final FileWriter fw = new FileWriter(f);
            final JSONArray arr = new JSONArray();
            arr.add((Object)this.key);
            arr.add((Object)this.port);
            fw.write(arr.toString());
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setKey(final String key) {
        this.key.put((Object)"Key", (Object)key);
        this.saveKey();
    }
    
    public void setPort(final String port) {
        this.port.put((Object)"Port", (Object)port);
        this.saveKey();
    }
    
    public int getPort() {
        return Integer.parseInt(this.port.get((Object)"Port").toString());
    }
    
    public boolean checkKey(final String key) {
        return this.key.get((Object)"Key").toString().equals(key);
    }
    
    public ArrayList<String> getList() {
        final JSONArray list = (JSONArray)this.obj.get((Object)"List");
        final ArrayList<String> lists = new ArrayList<String>();
        for (int i = 0; i < list.size(); ++i) {
            final JSONObject o = (JSONObject)list.get(i);
            lists.add(String.valueOf(i) + ".[" + o.get((Object)"Type").toString() + "]" + o.get((Object)"Player").toString() + " : " + o.get((Object)"Info").toString());
        }
        return lists;
    }
    
    public JSONArray getListJSON() {
        final JSONArray list = (JSONArray)this.obj.get((Object)"List");
        return list;
    }
    
    public void removeList(final String str, final Player p) {
        try {
            final int num = Integer.valueOf(str);
            final JSONArray list = (JSONArray)this.obj.get((Object)"List");
            list.remove(num);
            this.obj.put((Object)"List", (Object)list);
            this.save();
            p.sendMessage("[\ube0c\ud3ec's \uc54c\ub9bc \uc11c\ube44\uc2a4] \uc0ad\uc81c \uc644\ub8cc");
        }
        catch (Exception localException) {
            p.sendMessage("[\ube0c\ud3ec's \uc54c\ub9bc \uc11c\ube44\uc2a4] \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.");
        }
    }
    
    public void removeList(final String str) {
        try {
            final int num = Integer.valueOf(str);
            final JSONArray list = (JSONArray)this.obj.get((Object)"List");
            list.remove(num);
            this.obj.put((Object)"List", (Object)list);
            this.save();
        }
        catch (Exception ex) {}
    }
    
    public String getKey() {
        return this.key.get((Object)"Key").toString();
    }
}
