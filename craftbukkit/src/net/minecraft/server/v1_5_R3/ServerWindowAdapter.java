// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

final class ServerWindowAdapter extends WindowAdapter
{
    final /* synthetic */ DedicatedServer a;
    
    ServerWindowAdapter(final DedicatedServer a) {
        this.a = a;
    }
    
    public void windowClosing(final WindowEvent windowEvent) {
        this.a.safeShutdown();
        while (!this.a.isStopped()) {
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.exit(0);
    }
}
