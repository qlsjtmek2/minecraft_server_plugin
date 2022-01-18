// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface IDispenseBehavior
{
    public static final IDispenseBehavior a = new DispenseBehaviorNoop();
    
    ItemStack a(final ISourceBlock p0, final ItemStack p1);
}
