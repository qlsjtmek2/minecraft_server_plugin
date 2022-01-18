// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import java.util.Map;
import com.avaje.ebean.config.dbplatform.DbEncrypt;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import javax.persistence.FetchType;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.type.ScalarTypeEnum;
import com.avaje.ebeaninternal.server.core.InternString;
import javax.persistence.Version;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.avaje.ebean.annotation.CreatedTimestamp;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
import java.util.ArrayList;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebean.validation.factory.Validator;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
import java.lang.reflect.Method;
import java.util.HashMap;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.lang.reflect.Field;
import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
import com.avaje.ebean.config.ldap.LdapAttributeAdapter;

public class DeployBeanProperty
{
    private static final int ID_ORDER = 1000000;
    private static final int UNIDIRECTIONAL_ORDER = 100000;
    private static final int AUDITCOLUMN_ORDER = -1000000;
    private static final int VERSIONCOLUMN_ORDER = -1000000;
    public static final String EXCLUDE_FROM_UPDATE_WHERE = "EXCLUDE_FROM_UPDATE_WHERE";
    public static final String EXCLUDE_FROM_DELETE_WHERE = "EXCLUDE_FROM_DELETE_WHERE";
    public static final String EXCLUDE_FROM_INSERT = "EXCLUDE_FROM_INSERT";
    public static final String EXCLUDE_FROM_UPDATE = "EXCLUDE_FROM_UPDATE";
    private boolean id;
    private boolean embedded;
    private boolean versionColumn;
    private boolean fetchEager;
    private boolean nullable;
    private boolean unique;
    private LdapAttributeAdapter ldapAttributeAdapter;
    private int dbLength;
    private int dbScale;
    private String dbColumnDefn;
    private boolean isTransient;
    private boolean localEncrypted;
    private boolean dbEncrypted;
    private DbEncryptFunction dbEncryptFunction;
    private int dbEncryptedType;
    private String dbBind;
    private boolean dbRead;
    private boolean dbInsertable;
    private boolean dbUpdateable;
    private DeployTableJoin secondaryTableJoin;
    private String secondaryTableJoinPrefix;
    private String secondaryTable;
    private Class<?> owningType;
    private boolean lob;
    private String name;
    private Field field;
    private Class<?> propertyType;
    private ScalarType<?> scalarType;
    private String dbColumn;
    private String sqlFormulaSelect;
    private String sqlFormulaJoin;
    private int dbType;
    private Object defaultValue;
    private HashMap<String, String> extraAttributeMap;
    private Method readMethod;
    private Method writeMethod;
    private BeanReflectGetter getter;
    private BeanReflectSetter setter;
    private GeneratedProperty generatedProperty;
    private List<Validator> validators;
    private final DeployBeanDescriptor<?> desc;
    private boolean undirectionalShadow;
    private int sortOrder;
    
    public DeployBeanProperty(final DeployBeanDescriptor<?> desc, final Class<?> propertyType, final ScalarType<?> scalarType, final ScalarTypeConverter<?, ?> typeConverter) {
        this.fetchEager = true;
        this.nullable = true;
        this.dbBind = "?";
        this.extraAttributeMap = new HashMap<String, String>();
        this.validators = new ArrayList<Validator>();
        this.desc = desc;
        this.propertyType = propertyType;
        this.scalarType = this.wrapScalarType(propertyType, scalarType, typeConverter);
    }
    
    private ScalarType<?> wrapScalarType(final Class<?> propertyType, final ScalarType<?> scalarType, final ScalarTypeConverter<?, ?> typeConverter) {
        if (typeConverter == null) {
            return scalarType;
        }
        return new ScalarTypeWrapper<Object, Object>(propertyType, scalarType, typeConverter);
    }
    
    public int getSortOverride() {
        if (this.field == null) {
            return 0;
        }
        if (this.field.getAnnotation(Id.class) != null) {
            return 1000000;
        }
        if (this.field.getAnnotation(EmbeddedId.class) != null) {
            return 1000000;
        }
        if (this.undirectionalShadow) {
            return 100000;
        }
        if (this.field.getAnnotation(CreatedTimestamp.class) != null) {
            return -1000000;
        }
        if (this.field.getAnnotation(UpdatedTimestamp.class) != null) {
            return -1000000;
        }
        if (this.field.getAnnotation(Version.class) != null) {
            return -1000000;
        }
        return 0;
    }
    
    public boolean isScalar() {
        return true;
    }
    
    public String getFullBeanName() {
        return this.desc.getFullName() + "." + this.name;
    }
    
    public boolean isNullablePrimitive() {
        return this.nullable && this.propertyType.isPrimitive();
    }
    
    public int getDbLength() {
        if (this.dbLength == 0 && this.scalarType != null) {
            return this.scalarType.getLength();
        }
        return this.dbLength;
    }
    
