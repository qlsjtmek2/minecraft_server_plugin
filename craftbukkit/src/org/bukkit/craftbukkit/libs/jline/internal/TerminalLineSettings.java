// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.internal;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.Closeable;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.text.MessageFormat;

public final class TerminalLineSettings
{
    public static final String JLINE_STTY = "org.bukkit.craftbukkit.libs.jline.stty";
    public static final String DEFAULT_STTY = "stty";
    public static final String JLINE_SH = "org.bukkit.craftbukkit.libs.jline.sh";
    public static final String DEFAULT_SH = "sh";
    private String sttyCommand;
    private String shCommand;
    private String config;
    private long configLastFetched;
    
    public TerminalLineSettings() throws IOException, InterruptedException {
        this.sttyCommand = Configuration.getString("org.bukkit.craftbukkit.libs.jline.stty", "stty");
        this.shCommand = Configuration.getString("org.bukkit.craftbukkit.libs.jline.sh", "sh");
        this.config = this.get("-a");
        this.configLastFetched = System.currentTimeMillis();
        Log.debug("Config: ", this.config);
        if (this.config.length() == 0) {
            throw new IOException(MessageFormat.format("Unrecognized stty code: {0}", this.config));
        }
    }
    
    public String getConfig() {
        return this.config;
    }
    
    public void restore() throws IOException, InterruptedException {
        this.set("sane");
    }
    
    public String get(final String args) throws IOException, InterruptedException {
        return this.stty(args);
    }
    
    public void set(final String args) throws IOException, InterruptedException {
        this.stty(args);
    }
    
    public int getProperty(final String name) {
        assert name != null;
        final long currentTime = System.currentTimeMillis();
        try {
            if (this.config == null || currentTime - this.configLastFetched > 1000L) {
                this.config = this.get("-a");
            }
        }
        catch (Exception e) {
            Log.debug("Failed to query stty ", name, "\n", e);
        }
        if (currentTime - this.configLastFetched > 1000L) {
            this.configLastFetched = currentTime;
        }
        return getProperty(name, this.config);
    }
    
    protected static int getProperty(final String name, final String stty) {
        Pattern pattern = Pattern.compile(name + "\\s+=\\s+([^;]*)[;\\n\\r]");
        Matcher matcher = pattern.matcher(stty);
        if (!matcher.find()) {
            pattern = Pattern.compile(name + "\\s+([^;]*)[;\\n\\r]");
            matcher = pattern.matcher(stty);
            if (!matcher.find()) {
                pattern = Pattern.compile("(\\S*)\\s+" + name);
                matcher = pattern.matcher(stty);
                if (!matcher.find()) {
                    return -1;
                }
            }
        }
        return parseControlChar(matcher.group(1));
    }
    
    private static int parseControlChar(final String str) {
        if ("<undef>".equals(str)) {
            return -1;
        }
        if (str.charAt(0) == '0') {
            return Integer.parseInt(str, 8);
        }
        if (str.charAt(0) >= '1' && str.charAt(0) <= '9') {
            return Integer.parseInt(str, 10);
        }
        if (str.charAt(0) == '^') {
            if (str.charAt(1) == '?') {
                return 127;
            }
            return str.charAt(1) - '@';
        }
        else {
            if (str.charAt(0) != 'M' || str.charAt(1) != '-') {
                return str.charAt(0);
            }
            if (str.charAt(2) != '^') {
                return str.charAt(2) + '\u0080';
            }
            if (str.charAt(3) == '?') {
                return 255;
            }
            return str.charAt(3) - '@' + '\u0080';
        }
    }
    
    private String stty(final String args) throws IOException, InterruptedException {
        assert args != null;
        return this.exec(String.format("%s %s < /dev/tty", this.sttyCommand, args));
    }
    
    private String exec(final String cmd) throws IOException, InterruptedException {
        assert cmd != null;
        return this.exec(this.shCommand, "-c", cmd);
    }
    
    private String exec(final String... cmd) throws IOException, InterruptedException {
        assert cmd != null;
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Log.trace("Running: ", cmd);
        final Process p = Runtime.getRuntime().exec(cmd);
        InputStream in = null;
        InputStream err = null;
        OutputStream out = null;
        try {
            in = p.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
                bout.write(c);
            }
            err = p.getErrorStream();
            while ((c = err.read()) != -1) {
                bout.write(c);
            }
            out = p.getOutputStream();
            p.waitFor();
        }
        finally {
            close(in, out, err);
        }
        final String result = bout.toString();
        Log.trace("Result: ", result);
        return result;
    }
    
    private static void close(final Closeable... closeables) {
        for (final Closeable c : closeables) {
            try {
                c.close();
            }
            catch (Exception ex) {}
        }
    }
}
