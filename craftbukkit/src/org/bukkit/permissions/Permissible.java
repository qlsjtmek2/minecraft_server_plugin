// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.permissions;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface Permissible extends ServerOperator
{
    boolean isPermissionSet(final String p0);
    
    boolean isPermissionSet(final Permission p0);
    
    boolean hasPermission(final String p0);
    
    boolean hasPermission(final Permission p0);
    
    PermissionAttachment addAttachment(final Plugin p0, final String p1, final boolean p2);
    
    PermissionAttachment addAttachment(final Plugin p0);
    
    PermissionAttachment addAttachment(final Plugin p0, final String p1, final boolean p2, final int p3);
    
    PermissionAttachment addAttachment(final Plugin p0, final int p1);
    
    void removeAttachment(final PermissionAttachment p0);
    
    void recalculatePermissions();
    
    Set<PermissionAttachmentInfo> getEffectivePermissions();
}
