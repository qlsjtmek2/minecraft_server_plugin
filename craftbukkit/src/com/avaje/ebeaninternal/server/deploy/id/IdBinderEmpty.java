// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public final class IdBinderEmpty implements IdBinder
{
    private static final String bindIdSql = "";
    private static final BeanProperty[] properties;
    
    public void initialise() {
    }
    
    public Object readTerm(final String idTermValue) {
        return null;
    }
    
    public String writeTerm(final Object idValue) {
        return null;
    }
    
    public String getOrderBy(final String pathPrefix, final boolean ascending) {
        return pathPrefix;
    }
    
    public void buildSelectExpressionChain(final String prefix, final List<String> selectChain) {
    }
    
    public void createLdapNameById(final LdapName name, final Object id) throws InvalidNameException {
    }
    
    public void createLdapNameByBean(final LdapName name, final Object bean) throws InvalidNameException {
    }
    
    public int getPropertyCount() {
        return 0;
    }
    
    public String getIdProperty() {
        return null;
    }
    
    public BeanProperty findBeanProperty(final String dbColumnName) {
        return null;
    }
    
    public boolean isComplexId() {
        return true;
    }
    
    public String getDefaultOrderBy() {
        return "";
    }
    
    public BeanProperty[] getProperties() {
        return IdBinderEmpty.properties;
    }
    
    public String getBindIdSql(final String baseTableAlias) {
        return "";
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        return null;
    }
    
    public String getAssocIdInExpr(final String prefix) {
        return null;
    }
    
    public void addIdInBindValue(final SpiExpressionRequest request, final Object value) {
    }
    
    public String getIdInValueExprDelete(final int size) {
        return this.getIdInValueExpr(size);
    }
    
    public String getIdInValueExpr(final int size) {
        return "";
    }
    
    public String getBindIdInSql(final String baseTableAlias) {
        return null;
    }
    
    public Object[] getIdValues(final Object bean) {
        return null;
    }
    
    public Object[] getBindValues(final Object idValue) {
        return new Object[] { idValue };
    }
    
    public void bindId(final DefaultSqlUpdate sqlUpdate, final Object value) {
    }
    
    public void bindId(final DataBind dataBind, final Object value) throws SQLException {
    }
    
    public void loadIgnore(final DbReadContext ctx) {
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean) throws SQLException {
        return null;
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        return null;
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
    }
    
    public Object convertSetId(final Object idValue, final Object bean) {
        return idValue;
    }
    
    public Object readData(final DataInput dataOutput) throws IOException {
        return null;
    }
    
    public void writeData(final DataOutput dataOutput, final Object idValue) throws IOException {
    }
    
    static {
        properties = new BeanProperty[0];
    }
}
