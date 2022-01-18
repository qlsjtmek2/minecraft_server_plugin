// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
import java.util.Iterator;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompoundScalar;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompoundRoot;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.type.CtCompoundType;

public class DeployBeanPropertyCompound extends DeployBeanProperty
{
    final CtCompoundType<?> compoundType;
    final ScalarTypeConverter<?, ?> typeConverter;
    DeployBeanEmbedded deployEmbedded;
    
    public DeployBeanPropertyCompound(final DeployBeanDescriptor<?> desc, final Class<?> targetType, final CtCompoundType<?> compoundType, final ScalarTypeConverter<?, ?> typeConverter) {
        super(desc, targetType, null, null);
        this.compoundType = compoundType;
        this.typeConverter = typeConverter;
    }
    
    public BeanPropertyCompoundRoot getFlatProperties(final BeanDescriptorMap owner, final BeanDescriptor<?> descriptor) {
        final BeanPropertyCompoundRoot rootProperty = new BeanPropertyCompoundRoot(this);
        final CtCompoundTypeScalarList ctMeta = new CtCompoundTypeScalarList();
        this.compoundType.accumulateScalarTypes(null, ctMeta);
        final List<BeanProperty> beanPropertyList = new ArrayList<BeanProperty>();
        for (final Map.Entry<String, ScalarType<?>> entry : ctMeta.entries()) {
            final String relativePropertyName = entry.getKey();
            final ScalarType<?> scalarType = entry.getValue();
            final CtCompoundProperty ctProp = ctMeta.getCompoundType(relativePropertyName);
            String dbColumn = relativePropertyName.replace(".", "_");
            dbColumn = this.getDbColumn(relativePropertyName, dbColumn);
            final DeployBeanProperty deploy = new DeployBeanProperty(null, scalarType.getType(), scalarType, null);
            deploy.setScalarType(scalarType);
            deploy.setDbColumn(dbColumn);
            deploy.setName(relativePropertyName);
            deploy.setDbInsertable(true);
            deploy.setDbUpdateable(true);
            deploy.setDbRead(true);
            final BeanPropertyCompoundScalar bp = new BeanPropertyCompoundScalar(rootProperty, deploy, ctProp, this.typeConverter);
            beanPropertyList.add(bp);
            rootProperty.register(bp);
        }
        rootProperty.setNonScalarProperties(ctMeta.getNonScalarProperties());
        return rootProperty;
    }
    
    private String getDbColumn(final String propName, final String defaultDbColumn) {
        if (this.deployEmbedded == null) {
            return defaultDbColumn;
        }
        final String dbColumn = this.deployEmbedded.getPropertyColumnMap().get(propName);
        return (dbColumn == null) ? defaultDbColumn : dbColumn;
    }
    
    public DeployBeanEmbedded getDeployEmbedded() {
        if (this.deployEmbedded == null) {
            this.deployEmbedded = new DeployBeanEmbedded();
        }
        return this.deployEmbedded;
    }
    
    public ScalarTypeConverter<?, ?> getTypeConverter() {
        return this.typeConverter;
    }
    
    public CtCompoundType<?> getCompoundType() {
        return this.compoundType;
    }
}
