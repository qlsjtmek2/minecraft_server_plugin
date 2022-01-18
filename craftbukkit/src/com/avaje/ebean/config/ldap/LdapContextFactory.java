// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.ldap;

import javax.naming.directory.DirContext;

public interface LdapContextFactory
{
    DirContext createContext();
}
