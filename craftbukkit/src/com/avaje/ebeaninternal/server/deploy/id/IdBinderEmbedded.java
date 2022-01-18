// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.SplitName;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.naming.ldap.Rdn;
import javax.naming.ldap.LdapName;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

public final class IdBinderEmbedded implements IdBinder
{
    private final BeanPropertyAssocOne<?> embIdProperty;
    private final boolean idInExpandedForm;
    private BeanProperty[] props;
    private BeanDescriptor<?> idDesc;
    private String idInValueSql;
    
    public IdBinderEmbedded(final boolean idInExpandedForm, final BeanPropertyAssocOne<?> embIdProperty) {
        this.idInExpandedForm = idInExpandedForm;
        this.embIdProperty = embIdProperty;
    }
    
    public void initialise() {
        this.idDesc = this.embIdProperty.getTargetDescriptor();
        this.props = this.embIdProperty.getProperties();
        this.idInValueSql = (this.idInExpandedForm ? this.idInExpanded() : this.idInCompressed());
    }
    
    private String idInExpanded() {
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" and ");
            }
            sb.append(this.embIdProperty.getName());
            sb.append(".");
            sb.append(this.props[i].getName());
            sb.append("=?");
        }
        sb.append(")");
        return sb.toString();
    }
    
    private String idInCompressed() {
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();
    }
    
    public String getOrderBy(final String pathPrefix, final boolean ascending) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            if (pathPrefix != null) {
                sb.append(pathPrefix).append(".");
            }
            sb.append(this.embIdProperty.getName()).append(".");
            sb.append(this.props[i].getName());
            if (!ascending) {
                sb.append(" desc");
            }
        }
        return sb.toString();
    }
    
    public void createLdapNameById(final LdapName name, final Object id) throws InvalidNameException {
        for (int i = 0; i < this.props.length; ++i) {
            final Object v = this.props[i].getValue(id);
            final Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
            name.add(rdn);
        }
    }
    
    public void createLdapNameByBean(final LdapName name, final Object bean) throws InvalidNameException {
        final Object id = this.embIdProperty.getValue(bean);
        this.createLdapNameById(name, id);
    }
    
    public BeanDescriptor<?> getIdBeanDescriptor() {
        return this.idDesc;
    }
    
    public int getPropertyCount() {
        return this.props.length;
    }
    
    public String getIdProperty() {
        return this.embIdProperty.getName();
    }
    
    public void buildSelectExpressionChain(String prefix, final List<String> selectChain) {
        prefix = SplitName.add(prefix, this.embIdProperty.getName());
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].buildSelectExpressionChain(prefix, selectChain);
        }
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
            sb.append(this.embIdProperty.getName());
            sb.append(".");
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
        if (!this.idInExpandedForm) {
            return this.getIdInValueExpr(size);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int j = 0; j < size; ++j) {
            if (j > 0) {
                sb.append(" or ");
            }
            sb.append("(");
            for (int i = 0; i < this.props.length; ++i) {
                if (i > 0) {
                    sb.append(" and ");
                }
                sb.append(this.props[i].getDbColumn());
                sb.append("=?");
            }
            sb.append(")");
        }
        sb.append(") ");
        return sb.toString();
    }
    
    public String getIdInValueExpr(final int size) {
        final StringBuilder sb = new StringBuilder();
        if (!this.idInExpandedForm) {
            sb.append(" in");
        }
        sb.append(" (");
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                if (this.idInExpandedForm) {
                    sb.append(" or ");
                }
                else {
                    sb.append(",");
                }
            }
            sb.append(this.idInValueSql);
        }
        sb.append(") ");
        return sb.toString();
    }
    
    public String getIdInValueExpr() {
        return this.idInValueSql;
    }
    
    public Object readTerm(final String idTermValue) {
        final String[] split = idTermValue.split("|");
        if (split.length != this.props.length) {
            final String msg = "Failed to split [" + idTermValue + "] using | for id.";
            throw new PersistenceException(msg);
        }
        final Object embId = this.idDesc.createVanillaBean();
        for (int i = 0; i < this.props.length; ++i) {
            final Object v = this.props[i].getScalarType().parse(split[i]);
            this.props[i].setValue(embId, v);
        }
        return embId;
    }
    
    public String writeTerm(final Object idValue) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            final Object v = this.props[i].getValue(idValue);
            final String formatValue = this.props[i].getScalarType().format(v);
            if (i > 0) {
                sb.append("|");
            }
            sb.append(formatValue);
        }
        return sb.toString();
    }
    
    public Object[] getIdValues(Object bean) {
        bean = this.embIdProperty.getValue(bean);
        final Object[] bindvalues = new Object[this.props.length];
        for (int i = 0; i < this.props.length; ++i) {
            bindvalues[i] = this.props[i].getValue(bean);
        }
        return bindvalues;
    }
    
    public Object[] getBindValues(final Object value) {
        final Object[] bindvalues = new Object[this.props.length];
        for (int i = 0; i < this.props.length; ++i) {
            bindvalues[i] = this.props[i].getValue(value);
        }
        return bindvalues;
    }
    
    public void bindId(final DefaultSqlUpdate sqlUpdate, final Object value) {
        for (int i = 0; i < this.props.length; ++i) {
            final Object embFieldValue = this.props[i].getValue(value);
            sqlUpdate.addParameter(embFieldValue);
        }
    }
    
    public void bindId(final DataBind dataBind, final Object value) throws SQLException {
        for (int i = 0; i < this.props.length; ++i) {
            final Object embFieldValue = this.props[i].getValue(value);
            this.props[i].bind(dataBind, embFieldValue);
        }
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        final Object embId = this.idDesc.createVanillaBean();
        boolean notNull = true;
        for (int i = 0; i < this.props.length; ++i) {
            final Object value = this.props[i].readData(dataInput);
            this.props[i].setValue(embId, value);
            if (value == null) {
                notNull = false;
            }
        }
        if (notNull) {
            return embId;
        }
        return null;
    }
    
    public void writeData(final DataOutput dataOutput, final Object idValue) throws IOException {
        for (int i = 0; i < this.props.length; ++i) {
            final Object embFieldValue = this.props[i].getValue(idValue);
            this.props[i].writeData(dataOutput, embFieldValue);
        }
    }
    
    public void loadIgnore(final DbReadContext ctx) {
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].loadIgnore(ctx);
        }
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        final Object embId = this.idDesc.createVanillaBean();
        boolean notNull = true;
        for (int i = 0; i < this.props.length; ++i) {
            final Object value = this.props[i].readSet(ctx, embId, null);
            if (value == null) {
                notNull = false;
            }
        }
        if (notNull) {
            return embId;
        }
        return null;
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean) throws SQLException {
        final Object embId = this.read(ctx);
        if (embId != null) {
            this.embIdProperty.setValue(bean, embId);
            return embId;
        }
        return null;
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
            sb.append(this.embIdProperty.getName());
            sb.append(".");
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
    
    public String getBindIdInSql(final String baseTableAlias) {
        if (this.idInExpandedForm) {
            return "";
        }
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
    
    public Object convertSetId(final Object idValue, final Object bean) {
        if (bean != null) {
            this.embIdProperty.setValueIntercept(bean, idValue);
        }
        return idValue;
    }
}
