// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebean.validation.Pattern;
import com.avaje.ebean.validation.ValidatorMeta;
import com.avaje.ebean.validation.Patterns;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import com.avaje.ebean.config.dbplatform.IdType;
import java.util.UUID;
import com.avaje.ebeaninternal.server.type.ScalarTypeEncryptedWrapper;
import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
import com.avaje.ebean.config.dbplatform.DbEncrypt;
import com.avaje.ebeaninternal.server.type.DataEncryptSupport;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesEncrypted;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesBase;
import com.avaje.ebeaninternal.server.type.ScalarTypeLdapDate;
import com.avaje.ebeaninternal.server.type.ScalarTypeLdapTimestamp;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.type.CtCompoundType;
import java.util.Map;
import com.avaje.ebean.annotation.Encrypted;
import com.avaje.ebean.config.EncryptDeploy;
import javax.persistence.Transient;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
import com.avaje.ebean.annotation.EmbeddedColumns;
import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotNull;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.avaje.ebean.annotation.CreatedTimestamp;
import javax.persistence.Basic;
import javax.persistence.Version;
import com.avaje.ebean.annotation.Formula;
import javax.persistence.Temporal;
import javax.persistence.Lob;
import com.avaje.ebean.annotation.LdapId;
import javax.persistence.GeneratedValue;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.annotation.LdapAttribute;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.type.ScalarTypeLdapBoolean;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedPropertyFactory;
import javax.persistence.FetchType;

public class AnnotationFields extends AnnotationParser
{
    private FetchType defaultLobFetchType;
    private GeneratedPropertyFactory generatedPropFactory;
    private static final ScalarTypeLdapBoolean LDAP_BOOLEAN_SCALARTYPE;
    
    public AnnotationFields(final DeployBeanInfo<?> info) {
        super(info);
        this.defaultLobFetchType = FetchType.LAZY;
        this.generatedPropFactory = new GeneratedPropertyFactory();
        if (GlobalProperties.getBoolean("ebean.lobEagerFetch", false)) {
            this.defaultLobFetchType = FetchType.EAGER;
        }
    }
    
    public void parse() {
        final Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
        while (it.hasNext()) {
            final DeployBeanProperty prop = it.next();
            if (prop instanceof DeployBeanPropertyAssoc) {
                this.readAssocOne(prop);
            }
            else {
                this.readField(prop);
            }
            this.readValidations(prop);
        }
    }
    
    private void readAssocOne(final DeployBeanProperty prop) {
        final Id id = this.get(prop, Id.class);
        if (id != null) {
            prop.setId(true);
            prop.setNullable(false);
        }
        final EmbeddedId embeddedId = this.get(prop, EmbeddedId.class);
        if (embeddedId != null) {
            prop.setId(true);
            prop.setNullable(false);
            prop.setEmbedded(true);
        }
    }
    
