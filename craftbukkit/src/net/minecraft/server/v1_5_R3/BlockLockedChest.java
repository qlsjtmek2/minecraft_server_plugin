// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockLockedChest extends Block
{
    protected BlockLockedChest(final int i) {
        super(i, Material.WOOD);
    }
    
    public boolean canPlace(final World world, final int n, final int n2, final int n3) {
        return true;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        world.setAir(i, j, k);
    }
}
