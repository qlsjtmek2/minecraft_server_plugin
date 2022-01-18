// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSaddle extends Item
{
    public ItemSaddle(final int n) {
        super(n);
        this.maxStackSize = 1;
        this.a(CreativeModeTab.e);
    }
    
    public boolean a(final ItemStack itemStack, final EntityLiving entityLiving) {
        if (entityLiving instanceof EntityPig) {
            final EntityPig entityPig = (EntityPig)entityLiving;
            if (!entityPig.hasSaddle() && !entityPig.isBaby()) {
                entityPig.setSaddle(true);
                --itemStack.count;
            }
            return true;
        }
        return false;
    }
    
    public boolean a(final ItemStack itemStack, final EntityLiving entityLiving, final EntityLiving entityLiving2) {
        this.a(itemStack, entityLiving);
        return true;
    }
}
