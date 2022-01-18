// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockWorkbench extends Block
{
    protected BlockWorkbench(final int i) {
        super(i, Material.WOOD);
        this.a(CreativeModeTab.c);
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityHuman, final int n, final float n2, final float n3, final float n4) {
        if (world.isStatic) {
            return true;
        }
        entityHuman.startCrafting(i, j, k);
        return true;
    }
}
