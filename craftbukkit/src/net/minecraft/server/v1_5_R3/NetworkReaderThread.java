// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class NetworkReaderThread extends Thread
{
    final /* synthetic */ NetworkManager a;
    
    NetworkReaderThread(final NetworkManager a, final String s) {
        this.a = a;
        super(s);
    }
    
    public void run() {
        NetworkManager.a.getAndIncrement();
        try {
            while (NetworkManager.a(this.a) && !NetworkManager.b(this.a)) {
                while (NetworkManager.c(this.a)) {}
                try {
                    Thread.sleep(2L);
                }
                catch (InterruptedException ex) {}
            }
        }
        finally {
            NetworkManager.a.getAndDecrement();
        }
    }
}
