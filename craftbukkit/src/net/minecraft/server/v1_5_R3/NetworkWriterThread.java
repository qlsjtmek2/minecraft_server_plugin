// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.IOException;

class NetworkWriterThread extends Thread
{
    final NetworkManager a;
    
    NetworkWriterThread(final NetworkManager networkmanager, final String s) {
        super(s);
        this.a = networkmanager;
    }
    
    public void run() {
        NetworkManager.b.getAndIncrement();
        try {
            while (NetworkManager.a(this.a)) {
                boolean flag = false;
                while (NetworkManager.d(this.a)) {
                    flag = true;
                }
                try {
                    if (flag && NetworkManager.e(this.a) != null) {
                        NetworkManager.e(this.a).flush();
                    }
                }
                catch (IOException ioexception) {
                    if (!NetworkManager.f(this.a)) {
                        NetworkManager.a(this.a, ioexception);
                    }
                }
                try {
                    Thread.sleep(2L);
                }
                catch (InterruptedException ex) {}
            }
        }
        finally {
            NetworkManager.b.getAndDecrement();
        }
    }
}
