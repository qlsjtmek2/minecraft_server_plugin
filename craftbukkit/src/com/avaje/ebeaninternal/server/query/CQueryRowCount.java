// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.logging.Level;
import java.sql.SQLException;
import java.sql.Connection;
import com.avaje.ebeaninternal.api.SpiTransaction;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import java.util.logging.Logger;

public class CQueryRowCount
{
    private static final Logger logger;
    private final OrmQueryRequest<?> request;
    private final BeanDescriptor<?> desc;
    private final SpiQuery<?> query;
    private final CQueryPredicates predicates;
    private final String sql;
    private ResultSet rset;
    private PreparedStatement pstmt;
    private String bindLog;
    private long startNano;
    private int executionTimeMicros;
    private int rowCount;
    
    public CQueryRowCount(final OrmQueryRequest<?> request, final CQueryPredicates predicates, final String sql) {
        this.request = request;
        this.query = request.getQuery();
        this.sql = sql;
        this.query.setGeneratedSql(sql);
        this.desc = request.getBeanDescriptor();
        this.predicates = predicates;
    }
    
    public String getSummary() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FindRowCount exeMicros[").append(this.executionTimeMicros).append("] rows[").append(this.rowCount).append("] type[").append(this.desc.getFullName()).append("] predicates[").append(this.predicates.getLogWhereSql()).append("] bind[").append(this.bindLog).append("]");
        return sb.toString();
    }
    
    public String getBindLog() {
        return this.bindLog;
    }
    
    public String getGeneratedSql() {
        return this.sql;
    }
    
    public SpiOrmQueryRequest<?> getQueryRequest() {
        return this.request;
    }
    
    public int findRowCount() throws SQLException {
        this.startNano = System.nanoTime();
        try {
            final SpiTransaction t = this.request.getTransaction();
            final Connection conn = t.getInternalConnection();
            this.pstmt = conn.prepareStatement(this.sql);
            if (this.query.getTimeout() > 0) {
                this.pstmt.setQueryTimeout(this.query.getTimeout());
            }
            this.bindLog = this.predicates.bind(new DataBind(this.pstmt));
            this.rset = this.pstmt.executeQuery();
            if (!this.rset.next()) {
                throw new PersistenceException("Expecting 1 row but got none?");
            }
            this.rowCount = this.rset.getInt(1);
            final long exeNano = System.nanoTime() - this.startNano;
            this.executionTimeMicros = (int)exeNano / 1000;
            return this.rowCount;
        }
        finally {
            this.close();
        }
    }
    
    private void close() {
        try {
            if (this.rset != null) {
                this.rset.close();
                this.rset = null;
            }
        }
        catch (SQLException e) {
            CQueryRowCount.logger.log(Level.SEVERE, null, e);
        }
        try {
            if (this.pstmt != null) {
                this.pstmt.close();
                this.pstmt = null;
            }
        }
        catch (SQLException e) {
            CQueryRowCount.logger.log(Level.SEVERE, null, e);
        }
    }
    
    static {
        logger = Logger.getLogger(CQueryRowCount.class.getName());
    }
}
