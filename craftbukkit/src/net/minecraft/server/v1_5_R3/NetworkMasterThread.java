// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class NetworkMasterThread extends Thread
{
    final /* synthetic */ NetworkManager a;
    
    NetworkMasterThread(final NetworkManager a) {
        this.a = a;
    }
    
    public void run() {
        try {
            Thread.sleep(5000L);
            if (NetworkManager.g(this.a).isAlive()) {
                try {
                    NetworkManager.g(this.a).stop();
                }
                catch (Throwable t) {}
            }
            if (NetworkManager.h(this.a).isAlive()) {
                try {
                    NetworkManager.h(this.a).stop();
                }
                catch (Throwable t2) {}
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
