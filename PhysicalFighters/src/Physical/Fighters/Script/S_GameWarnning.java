// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.Script;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MajorModule.AbilityList;
import org.bukkit.entity.Player;

import Physical.Fighters.MinerModule.TimerBase;

public class S_GameWarnning
{
    private S_ScriptTimer stimer;
    @SuppressWarnings("unused")
	private MainScripter ms;
    PhysicalFighters M;
    
    public S_GameWarnning(final MainScripter ms) {
        this.stimer = new S_ScriptTimer();
        this.ms = ms;
    }
    
    public void GameWarnningStart() {
        this.stimer.StartTimer(99999999);
    }
    
    public void GameWarnningStop() {
        this.stimer.EndTimer();
    }
    
    public final class S_ScriptTimer extends TimerBase
    {
        @Override
        public void EventStartTimer() {
        }
        
        @Override
        public void EventRunningTimer(final int count) {
            if (count >= 20 && count % 20 == 0) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "�ɷ��� Ȯ������ ������ ���� ��e[ /va yes, /va no ] ��6��ɾ�� �ɷ��� ��cȮ����6�ϼ���.");
                for (int l = 0; l < AbilityList.AbilityList.size(); ++l) {
                    if (AbilityList.AbilityList.get(l).GetPlayer() != null) {
                        final AbilityBase tempab = AbilityList.AbilityList.get(l);
                        if (!MainScripter.OKSign.contains(tempab.GetPlayer())) {
                            tempab.GetPlayer().sendMessage(PhysicalFighters.a + "��c����� �ɷ��� �ùٸ��� Ȯ������ �ʾҽ��ϴ�.");
                        }
                    }
                }
            }
        }
        
        @Override
        public void EventEndTimer() {
        }
    }
}
