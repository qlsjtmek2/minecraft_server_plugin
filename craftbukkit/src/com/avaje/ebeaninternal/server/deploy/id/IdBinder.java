// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import java.util.List;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public interface IdBinder
{
    void initialise();
    
    String writeTerm(final Object p0);
    
    Object readTerm(final String p0);
    
    void writeData(final DataOutput p0, final Object p1) throws IOException;
    
    Object readData(final DataInput p0) throws IOException;
    
    void createLdapNameById(final LdapName p0, final Object p1) throws InvalidNameException;
    
    void createLdapNameByBean(final LdapName p0, final Object p1) throws InvalidNameException;
    
    String getIdProperty();
    
    BeanProperty findBeanProperty(final String p0);
    
    int getPropertyCount();
    
    boolean isComplexId();
    
    String getDefaultOrderBy();
    
    String getOrderBy(final String p0, final boolean p1);
    
    Object[] getBindValues(final Object p0);
    
    Object[] getIdValues(final Object p0);
    
    String getAssocOneIdExpr(final String p0, final String p1);
    
    String getAssocIdInExpr(final String p0);
    
    void bindId(final DataBind p0, final Object p1) throws SQLException;
    
    void bindId(final DefaultSqlUpdate p0, final Object p1);
    
    void addIdInBindValue(final SpiExpressionRequest p0, final Object p1);
    
    String getBindIdInSql(final String p0);
    
    String getIdInValueExpr(final int p0);
    
    String getIdInValueExprDelete(final int p0);
    
    void buildSelectExpressionChain(final String p0, final List<String> p1);
    
    Object readSet(final DbReadContext p0, final Object p1) throws SQLException;
    
    void loadIgnore(final DbReadContext p0);
    
    Object read(final DbReadContext p0) throws SQLException;
    
    void appendSelect(final DbSqlContext p0, final boolean p1);
    
    String getBindIdSql(final String p0);
    
    BeanProperty[] getProperties();
    
    Object convertSetId(final Object p0, final Object p1);
}
