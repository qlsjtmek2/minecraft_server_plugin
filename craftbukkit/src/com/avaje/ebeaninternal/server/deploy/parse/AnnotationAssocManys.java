// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebean.config.TableName;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import com.avaje.ebean.annotation.LdapAttribute;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumn;
import com.avaje.ebean.annotation.Where;
import javax.persistence.MapKey;
import javax.persistence.OrderBy;
import javax.persistence.ManyToMany;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.annotation.PrivateOwned;
import javax.persistence.OneToMany;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;

public class AnnotationAssocManys extends AnnotationParser
{
    private final BeanDescriptorManager factory;
    
    public AnnotationAssocManys(final DeployBeanInfo<?> info, final BeanDescriptorManager factory) {
        super(info);
        this.factory = factory;
    }
    
    public void parse() {
        final Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
        while (it.hasNext()) {
            final DeployBeanProperty prop = it.next();
            if (prop instanceof DeployBeanPropertyAssocMany) {
                this.read((DeployBeanPropertyAssocMany<?>)prop);
            }
        }
    }
    
    private void read(final DeployBeanPropertyAssocMany<?> prop) {
        final OneToMany oneToMany = this.get(prop, OneToMany.class);
        if (oneToMany != null) {
            this.readToOne(oneToMany, prop);
            final PrivateOwned privateOwned = this.get(prop, PrivateOwned.class);
            if (privateOwned != null) {
                prop.setModifyListenMode(BeanCollection.ModifyListenMode.REMOVALS);
                prop.getCascadeInfo().setDelete(privateOwned.cascadeRemove());
            }
        }
        final ManyToMany manyToMany = this.get(prop, ManyToMany.class);
        if (manyToMany != null) {
            this.readToMany(manyToMany, prop);
        }
        final OrderBy orderBy = this.get(prop, OrderBy.class);
        if (orderBy != null) {
            prop.setFetchOrderBy(orderBy.value());
        }
        final MapKey mapKey = this.get(prop, MapKey.class);
        if (mapKey != null) {
            prop.setMapKey(mapKey.name());
        }
        final Where where = this.get(prop, Where.class);
        if (where != null) {
            prop.setExtraWhere(where.clause());
        }
        final BeanTable beanTable = prop.getBeanTable();
        final JoinColumn joinColumn = this.get(prop, JoinColumn.class);
        if (joinColumn != null) {
            prop.getTableJoin().addJoinColumn(true, joinColumn, beanTable);
        }
        final JoinColumns joinColumns = this.get(prop, JoinColumns.class);
        if (joinColumns != null) {
            prop.getTableJoin().addJoinColumn(true, joinColumns.value(), beanTable);
        }
        final JoinTable joinTable = this.get(prop, JoinTable.class);
        if (joinTable != null) {
            if (prop.isManyToMany()) {
                this.readJoinTable(joinTable, prop);
            }
            else {
                prop.getTableJoin().addJoinColumn(true, joinTable.joinColumns(), beanTable);
            }
        }
        final LdapAttribute ldapAttribute = this.get(prop, LdapAttribute.class);
        if (ldapAttribute != null) {
            this.readLdapAttribute(ldapAttribute, prop);
        }
        if (prop.getMappedBy() != null) {
            return;
        }
        if (prop.isManyToMany()) {
            this.manyToManyDefaultJoins(prop);
            return;
        }
        if (!prop.getTableJoin().hasJoinColumns() && beanTable != null) {
            final NamingConvention nc = this.factory.getNamingConvention();
            String fkeyPrefix = null;
            if (nc.isUseForeignKeyPrefix()) {
                fkeyPrefix = nc.getColumnFromProperty(this.descriptor.getBeanType(), this.descriptor.getName());
            }
            final BeanTable owningBeanTable = this.factory.getBeanTable(this.descriptor.getBeanType());
            owningBeanTable.createJoinColumn(fkeyPrefix, prop.getTableJoin(), false);
        }
    }
    
    private void readJoinTable(final JoinTable joinTable, final DeployBeanPropertyAssocMany<?> prop) {
        final String intTableName = this.getFullTableName(joinTable);
        final DeployTableJoin intJoin = new DeployTableJoin();
        intJoin.setTable(intTableName);
        intJoin.addJoinColumn(true, joinTable.joinColumns(), prop.getBeanTable());
        final DeployTableJoin destJoin = prop.getTableJoin();
        destJoin.addJoinColumn(false, joinTable.inverseJoinColumns(), prop.getBeanTable());
        intJoin.setType("left outer join");
        final DeployTableJoin inverseDest = destJoin.createInverse(intTableName);
        prop.setIntersectionJoin(intJoin);
        prop.setInverseJoin(inverseDest);
    }
    
