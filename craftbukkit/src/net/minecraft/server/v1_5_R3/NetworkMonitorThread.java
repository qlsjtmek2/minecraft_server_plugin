// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class NetworkMonitorThread extends Thread
{
    final /* synthetic */ NetworkManager a;
    
    NetworkMonitorThread(final NetworkManager a) {
        this.a = a;
    }
    
    public void run() {
        try {
            Thread.sleep(2000L);
            if (NetworkManager.a(this.a)) {
                NetworkManager.h(this.a).interrupt();
                this.a.a("disconnect.closed", new Object[0]);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
