// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import Physical.Fighters.MajorModule.AbilityList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.Script.MainScripter;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Mirroring extends AbilityBase
{
    public Mirroring() {
        if (!PhysicalFighters.Specialability) {
            this.InitAbility("\ubbf8\ub7ec\ub9c1", Type.Passive_Manual, Rank.SSS, "\ub2f9\uc2e0\uc744 \uc8fd\uc778 \uc0ac\ub78c\uc744 \ud568\uaed8 \uc800\uc2b9\uc73c\ub85c \ub04c\uace0\uac11\ub2c8\ub2e4.", "\uc790\uc2e0\uc774 \uc8fd\uc744\uacbd\uc6b0 \uc8fd\uc778 \uc0ac\ub78c \uc5ed\uc2dc \uc8fd\uac8c\ub429\ub2c8\ub2e4.", "\ub370\uc2a4\ub178\ud2b8\ub294 \uc774 \ub2a5\ub825\uc5d0 \uc8fd\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDeath.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final EntityDeathEvent Event = (EntityDeathEvent)event;
        if (Event.getEntity().getKiller() instanceof Player && this.PlayerCheck((Entity)Event.getEntity())) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final EntityDeathEvent Event = (EntityDeathEvent)event;
        final Player p = (Player)Event.getEntity();
        if (!p.getWorld().getName().equalsIgnoreCase("world")) {
            for (Player player : MainScripter.PlayerList)
            	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님의 §c미러링 §6능력이 발동되었습니다.", p.getName()));
            if (AbilityList.assimilation.GetPlayer() == p.getKiller()) {
                AbilityList.assimilation.A_Effect((Event)Event, 1);
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "미러링 능력이§c 무력화 §6되었습니다.");
                return;
            }
            if (AbilityList.aegis.GetPlayer() == p.getKiller() && AbilityList.aegis.GetDurationState()) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "미러링 능력이 §f무력화§6 되었습니다.");
                return;
            }
            for (Player player : MainScripter.PlayerList)
            	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님의 능력에 의해 §c%s §6님이 죽었습니다.", p.getName(), p.getKiller().getName()));
            p.getKiller().damage(5000);
        }
    }
}
