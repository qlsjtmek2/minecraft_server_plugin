// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebean.config.dbplatform.DbType;
import com.avaje.ebean.text.StringParser;
import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.bean.EntityBean;
import javax.naming.NamingException;
import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.Attribute;
import com.avaje.ebean.InvalidValue;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.util.List;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import java.sql.SQLException;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebeaninternal.util.ValueUtil;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebean.config.lucene.LuceneIndex;
import java.util.ArrayList;
import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
import com.avaje.ebean.validation.factory.Validator;
import com.avaje.ebean.config.ldap.LdapAttributeAdapter;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
import java.lang.reflect.Method;
import java.util.Map;
import java.lang.reflect.Field;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;

public class BeanProperty implements ElPropertyValue
{
    public static final String EXCLUDE_FROM_UPDATE_WHERE = "EXCLUDE_FROM_UPDATE_WHERE";
    public static final String EXCLUDE_FROM_DELETE_WHERE = "EXCLUDE_FROM_DELETE_WHERE";
    public static final String EXCLUDE_FROM_INSERT = "EXCLUDE_FROM_INSERT";
    public static final String EXCLUDE_FROM_UPDATE = "EXCLUDE_FROM_UPDATE";
    final boolean id;
    final boolean unidirectionalShadow;
    final boolean embedded;
    final boolean version;
    final boolean nullable;
    final boolean unique;
    final boolean dbRead;
    final boolean dbInsertable;
    final boolean dbUpdatable;
    final boolean secondaryTable;
    final TableJoin secondaryTableJoin;
    final String secondaryTableJoinPrefix;
    final boolean inherited;
    final Class<?> owningType;
    final boolean local;
    final boolean lob;
    final boolean fetchEager;
    final boolean isTransient;
    final String name;
    final Field field;
    final Class<?> propertyType;
    final String dbBind;
    final String dbColumn;
    final String elPlaceHolder;
    final String elPlaceHolderEncrypted;
    final String sqlFormulaSelect;
    final String sqlFormulaJoin;
    final boolean formula;
    final boolean dbEncrypted;
    final boolean localEncrypted;
    final int dbEncryptedType;
    final int dbType;
    final Object defaultValue;
    final Map<String, String> extraAttributeMap;
    final Method readMethod;
    final Method writeMethod;
    final GeneratedProperty generatedProperty;
    final BeanReflectGetter getter;
    final BeanReflectSetter setter;
    final BeanDescriptor<?> descriptor;
    final ScalarType scalarType;
    final LdapAttributeAdapter ldapAttributeAdapter;
    final Validator[] validators;
    final boolean hasLocalValidators;
    boolean cascadeValidate;
    final int dbLength;
    final int dbScale;
    final String dbColumnDefn;
    final String dbConstraintExpression;
    final DbEncryptFunction dbEncryptFunction;
    final boolean dynamicSubclassWithInheritance;
    int deployOrder;
    private static Object[] NO_ARGS;
    private ArrayList<LuceneIndex> luceneIndexes;
    
    public BeanProperty(final DeployBeanProperty deploy) {
        this(null, null, deploy);
    }
    
