// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Timer;

import me.ThaH3lper.com.EpicBoss;

public class Timer
{
    private int time;
    private int maxtime;
    private String name;
    private String location;
    private String boss;
    private String spawnmsg;
    private EpicBoss eb;
    
    public Timer(final String newname, final String newboss, final String newlocation, final int newtime, final EpicBoss neweb) {
        this.maxtime = newtime;
        this.boss = newboss;
        this.time = 0;
        this.spawnmsg = "";
        this.name = newname;
        this.location = newlocation;
        this.eb = neweb;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getMaxTime() {
        return this.maxtime;
    }
    
    public int getTime() {
        return this.time;
    }
    
    public void setTime(final int ntime) {
        this.time = ntime;
    }
    
    public void setText(final String newmsg) {
        this.spawnmsg = newmsg;
    }
    
    public String getText() {
        return this.spawnmsg;
    }
    
    public String getBossName() {
        return this.boss;
    }
    
    public String getLocationStr() {
        return this.location;
    }
    
    public void lower() {
        if (this.time <= 0 && this.time >= -9) {
            this.eb.timerstuff.spawndeath(this);
            this.time = -10;
        }
        else if (this.time != -10) {
            this.time -= 5;
        }
    }
}
