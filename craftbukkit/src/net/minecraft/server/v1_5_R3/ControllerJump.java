// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ControllerJump
{
    private EntityLiving a;
    private boolean b;
    
    public ControllerJump(final EntityLiving a) {
        this.b = false;
        this.a = a;
    }
    
    public void a() {
        this.b = true;
    }
    
    public void b() {
        this.a.f(this.b);
        this.b = false;
    }
}
