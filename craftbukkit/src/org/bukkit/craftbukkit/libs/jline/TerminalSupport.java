// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

public abstract class TerminalSupport implements Terminal
{
    public static final String JLINE_SHUTDOWNHOOK = "org.bukkit.craftbukkit.libs.jline.shutdownhook";
    public static final int DEFAULT_WIDTH = 80;
    public static final int DEFAULT_HEIGHT = 24;
    private Thread shutdownHook;
    private boolean shutdownHookEnabled;
    private boolean supported;
    private boolean echoEnabled;
    private boolean ansiSupported;
    private Configuration configuration;
    
    protected TerminalSupport(final boolean supported) {
        this.supported = supported;
        this.shutdownHookEnabled = Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.shutdownhook", false);
    }
    
    public void init() throws Exception {
        this.installShutdownHook(new RestoreHook());
    }
    
    public void restore() throws Exception {
        TerminalFactory.resetIf(this);
        this.removeShutdownHook();
    }
    
    public void reset() throws Exception {
        this.restore();
        this.init();
    }
    
    protected void installShutdownHook(final Thread hook) {
        if (!this.shutdownHookEnabled) {
            Log.debug("Not install shutdown hook " + hook + " because they are disabled.");
            return;
        }
        assert hook != null;
        if (this.shutdownHook != null) {
            throw new IllegalStateException("Shutdown hook already installed");
        }
        try {
            Runtime.getRuntime().addShutdownHook(hook);
            this.shutdownHook = hook;
        }
        catch (AbstractMethodError e) {
            Log.trace("Failed to register shutdown hook: ", e);
        }
    }
    
    protected void removeShutdownHook() {
        if (!this.shutdownHookEnabled) {
            return;
        }
        if (this.shutdownHook != null) {
            try {
                Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
            }
            catch (AbstractMethodError e) {
                Log.trace("Failed to remove shutdown hook: ", e);
            }
            catch (IllegalStateException ex) {}
            this.shutdownHook = null;
        }
    }
    
    public final boolean isSupported() {
        return this.supported;
    }
    
    public synchronized boolean isAnsiSupported() {
        return this.ansiSupported;
    }
    
    protected synchronized void setAnsiSupported(final boolean supported) {
        this.ansiSupported = supported;
        Log.debug("Ansi supported: ", supported);
    }
    
    public OutputStream wrapOutIfNeeded(final OutputStream out) {
        return out;
    }
    
    public boolean hasWeirdWrap() {
        return true;
    }
    
    public int getWidth() {
        return 80;
    }
    
    public int getHeight() {
        return 24;
    }
    
    public synchronized boolean isEchoEnabled() {
        return this.echoEnabled;
    }
    
    public synchronized void setEchoEnabled(final boolean enabled) {
        this.echoEnabled = enabled;
        Log.debug("Echo enabled: ", enabled);
    }
    
    public InputStream wrapInIfNeeded(final InputStream in) throws IOException {
        return in;
    }
    
    protected class RestoreHook extends Thread
    {
        public void start() {
            try {
                TerminalSupport.this.restore();
            }
            catch (Exception e) {
                Log.trace("Failed to restore: ", e);
            }
        }
    }
}
