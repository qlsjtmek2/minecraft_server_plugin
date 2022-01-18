// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import net.minecraft.server.v1_5_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_5_R3.MinecraftServer;

public class ServerShutdownThread extends Thread
{
    private final MinecraftServer server;
    
    public ServerShutdownThread(final MinecraftServer server) {
        this.server = server;
    }
    
    public void run() {
        try {
            this.server.stop();
        }
        catch (ExceptionWorldConflict ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                this.server.reader.getTerminal().restore();
            }
            catch (Exception ex2) {}
        }
    }
}
