// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi;

import java.io.IOException;
import java.io.FilterOutputStream;
import org.fusesource.jansi.internal.CLibrary;
import java.io.OutputStream;
import java.io.PrintStream;

public class AnsiConsole
{
    public static final PrintStream system_out;
    public static final PrintStream out;
    public static final PrintStream system_err;
    public static final PrintStream err;
    private static int installed;
    
    public static OutputStream wrapOutputStream(final OutputStream stream) {
        if (Boolean.getBoolean("jansi.passthrough")) {
            return stream;
        }
        if (Boolean.getBoolean("jansi.strip")) {
            return new AnsiOutputStream(stream);
        }
        final String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            try {
                return new WindowsAnsiOutputStream(stream);
            }
            catch (Throwable ignore) {
                return new AnsiOutputStream(stream);
            }
        }
        try {
            final int rc = CLibrary.isatty(CLibrary.STDOUT_FILENO);
            if (rc == 0) {
                return new AnsiOutputStream(stream);
            }
        }
        catch (NoClassDefFoundError ignore2) {}
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
        return new FilterOutputStream(stream) {
            public void close() throws IOException {
                this.write(AnsiOutputStream.REST_CODE);
                this.flush();
                super.close();
            }
        };
    }
    
    public static PrintStream out() {
        return AnsiConsole.out;
    }
    
    public static PrintStream err() {
        return AnsiConsole.err;
    }
    
    public static synchronized void systemInstall() {
        ++AnsiConsole.installed;
        if (AnsiConsole.installed == 1) {
            System.setOut(AnsiConsole.out);
            System.setErr(AnsiConsole.err);
        }
    }
    
    public static synchronized void systemUninstall() {
        --AnsiConsole.installed;
        if (AnsiConsole.installed == 0) {
            System.setOut(AnsiConsole.system_out);
            System.setErr(AnsiConsole.system_err);
        }
    }
    
    static {
        system_out = System.out;
        out = new PrintStream(wrapOutputStream(AnsiConsole.system_out));
        system_err = System.err;
        err = new PrintStream(wrapOutputStream(AnsiConsole.system_err));
    }
}