    public int getSortOrder() {
        return this.sortOrder;
    }
    
    public void setSortOrder(final int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public boolean isUndirectionalShadow() {
        return this.undirectionalShadow;
    }
    
    public void setUndirectionalShadow(final boolean undirectionalShadow) {
        this.undirectionalShadow = undirectionalShadow;
    }
    
    public boolean isLocalEncrypted() {
        return this.localEncrypted;
    }
    
    public void setLocalEncrypted(final boolean localEncrypted) {
        this.localEncrypted = localEncrypted;
    }
    
    public void setDbLength(final int dbLength) {
        this.dbLength = dbLength;
    }
    
    public int getDbScale() {
        return this.dbScale;
    }
    
    public void setDbScale(final int dbScale) {
        this.dbScale = dbScale;
    }
    
    public String getDbColumnDefn() {
        return this.dbColumnDefn;
    }
    
    public void setDbColumnDefn(final String dbColumnDefn) {
        if (dbColumnDefn == null || dbColumnDefn.trim().length() == 0) {
            this.dbColumnDefn = null;
        }
        else {
            this.dbColumnDefn = InternString.intern(dbColumnDefn);
        }
    }
    
    public String getDbConstraintExpression() {
        if (this.scalarType instanceof ScalarTypeEnum) {
            final ScalarTypeEnum etype = (ScalarTypeEnum)this.scalarType;
            return "check (" + this.dbColumn + " in " + etype.getContraintInValues() + ")";
        }
        return null;
    }
    
    public void addValidator(final Validator validator) {
        this.validators.add(validator);
    }
    
    public boolean containsValidatorType(final Class<?> type) {
        for (final Validator validator : this.validators) {
            if (validator.getClass().equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    public Validator[] getValidators() {
        return this.validators.toArray(new Validator[this.validators.size()]);
    }
    
    public ScalarType<?> getScalarType() {
        return this.scalarType;
    }
    
    public void setScalarType(final ScalarType<?> scalarType) {
        this.scalarType = scalarType;
    }
    
    public BeanReflectGetter getGetter() {
        return this.getter;
    }
    
    public BeanReflectSetter getSetter() {
        return this.setter;
    }
    
    public Method getReadMethod() {
        return this.readMethod;
    }
    
    public Method getWriteMethod() {
        return this.writeMethod;
    }
    
    public void setOwningType(final Class<?> owningType) {
        this.owningType = owningType;
    }
    
    public Class<?> getOwningType() {
        return this.owningType;
    }
    
    public boolean isLocal() {
        return this.owningType == null || this.owningType.equals(this.desc.getBeanType());
    }
    
    public void setGetter(final BeanReflectGetter getter) {
        this.getter = getter;
    }
    
    public void setSetter(final BeanReflectSetter setter) {
        this.setter = setter;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = InternString.intern(name);
    }
    
    public Field getField() {
        return this.field;
    }
    
    public void setField(final Field field) {
        this.field = field;
    }
    
    public boolean isGenerated() {
        return this.generatedProperty != null;
    }
    
    public GeneratedProperty getGeneratedProperty() {
        return this.generatedProperty;
    }
    
    public void setGeneratedProperty(final GeneratedProperty generatedValue) {
        this.generatedProperty = generatedValue;
    }
    
    public boolean isNullable() {
        return this.nullable;
    }
    
    public void setNullable(final boolean isNullable) {
        this.nullable = isNullable;
    }
    
    public boolean isUnique() {
        return this.unique;
    }
    
    public void setUnique(final boolean unique) {
        this.unique = unique;
    }
    
    public LdapAttributeAdapter getLdapAttributeAdapter() {
        return this.ldapAttributeAdapter;
    }
    
    public void setLdapAttributeAdapter(final LdapAttributeAdapter ldapAttributeAdapter) {
        this.ldapAttributeAdapter = ldapAttributeAdapter;
    }
    
    public boolean isVersionColumn() {
        return this.versionColumn;
    }
    
    public void setVersionColumn(final boolean isVersionColumn) {
        this.versionColumn = isVersionColumn;
    }
    
    public boolean isFetchEager() {
        return this.fetchEager;
    }
    
    public void setFetchType(final FetchType fetchType) {
        this.fetchEager = FetchType.EAGER.equals(fetchType);
    }
    
    public String getSqlFormulaSelect() {
        return this.sqlFormulaSelect;
    }
    
    public String getSqlFormulaJoin() {
        return this.sqlFormulaJoin;
    }
    
    public void setSqlFormula(final String formulaSelect, final String formulaJoin) {
        this.sqlFormulaSelect = formulaSelect;
        this.sqlFormulaJoin = (formulaJoin.equals("") ? null : formulaJoin);
        this.dbRead = true;
        this.dbInsertable = false;
        this.dbUpdateable = false;
    }
    
    public String getElPlaceHolder(final BeanDescriptor.EntityType et) {
        if (this.sqlFormulaSelect != null) {
            return this.sqlFormulaSelect;
        }
        if (BeanDescriptor.EntityType.LDAP.equals(et)) {
            return this.getDbColumn();
        }
        if (this.secondaryTableJoinPrefix != null) {
            return "${" + this.secondaryTableJoinPrefix + "}" + this.getDbColumn();
        }
        return "${}" + this.getDbColumn();
    }
    
    public String getDbColumn() {
        if (this.sqlFormulaSelect != null) {
            return this.sqlFormulaSelect;
        }
        return this.dbColumn;
    }
    
    public void setDbColumn(final String dbColumn) {
        this.dbColumn = InternString.intern(dbColumn);
    }
    
    public int getDbType() {
        return this.dbType;
    }
    
    public void setDbType(final int dbType) {
        this.dbType = dbType;
        this.lob = this.isLobType(dbType);
    }
    
    public boolean isLob() {
        return this.lob;
    }
    
    private boolean isLobType(final int type) {
        switch (type) {
            case 2005: {
                return true;
            }
            case 2004: {
                return true;
            }
            case -4: {
                return true;
            }
            case -1: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isSecondaryTable() {
        return this.secondaryTable != null;
    }
    
    public String getSecondaryTable() {
        return this.secondaryTable;
    }
    
    public void setSecondaryTable(final String secondaryTable) {
        this.secondaryTable = secondaryTable;
        this.dbInsertable = false;
        this.dbUpdateable = false;
    }
    
    public String getSecondaryTableJoinPrefix() {
        return this.secondaryTableJoinPrefix;
    }
    
    public DeployTableJoin getSecondaryTableJoin() {
        return this.secondaryTableJoin;
    }
    
    public void setSecondaryTableJoin(final DeployTableJoin secondaryTableJoin, final String prefix) {
        this.secondaryTableJoin = secondaryTableJoin;
        this.secondaryTableJoinPrefix = prefix;
    }
    
    public String getDbBind() {
        return this.dbBind;
    }
    
    public void setDbBind(final String dbBind) {
        this.dbBind = dbBind;
    }
    
    public boolean isDbEncrypted() {
        return this.dbEncrypted;
    }
    
    public DbEncryptFunction getDbEncryptFunction() {
        return this.dbEncryptFunction;
    }
    
    public void setDbEncryptFunction(final DbEncryptFunction dbEncryptFunction, final DbEncrypt dbEncrypt, final int dbLen) {
        this.dbEncryptFunction = dbEncryptFunction;
        this.dbEncrypted = true;
        this.dbBind = dbEncryptFunction.getEncryptBindSql();
        this.dbEncryptedType = (this.isLob() ? 2004 : dbEncrypt.getEncryptDbType());
        if (dbLen > 0) {
            this.setDbLength(dbLen);
        }
    }
    
    public int getDbEncryptedType() {
        return this.dbEncryptedType;
    }
    
    public void setDbEncryptedType(final int dbEncryptedType) {
        this.dbEncryptedType = dbEncryptedType;
    }
    
    public boolean isDbRead() {
        return this.dbRead;
    }
    
    public void setDbRead(final boolean isDBRead) {
        this.dbRead = isDBRead;
    }
    
    public boolean isDbInsertable() {
        return this.dbInsertable;
    }
    
    public void setDbInsertable(final boolean insertable) {
        this.dbInsertable = insertable;
    }
    
    public boolean isDbUpdateable() {
        return this.dbUpdateable;
    }
    
    public void setDbUpdateable(final boolean updateable) {
        this.dbUpdateable = updateable;
    }
    
    public boolean isTransient() {
        return this.isTransient;
    }
    
    public void setTransient(final boolean isTransient) {
        this.isTransient = isTransient;
    }
    
    public void setReadMethod(final Method readMethod) {
        this.readMethod = readMethod;
    }
    
    public void setWriteMethod(final Method writeMethod) {
        this.writeMethod = writeMethod;
    }
    
    public Class<?> getPropertyType() {
        return this.propertyType;
    }
    
    public boolean isId() {
        return this.id;
    }
    
    public void setId(final boolean id) {
        this.id = id;
    }
    
    public boolean isEmbedded() {
        return this.embedded;
    }
    
    public void setEmbedded(final boolean embedded) {
        this.embedded = embedded;
    }
    
    public Map<String, String> getExtraAttributeMap() {
        return this.extraAttributeMap;
    }
    
    public String getExtraAttribute(final String key) {
        return this.extraAttributeMap.get(key);
    }
    
    public void setExtraAttribute(final String key, final String value) {
        this.extraAttributeMap.put(key, value);
    }
    
    public Object getDefaultValue() {
        return this.defaultValue;
    }
    
    public void setDefaultValue(final Object defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public String toString() {
        return this.desc.getFullName() + "." + this.name;
    }
}
