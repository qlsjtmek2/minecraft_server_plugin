// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MajorModule;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.TimerBase;

public final class DurationTimer extends TimerBase
{
    private AbilityBase ab;
    private CoolDownTimer ctimer;
    PhysicalFighters M;
    
    public DurationTimer(final AbilityBase ab, final CoolDownTimer ctimer) {
        this.ab = ab;
        this.ctimer = ctimer;
    }
    
    @Override
    public void EventStartTimer() {
        this.ab.A_DurationStart();
    }
    
    @Override
    public void EventRunningTimer(final int count) {
        if ((count <= 3 && count >= 1 && this.ab.GetShowText() == AbilityBase.ShowText.All_Text) || this.ab.GetShowText() == AbilityBase.ShowText.No_CoolDownText) {
            this.ab.GetPlayer().sendMessage(String.format(PhysicalFighters.a + "지속 시간 §a%d초 §f전", count));
        }
    }
    
    @Override
    public void EventEndTimer() {
        this.ab.A_DurationEnd();
        if (this.ab.GetShowText() != AbilityBase.ShowText.Custom_Text) {
            this.ab.GetPlayer().sendMessage(ACC.DefaultDurationEnd);
        }
        this.ctimer.StartTimer(this.ab.GetCoolDown(), true);
    }
    
    @Override
    public void FinalEventEndTimer() {
        this.ab.A_FinalDurationEnd();
    }
}
