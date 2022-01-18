// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;

public interface LdapAttributeAdapter
{
    Object readAttribute(final Attribute p0) throws NamingException;
    
    Attribute createAttribute(final Object p0);
}
