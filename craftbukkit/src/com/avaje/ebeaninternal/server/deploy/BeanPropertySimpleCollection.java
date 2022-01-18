// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import javax.naming.NamingEnumeration;
import com.avaje.ebean.bean.BeanCollectionAdd;
import javax.naming.NamingException;
import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
import java.util.Iterator;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.Attribute;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
import com.avaje.ebeaninternal.server.type.ScalarType;

public class BeanPropertySimpleCollection<T> extends BeanPropertyAssocMany<T>
{
    private final ScalarType<T> collectionScalarType;
    
    public BeanPropertySimpleCollection(final BeanDescriptorMap owner, final BeanDescriptor<?> descriptor, final DeployBeanPropertySimpleCollection<T> deploy) {
        super(owner, descriptor, deploy);
        this.collectionScalarType = deploy.getCollectionScalarType();
    }
    
    public void initialise() {
        super.initialise();
    }
    
    public void copyProperty(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
        final Object srcValue = this.getValueUnderlying(sourceBean);
        final Object dstValue = this.help.copyCollection(srcValue, ctx, maxDepth, destBean);
        this.setValue(destBean, dstValue);
    }
    
    public Attribute createAttribute(final Object bean) {
        final Object v = this.getValue(bean);
        if (v == null) {
            return null;
        }
        if (this.ldapAttributeAdapter != null) {
            return this.ldapAttributeAdapter.createAttribute(v);
        }
        final BasicAttribute attrs = new BasicAttribute(this.getDbColumn());
        final Iterator<?> it = this.help.getIterator(v);
        if (it != null) {
            while (it.hasNext()) {
                final Object beanValue = it.next();
                final Object attrValue = this.collectionScalarType.toJdbcType(beanValue);
                attrs.add(attrValue);
            }
        }
        return attrs;
    }
    
    public void setAttributeValue(final Object bean, final Attribute attr) {
        try {
            if (attr != null) {
                Object beanValue;
                if (this.ldapAttributeAdapter != null) {
                    beanValue = this.ldapAttributeAdapter.readAttribute(attr);
                }
                else {
                    final boolean vanilla = true;
                    beanValue = this.help.createEmpty(vanilla);
                    final BeanCollectionAdd collAdd = this.help.getBeanCollectionAdd(beanValue, this.mapKey);
                    final NamingEnumeration<?> en = attr.getAll();
                    while (en.hasMoreElements()) {
                        final Object attrValue = en.nextElement();
                        final Object collValue = this.collectionScalarType.toBeanType(attrValue);
                        collAdd.addBean(collValue);
                    }
                }
                this.setValue(bean, beanValue);
            }
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
}
