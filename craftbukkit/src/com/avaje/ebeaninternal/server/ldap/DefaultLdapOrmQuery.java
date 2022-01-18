// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebeaninternal.server.querydefn.DefaultOrmQuery;

public class DefaultLdapOrmQuery<T> extends DefaultOrmQuery<T>
{
    private static final long serialVersionUID = -4344629258591773124L;
    
    public DefaultLdapOrmQuery(final Class<T> beanType, final EbeanServer server, final ExpressionFactory expressionFactory, final String query) {
        super(beanType, server, expressionFactory, query);
    }
}
