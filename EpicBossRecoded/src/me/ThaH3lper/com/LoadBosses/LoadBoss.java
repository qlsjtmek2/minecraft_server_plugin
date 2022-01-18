// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.LoadBosses;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class LoadBoss
{
    public String Name;
    public String Type;
    public int Health;
    public int Damage;
    boolean Showhp;
    public List<String> Items;
    public List<String> Skillslist;
    boolean showtitle;
    String skin;
    
    public LoadBoss(final String newName, final String newType, final int newHealth, final int newDamage, final List<String> newItems, final boolean newShowhp, final List<String> newskills, final String Skin, final Boolean Showtitle) {
        this.showtitle = false;
        this.Name = newName;
        this.Type = newType;
        this.Health = newHealth;
        this.Damage = newDamage;
        this.Items = new ArrayList<String>(newItems);
        this.Showhp = newShowhp;
        this.Skillslist = new ArrayList<String>(newskills);
        this.skin = Skin;
        this.showtitle = Showtitle;
    }
    
    public String getName() {
        return this.Name;
    }
    
    public String getType() {
        return this.Type;
    }
    
    public int getHealth() {
        return this.Health;
    }
    
    public int getDamage() {
        return this.Damage;
    }
    
    public List<String> getItems() {
        return this.Items;
    }
    
    public List<String> getSkills() {
        return this.Skillslist;
    }
    
    public boolean getShowhp() {
        return this.Showhp;
    }
    
    public boolean getShowtitle() {
        return this.showtitle;
    }
    
    public String getSkin() {
        return this.skin;
    }
}
