// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockStep extends BlockStepAbstract
{
    public static final String[] b;
    
    public BlockStep(final int n, final boolean b) {
        super(n, b, Material.STONE);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Block.STEP.id;
    }
    
    protected ItemStack c_(final int n) {
        return new ItemStack(Block.STEP.id, 2, n & 0x7);
    }
    
    public String c(int n) {
        if (n < 0 || n >= BlockStep.b.length) {
            n = 0;
        }
        return super.a() + "." + BlockStep.b[n];
    }
    
    static {
        b = new String[] { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz" };
    }
}
