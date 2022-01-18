// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemMultiTexture extends ItemBlock
{
    private final Block a;
    private final String[] b;
    
    public ItemMultiTexture(final int i, final Block a, final String[] b) {
        super(i);
        this.a = a;
        this.b = b;
        this.setMaxDurability(0);
        this.a(true);
    }
    
    public int filterData(final int n) {
        return n;
    }
    
    public String d(final ItemStack itemStack) {
        int data = itemStack.getData();
        if (data < 0 || data >= this.b.length) {
            data = 0;
        }
        return super.getName() + "." + this.b[data];
    }
}
