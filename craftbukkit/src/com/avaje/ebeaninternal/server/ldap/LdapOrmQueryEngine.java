// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import java.util.List;
import javax.naming.directory.DirContext;
import com.avaje.ebean.config.ldap.LdapContextFactory;

public class LdapOrmQueryEngine
{
    private final boolean defaultVanillaMode;
    private final LdapContextFactory contextFactory;
    
    public LdapOrmQueryEngine(final boolean defaultVanillaMode, final LdapContextFactory contextFactory) {
        this.defaultVanillaMode = defaultVanillaMode;
        this.contextFactory = contextFactory;
    }
    
    public <T> T findId(final LdapOrmQueryRequest<T> request) {
        final DirContext dc = this.contextFactory.createContext();
        final LdapOrmQueryExecute<T> exe = new LdapOrmQueryExecute<T>(request, this.defaultVanillaMode, dc);
        return exe.findId();
    }
    
    public <T> List<T> findList(final LdapOrmQueryRequest<T> request) {
        final DirContext dc = this.contextFactory.createContext();
        final LdapOrmQueryExecute<T> exe = new LdapOrmQueryExecute<T>(request, this.defaultVanillaMode, dc);
        return exe.findList();
    }
}
