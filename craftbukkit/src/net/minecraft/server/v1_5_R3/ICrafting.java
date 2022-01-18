// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public interface ICrafting
{
    void a(final Container p0, final List p1);
    
    void a(final Container p0, final int p1, final ItemStack p2);
    
    void setContainerData(final Container p0, final int p1, final int p2);
}
