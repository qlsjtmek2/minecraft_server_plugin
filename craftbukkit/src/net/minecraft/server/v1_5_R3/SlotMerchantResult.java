// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class SlotMerchantResult extends Slot
{
    private final InventoryMerchant a;
    private EntityHuman b;
    private int c;
    private final IMerchant d;
    
    public SlotMerchantResult(final EntityHuman b, final IMerchant d, final InventoryMerchant inventoryMerchant, final int i, final int j, final int k) {
        super(inventoryMerchant, i, j, k);
        this.b = b;
        this.d = d;
        this.a = inventoryMerchant;
    }
    
    public boolean isAllowed(final ItemStack itemStack) {
        return false;
    }
    
    public ItemStack a(final int i) {
        if (this.d()) {
            this.c += Math.min(i, this.getItem().count);
        }
        return super.a(i);
    }
    
    protected void a(final ItemStack itemStack, final int n) {
        this.c += n;
        this.b(itemStack);
    }
    
    protected void b(final ItemStack itemStack) {
        itemStack.a(this.b.world, this.b, this.c);
        this.c = 0;
    }
    
    public void a(final EntityHuman entityHuman, final ItemStack itemStack) {
        this.b(itemStack);
        final MerchantRecipe recipe = this.a.getRecipe();
        if (recipe != null) {
            ItemStack item = this.a.getItem(0);
            ItemStack item2 = this.a.getItem(1);
            if (this.a(recipe, item, item2) || this.a(recipe, item2, item)) {
                if (item != null && item.count <= 0) {
                    item = null;
                }
                if (item2 != null && item2.count <= 0) {
                    item2 = null;
                }
                this.a.setItem(0, item);
                this.a.setItem(1, item2);
                this.d.a(recipe);
            }
        }
    }
    
    private boolean a(final MerchantRecipe merchantRecipe, final ItemStack itemStack, final ItemStack itemStack2) {
        final ItemStack buyItem1 = merchantRecipe.getBuyItem1();
        final ItemStack buyItem2 = merchantRecipe.getBuyItem2();
        if (itemStack != null && itemStack.id == buyItem1.id) {
            if (buyItem2 != null && itemStack2 != null && buyItem2.id == itemStack2.id) {
                itemStack.count -= buyItem1.count;
                itemStack2.count -= buyItem2.count;
                return true;
            }
            if (buyItem2 == null && itemStack2 == null) {
                itemStack.count -= buyItem1.count;
                return true;
            }
        }
        return false;
    }
}
