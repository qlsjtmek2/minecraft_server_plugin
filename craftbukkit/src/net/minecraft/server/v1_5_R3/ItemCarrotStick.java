// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemCarrotStick extends Item
{
    public ItemCarrotStick(final int n) {
        super(n);
        this.a(CreativeModeTab.e);
        this.d(1);
        this.setMaxDurability(25);
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entityliving) {
        if (entityliving.af() && entityliving.vehicle instanceof EntityPig) {
            final EntityPig entityPig = (EntityPig)entityliving.vehicle;
            if (entityPig.n().h() && itemStack.l() - itemStack.getData() >= 7) {
                entityPig.n().g();
                itemStack.damage(7, entityliving);
                if (itemStack.count == 0) {
                    final ItemStack itemStack2 = new ItemStack(Item.FISHING_ROD);
                    itemStack2.setTag(itemStack.tag);
                    return itemStack2;
                }
            }
        }
        return itemStack;
    }
}
