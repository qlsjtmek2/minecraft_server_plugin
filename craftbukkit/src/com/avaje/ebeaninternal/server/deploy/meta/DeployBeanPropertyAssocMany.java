// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.ManyType;
import com.avaje.ebean.bean.BeanCollection;

public class DeployBeanPropertyAssocMany<T> extends DeployBeanPropertyAssoc<T>
{
    BeanCollection.ModifyListenMode modifyListenMode;
    boolean manyToMany;
    boolean unidirectional;
    DeployTableJoin intersectionJoin;
    DeployTableJoin inverseJoin;
    String fetchOrderBy;
    String mapKey;
    ManyType manyType;
    
    public DeployBeanPropertyAssocMany(final DeployBeanDescriptor<?> desc, final Class<T> targetType, final ManyType manyType) {
        super(desc, targetType);
        this.modifyListenMode = BeanCollection.ModifyListenMode.NONE;
        this.manyType = manyType;
    }
    
    public void setTargetType(final Class<?> cls) {
        this.targetType = (Class<T>)cls;
    }
    
    public ManyType getManyType() {
        return this.manyType;
    }
    
    public boolean isManyToMany() {
        return this.manyToMany;
    }
    
    public void setManyToMany(final boolean isManyToMany) {
        this.manyToMany = isManyToMany;
    }
    
    public BeanCollection.ModifyListenMode getModifyListenMode() {
        return this.modifyListenMode;
    }
    
    public void setModifyListenMode(final BeanCollection.ModifyListenMode modifyListenMode) {
        this.modifyListenMode = modifyListenMode;
    }
    
    public boolean isUnidirectional() {
        return this.unidirectional;
    }
    
    public void setUnidirectional(final boolean unidirectional) {
        this.unidirectional = unidirectional;
    }
    
    public TableJoin createIntersectionTableJoin() {
        if (this.intersectionJoin != null) {
            return new TableJoin(this.intersectionJoin, null);
        }
        return null;
    }
    
    public TableJoin createInverseTableJoin() {
        if (this.inverseJoin != null) {
            return new TableJoin(this.inverseJoin, null);
        }
        return null;
    }
    
    public DeployTableJoin getIntersectionJoin() {
        return this.intersectionJoin;
    }
    
    public DeployTableJoin getInverseJoin() {
        return this.inverseJoin;
    }
    
    public void setIntersectionJoin(final DeployTableJoin intersectionJoin) {
        this.intersectionJoin = intersectionJoin;
    }
    
    public void setInverseJoin(final DeployTableJoin inverseJoin) {
        this.inverseJoin = inverseJoin;
    }
    
    public String getFetchOrderBy() {
        return this.fetchOrderBy;
    }
    
    public String getMapKey() {
        return this.mapKey;
    }
    
    public void setMapKey(final String mapKey) {
        if (mapKey != null && mapKey.length() > 0) {
            this.mapKey = mapKey;
        }
    }
    
    public void setFetchOrderBy(final String orderBy) {
        if (orderBy != null && orderBy.length() > 0) {
            this.fetchOrderBy = orderBy;
        }
    }
}
