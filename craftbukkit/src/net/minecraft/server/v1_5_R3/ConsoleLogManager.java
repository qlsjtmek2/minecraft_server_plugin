// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.io.File;
import org.bukkit.craftbukkit.v1_5_R3.util.ShortConsoleLogFormatter;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import org.bukkit.craftbukkit.v1_5_R3.util.TerminalConsoleHandler;
import java.util.logging.Logger;

public class ConsoleLogManager implements IConsoleLogManager
{
    private final Logger a;
    private final String b;
    private final String c;
    private final String d;
    public static Logger global;
    
    public ConsoleLogManager(final String s, final String s1, final String s2) {
        this.a = Logger.getLogger(s);
        this.c = s;
        this.d = s1;
        this.b = s2;
        this.b();
    }
    
    private void b() {
        this.a.setUseParentHandlers(false);
        for (final Handler handler : this.a.getHandlers()) {
            this.a.removeHandler(handler);
        }
        final ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter(this, null);
        final MinecraftServer server = MinecraftServer.getServer();
        final ConsoleHandler consolehandler = new TerminalConsoleHandler(server.reader);
        consolehandler.setFormatter(consolelogformatter);
        this.a.addHandler(consolehandler);
        for (final Handler handler2 : ConsoleLogManager.global.getHandlers()) {
            ConsoleLogManager.global.removeHandler(handler2);
        }
        consolehandler.setFormatter(new ShortConsoleLogFormatter(server));
        ConsoleLogManager.global.addHandler(consolehandler);
        try {
            final String pattern = (String)server.options.valueOf("log-pattern");
            String tmpDir = System.getProperty("java.io.tmpdir");
            final String homeDir = System.getProperty("user.home");
            if (tmpDir == null) {
                tmpDir = homeDir;
            }
            File parent = new File(pattern).getParentFile();
            final StringBuilder fixedPattern = new StringBuilder();
            String parentPath = "";
            if (parent != null) {
                parentPath = parent.getPath();
            }
            int k = 0;
            while (k < parentPath.length()) {
                final char ch = parentPath.charAt(k);
                char ch2 = '\0';
                if (k + 1 < parentPath.length()) {
                    ch2 = Character.toLowerCase(pattern.charAt(k + 1));
                }
                if (ch == '%') {
                    if (ch2 == 'h') {
                        k += 2;
                        fixedPattern.append(homeDir);
                        continue;
                    }
                    if (ch2 == 't') {
                        k += 2;
                        fixedPattern.append(tmpDir);
                        continue;
                    }
                    if (ch2 == '%') {
                        k += 2;
                        fixedPattern.append("%%");
                        continue;
                    }
                    if (ch2 != '\0') {
                        throw new IOException("log-pattern can only use %t and %h for directories, got %" + ch2);
                    }
                }
                fixedPattern.append(ch);
                ++k;
            }
            parent = new File(fixedPattern.toString());
            if (parent != null) {
                parent.mkdirs();
            }
            final int limit = (int)server.options.valueOf("log-limit");
            final int count = (int)server.options.valueOf("log-count");
            final boolean append = (boolean)server.options.valueOf("log-append");
            final FileHandler filehandler = new FileHandler(pattern, limit, count, append);
            filehandler.setFormatter(consolelogformatter);
            this.a.addHandler(filehandler);
            ConsoleLogManager.global.addHandler(filehandler);
        }
        catch (Exception exception) {
            this.a.log(Level.WARNING, "Failed to log " + this.c + " to " + this.b, exception);
        }
    }
    
    public Logger getLogger() {
        return this.a;
    }
    
    public void info(final String s) {
        this.a.log(Level.INFO, s);
    }
    
    public void warning(final String s) {
        this.a.log(Level.WARNING, s);
    }
    
    public void warning(final String s, final Object... aobject) {
        this.a.log(Level.WARNING, s, aobject);
    }
    
    public void warning(final String s, final Throwable throwable) {
        this.a.log(Level.WARNING, s, throwable);
    }
    
    public void severe(final String s) {
        this.a.log(Level.SEVERE, s);
    }
    
    public void severe(final String s, final Throwable throwable) {
        this.a.log(Level.SEVERE, s, throwable);
    }
    
    static String a(final ConsoleLogManager consolelogmanager) {
        return consolelogmanager.d;
    }
    
    static {
        ConsoleLogManager.global = Logger.getLogger("");
    }
}
