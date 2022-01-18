// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import java.io.File;
import java.util.Properties;

public class PropertyManager
{
    public final Properties properties;
    private final IConsoleLogManager loggingAgent;
    private final File c;
    private OptionSet options;
    
    public PropertyManager(final File file1, final IConsoleLogManager iconsolelogmanager) {
        this.properties = new Properties();
        this.options = null;
        this.c = file1;
        this.loggingAgent = iconsolelogmanager;
        if (file1.exists()) {
            FileInputStream fileinputstream = null;
            try {
                fileinputstream = new FileInputStream(file1);
                this.properties.load(fileinputstream);
            }
            catch (Exception exception) {
                iconsolelogmanager.warning("Failed to load " + file1, exception);
                this.a();
            }
            finally {
                if (fileinputstream != null) {
                    try {
                        fileinputstream.close();
                    }
                    catch (IOException ex) {}
                }
            }
        }
        else {
            iconsolelogmanager.warning(file1 + " does not exist");
            this.a();
        }
    }
    
    public PropertyManager(final OptionSet options, final IConsoleLogManager iconsolelogmanager) {
        this((File)options.valueOf("config"), iconsolelogmanager);
        this.options = options;
    }
    
    private <T> T getOverride(final String name, final T value) {
        if (this.options != null && this.options.has(name) && !name.equals("online-mode")) {
            return (T)this.options.valueOf(name);
        }
        return value;
    }
    
    public void a() {
        this.loggingAgent.info("Generating new properties file");
        this.savePropertiesFile();
    }
    
    public void savePropertiesFile() {
        FileOutputStream fileoutputstream = null;
        try {
            if (this.c.exists() && !this.c.canWrite()) {
                return;
            }
            fileoutputstream = new FileOutputStream(this.c);
            this.properties.store(fileoutputstream, "Minecraft server properties");
        }
        catch (Exception exception) {
            this.loggingAgent.warning("Failed to save " + this.c, exception);
            this.a();
        }
        finally {
            if (fileoutputstream != null) {
                try {
                    fileoutputstream.close();
                }
                catch (IOException ex) {}
            }
        }
    }
    
    public File c() {
        return this.c;
    }
    
    public String getString(final String s, final String s1) {
        if (!this.properties.containsKey(s)) {
            this.properties.setProperty(s, s1);
            this.savePropertiesFile();
        }
        return this.getOverride(s, this.properties.getProperty(s, s1));
    }
    
    public int getInt(final String s, final int i) {
        try {
            return this.getOverride(s, Integer.parseInt(this.getString(s, "" + i)));
        }
        catch (Exception exception) {
            this.properties.setProperty(s, "" + i);
            return this.getOverride(s, i);
        }
    }
    
    public boolean getBoolean(final String s, final boolean flag) {
        try {
            return this.getOverride(s, Boolean.parseBoolean(this.getString(s, "" + flag)));
        }
        catch (Exception exception) {
            this.properties.setProperty(s, "" + flag);
            return this.getOverride(s, flag);
        }
    }
    
    public void a(final String s, final Object object) {
        this.properties.setProperty(s, "" + object);
    }
}
