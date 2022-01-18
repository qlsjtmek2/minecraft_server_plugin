// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public final class ThreadShutdown extends Thread
{
    final /* synthetic */ DedicatedServer a;
    
    public ThreadShutdown(final DedicatedServer a) {
        this.a = a;
    }
    
    public void run() {
        this.a.stop();
    }
}
