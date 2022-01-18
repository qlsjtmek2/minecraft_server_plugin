// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemWithAuxData extends ItemBlock
{
    private final Block a;
    private String[] b;
    
    public ItemWithAuxData(final int i, final boolean b) {
        super(i);
        this.a = Block.byId[this.g()];
        if (b) {
            this.setMaxDurability(0);
            this.a(true);
        }
    }
    
    public int filterData(final int n) {
        return n;
    }
    
    public ItemWithAuxData a(final String[] b) {
        this.b = b;
        return this;
    }
    
    public String d(final ItemStack itemstack) {
        if (this.b == null) {
            return super.d(itemstack);
        }
        final int data = itemstack.getData();
        if (data >= 0 && data < this.b.length) {
            return super.d(itemstack) + "." + this.b[data];
        }
        return super.d(itemstack);
    }
}
