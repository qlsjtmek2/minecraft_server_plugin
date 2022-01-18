// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class SlotArmor extends Slot
{
    final /* synthetic */ int a;
    final /* synthetic */ ContainerPlayer b;
    
    SlotArmor(final ContainerPlayer b, final IInventory iinventory, final int i, final int j, final int k, final int a) {
        this.b = b;
        this.a = a;
        super(iinventory, i, j, k);
    }
    
    public int a() {
        return 1;
    }
    
    public boolean isAllowed(final ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (itemStack.getItem() instanceof ItemArmor) {
            return ((ItemArmor)itemStack.getItem()).b == this.a;
        }
        return (itemStack.getItem().id == Block.PUMPKIN.id || itemStack.getItem().id == Item.SKULL.id) && this.a == 0;
    }
}
