// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface IEntitySelector
{
    public static final IEntitySelector a = new EntitySelectorLiving();
    public static final IEntitySelector b = new EntitySelectorContainer();
    
    boolean a(final Entity p0);
}
