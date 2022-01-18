// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.java;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.TimedRegisteredListener;
import org.bukkit.event.EventException;
import org.bukkit.plugin.EventExecutor;
import java.util.Arrays;
import org.bukkit.event.EventHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import org.bukkit.plugin.RegisteredListener;
import java.util.Set;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.jar.JarEntry;
import java.io.InputStream;
import org.yaml.snakeyaml.error.YAMLException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.jar.JarFile;
import org.bukkit.plugin.AuthorNagException;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import org.bukkit.plugin.PluginDescriptionFile;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import org.bukkit.plugin.UnknownDependencyException;
import com.google.common.collect.ImmutableList;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import java.io.FileNotFoundException;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.util.logging.Level;
import org.bukkit.Warning;
import org.apache.commons.lang.Validate;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.bukkit.Server;
import org.bukkit.plugin.PluginLoader;

public class JavaPluginLoader implements PluginLoader
{
    final Server server;
    final boolean extended;
    boolean warn;
    private final Pattern[] fileFilters0;
    @Deprecated
    protected final Pattern[] fileFilters;
    private final Map<String, Class<?>> classes0;
    @Deprecated
    protected final Map<String, Class<?>> classes;
    private final Map<String, PluginClassLoader> loaders0;
    @Deprecated
    protected final Map<String, PluginClassLoader> loaders;
    
    public JavaPluginLoader(final Server instance) {
        this.extended = (this.getClass() != JavaPluginLoader.class);
        this.fileFilters0 = new Pattern[] { Pattern.compile("\\.jar$") };
        this.fileFilters = this.fileFilters0;
        this.classes0 = new HashMap<String, Class<?>>();
        this.classes = this.classes0;
        this.loaders0 = new LinkedHashMap<String, PluginClassLoader>();
        this.loaders = this.loaders0;
        Validate.notNull(instance, "Server cannot be null");
        this.server = instance;
        this.warn = (instance.getWarningState() != Warning.WarningState.OFF);
        if (this.extended && this.warn) {
            this.warn = false;
            instance.getLogger().log(Level.WARNING, "JavaPluginLoader not intended to be extended by " + this.getClass() + ", and may be final in a future version of Bukkit");
        }
    }
    
