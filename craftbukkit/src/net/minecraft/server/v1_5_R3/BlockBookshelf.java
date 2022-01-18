// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockBookshelf extends Block
{
    public BlockBookshelf(final int i) {
        super(i, Material.WOOD);
        this.a(CreativeModeTab.b);
    }
    
    public int a(final Random random) {
        return 3;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.BOOK.id;
    }
}
