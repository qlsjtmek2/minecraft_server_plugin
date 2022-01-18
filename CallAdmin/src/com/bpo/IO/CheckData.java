// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.IO;

import java.io.FileNotFoundException;
import org.json.simple.JSONValue;
import java.io.InputStream;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.simple.JSONArray;
import java.io.File;
import org.json.simple.JSONObject;

public class CheckData
{
    private JSONObject obj;
    
    public CheckData() {
        this.obj = new JSONObject();
    }
    
    public JSONObject getObj() {
        return this.obj;
    }
    
    public void run() {
        final File f = new File("plugins\\Bpo_CallAdmin\\Data.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
                this.obj.put((Object)"UUID", (Object)new JSONArray());
                this.obj.put((Object)"regID", (Object)new JSONArray());
                this.obj.put((Object)"List", (Object)new JSONArray());
                return;
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            final Scanner s = new Scanner(new FileInputStream(f));
            String str = "";
            while (s.hasNext()) {
                str = String.valueOf(str) + s.nextLine();
            }
            this.obj = (JSONObject)JSONValue.parse(str);
            if (str.length() < 3 || this.obj == null || this.obj.get((Object)"UUID") == null) {
                (this.obj = new JSONObject()).put((Object)"UUID", (Object)new JSONArray());
                this.obj.put((Object)"regID", (Object)new JSONArray());
                this.obj.put((Object)"List", (Object)new JSONArray());
            }
        }
        catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }
}