    public BeanProperty(final BeanDescriptorMap owner, final BeanDescriptor<?> descriptor, final DeployBeanProperty deploy) {
        this.descriptor = descriptor;
        this.name = InternString.intern(deploy.getName());
        if (descriptor != null) {
            this.dynamicSubclassWithInheritance = (descriptor.isDynamicSubclass() && descriptor.hasInheritance());
        }
        else {
            this.dynamicSubclassWithInheritance = false;
        }
        this.unidirectionalShadow = deploy.isUndirectionalShadow();
        this.localEncrypted = deploy.isLocalEncrypted();
        this.dbEncrypted = deploy.isDbEncrypted();
        this.dbEncryptedType = deploy.getDbEncryptedType();
        this.dbEncryptFunction = deploy.getDbEncryptFunction();
        this.dbBind = deploy.getDbBind();
        this.dbRead = deploy.isDbRead();
        this.dbInsertable = deploy.isDbInsertable();
        this.dbUpdatable = deploy.isDbUpdateable();
        this.secondaryTable = deploy.isSecondaryTable();
        if (this.secondaryTable) {
            this.secondaryTableJoin = new TableJoin(deploy.getSecondaryTableJoin(), null);
            this.secondaryTableJoinPrefix = deploy.getSecondaryTableJoinPrefix();
        }
        else {
            this.secondaryTableJoin = null;
            this.secondaryTableJoinPrefix = null;
        }
        this.fetchEager = deploy.isFetchEager();
        this.isTransient = deploy.isTransient();
        this.nullable = deploy.isNullable();
        this.unique = deploy.isUnique();
        this.dbLength = deploy.getDbLength();
        this.dbScale = deploy.getDbScale();
        this.dbColumnDefn = InternString.intern(deploy.getDbColumnDefn());
        this.dbConstraintExpression = InternString.intern(deploy.getDbConstraintExpression());
        this.inherited = false;
        this.owningType = deploy.getOwningType();
        this.local = deploy.isLocal();
        this.version = deploy.isVersionColumn();
        this.embedded = deploy.isEmbedded();
        this.id = deploy.isId();
        this.generatedProperty = deploy.getGeneratedProperty();
        this.readMethod = deploy.getReadMethod();
        this.writeMethod = deploy.getWriteMethod();
        this.getter = deploy.getGetter();
        if (descriptor != null && this.getter == null && !this.unidirectionalShadow) {
            final String m = "Null Getter for: " + this.getFullBeanName();
            throw new RuntimeException(m);
        }
        this.setter = deploy.getSetter();
        this.dbColumn = this.tableAliasIntern(descriptor, deploy.getDbColumn(), false, null);
        this.sqlFormulaJoin = InternString.intern(deploy.getSqlFormulaJoin());
        this.sqlFormulaSelect = InternString.intern(deploy.getSqlFormulaSelect());
        this.formula = (this.sqlFormulaSelect != null);
        this.extraAttributeMap = deploy.getExtraAttributeMap();
        this.defaultValue = deploy.getDefaultValue();
        this.dbType = deploy.getDbType();
        this.scalarType = deploy.getScalarType();
        this.ldapAttributeAdapter = deploy.getLdapAttributeAdapter();
        this.lob = this.isLobType(this.dbType);
        this.propertyType = deploy.getPropertyType();
        this.field = deploy.getField();
        this.validators = deploy.getValidators();
        this.hasLocalValidators = (this.validators.length > 0);
        final BeanDescriptor.EntityType et = (descriptor == null) ? null : descriptor.getEntityType();
        this.elPlaceHolder = this.tableAliasIntern(descriptor, deploy.getElPlaceHolder(et), false, null);
        this.elPlaceHolderEncrypted = this.tableAliasIntern(descriptor, deploy.getElPlaceHolder(et), this.dbEncrypted, this.dbColumn);
    }
    
    private String tableAliasIntern(final BeanDescriptor<?> descriptor, String s, final boolean dbEncrypted, final String dbColumn) {
        if (descriptor != null) {
            s = StringHelper.replaceString(s, "${ta}.", "${}");
            s = StringHelper.replaceString(s, "${ta}", "${}");
            if (dbEncrypted) {
                s = this.dbEncryptFunction.getDecryptSql(s);
                final String namedParam = ":encryptkey_" + descriptor.getBaseTable() + "___" + dbColumn;
                s = StringHelper.replaceString(s, "?", namedParam);
            }
        }
        return InternString.intern(s);
    }
    
