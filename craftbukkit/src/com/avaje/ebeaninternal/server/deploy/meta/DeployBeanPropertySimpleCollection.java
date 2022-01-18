// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.server.deploy.ManyType;
import com.avaje.ebeaninternal.server.type.ScalarType;

public class DeployBeanPropertySimpleCollection<T> extends DeployBeanPropertyAssocMany<T>
{
    private final ScalarType<T> collectionScalarType;
    
    public DeployBeanPropertySimpleCollection(final DeployBeanDescriptor<?> desc, final Class<T> targetType, final ScalarType<T> scalarType, final ManyType manyType) {
        super(desc, targetType, manyType);
        this.collectionScalarType = scalarType;
        this.modifyListenMode = BeanCollection.ModifyListenMode.ALL;
    }
    
    public ScalarType<T> getCollectionScalarType() {
        return this.collectionScalarType;
    }
    
    public boolean isManyToMany() {
        return false;
    }
    
    public boolean isUnidirectional() {
        return true;
    }
}
