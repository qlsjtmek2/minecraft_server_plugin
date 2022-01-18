// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class MaterialMapColor
{
    public static final MaterialMapColor[] a;
    public static final MaterialMapColor b;
    public static final MaterialMapColor c;
    public static final MaterialMapColor d;
    public static final MaterialMapColor e;
    public static final MaterialMapColor f;
    public static final MaterialMapColor g;
    public static final MaterialMapColor h;
    public static final MaterialMapColor i;
    public static final MaterialMapColor j;
    public static final MaterialMapColor k;
    public static final MaterialMapColor l;
    public static final MaterialMapColor m;
    public static final MaterialMapColor n;
    public static final MaterialMapColor o;
    public final int p;
    public final int q;
    
    private MaterialMapColor(final int q, final int p2) {
        this.q = q;
        this.p = p2;
        MaterialMapColor.a[q] = this;
    }
    
    static {
        a = new MaterialMapColor[16];
        b = new MaterialMapColor(0, 0);
        c = new MaterialMapColor(1, 8368696);
        d = new MaterialMapColor(2, 16247203);
        e = new MaterialMapColor(3, 10987431);
        f = new MaterialMapColor(4, 16711680);
        g = new MaterialMapColor(5, 10526975);
        h = new MaterialMapColor(6, 10987431);
        i = new MaterialMapColor(7, 31744);
        j = new MaterialMapColor(8, 16777215);
        k = new MaterialMapColor(9, 10791096);
        l = new MaterialMapColor(10, 12020271);
        m = new MaterialMapColor(11, 7368816);
        n = new MaterialMapColor(12, 4210943);
        o = new MaterialMapColor(13, 6837042);
    }
}
