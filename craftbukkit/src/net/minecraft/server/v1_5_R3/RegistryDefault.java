// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RegistryDefault extends RegistrySimple
{
    private final Object b;
    
    public RegistryDefault(final Object b) {
        this.b = b;
    }
    
    public Object a(final Object o) {
        final Object a = super.a(o);
        return (a == null) ? this.b : a;
    }
}
