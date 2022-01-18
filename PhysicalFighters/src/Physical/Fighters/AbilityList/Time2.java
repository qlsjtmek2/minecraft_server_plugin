// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.Script.MainScripter;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Time2 extends AbilityBase
{
    public Time2() {
        if (!PhysicalFighters.Specialability) {
            this.InitAbility("\uc2dc\uac04\uc744 \uc9c0\ubc30\ud558\ub294 \uc790", Type.Active_Immediately, Rank.A, "\uc790\uc2e0\uc744 \uc81c\uc678\ud55c \ubaa8\ub4e0 \ud50c\ub808\uc774\uc5b4\ub4e4\uc758 \uc18d\ub3c4\ub97c 15\ucd08\uac04 \ub290\ub9ac\uac8c \ub9cc\ub4ed\ub2c8\ub2e4.");
            this.InitAbility(40, 0, true);
            this.RegisterLeftClickEvent();
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
        for (Player player : MainScripter.PlayerList) {
        	player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2), true);
        	player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2), false);
        	player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2), false);
        	player.sendMessage(PhysicalFighters.a + "§c시간§f을 지배하는 자에의해 §e15초§f간 당신의 시간이 느리게 흘러갑니다.");
        }
    }
}
