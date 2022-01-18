// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MinerModule;

import java.util.TimerTask;
import org.bukkit.Bukkit;
import java.util.Timer;

public abstract class TimerBase
{
    private Timer timer;
    private boolean Running;
    private boolean ReverseTimer;
    private int Count;
    private int MaxCount;
    
    public TimerBase() {
        this.Running = false;
        this.ReverseTimer = false;
        this.Count = 0;
        this.MaxCount = 0;
    }
    
    public abstract void EventStartTimer();
    
    public abstract void EventRunningTimer(final int p0);
    
    public abstract void EventEndTimer();
    
    public void FinalEventEndTimer() {
    }
    
    public final void SetTimerData(final int MaxCount, final boolean Reverse) {
        this.MaxCount = MaxCount;
        this.ReverseTimer = Reverse;
    }
    
    public final int GetCount() {
        return this.Count;
    }
    
    public final void SetCount(final int c) {
        this.Count = c;
    }
    
    public final boolean GetTimerRunning() {
        return this.Running;
    }
    
    public final void StartTimer() {
        this.StartTimer(this.MaxCount, this.ReverseTimer);
    }
    
    public final void StartTimer(final int MaxCount) {
        this.StartTimer(MaxCount, false);
    }
    
    public final void StartTimer(final int MaxCount, final boolean Reverse) {
        if (MaxCount < 0) {
            Bukkit.broadcastMessage("Error : TimerBase : \uce74\uc6b4\ud2b8\uac00 0\ubcf4\ub2e4 \uc791\uc2b5\ub2c8\ub2e4.");
            return;
        }
        (this.timer = new Timer()).schedule(new CustomTimerTask(), 0L, 1000L);
        this.Running = true;
        this.SetTimerData(MaxCount, Reverse);
        this.Count = 0;
        if (Reverse) {
            this.Count = MaxCount;
        }
        this.EventStartTimer();
    }
    
    public final void EndTimer() {
        this.StopTimer();
        this.EventEndTimer();
    }
    
    public final void StopTimer() {
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.Count = 0;
        this.Running = false;
        this.FinalEventEndTimer();
    }
    
    static /* synthetic */ void access$2(final TimerBase timerBase, final int count) {
        timerBase.Count = count;
    }
    
    private final class CustomTimerTask extends TimerTask
    {
        @Override
        public void run() {
            TimerBase.this.EventRunningTimer(TimerBase.this.Count);
            if (TimerBase.this.ReverseTimer) {
                if (TimerBase.this.Count <= 0) {
                    TimerBase.this.EndTimer();
                    return;
                }
                final TimerBase this$0 = TimerBase.this;
                TimerBase.access$2(this$0, this$0.Count - 1);
            }
            else {
                if (TimerBase.this.Count >= TimerBase.this.MaxCount) {
                    TimerBase.this.EndTimer();
                    return;
                }
                final TimerBase this$2 = TimerBase.this;
                TimerBase.access$2(this$2, this$2.Count + 1);
            }
        }
    }
}
