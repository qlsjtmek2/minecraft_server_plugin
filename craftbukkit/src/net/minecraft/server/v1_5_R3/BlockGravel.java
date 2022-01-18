// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockGravel extends BlockSand
{
    public BlockGravel(final int n) {
        super(n);
    }
    
    public int getDropType(final int n, final Random random, int n2) {
        if (n2 > 3) {
            n2 = 3;
        }
        if (random.nextInt(10 - n2 * 3) == 0) {
            return Item.FLINT.id;
        }
        return this.id;
    }
}