    public BeanProperty(final BeanProperty source, final BeanPropertyOverride override) {
        this.descriptor = source.descriptor;
        this.name = InternString.intern(source.getName());
        this.dynamicSubclassWithInheritance = source.dynamicSubclassWithInheritance;
        this.dbColumn = InternString.intern(override.getDbColumn());
        this.sqlFormulaJoin = InternString.intern(override.getSqlFormulaJoin());
        this.sqlFormulaSelect = InternString.intern(override.getSqlFormulaSelect());
        this.formula = (this.sqlFormulaSelect != null);
        this.fetchEager = source.fetchEager;
        this.unidirectionalShadow = source.unidirectionalShadow;
        this.localEncrypted = source.isLocalEncrypted();
        this.isTransient = source.isTransient();
        this.secondaryTable = source.isSecondaryTable();
        this.secondaryTableJoin = source.secondaryTableJoin;
        this.secondaryTableJoinPrefix = source.secondaryTableJoinPrefix;
        this.dbBind = source.getDbBind();
        this.dbEncrypted = source.isDbEncrypted();
        this.dbEncryptedType = source.getDbEncryptedType();
        this.dbEncryptFunction = source.dbEncryptFunction;
        this.dbRead = source.isDbRead();
        this.dbInsertable = source.isDbInsertable();
        this.dbUpdatable = source.isDbUpdatable();
        this.nullable = source.isNullable();
        this.unique = source.isUnique();
        this.dbLength = source.getDbLength();
        this.dbScale = source.getDbScale();
        this.dbColumnDefn = InternString.intern(source.getDbColumnDefn());
        this.dbConstraintExpression = InternString.intern(source.getDbConstraintExpression());
        this.inherited = source.isInherited();
        this.owningType = source.owningType;
        this.local = this.owningType.equals(this.descriptor.getBeanType());
        this.version = source.isVersion();
        this.embedded = source.isEmbedded();
        this.id = source.isId();
        this.generatedProperty = source.getGeneratedProperty();
        this.readMethod = source.getReadMethod();
        this.writeMethod = source.getWriteMethod();
        this.getter = source.getter;
        this.setter = source.setter;
        this.extraAttributeMap = source.extraAttributeMap;
        this.defaultValue = source.getDefaultValue();
        this.dbType = source.getDbType();
        this.scalarType = source.scalarType;
        this.ldapAttributeAdapter = source.ldapAttributeAdapter;
        this.lob = this.isLobType(this.dbType);
        this.propertyType = source.getPropertyType();
        this.field = source.getField();
        this.validators = source.getValidators();
        this.hasLocalValidators = (this.validators.length > 0);
        this.elPlaceHolder = override.replace(source.elPlaceHolder, source.dbColumn);
        this.elPlaceHolderEncrypted = override.replace(source.elPlaceHolderEncrypted, source.dbColumn);
    }
    
    public void initialise() {
        if (!this.isTransient && this.scalarType == null) {
            final String msg = "No ScalarType assigned to " + this.descriptor.getFullName() + "." + this.getName();
            throw new RuntimeException(msg);
        }
    }
    
    public int getDeployOrder() {
        return this.deployOrder;
    }
    
    public void setDeployOrder(final int deployOrder) {
        this.deployOrder = deployOrder;
    }
    
    public ElPropertyValue buildElPropertyValue(final String propName, final String remainder, final ElPropertyChainBuilder chain, final boolean propertyDeploy) {
        throw new PersistenceException("Not valid on scalar bean property " + this.getFullBeanName());
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.descriptor;
    }
    
    public boolean isScalar() {
        return true;
    }
    
    public boolean isFormula() {
        return this.formula;
    }
    
    public boolean hasChanged(final Object bean, final Object oldValues) {
        final Object value = this.getValue(bean);
        final Object oldVal = this.getValue(oldValues);
        return !ValueUtil.areEqual(value, oldVal);
    }
    
    public void copyProperty(final Object sourceBean, final Object destBean) {
        final Object value = this.getValue(sourceBean);
        this.setValue(destBean, value);
    }
    
    public void copyProperty(final Object sourceBean, final Object destBean, final CopyContext ctx, final int maxDepth) {
        final Object value = this.getValue(sourceBean);
        this.setValue(destBean, value);
    }
    
    public EncryptKey getEncryptKey() {
        return this.descriptor.getEncryptKey(this);
    }
    
    public String getDecryptProperty() {
        return this.dbEncryptFunction.getDecryptSql(this.getName());
    }
    
    public String getDecryptProperty(final String propertyName) {
        return this.dbEncryptFunction.getDecryptSql(propertyName);
    }
    
    public String getDecryptSql() {
        return this.dbEncryptFunction.getDecryptSql(this.getDbColumn());
    }
    
    public String getDecryptSql(final String tableAlias) {
        return this.dbEncryptFunction.getDecryptSql(tableAlias + "." + this.getDbColumn());
    }
    
    public void appendFrom(final DbSqlContext ctx, final boolean forceOuterJoin) {
        if (this.formula && this.sqlFormulaJoin != null) {
            ctx.appendFormulaJoin(this.sqlFormulaJoin, forceOuterJoin);
        }
        else if (this.secondaryTableJoin != null) {
            final String relativePrefix = ctx.getRelativePrefix(this.secondaryTableJoinPrefix);
            this.secondaryTableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
        }
    }
    
    public String getSecondaryTableJoinPrefix() {
        return this.secondaryTableJoinPrefix;
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
        if (this.formula) {
            ctx.appendFormulaSelect(this.sqlFormulaSelect);
        }
        else if (!this.isTransient) {
            if (this.secondaryTableJoin != null) {
                final String relativePrefix = ctx.getRelativePrefix(this.secondaryTableJoinPrefix);
                ctx.pushTableAlias(relativePrefix);
            }
            if (this.dbEncrypted) {
                final String decryptSql = this.getDecryptSql(ctx.peekTableAlias());
                ctx.appendRawColumn(decryptSql);
                ctx.addEncryptedProp(this);
            }
            else {
                ctx.appendColumn(this.dbColumn);
            }
            if (this.secondaryTableJoin != null) {
                ctx.popTableAlias();
            }
        }
    }
    
