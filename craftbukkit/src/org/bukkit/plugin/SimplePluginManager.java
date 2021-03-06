// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import java.util.WeakHashMap;
import org.bukkit.permissions.PermissionDefault;
import com.google.common.collect.ImmutableSet;
import java.lang.reflect.Method;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;
import org.bukkit.command.Command;
import org.bukkit.event.HandlerList;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.util.FileUtil;
import java.util.regex.Matcher;
import java.util.Iterator;
import org.bukkit.command.defaults.TimingsCommand;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.Collection;
import org.apache.commons.lang.Validate;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.permissions.Permissible;
import java.util.Set;
import org.bukkit.permissions.Permission;
import org.bukkit.command.SimpleCommandMap;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;
import org.bukkit.Server;

public final class SimplePluginManager implements PluginManager
{
    private final Server server;
    private final Map<Pattern, PluginLoader> fileAssociations;
    private final List<Plugin> plugins;
    private final Map<String, Plugin> lookupNames;
    private static File updateDirectory;
    private final SimpleCommandMap commandMap;
    private final Map<String, Permission> permissions;
    private final Map<Boolean, Set<Permission>> defaultPerms;
    private final Map<String, Map<Permissible, Boolean>> permSubs;
    private final Map<Boolean, Map<Permissible, Boolean>> defSubs;
    private boolean useTimings;
    
    public SimplePluginManager(final Server instance, final SimpleCommandMap commandMap) {
        this.fileAssociations = new HashMap<Pattern, PluginLoader>();
        this.plugins = new ArrayList<Plugin>();
        this.lookupNames = new HashMap<String, Plugin>();
        this.permissions = new HashMap<String, Permission>();
        this.defaultPerms = new LinkedHashMap<Boolean, Set<Permission>>();
        this.permSubs = new HashMap<String, Map<Permissible, Boolean>>();
        this.defSubs = new HashMap<Boolean, Map<Permissible, Boolean>>();
        this.useTimings = false;
        this.server = instance;
        this.commandMap = commandMap;
        this.defaultPerms.put(true, new HashSet<Permission>());
        this.defaultPerms.put(false, new HashSet<Permission>());
    }
    
    public void registerInterface(final Class<? extends PluginLoader> loader) throws IllegalArgumentException {
        if (PluginLoader.class.isAssignableFrom(loader)) {
            PluginLoader instance;
            try {
                final Constructor<? extends PluginLoader> constructor = loader.getConstructor(Server.class);
                instance = (PluginLoader)constructor.newInstance(this.server);
            }
            catch (NoSuchMethodException ex) {
                final String className = loader.getName();
                throw new IllegalArgumentException(String.format("Class %s does not have a public %s(Server) constructor", className, className), ex);
            }
            catch (Exception ex2) {
                throw new IllegalArgumentException(String.format("Unexpected exception %s while attempting to construct a new instance of %s", ex2.getClass().getName(), loader.getName()), ex2);
            }
            final Pattern[] patterns = instance.getPluginFileFilters();
            synchronized (this) {
                for (final Pattern pattern : patterns) {
                    this.fileAssociations.put(pattern, instance);
                }
            }
            return;
        }
        throw new IllegalArgumentException(String.format("Class %s does not implement interface PluginLoader", loader.getName()));
    }
    
