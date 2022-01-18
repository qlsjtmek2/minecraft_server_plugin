// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockMelon extends Block
{
    protected BlockMelon(final int i) {
        super(i, Material.PUMPKIN);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.MELON.id;
    }
    
    public int a(final Random random) {
        return 3 + random.nextInt(5);
    }
    
    public int getDropCount(final int n, final Random random) {
        int n2 = this.a(random) + random.nextInt(1 + n);
        if (n2 > 9) {
            n2 = 9;
        }
        return n2;
    }
}
