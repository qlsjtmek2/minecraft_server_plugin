// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemBlockWithAuxData extends ItemBlock
{
    private Block a;
    
    public ItemBlockWithAuxData(final int i, final Block a) {
        super(i);
        this.a = a;
        this.setMaxDurability(0);
        this.a(true);
    }
    
    public int filterData(final int n) {
        return n;
    }
}
