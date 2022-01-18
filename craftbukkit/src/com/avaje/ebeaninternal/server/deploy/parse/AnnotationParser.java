// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import javax.persistence.PersistenceException;
import com.avaje.ebean.config.ldap.LdapAttributeAdapter;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebean.annotation.LdapAttribute;
import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
import javax.persistence.CascadeType;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;

public abstract class AnnotationParser extends AnnotationBase
{
    protected final DeployBeanInfo<?> info;
    protected final DeployBeanDescriptor<?> descriptor;
    protected final Class<?> beanType;
    
    public AnnotationParser(final DeployBeanInfo<?> info) {
        super(info.getUtil());
        this.info = info;
        this.beanType = info.getDescriptor().getBeanType();
        this.descriptor = info.getDescriptor();
    }
    
    public abstract void parse();
    
    protected void setCascadeTypes(final CascadeType[] cascadeTypes, final BeanCascadeInfo cascadeInfo) {
        if (cascadeTypes != null && cascadeTypes.length > 0) {
            cascadeInfo.setTypes(cascadeTypes);
        }
    }
    
    protected void readLdapAttribute(final LdapAttribute ldapAttribute, final DeployBeanProperty prop) {
        if (!this.isEmpty(ldapAttribute.name())) {
            prop.setDbColumn(ldapAttribute.name());
        }
        prop.setDbInsertable(ldapAttribute.insertable());
        prop.setDbUpdateable(ldapAttribute.updatable());
        final Class<?> adapterCls = ldapAttribute.adapter();
        if (adapterCls != null && !Void.TYPE.equals(adapterCls)) {
            try {
                final LdapAttributeAdapter adapter = (LdapAttributeAdapter)adapterCls.newInstance();
                prop.setLdapAttributeAdapter(adapter);
            }
            catch (Exception e) {
                final String msg = "Error creating LdapAttributeAdapter for [" + prop.getFullBeanName() + "] " + "with class [" + adapterCls + "] using the default constructor.";
                throw new PersistenceException(msg, e);
            }
        }
    }
}
