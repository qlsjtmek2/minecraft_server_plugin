// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.permissions;

import java.util.LinkedHashMap;
import org.bukkit.plugin.Plugin;
import java.util.Map;

public class PermissionAttachment
{
    private PermissionRemovedExecutor removed;
    private final Map<String, Boolean> permissions;
    private final Permissible permissible;
    private final Plugin plugin;
    
    public PermissionAttachment(final Plugin plugin, final Permissible Permissible) {
        this.permissions = new LinkedHashMap<String, Boolean>();
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (!plugin.isEnabled()) {
            throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
        }
        this.permissible = Permissible;
        this.plugin = plugin;
    }
    
    public Plugin getPlugin() {
        return this.plugin;
    }
    
    public void setRemovalCallback(final PermissionRemovedExecutor ex) {
        this.removed = ex;
    }
    
    public PermissionRemovedExecutor getRemovalCallback() {
        return this.removed;
    }
    
    public Permissible getPermissible() {
        return this.permissible;
    }
    
    public Map<String, Boolean> getPermissions() {
        return new LinkedHashMap<String, Boolean>(this.permissions);
    }
    
    public void setPermission(final String name, final boolean value) {
        this.permissions.put(name.toLowerCase(), value);
        this.permissible.recalculatePermissions();
    }
    
    public void setPermission(final Permission perm, final boolean value) {
        this.setPermission(perm.getName(), value);
    }
    
    public void unsetPermission(final String name) {
        this.permissions.remove(name.toLowerCase());
        this.permissible.recalculatePermissions();
    }
    
    public void unsetPermission(final Permission perm) {
        this.unsetPermission(perm.getName());
    }
    
    public boolean remove() {
        try {
            this.permissible.removeAttachment(this);
            return true;
        }
        catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
