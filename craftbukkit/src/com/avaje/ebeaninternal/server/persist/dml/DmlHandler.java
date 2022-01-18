// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.server.persist.BatchedPstmtHolder;
import com.avaje.ebeaninternal.server.persist.BatchedPstmt;
import com.avaje.ebeaninternal.server.persist.BatchPostExecute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import javax.persistence.OptimisticLockException;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.Set;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;

public abstract class DmlHandler implements PersistHandler, BindableRequest
{
    protected static final Logger logger;
    protected final PersistRequestBean<?> persistRequest;
    protected final StringBuilder bindLog;
    protected final Set<String> loadedProps;
    protected final SpiTransaction transaction;
    protected final boolean emptyStringToNull;
    protected final boolean logLevelSql;
    protected DataBind dataBind;
    protected String sql;
    protected ArrayList<UpdateGenValue> updateGenValues;
    private Set<String> additionalProps;
    private boolean checkDelta;
    private BeanDelta deltaBean;
    
    protected DmlHandler(final PersistRequestBean<?> persistRequest, final boolean emptyStringToNull) {
        this.persistRequest = persistRequest;
        this.emptyStringToNull = emptyStringToNull;
        this.loadedProps = persistRequest.getLoadedProperties();
        this.transaction = persistRequest.getTransaction();
        this.logLevelSql = this.transaction.isLogSql();
        if (this.logLevelSql) {
            this.bindLog = new StringBuilder();
        }
        else {
            this.bindLog = null;
        }
    }
    
    protected void setCheckDelta(final boolean checkDelta) {
        this.checkDelta = checkDelta;
    }
    
    public PersistRequestBean<?> getPersistRequest() {
        return this.persistRequest;
    }
    
    public abstract void bind() throws SQLException;
    
    public abstract void execute() throws SQLException;
    
    protected void checkRowCount(final int rowCount) throws SQLException, OptimisticLockException {
        try {
            this.persistRequest.checkRowCount(rowCount);
            this.persistRequest.postExecute();
        }
        catch (OptimisticLockException e) {
            final String m = e.getMessage() + " sql[" + this.sql + "] bind[" + (Object)this.bindLog + "]";
            this.persistRequest.getTransaction().log("OptimisticLockException:" + m);
            throw new OptimisticLockException(m, null, e.getEntity());
        }
    }
    
    public void addBatch() throws SQLException {
        final PstmtBatch pstmtBatch = this.persistRequest.getPstmtBatch();
        if (pstmtBatch != null) {
            pstmtBatch.addBatch(this.dataBind.getPstmt());
        }
        else {
            this.dataBind.getPstmt().addBatch();
        }
    }
    
