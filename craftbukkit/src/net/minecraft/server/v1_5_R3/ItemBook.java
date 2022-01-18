// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemBook extends Item
{
    public ItemBook(final int n) {
        super(n);
    }
    
    public boolean d_(final ItemStack itemStack) {
        return itemStack.count == 1;
    }
    
    public int c() {
        return 1;
    }
}
