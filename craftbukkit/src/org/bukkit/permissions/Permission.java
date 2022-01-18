// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.permissions;

import org.apache.commons.lang.Validate;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.PluginManager;
import java.util.Iterator;
import org.bukkit.Bukkit;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Map;

public class Permission
{
    public static final PermissionDefault DEFAULT_PERMISSION;
    private final String name;
    private final Map<String, Boolean> children;
    private PermissionDefault defaultValue;
    private String description;
    
    public Permission(final String name) {
        this(name, null, null, null);
    }
    
    public Permission(final String name, final String description) {
        this(name, description, null, null);
    }
    
    public Permission(final String name, final PermissionDefault defaultValue) {
        this(name, null, defaultValue, null);
    }
    
    public Permission(final String name, final String description, final PermissionDefault defaultValue) {
        this(name, description, defaultValue, null);
    }
    
    public Permission(final String name, final Map<String, Boolean> children) {
        this(name, null, null, children);
    }
    
    public Permission(final String name, final String description, final Map<String, Boolean> children) {
        this(name, description, null, children);
    }
    
    public Permission(final String name, final PermissionDefault defaultValue, final Map<String, Boolean> children) {
        this(name, null, defaultValue, children);
    }
    
    public Permission(final String name, final String description, final PermissionDefault defaultValue, final Map<String, Boolean> children) {
        this.children = new LinkedHashMap<String, Boolean>();
        this.defaultValue = Permission.DEFAULT_PERMISSION;
        this.name = name;
        this.description = ((description == null) ? "" : description);
        if (defaultValue != null) {
            this.defaultValue = defaultValue;
        }
        if (children != null) {
            this.children.putAll(children);
        }
        this.recalculatePermissibles();
    }
    
    public String getName() {
        return this.name;
    }
    
    public Map<String, Boolean> getChildren() {
        return this.children;
    }
    
    public PermissionDefault getDefault() {
        return this.defaultValue;
    }
    
    public void setDefault(final PermissionDefault value) {
        if (this.defaultValue == null) {
            throw new IllegalArgumentException("Default value cannot be null");
        }
        this.defaultValue = value;
        this.recalculatePermissibles();
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String value) {
        if (value == null) {
            this.description = "";
        }
        else {
            this.description = value;
        }
    }
    
    public Set<Permissible> getPermissibles() {
        return Bukkit.getServer().getPluginManager().getPermissionSubscriptions(this.name);
    }
    
    public void recalculatePermissibles() {
        final Set<Permissible> perms = this.getPermissibles();
        Bukkit.getServer().getPluginManager().recalculatePermissionDefaults(this);
        for (final Permissible p : perms) {
            p.recalculatePermissions();
        }
    }
    
    public Permission addParent(final String name, final boolean value) {
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        final String lname = name.toLowerCase();
        Permission perm = pm.getPermission(lname);
        if (perm == null) {
            perm = new Permission(lname);
            pm.addPermission(perm);
        }
        this.addParent(perm, value);
        return perm;
    }
    
    public void addParent(final Permission perm, final boolean value) {
        perm.getChildren().put(this.getName(), value);
        perm.recalculatePermissibles();
    }
    
    public static List<Permission> loadPermissions(final Map<?, ?> data, final String error, final PermissionDefault def) {
        final List<Permission> result = new ArrayList<Permission>();
        for (final Map.Entry<?, ?> entry : data.entrySet()) {
            try {
                result.add(loadPermission(entry.getKey().toString(), (Map<?, ?>)entry.getValue(), def, result));
            }
            catch (Throwable ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, String.format(error, entry.getKey()), ex);
            }
        }
        return result;
    }
    
    public static Permission loadPermission(final String name, final Map<String, Object> data) {
        return loadPermission(name, data, Permission.DEFAULT_PERMISSION, null);
    }
    
    public static Permission loadPermission(final String name, final Map<?, ?> data, PermissionDefault def, final List<Permission> output) {
        Validate.notNull(name, "Name cannot be null");
        Validate.notNull(data, "Data cannot be null");
        String desc = null;
        Map<String, Boolean> children = null;
        if (data.get("default") != null) {
            final PermissionDefault value = PermissionDefault.getByName(data.get("default").toString());
            if (value == null) {
                throw new IllegalArgumentException("'default' key contained unknown value");
            }
            def = value;
        }
        if (data.get("children") != null) {
            final Object childrenNode = data.get("children");
            if (childrenNode instanceof Iterable) {
                children = new LinkedHashMap<String, Boolean>();
                for (final Object child : (Iterable)childrenNode) {
                    if (child != null) {
                        children.put(child.toString(), Boolean.TRUE);
                    }
                }
            }
            else {
                if (!(childrenNode instanceof Map)) {
                    throw new IllegalArgumentException("'children' key is of wrong type");
                }
                children = extractChildren((Map<?, ?>)childrenNode, name, def, output);
            }
        }
        if (data.get("description") != null) {
            desc = data.get("description").toString();
        }
        return new Permission(name, desc, def, children);
    }
    
    private static Map<String, Boolean> extractChildren(final Map<?, ?> input, final String name, final PermissionDefault def, final List<Permission> output) {
        final Map<String, Boolean> children = new LinkedHashMap<String, Boolean>();
        for (final Map.Entry<?, ?> entry : input.entrySet()) {
            if (!(entry.getValue() instanceof Boolean)) {
                if (entry.getValue() instanceof Map) {
                    try {
                        final Permission perm = loadPermission(entry.getKey().toString(), (Map<?, ?>)entry.getValue(), def, output);
                        children.put(perm.getName(), Boolean.TRUE);
                        if (output == null) {
                            continue;
                        }
                        output.add(perm);
                        continue;
                    }
                    catch (Throwable ex) {
                        throw new IllegalArgumentException("Permission node '" + entry.getKey().toString() + "' in child of " + name + " is invalid", ex);
                    }
                }
                throw new IllegalArgumentException("Child '" + entry.getKey().toString() + "' contains invalid value");
            }
            children.put(entry.getKey().toString(), (Boolean)entry.getValue());
        }
        return children;
    }
    
    static {
        DEFAULT_PERMISSION = PermissionDefault.OP;
    }
}
