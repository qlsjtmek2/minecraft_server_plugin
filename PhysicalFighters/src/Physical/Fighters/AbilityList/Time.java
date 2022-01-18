// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.Script.MainScripter;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Time extends AbilityBase
{
    public Time() {
        if (!PhysicalFighters.Specialability) {
            this.InitAbility("\ud0c0\uc784", Type.Active_Continue, Rank.A, "\uc790\uc2e0\uc744 \uc81c\uc678\ud55c \ubaa8\ub4e0 \ub2a5\ub825\uc790\uc758 \uc774\ub3d9\uc744 5\ucd08\ub3d9\uc548", "\ucc28\ub2e8\ud569\ub2c8\ub2e4. \ub2e8, \uc9c1\uc811\uc801\uc778 \uc774\ub3d9\ub9cc \ubd88\uac00\ub2a5\ud569\ub2c8\ub2e4.", "\ub2a5\ub825\uc774 \uc5c6\ub294 \uc0ac\ub78c\ub3c4 \ub2e4 \uba48\ucda5\ub2c8\ub2e4.");
            this.InitAbility(40, 5, true);
            this.RegisterLeftClickEvent();
            EventManager.onPlayerMoveEvent.add(new EventData(this));
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerMoveEvent Event = (PlayerMoveEvent)event;
        if (!this.PlayerCheck(Event.getPlayer()) && Event.getPlayer().getWorld().getName().equalsIgnoreCase("world_pvp")) {
            Event.setCancelled(true);
        }
    }
    
    @Override
    public void A_DurationStart() {
        for (Player player : MainScripter.PlayerList)
        	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이 §bTime §6능력을 사용했습니다.", this.GetPlayer().getName()));
    }
    
    @Override
    public void A_DurationEnd() {
        for (Player player : MainScripter.PlayerList)
        	player.sendMessage(String.format(PhysicalFighters.p + "Time 능력이 §c해제§6 되었습니다.", new Object[0]));
    }
}
