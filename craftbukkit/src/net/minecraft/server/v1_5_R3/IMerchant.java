// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface IMerchant
{
    void a(final EntityHuman p0);
    
    EntityHuman m_();
    
    MerchantRecipeList getOffers(final EntityHuman p0);
    
    void a(final MerchantRecipe p0);
}
