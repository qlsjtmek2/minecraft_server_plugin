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
    		player.sendMessage(PhysicalFighters.p + "��c�Ϻ� �ɷ¡�6�� ������ ��c������6�Ǿ����ϴ�.");
    }
}
