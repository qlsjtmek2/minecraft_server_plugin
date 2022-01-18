// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import javax.persistence.OptimisticLockException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiUpdatePlan;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.Set;

public class UpdateHandler extends DmlHandler
{
    private final UpdateMeta meta;
    private Set<String> updatedProperties;
    private boolean emptySetClause;
    
    public UpdateHandler(final PersistRequestBean<?> persist, final UpdateMeta meta) {
        super(persist, meta.isEmptyStringAsNull());
        this.meta = meta;
    }
    
    public void bind() throws SQLException {
        final SpiUpdatePlan updatePlan = this.meta.getUpdatePlan(this.persistRequest);
        if (updatePlan.isEmptySetClause()) {
            this.emptySetClause = true;
            return;
        }
        this.updatedProperties = updatePlan.getProperties();
        this.sql = updatePlan.getSql();
        final SpiTransaction t = this.persistRequest.getTransaction();
        final boolean isBatch = t.isBatchThisRequest();
        PreparedStatement pstmt;
        if (isBatch) {
            pstmt = this.getPstmt(t, this.sql, this.persistRequest, false);
        }
        else {
            this.logSql(this.sql);
            pstmt = this.getPstmt(t, this.sql, false);
        }
        this.dataBind = new DataBind(pstmt);
        this.bindLogAppend("Binding Update [");
        this.bindLogAppend(this.meta.getTableName());
        this.bindLogAppend("] ");
        this.meta.bind(this.persistRequest, this, updatePlan);
        this.setUpdateGenValues();
        this.bindLogAppend("]");
        this.logBinding();
    }
    
    public void addBatch() throws SQLException {
        if (!this.emptySetClause) {
            super.addBatch();
        }
    }
    
    public void execute() throws SQLException, OptimisticLockException {
        if (!this.emptySetClause) {
            final int rowCount = this.dataBind.executeUpdate();
            this.checkRowCount(rowCount);
            this.setAdditionalProperties();
        }
    }
    
    public boolean isIncluded(final BeanProperty prop) {
        return prop.isDbUpdatable() && (this.updatedProperties == null || this.updatedProperties.contains(prop.getName()));
    }
}
