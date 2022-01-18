// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Boss;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class Boss
{
    private String name;
    private String entityspawnname;
    private String Timer;
    private int MaxHealth;
    private int Health;
    private LivingEntity entity;
    private int damage;
    private boolean showHp;
    private Location spawnlocation;
    private Location savedlocation;
    private boolean saved;
    private List<String> Items;
    private List<String> Skills;
    private List<Integer> percent;
    private List<String> Effects;
    private String skin;
    private Boolean showtitle;
    private Boolean naturalSpawned;
    
    public Boss(final String newname, final int newmaxhealth, final Location newspawnlocation, final String newentityspawnname, final int newdamage, final boolean newshowHP, final List<String> newItems, final List<String> newSkills, final boolean newshowTitle, final String newskin) {
        this.saved = true;
        this.name = newname;
        this.MaxHealth = newmaxhealth;
        this.Health = newmaxhealth;
        this.damage = newdamage;
        this.showHp = newshowHP;
        this.spawnlocation = newspawnlocation;
        this.savedlocation = newspawnlocation;
        this.entityspawnname = newentityspawnname;
        this.Items = new ArrayList<String>(newItems);
        this.Skills = new ArrayList<String>(newSkills);
        this.percent = new ArrayList<Integer>();
        this.Effects = this.AddSkills();
        this.Timer = "null";
        this.naturalSpawned = false;
        this.showtitle = newshowTitle;
        this.skin = newskin;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public void setDamage(final int i) {
        this.damage = i;
    }
    
    public boolean getShowHp() {
        return this.showHp;
    }
    
    public void setShowHp(final boolean i) {
        this.showHp = i;
    }
    
    public int getMaxHealth() {
        return this.MaxHealth;
    }
    
    public void setMaxHealth(final int i) {
        this.MaxHealth = i;
    }
    
    public int getHealth() {
        return this.Health;
    }
    
    public void sethealth(final int i) {
        this.Health = i;
    }
    
    public void addPercent(final int i) {
        this.percent.add(i);
    }
    
    public boolean hasPercent(final int i) {
        return this.percent.contains(i);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String i) {
        this.name = i;
    }
    
    public boolean getNatural() {
        return this.naturalSpawned;
    }
    
    public void setNatural(final Boolean i) {
        this.naturalSpawned = i;
    }
    
    public LivingEntity getLivingEntity() {
        return this.entity;
    }
    
    public void setEntity(final LivingEntity i) {
        this.entity = i;
    }
    
    public int getId() {
        return this.entity.getEntityId();
    }
    
    public Location getLocation() {
        return this.entity.getLocation();
    }
    
    public Location getWorkingLocation() {
        if (this.saved) {
            return this.savedlocation;
        }
        return this.entity.getLocation();
    }
    
    public Location getSpawnLocation() {
        return this.spawnlocation;
    }
    
    public void setSavedLocation(final Location l) {
        this.savedlocation = l;
    }
    
    public Location getSavedLocation() {
        return this.savedlocation;
    }
    
    public void setSaved(final boolean i) {
        this.saved = i;
    }
    
    public boolean getSaved() {
        return this.saved;
    }
    
    public List<String> getItems() {
        return this.Items;
    }
    
    public List<String> getSkill() {
        return this.Skills;
    }
    
    public void setRemoveSkill(final int i) {
        this.Skills.set(i, "null");
    }
    
    public String getEntitySpawnName() {
        return this.entityspawnname;
    }
    
    public void setTimer(final String name) {
        this.Timer = name;
    }
    
    public String getTimer() {
        return this.Timer;
    }
    
    public List<String> getEffects() {
        return this.Effects;
    }
    
    public String getSkinUrl() {
        return this.skin;
    }
    
    public Boss setSkinUrl(final String skin) {
        this.skin = skin;
        return this;
    }
    
    public boolean isTitleShowed() {
        return this.showtitle;
    }
    
    public Boss showTitle(final Boolean show) {
        this.showtitle = show;
        return this;
    }
    
    private List<String> AddSkills() {
        final List<String> skills = new ArrayList<String>();
        for (final String s : this.getSkill()) {
            final String[] Parts = s.split(" ");
            if (Parts[0].equalsIgnoreCase("effect")) {
                skills.add(Parts[1]);
            }
        }
        return skills;
    }
}
