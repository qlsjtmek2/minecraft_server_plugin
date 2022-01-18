// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Spawning;

import me.ThaH3lper.com.LoadBosses.LoadBoss;

public class Spawnings
{
    public String Replace;
    public String worlds;
    public String Biomes;
    public LoadBoss loadBoss;
    public Float chance;
    public boolean remove;
    public int limit;
    
    public Spawnings(final LoadBoss loadBoss, final String Replace, final Float chance, final String worlds, final boolean remove, final int limit, final String Biomes) {
        this.Replace = Replace;
        this.worlds = worlds;
        this.Biomes = Biomes;
        this.chance = chance;
        this.loadBoss = loadBoss;
        this.remove = remove;
        this.limit = limit;
    }
}
