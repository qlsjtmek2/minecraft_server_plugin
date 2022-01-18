// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Achievement extends Statistic
{
    public final int a;
    public final int b;
    public final Achievement c;
    private final String k;
    public final ItemStack d;
    private boolean m;
    
    public Achievement(final int n, final String s, final int n2, final int n3, final Item item, final Achievement achievement) {
        this(n, s, n2, n3, new ItemStack(item), achievement);
    }
    
    public Achievement(final int n, final String s, final int n2, final int n3, final Block block, final Achievement achievement) {
        this(n, s, n2, n3, new ItemStack(block), achievement);
    }
    
    public Achievement(final int n, final String s, final int c, final int d, final ItemStack d2, final Achievement c2) {
        super(5242880 + n, "achievement." + s);
        this.d = d2;
        this.k = "achievement." + s + ".desc";
        this.a = c;
        this.b = d;
        if (c < AchievementList.a) {
            AchievementList.a = c;
        }
        if (d < AchievementList.b) {
            AchievementList.b = d;
        }
        if (c > AchievementList.c) {
            AchievementList.c = c;
        }
        if (d > AchievementList.d) {
            AchievementList.d = d;
        }
        this.c = c2;
    }
    
    public Achievement a() {
        this.f = true;
        return this;
    }
    
    public Achievement b() {
        this.m = true;
        return this;
    }
    
    public Achievement c() {
        super.g();
        AchievementList.e.add(this);
        return this;
    }
}
