// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemGoldenApple extends ItemFood
{
    public ItemGoldenApple(final int i, final int j, final float f, final boolean flag) {
        super(i, j, f, flag);
        this.a(true);
    }
    
    protected void c(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        if (itemstack.getData() > 0) {
            if (!world.isStatic) {
                entityhuman.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 600, 3));
                entityhuman.addEffect(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0));
                entityhuman.addEffect(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0));
            }
        }
        else {
            super.c(itemstack, world, entityhuman);
        }
    }
}
