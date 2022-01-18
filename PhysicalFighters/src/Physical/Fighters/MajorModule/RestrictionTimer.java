// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MajorModule;

import org.bukkit.entity.Player;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MinerModule.TimerBase;
import Physical.Fighters.Script.MainScripter;

public final class RestrictionTimer extends TimerBase
{
	PhysicalFighters M;
	
    @Override
    public void EventStartTimer() {
    }
    
    @Override
    public void EventRunningTimer(final int count) {
    }
    
    @Override
    public void EventEndTimer() {
    	for (Player player : MainScripter.PlayerList)
    		player.sendMessage(PhysicalFighters.p + "§c일부 능력§6의 제약이 §c해제§6되었습니다.");
    }
}
