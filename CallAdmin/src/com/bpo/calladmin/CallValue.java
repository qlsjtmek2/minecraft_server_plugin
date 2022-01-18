// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.calladmin;

import org.json.simple.JSONObject;
import com.bpo.service.GCMService;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CallValue
{
    private String name;
    private String player;
    private String info;
    private String type;
    
    public CallValue(final PlayerCommandPreprocessEvent e, final String type) {
        this.name = e.getPlayer().getName();
        this.info = e.getMessage().substring(5, e.getMessage().length());
        this.type = type;
    }
    
    public boolean Call() {
        CallAdminEntry.io.addCall(this.getObj());
        GCMService.push(this.name, this.type, this.info);
        return false;
    }
    
    public JSONObject getObj() {
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Player", (Object)this.name);
        obj.put((Object)"Info", (Object)this.info);
        obj.put((Object)"Type", (Object)this.type);
        return obj;
    }
    
    public String getObjString() {
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Player", (Object)this.name);
        obj.put((Object)"Info", (Object)this.info);
        obj.put((Object)"Type", (Object)this.type);
        return obj.toString();
    }
}
