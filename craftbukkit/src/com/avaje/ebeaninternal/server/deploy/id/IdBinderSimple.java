// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import javax.naming.InvalidNameException;
import javax.naming.ldap.Rdn;
import javax.naming.ldap.LdapName;
import java.util.List;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public final class IdBinderSimple implements IdBinder
{
    private final BeanProperty idProperty;
    private final String bindIdSql;
    private final BeanProperty[] properties;
    private final Class<?> expectedType;
    private final ScalarType scalarType;
    
    public IdBinderSimple(final BeanProperty idProperty) {
        this.idProperty = idProperty;
        this.scalarType = idProperty.getScalarType();
        this.expectedType = idProperty.getPropertyType();
        (this.properties = new BeanProperty[1])[0] = idProperty;
        this.bindIdSql = InternString.intern(idProperty.getDbColumn() + " = ? ");
    }
    
    public void initialise() {
    }
    
    public Object readTerm(final String idTermValue) {
        return this.scalarType.parse(idTermValue);
    }
    
    public String writeTerm(final Object idValue) {
        return this.scalarType.format(idValue);
    }
    
    public String getOrderBy(final String pathPrefix, final boolean ascending) {
        final StringBuilder sb = new StringBuilder();
        if (pathPrefix != null) {
            sb.append(pathPrefix).append(".");
        }
        sb.append(this.idProperty.getName());
        if (!ascending) {
            sb.append(" desc");
        }
        return sb.toString();
    }
    
    public void buildSelectExpressionChain(final String prefix, final List<String> selectChain) {
        this.idProperty.buildSelectExpressionChain(prefix, selectChain);
    }
    
    public void createLdapNameById(final LdapName name, final Object id) throws InvalidNameException {
        final Rdn rdn = new Rdn(this.idProperty.getDbColumn(), id);
        name.add(rdn);
    }
    
    public void createLdapNameByBean(final LdapName name, final Object bean) throws InvalidNameException {
        final Object id = this.idProperty.getValue(bean);
        this.createLdapNameById(name, id);
    }
    
    public int getPropertyCount() {
        return 1;
    }
    
    public String getIdProperty() {
        return this.idProperty.getName();
    }
    
    public BeanProperty findBeanProperty(final String dbColumnName) {
        if (dbColumnName.equalsIgnoreCase(this.idProperty.getDbColumn())) {
            return this.idProperty;
        }
        return null;
    }
    
    public boolean isComplexId() {
        return false;
    }
    
    public String getDefaultOrderBy() {
        return this.idProperty.getName();
    }
    
    public BeanProperty[] getProperties() {
        return this.properties;
    }
    
    public String getBindIdInSql(final String baseTableAlias) {
        if (baseTableAlias == null) {
            return this.idProperty.getDbColumn();
        }
        return baseTableAlias + "." + this.idProperty.getDbColumn();
    }
    
    public String getBindIdSql(final String baseTableAlias) {
        if (baseTableAlias == null) {
            return this.bindIdSql;
        }
        return baseTableAlias + "." + this.bindIdSql;
    }
    
    public Object[] getIdValues(final Object bean) {
        return new Object[] { this.idProperty.getValue(bean) };
    }
    
    public Object[] getBindValues(final Object idValue) {
        return new Object[] { idValue };
    }
    
    public String getIdInValueExprDelete(final int size) {
        return this.getIdInValueExpr(size);
    }
    
    public String getIdInValueExpr(final int size) {
        final StringBuilder sb = new StringBuilder(2 * size + 10);
        sb.append(" in");
        sb.append(" (?");
        for (int i = 1; i < size; ++i) {
            sb.append(",?");
        }
        sb.append(") ");
        return sb.toString();
    }
    
    public void addIdInBindValue(final SpiExpressionRequest request, Object value) {
        value = this.convertSetId(value, null);
        request.addBindValue(value);
    }
    
    public void bindId(final DefaultSqlUpdate sqlUpdate, final Object value) {
        sqlUpdate.addParameter(value);
    }
    
    public void bindId(final DataBind dataBind, Object value) throws SQLException {
        value = this.idProperty.toBeanType(value);
        this.idProperty.bind(dataBind, value);
    }
    
    public void writeData(final DataOutput os, final Object value) throws IOException {
        this.idProperty.writeData(os, value);
    }
    
    public Object readData(final DataInput is) throws IOException {
        return this.idProperty.readData(is);
    }
    
    public void loadIgnore(final DbReadContext ctx) {
        this.idProperty.loadIgnore(ctx);
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean) throws SQLException {
        final Object id = this.idProperty.read(ctx);
        if (id != null) {
            this.idProperty.setValue(bean, id);
        }
        return id;
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        return this.idProperty.read(ctx);
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
        this.idProperty.appendSelect(ctx, subQuery);
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        final StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
            sb.append(".");
        }
        sb.append(this.idProperty.getName());
        sb.append(operator);
        return sb.toString();
    }
    
    public String getAssocIdInExpr(final String prefix) {
        final StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
            sb.append(".");
        }
        sb.append(this.idProperty.getName());
        return sb.toString();
    }
    
    public Object convertSetId(Object idValue, final Object bean) {
        if (!idValue.getClass().equals(this.expectedType)) {
            idValue = this.scalarType.toBeanType(idValue);
        }
        if (bean != null) {
            this.idProperty.setValueIntercept(bean, idValue);
        }
        return idValue;
    }
}
