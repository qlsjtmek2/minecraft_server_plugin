// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class MerchantRecipeList extends ArrayList
{
    public MerchantRecipeList() {
    }
    
    public MerchantRecipeList(final NBTTagCompound nbtTagCompound) {
        this.a(nbtTagCompound);
    }
    
    public MerchantRecipe a(final ItemStack itemStack, final ItemStack itemStack2, final int n) {
        if (n <= 0 || n >= this.size()) {
            for (int i = 0; i < this.size(); ++i) {
                final MerchantRecipe merchantRecipe = this.get(i);
                if (itemStack.id == merchantRecipe.getBuyItem1().id && itemStack.count >= merchantRecipe.getBuyItem1().count && ((!merchantRecipe.hasSecondItem() && itemStack2 == null) || (merchantRecipe.hasSecondItem() && itemStack2 != null && merchantRecipe.getBuyItem2().id == itemStack2.id && itemStack2.count >= merchantRecipe.getBuyItem2().count))) {
                    return merchantRecipe;
                }
            }
            return null;
        }
        final MerchantRecipe merchantRecipe2 = this.get(n);
        if (itemStack.id == merchantRecipe2.getBuyItem1().id && ((itemStack2 == null && !merchantRecipe2.hasSecondItem()) || (merchantRecipe2.hasSecondItem() && itemStack2 != null && merchantRecipe2.getBuyItem2().id == itemStack2.id)) && itemStack.count >= merchantRecipe2.getBuyItem1().count && (!merchantRecipe2.hasSecondItem() || itemStack2.count >= merchantRecipe2.getBuyItem2().count)) {
            return merchantRecipe2;
        }
        return null;
    }
    
    public void a(final MerchantRecipe merchantRecipe) {
        for (int i = 0; i < this.size(); ++i) {
            final MerchantRecipe merchantRecipe2 = this.get(i);
            if (merchantRecipe.a(merchantRecipe2)) {
                if (merchantRecipe.b(merchantRecipe2)) {
                    this.set(i, merchantRecipe);
                }
                return;
            }
        }
        this.add(merchantRecipe);
    }
    
    public void a(final DataOutputStream dataoutputstream) {
        dataoutputstream.writeByte((byte)(this.size() & 0xFF));
        for (int i = 0; i < this.size(); ++i) {
            final MerchantRecipe merchantRecipe = this.get(i);
            Packet.a(merchantRecipe.getBuyItem1(), dataoutputstream);
            Packet.a(merchantRecipe.getBuyItem3(), dataoutputstream);
            final ItemStack buyItem2 = merchantRecipe.getBuyItem2();
            dataoutputstream.writeBoolean(buyItem2 != null);
            if (buyItem2 != null) {
                Packet.a(buyItem2, dataoutputstream);
            }
            dataoutputstream.writeBoolean(merchantRecipe.g());
        }
    }
    
    public void a(final NBTTagCompound nbtTagCompound) {
        final NBTTagList list = nbtTagCompound.getList("Recipes");
        for (int i = 0; i < list.size(); ++i) {
            this.add(new MerchantRecipe((NBTTagCompound)list.get(i)));
        }
    }
    
    public NBTTagCompound a() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final NBTTagList list = new NBTTagList("Recipes");
        for (int i = 0; i < this.size(); ++i) {
            list.add(((MerchantRecipe)this.get(i)).i());
        }
        nbtTagCompound.set("Recipes", list);
        return nbtTagCompound;
    }
}
