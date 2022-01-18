// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.Map;

public class RegistrySimple implements IRegistry
{
    protected final Map a;
    
    public RegistrySimple() {
        this.a = new HashMap();
    }
    
    public Object a(final Object o) {
        return this.a.get(o);
    }
    
    public void a(final Object o, final Object o2) {
        this.a.put(o, o2);
    }
}
