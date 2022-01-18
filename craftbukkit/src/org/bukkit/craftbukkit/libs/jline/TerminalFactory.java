// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline;

import java.util.HashMap;
import java.text.MessageFormat;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import java.util.Map;

public class TerminalFactory
{
    public static final String JLINE_TERMINAL = "org.bukkit.craftbukkit.libs.jline.terminal";
    public static final String AUTO = "auto";
    public static final String UNIX = "unix";
    public static final String WIN = "win";
    public static final String WINDOWS = "windows";
    public static final String NONE = "none";
    public static final String OFF = "off";
    public static final String FALSE = "false";
    private static final InheritableThreadLocal<Terminal> holder;
    private static final Map<Flavor, Class<? extends Terminal>> FLAVORS;
    
    public static synchronized Terminal create() {
        if (Log.TRACE) {
            Log.trace(new Throwable("CREATE MARKER"));
        }
        final String type = Configuration.getString("org.bukkit.craftbukkit.libs.jline.terminal", "auto");
        Log.debug("Creating terminal; type=", type);
        Terminal t;
        try {
            final String tmp = type.toLowerCase();
            if (tmp.equals("unix")) {
                t = getFlavor(Flavor.UNIX);
            }
            else if (tmp.equals("win") | tmp.equals("windows")) {
                t = getFlavor(Flavor.WINDOWS);
            }
            else if (tmp.equals("none") || tmp.equals("off") || tmp.equals("false")) {
                t = new UnsupportedTerminal();
            }
            else if (tmp.equals("auto")) {
                final String os = Configuration.getOsName();
                Flavor flavor = Flavor.UNIX;
                if (os.contains("windows")) {
                    flavor = Flavor.WINDOWS;
                }
                t = getFlavor(flavor);
            }
            else {
                try {
                    t = (Terminal)Thread.currentThread().getContextClassLoader().loadClass(type).newInstance();
                }
                catch (Exception e) {
                    throw new IllegalArgumentException(MessageFormat.format("Invalid terminal type: {0}", type), e);
                }
            }
        }
        catch (Exception e2) {
            Log.error("Failed to construct terminal; falling back to unsupported", e2);
            t = new UnsupportedTerminal();
        }
        Log.debug("Created Terminal: ", t);
        try {
            t.init();
        }
        catch (Exception e2) {
            Log.error("Terminal initialization failed; falling back to unsupported", e2);
            return new UnsupportedTerminal();
        }
        return t;
    }
    
    public static synchronized void reset() {
        TerminalFactory.holder.remove();
    }
    
    public static synchronized void resetIf(final Terminal t) {
        if (TerminalFactory.holder.get() == t) {
            reset();
        }
    }
    
    public static synchronized void configure(final String type) {
        assert type != null;
        System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", type);
    }
    
    public static synchronized void configure(final Type type) {
        assert type != null;
        configure(type.name().toLowerCase());
    }
    
    public static synchronized Terminal get() {
        Terminal t = TerminalFactory.holder.get();
        if (t == null) {
            t = create();
            TerminalFactory.holder.set(t);
        }
        return t;
    }
    
    public static Terminal getFlavor(final Flavor flavor) throws Exception {
        final Class<? extends Terminal> type = TerminalFactory.FLAVORS.get(flavor);
        if (type != null) {
            return (Terminal)type.newInstance();
        }
        throw new InternalError();
    }
    
    public static void registerFlavor(final Flavor flavor, final Class<? extends Terminal> type) {
        TerminalFactory.FLAVORS.put(flavor, type);
    }
    
    static {
        holder = new InheritableThreadLocal<Terminal>();
        FLAVORS = new HashMap<Flavor, Class<? extends Terminal>>();
        registerFlavor(Flavor.WINDOWS, AnsiWindowsTerminal.class);
        registerFlavor(Flavor.UNIX, UnixTerminal.class);
    }
    
    public enum Type
    {
        AUTO, 
        WINDOWS, 
        UNIX, 
        NONE;
    }
    
    public enum Flavor
    {
        WINDOWS, 
        UNIX;
    }
}