    public boolean isAssignableFrom(final Class<?> type) {
        return this.owningType.isAssignableFrom(type);
    }
    
    public Object readSetOwning(final DbReadContext ctx, final Object bean, final Class<?> type) throws SQLException {
        try {
            final Object value = this.scalarType.read(ctx.getDataReader());
            if (value != null) {
                if (bean != null) {
                    if (this.owningType.equals(type)) {
                        this.setValue(bean, value);
                    }
                }
            }
            return value;
        }
        catch (Exception e) {
            final String msg = "Error readSet on " + this.descriptor + "." + this.name;
            throw new PersistenceException(msg, e);
        }
    }
    
    public void loadIgnore(final DbReadContext ctx) {
        this.scalarType.loadIgnore(ctx.getDataReader());
    }
    
    public void load(final SqlBeanLoad sqlBeanLoad) throws SQLException {
        sqlBeanLoad.load(this);
    }
    
    public void buildSelectExpressionChain(final String prefix, final List<String> selectChain) {
        if (prefix == null) {
            selectChain.add(this.name);
        }
        else {
            selectChain.add(prefix + "." + this.name);
        }
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        return this.scalarType.read(ctx.getDataReader());
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean, final Class<?> type) throws SQLException {
        try {
            final Object value = this.scalarType.read(ctx.getDataReader());
            if (bean != null) {
                if (type == null || this.owningType.isAssignableFrom(type)) {
                    this.setValue(bean, value);
                }
            }
            return value;
        }
        catch (Exception e) {
            final String msg = "Error readSet on " + this.descriptor + "." + this.name;
            throw new PersistenceException(msg, e);
        }
    }
    
    public Object toBeanType(final Object value) {
        return this.scalarType.toBeanType(value);
    }
    
    public void bind(final DataBind b, final Object value) throws SQLException {
        this.scalarType.bind(b, value);
    }
    
    public void writeData(final DataOutput dataOutput, final Object value) throws IOException {
        this.scalarType.writeData(dataOutput, value);
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        return this.scalarType.readData(dataInput);
    }
    
    Validator[] getValidators() {
        return this.validators;
    }
    
    public boolean isCascadeValidate() {
        return this.cascadeValidate;
    }
    
    public boolean hasLocalValidators() {
        return this.hasLocalValidators;
    }
    
    public boolean hasValidationRules(final boolean cascade) {
        return this.hasLocalValidators || (cascade && this.cascadeValidate);
    }
    
    public boolean isValueLoaded(final Object value) {
        return true;
    }
    
    public InvalidValue validateCascade(final Object value) {
        return null;
    }
    
    public final List<InvalidValue> validate(final boolean cascade, final Object value) {
        if (!this.isValueLoaded(value)) {
            return null;
        }
        ArrayList<InvalidValue> list = null;
        for (int i = 0; i < this.validators.length; ++i) {
            if (!this.validators[i].isValid(value)) {
                if (list == null) {
                    list = new ArrayList<InvalidValue>();
                }
                final Validator v = this.validators[i];
                list.add(new InvalidValue(v.getKey(), v.getAttributes(), this.descriptor.getFullName(), this.name, value));
            }
        }
        if (list == null && cascade && this.cascadeValidate) {
            final InvalidValue recursive = this.validateCascade(value);
            if (recursive != null) {
                return InvalidValue.toList(recursive);
            }
        }
        return list;
    }
    
    public BeanProperty getBeanProperty() {
        return this;
    }
    
    public Method getReadMethod() {
        return this.readMethod;
    }
    
    public Method getWriteMethod() {
        return this.writeMethod;
    }
    
    public boolean isInherited() {
        return this.inherited;
    }
    
    public boolean isLocal() {
        return this.local;
    }
    
    public Attribute createAttribute(final Object bean) {
        final Object v = this.getValue(bean);
        if (v == null) {
            return null;
        }
        if (this.ldapAttributeAdapter != null) {
            return this.ldapAttributeAdapter.createAttribute(v);
        }
        final Object ldapValue = this.scalarType.toJdbcType(v);
        return new BasicAttribute(this.dbColumn, ldapValue);
    }
    
