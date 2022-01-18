// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Material
{
    public static final Material AIR;
    public static final Material GRASS;
    public static final Material EARTH;
    public static final Material WOOD;
    public static final Material STONE;
    public static final Material ORE;
    public static final Material HEAVY;
    public static final Material WATER;
    public static final Material LAVA;
    public static final Material LEAVES;
    public static final Material PLANT;
    public static final Material REPLACEABLE_PLANT;
    public static final Material SPONGE;
    public static final Material CLOTH;
    public static final Material FIRE;
    public static final Material SAND;
    public static final Material ORIENTABLE;
    public static final Material SHATTERABLE;
    public static final Material BUILDABLE_GLASS;
    public static final Material TNT;
    public static final Material CORAL;
    public static final Material ICE;
    public static final Material SNOW_LAYER;
    public static final Material SNOW_BLOCK;
    public static final Material CACTUS;
    public static final Material CLAY;
    public static final Material PUMPKIN;
    public static final Material DRAGON_EGG;
    public static final Material PORTAL;
    public static final Material CAKE;
    public static final Material WEB;
    public static final Material PISTON;
    private boolean canBurn;
    private boolean I;
    private boolean J;
    public final MaterialMapColor G;
    private boolean K;
    private int L;
    private boolean M;
    
    public Material(final MaterialMapColor g) {
        this.K = true;
        this.G = g;
    }
    
    public boolean isLiquid() {
        return false;
    }
    
    public boolean isBuildable() {
        return true;
    }
    
    public boolean blocksLight() {
        return true;
    }
    
    public boolean isSolid() {
        return true;
    }
    
    private Material r() {
        this.J = true;
        return this;
    }
    
    protected Material f() {
        this.K = false;
        return this;
    }
    
    protected Material g() {
        this.canBurn = true;
        return this;
    }
    
    public boolean isBurnable() {
        return this.canBurn;
    }
    
    public Material i() {
        this.I = true;
        return this;
    }
    
    public boolean isReplaceable() {
        return this.I;
    }
    
    public boolean k() {
        return !this.J && this.isSolid();
    }
    
    public boolean isAlwaysDestroyable() {
        return this.K;
    }
    
    public int getPushReaction() {
        return this.L;
    }
    
    protected Material n() {
        this.L = 1;
        return this;
    }
    
    protected Material o() {
        this.L = 2;
        return this;
    }
    
    protected Material p() {
        this.M = true;
        return this;
    }
    
    public boolean q() {
        return this.M;
    }
    
    static {
        AIR = new MaterialGas(MaterialMapColor.b);
        GRASS = new Material(MaterialMapColor.c);
        EARTH = new Material(MaterialMapColor.l);
        WOOD = new Material(MaterialMapColor.o).g();
        STONE = new Material(MaterialMapColor.m).f();
        ORE = new Material(MaterialMapColor.h).f();
        HEAVY = new Material(MaterialMapColor.h).f().o();
        WATER = new MaterialLiquid(MaterialMapColor.n).n();
        LAVA = new MaterialLiquid(MaterialMapColor.f).n();
        LEAVES = new Material(MaterialMapColor.i).g().r().n();
        PLANT = new MaterialDecoration(MaterialMapColor.i).n();
        REPLACEABLE_PLANT = new MaterialDecoration(MaterialMapColor.i).g().n().i();
        SPONGE = new Material(MaterialMapColor.e);
        CLOTH = new Material(MaterialMapColor.e).g();
        FIRE = new MaterialGas(MaterialMapColor.b).n();
        SAND = new Material(MaterialMapColor.d);
        ORIENTABLE = new MaterialDecoration(MaterialMapColor.b).n();
        SHATTERABLE = new Material(MaterialMapColor.b).r().p();
        BUILDABLE_GLASS = new Material(MaterialMapColor.b).p();
        TNT = new Material(MaterialMapColor.f).g().r();
        CORAL = new Material(MaterialMapColor.i).n();
        ICE = new Material(MaterialMapColor.g).r().p();
        SNOW_LAYER = new MaterialDecoration(MaterialMapColor.j).i().r().f().n();
        SNOW_BLOCK = new Material(MaterialMapColor.j).f();
        CACTUS = new Material(MaterialMapColor.i).r().n();
        CLAY = new Material(MaterialMapColor.k);
        PUMPKIN = new Material(MaterialMapColor.i).n();
        DRAGON_EGG = new Material(MaterialMapColor.i).n();
        PORTAL = new MaterialPortal(MaterialMapColor.b).o();
        CAKE = new Material(MaterialMapColor.b).n();
        WEB = new MaterialWeb(MaterialMapColor.e).f().n();
        PISTON = new Material(MaterialMapColor.m).o();
    }
}
