// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.TreeMap;

public class GameRules
{
    private TreeMap a;
    
    public GameRules() {
        this.a = new TreeMap();
        this.a("doFireTick", "true");
        this.a("mobGriefing", "true");
        this.a("keepInventory", "false");
        this.a("doMobSpawning", "true");
        this.a("doMobLoot", "true");
        this.a("doTileDrops", "true");
        this.a("commandBlockOutput", "true");
    }
    
    public void a(final String s, final String s2) {
        this.a.put(s, new GameRuleValue(s2));
    }
    
    public void set(final String s, final String s2) {
        final GameRuleValue gameRuleValue = this.a.get(s);
        if (gameRuleValue != null) {
            gameRuleValue.a(s2);
        }
        else {
            this.a(s, s2);
        }
    }
    
    public String get(final String s) {
        final GameRuleValue gameRuleValue = this.a.get(s);
        if (gameRuleValue != null) {
            return gameRuleValue.a();
        }
        return "";
    }
    
    public boolean getBoolean(final String s) {
        final GameRuleValue gameRuleValue = this.a.get(s);
        return gameRuleValue != null && gameRuleValue.b();
    }
    
    public NBTTagCompound a() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound("GameRules");
        for (final String s : this.a.keySet()) {
            nbtTagCompound.setString(s, ((GameRuleValue)this.a.get(s)).a());
        }
        return nbtTagCompound;
    }
    
    public void a(final NBTTagCompound nbtTagCompound) {
        for (final NBTBase nbtBase : nbtTagCompound.c()) {
            this.set(nbtBase.getName(), nbtTagCompound.getString(nbtBase.getName()));
        }
    }
    
    public String[] b() {
        return (String[])this.a.keySet().toArray(new String[0]);
    }
    
    public boolean e(final String s) {
        return this.a.containsKey(s);
    }
}
