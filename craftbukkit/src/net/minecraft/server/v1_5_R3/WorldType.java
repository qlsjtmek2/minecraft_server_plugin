// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class WorldType
{
    public static final WorldType[] types;
    public static final WorldType NORMAL;
    public static final WorldType FLAT;
    public static final WorldType LARGE_BIOMES;
    public static final WorldType NORMAL_1_1;
    private final int f;
    private final String name;
    private final int version;
    private boolean i;
    private boolean j;
    
    private WorldType(final int n, final String s) {
        this(n, s, 0);
    }
    
    private WorldType(final int f, final String name, final int version) {
        this.name = name;
        this.version = version;
        this.i = true;
        this.f = f;
        WorldType.types[f] = this;
    }
    
    public String name() {
        return this.name;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public WorldType a(final int n) {
        if (this == WorldType.NORMAL && n == 0) {
            return WorldType.NORMAL_1_1;
        }
        return this;
    }
    
    private WorldType a(final boolean i) {
        this.i = i;
        return this;
    }
    
    private WorldType g() {
        this.j = true;
        return this;
    }
    
    public boolean e() {
        return this.j;
    }
    
    public static WorldType getType(final String s) {
        for (int i = 0; i < WorldType.types.length; ++i) {
            if (WorldType.types[i] != null && WorldType.types[i].name.equalsIgnoreCase(s)) {
                return WorldType.types[i];
            }
        }
        return null;
    }
    
    public int f() {
        return this.f;
    }
    
    static {
        types = new WorldType[16];
        NORMAL = new WorldType(0, "default", 1).g();
        FLAT = new WorldType(1, "flat");
        LARGE_BIOMES = new WorldType(2, "largeBiomes");
        NORMAL_1_1 = new WorldType(8, "default_1_1", 0).a(false);
    }
}
