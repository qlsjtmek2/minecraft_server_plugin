// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ThreadServerApplication extends Thread
{
    final /* synthetic */ MinecraftServer a;
    
    public ThreadServerApplication(final MinecraftServer a, final String s) {
        this.a = a;
        super(s);
    }
    
    public void run() {
        this.a.run();
    }
}