    public Plugin loadPlugin(final File file) throws InvalidPluginException {
        Validate.notNull(file, "File cannot be null");
        if (!file.exists()) {
            throw new InvalidPluginException(new FileNotFoundException(file.getPath() + " does not exist"));
        }
        PluginDescriptionFile description;
        try {
            description = this.getPluginDescription(file);
        }
        catch (InvalidDescriptionException ex) {
            throw new InvalidPluginException(ex);
        }
        final File dataFolder = new File(file.getParentFile(), description.getName());
        final File oldDataFolder = this.extended ? this.getDataFolder(file) : this.getDataFolder0(file);
        if (!dataFolder.equals(oldDataFolder)) {
            if (dataFolder.isDirectory() && oldDataFolder.isDirectory()) {
                this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) found old-data folder: %s next to the new one: %s", description.getName(), file, oldDataFolder, dataFolder));
            }
            else if (oldDataFolder.isDirectory() && !dataFolder.exists()) {
                if (!oldDataFolder.renameTo(dataFolder)) {
                    throw new InvalidPluginException("Unable to rename old data folder: '" + oldDataFolder + "' to: '" + dataFolder + "'");
                }
                this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) renamed data folder: '%s' to '%s'", description.getName(), file, oldDataFolder, dataFolder));
            }
        }
        if (dataFolder.exists() && !dataFolder.isDirectory()) {
            throw new InvalidPluginException(String.format("Projected datafolder: '%s' for %s (%s) exists and is not a directory", dataFolder, description.getName(), file));
        }
        List<String> depend = description.getDepend();
        if (depend == null) {
            depend = (List<String>)ImmutableList.of();
        }
        for (final String pluginName : depend) {
            if (this.loaders0 == null) {
                throw new UnknownDependencyException(pluginName);
            }
            final PluginClassLoader current = this.loaders0.get(pluginName);
            if (current == null) {
                throw new UnknownDependencyException(pluginName);
            }
        }
        PluginClassLoader loader = null;
        JavaPlugin result = null;
        try {
            final URL[] urls = { file.toURI().toURL() };
            if (description.getClassLoaderOf() != null) {
                loader = this.loaders0.get(description.getClassLoaderOf());
                loader.addURL(urls[0]);
            }
            else {
                loader = new PluginClassLoader(this, urls, this.getClass().getClassLoader(), null);
            }
            final Class<?> jarClass = Class.forName(description.getMain(), true, loader);
            final Class<? extends JavaPlugin> plugin = jarClass.asSubclass(JavaPlugin.class);
            final Constructor<? extends JavaPlugin> constructor = plugin.getConstructor((Class<?>[])new Class[0]);
            result = (JavaPlugin)constructor.newInstance(new Object[0]);
            result.initialize(this, this.server, description, dataFolder, file, loader);
        }
        catch (InvocationTargetException ex2) {
            throw new InvalidPluginException(ex2.getCause());
        }
        catch (Throwable ex3) {
            throw new InvalidPluginException(ex3);
        }
        this.loaders0.put(description.getName(), loader);
        return result;
    }
    
    @Deprecated
    public Plugin loadPlugin(final File file, final boolean ignoreSoftDependencies) throws InvalidPluginException {
        if (this.warn) {
            this.server.getLogger().log(Level.WARNING, "Method \"public Plugin loadPlugin(File, boolean)\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            this.warn = false;
        }
        return this.loadPlugin(file);
    }
    
    @Deprecated
    protected File getDataFolder(final File file) {
        if (this.warn) {
            this.server.getLogger().log(Level.WARNING, "Method \"protected File getDataFolder(File)\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            this.warn = false;
        }
        return this.getDataFolder0(file);
    }
    
    private File getDataFolder0(final File file) {
        File dataFolder = null;
        final String filename = file.getName();
        final int index = file.getName().lastIndexOf(".");
        if (index != -1) {
            final String name = filename.substring(0, index);
            dataFolder = new File(file.getParentFile(), name);
        }
        else {
            dataFolder = new File(file.getParentFile(), filename + "_");
        }
        return dataFolder;
    }
    
    public PluginDescriptionFile getPluginDescription(final File file) throws InvalidDescriptionException {
        Validate.notNull(file, "File cannot be null");
        JarFile jar = null;
        InputStream stream = null;
        try {
            jar = new JarFile(file);
            final JarEntry entry = jar.getJarEntry("plugin.yml");
            if (entry == null) {
                throw new InvalidDescriptionException(new FileNotFoundException("Jar does not contain plugin.yml"));
            }
            stream = jar.getInputStream(entry);
            return new PluginDescriptionFile(stream);
        }
        catch (IOException ex) {
            throw new InvalidDescriptionException(ex);
        }
        catch (YAMLException ex2) {
            throw new InvalidDescriptionException(ex2);
        }
        finally {
            if (jar != null) {
                try {
                    jar.close();
                }
                catch (IOException ex3) {}
            }
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException ex4) {}
            }
        }
    }
    
    public Pattern[] getPluginFileFilters() {
        return this.fileFilters0.clone();
    }
    
    @Deprecated
    public Class<?> getClassByName(final String name) {
        if (this.warn) {
            this.server.getLogger().log(Level.WARNING, "Method \"public Class<?> getClassByName(String)\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            this.warn = false;
        }
        return this.getClassByName0(name);
    }
    
    Class<?> getClassByName0(final String name) {
        Class<?> cachedClass = this.classes0.get(name);
        if (cachedClass != null) {
            return cachedClass;
        }
        for (final String current : this.loaders0.keySet()) {
            final PluginClassLoader loader = this.loaders0.get(current);
            try {
                cachedClass = (loader.extended ? loader.findClass(name, false) : loader.findClass0(name, false));
            }
            catch (ClassNotFoundException ex) {}
            if (cachedClass != null) {
                return cachedClass;
            }
        }
        return null;
    }
    
    @Deprecated
    public void setClass(final String name, final Class<?> clazz) {
        if (this.warn) {
            this.server.getLogger().log(Level.WARNING, "Method \"public void setClass(String, Class<?>)\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            this.warn = false;
        }
        this.setClass0(name, clazz);
    }
    
    void setClass0(final String name, final Class<?> clazz) {
        if (!this.classes0.containsKey(name)) {
            this.classes0.put(name, clazz);
            if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                final Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.registerClass(serializable);
            }
        }
    }
    
    @Deprecated
    public void removeClass(final String name) {
        if (this.warn) {
            this.server.getLogger().log(Level.WARNING, "Method \"public void removeClass(String)\" is Deprecated, and may be removed in a future version of Bukkit", new AuthorNagException(""));
            this.warn = false;
        }
        this.removeClass0(name);
    }
    
    private void removeClass0(final String name) {
        final Class<?> clazz = this.classes0.remove(name);
        try {
            if (clazz != null && ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                final Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.unregisterClass(serializable);
            }
        }
        catch (NullPointerException ex) {}
    }
    
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(final Listener listener, final Plugin plugin) {
        Validate.notNull(plugin, "Plugin can not be null");
        Validate.notNull(listener, "Listener can not be null");
        final boolean useTimings = this.server.getPluginManager().useTimings();
        final Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();
        Set<Method> methods;
        try {
            final Method[] publicMethods = listener.getClass().getMethods();
            methods = new HashSet<Method>(publicMethods.length, Float.MAX_VALUE);
            for (final Method method : publicMethods) {
                methods.add(method);
            }
            for (final Method method : listener.getClass().getDeclaredMethods()) {
                methods.add(method);
            }
        }
        catch (NoClassDefFoundError e) {
            plugin.getLogger().severe("Plugin " + plugin.getDescription().getFullName() + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");
            return ret;
        }
        for (final Method method2 : methods) {
            final EventHandler eh = method2.getAnnotation(EventHandler.class);
            if (eh == null) {
                continue;
            }
            final Class<?> checkClass;
            if (method2.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method2.getParameterTypes()[0])) {
                plugin.getLogger().severe(plugin.getDescription().getFullName() + " attempted to register an invalid EventHandler method signature \"" + method2.toGenericString() + "\" in " + listener.getClass());
            }
            else {
                final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
                method2.setAccessible(true);
                Set<RegisteredListener> eventSet = ret.get(eventClass);
                if (eventSet == null) {
                    eventSet = new HashSet<RegisteredListener>();
                    ret.put(eventClass, eventSet);
                }
                Class<?> clazz = eventClass;
                while (Event.class.isAssignableFrom(clazz)) {
                    if (clazz.getAnnotation(Deprecated.class) != null) {
                        final Warning warning = clazz.getAnnotation(Warning.class);
                        final Warning.WarningState warningState = this.server.getWarningState();
                        if (!warningState.printFor(warning)) {
                            break;
                        }
                        plugin.getLogger().log(Level.WARNING, String.format("\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated. \"%s\"; please notify the authors %s.", plugin.getDescription().getFullName(), clazz.getName(), method2.toGenericString(), (warning != null && warning.reason().length() != 0) ? warning.reason() : "Server performance will be affected", Arrays.toString(plugin.getDescription().getAuthors().toArray())), (warningState == Warning.WarningState.ON) ? new AuthorNagException((String)null) : null);
                        break;
                    }
                    else {
                        clazz = clazz.getSuperclass();
                    }
                }
                final EventExecutor executor = new EventExecutor() {
                    public void execute(final Listener listener, final Event event) throws EventException {
                        try {
                            if (!eventClass.isAssignableFrom(event.getClass())) {
                                return;
                            }
                            method2.invoke(listener, event);
                        }
                        catch (InvocationTargetException ex) {
                            throw new EventException(ex.getCause());
                        }
                        catch (Throwable t) {
                            throw new EventException(t);
                        }
                    }
                };
                eventSet.add(new TimedRegisteredListener(listener, executor, eh.priority(), plugin, eh.ignoreCancelled()));
            }
        }
        return ret;
    }
    
    public void enablePlugin(final Plugin plugin) {
        Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
        if (!plugin.isEnabled()) {
            plugin.getLogger().info("Enabling " + plugin.getDescription().getFullName());
            final JavaPlugin jPlugin = (JavaPlugin)plugin;
            final String pluginName = jPlugin.getDescription().getName();
            if (!this.loaders0.containsKey(pluginName)) {
                this.loaders0.put(pluginName, (PluginClassLoader)jPlugin.getClassLoader());
            }
            try {
                jPlugin.setEnabled(true);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            this.server.getPluginManager().callEvent(new PluginEnableEvent(plugin));
        }
    }
    
    public void disablePlugin(final Plugin plugin) {
        Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
        if (plugin.isEnabled()) {
            final String message = String.format("Disabling %s", plugin.getDescription().getFullName());
            plugin.getLogger().info(message);
            this.server.getPluginManager().callEvent(new PluginDisableEvent(plugin));
            final JavaPlugin jPlugin = (JavaPlugin)plugin;
            final ClassLoader cloader = jPlugin.getClassLoader();
            try {
                jPlugin.setEnabled(false);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            this.loaders0.remove(jPlugin.getDescription().getName());
            if (cloader instanceof PluginClassLoader) {
                final PluginClassLoader loader = (PluginClassLoader)cloader;
                final Set<String> names = loader.extended ? loader.getClasses() : loader.getClasses0();
                for (final String name : names) {
                    if (this.extended) {
                        this.removeClass(name);
                    }
                    else {
                        this.removeClass0(name);
                    }
                }
            }
        }
    }
}
