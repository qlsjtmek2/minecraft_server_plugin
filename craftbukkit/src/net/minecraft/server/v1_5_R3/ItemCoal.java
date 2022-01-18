// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemCoal extends Item
{
    public ItemCoal(final int n) {
        super(n);
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.l);
    }
    
    public String d(final ItemStack itemStack) {
        if (itemStack.getData() == 1) {
            return "item.charcoal";
        }
        return "item.coal";
    }
}
