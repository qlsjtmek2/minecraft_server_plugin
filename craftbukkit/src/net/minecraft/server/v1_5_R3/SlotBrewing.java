// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class SlotBrewing extends Slot
{
    final /* synthetic */ ContainerBrewingStand a;
    
    public SlotBrewing(final ContainerBrewingStand a, final IInventory iinventory, final int i, final int j, final int k) {
        this.a = a;
        super(iinventory, i, j, k);
    }
    
    public boolean isAllowed(final ItemStack itemStack) {
        return itemStack != null && Item.byId[itemStack.id].w();
    }
    
    public int a() {
        return 64;
    }
}
