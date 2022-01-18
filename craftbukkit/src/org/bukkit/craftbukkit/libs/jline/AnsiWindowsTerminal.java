// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline;

import java.io.ByteArrayOutputStream;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.AnsiOutputStream;
import org.fusesource.jansi.WindowsAnsiOutputStream;
import java.io.OutputStream;

public class AnsiWindowsTerminal extends WindowsTerminal
{
    private final boolean ansiSupported;
    
    public OutputStream wrapOutIfNeeded(final OutputStream out) {
        return wrapOutputStream(out);
    }
    
    private static OutputStream wrapOutputStream(final OutputStream stream) {
        final String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            try {
                return new WindowsAnsiOutputStream(stream);
            }
            catch (Throwable ignore) {
                return new AnsiOutputStream(stream);
            }
        }
        return stream;
    }
    
    private static boolean detectAnsiSupport() {
        AnsiConsole.systemInstall();
        final OutputStream out = AnsiConsole.wrapOutputStream(new ByteArrayOutputStream());
        try {
            out.close();
        }
        catch (Exception ex) {}
        return out instanceof WindowsAnsiOutputStream;
    }
    
    public AnsiWindowsTerminal() throws Exception {
        this.ansiSupported = detectAnsiSupport();
    }
    
    public boolean isAnsiSupported() {
        return this.ansiSupported;
    }
    
    public boolean hasWeirdWrap() {
        return false;
    }
}
