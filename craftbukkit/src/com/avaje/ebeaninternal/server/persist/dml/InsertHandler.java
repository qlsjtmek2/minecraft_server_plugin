// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.ResultSet;
import java.util.logging.Level;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.Message;
import javax.persistence.OptimisticLockException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.persist.DmlUtil;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public class InsertHandler extends DmlHandler
{
    private final InsertMeta meta;
    private final boolean concatinatedKey;
    private boolean useGeneratedKeys;
    private String selectLastInsertedId;
    
    public InsertHandler(final PersistRequestBean<?> persist, final InsertMeta meta) {
        super(persist, meta.isEmptyStringToNull());
        this.meta = meta;
        this.concatinatedKey = meta.isConcatinatedKey();
    }
    
    public boolean isIncluded(final BeanProperty prop) {
        return prop.isDbInsertable() && super.isIncluded(prop);
    }
    
    public void bind() throws SQLException {
        final BeanDescriptor<?> desc = this.persistRequest.getBeanDescriptor();
        final Object bean = this.persistRequest.getBean();
        final Object idValue = desc.getId(bean);
        boolean withId = !DmlUtil.isNullOrZero(idValue);
        if (!withId) {
            if (this.concatinatedKey) {
                withId = this.meta.deriveConcatenatedId(this.persistRequest);
            }
            else if (this.meta.supportsGetGeneratedKeys()) {
                this.useGeneratedKeys = true;
            }
            else {
                this.selectLastInsertedId = this.meta.getSelectLastInsertedId();
            }
        }
        final SpiTransaction t = this.persistRequest.getTransaction();
        final boolean isBatch = t.isBatchThisRequest();
        this.sql = this.meta.getSql(withId);
        PreparedStatement pstmt;
        if (isBatch) {
            pstmt = this.getPstmt(t, this.sql, this.persistRequest, this.useGeneratedKeys);
        }
        else {
            this.logSql(this.sql);
            pstmt = this.getPstmt(t, this.sql, this.useGeneratedKeys);
        }
        this.dataBind = new DataBind(pstmt);
        this.bindLogAppend("Binding Insert [");
        this.bindLogAppend(desc.getBaseTable());
        this.bindLogAppend("]  set[");
        this.meta.bind(this, bean, withId);
        this.bindLogAppend("]");
        this.logBinding();
    }
    
    protected PreparedStatement getPstmt(final SpiTransaction t, final String sql, final boolean useGeneratedKeys) throws SQLException {
        final Connection conn = t.getInternalConnection();
        if (useGeneratedKeys) {
            return conn.prepareStatement(sql, 1);
        }
        return conn.prepareStatement(sql);
    }
    
    public void execute() throws SQLException, OptimisticLockException {
        final int rc = this.dataBind.executeUpdate();
        if (this.useGeneratedKeys) {
            this.getGeneratedKeys();
        }
        else if (this.selectLastInsertedId != null) {
            this.fetchGeneratedKeyUsingSelect();
        }
        this.checkRowCount(rc);
        this.setAdditionalProperties();
    }
    
    private void getGeneratedKeys() throws SQLException {
        final ResultSet rset = this.dataBind.getPstmt().getGeneratedKeys();
        try {
            if (!rset.next()) {
                throw new PersistenceException(Message.msg("persist.autoinc.norows"));
            }
            final Object idValue = rset.getObject(1);
            if (idValue != null) {
                this.persistRequest.setGeneratedKey(idValue);
            }
        }
        finally {
            try {
                rset.close();
            }
            catch (SQLException ex) {
                final String msg = "Error closing rset for returning generatedKeys?";
                InsertHandler.logger.log(Level.WARNING, msg, ex);
            }
        }
    }
    
    private void fetchGeneratedKeyUsingSelect() throws SQLException {
        final Connection conn = this.transaction.getConnection();
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            stmt = conn.prepareStatement(this.selectLastInsertedId);
            rset = stmt.executeQuery();
            if (!rset.next()) {
                throw new PersistenceException(Message.msg("persist.autoinc.norows"));
            }
            final Object idValue = rset.getObject(1);
            if (idValue != null) {
                this.persistRequest.setGeneratedKey(idValue);
            }
        }
        finally {
            try {
                if (rset != null) {
                    rset.close();
                }
            }
            catch (SQLException ex) {
                final String msg = "Error closing rset for fetchGeneratedKeyUsingSelect?";
                InsertHandler.logger.log(Level.WARNING, msg, ex);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException ex) {
                final String msg = "Error closing stmt for fetchGeneratedKeyUsingSelect?";
                InsertHandler.logger.log(Level.WARNING, msg, ex);
            }
        }
    }
}
