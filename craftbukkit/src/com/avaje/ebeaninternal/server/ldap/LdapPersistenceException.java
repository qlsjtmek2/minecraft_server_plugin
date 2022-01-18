// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import javax.persistence.PersistenceException;

public class LdapPersistenceException extends PersistenceException
{
    private static final long serialVersionUID = -3170359404117927668L;
    
    public LdapPersistenceException(final Throwable e) {
        super(e);
    }
    
    public LdapPersistenceException(final String msg, final Throwable e) {
        super(msg, e);
    }
    
    public LdapPersistenceException(final String msg) {
        super(msg);
    }
}
