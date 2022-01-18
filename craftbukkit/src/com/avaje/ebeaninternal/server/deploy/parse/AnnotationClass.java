// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebean.annotation.CacheStrategy;
import com.avaje.ebean.annotation.NamedUpdate;
import com.avaje.ebean.annotation.NamedUpdates;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import com.avaje.ebean.annotation.UpdateMode;
import javax.persistence.Table;
import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
import javax.persistence.UniqueConstraint;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.annotation.LdapDomain;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import com.avaje.ebean.config.TableName;

public class AnnotationClass extends AnnotationParser
{
    public AnnotationClass(final DeployBeanInfo<?> info) {
        super(info);
    }
    
    public void parse() {
        this.read(this.descriptor.getBeanType());
        this.setTableName();
    }
    
    private void setTableName() {
        if (this.descriptor.isBaseTableType()) {
            final TableName tableName = this.namingConvention.getTableName(this.descriptor.getBeanType());
            this.descriptor.setBaseTable(tableName);
        }
    }
    
    private String[] parseLdapObjectclasses(final String objectclasses) {
        if (objectclasses == null || objectclasses.length() == 0) {
            return null;
        }
        return objectclasses.split(",");
    }
    
    private boolean isXmlElement(final Class<?> cls) {
        final XmlRootElement rootElement = cls.getAnnotation(XmlRootElement.class);
        if (rootElement != null) {
            return true;
        }
        final XmlType xmlType = cls.getAnnotation(XmlType.class);
        return xmlType != null;
    }
    
    private void read(final Class<?> cls) {
        final LdapDomain ldapDomain = cls.getAnnotation(LdapDomain.class);
        if (ldapDomain != null) {
            this.descriptor.setName(cls.getSimpleName());
            this.descriptor.setEntityType(BeanDescriptor.EntityType.LDAP);
            this.descriptor.setLdapBaseDn(ldapDomain.baseDn());
            this.descriptor.setLdapObjectclasses(this.parseLdapObjectclasses(ldapDomain.objectclass()));
        }
        final Entity entity = cls.getAnnotation(Entity.class);
        if (entity != null) {
            if (entity.name().equals("")) {
                this.descriptor.setName(cls.getSimpleName());
            }
            else {
                this.descriptor.setName(entity.name());
            }
        }
        else if (this.isXmlElement(cls)) {
            this.descriptor.setName(cls.getSimpleName());
            this.descriptor.setEntityType(BeanDescriptor.EntityType.XMLELEMENT);
        }
        final Embeddable embeddable = cls.getAnnotation(Embeddable.class);
        if (embeddable != null) {
            this.descriptor.setEntityType(BeanDescriptor.EntityType.EMBEDDED);
            this.descriptor.setName("Embeddable:" + cls.getSimpleName());
        }
        final UniqueConstraint uc = cls.getAnnotation(UniqueConstraint.class);
        if (uc != null) {
            this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(uc.columnNames()));
        }
        final Table table = cls.getAnnotation(Table.class);
        if (table != null) {
            final UniqueConstraint[] uniqueConstraints = table.uniqueConstraints();
            if (uniqueConstraints != null) {
                for (final UniqueConstraint c : uniqueConstraints) {
                    this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(c.columnNames()));
                }
            }
        }
        final UpdateMode updateMode = cls.getAnnotation(UpdateMode.class);
        if (updateMode != null) {
            this.descriptor.setUpdateChangesOnly(updateMode.updateChangesOnly());
        }
        final NamedQueries namedQueries = cls.getAnnotation(NamedQueries.class);
        if (namedQueries != null) {
            this.readNamedQueries(namedQueries);
        }
        final NamedQuery namedQuery = cls.getAnnotation(NamedQuery.class);
        if (namedQuery != null) {
            this.readNamedQuery(namedQuery);
        }
        final NamedUpdates namedUpdates = cls.getAnnotation(NamedUpdates.class);
        if (namedUpdates != null) {
            this.readNamedUpdates(namedUpdates);
        }
        final NamedUpdate namedUpdate = cls.getAnnotation(NamedUpdate.class);
        if (namedUpdate != null) {
            this.readNamedUpdate(namedUpdate);
        }
        final CacheStrategy cacheStrategy = cls.getAnnotation(CacheStrategy.class);
        if (cacheStrategy != null) {
            this.readCacheStrategy(cacheStrategy);
        }
    }
    
    private void readCacheStrategy(final CacheStrategy cacheStrategy) {
        final boolean useCache = cacheStrategy.useBeanCache();
        final boolean readOnly = cacheStrategy.readOnly();
        final String warmingQuery = cacheStrategy.warmingQuery();
        final ReferenceOptions opt = new ReferenceOptions(useCache, readOnly, warmingQuery);
        this.descriptor.setReferenceOptions(opt);
        if (!Query.UseIndex.DEFAULT.equals(cacheStrategy.useIndex())) {
            this.descriptor.setUseIndex(cacheStrategy.useIndex());
        }
    }
    
    private void readNamedQueries(final NamedQueries namedQueries) {
        final NamedQuery[] queries = namedQueries.value();
        for (int i = 0; i < queries.length; ++i) {
            this.readNamedQuery(queries[i]);
        }
    }
    
    private void readNamedQuery(final NamedQuery namedQuery) {
        final DeployNamedQuery q = new DeployNamedQuery(namedQuery);
        this.descriptor.add(q);
    }
    
    private void readNamedUpdates(final NamedUpdates updates) {
        final NamedUpdate[] updateArray = updates.value();
        for (int i = 0; i < updateArray.length; ++i) {
            this.readNamedUpdate(updateArray[i]);
        }
    }
    
    private void readNamedUpdate(final NamedUpdate update) {
        final DeployNamedUpdate upd = new DeployNamedUpdate(update);
        this.descriptor.add(upd);
    }
}