    private void readField(final DeployBeanProperty prop) {
        final boolean isEnum = prop.getPropertyType().isEnum();
        final Enumerated enumerated = this.get(prop, Enumerated.class);
        if (isEnum || enumerated != null) {
            this.util.setEnumScalarType(enumerated, prop);
        }
        prop.setDbRead(true);
        prop.setDbInsertable(true);
        prop.setDbUpdateable(true);
        final Column column = this.get(prop, Column.class);
        if (column != null) {
            this.readColumn(column, prop);
        }
        final LdapAttribute ldapAttribute = this.get(prop, LdapAttribute.class);
        if (ldapAttribute != null) {
            this.readLdapAttribute(ldapAttribute, prop);
        }
        if (prop.getDbColumn() == null) {
            if (BeanDescriptor.EntityType.LDAP.equals(this.descriptor.getEntityType())) {
                prop.setDbColumn(prop.getName());
            }
            else {
                final String dbColumn = this.namingConvention.getColumnFromProperty(this.beanType, prop.getName());
                prop.setDbColumn(dbColumn);
            }
        }
        final GeneratedValue gen = this.get(prop, GeneratedValue.class);
        if (gen != null) {
            this.readGenValue(gen, prop);
        }
        final Id id = this.get(prop, Id.class);
        if (id != null) {
            this.readId(id, prop);
        }
        final LdapId ldapId = this.get(prop, LdapId.class);
        if (ldapId != null) {
            prop.setId(true);
            prop.setNullable(false);
        }
        final Lob lob = this.get(prop, Lob.class);
        final Temporal temporal = this.get(prop, Temporal.class);
        if (temporal != null) {
            this.readTemporal(temporal, prop);
        }
        else if (lob != null) {
            this.util.setLobType(prop);
        }
        final Formula formula = this.get(prop, Formula.class);
        if (formula != null) {
            prop.setSqlFormula(formula.select(), formula.join());
        }
        final Version version = this.get(prop, Version.class);
        if (version != null) {
            prop.setVersionColumn(true);
            this.generatedPropFactory.setVersion(prop);
        }
        final Basic basic = this.get(prop, Basic.class);
        if (basic != null) {
            prop.setFetchType(basic.fetch());
            if (!basic.optional()) {
                prop.setNullable(false);
            }
        }
        else if (prop.isLob()) {
            prop.setFetchType(this.defaultLobFetchType);
        }
        final CreatedTimestamp ct = this.get(prop, CreatedTimestamp.class);
        if (ct != null) {
            this.generatedPropFactory.setInsertTimestamp(prop);
        }
        final UpdatedTimestamp ut = this.get(prop, UpdatedTimestamp.class);
        if (ut != null) {
            this.generatedPropFactory.setUpdateTimestamp(prop);
        }
        final NotNull notNull = this.get(prop, NotNull.class);
        if (notNull != null) {
            prop.setNullable(false);
        }
        final Length length = this.get(prop, Length.class);
        if (length != null && length.max() < Integer.MAX_VALUE) {
            prop.setDbLength(length.max());
        }
        final EmbeddedColumns columns = this.get(prop, EmbeddedColumns.class);
        if (columns != null) {
            if (!(prop instanceof DeployBeanPropertyCompound)) {
                throw new RuntimeException("Can't use EmbeddedColumns on ScalarType " + prop.getFullBeanName());
            }
            final DeployBeanPropertyCompound p = (DeployBeanPropertyCompound)prop;
            final String propColumns = columns.columns();
            final Map<String, String> propMap = StringHelper.delimitedToMap(propColumns, ",", "=");
            p.getDeployEmbedded().putAll(propMap);
            final CtCompoundType<?> compoundType = p.getCompoundType();
            if (compoundType == null) {
                throw new RuntimeException("No registered CtCompoundType for " + p.getPropertyType());
            }
        }
        final Transient t = this.get(prop, Transient.class);
        if (t != null) {
            prop.setDbRead(false);
            prop.setDbInsertable(false);
            prop.setDbUpdateable(false);
            prop.setTransient(true);
        }
        if (!prop.isTransient()) {
            final EncryptDeploy encryptDeploy = this.util.getEncryptDeploy(this.info.getDescriptor().getBaseTableFull(), prop.getDbColumn());
            if (encryptDeploy == null || encryptDeploy.getMode().equals(EncryptDeploy.Mode.MODE_ANNOTATION)) {
                final Encrypted encrypted = this.get(prop, Encrypted.class);
                if (encrypted != null) {
                    this.setEncryption(prop, encrypted.dbEncryption(), encrypted.dbLength());
                }
            }
            else if (EncryptDeploy.Mode.MODE_ENCRYPT.equals(encryptDeploy.getMode())) {
                this.setEncryption(prop, encryptDeploy.isDbEncrypt(), encryptDeploy.getDbLength());
            }
        }
        if (BeanDescriptor.EntityType.LDAP.equals(this.descriptor.getEntityType())) {
            this.adjustTypesForLdap(prop);
        }
    }
    
    private void adjustTypesForLdap(final DeployBeanProperty prop) {
        final Class<?> pt = prop.getPropertyType();
        if (Boolean.TYPE.equals(pt) || Boolean.class.equals(pt)) {
            prop.setScalarType(AnnotationFields.LDAP_BOOLEAN_SCALARTYPE);
        }
        else {
            final ScalarType<?> sqlScalarType = prop.getScalarType();
            final int sqlType = sqlScalarType.getJdbcType();
            if (sqlType == 93) {
                prop.setScalarType(new ScalarTypeLdapTimestamp<Object>(sqlScalarType));
            }
            else if (sqlType == 91) {
                prop.setScalarType(new ScalarTypeLdapDate<Object>(sqlScalarType));
            }
        }
    }
    
    private void setEncryption(final DeployBeanProperty prop, final boolean dbEncString, final int dbLen) {
        this.util.checkEncryptKeyManagerDefined(prop.getFullBeanName());
        final ScalarType<?> st = prop.getScalarType();
        if (byte[].class.equals(st.getType())) {
            final ScalarTypeBytesBase baseType = (ScalarTypeBytesBase)st;
            final DataEncryptSupport support = this.createDataEncryptSupport(prop);
            final ScalarTypeBytesEncrypted encryptedScalarType = new ScalarTypeBytesEncrypted(baseType, support);
            prop.setScalarType(encryptedScalarType);
            prop.setLocalEncrypted(true);
            return;
        }
        if (dbEncString) {
            final DbEncrypt dbEncrypt = this.util.getDbPlatform().getDbEncrypt();
            if (dbEncrypt != null) {
                final int jdbcType = prop.getScalarType().getJdbcType();
                final DbEncryptFunction dbEncryptFunction = dbEncrypt.getDbEncryptFunction(jdbcType);
                if (dbEncryptFunction != null) {
                    prop.setDbEncryptFunction(dbEncryptFunction, dbEncrypt, dbLen);
                    return;
                }
            }
        }
        prop.setScalarType(this.createScalarType(prop, st));
        prop.setLocalEncrypted(true);
        if (dbLen > 0) {
            prop.setDbLength(dbLen);
        }
    }
    
