// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.logging.Level;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.persist.BeanPersister;

public final class DmlBeanPersister implements BeanPersister
{
    private static final Logger logger;
    private final UpdateMeta updateMeta;
    private final InsertMeta insertMeta;
    private final DeleteMeta deleteMeta;
    
    public DmlBeanPersister(final UpdateMeta updateMeta, final InsertMeta insertMeta, final DeleteMeta deleteMeta) {
        this.updateMeta = updateMeta;
        this.insertMeta = insertMeta;
        this.deleteMeta = deleteMeta;
    }
    
    public void delete(final PersistRequestBean<?> request) {
        final DeleteHandler delete = new DeleteHandler(request, this.deleteMeta);
        this.execute(request, delete);
    }
    
    public void insert(final PersistRequestBean<?> request) {
        final InsertHandler insert = new InsertHandler(request, this.insertMeta);
        this.execute(request, insert);
    }
    
    public void update(final PersistRequestBean<?> request) {
        final UpdateHandler update = new UpdateHandler(request, this.updateMeta);
        this.execute(request, update);
    }
    
    private void execute(final PersistRequest request, final PersistHandler handler) {
        final SpiTransaction trans = request.getTransaction();
        final boolean batchThisRequest = trans.isBatchThisRequest();
        try {
            handler.bind();
            if (batchThisRequest) {
                handler.addBatch();
            }
            else {
                handler.execute();
            }
        }
        catch (SQLException e) {
            final String errMsg = StringHelper.replaceStringMulti(e.getMessage(), new String[] { "\r", "\n" }, "\\n ");
            final String msg = "ERROR executing DML bindLog[" + handler.getBindLog() + "] error[" + errMsg + "]";
            if (request.getTransaction().isLogSummary()) {
                request.getTransaction().logInternal(msg);
            }
            throw new PersistenceException(msg, e);
        }
        finally {
            if (!batchThisRequest && handler != null) {
                try {
                    handler.close();
                }
                catch (SQLException e2) {
                    DmlBeanPersister.logger.log(Level.SEVERE, null, e2);
                }
            }
        }
    }
    
    static {
        logger = Logger.getLogger(DmlBeanPersister.class.getName());
    }
}
