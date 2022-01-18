// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MajorModule;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.TimerBase;

public final class CoolDownTimer extends TimerBase
{
    private AbilityBase ab;
    PhysicalFighters M;
    
    public CoolDownTimer(final AbilityBase ab) {
        this.ab = ab;
    }
    
    @Override
    public void EventStartTimer() {
        this.ab.A_CoolDownStart();
    }
    
    @Override
    public void EventRunningTimer(final int count) {
        if ((count <= 3 && count >= 1 && this.ab.GetShowText() == AbilityBase.ShowText.All_Text) || this.ab.GetShowText() == AbilityBase.ShowText.No_DurationText) {
            this.ab.GetPlayer().sendMessage(String.format(PhysicalFighters.a + "§c%d초 §f후 §b능력§f을 사용하실 수 있습니다.", count));
        }
    }
    
    @Override
    public void EventEndTimer() {
        this.ab.A_CoolDownEnd();
        if (this.ab.GetShowText() != AbilityBase.ShowText.Custom_Text) {
            this.ab.GetPlayer().sendMessage(ACC.DefaultCoolDownEnd);
        }
    }
}
