// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import java.util.HashMap;
import java.util.Iterator;
import com.google.common.collect.ImmutableMap;
import java.io.Writer;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import java.io.Reader;
import java.io.InputStream;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.permissions.Permission;
import java.util.Map;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

public final class PluginDescriptionFile
{
    private static final Yaml yaml;
    private String name;
    private String main;
    private String classLoaderOf;
    private List<String> depend;
    private List<String> softDepend;
    private List<String> loadBefore;
    private String version;
    private Map<String, Map<String, Object>> commands;
    private String description;
    private List<String> authors;
    private String website;
    private String prefix;
    private boolean database;
    private PluginLoadOrder order;
    private List<Permission> permissions;
    private Map<?, ?> lazyPermissions;
    private PermissionDefault defaultPerm;
    
    public PluginDescriptionFile(final InputStream stream) throws InvalidDescriptionException {
        this.name = null;
        this.main = null;
        this.classLoaderOf = null;
        this.depend = null;
        this.softDepend = null;
        this.loadBefore = null;
        this.version = null;
        this.commands = null;
        this.description = null;
        this.authors = null;
        this.website = null;
        this.prefix = null;
        this.database = false;
        this.order = PluginLoadOrder.POSTWORLD;
        this.permissions = null;
        this.lazyPermissions = null;
        this.defaultPerm = PermissionDefault.OP;
        this.loadMap(this.asMap(PluginDescriptionFile.yaml.load(stream)));
    }
    
    public PluginDescriptionFile(final Reader reader) throws InvalidDescriptionException {
        this.name = null;
        this.main = null;
        this.classLoaderOf = null;
        this.depend = null;
        this.softDepend = null;
        this.loadBefore = null;
        this.version = null;
        this.commands = null;
        this.description = null;
        this.authors = null;
        this.website = null;
        this.prefix = null;
        this.database = false;
        this.order = PluginLoadOrder.POSTWORLD;
        this.permissions = null;
        this.lazyPermissions = null;
        this.defaultPerm = PermissionDefault.OP;
        this.loadMap(this.asMap(PluginDescriptionFile.yaml.load(reader)));
    }
    
