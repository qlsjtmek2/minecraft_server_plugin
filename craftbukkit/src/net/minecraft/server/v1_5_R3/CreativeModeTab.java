// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CreativeModeTab
{
    public static final CreativeModeTab[] a;
    public static final CreativeModeTab b;
    public static final CreativeModeTab c;
    public static final CreativeModeTab d;
    public static final CreativeModeTab e;
    public static final CreativeModeTab f;
    public static final CreativeModeTab g;
    public static final CreativeModeTab h;
    public static final CreativeModeTab i;
    public static final CreativeModeTab j;
    public static final CreativeModeTab k;
    public static final CreativeModeTab l;
    public static final CreativeModeTab m;
    private final int n;
    private final String o;
    private String p;
    private boolean q;
    private boolean r;
    
    public CreativeModeTab(final int n, final String o) {
        this.p = "list_items.png";
        this.q = true;
        this.r = true;
        this.n = n;
        this.o = o;
        CreativeModeTab.a[n] = this;
    }
    
    public CreativeModeTab a(final String p) {
        this.p = p;
        return this;
    }
    
    public CreativeModeTab h() {
        this.r = false;
        return this;
    }
    
    public CreativeModeTab j() {
        this.q = false;
        return this;
    }
    
    static {
        a = new CreativeModeTab[12];
        b = new CreativeModeTab1(0, "buildingBlocks");
        c = new CreativeModeTab2(1, "decorations");
        d = new CreativeModeTab3(2, "redstone");
        e = new CreativeModeTab4(3, "transportation");
        f = new CreativeModeTab5(4, "misc");
        g = new CreativeModeTab6(5, "search").a("search.png");
        h = new CreativeModeTab7(6, "food");
        i = new CreativeModeTab8(7, "tools");
        j = new CreativeModeTab9(8, "combat");
        k = new CreativeModeTab10(9, "brewing");
        l = new CreativeModeTab11(10, "materials");
        m = new CreativeModeTab12(11, "inventory").a("survival_inv.png").j().h();
    }
}
