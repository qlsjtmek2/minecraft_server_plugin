// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.entity.Player;

public class PlayerDeathEvent extends EntityDeathEvent
{
    private int newExp;
    private String deathMessage;
    private int newLevel;
    private int newTotalExp;
    private boolean keepLevel;
    
    public PlayerDeathEvent(final Player player, final List<ItemStack> drops, final int droppedExp, final String deathMessage) {
        this(player, drops, droppedExp, 0, deathMessage);
    }
    
    public PlayerDeathEvent(final Player player, final List<ItemStack> drops, final int droppedExp, final int newExp, final String deathMessage) {
        this(player, drops, droppedExp, newExp, 0, 0, deathMessage);
    }
    
    public PlayerDeathEvent(final Player player, final List<ItemStack> drops, final int droppedExp, final int newExp, final int newTotalExp, final int newLevel, final String deathMessage) {
        super(player, drops, droppedExp);
        this.newExp = 0;
        this.deathMessage = "";
        this.newLevel = 0;
        this.newTotalExp = 0;
        this.keepLevel = false;
        this.newExp = newExp;
        this.newTotalExp = newTotalExp;
        this.newLevel = newLevel;
        this.deathMessage = deathMessage;
    }
    
    public Player getEntity() {
        return (Player)this.entity;
    }
    
    public void setDeathMessage(final String deathMessage) {
        this.deathMessage = deathMessage;
    }
    
    public String getDeathMessage() {
        return this.deathMessage;
    }
    
    public int getNewExp() {
        return this.newExp;
    }
    
    public void setNewExp(final int exp) {
        this.newExp = exp;
    }
    
    public int getNewLevel() {
        return this.newLevel;
    }
    
    public void setNewLevel(final int level) {
        this.newLevel = level;
    }
    
    public int getNewTotalExp() {
        return this.newTotalExp;
    }
    
    public void setNewTotalExp(final int totalExp) {
        this.newTotalExp = totalExp;
    }
    
    public boolean getKeepLevel() {
        return this.keepLevel;
    }
    
    public void setKeepLevel(final boolean keepLevel) {
        this.keepLevel = keepLevel;
    }
}
