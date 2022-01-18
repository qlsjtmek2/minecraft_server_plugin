// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Map;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;

public class BeanEmbeddedMetaFactory
{
    public static BeanEmbeddedMeta create(final BeanDescriptorMap owner, final DeployBeanPropertyAssocOne<?> prop, final BeanDescriptor<?> descriptor) {
        final BeanDescriptor<?> targetDesc = owner.getBeanDescriptor(prop.getTargetType());
        if (targetDesc == null) {
            final String msg = "Could not find BeanDescriptor for " + prop.getTargetType() + ". Perhaps the EmbeddedId class is not registered?";
            throw new PersistenceException(msg);
        }
        final Map<String, String> propColMap = prop.getDeployEmbedded().getPropertyColumnMap();
        final BeanProperty[] sourceProperties = targetDesc.propertiesBaseScalar();
        final BeanProperty[] embeddedProperties = new BeanProperty[sourceProperties.length];
        for (int i = 0; i < sourceProperties.length; ++i) {
            final String propertyName = sourceProperties[i].getName();
            String dbColumn = propColMap.get(propertyName);
            if (dbColumn == null) {
                dbColumn = sourceProperties[i].getDbColumn();
            }
            final BeanPropertyOverride overrides = new BeanPropertyOverride(dbColumn);
            embeddedProperties[i] = new BeanProperty(sourceProperties[i], overrides);
        }
        return new BeanEmbeddedMeta(embeddedProperties);
    }
}
