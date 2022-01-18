// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemCloth extends ItemBlock
{
    public ItemCloth(final int i) {
        super(i);
        this.setMaxDurability(0);
        this.a(true);
    }
    
    public int filterData(final int n) {
        return n;
    }
    
    public String d(final ItemStack itemStack) {
        return super.getName() + "." + ItemDye.a[BlockCloth.g_(itemStack.getData())];
    }
}
