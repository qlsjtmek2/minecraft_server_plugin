// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class SlotBeacon extends Slot
{
    final /* synthetic */ ContainerBeacon a;
    
    public SlotBeacon(final ContainerBeacon a, final IInventory iinventory, final int i, final int j, final int k) {
        this.a = a;
        super(iinventory, i, j, k);
    }
    
    public boolean isAllowed(final ItemStack itemStack) {
        return itemStack != null && (itemStack.id == Item.EMERALD.id || itemStack.id == Item.DIAMOND.id || itemStack.id == Item.GOLD_INGOT.id || itemStack.id == Item.IRON_INGOT.id);
    }
    
    public int a() {
        return 1;
    }
}
