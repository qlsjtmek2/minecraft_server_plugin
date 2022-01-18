// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline;

import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.craftbukkit.libs.jline.internal.TerminalLineSettings;

public class UnixTerminal extends TerminalSupport
{
    private final TerminalLineSettings settings;
    
    public UnixTerminal() throws Exception {
        super(true);
        this.settings = new TerminalLineSettings();
    }
    
    protected TerminalLineSettings getSettings() {
        return this.settings;
    }
    
    public void init() throws Exception {
        super.init();
        this.setAnsiSupported(true);
        this.settings.set("-icanon min 1");
        this.setEchoEnabled(false);
    }
    
    public void restore() throws Exception {
        this.settings.restore();
        super.restore();
        System.out.println();
    }
    
    public int getWidth() {
        final int w = this.settings.getProperty("columns");
        return (w < 1) ? 80 : w;
    }
    
    public int getHeight() {
        final int h = this.settings.getProperty("rows");
        return (h < 1) ? 24 : h;
    }
    
    public synchronized void setEchoEnabled(final boolean enabled) {
        try {
            if (enabled) {
                this.settings.set("echo");
            }
            else {
                this.settings.set("-echo");
            }
            super.setEchoEnabled(enabled);
        }
        catch (Exception e) {
            Log.error("Failed to ", enabled ? "enable" : "disable", " echo: ", e);
        }
    }
}
