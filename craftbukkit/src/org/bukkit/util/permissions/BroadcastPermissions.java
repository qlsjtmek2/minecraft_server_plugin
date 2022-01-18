// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.permissions;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.permissions.Permission;

public final class BroadcastPermissions
{
    private static final String ROOT = "bukkit.broadcast";
    private static final String PREFIX = "bukkit.broadcast.";
    
    public static Permission registerPermissions(final Permission parent) {
        final Permission broadcasts = DefaultPermissions.registerPermission("bukkit.broadcast", "Allows the user to receive all broadcast messages", parent);
        DefaultPermissions.registerPermission("bukkit.broadcast.admin", "Allows the user to receive administrative broadcasts", PermissionDefault.OP, broadcasts);
        DefaultPermissions.registerPermission("bukkit.broadcast.user", "Allows the user to receive user broadcasts", PermissionDefault.TRUE, broadcasts);
        broadcasts.recalculatePermissibles();
        return broadcasts;
    }
}