    public void setAttributeValue(final Object bean, final Attribute attr) {
        try {
            if (attr != null) {
                Object beanValue;
                if (this.ldapAttributeAdapter != null) {
                    beanValue = this.ldapAttributeAdapter.readAttribute(attr);
                }
                else {
                    beanValue = this.scalarType.toBeanType(attr.get());
                }
                this.setValue(bean, beanValue);
            }
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
    
    public void setValue(final Object bean, final Object value) {
        try {
            if (bean instanceof EntityBean) {
                this.setter.set(bean, value);
            }
            else {
                final Object[] args = { value };
                this.writeMethod.invoke(bean, args);
            }
        }
        catch (Exception ex) {
            final String beanType = (bean == null) ? "null" : bean.getClass().getName();
            final String msg = "set " + this.name + " on [" + this.descriptor + "] arg[" + value + "] type[" + beanType + "] threw error";
            throw new RuntimeException(msg, ex);
        }
    }
    
    public void setValueIntercept(final Object bean, final Object value) {
        try {
            if (bean instanceof EntityBean) {
                this.setter.setIntercept(bean, value);
            }
            else {
                final Object[] args = { value };
                this.writeMethod.invoke(bean, args);
            }
        }
        catch (Exception ex) {
            final String beanType = (bean == null) ? "null" : bean.getClass().getName();
            final String msg = "setIntercept " + this.name + " on [" + this.descriptor + "] arg[" + value + "] type[" + beanType + "] threw error";
            throw new RuntimeException(msg, ex);
        }
    }
    
    public Object getValueWithInheritance(final Object bean) {
        if (this.dynamicSubclassWithInheritance) {
            return this.descriptor.getBeanPropertyWithInheritance(bean, this.name);
        }
        return this.getValue(bean);
    }
    
    public Object getValue(final Object bean) {
        try {
            if (bean instanceof EntityBean) {
                return this.getter.get(bean);
            }
            return this.readMethod.invoke(bean, BeanProperty.NO_ARGS);
        }
        catch (Exception ex) {
            final String beanType = (bean == null) ? "null" : bean.getClass().getName();
            final String msg = "get " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
            throw new RuntimeException(msg, ex);
        }
    }
    
    public Object getValueViaReflection(final Object bean) {
        try {
            return this.readMethod.invoke(bean, BeanProperty.NO_ARGS);
        }
        catch (Exception ex) {
            final String beanType = (bean == null) ? "null" : bean.getClass().getName();
            final String msg = "get " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
            throw new RuntimeException(msg, ex);
        }
    }
    
    public Object getValueIntercept(final Object bean) {
        try {
            if (bean instanceof EntityBean) {
                return this.getter.getIntercept(bean);
            }
            return this.readMethod.invoke(bean, BeanProperty.NO_ARGS);
        }
        catch (Exception ex) {
            final String beanType = (bean == null) ? "null" : bean.getClass().getName();
            final String msg = "getIntercept " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
            throw new RuntimeException(msg, ex);
        }
    }
    
    public Object elConvertType(final Object value) {
        if (value == null) {
            return null;
        }
        return this.convertToLogicalType(value);
    }
    
    public void elSetReference(final Object bean) {
        throw new RuntimeException("Should not be called");
    }
    
    public void elSetValue(final Object bean, final Object value, final boolean populate, final boolean reference) {
        if (bean != null) {
            this.setValueIntercept(bean, value);
        }
    }
    
    public Object elGetValue(final Object bean) {
        if (bean == null) {
            return null;
        }
        return this.getValueIntercept(bean);
    }
    
    public Object elGetReference(final Object bean) {
        throw new RuntimeException("Not expected to call this");
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getElName() {
        return this.name;
    }
    
    public boolean isDeployOnly() {
        return false;
    }
    
    public boolean containsManySince(final String sinceProperty) {
        return this.containsMany();
    }
    
    public boolean containsMany() {
        return false;
    }
    
    public Object[] getAssocOneIdValues(final Object bean) {
        return null;
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        return null;
    }
    
    public String getAssocIdInExpr(final String prefix) {
        return null;
    }
    
    public String getAssocIdInValueExpr(final int size) {
        return null;
    }
    
    public boolean isAssocId() {
        return false;
    }
    
    public boolean isAssocProperty() {
        return false;
    }
    
    public String getElPlaceholder(final boolean encrypted) {
        return encrypted ? this.elPlaceHolderEncrypted : this.elPlaceHolder;
    }
    
    public String getElPrefix() {
        return this.secondaryTableJoinPrefix;
    }
    
    public String getFullBeanName() {
        return this.descriptor.getFullName() + "." + this.name;
    }
    
    public ScalarType<?> getScalarType() {
        return (ScalarType<?>)this.scalarType;
    }
    
    public StringFormatter getStringFormatter() {
        return this.scalarType;
    }
    
    public StringParser getStringParser() {
        return this.scalarType;
    }
    
    public boolean isDateTimeCapable() {
        return this.scalarType != null && this.scalarType.isDateTimeCapable();
    }
    
    public int getJdbcType() {
        return (this.scalarType == null) ? 0 : this.scalarType.getJdbcType();
    }
    
    public Object parseDateTime(final long systemTimeMillis) {
        return this.scalarType.parseDateTime(systemTimeMillis);
    }
    
    public int getDbLength() {
        return this.dbLength;
    }
    
    public int getDbScale() {
        return this.dbScale;
    }
    
    public String getDbColumnDefn() {
        return this.dbColumnDefn;
    }
    
    public String getDbConstraintExpression() {
        return this.dbConstraintExpression;
    }
    
    public String renderDbType(final DbType dbType) {
        if (this.dbColumnDefn != null) {
            return this.dbColumnDefn;
        }
        return dbType.renderType(this.dbLength, this.dbScale);
    }
    
    public Field getField() {
        return this.field;
    }
    
    public GeneratedProperty getGeneratedProperty() {
        return this.generatedProperty;
    }
    
    public boolean isNullable() {
        return this.nullable;
    }
    
    public boolean isDDLNotNull() {
        return this.isVersion() || (this.generatedProperty != null && this.generatedProperty.isDDLNotNullable());
    }
    
    public boolean isUnique() {
        return this.unique;
    }
    
    public boolean isTransient() {
        return this.isTransient;
    }
    
    public boolean isVersion() {
        return this.version;
    }
    
    public String getDeployProperty() {
        return this.dbColumn;
    }
    
    public String getDbColumn() {
        return this.dbColumn;
    }
    
    public int getDbType() {
        return this.dbType;
    }
    
    public Object convertToLogicalType(final Object value) {
        if (this.scalarType != null) {
            return this.scalarType.toBeanType(value);
        }
        return value;
    }
    
    public void registerLuceneIndex(final LuceneIndex luceneIndex) {
        if (this.luceneIndexes == null) {
            this.luceneIndexes = new ArrayList<LuceneIndex>();
        }
        this.luceneIndexes.add(luceneIndex);
    }
    
    public boolean isDeltaRequired() {
        return this.luceneIndexes != null;
    }
    
    public boolean isFetchEager() {
        return this.fetchEager;
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
    
    public String getDbBind() {
        return this.dbBind;
    }
    
    public boolean isLocalEncrypted() {
        return this.localEncrypted;
    }
    
    public boolean isDbEncrypted() {
        return this.dbEncrypted;
    }
    
    public int getDbEncryptedType() {
        return this.dbEncryptedType;
    }
    
    public boolean isDbInsertable() {
        return this.dbInsertable;
    }
    
    public boolean isDbUpdatable() {
        return this.dbUpdatable;
    }
    
    public boolean isDbRead() {
        return this.dbRead;
    }
    
    public boolean isSecondaryTable() {
        return this.secondaryTable;
    }
    
    public Class<?> getPropertyType() {
        return this.propertyType;
    }
    
    public boolean isId() {
        return this.id;
    }
    
    public boolean isEmbedded() {
        return this.embedded;
    }
    
    public String getExtraAttribute(final String key) {
        return this.extraAttributeMap.get(key);
    }
    
    public Object getDefaultValue() {
        return this.defaultValue;
    }
    
    public String toString() {
        return this.name;
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final Object bean) {
        final Object value = this.getValueIntercept(bean);
        if (value == null) {
            ctx.appendNull(this.name);
        }
        else {
            final String jv = this.scalarType.jsonToString(value, ctx.getValueAdapter());
            ctx.appendKeyValue(this.name, jv);
        }
    }
    
    public void jsonRead(final ReadJsonContext ctx, final Object bean) {
        final String jsonValue = ctx.readScalarValue();
        Object objValue;
        if (jsonValue == null) {
            objValue = null;
        }
        else {
            objValue = this.scalarType.jsonFromString(jsonValue, ctx.getValueAdapter());
        }
        this.setValue(bean, objValue);
    }
    
    static {
        BeanProperty.NO_ARGS = new Object[0];
    }
}
