// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.permissions;

import java.util.Map;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

public final class DefaultPermissions
{
    private static final String ROOT = "craftbukkit";
    private static final String LEGACY_PREFIX = "craft";
    
    public static Permission registerPermission(final Permission perm) {
        return registerPermission(perm, true);
    }
    
    public static Permission registerPermission(final Permission perm, final boolean withLegacy) {
        Permission result = perm;
        try {
            Bukkit.getPluginManager().addPermission(perm);
        }
        catch (IllegalArgumentException ex) {
            result = Bukkit.getPluginManager().getPermission(perm.getName());
        }
        if (withLegacy) {
            final Permission legacy = new Permission("craft" + result.getName(), result.getDescription(), PermissionDefault.FALSE);
            legacy.getChildren().put(result.getName(), true);
            registerPermission(perm, false);
        }
        return result;
    }
    
    public static Permission registerPermission(final Permission perm, final Permission parent) {
        parent.getChildren().put(perm.getName(), true);
        return registerPermission(perm);
    }
    
    public static Permission registerPermission(final String name, final String desc) {
        final Permission perm = registerPermission(new Permission(name, desc));
        return perm;
    }
    
    public static Permission registerPermission(final String name, final String desc, final Permission parent) {
        final Permission perm = registerPermission(name, desc);
        parent.getChildren().put(perm.getName(), true);
        return perm;
    }
    
    public static Permission registerPermission(final String name, final String desc, final PermissionDefault def) {
        final Permission perm = registerPermission(new Permission(name, desc, def));
        return perm;
    }
    
    public static Permission registerPermission(final String name, final String desc, final PermissionDefault def, final Permission parent) {
        final Permission perm = registerPermission(name, desc, def);
        parent.getChildren().put(perm.getName(), true);
        return perm;
    }
    
    public static Permission registerPermission(final String name, final String desc, final PermissionDefault def, final Map<String, Boolean> children) {
        final Permission perm = registerPermission(new Permission(name, desc, def, children));
        return perm;
    }
    
    public static Permission registerPermission(final String name, final String desc, final PermissionDefault def, final Map<String, Boolean> children, final Permission parent) {
        final Permission perm = registerPermission(name, desc, def, children);
        parent.getChildren().put(perm.getName(), true);
        return perm;
    }
    
    public static void registerCorePermissions() {
        final Permission parent = registerPermission("craftbukkit", "Gives the user the ability to use all CraftBukkit utilities and commands");
        CommandPermissions.registerPermissions(parent);
        BroadcastPermissions.registerPermissions(parent);
        parent.recalculatePermissibles();
    }
}
