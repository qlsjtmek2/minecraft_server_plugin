// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.Map;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.util.logging.Level;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import java.sql.ResultSet;
import java.sql.Connection;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.server.type.RsetDataReader;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import java.util.logging.Logger;

public class CQueryFetchIds
{
    private static final Logger logger;
    private final OrmQueryRequest<?> request;
    private final BeanDescriptor<?> desc;
    private final SpiQuery<?> query;
    private final BackgroundExecutor backgroundExecutor;
    private final CQueryPredicates predicates;
    private final String sql;
    private RsetDataReader dataReader;
    private PreparedStatement pstmt;
    private String bindLog;
    private long startNano;
    private int executionTimeMicros;
    private int rowCount;
    private final int maxRows;
    private final int bgFetchAfter;
    
    public CQueryFetchIds(final OrmQueryRequest<?> request, final CQueryPredicates predicates, final String sql, final BackgroundExecutor backgroundExecutor) {
        this.backgroundExecutor = backgroundExecutor;
        this.request = request;
        this.query = request.getQuery();
        this.sql = sql;
        this.maxRows = this.query.getMaxRows();
        this.bgFetchAfter = this.query.getBackgroundFetchAfter();
        this.query.setGeneratedSql(sql);
        this.desc = request.getBeanDescriptor();
        this.predicates = predicates;
    }
    
    public String getSummary() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FindIds exeMicros[").append(this.executionTimeMicros).append("] rows[").append(this.rowCount).append("] type[").append(this.desc.getName()).append("] predicates[").append(this.predicates.getLogWhereSql()).append("] bind[").append(this.bindLog).append("]");
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
    
    public BeanIdList findIds() throws SQLException {
        boolean useBackgroundToContinueFetch = false;
        this.startNano = System.nanoTime();
        try {
            List<Object> idList = this.query.getIdList();
            if (idList == null) {
                idList = Collections.synchronizedList(new ArrayList<Object>());
                this.query.setIdList(idList);
            }
            final BeanIdList result = new BeanIdList(idList);
            final SpiTransaction t = this.request.getTransaction();
            final Connection conn = t.getInternalConnection();
            this.pstmt = conn.prepareStatement(this.sql);
            if (this.query.getBufferFetchSizeHint() > 0) {
                this.pstmt.setFetchSize(this.query.getBufferFetchSizeHint());
            }
            if (this.query.getTimeout() > 0) {
                this.pstmt.setQueryTimeout(this.query.getTimeout());
            }
            this.bindLog = this.predicates.bind(new DataBind(this.pstmt));
            final ResultSet rset = this.pstmt.executeQuery();
            this.dataReader = new RsetDataReader(rset);
            boolean hitMaxRows = false;
            boolean hasMoreRows = false;
            this.rowCount = 0;
            final DbReadContext ctx = new DbContext();
            while (rset.next()) {
                final Object idValue = this.desc.getIdBinder().read(ctx);
                idList.add(idValue);
                this.dataReader.resetColumnPosition();
                ++this.rowCount;
                if (this.maxRows > 0 && this.rowCount == this.maxRows) {
                    hitMaxRows = true;
                    hasMoreRows = rset.next();
                    break;
                }
                if (this.bgFetchAfter > 0 && this.rowCount >= this.bgFetchAfter) {
                    useBackgroundToContinueFetch = true;
                    break;
                }
            }
            if (hitMaxRows) {
                result.setHasMore(hasMoreRows);
            }
            if (useBackgroundToContinueFetch) {
                this.request.setBackgroundFetching();
                final BackgroundIdFetch bgFetch = new BackgroundIdFetch(t, rset, this.pstmt, ctx, this.desc, result);
                final FutureTask<Integer> future = new FutureTask<Integer>(bgFetch);
                this.backgroundExecutor.execute(future);
                result.setBackgroundFetch(future);
            }
            final long exeNano = System.nanoTime() - this.startNano;
            this.executionTimeMicros = (int)exeNano / 1000;
            return result;
        }
        finally {
            if (!useBackgroundToContinueFetch) {
                this.close();
            }
        }
    }
    
    private void close() {
        try {
            if (this.dataReader != null) {
                this.dataReader.close();
                this.dataReader = null;
            }
        }
        catch (SQLException e) {
            CQueryFetchIds.logger.log(Level.SEVERE, null, e);
        }
        try {
            if (this.pstmt != null) {
                this.pstmt.close();
                this.pstmt = null;
            }
        }
        catch (SQLException e) {
            CQueryFetchIds.logger.log(Level.SEVERE, null, e);
        }
    }
    
    static {
        logger = Logger.getLogger(CQueryFetchIds.class.getName());
    }
    
    class DbContext implements DbReadContext
    {
        public int getParentState() {
            return 0;
        }
        
        public void propagateState(final Object e) {
            throw new RuntimeException("Not Called");
        }
        
        public SpiQuery.Mode getQueryMode() {
            return SpiQuery.Mode.NORMAL;
        }
        
        public DataReader getDataReader() {
            return CQueryFetchIds.this.dataReader;
        }
        
        public boolean isVanillaMode() {
            return false;
        }
        
        public boolean isSharedInstance() {
            return false;
        }
        
        public boolean isReadOnly() {
            return false;
        }
        
        public boolean isRawSql() {
            return false;
        }
        
        public void register(final String path, final EntityBeanIntercept ebi) {
        }
        
        public void register(final String path, final BeanCollection<?> bc) {
        }
        
        public ReferenceOptions getReferenceOptionsFor(final BeanPropertyAssocOne<?> beanProp) {
            return null;
        }
        
        public BeanPropertyAssocMany<?> getManyProperty() {
            return null;
        }
        
        public PersistenceContext getPersistenceContext() {
            return null;
        }
        
        public boolean isAutoFetchProfiling() {
            return false;
        }
        
        public void profileBean(final EntityBeanIntercept ebi, final String prefix) {
        }
        
        public void setCurrentPrefix(final String currentPrefix, final Map<String, String> pathMap) {
        }
        
        public void setLoadedBean(final Object loadedBean, final Object id) {
        }
        
        public void setLoadedManyBean(final Object loadedBean) {
        }
    }
}
