// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockHugeMushroom extends Block
{
    private static final String[] a;
    private final int b;
    
    public BlockHugeMushroom(final int i, final Material material, final int b) {
        super(i, material);
        this.b = b;
    }
    
    public int a(final Random random) {
        int n = random.nextInt(10) - 7;
        if (n < 0) {
            n = 0;
        }
        return n;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Block.BROWN_MUSHROOM.id + this.b;
    }
    
    static {
        a = new String[] { "mushroom_skin_brown", "mushroom_skin_red" };
    }
}
