// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.ChatColor;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Bread extends AbilityBase
{
    int Ang;
    int health;
    
    public Bread() {
        this.Ang = 0;
        this.health = -1;
        this.InitAbility("\ube0c\ub79c\ub4dc", Type.Active_Immediately, Rank.S, "\ud654\uc5fc \ub370\ubbf8\uc9c0\ub97c \uc785\uc744 \ub54c \ub9c8\ub2e4 \uac8c\uc774\uc9c0\uac00 \uc313\uc785\ub2c8\ub2e4.", "\ucca0\uad34\ub85c \uc0c1\ub300 \ud0c0\uaca9\uc2dc \ubd84\ub178\uac8c\uc774\uc9c0\uc758 1/5 \ub9cc\ud07c \ub370\ubbf8\uc9c0\ub97c \uc90d\ub2c8\ub2e4.", "\ucca0\uad34 \uc6b0\ud074\ub9ad\uc2dc \ubd84\ub178\uac8c\uc774\uc9c0\ub97c \ubcfc \uc218 \uc788\uc2b5\ub2c8\ub2e4.");
        this.InitAbility(5, 0, true);
        this.RegisterLeftClickEvent();
        EventManager.onEntityDamage.add(new EventData(this, 1));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final PlayerInteractEvent e = (PlayerInteractEvent)event;
                if (this.PlayerCheck(e.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                    final Player p = e.getPlayer();
                    p.sendMessage(PhysicalFighters.a + "분노게이지: §c" + ChatColor.WHITE + this.Ang);
                    break;
                }
                break;
            }
            case 1: {
                final EntityDamageEvent Event = (EntityDamageEvent)event;
                if (!this.PlayerCheck(Event.getEntity()) || !(Event.getEntity() instanceof Player) || Event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    break;
                }
                final Player p2 = (Player)Event.getEntity();
                if (p2.getHealth() == this.health) {
                    break;
                }
                this.health = p2.getHealth();
                if (this.Ang < 50) {
                    this.Ang += Event.getDamage();
                }
                if (this.Ang >= 50) {
                    this.Ang = 50;
                    break;
                }
                break;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
        final int d = this.Ang / 5;
        Event1.setDamage(d);
        this.Ang = 0;
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        this.Ang = 0;
    }
}
