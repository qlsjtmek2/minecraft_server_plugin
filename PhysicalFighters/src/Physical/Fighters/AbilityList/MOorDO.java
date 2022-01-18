// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.Random;
import org.bukkit.entity.Player;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class MOorDO extends AbilityBase
{
    public MOorDO() {
    	this.InitAbility("\ub7ec\uc2dc\uc548 \ub8f0\ub81b", Type.Active_Immediately, Rank.A, "\ucca0\uad34\ub85c \uc0c1\ub300 \ud074\ub9ad\uc2dc 50% \ud655\ub960\ub85c \uc8fd\uc774\uace0 50% \ud655\ub960\ub85c \uc8fd\uc2b5\ub2c8\ub2e4.");
    	this.InitAbility(8, 0, true);
    	EventManager.onEntityDamageByEntity.add(new EventData(this, 0));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        if (!EventManager.DamageGuard && this.PlayerCheck(Event.getDamager()) && this.ItemCheck(ACC.DefaultItem) && Event.getEntity() instanceof Player) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
        final Player p = (Player)Event.getEntity();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            final Player p2 = (Player)Event.getDamager();
            final Random r = new Random();
            final int a = r.nextInt(10);
            if (a == 1 || a == 2 || a == 8 || a == 7 || a == 4) {
                p2.setHealth(0);
                Bukkit.broadcastMessage(PhysicalFighters.p + ChatColor.RED + p2.getDisplayName() + ChatColor.GOLD + "´Ô ²²¼­ ¡×c·¯½Ã¾È ·ê·¿¡×6ÇÏ´Ù°¡ Á×¾ú½À´Ï´Ù");
            }
            else {
                p.setHealth(0);
                Bukkit.broadcastMessage(PhysicalFighters.p + ChatColor.RED + p2.getDisplayName() + ChatColor.GOLD + "´Ô ²²¼­ ·¯½Ã¾È ·ê·¿À¸·Î ¡×c" + p.getDisplayName() + ChatColor.RED + "¡×6´ÔÀ» Á×¿´½À´Ï´Ù.");
            }
        }
    }
}
