// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CounterStatistic extends Statistic
{
    public CounterStatistic(final int n, final String s, final Counter counter) {
        super(n, s, counter);
    }
    
    public CounterStatistic(final int n, final String s) {
        super(n, s);
    }
    
    public Statistic g() {
        super.g();
        StatisticList.c.add(this);
        return this;
    }
}