    private ScalarTypeEncryptedWrapper<?> createScalarType(final DeployBeanProperty prop, final ScalarType<?> st) {
        final DataEncryptSupport support = this.createDataEncryptSupport(prop);
        final ScalarTypeBytesBase byteType = this.getDbEncryptType(prop);
        return new ScalarTypeEncryptedWrapper<Object>(st, byteType, support);
    }
    
    private ScalarTypeBytesBase getDbEncryptType(final DeployBeanProperty prop) {
        final int dbType = prop.isLob() ? 2004 : -3;
        return (ScalarTypeBytesBase)this.util.getTypeManager().getScalarType(dbType);
    }
    
    private DataEncryptSupport createDataEncryptSupport(final DeployBeanProperty prop) {
        final String table = this.info.getDescriptor().getBaseTable();
        final String column = prop.getDbColumn();
        return this.util.createDataEncryptSupport(table, column);
    }
    
    private void readId(final Id id, final DeployBeanProperty prop) {
        prop.setId(true);
        prop.setNullable(false);
        if (prop.getPropertyType().equals(UUID.class) && this.descriptor.getIdGeneratorName() == null) {
            this.descriptor.setIdGeneratorName("auto.uuid");
            this.descriptor.setIdType(IdType.GENERATOR);
        }
    }
    
    private void readGenValue(final GeneratedValue gen, final DeployBeanProperty prop) {
        String genName = gen.generator();
        final SequenceGenerator sequenceGenerator = this.find(prop, SequenceGenerator.class);
        if (sequenceGenerator != null && sequenceGenerator.name().equals(genName)) {
            genName = sequenceGenerator.sequenceName();
        }
        final GenerationType strategy = gen.strategy();
        if (strategy == GenerationType.IDENTITY) {
            this.descriptor.setIdType(IdType.IDENTITY);
        }
        else if (strategy == GenerationType.SEQUENCE) {
            this.descriptor.setIdType(IdType.SEQUENCE);
            if (genName != null && genName.length() > 0) {
                this.descriptor.setIdGeneratorName(genName);
            }
        }
        else if (strategy == GenerationType.AUTO && prop.getPropertyType().equals(UUID.class)) {
            this.descriptor.setIdGeneratorName("auto.uuid");
            this.descriptor.setIdType(IdType.GENERATOR);
        }
    }
    
    private void readTemporal(final Temporal temporal, final DeployBeanProperty prop) {
        final TemporalType type = temporal.value();
        if (type.equals(TemporalType.DATE)) {
            prop.setDbType(91);
        }
        else if (type.equals(TemporalType.TIMESTAMP)) {
            prop.setDbType(93);
        }
        else {
            if (!type.equals(TemporalType.TIME)) {
                throw new PersistenceException("Unhandled type " + type);
            }
            prop.setDbType(92);
        }
    }
    
    private void readColumn(final Column columnAnn, final DeployBeanProperty prop) {
        if (!this.isEmpty(columnAnn.name())) {
            final String dbColumn = this.databasePlatform.convertQuotedIdentifiers(columnAnn.name());
            prop.setDbColumn(dbColumn);
        }
        prop.setDbInsertable(columnAnn.insertable());
        prop.setDbUpdateable(columnAnn.updatable());
        prop.setNullable(columnAnn.nullable());
        prop.setUnique(columnAnn.unique());
        if (columnAnn.precision() > 0) {
            prop.setDbLength(columnAnn.precision());
        }
        else if (columnAnn.length() != 255) {
            prop.setDbLength(columnAnn.length());
        }
        prop.setDbScale(columnAnn.scale());
        prop.setDbColumnDefn(columnAnn.columnDefinition());
        final String baseTable = this.descriptor.getBaseTable();
        final String tableName = columnAnn.table();
        if (!tableName.equals("")) {
            if (!tableName.equalsIgnoreCase(baseTable)) {
                prop.setSecondaryTable(tableName);
            }
        }
    }
    
    private void readValidations(final DeployBeanProperty prop) {
        final Field field = prop.getField();
        if (field != null) {
            final Annotation[] fieldAnnotations = field.getAnnotations();
            for (int i = 0; i < fieldAnnotations.length; ++i) {
                this.readValidations(prop, fieldAnnotations[i]);
            }
        }
        final Method readMethod = prop.getReadMethod();
        if (readMethod != null) {
            final Annotation[] methAnnotations = readMethod.getAnnotations();
            for (int j = 0; j < methAnnotations.length; ++j) {
                this.readValidations(prop, methAnnotations[j]);
            }
        }
    }
    
    private void readValidations(final DeployBeanProperty prop, final Annotation ann) {
        final Class<?> type = ann.annotationType();
        if (type.equals(Patterns.class)) {
            final Patterns patterns = (Patterns)ann;
            final Pattern[] patternsArray = patterns.patterns();
            for (int i = 0; i < patternsArray.length; ++i) {
                this.util.createValidator(prop, patternsArray[i]);
            }
        }
        else {
            final ValidatorMeta meta = type.getAnnotation(ValidatorMeta.class);
            if (meta != null) {
                this.util.createValidator(prop, ann);
            }
        }
    }
    
    static {
        LDAP_BOOLEAN_SCALARTYPE = new ScalarTypeLdapBoolean();
    }
}
