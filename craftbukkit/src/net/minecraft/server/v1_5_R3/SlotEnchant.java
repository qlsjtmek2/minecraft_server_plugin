// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class SlotEnchant extends Slot
{
    final /* synthetic */ ContainerEnchantTable a;
    
    SlotEnchant(final ContainerEnchantTable a, final IInventory iinventory, final int i, final int j, final int k) {
        this.a = a;
        super(iinventory, i, j, k);
    }
    
    public boolean isAllowed(final ItemStack itemStack) {
        return true;
    }
}
