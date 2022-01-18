// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline;

import org.fusesource.jansi.internal.WindowsSupport;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

public class WindowsTerminal extends TerminalSupport
{
    public static final String JLINE_WINDOWS_TERMINAL_DIRECT_CONSOLE = "org.bukkit.craftbukkit.libs.jline.WindowsTerminal.directConsole";
    public static final String ANSI;
    private boolean directConsole;
    private int originalMode;
    
    public WindowsTerminal() throws Exception {
        super(true);
    }
    
    public void init() throws Exception {
        super.init();
        this.setAnsiSupported(Configuration.getBoolean(WindowsTerminal.ANSI, true));
        this.setDirectConsole(Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.WindowsTerminal.directConsole", true));
        this.originalMode = this.getConsoleMode();
        this.setConsoleMode(this.originalMode & ~ConsoleMode.ENABLE_ECHO_INPUT.code);
        this.setEchoEnabled(false);
    }
    
    public void restore() throws Exception {
        this.setConsoleMode(this.originalMode);
        super.restore();
    }
    
    public int getWidth() {
        final int w = this.getWindowsTerminalWidth();
        return (w < 1) ? 80 : w;
    }
    
    public int getHeight() {
        final int h = this.getWindowsTerminalHeight();
        return (h < 1) ? 24 : h;
    }
    
    public void setEchoEnabled(final boolean enabled) {
        if (enabled) {
            this.setConsoleMode(this.getConsoleMode() | ConsoleMode.ENABLE_ECHO_INPUT.code | ConsoleMode.ENABLE_LINE_INPUT.code | ConsoleMode.ENABLE_PROCESSED_INPUT.code | ConsoleMode.ENABLE_WINDOW_INPUT.code);
        }
        else {
            this.setConsoleMode(this.getConsoleMode() & ~(ConsoleMode.ENABLE_LINE_INPUT.code | ConsoleMode.ENABLE_ECHO_INPUT.code | ConsoleMode.ENABLE_PROCESSED_INPUT.code | ConsoleMode.ENABLE_WINDOW_INPUT.code));
        }
        super.setEchoEnabled(enabled);
    }
    
    public void setDirectConsole(final boolean flag) {
        this.directConsole = flag;
        Log.debug("Direct console: ", flag);
    }
    
    public Boolean getDirectConsole() {
        return this.directConsole;
    }
    
    public InputStream wrapInIfNeeded(final InputStream in) throws IOException {
        if (this.directConsole && this.isSystemIn(in)) {
            return new InputStream() {
                public int read() throws IOException {
                    return WindowsTerminal.this.readByte();
                }
            };
        }
        return super.wrapInIfNeeded(in);
    }
    
    protected boolean isSystemIn(final InputStream in) throws IOException {
        assert in != null;
        return in == System.in || (in instanceof FileInputStream && ((FileInputStream)in).getFD() == FileDescriptor.in);
    }
    
    private int getConsoleMode() {
        return WindowsSupport.getConsoleMode();
    }
    
    private void setConsoleMode(final int mode) {
        WindowsSupport.setConsoleMode(mode);
    }
    
    private int readByte() {
        return WindowsSupport.readByte();
    }
    
    private int getWindowsTerminalWidth() {
        return WindowsSupport.getWindowsTerminalWidth();
    }
    
    private int getWindowsTerminalHeight() {
        return WindowsSupport.getWindowsTerminalHeight();
    }
    
    static {
        ANSI = WindowsTerminal.class.getName() + ".ansi";
    }
    
    public enum ConsoleMode
    {
        ENABLE_LINE_INPUT(2), 
        ENABLE_ECHO_INPUT(4), 
        ENABLE_PROCESSED_INPUT(1), 
        ENABLE_WINDOW_INPUT(8), 
        ENABLE_MOUSE_INPUT(16), 
        ENABLE_PROCESSED_OUTPUT(1), 
        ENABLE_WRAP_AT_EOL_OUTPUT(2);
        
        public final int code;
        
        private ConsoleMode(final int code) {
            this.code = code;
        }
    }
}