    public PluginDescriptionFile(final String pluginName, final String pluginVersion, final String mainClass) {
        this.name = null;
        this.main = null;
        this.classLoaderOf = null;
        this.depend = null;
        this.softDepend = null;
        this.loadBefore = null;
        this.version = null;
        this.commands = null;
        this.description = null;
        this.authors = null;
        this.website = null;
        this.prefix = null;
        this.database = false;
        this.order = PluginLoadOrder.POSTWORLD;
        this.permissions = null;
        this.lazyPermissions = null;
        this.defaultPerm = PermissionDefault.OP;
        this.name = pluginName;
        this.version = pluginVersion;
        this.main = mainClass;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getMain() {
        return this.main;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public PluginLoadOrder getLoad() {
        return this.order;
    }
    
    public List<String> getAuthors() {
        return this.authors;
    }
    
    public String getWebsite() {
        return this.website;
    }
    
    public boolean isDatabaseEnabled() {
        return this.database;
    }
    
    public List<String> getDepend() {
        return this.depend;
    }
    
    public List<String> getSoftDepend() {
        return this.softDepend;
    }
    
    public List<String> getLoadBefore() {
        return this.loadBefore;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public Map<String, Map<String, Object>> getCommands() {
        return this.commands;
    }
    
    public List<Permission> getPermissions() {
        if (this.permissions == null) {
            if (this.lazyPermissions == null) {
                this.permissions = (List<Permission>)ImmutableList.of();
            }
            else {
                this.permissions = (List<Permission>)ImmutableList.copyOf((Collection<?>)Permission.loadPermissions(this.lazyPermissions, "Permission node '%s' in plugin description file for " + this.getFullName() + " is invalid", this.defaultPerm));
                this.lazyPermissions = null;
            }
        }
        return this.permissions;
    }
    
    public PermissionDefault getPermissionDefault() {
        return this.defaultPerm;
    }
    
    public String getFullName() {
        return this.name + " v" + this.version;
    }
    
    public String getClassLoaderOf() {
        return this.classLoaderOf;
    }
    
    public void setDatabaseEnabled(final boolean database) {
        this.database = database;
    }
    
    public void save(final Writer writer) {
        PluginDescriptionFile.yaml.dump(this.saveMap(), writer);
    }
    
    private void loadMap(final Map<?, ?> map) throws InvalidDescriptionException {
        try {
            this.name = map.get("name").toString();
            if (!this.name.matches("^[A-Za-z0-9 _.-]+$")) {
                throw new InvalidDescriptionException("name '" + this.name + "' contains invalid characters.");
            }
        }
        catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "name is not defined");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "name is of wrong type");
        }
        try {
            this.version = map.get("version").toString();
        }
        catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "version is not defined");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "version is of wrong type");
        }
        try {
            this.main = map.get("main").toString();
            if (this.main.startsWith("org.bukkit.")) {
                throw new InvalidDescriptionException("main may not be within the org.bukkit namespace");
            }
        }
        catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "main is not defined");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "main is of wrong type");
        }
        if (map.get("commands") != null) {
            final ImmutableMap.Builder<String, Map<String, Object>> commandsBuilder = ImmutableMap.builder();
            try {
                for (final Map.Entry<?, ?> command : ((Map)map.get("commands")).entrySet()) {
                    final ImmutableMap.Builder<String, Object> commandBuilder = ImmutableMap.builder();
                    if (command.getValue() != null) {
                        for (final Map.Entry<?, ?> commandEntry : ((Map)command.getValue()).entrySet()) {
                            if (commandEntry.getValue() instanceof Iterable) {
                                final ImmutableList.Builder<Object> commandSubList = ImmutableList.builder();
                                for (final Object commandSubListItem : (Iterable)commandEntry.getValue()) {
                                    if (commandSubListItem != null) {
                                        commandSubList.add(commandSubListItem);
                                    }
                                }
                                commandBuilder.put(commandEntry.getKey().toString(), commandSubList.build());
                            }
                            else {
                                if (commandEntry.getValue() == null) {
                                    continue;
                                }
                                commandBuilder.put(commandEntry.getKey().toString(), commandEntry.getValue());
                            }
                        }
                    }
                    commandsBuilder.put(command.getKey().toString(), commandBuilder.build());
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "commands are of wrong type");
            }
            this.commands = commandsBuilder.build();
        }
        if (map.get("class-loader-of") != null) {
            this.classLoaderOf = map.get("class-loader-of").toString();
        }
        if (map.get("depend") != null) {
            final ImmutableList.Builder<String> dependBuilder = ImmutableList.builder();
            try {
                for (final Object dependency : (Iterable)map.get("depend")) {
                    dependBuilder.add(dependency.toString());
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "depend is of wrong type");
            }
            catch (NullPointerException e) {
                throw new InvalidDescriptionException(e, "invalid dependency format");
            }
            this.depend = dependBuilder.build();
        }
        if (map.get("softdepend") != null) {
            final ImmutableList.Builder<String> softDependBuilder = ImmutableList.builder();
            try {
                for (final Object dependency : (Iterable)map.get("softdepend")) {
                    softDependBuilder.add(dependency.toString());
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "softdepend is of wrong type");
            }
            catch (NullPointerException ex4) {
                throw new InvalidDescriptionException(ex4, "invalid soft-dependency format");
            }
            this.softDepend = softDependBuilder.build();
        }
        if (map.get("loadbefore") != null) {
            final ImmutableList.Builder<String> loadBeforeBuilder = ImmutableList.builder();
            try {
                for (final Object predependency : (Iterable)map.get("loadbefore")) {
                    loadBeforeBuilder.add(predependency.toString());
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "loadbefore is of wrong type");
            }
            catch (NullPointerException ex4) {
                throw new InvalidDescriptionException(ex4, "invalid load-before format");
            }
            this.loadBefore = loadBeforeBuilder.build();
        }
        if (map.get("database") != null) {
            try {
                this.database = (boolean)map.get("database");
            }
            catch (ClassCastException ex2) {
                throw new InvalidDescriptionException(ex2, "database is of wrong type");
            }
        }
        if (map.get("website") != null) {
            this.website = map.get("website").toString();
        }
        if (map.get("description") != null) {
            this.description = map.get("description").toString();
        }
        if (map.get("load") != null) {
            try {
                this.order = PluginLoadOrder.valueOf(((String)map.get("load")).toUpperCase().replaceAll("\\W", ""));
            }
            catch (ClassCastException ex2) {
                throw new InvalidDescriptionException(ex2, "load is of wrong type");
            }
            catch (IllegalArgumentException ex5) {
                throw new InvalidDescriptionException(ex5, "load is not a valid choice");
            }
        }
        if (map.get("authors") != null) {
            final ImmutableList.Builder<String> authorsBuilder = ImmutableList.builder();
            if (map.get("author") != null) {
                authorsBuilder.add(map.get("author").toString());
            }
            try {
                for (final Object o : (Iterable)map.get("authors")) {
                    authorsBuilder.add(o.toString());
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "authors are of wrong type");
            }
            catch (NullPointerException ex4) {
                throw new InvalidDescriptionException(ex4, "authors are improperly defined");
            }
            this.authors = authorsBuilder.build();
        }
        else if (map.get("author") != null) {
            this.authors = ImmutableList.of(map.get("author").toString());
        }
        else {
            this.authors = (List<String>)ImmutableList.of();
        }
        if (map.get("default-permission") != null) {
            try {
                this.defaultPerm = PermissionDefault.getByName(map.get("default-permission").toString());
            }
            catch (ClassCastException ex2) {
                throw new InvalidDescriptionException(ex2, "default-permission is of wrong type");
            }
            catch (IllegalArgumentException ex5) {
                throw new InvalidDescriptionException(ex5, "default-permission is not a valid choice");
            }
        }
        try {
            this.lazyPermissions = (Map<?, ?>)map.get("permissions");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "permissions are of the wrong type");
        }
        if (map.get("prefix") != null) {
            this.prefix = map.get("prefix").toString();
        }
    }
    
    private Map<String, Object> saveMap() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", this.name);
        map.put("main", this.main);
        map.put("version", this.version);
        map.put("database", this.database);
        map.put("order", this.order.toString());
        map.put("default-permission", this.defaultPerm.toString());
        if (this.commands != null) {
            map.put("command", this.commands);
        }
        if (this.depend != null) {
            map.put("depend", this.depend);
        }
        if (this.softDepend != null) {
            map.put("softdepend", this.softDepend);
        }
        if (this.website != null) {
            map.put("website", this.website);
        }
        if (this.description != null) {
            map.put("description", this.description);
        }
        if (this.authors.size() == 1) {
            map.put("author", this.authors.get(0));
        }
        else if (this.authors.size() > 1) {
            map.put("authors", this.authors);
        }
        if (this.classLoaderOf != null) {
            map.put("class-loader-of", this.classLoaderOf);
        }
        if (this.prefix != null) {
            map.put("prefix", this.prefix);
        }
        return map;
    }
    
    private Map<?, ?> asMap(final Object object) throws InvalidDescriptionException {
        if (object instanceof Map) {
            return (Map<?, ?>)object;
        }
        throw new InvalidDescriptionException(object + " is not properly structured.");
    }
    
    static {
        yaml = new Yaml(new SafeConstructor());
    }
}
