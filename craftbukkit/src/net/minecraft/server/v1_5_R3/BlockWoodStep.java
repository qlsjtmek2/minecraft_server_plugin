// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockWoodStep extends BlockStepAbstract
{
    public static final String[] b;
    
    public BlockWoodStep(final int n, final boolean b) {
        super(n, b, Material.WOOD);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Block.WOOD_STEP.id;
    }
    
    protected ItemStack c_(final int n) {
        return new ItemStack(Block.WOOD_STEP.id, 2, n & 0x7);
    }
    
    public String c(int n) {
        if (n < 0 || n >= BlockWoodStep.b.length) {
            n = 0;
        }
        return super.a() + "." + BlockWoodStep.b[n];
    }
    
    static {
        b = new String[] { "oak", "spruce", "birch", "jungle" };
    }
}
