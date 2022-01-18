// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public enum EnumArmorMaterial
{
    CLOTH("CLOTH", 0, 5, new int[] { 1, 3, 2, 1 }, 15), 
    CHAIN("CHAIN", 1, 15, new int[] { 2, 5, 4, 1 }, 12), 
    IRON("IRON", 2, 15, new int[] { 2, 6, 5, 2 }, 9), 
    GOLD("GOLD", 3, 7, new int[] { 2, 5, 3, 1 }, 25), 
    DIAMOND("DIAMOND", 4, 33, new int[] { 3, 8, 6, 3 }, 10);
    
    private int f;
    private int[] g;
    private int h;
    
    private EnumArmorMaterial(final String s, final int n, final int f, final int[] g, final int h) {
        this.f = f;
        this.g = g;
        this.h = h;
    }
    
    public int a(final int n) {
        return ItemArmor.cu[n] * this.f;
    }
    
    public int b(final int n) {
        return this.g[n];
    }
    
    public int a() {
        return this.h;
    }
    
    public int b() {
        if (this == EnumArmorMaterial.CLOTH) {
            return Item.LEATHER.id;
        }
        if (this == EnumArmorMaterial.CHAIN) {
            return Item.IRON_INGOT.id;
        }
        if (this == EnumArmorMaterial.GOLD) {
            return Item.GOLD_INGOT.id;
        }
        if (this == EnumArmorMaterial.IRON) {
            return Item.IRON_INGOT.id;
        }
        if (this == EnumArmorMaterial.DIAMOND) {
            return Item.DIAMOND.id;
        }
        return 0;
    }
}
