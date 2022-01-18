// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.internal;

import java.nio.charset.Charset;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.util.Properties;

public class Configuration
{
    public static final String JLINE_INPUTRC = "org.bukkit.craftbukkit.libs.jline.inputrc";
    public static final String JLINE_RC = ".org.bukkit.craftbukkit.libs.jline.rc";
    public static final String INPUT_RC = ".inputrc";
    private static Configuration configuration;
    private final Properties props;
    private final URL jlinercUrl;
    
    public static Configuration getConfig() {
        return getConfig((URL)null);
    }
    
    public static Configuration getConfig(final String fileOrUrl) {
        return getConfig(getUrlFrom(fileOrUrl));
    }
    
    public static Configuration getConfig(URL url) {
        if (url == null) {
            url = getUrlFrom(new File(getUserHome(), ".org.bukkit.craftbukkit.libs.jline.rc"));
        }
        if (Configuration.configuration == null || !url.equals(Configuration.configuration.jlinercUrl)) {
            Configuration.configuration = new Configuration(url);
        }
        return Configuration.configuration;
    }
    
    public Configuration() {
        this(getUrlFrom(new File(getUserHome(), ".org.bukkit.craftbukkit.libs.jline.rc")));
    }
    
    public Configuration(final File inputRc) {
        this(getUrlFrom(inputRc));
    }
    
    public Configuration(final String fileOrUrl) {
        this(getUrlFrom(fileOrUrl));
    }
    
    public Configuration(final URL jlinercUrl) {
        this.jlinercUrl = jlinercUrl;
        this.props = this.loadProps();
    }
    
    protected Properties loadProps() {
        final Properties props = new Properties();
        try {
            final InputStream input = this.jlinercUrl.openStream();
            try {
                props.load(new BufferedInputStream(input));
            }
            finally {
                try {
                    input.close();
                }
                catch (IOException ex) {}
            }
        }
        catch (IOException e) {
            if (this.jlinercUrl.getProtocol().equals("file")) {
                final File file = new File(this.jlinercUrl.getPath());
                if (file.exists()) {
                    Log.warn("Unable to read user configuration: ", this.jlinercUrl, e);
                }
            }
            else {
                Log.warn("Unable to read user configuration: ", this.jlinercUrl, e);
            }
        }
        return props;
    }
    
    public static URL getUrlFrom(final String fileOrUrl) {
        if (fileOrUrl == null) {
            return null;
        }
        try {
            return new URL(fileOrUrl);
        }
        catch (MalformedURLException e) {
            return getUrlFrom(new File(fileOrUrl));
        }
    }
    
    public static URL getUrlFrom(final File inputRc) {
        try {
            return (inputRc != null) ? inputRc.toURI().toURL() : null;
        }
        catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public String string(final String name, final String defaultValue) {
        assert name != null;
        String value = System.getProperty(name);
        if (value == null) {
            value = this.props.getProperty(name);
            if (value == null) {
                value = defaultValue;
            }
        }
        return value;
    }
    
    public String string(final String name) {
        return this.string(name, null);
    }
    
    public boolean bool(final String name, final boolean defaultValue) {
        final String value = this.string(name, null);
        if (value == null) {
            return defaultValue;
        }
        return value.length() == 0 || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("on") || value.equalsIgnoreCase("true");
    }
    
    public boolean bool(final String name) {
        return this.bool(name, false);
    }
    
    public static String getString(final String name, final String defaultValue) {
        return getConfig().string(name, defaultValue);
    }
    
    public static String getString(final String name) {
        return getString(name, null);
    }
    
    public static boolean getBoolean(final String name, final boolean defaultValue) {
        return getConfig().bool(name, defaultValue);
    }
    
    public static boolean getBoolean(final String name) {
        return getBoolean(name, false);
    }
    
    public static File getUserHome() {
        return new File(System.getProperty("user.home"));
    }
    
    public static String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }
    
    public static String getFileEncoding() {
        return System.getProperty("file.encoding");
    }
    
    public static String getEncoding() {
        final String ctype = System.getenv("LC_CTYPE");
        if (ctype != null && ctype.indexOf(46) > 0) {
            return ctype.substring(ctype.indexOf(46) + 1);
        }
        return System.getProperty("input.encoding", Charset.defaultCharset().name());
    }
}
