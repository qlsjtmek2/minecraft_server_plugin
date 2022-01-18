// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.IO;

import org.json.simple.JSONValue;
import java.io.InputStream;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class CheckKey
{
    private String key;
    private String port;
    private JSONArray arr;
    
    public CheckKey() {
        this.arr = new JSONArray();
    }
    
    public String getKey() {
        return this.key;
    }
    
    public JSONArray getObj() {
        return this.arr;
    }
    
    public boolean run() {
        this.checkDir();
        if (!this.getData()) {
            this.arr = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put((Object)"Key", (Object)"");
            this.arr.add((Object)obj);
            obj = new JSONObject();
            obj.put((Object)"Port", (Object)"2016");
            this.arr.add((Object)obj);
            System.out.println(this.arr.toString());
            return false;
        }
        return true;
    }
    
    private boolean getData() {
        (this.arr = new JSONArray()).add(new JSONObject().put((Object)"Key", (Object)""));
        this.arr.add(new JSONObject().put((Object)"Port", (Object)"2016"));
        final File f = new File("plugins\\Bpo_CallAdmin\\Config.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                String str = "";
                final Scanner s = new Scanner(new FileInputStream(f));
                while (s.hasNext()) {
                    str = String.valueOf(str) + s.nextLine();
                }
                s.close();
                if (str.length() > 3) {
                    this.arr = (JSONArray)JSONValue.parse(str);
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
                (this.arr = new JSONArray()).add(new JSONObject().put((Object)"Key", (Object)""));
                this.arr.add(new JSONObject().put((Object)"Port", (Object)"2016"));
                return false;
            }
        }
        try {
            if (this.arr == null || this.arr.get(0) == null || ((JSONObject)this.arr.get(0)).get((Object)"Key").toString().equals("")) {
                return false;
            }
            this.key = ((JSONObject)this.arr.get(0)).get((Object)"Key").toString();
            if (this.arr == null || this.arr.get(1) == null || ((JSONObject)this.arr.get(1)).get((Object)"Port").toString().length() == 0) {
                this.arr.remove(1);
                this.arr.add(new JSONObject().put((Object)"Port", (Object)"2016"));
                return true;
            }
            this.port = ((JSONObject)this.arr.get(1)).get((Object)"Port").toString();
            return true;
        }
        catch (NullPointerException e3) {
            (this.arr = new JSONArray()).add(new JSONObject().put((Object)"Key", (Object)""));
            this.arr.add(new JSONObject().put((Object)"Port", (Object)"2016"));
            return false;
        }
    }
    
    private void checkDir() {
        final File f = new File("plugins\\Bpo_CallAdmin\\");
        if (!f.exists()) {
            f.mkdirs();
        }
    }
}
