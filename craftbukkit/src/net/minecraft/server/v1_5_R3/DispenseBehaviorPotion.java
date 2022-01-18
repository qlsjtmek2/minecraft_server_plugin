// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class DispenseBehaviorPotion implements IDispenseBehavior
{
    private final DispenseBehaviorItem b;
    
    DispenseBehaviorPotion() {
        this.b = new DispenseBehaviorItem();
    }
    
    public ItemStack a(final ISourceBlock sourceBlock, final ItemStack itemStack) {
        if (ItemPotion.f(itemStack.getData())) {
            return new DispenseBehaviorThrownPotion(this, itemStack).a(sourceBlock, itemStack);
        }
        return this.b.a(sourceBlock, itemStack);
    }
}
