// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemLeaves extends ItemBlock
{
    public ItemLeaves(final int i) {
        super(i);
        this.setMaxDurability(0);
        this.a(true);
    }
    
    public int filterData(final int n) {
        return n | 0x4;
    }
    
    public String d(final ItemStack itemStack) {
        int data = itemStack.getData();
        if (data < 0 || data >= BlockLeaves.a.length) {
            data = 0;
        }
        return super.getName() + "." + BlockLeaves.a[data];
    }
}
