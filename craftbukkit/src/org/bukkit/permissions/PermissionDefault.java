// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.permissions;

import java.util.HashMap;
import java.util.Map;

public enum PermissionDefault
{
    TRUE(new String[] { "true" }), 
    FALSE(new String[] { "false" }), 
    OP(new String[] { "op", "isop", "operator", "isoperator", "admin", "isadmin" }), 
    NOT_OP(new String[] { "!op", "notop", "!operator", "notoperator", "!admin", "notadmin" });
    
    private final String[] names;
    private static final Map<String, PermissionDefault> lookup;
    
    private PermissionDefault(final String[] names) {
        this.names = names;
    }
    
    public boolean getValue(final boolean op) {
        switch (this) {
            case TRUE: {
                return true;
            }
            case FALSE: {
                return false;
            }
            case OP: {
                return op;
            }
            case NOT_OP: {
                return !op;
            }
            default: {
                return false;
            }
        }
    }
    
    public static PermissionDefault getByName(final String name) {
        return PermissionDefault.lookup.get(name.toLowerCase().replaceAll("[^a-z!]", ""));
    }
    
    public String toString() {
        return this.names[0];
    }
    
    static {
        lookup = new HashMap<String, PermissionDefault>();
        for (final PermissionDefault value : values()) {
            for (final String name : value.names) {
                PermissionDefault.lookup.put(name, value);
            }
        }
    }
}
