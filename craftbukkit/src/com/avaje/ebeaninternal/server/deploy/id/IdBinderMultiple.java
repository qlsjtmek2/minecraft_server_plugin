// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.lib.util.MapFromString;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.util.LinkedHashMap;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.naming.ldap.Rdn;
import java.util.Map;
import javax.naming.ldap.LdapName;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public final class IdBinderMultiple implements IdBinder
{
    private final BeanProperty[] props;
    private final String idProperties;
    private final String idInValueSql;
    
    public IdBinderMultiple(final BeanProperty[] idProps) {
        this.props = idProps;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < idProps.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(idProps[i].getName());
        }
        this.idProperties = InternString.intern(sb.toString());
        sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        sb.append(")");
        this.idInValueSql = sb.toString();
    }
    
    public void initialise() {
    }
    
    public String getOrderBy(final String pathPrefix, final boolean ascending) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" ");
            }
            if (pathPrefix != null) {
                sb.append(pathPrefix).append(".");
            }
            sb.append(this.props[i].getName());
            if (!ascending) {
                sb.append(" desc");
            }
        }
        return sb.toString();
    }
    
    public void createLdapNameById(final LdapName name, final Object id) throws InvalidNameException {
        if (!(id instanceof Map)) {
            throw new RuntimeException("Expecting a Map for concatinated key");
        }
        final Map<?, ?> mapId = (Map<?, ?>)id;
        for (int i = 0; i < this.props.length; ++i) {
            final Object v = mapId.get(this.props[i].getName());
            if (v == null) {
                throw new RuntimeException("No value in Map for key " + this.props[i].getName());
            }
            final Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
            name.add(rdn);
        }
    }
    
    public void buildSelectExpressionChain(final String prefix, final List<String> selectChain) {
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].buildSelectExpressionChain(prefix, selectChain);
        }
    }
    
    public void createLdapNameByBean(final LdapName name, final Object bean) throws InvalidNameException {
        for (int i = 0; i < this.props.length; ++i) {
            final Object v = this.props[i].getValue(bean);
            final Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
            name.add(rdn);
        }
    }
    
    public int getPropertyCount() {
        return this.props.length;
    }
    
    public String getIdProperty() {
        return this.idProperties;
    }
    
    public BeanProperty findBeanProperty(final String dbColumnName) {
        for (int i = 0; i < this.props.length; ++i) {
            if (dbColumnName.equalsIgnoreCase(this.props[i].getDbColumn())) {
                return this.props[i];
            }
        }
        return null;
    }
    
    public boolean isComplexId() {
        return true;
    }
    
    public String getDefaultOrderBy() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(this.props[i].getName());
        }
        return sb.toString();
    }
    
    public BeanProperty[] getProperties() {
        return this.props;
    }
    
    public void addIdInBindValue(final SpiExpressionRequest request, final Object value) {
        for (int i = 0; i < this.props.length; ++i) {
            request.addBindValue(this.props[i].getValue(value));
        }
    }
    
    public String getIdInValueExprDelete(final int size) {
        return this.getIdInValueExpr(size);
    }
    
    public String getIdInValueExpr(final int size) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" in");
        sb.append(" (");
        sb.append(this.idInValueSql);
        for (int i = 1; i < size; ++i) {
            sb.append(",").append(this.idInValueSql);
        }
        sb.append(") ");
        return sb.toString();
    }
    
    public String getBindIdInSql(final String baseTableAlias) {
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            if (baseTableAlias != null) {
                sb.append(baseTableAlias);
                sb.append(".");
            }
            sb.append(this.props[i].getDbColumn());
        }
        sb.append(")");
        return sb.toString();
    }
    
    public Object[] getIdValues(final Object bean) {
        final Object[] bindvalues = new Object[this.props.length];
        for (int i = 0; i < this.props.length; ++i) {
            bindvalues[i] = this.props[i].getValue(bean);
        }
        return bindvalues;
    }
    
    public Object[] getBindValues(final Object idValue) {
        final Object[] bindvalues = new Object[this.props.length];
        try {
            final Map<String, ?> uidMap = (Map<String, ?>)idValue;
            for (int i = 0; i < this.props.length; ++i) {
                final Object value = uidMap.get(this.props[i].getName());
                bindvalues[i] = value;
            }
            return bindvalues;
        }
        catch (ClassCastException e) {
            final String msg = "Expecting concatinated idValue to be a Map";
            throw new PersistenceException(msg, e);
        }
    }
    
    public Object readTerm(final String idTermValue) {
        final String[] split = idTermValue.split("|");
        if (split.length != this.props.length) {
            final String msg = "Failed to split [" + idTermValue + "] using | for id.";
            throw new PersistenceException(msg);
        }
        final Map<String, Object> uidMap = new LinkedHashMap<String, Object>();
        for (int i = 0; i < this.props.length; ++i) {
            final Object v = this.props[i].getScalarType().parse(split[i]);
            uidMap.put(this.props[i].getName(), v);
        }
        return uidMap;
    }
    
    public String writeTerm(final Object idValue) {
        final Map<String, ?> uidMap = (Map<String, ?>)idValue;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            final Object v = uidMap.get(this.props[i].getName());
            final String formatValue = this.props[i].getScalarType().format(v);
            if (i > 0) {
                sb.append("|");
            }
            sb.append(formatValue);
        }
        return sb.toString();
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        boolean notNull = true;
        for (int i = 0; i < this.props.length; ++i) {
            final Object value = this.props[i].readData(dataInput);
            map.put(this.props[i].getName(), value);
            if (value == null) {
                notNull = false;
            }
        }
        if (notNull) {
            return map;
        }
        return null;
    }
    
    public void writeData(final DataOutput dataOutput, final Object idValue) throws IOException {
        final Map<String, Object> map = (Map<String, Object>)idValue;
        for (int i = 0; i < this.props.length; ++i) {
            final Object embFieldValue = map.get(this.props[i].getName());
            this.props[i].writeData(dataOutput, embFieldValue);
        }
    }
    
    public void loadIgnore(final DbReadContext ctx) {
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].loadIgnore(ctx);
        }
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean) throws SQLException {
        final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        boolean notNull = false;
        for (int i = 0; i < this.props.length; ++i) {
            final Object value = this.props[i].readSet(ctx, bean, null);
            if (value != null) {
                map.put(this.props[i].getName(), value);
                notNull = true;
            }
        }
        if (notNull) {
            return map;
        }
        return null;
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        boolean notNull = false;
        for (int i = 0; i < this.props.length; ++i) {
            final Object value = this.props[i].read(ctx);
            if (value != null) {
                map.put(this.props[i].getName(), value);
                notNull = true;
            }
        }
        if (notNull) {
            return map;
        }
        return null;
    }
    
    public void bindId(final DefaultSqlUpdate sqlUpdate, final Object idValue) {
        try {
            final Map<String, ?> uidMap = (Map<String, ?>)idValue;
            for (int i = 0; i < this.props.length; ++i) {
                final Object value = uidMap.get(this.props[i].getName());
                sqlUpdate.addParameter(value);
            }
        }
        catch (ClassCastException e) {
            final String msg = "Expecting concatinated idValue to be a Map";
            throw new PersistenceException(msg, e);
        }
    }
    
    public void bindId(final DataBind bind, final Object idValue) throws SQLException {
        try {
            final Map<String, ?> uidMap = (Map<String, ?>)idValue;
            for (int i = 0; i < this.props.length; ++i) {
                final Object value = uidMap.get(this.props[i].getName());
                this.props[i].bind(bind, value);
            }
        }
        catch (ClassCastException e) {
            final String msg = "Expecting concatinated idValue to be a Map";
            throw new PersistenceException(msg, e);
        }
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].appendSelect(ctx, subQuery);
        }
    }
    
    public String getAssocIdInExpr(final String prefix) {
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            if (prefix != null) {
                sb.append(prefix);
                sb.append(".");
            }
            sb.append(this.props[i].getName());
        }
        sb.append(")");
        return sb.toString();
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" and ");
            }
            if (prefix != null) {
                sb.append(prefix);
                sb.append(".");
            }
            sb.append(this.props[i].getName());
            sb.append(operator);
        }
        return sb.toString();
    }
    
    public String getBindIdSql(final String baseTableAlias) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" and ");
            }
            if (baseTableAlias != null) {
                sb.append(baseTableAlias);
                sb.append(".");
            }
            sb.append(this.props[i].getDbColumn());
            sb.append(" = ? ");
        }
        return sb.toString();
    }
    
    public Object convertSetId(final Object idValue, final Object bean) {
        Map<?, ?> mapVal = null;
        if (idValue instanceof Map) {
            mapVal = (Map<?, ?>)idValue;
        }
        else {
            mapVal = MapFromString.parse(idValue.toString());
        }
        final LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
        for (int i = 0; i < this.props.length; ++i) {
            final BeanProperty prop = this.props[i];
            Object value = mapVal.get(prop.getName());
            value = this.props[i].getScalarType().toBeanType(value);
            newMap.put(prop.getName(), value);
            if (bean != null) {
                prop.setValueIntercept(bean, value);
            }
        }
        return newMap;
    }
}
