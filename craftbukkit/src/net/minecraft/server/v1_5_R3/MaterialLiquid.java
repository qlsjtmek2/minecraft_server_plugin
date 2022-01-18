// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class MaterialLiquid extends Material
{
    public MaterialLiquid(final MaterialMapColor materialMapColor) {
        super(materialMapColor);
        this.i();
        this.n();
    }
    
    public boolean isLiquid() {
        return true;
    }
    
    public boolean isSolid() {
        return false;
    }
    
    public boolean isBuildable() {
        return false;
    }
}
