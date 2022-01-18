// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class SlotPotionBottle extends Slot
{
    private EntityHuman a;
    
    public SlotPotionBottle(final EntityHuman a, final IInventory iinventory, final int i, final int j, final int k) {
        super(iinventory, i, j, k);
        this.a = a;
    }
    
    public boolean isAllowed(final ItemStack itemStack) {
        return a_(itemStack);
    }
    
    public int a() {
        return 1;
    }
    
    public void a(final EntityHuman entityhuman, final ItemStack itemstack) {
        if (itemstack.id == Item.POTION.id && itemstack.getData() > 0) {
            this.a.a(AchievementList.A, 1);
        }
        super.a(entityhuman, itemstack);
    }
    
    public static boolean a_(final ItemStack itemStack) {
        return itemStack != null && (itemStack.id == Item.POTION.id || itemStack.id == Item.GLASS_BOTTLE.id);
    }
}
