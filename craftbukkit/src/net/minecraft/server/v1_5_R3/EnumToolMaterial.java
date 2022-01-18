// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public enum EnumToolMaterial
{
    WOOD("WOOD", 0, 0, 59, 2.0f, 0, 15), 
    STONE("STONE", 1, 1, 131, 4.0f, 1, 5), 
    IRON("IRON", 2, 2, 250, 6.0f, 2, 14), 
    DIAMOND("EMERALD", 3, 3, 1561, 8.0f, 3, 10), 
    GOLD("GOLD", 4, 0, 32, 12.0f, 0, 22);
    
    private final int f;
    private final int g;
    private final float h;
    private final int i;
    private final int j;
    
    private EnumToolMaterial(final String s, final int n, final int f, final int g, final float h, final int i, final int j) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = j;
    }
    
    public int a() {
        return this.g;
    }
    
    public float b() {
        return this.h;
    }
    
    public int c() {
        return this.i;
    }
    
    public int d() {
        return this.f;
    }
    
    public int e() {
        return this.j;
    }
    
    public int f() {
        if (this == EnumToolMaterial.WOOD) {
            return Block.WOOD.id;
        }
        if (this == EnumToolMaterial.STONE) {
            return Block.COBBLESTONE.id;
        }
        if (this == EnumToolMaterial.GOLD) {
            return Item.GOLD_INGOT.id;
        }
        if (this == EnumToolMaterial.IRON) {
            return Item.IRON_INGOT.id;
        }
        if (this == EnumToolMaterial.DIAMOND) {
            return Item.DIAMOND.id;
        }
        return 0;
    }
}