    public void close() {
        try {
            if (this.dataBind != null) {
                this.dataBind.close();
            }
        }
        catch (SQLException ex) {
            DmlHandler.logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public String getBindLog() {
        return (this.bindLog == null) ? "" : this.bindLog.toString();
    }
    
    public void setIdValue(final Object idValue) {
        this.persistRequest.setBoundId(idValue);
    }
    
    protected void logBinding() {
        if (this.logLevelSql) {
            this.transaction.logInternal(this.bindLog.toString());
        }
    }
    
    protected void logSql(final String sql) {
        if (this.logLevelSql) {
            this.transaction.logInternal(sql);
        }
    }
    
    public boolean isIncluded(final BeanProperty prop) {
        return this.loadedProps == null || this.loadedProps.contains(prop.getName());
    }
    
    public boolean isIncludedWhere(final BeanProperty prop) {
        if (prop.isDbEncrypted()) {
            return this.isIncluded(prop);
        }
        return prop.isDbUpdatable() && (this.loadedProps == null || this.loadedProps.contains(prop.getName()));
    }
    
    public Object bind(final String propName, final Object value, final int sqlType) throws SQLException {
        if (this.logLevelSql) {
            this.bindLog.append(propName).append("=");
            this.bindLog.append(value).append(", ");
        }
        this.dataBind.setObject(value, sqlType);
        return value;
    }
    
    public Object bindNoLog(final Object value, final int sqlType, final String logPlaceHolder) throws SQLException {
        if (this.logLevelSql) {
            this.bindLog.append(logPlaceHolder).append(" ");
        }
        this.dataBind.setObject(value, sqlType);
        return value;
    }
    
    public Object bind(final Object value, final BeanProperty prop, final String propName, final boolean bindNull) throws SQLException {
        return this.bindInternal(this.logLevelSql, value, prop, propName, bindNull);
    }
    
    public Object bindNoLog(final Object value, final BeanProperty prop, final String propName, final boolean bindNull) throws SQLException {
        return this.bindInternal(false, value, prop, propName, bindNull);
    }
    
    private Object bindInternal(final boolean log, Object value, final BeanProperty prop, final String propName, final boolean bindNull) throws SQLException {
        if (!bindNull && this.emptyStringToNull && value instanceof String && ((String)value).length() == 0) {
            value = null;
        }
        if (!bindNull && value == null) {
            if (log) {
                this.bindLog.append(propName).append("=");
                this.bindLog.append("null, ");
            }
        }
        else {
            if (log) {
                this.bindLog.append(propName).append("=");
                if (prop.isLob()) {
                    this.bindLog.append("[LOB]");
                }
                else {
                    String sv = String.valueOf(value);
                    if (sv.length() > 50) {
                        sv = sv.substring(0, 47) + "...";
                    }
                    this.bindLog.append(sv);
                }
                this.bindLog.append(", ");
            }
            prop.bind(this.dataBind, value);
            if (this.checkDelta && !prop.isId() && prop.isDeltaRequired()) {
                if (this.deltaBean == null) {
                    this.deltaBean = this.persistRequest.createDeltaBean();
                    this.transaction.getEvent().addBeanDelta(this.deltaBean);
                }
                this.deltaBean.add(prop, value);
            }
        }
        return value;
    }
    
    protected void bindLogAppend(final String comment) {
        if (this.logLevelSql) {
            this.bindLog.append(comment);
        }
    }
    
    public final void registerAdditionalProperty(final String propertyName) {
        if (this.loadedProps != null && !this.loadedProps.contains(propertyName)) {
            if (this.additionalProps == null) {
                this.additionalProps = new HashSet<String>();
            }
            this.additionalProps.add(propertyName);
        }
    }
    
    protected void setAdditionalProperties() {
        if (this.additionalProps != null) {
            this.additionalProps.addAll(this.loadedProps);
            this.persistRequest.setLoadedProps(this.additionalProps);
        }
    }
    
    public void registerUpdateGenValue(final BeanProperty prop, final Object bean, final Object value) {
        if (this.updateGenValues == null) {
            this.updateGenValues = new ArrayList<UpdateGenValue>();
        }
        this.updateGenValues.add(new UpdateGenValue(prop, bean, value));
        this.registerAdditionalProperty(prop.getName());
    }
    
    public void setUpdateGenValues() {
        if (this.updateGenValues != null) {
            for (int i = 0; i < this.updateGenValues.size(); ++i) {
                final UpdateGenValue updGenVal = this.updateGenValues.get(i);
                updGenVal.setValue();
            }
        }
    }
    
    protected PreparedStatement getPstmt(final SpiTransaction t, final String sql, final boolean genKeys) throws SQLException {
        final Connection conn = t.getInternalConnection();
        if (genKeys) {
            final int[] columns = { 1 };
            return conn.prepareStatement(sql, columns);
        }
        return conn.prepareStatement(sql);
    }
    
    protected PreparedStatement getPstmt(final SpiTransaction t, final String sql, final PersistRequestBean<?> request, final boolean genKeys) throws SQLException {
        final BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
        PreparedStatement stmt = batch.getStmt(sql, request);
        if (stmt != null) {
            return stmt;
        }
        if (this.logLevelSql) {
            t.logInternal(sql);
        }
        stmt = this.getPstmt(t, sql, genKeys);
        final PstmtBatch pstmtBatch = request.getPstmtBatch();
        if (pstmtBatch != null) {
            pstmtBatch.setBatchSize(stmt, t.getBatchControl().getBatchSize());
        }
        final BatchedPstmt bs = new BatchedPstmt(stmt, genKeys, sql, request.getPstmtBatch(), true);
        batch.addStmt(bs, request);
        return stmt;
    }
    
    static {
        logger = Logger.getLogger(DmlHandler.class.getName());
    }
    
    private static final class UpdateGenValue
    {
        private final BeanProperty property;
        private final Object bean;
        private final Object value;
        
        private UpdateGenValue(final BeanProperty property, final Object bean, final Object value) {
            this.property = property;
            this.bean = bean;
            this.value = value;
        }
        
        private void setValue() {
            this.property.setValueIntercept(this.bean, this.value);
        }
    }
}
