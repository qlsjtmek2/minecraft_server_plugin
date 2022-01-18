// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Statistic
{
    public final int e;
    private final String a;
    public boolean f;
    public String g;
    private final Counter b;
    private static NumberFormat c;
    public static Counter h;
    private static DecimalFormat d;
    public static Counter i;
    public static Counter j;
    
    public Statistic(final int e, final String a, final Counter b) {
        this.f = false;
        this.e = e;
        this.a = a;
        this.b = b;
    }
    
    public Statistic(final int n, final String s) {
        this(n, s, Statistic.h);
    }
    
    public Statistic h() {
        this.f = true;
        return this;
    }
    
    public Statistic g() {
        if (StatisticList.a.containsKey(this.e)) {
            throw new RuntimeException("Duplicate stat id: \"" + StatisticList.a.get(this.e).a + "\" and \"" + this.a + "\" at id " + this.e);
        }
        StatisticList.b.add(this);
        StatisticList.a.put(this.e, this);
        this.g = AchievementMap.a(this.e);
        return this;
    }
    
    public String toString() {
        return LocaleI18n.get(this.a);
    }
    
    static {
        Statistic.c = NumberFormat.getIntegerInstance(Locale.US);
        Statistic.h = new UnknownCounter();
        Statistic.d = new DecimalFormat("########0.00");
        Statistic.i = new TimeCounter();
        Statistic.j = new DistancesCounter();
    }
}
