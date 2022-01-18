// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import com.avaje.ebeaninternal.server.core.PersistRequest;
import javax.naming.directory.Attribute;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import javax.naming.directory.BasicAttributes;
import java.util.Set;
import javax.naming.directory.Attributes;
import javax.naming.Name;
import javax.naming.directory.DirContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import com.avaje.ebean.config.ldap.LdapContextFactory;
import java.util.logging.Logger;

public class DefaultLdapPersister
{
    private static final Logger logger;
    private final LdapContextFactory contextFactory;
    
    public DefaultLdapPersister(final LdapContextFactory dirContextFactory) {
        this.contextFactory = dirContextFactory;
    }
    
    public int persist(final LdapPersistBeanRequest<?> request) {
        switch (request.getType()) {
            case INSERT: {
                return this.insert(request);
            }
            case UPDATE: {
                return this.update(request);
            }
            case DELETE: {
                return this.delete(request);
            }
            default: {
                throw new LdapPersistenceException("Invalid type " + request.getType());
            }
        }
    }
    
    private int insert(final LdapPersistBeanRequest<?> request) {
        final DirContext dc = this.contextFactory.createContext();
        final Name name = request.createLdapName();
        final Attributes attrs = this.createAttributes(request, false, request.getLoadedProperties());
        if (DefaultLdapPersister.logger.isLoggable(Level.FINE)) {
            DefaultLdapPersister.logger.fine("Ldap Insert Name:" + name + " Attrs:" + attrs);
        }
        try {
            dc.bind(name, null, attrs);
            return 1;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
    
    private int delete(final LdapPersistBeanRequest<?> request) {
        final DirContext dc = this.contextFactory.createContext();
        final Name name = request.createLdapName();
        if (DefaultLdapPersister.logger.isLoggable(Level.FINE)) {
            DefaultLdapPersister.logger.fine("Ldap Delete Name:" + name);
        }
        try {
            dc.unbind(name);
            return 1;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
    
    private int update(final LdapPersistBeanRequest<?> request) {
        final Name name = request.createLdapName();
        final Set<String> updatedProperties = request.getUpdatedProperties();
        if (updatedProperties == null || updatedProperties.isEmpty()) {
            DefaultLdapPersister.logger.info("Ldap Update has no changed properties?  Name:" + name);
            return 0;
        }
        final DirContext dc = this.contextFactory.createContext();
        final Attributes attrs = this.createAttributes(request, true, updatedProperties);
        if (DefaultLdapPersister.logger.isLoggable(Level.FINE)) {
            DefaultLdapPersister.logger.fine("Ldap Update Name:" + name + " Attrs:" + attrs);
        }
        try {
            dc.modifyAttributes(name, 2, attrs);
            return 1;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
    
    private Attributes createAttributes(final LdapPersistBeanRequest<?> request, final boolean update, final Set<String> props) {
        final BeanDescriptor<?> desc = request.getBeanDescriptor();
        Attributes attrs = desc.createAttributes();
        if (update) {
            attrs = new BasicAttributes(true);
        }
        else {
            attrs = desc.createAttributes();
        }
        final Object bean = request.getBean();
        if (props != null) {
            for (final String propName : props) {
                final BeanProperty p = desc.getBeanPropertyFromPath(propName);
                final Attribute attr = p.createAttribute(bean);
                if (attr != null) {
                    attrs.put(attr);
                }
            }
        }
        else {
            final Iterator<BeanProperty> it = desc.propertiesAll();
            while (it.hasNext()) {
                final BeanProperty p2 = it.next();
                final Attribute attr2 = p2.createAttribute(bean);
                if (attr2 != null) {
                    attrs.put(attr2);
                }
            }
        }
        return attrs;
    }
    
    static {
        logger = Logger.getLogger(DefaultLdapPersister.class.getName());
    }
}
