// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.java;

import java.util.Set;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import org.bukkit.plugin.AuthorNagException;
import java.util.logging.Level;
import java.net.URL;
import java.util.Map;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader
{
    private final JavaPluginLoader loader;
    private final Map<String, Class<?>> classes;
    final boolean extended;
    
    public PluginClassLoader(final JavaPluginLoader loader, final URL[] urls, final ClassLoader parent) {
        this(loader, urls, parent, null);
        if (loader.warn) {
            if (this.extended) {
                loader.server.getLogger().log(Level.WARNING, "PluginClassLoader not intended to be extended by " + this.getClass() + ", and may be final in a future version of Bukkit");
            }
            else {
                loader.server.getLogger().log(Level.WARNING, "Constructor \"public PluginClassLoader(JavaPluginLoader, URL[], ClassLoader)\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            }
            loader.warn = false;
        }
    }
    
    PluginClassLoader(final JavaPluginLoader loader, final URL[] urls, final ClassLoader parent, final Object methodSignature) {
        super(urls, parent);
        this.classes = new HashMap<String, Class<?>>();
        this.extended = (this.getClass() != PluginClassLoader.class);
        Validate.notNull(loader, "Loader cannot be null");
        this.loader = loader;
    }
    
    public void addURL(final URL url) {
        super.addURL(url);
    }
    
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        return this.extended ? this.findClass(name, true) : this.findClass0(name, true);
    }
    
    @Deprecated
    protected Class<?> findClass(final String name, final boolean checkGlobal) throws ClassNotFoundException {
        if (this.loader.warn) {
            this.loader.server.getLogger().log(Level.WARNING, "Method \"protected Class<?> findClass(String, boolean)\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            this.loader.warn = false;
        }
        return this.findClass0(name, checkGlobal);
    }
    
    Class<?> findClass0(final String name, final boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
            throw new ClassNotFoundException(name);
        }
        Class<?> result = this.classes.get(name);
        if (result == null) {
            if (checkGlobal) {
                result = (this.loader.extended ? this.loader.getClassByName(name) : this.loader.getClassByName0(name));
            }
            if (result == null) {
                result = super.findClass(name);
                if (result != null) {
                    if (this.loader.extended) {
                        this.loader.setClass(name, result);
                    }
                    else {
                        this.loader.setClass0(name, result);
                    }
                }
            }
            this.classes.put(name, result);
        }
        return result;
    }
    
    @Deprecated
    public Set<String> getClasses() {
        if (this.loader.warn) {
            this.loader.server.getLogger().log(Level.WARNING, "Method \"public Set<String> getClasses()\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            this.loader.warn = false;
        }
        return this.getClasses0();
    }
    
    Set<String> getClasses0() {
        return this.classes.keySet();
    }
}
