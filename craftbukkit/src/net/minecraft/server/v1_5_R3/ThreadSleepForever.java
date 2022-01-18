// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class ThreadSleepForever extends Thread
{
    final /* synthetic */ DedicatedServer a;
    
    ThreadSleepForever(final DedicatedServer a) {
        this.a = a;
        this.setDaemon(true);
        this.start();
    }
    
    public void run() {
        while (true) {
            try {
                while (true) {
                    Thread.sleep(2147483647L);
                }
            }
            catch (InterruptedException ex) {
                continue;
            }
            break;
        }
    }
}
