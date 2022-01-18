// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MinerModule;

import Physical.Fighters.MainModule.AbilityBase;

public class EventData
{
    public AbilityBase ab;
    public int parameter;
    
    public EventData(final AbilityBase ab) {
        this(ab, 0);
    }
    
    public EventData(final AbilityBase ab, final int parameter) {
        this.parameter = 0;
        this.ab = ab;
        this.parameter = parameter;
    }
}
