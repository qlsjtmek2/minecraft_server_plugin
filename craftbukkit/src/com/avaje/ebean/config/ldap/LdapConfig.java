// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.ldap;

public class LdapConfig
{
    private LdapContextFactory contextFactory;
    private boolean vanillaMode;
    
    public LdapContextFactory getContextFactory() {
        return this.contextFactory;
    }
    
    public void setContextFactory(final LdapContextFactory contextFactory) {
        this.contextFactory = contextFactory;
    }
    
    public boolean isVanillaMode() {
        return this.vanillaMode;
    }
    
    public void setVanillaMode(final boolean vanillaMode) {
        this.vanillaMode = vanillaMode;
    }
}
