// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MainModule.AbilityBase;

public class Clocking extends AbilityBase
{
    public Clocking() {
        this.InitAbility("\ud074\ub85c\ud0b9", Type.Active_Continue, Rank.A, "\ub2a5\ub825 \uc0ac\uc6a9\uc2dc \uc77c\uc815\uc2dc\uac04\ub3d9\uc548 \ub2e4\ub978 \uc0ac\ub78c\uc5d0\uac8c \ubcf4\uc774\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.", "\ud074\ub85c\ud0b9 \uc0c1\ud0dc\uc5d0\uc11c\ub294 \ud0c0\uc778\uc5d0\uac8c \uacf5\uaca9 \ubc1b\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
        this.InitAbility(35, 5, true);
        this.RegisterLeftClickEvent();
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
    public void A_DurationStart() {
        final Player[] List = Bukkit.getOnlinePlayers();
        Player[] array;
        for (int length = (array = List).length, i = 0; i < length; ++i) {
            final Player p = array[i];
            p.hidePlayer(this.GetPlayer());
        }
    }
    
    @Override
    public void A_FinalDurationEnd() {
        if (this.GetPlayer() != null) {
            final Player[] List = Bukkit.getOnlinePlayers();
            if (List != null && List.length != 0) {
                Player[] array;
                for (int length = (array = List).length, i = 0; i < length; ++i) {
                    final Player p = array[i];
                    p.showPlayer(this.GetPlayer());
                }
            }
        }
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
    }
}
