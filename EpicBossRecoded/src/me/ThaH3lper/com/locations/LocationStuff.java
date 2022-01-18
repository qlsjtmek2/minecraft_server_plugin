// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.locations;

import org.bukkit.entity.Player;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import me.ThaH3lper.com.EpicBoss;

public class LocationStuff
{
    EpicBoss eb;
    
    public LocationStuff(final EpicBoss neweb) {
        this.eb = neweb;
        this.loadLocation();
        this.saveLocation();
    }
    
    public void addLocation(final String name, final Location l) {
        this.eb.LocationList.add(new Locations(l, name, l.getWorld().getName()));
        this.saveLocation();
    }
    
    public void removeLocation(final String name) {
        if (this.eb.LocationList != null) {
            for (int i = 0; i < this.eb.LocationList.size(); ++i) {
                if (this.eb.LocationList.get(i).getName().equals(name)) {
                    this.eb.LocationList.remove(i);
                    this.saveLocation();
                }
            }
        }
    }
    
    public void loadLocation() {
        if (this.eb.SavedData.getCustomConfig().contains("Location") && this.eb.SavedData.getCustomConfig().getStringList("Location") != null) {
            for (final String s : this.eb.SavedData.getCustomConfig().getStringList("Location")) {
                final String[] Splits = s.split(":");
                final String name = Splits[0];
                final Location l = new Location(Bukkit.getWorld(Splits[1]), Double.parseDouble(Splits[2]), Double.parseDouble(Splits[3]), Double.parseDouble(Splits[4]));
                this.eb.LocationList.add(new Locations(l, name, Splits[1]));
            }
        }
    }
    
    public void saveLocation() {
        if (this.eb.LocationList != null) {
            final List<String> saved = new ArrayList<String>();
            for (final Locations loc : this.eb.LocationList) {
                final String save = String.valueOf(loc.getName()) + ":" + loc.getWorldName() + ":" + (int)loc.getLocation().getX() + ":" + (int)loc.getLocation().getY() + ":" + (int)loc.getLocation().getZ();
                saved.add(save);
            }
            this.eb.SavedData.reloadCustomConfig();
            this.eb.SavedData.getCustomConfig().set("Location", (Object)saved);
            this.eb.SavedData.saveCustomConfig();
        }
    }
    
    public boolean locationExict(final String name) {
        if (this.eb.LocationList != null) {
            for (final Locations loc : this.eb.LocationList) {
                if (loc.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Player getPlayer(final String name) {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    public Locations getLocations(final String name) {
        if (this.eb.LocationList != null) {
            for (final Locations loc : this.eb.LocationList) {
                if (loc.getName().equals(name)) {
                    return loc;
                }
            }
        }
        return null;
    }
}
