// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.craftbukkit.Main;

class ThreadCommandReader extends Thread
{
    final DedicatedServer server;
    
    ThreadCommandReader(final DedicatedServer dedicatedserver) {
        super("Command Reader");
        this.server = dedicatedserver;
    }
    
    public void run() {
        if (!Main.useConsole) {
            return;
        }
        final ConsoleReader bufferedreader = this.server.reader;
        try {
            while (!this.server.isStopped() && this.server.isRunning()) {
                String s;
                if (Main.useJline) {
                    s = bufferedreader.readLine(">", null);
                }
                else {
                    s = bufferedreader.readLine();
                }
                if (s != null) {
                    this.server.issueCommand(s, this.server);
                }
            }
        }
        catch (IOException ioexception) {
            Logger.getLogger("").log(Level.SEVERE, null, ioexception);
        }
    }
}
