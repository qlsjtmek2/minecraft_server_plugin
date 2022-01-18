// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class MerchantRecipe
{
    private ItemStack buyingItem1;
    private ItemStack buyingItem2;
    private ItemStack sellingItem;
    private int uses;
    private int maxUses;
    
    public MerchantRecipe(final NBTTagCompound nbtTagCompound) {
        this.a(nbtTagCompound);
    }
    
    public MerchantRecipe(final ItemStack buyingItem1, final ItemStack buyingItem2, final ItemStack sellingItem) {
        this.buyingItem1 = buyingItem1;
        this.buyingItem2 = buyingItem2;
        this.sellingItem = sellingItem;
        this.maxUses = 7;
    }
    
    public MerchantRecipe(final ItemStack itemStack, final ItemStack itemStack2) {
        this(itemStack, null, itemStack2);
    }
    
    public MerchantRecipe(final ItemStack itemStack, final Item item) {
        this(itemStack, new ItemStack(item));
    }
    
    public ItemStack getBuyItem1() {
        return this.buyingItem1;
    }
    
    public ItemStack getBuyItem2() {
        return this.buyingItem2;
    }
    
    public boolean hasSecondItem() {
        return this.buyingItem2 != null;
    }
    
    public ItemStack getBuyItem3() {
        return this.sellingItem;
    }
    
    public boolean a(final MerchantRecipe merchantRecipe) {
        return this.buyingItem1.id == merchantRecipe.buyingItem1.id && this.sellingItem.id == merchantRecipe.sellingItem.id && ((this.buyingItem2 == null && merchantRecipe.buyingItem2 == null) || (this.buyingItem2 != null && merchantRecipe.buyingItem2 != null && this.buyingItem2.id == merchantRecipe.buyingItem2.id));
    }
    
    public boolean b(final MerchantRecipe merchantRecipe) {
        return this.a(merchantRecipe) && (this.buyingItem1.count < merchantRecipe.buyingItem1.count || (this.buyingItem2 != null && this.buyingItem2.count < merchantRecipe.buyingItem2.count));
    }
    
    public void f() {
        ++this.uses;
    }
    
    public void a(final int n) {
        this.maxUses += n;
    }
    
    public boolean g() {
        return this.uses >= this.maxUses;
    }
    
    public void a(final NBTTagCompound nbtTagCompound) {
        this.buyingItem1 = ItemStack.createStack(nbtTagCompound.getCompound("buy"));
        this.sellingItem = ItemStack.createStack(nbtTagCompound.getCompound("sell"));
        if (nbtTagCompound.hasKey("buyB")) {
            this.buyingItem2 = ItemStack.createStack(nbtTagCompound.getCompound("buyB"));
        }
        if (nbtTagCompound.hasKey("uses")) {
            this.uses = nbtTagCompound.getInt("uses");
        }
        if (nbtTagCompound.hasKey("maxUses")) {
            this.maxUses = nbtTagCompound.getInt("maxUses");
        }
        else {
            this.maxUses = 7;
        }
    }
    
    public NBTTagCompound i() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setCompound("buy", this.buyingItem1.save(new NBTTagCompound("buy")));
        nbtTagCompound.setCompound("sell", this.sellingItem.save(new NBTTagCompound("sell")));
        if (this.buyingItem2 != null) {
            nbtTagCompound.setCompound("buyB", this.buyingItem2.save(new NBTTagCompound("buyB")));
        }
        nbtTagCompound.setInt("uses", this.uses);
        nbtTagCompound.setInt("maxUses", this.maxUses);
        return nbtTagCompound;
    }
}
