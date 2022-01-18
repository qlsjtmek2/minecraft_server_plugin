// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import javax.naming.NamingException;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import javax.naming.NamingEnumeration;
import com.avaje.ebean.bean.EntityBean;
import javax.naming.directory.Attribute;
import java.util.LinkedHashSet;
import javax.naming.directory.Attributes;
import java.util.Set;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.logging.Logger;

public class LdapBeanBuilder<T>
{
    private static final Logger logger;
    private final BeanDescriptor<T> beanDescriptor;
    private final boolean vanillaMode;
    private Set<String> loadedProps;
    
    public LdapBeanBuilder(final BeanDescriptor<T> beanDescriptor, final boolean vanillaMode) {
        this.beanDescriptor = beanDescriptor;
        this.vanillaMode = vanillaMode;
    }
    
    public T readAttributes(final Attributes attributes) throws NamingException {
        final Object bean = this.beanDescriptor.createBean(this.vanillaMode);
        final NamingEnumeration<? extends Attribute> all = attributes.getAll();
        boolean setLoadedProps = false;
        if (this.loadedProps == null) {
            setLoadedProps = true;
            this.loadedProps = new LinkedHashSet<String>();
        }
        while (all.hasMoreElements()) {
            final Attribute attr = all.nextElement();
            final String attrName = attr.getID();
            final BeanProperty prop = this.beanDescriptor.getBeanPropertyFromDbColumn(attrName);
            if (prop == null) {
                if ("objectclass".equalsIgnoreCase(attrName)) {
                    continue;
                }
                LdapBeanBuilder.logger.info("... hmm, no property to map to attribute[" + attrName + "] value[" + attr.get() + "]");
            }
            else {
                prop.setAttributeValue(bean, attr);
                if (!setLoadedProps) {
                    continue;
                }
                this.loadedProps.add(prop.getName());
            }
        }
        if (bean instanceof EntityBean) {
            final EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
            ebi.setLoadedProps(this.loadedProps);
            ebi.setLoaded();
        }
        final BeanPersistController persistController = this.beanDescriptor.getPersistController();
        if (persistController != null) {
            persistController.postLoad(bean, this.loadedProps);
        }
        return (T)bean;
    }
    
    static {
        logger = Logger.getLogger(LdapBeanBuilder.class.getName());
    }
}