    public Plugin[] loadPlugins(final File directory) {
        Validate.notNull(directory, "Directory cannot be null");
        Validate.isTrue(directory.isDirectory(), "Directory must be a directory");
        final List<Plugin> result = new ArrayList<Plugin>();
        final Set<Pattern> filters = this.fileAssociations.keySet();
        if (!this.server.getUpdateFolder().equals("")) {
            SimplePluginManager.updateDirectory = new File(directory, this.server.getUpdateFolder());
        }
        final Map<String, File> plugins = new HashMap<String, File>();
        final Set<String> loadedPlugins = new HashSet<String>();
        final Map<String, Collection<String>> dependencies = new HashMap<String, Collection<String>>();
        final Map<String, Collection<String>> softDependencies = new HashMap<String, Collection<String>>();
        for (final File file : directory.listFiles()) {
            PluginLoader loader = null;
            for (final Pattern filter : filters) {
                final Matcher match = filter.matcher(file.getName());
                if (match.find()) {
                    loader = this.fileAssociations.get(filter);
                }
            }
            Label_0538: {
                if (loader != null) {
                    PluginDescriptionFile description = null;
                    try {
                        description = loader.getPluginDescription(file);
                    }
                    catch (InvalidDescriptionException ex) {
                        this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
                        break Label_0538;
                    }
                    plugins.put(description.getName(), file);
                    final Collection<String> softDependencySet = description.getSoftDepend();
                    if (softDependencySet != null) {
                        if (softDependencies.containsKey(description.getName())) {
                            softDependencies.get(description.getName()).addAll(softDependencySet);
                        }
                        else {
                            softDependencies.put(description.getName(), new LinkedList<String>(softDependencySet));
                        }
                    }
                    final Collection<String> dependencySet = description.getDepend();
                    if (dependencySet != null) {
                        dependencies.put(description.getName(), new LinkedList<String>(dependencySet));
                    }
                    final Collection<String> loadBeforeSet = description.getLoadBefore();
                    if (loadBeforeSet != null) {
                        for (final String loadBeforeTarget : loadBeforeSet) {
                            if (softDependencies.containsKey(loadBeforeTarget)) {
                                softDependencies.get(loadBeforeTarget).add(description.getName());
                            }
                            else {
                                final Collection<String> shortSoftDependency = new LinkedList<String>();
                                shortSoftDependency.add(description.getName());
                                softDependencies.put(loadBeforeTarget, shortSoftDependency);
                            }
                        }
                    }
                }
            }
        }
        while (!plugins.isEmpty()) {
            boolean missingDependency = true;
            Iterator<String> pluginIterator = plugins.keySet().iterator();
            while (pluginIterator.hasNext()) {
                final String plugin = pluginIterator.next();
                if (dependencies.containsKey(plugin)) {
                    final Iterator<String> dependencyIterator = dependencies.get(plugin).iterator();
                    while (dependencyIterator.hasNext()) {
                        final String dependency = dependencyIterator.next();
                        if (loadedPlugins.contains(dependency)) {
                            dependencyIterator.remove();
                        }
                        else {
                            if (!plugins.containsKey(dependency)) {
                                missingDependency = false;
                                final File file2 = plugins.get(plugin);
                                pluginIterator.remove();
                                softDependencies.remove(plugin);
                                dependencies.remove(plugin);
                                this.server.getLogger().log(Level.SEVERE, "Could not load '" + file2.getPath() + "' in folder '" + directory.getPath() + "'", new UnknownDependencyException(dependency));
                                break;
                            }
                            continue;
                        }
                    }
                    if (dependencies.containsKey(plugin) && dependencies.get(plugin).isEmpty()) {
                        dependencies.remove(plugin);
                    }
                }
                if (softDependencies.containsKey(plugin)) {
                    final Iterator<String> softDependencyIterator = softDependencies.get(plugin).iterator();
                    while (softDependencyIterator.hasNext()) {
                        final String softDependency = softDependencyIterator.next();
                        if (!plugins.containsKey(softDependency)) {
                            softDependencyIterator.remove();
                        }
                    }
                    if (softDependencies.get(plugin).isEmpty()) {
                        softDependencies.remove(plugin);
                    }
                }
                if (!dependencies.containsKey(plugin) && !softDependencies.containsKey(plugin) && plugins.containsKey(plugin)) {
                    final File file = plugins.get(plugin);
                    pluginIterator.remove();
                    missingDependency = false;
                    try {
                        result.add(this.loadPlugin(file));
                        loadedPlugins.add(plugin);
                    }
                    catch (InvalidPluginException ex2) {
                        this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex2);
                    }
                }
            }
            if (missingDependency) {
                pluginIterator = plugins.keySet().iterator();
                while (pluginIterator.hasNext()) {
                    final String plugin = pluginIterator.next();
                    if (!dependencies.containsKey(plugin)) {
                        softDependencies.remove(plugin);
                        missingDependency = false;
                        final File file = plugins.get(plugin);
                        pluginIterator.remove();
                        try {
                            result.add(this.loadPlugin(file));
                            loadedPlugins.add(plugin);
                            break;
                        }
                        catch (InvalidPluginException ex2) {
                            this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex2);
                        }
                    }
                }
                if (!missingDependency) {
                    continue;
                }
                softDependencies.clear();
                dependencies.clear();
                final Iterator<File> failedPluginIterator = plugins.values().iterator();
                while (failedPluginIterator.hasNext()) {
                    final File file = failedPluginIterator.next();
                    failedPluginIterator.remove();
                    this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': circular dependency detected");
                }
            }
        }
        TimingsCommand.timingStart = System.nanoTime();
        return result.toArray(new Plugin[result.size()]);
    }
    
    public synchronized Plugin loadPlugin(final File file) throws InvalidPluginException, UnknownDependencyException {
        Validate.notNull(file, "File cannot be null");
        this.checkUpdate(file);
        final Set<Pattern> filters = this.fileAssociations.keySet();
        Plugin result = null;
        for (final Pattern filter : filters) {
            final String name = file.getName();
            final Matcher match = filter.matcher(name);
            if (match.find()) {
                final PluginLoader loader = this.fileAssociations.get(filter);
                result = loader.loadPlugin(file);
            }
        }
        if (result != null) {
            this.plugins.add(result);
            this.lookupNames.put(result.getDescription().getName(), result);
        }
        return result;
    }
    
    private void checkUpdate(final File file) {
        if (SimplePluginManager.updateDirectory == null || !SimplePluginManager.updateDirectory.isDirectory()) {
            return;
        }
        final File updateFile = new File(SimplePluginManager.updateDirectory, file.getName());
        if (updateFile.isFile() && FileUtil.copy(updateFile, file)) {
            updateFile.delete();
        }
    }
    
    public synchronized Plugin getPlugin(final String name) {
        return this.lookupNames.get(name);
    }
    
    public synchronized Plugin[] getPlugins() {
        return this.plugins.toArray(new Plugin[0]);
    }
    
    public boolean isPluginEnabled(final String name) {
        final Plugin plugin = this.getPlugin(name);
        return this.isPluginEnabled(plugin);
    }
    
    public boolean isPluginEnabled(final Plugin plugin) {
        return plugin != null && this.plugins.contains(plugin) && plugin.isEnabled();
    }
    
    public void enablePlugin(final Plugin plugin) {
        if (!plugin.isEnabled()) {
            final List<Command> pluginCommands = PluginCommandYamlParser.parse(plugin);
            if (!pluginCommands.isEmpty()) {
                this.commandMap.registerAll(plugin.getDescription().getName(), pluginCommands);
            }
            try {
                plugin.getPluginLoader().enablePlugin(plugin);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            HandlerList.bakeAll();
        }
    }
    
    public void disablePlugins() {
        final Plugin[] plugins = this.getPlugins();
        for (int i = plugins.length - 1; i >= 0; --i) {
            this.disablePlugin(plugins[i]);
        }
    }
    
    public void disablePlugin(final Plugin plugin) {
        if (plugin.isEnabled()) {
            try {
                plugin.getPluginLoader().disablePlugin(plugin);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            try {
                this.server.getScheduler().cancelTasks(plugin);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while cancelling tasks for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            try {
                this.server.getServicesManager().unregisterAll(plugin);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering services for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            try {
                HandlerList.unregisterAll(plugin);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering events for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            try {
                this.server.getMessenger().unregisterIncomingPluginChannel(plugin);
                this.server.getMessenger().unregisterOutgoingPluginChannel(plugin);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering plugin channels for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
        }
    }
    
    public void clearPlugins() {
        synchronized (this) {
            this.disablePlugins();
            this.plugins.clear();
            this.lookupNames.clear();
            HandlerList.unregisterAll();
            this.fileAssociations.clear();
            this.permissions.clear();
            this.defaultPerms.get(true).clear();
            this.defaultPerms.get(false).clear();
        }
    }
    
    public void callEvent(final Event event) {
        if (event.isAsynchronous()) {
            if (Thread.holdsLock(this)) {
                throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from inside synchronized code.");
            }
            if (this.server.isPrimaryThread()) {
                throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from primary server thread.");
            }
            this.fireEvent(event);
        }
        else {
            synchronized (this) {
                this.fireEvent(event);
            }
        }
    }
    
    private void fireEvent(final Event event) {
        final HandlerList handlers = event.getHandlers();
        final RegisteredListener[] arr$;
        final RegisteredListener[] listeners = arr$ = handlers.getRegisteredListeners();
        for (final RegisteredListener registration : arr$) {
            if (registration.getPlugin().isEnabled()) {
                try {
                    registration.callEvent(event);
                }
                catch (AuthorNagException ex) {
                    final Plugin plugin = registration.getPlugin();
                    if (plugin.isNaggable()) {
                        plugin.setNaggable(false);
                        this.server.getLogger().log(Level.SEVERE, String.format("Nag author(s): '%s' of '%s' about the following: %s", plugin.getDescription().getAuthors(), plugin.getDescription().getFullName(), ex.getMessage()));
                    }
                }
                catch (Throwable ex2) {
                    this.server.getLogger().log(Level.SEVERE, "Could not pass event " + event.getEventName() + " to " + registration.getPlugin().getDescription().getFullName(), ex2);
                }
            }
        }
    }
    
    public void registerEvents(final Listener listener, final Plugin plugin) {
        if (!plugin.isEnabled()) {
            throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");
        }
        for (final Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader().createRegisteredListeners(listener, plugin).entrySet()) {
            this.getEventListeners(this.getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
        }
    }
    
    public void registerEvent(final Class<? extends Event> event, final Listener listener, final EventPriority priority, final EventExecutor executor, final Plugin plugin) {
        this.registerEvent(event, listener, priority, executor, plugin, false);
    }
    
    public void registerEvent(final Class<? extends Event> event, final Listener listener, final EventPriority priority, final EventExecutor executor, final Plugin plugin, final boolean ignoreCancelled) {
        Validate.notNull(listener, "Listener cannot be null");
        Validate.notNull(priority, "Priority cannot be null");
        Validate.notNull(executor, "Executor cannot be null");
        Validate.notNull(plugin, "Plugin cannot be null");
        if (!plugin.isEnabled()) {
            throw new IllegalPluginAccessException("Plugin attempted to register " + event + " while not enabled");
        }
        if (this.useTimings) {
            this.getEventListeners(event).register(new TimedRegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
        }
        else {
            this.getEventListeners(event).register(new RegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
        }
    }
    
    private HandlerList getEventListeners(final Class<? extends Event> type) {
        try {
            final Method method = this.getRegistrationClass(type).getDeclaredMethod("getHandlerList", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            return (HandlerList)method.invoke(null, new Object[0]);
        }
        catch (Exception e) {
            throw new IllegalPluginAccessException(e.toString());
        }
    }
    
    private Class<? extends Event> getRegistrationClass(final Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlerList", (Class<?>[])new Class[0]);
            return clazz;
        }
        catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return this.getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            }
            throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName());
        }
    }
    
    public Permission getPermission(final String name) {
        return this.permissions.get(name.toLowerCase());
    }
    
    public void addPermission(final Permission perm) {
        final String name = perm.getName().toLowerCase();
        if (this.permissions.containsKey(name)) {
            throw new IllegalArgumentException("The permission " + name + " is already defined!");
        }
        this.permissions.put(name, perm);
        this.calculatePermissionDefault(perm);
    }
    
    public Set<Permission> getDefaultPermissions(final boolean op) {
        return (Set<Permission>)ImmutableSet.copyOf((Collection<?>)this.defaultPerms.get(op));
    }
    
    public void removePermission(final Permission perm) {
        this.removePermission(perm.getName());
    }
    
    public void removePermission(final String name) {
        this.permissions.remove(name.toLowerCase());
    }
    
    public void recalculatePermissionDefaults(final Permission perm) {
        if (this.permissions.containsValue(perm)) {
            this.defaultPerms.get(true).remove(perm);
            this.defaultPerms.get(false).remove(perm);
            this.calculatePermissionDefault(perm);
        }
    }
    
    private void calculatePermissionDefault(final Permission perm) {
        if (perm.getDefault() == PermissionDefault.OP || perm.getDefault() == PermissionDefault.TRUE) {
            this.defaultPerms.get(true).add(perm);
            this.dirtyPermissibles(true);
        }
        if (perm.getDefault() == PermissionDefault.NOT_OP || perm.getDefault() == PermissionDefault.TRUE) {
            this.defaultPerms.get(false).add(perm);
            this.dirtyPermissibles(false);
        }
    }
    
    private void dirtyPermissibles(final boolean op) {
        final Set<Permissible> permissibles = this.getDefaultPermSubscriptions(op);
        for (final Permissible p : permissibles) {
            p.recalculatePermissions();
        }
    }
    
    public void subscribeToPermission(final String permission, final Permissible permissible) {
        final String name = permission.toLowerCase();
        Map<Permissible, Boolean> map = this.permSubs.get(name);
        if (map == null) {
            map = new WeakHashMap<Permissible, Boolean>();
            this.permSubs.put(name, map);
        }
        map.put(permissible, true);
    }
    
    public void unsubscribeFromPermission(final String permission, final Permissible permissible) {
        final String name = permission.toLowerCase();
        final Map<Permissible, Boolean> map = this.permSubs.get(name);
        if (map != null) {
            map.remove(permissible);
            if (map.isEmpty()) {
                this.permSubs.remove(name);
            }
        }
    }
    
    public Set<Permissible> getPermissionSubscriptions(final String permission) {
        final String name = permission.toLowerCase();
        final Map<Permissible, Boolean> map = this.permSubs.get(name);
        if (map == null) {
            return (Set<Permissible>)ImmutableSet.of();
        }
        return (Set<Permissible>)ImmutableSet.copyOf((Collection<?>)map.keySet());
    }
    
    public void subscribeToDefaultPerms(final boolean op, final Permissible permissible) {
        Map<Permissible, Boolean> map = this.defSubs.get(op);
        if (map == null) {
            map = new WeakHashMap<Permissible, Boolean>();
            this.defSubs.put(op, map);
        }
        map.put(permissible, true);
    }
    
    public void unsubscribeFromDefaultPerms(final boolean op, final Permissible permissible) {
        final Map<Permissible, Boolean> map = this.defSubs.get(op);
        if (map != null) {
            map.remove(permissible);
            if (map.isEmpty()) {
                this.defSubs.remove(op);
            }
        }
    }
    
    public Set<Permissible> getDefaultPermSubscriptions(final boolean op) {
        final Map<Permissible, Boolean> map = this.defSubs.get(op);
        if (map == null) {
            return (Set<Permissible>)ImmutableSet.of();
        }
        return (Set<Permissible>)ImmutableSet.copyOf((Collection<?>)map.keySet());
    }
    
    public Set<Permission> getPermissions() {
        return new HashSet<Permission>(this.permissions.values());
    }
    
    public boolean useTimings() {
        return this.useTimings;
    }
    
    public void useTimings(final boolean use) {
        this.useTimings = use;
    }
    
    static {
        SimplePluginManager.updateDirectory = null;
    }
}
