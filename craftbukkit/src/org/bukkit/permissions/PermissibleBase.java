// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.permissions;

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class PermissibleBase implements Permissible
{
    private ServerOperator opable;
    private Permissible parent;
    private final List<PermissionAttachment> attachments;
    private final Map<String, PermissionAttachmentInfo> permissions;
    
    public PermissibleBase(final ServerOperator opable) {
        this.opable = null;
        this.parent = this;
        this.attachments = new LinkedList<PermissionAttachment>();
        this.permissions = new HashMap<String, PermissionAttachmentInfo>();
        this.opable = opable;
        if (opable instanceof Permissible) {
            this.parent = (Permissible)opable;
        }
        this.recalculatePermissions();
    }
    
    public boolean isOp() {
        return this.opable != null && this.opable.isOp();
    }
    
    public void setOp(final boolean value) {
        if (this.opable == null) {
            throw new UnsupportedOperationException("Cannot change op value as no ServerOperator is set");
        }
        this.opable.setOp(value);
    }
    
    public boolean isPermissionSet(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Permission name cannot be null");
        }
        return this.permissions.containsKey(name.toLowerCase());
    }
    
    public boolean isPermissionSet(final Permission perm) {
        if (perm == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }
        return this.isPermissionSet(perm.getName());
    }
    
    public boolean hasPermission(final String inName) {
        if (inName == null) {
            throw new IllegalArgumentException("Permission name cannot be null");
        }
        final String name = inName.toLowerCase();
        if (this.isPermissionSet(name)) {
            return this.permissions.get(name).getValue();
        }
        final Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
        if (perm != null) {
            return perm.getDefault().getValue(this.isOp());
        }
        return Permission.DEFAULT_PERMISSION.getValue(this.isOp());
    }
    
    public boolean hasPermission(final Permission perm) {
        if (perm == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }
        final String name = perm.getName().toLowerCase();
        if (this.isPermissionSet(name)) {
            return this.permissions.get(name).getValue();
        }
        return perm.getDefault().getValue(this.isOp());
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value) {
        if (name == null) {
            throw new IllegalArgumentException("Permission name cannot be null");
        }
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (!plugin.isEnabled()) {
            throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
        }
        final PermissionAttachment result = this.addAttachment(plugin);
        result.setPermission(name, value);
        this.recalculatePermissions();
        return result;
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (!plugin.isEnabled()) {
            throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
        }
        final PermissionAttachment result = new PermissionAttachment(plugin, this.parent);
        this.attachments.add(result);
        this.recalculatePermissions();
        return result;
    }
    
    public void removeAttachment(final PermissionAttachment attachment) {
        if (attachment == null) {
            throw new IllegalArgumentException("Attachment cannot be null");
        }
        if (this.attachments.contains(attachment)) {
            this.attachments.remove(attachment);
            final PermissionRemovedExecutor ex = attachment.getRemovalCallback();
            if (ex != null) {
                ex.attachmentRemoved(attachment);
            }
            this.recalculatePermissions();
            return;
        }
        throw new IllegalArgumentException("Given attachment is not part of Permissible object " + this.parent);
    }
    
    public void recalculatePermissions() {
        this.clearPermissions();
        final Set<Permission> defaults = Bukkit.getServer().getPluginManager().getDefaultPermissions(this.isOp());
        Bukkit.getServer().getPluginManager().subscribeToDefaultPerms(this.isOp(), this.parent);
        for (final Permission perm : defaults) {
            final String name = perm.getName().toLowerCase();
            this.permissions.put(name, new PermissionAttachmentInfo(this.parent, name, null, true));
            Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
            this.calculateChildPermissions(perm.getChildren(), false, null);
        }
        for (final PermissionAttachment attachment : this.attachments) {
            this.calculateChildPermissions(attachment.getPermissions(), false, attachment);
        }
    }
    
    public synchronized void clearPermissions() {
        final Set<String> perms = this.permissions.keySet();
        for (final String name : perms) {
            Bukkit.getServer().getPluginManager().unsubscribeFromPermission(name, this.parent);
        }
        Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(false, this.parent);
        Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(true, this.parent);
        this.permissions.clear();
    }
    
    private void calculateChildPermissions(final Map<String, Boolean> children, final boolean invert, final PermissionAttachment attachment) {
        final Set<String> keys = children.keySet();
        for (final String name : keys) {
            final Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
            final boolean value = children.get(name) ^ invert;
            final String lname = name.toLowerCase();
            this.permissions.put(lname, new PermissionAttachmentInfo(this.parent, lname, attachment, value));
            Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
            if (perm != null) {
                this.calculateChildPermissions(perm.getChildren(), !value, attachment);
            }
        }
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value, final int ticks) {
        if (name == null) {
            throw new IllegalArgumentException("Permission name cannot be null");
        }
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (!plugin.isEnabled()) {
            throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
        }
        final PermissionAttachment result = this.addAttachment(plugin, ticks);
        if (result != null) {
            result.setPermission(name, value);
        }
        return result;
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final int ticks) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (!plugin.isEnabled()) {
            throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
        }
        final PermissionAttachment result = this.addAttachment(plugin);
        if (Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RemoveAttachmentRunnable(result), ticks) == -1) {
            Bukkit.getServer().getLogger().log(Level.WARNING, "Could not add PermissionAttachment to " + this.parent + " for plugin " + plugin.getDescription().getFullName() + ": Scheduler returned -1");
            result.remove();
            return null;
        }
        return result;
    }
    
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return new HashSet<PermissionAttachmentInfo>(this.permissions.values());
    }
    
    private class RemoveAttachmentRunnable implements Runnable
    {
        private PermissionAttachment attachment;
        
        public RemoveAttachmentRunnable(final PermissionAttachment attachment) {
            this.attachment = attachment;
        }
        
        public void run() {
            this.attachment.remove();
        }
    }
}
