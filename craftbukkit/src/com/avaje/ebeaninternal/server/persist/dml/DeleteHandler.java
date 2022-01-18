// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import javax.persistence.OptimisticLockException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public class DeleteHandler extends DmlHandler
{
    private final DeleteMeta meta;
    
    public DeleteHandler(final PersistRequestBean<?> persist, final DeleteMeta meta) {
        super(persist, meta.isEmptyStringAsNull());
        this.meta = meta;
    }
    
    public void bind() throws SQLException {
        this.sql = this.meta.getSql(this.persistRequest);
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
        this.bindLogAppend("Binding Delete [");
        this.bindLogAppend(this.meta.getTableName());
        this.bindLogAppend("] where[");
        this.meta.bind(this.persistRequest, this);
        this.bindLogAppend("]");
        this.logBinding();
    }
    
    public void execute() throws SQLException, OptimisticLockException {
        final int rowCount = this.dataBind.executeUpdate();
        this.checkRowCount(rowCount);
    }
    
    public boolean isIncluded(final BeanProperty prop) {
        return prop.isDbUpdatable() && super.isIncluded(prop);
    }
    
    public boolean isIncludedWhere(final BeanProperty prop) {
        return prop.isDbUpdatable() && (this.loadedProps == null || this.loadedProps.contains(prop.getName()));
    }
}