    private String getFullTableName(final JoinTable joinTable) {
        final StringBuilder sb = new StringBuilder();
        if (!StringHelper.isNull(joinTable.catalog())) {
            sb.append(joinTable.catalog()).append(".");
        }
        if (!StringHelper.isNull(joinTable.schema())) {
            sb.append(joinTable.schema()).append(".");
        }
        sb.append(joinTable.name());
        return sb.toString();
    }
    
    private void manyToManyDefaultJoins(final DeployBeanPropertyAssocMany<?> prop) {
        String intTableName = null;
        DeployTableJoin intJoin = prop.getIntersectionJoin();
        if (intJoin == null) {
            intJoin = new DeployTableJoin();
            prop.setIntersectionJoin(intJoin);
        }
        else {
            intTableName = intJoin.getTable();
        }
        final BeanTable localTable = this.factory.getBeanTable(this.descriptor.getBeanType());
        final BeanTable otherTable = this.factory.getBeanTable(prop.getTargetType());
        final String localTableName = localTable.getUnqualifiedBaseTable();
        final String otherTableName = otherTable.getUnqualifiedBaseTable();
        if (intTableName == null) {
            intTableName = this.getM2MJoinTableName(localTable, otherTable);
            intJoin.setTable(intTableName);
            intJoin.setType("left outer join");
        }
        final DeployTableJoin destJoin = prop.getTableJoin();
        if (intJoin.hasJoinColumns() && destJoin.hasJoinColumns()) {
            return;
        }
        if (!intJoin.hasJoinColumns()) {
            final BeanProperty[] localIds = localTable.getIdProperties();
            for (int i = 0; i < localIds.length; ++i) {
                final String fkCol = localTableName + "_" + localIds[i].getDbColumn();
                intJoin.addJoinColumn(new DeployTableJoinColumn(localIds[i].getDbColumn(), fkCol));
            }
        }
        if (!destJoin.hasJoinColumns()) {
            final BeanProperty[] otherIds = otherTable.getIdProperties();
            for (int i = 0; i < otherIds.length; ++i) {
                final String fkCol = otherTableName + "_" + otherIds[i].getDbColumn();
                destJoin.addJoinColumn(new DeployTableJoinColumn(fkCol, otherIds[i].getDbColumn()));
            }
        }
        final DeployTableJoin inverseDest = destJoin.createInverse(intTableName);
        prop.setInverseJoin(inverseDest);
    }
    
    private String errorMsgMissingBeanTable(final Class<?> type, final String from) {
        return "Error with association to [" + type + "] from [" + from + "]. Is " + type + " registered?";
    }
    
    private void readToMany(final ManyToMany propAnn, final DeployBeanPropertyAssocMany<?> manyProp) {
        manyProp.setMappedBy(propAnn.mappedBy());
        manyProp.setFetchType(propAnn.fetch());
        this.setCascadeTypes(propAnn.cascade(), manyProp.getCascadeInfo());
        Class<?> targetType = (Class<?>)propAnn.targetEntity();
        if (targetType.equals(Void.TYPE)) {
            targetType = manyProp.getTargetType();
        }
        else {
            manyProp.setTargetType(targetType);
        }
        final BeanTable assoc = this.factory.getBeanTable(targetType);
        if (assoc == null) {
            final String msg = this.errorMsgMissingBeanTable(targetType, manyProp.getFullBeanName());
            throw new RuntimeException(msg);
        }
        manyProp.setManyToMany(true);
        manyProp.setModifyListenMode(BeanCollection.ModifyListenMode.ALL);
        manyProp.setBeanTable(assoc);
        manyProp.getTableJoin().setType("left outer join");
    }
    
    private void readToOne(final OneToMany propAnn, final DeployBeanPropertyAssocMany<?> manyProp) {
        manyProp.setMappedBy(propAnn.mappedBy());
        manyProp.setFetchType(propAnn.fetch());
        this.setCascadeTypes(propAnn.cascade(), manyProp.getCascadeInfo());
        Class<?> targetType = (Class<?>)propAnn.targetEntity();
        if (targetType.equals(Void.TYPE)) {
            targetType = manyProp.getTargetType();
        }
        else {
            manyProp.setTargetType(targetType);
        }
        final BeanTable assoc = this.factory.getBeanTable(targetType);
        if (assoc == null) {
            final String msg = this.errorMsgMissingBeanTable(targetType, manyProp.getFullBeanName());
            throw new RuntimeException(msg);
        }
        manyProp.setBeanTable(assoc);
        manyProp.getTableJoin().setType("left outer join");
    }
    
    private String getM2MJoinTableName(final BeanTable lhsTable, final BeanTable rhsTable) {
        final TableName lhs = new TableName(lhsTable.getBaseTable());
        final TableName rhs = new TableName(rhsTable.getBaseTable());
        final TableName joinTable = this.namingConvention.getM2MJoinTableName(lhs, rhs);
        return joinTable.getQualifiedName();
    }
}
