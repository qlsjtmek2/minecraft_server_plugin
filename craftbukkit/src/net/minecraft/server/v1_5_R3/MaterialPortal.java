// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class MaterialPortal extends Material
{
    public MaterialPortal(final MaterialMapColor materialMapColor) {
        super(materialMapColor);
    }
    
    public boolean isBuildable() {
        return false;
    }
    
    public boolean blocksLight() {
        return false;
    }
    
    public boolean isSolid() {
        return false;
    }
}
