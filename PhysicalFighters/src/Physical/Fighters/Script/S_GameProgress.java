// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.Script;

import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import org.bukkit.entity.Player;

import Physical.Fighters.MinerModule.TimerBase;
import Physical.Fighters.PhysicalFighters;

public class S_GameProgress
{
    private MainScripter ms;
    private S_ScriptTimer stimer;
    private int EarlyInvincibleTime;
    private boolean gcon;
    PhysicalFighters M;
    
    public S_GameProgress(final MainScripter ms) {
        this.stimer = new S_ScriptTimer();
        this.EarlyInvincibleTime = 300;
        this.gcon = false;
        this.ms = ms;
        this.EarlyInvincibleTime = PhysicalFighters.EarlyInvincibleTime * 60;
    }
    
    public void GameProgress() {
        this.stimer.StartTimer(99999999);
    }
    
    public void GameProgressStop() {
        this.gcon = false;
        this.stimer.StopTimer();
    }
    
    public final class S_ScriptTimer extends TimerBase
    {
        @Override
        public void EventStartTimer() {
        }
        
        @Override
        public void EventRunningTimer(final int count) {
            if (count > 20 && count % 15 == 0) {
                S_GameProgress.this.ms.gameworld.setStorm(false);
                if (S_GameProgress.this.gcon) {
                    System.gc();
                }
            }
            if (PhysicalFighters.Invincibility && S_GameProgress.this.EarlyInvincibleTime > 60 && S_GameProgress.this.EarlyInvincibleTime - 60 == count) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "초반 무적이 §c1분 §6후 §c해제§6됩니다.");
            }
            if (PhysicalFighters.Invincibility && S_GameProgress.this.EarlyInvincibleTime - 5 == count) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§c5초 §6후 초반무적이 §c해제§6됩니다.");
            }
            if (PhysicalFighters.Invincibility && S_GameProgress.this.EarlyInvincibleTime - 4 == count) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§c4초 §6후 초반무적이 §c해제§6됩니다.");
            }
            if (PhysicalFighters.Invincibility && S_GameProgress.this.EarlyInvincibleTime - 3 == count) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§c3초 §6후 초반무적이 §c해제§6됩니다.");
            }
            if (PhysicalFighters.Invincibility && S_GameProgress.this.EarlyInvincibleTime - 2 == count) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§c2초 §6후 초반무적이 §c해제§6됩니다.");
            }
            if (PhysicalFighters.Invincibility && S_GameProgress.this.EarlyInvincibleTime - 1 == count) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "§c1초 §6후 초반무적이 §c해제§6됩니다.");
            }
            if (PhysicalFighters.Invincibility && S_GameProgress.this.EarlyInvincibleTime == count) {
                for (Player player : MainScripter.PlayerList)
                	player.sendMessage(PhysicalFighters.p + "초반 무적이 §c해제§6되었습니다. 이제 §c데미지§6를 입습니다.");
                EventManager.DamageGuard = false;
                AbilityBase.restrictionTimer.StartTimer();
            }
        }
        
        @Override
        public void EventEndTimer() {
        }
    }
}
