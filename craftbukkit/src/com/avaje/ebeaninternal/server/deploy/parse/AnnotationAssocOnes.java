// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import javax.persistence.AttributeOverride;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.AttributeOverrides;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebean.annotation.EmbeddedColumns;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumn;
import com.avaje.ebean.validation.NotNull;
import com.avaje.ebean.annotation.Where;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Embedded;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;

public class AnnotationAssocOnes extends AnnotationParser
{
    private final BeanDescriptorManager factory;
    
    public AnnotationAssocOnes(final DeployBeanInfo<?> info, final BeanDescriptorManager factory) {
        super(info);
        this.factory = factory;
    }
    
    public void parse() {
        final Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
        while (it.hasNext()) {
            final DeployBeanProperty prop = it.next();
            if (prop instanceof DeployBeanPropertyAssocOne) {
                this.readAssocOne((DeployBeanPropertyAssocOne<?>)prop);
            }
        }
    }
    
    private void readAssocOne(final DeployBeanPropertyAssocOne<?> prop) {
        final ManyToOne manyToOne = this.get(prop, ManyToOne.class);
        if (manyToOne != null) {
            this.readManyToOne(manyToOne, prop);
        }
        final OneToOne oneToOne = this.get(prop, OneToOne.class);
        if (oneToOne != null) {
            this.readOneToOne(oneToOne, prop);
        }
        final Embedded embedded = this.get(prop, Embedded.class);
        if (embedded != null) {
            this.readEmbedded(embedded, prop);
        }
        final EmbeddedId emId = this.get(prop, EmbeddedId.class);
        if (emId != null) {
            prop.setEmbedded(true);
            prop.setId(true);
            prop.setNullable(false);
        }
        final Column column = this.get(prop, Column.class);
        if (column != null && !this.isEmpty(column.name())) {
            prop.setDbColumn(column.name());
        }
        final Id id = this.get(prop, Id.class);
        if (id != null) {
            prop.setEmbedded(true);
            prop.setId(true);
            prop.setNullable(false);
        }
        final Where where = this.get(prop, Where.class);
        if (where != null) {
            prop.setExtraWhere(where.clause());
        }
        final NotNull notNull = this.get(prop, NotNull.class);
        if (notNull != null) {
            prop.setNullable(false);
            prop.getTableJoin().setType("join");
        }
        final BeanTable beanTable = prop.getBeanTable();
        final JoinColumn joinColumn = this.get(prop, JoinColumn.class);
        if (joinColumn != null) {
            prop.getTableJoin().addJoinColumn(false, joinColumn, beanTable);
            if (!joinColumn.updatable()) {
                prop.setDbUpdateable(false);
            }
        }
        final JoinColumns joinColumns = this.get(prop, JoinColumns.class);
        if (joinColumns != null) {
            prop.getTableJoin().addJoinColumn(false, joinColumns.value(), beanTable);
        }
        final JoinTable joinTable = this.get(prop, JoinTable.class);
        if (joinTable != null) {
            prop.getTableJoin().addJoinColumn(false, joinTable.joinColumns(), beanTable);
        }
        this.info.setBeanJoinType(prop, prop.isNullable());
        if (!prop.getTableJoin().hasJoinColumns() && beanTable != null) {
            if (prop.getMappedBy() == null) {
                final NamingConvention nc = this.factory.getNamingConvention();
                String fkeyPrefix = null;
                if (nc.isUseForeignKeyPrefix()) {
                    fkeyPrefix = nc.getColumnFromProperty(this.beanType, prop.getName());
                }
                beanTable.createJoinColumn(fkeyPrefix, prop.getTableJoin(), true);
            }
        }
    }
    
    private String errorMsgMissingBeanTable(final Class<?> type, final String from) {
        return "Error with association to [" + type + "] from [" + from + "]. Is " + type + " registered?";
    }
    
    private void readManyToOne(final ManyToOne propAnn, final DeployBeanProperty prop) {
        final DeployBeanPropertyAssocOne<?> beanProp = (DeployBeanPropertyAssocOne<?>)prop;
        this.setCascadeTypes(propAnn.cascade(), beanProp.getCascadeInfo());
        final BeanTable assoc = this.factory.getBeanTable(beanProp.getPropertyType());
        if (assoc == null) {
            final String msg = this.errorMsgMissingBeanTable(beanProp.getPropertyType(), prop.getFullBeanName());
            throw new RuntimeException(msg);
        }
        beanProp.setBeanTable(assoc);
        beanProp.setDbInsertable(true);
        beanProp.setDbUpdateable(true);
        beanProp.setNullable(propAnn.optional());
        beanProp.setFetchType(propAnn.fetch());
    }
    
    private void readOneToOne(final OneToOne propAnn, final DeployBeanPropertyAssocOne<?> prop) {
        prop.setOneToOne(true);
        prop.setDbInsertable(true);
        prop.setDbUpdateable(true);
        prop.setNullable(propAnn.optional());
        prop.setFetchType(propAnn.fetch());
        prop.setMappedBy(propAnn.mappedBy());
        if (!"".equals(propAnn.mappedBy())) {
            prop.setOneToOneExported(true);
        }
        this.setCascadeTypes(propAnn.cascade(), prop.getCascadeInfo());
        final BeanTable assoc = this.factory.getBeanTable(prop.getPropertyType());
        if (assoc == null) {
            final String msg = this.errorMsgMissingBeanTable(prop.getPropertyType(), prop.getFullBeanName());
            throw new RuntimeException(msg);
        }
        prop.setBeanTable(assoc);
    }
    
    private void readEmbedded(final Embedded propAnn, final DeployBeanPropertyAssocOne<?> prop) {
        prop.setEmbedded(true);
        prop.setDbInsertable(true);
        prop.setDbUpdateable(true);
        final EmbeddedColumns columns = this.get(prop, EmbeddedColumns.class);
        if (columns != null) {
            final String propColumns = columns.columns();
            final Map<String, String> propMap = StringHelper.delimitedToMap(propColumns, ",", "=");
            prop.getDeployEmbedded().putAll(propMap);
        }
        final AttributeOverrides attrOverrides = this.get(prop, AttributeOverrides.class);
        if (attrOverrides != null) {
            final HashMap<String, String> propMap2 = new HashMap<String, String>();
            final AttributeOverride[] aoArray = attrOverrides.value();
            for (int i = 0; i < aoArray.length; ++i) {
                final String propName = aoArray[i].name();
                final String columnName = aoArray[i].column().name();
                propMap2.put(propName, columnName);
            }
            prop.getDeployEmbedded().putAll(propMap2);
        }
    }
}
