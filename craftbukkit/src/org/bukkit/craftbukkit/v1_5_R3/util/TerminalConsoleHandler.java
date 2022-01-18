// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import java.util.logging.ConsoleHandler;

public class TerminalConsoleHandler extends ConsoleHandler
{
    private final ConsoleReader reader;
    
    public TerminalConsoleHandler(final ConsoleReader reader) {
        this.reader = reader;
    }
    
    public synchronized void flush() {
        try {
            if (Main.useJline) {
                this.reader.print("\r");
                this.reader.flush();
                super.flush();
                try {
                    this.reader.drawLine();
                }
                catch (Throwable ex2) {
                    this.reader.getCursorBuffer().clear();
                }
                this.reader.flush();
            }
            else {
                super.flush();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(TerminalConsoleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
