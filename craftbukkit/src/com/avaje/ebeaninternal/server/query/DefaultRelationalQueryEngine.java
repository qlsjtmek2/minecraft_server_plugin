// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlQueryListener;
import com.avaje.ebeaninternal.api.BindParams;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiSqlQuery;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.util.BindParamsParser;
import com.avaje.ebeaninternal.server.core.RelationalQueryRequest;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.jmx.MAdminLogging;
import com.avaje.ebeaninternal.server.persist.Binder;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.core.RelationalQueryEngine;

public class DefaultRelationalQueryEngine implements RelationalQueryEngine
{
    private static final Logger logger;
    private final int defaultMaxRows;
    private final Binder binder;
    private final String dbTrueValue;
    
    public DefaultRelationalQueryEngine(final MAdminLogging logControl, final Binder binder, final String dbTrueValue) {
        this.binder = binder;
        this.defaultMaxRows = GlobalProperties.getInt("nativesql.defaultmaxrows", 100000);
        this.dbTrueValue = ((dbTrueValue == null) ? "true" : dbTrueValue);
    }
    
    public Object findMany(final RelationalQueryRequest request) {
        final SpiSqlQuery query = request.getQuery();
        final long startTime = System.currentTimeMillis();
        final SpiTransaction t = request.getTransaction();
        final Connection conn = t.getInternalConnection();
        ResultSet rset = null;
        PreparedStatement pstmt = null;
        final boolean useBackgroundToContinueFetch = false;
        String sql = query.getQuery();
        final BindParams bindParams = query.getBindParams();
        if (!bindParams.isEmpty()) {
            sql = BindParamsParser.parse(bindParams, sql);
        }
        try {
            String bindLog = "";
            String[] propNames = null;
            synchronized (query) {
                if (query.isCancelled()) {
                    DefaultRelationalQueryEngine.logger.finest("Query already cancelled");
                    return null;
                }
                pstmt = conn.prepareStatement(sql);
                if (query.getTimeout() > 0) {
                    pstmt.setQueryTimeout(query.getTimeout());
                }
                if (query.getBufferFetchSizeHint() > 0) {
                    pstmt.setFetchSize(query.getBufferFetchSizeHint());
                }
                if (!bindParams.isEmpty()) {
                    bindLog = this.binder.bind(bindParams, new DataBind(pstmt));
                }
                if (request.isLogSql()) {
                    String sOut = sql.replace('\n', ' ');
                    sOut = sOut.replace('\r', ' ');
                    t.logInternal(sOut);
                }
                rset = pstmt.executeQuery();
                propNames = this.getPropertyNames(rset);
            }
            final float initCap = propNames.length / 0.7f;
            final int estimateCapacity = (int)initCap + 1;
            int maxRows = this.defaultMaxRows;
            if (query.getMaxRows() >= 1) {
                maxRows = query.getMaxRows();
            }
            boolean hasHitMaxRows = false;
            int loadRowCount = 0;
            final SqlQueryListener listener = query.getListener();
            final BeanCollectionWrapper wrapper = new BeanCollectionWrapper(request);
            final boolean isMap = wrapper.isMap();
            final String mapKey = query.getMapKey();
            SqlRow bean = null;
            while (rset.next()) {
                synchronized (query) {
                    if (!query.isCancelled()) {
                        bean = this.readRow(request, rset, propNames, estimateCapacity);
                    }
                }
                if (bean != null) {
                    if (listener != null) {
                        listener.process(bean);
                    }
                    else if (isMap) {
                        final Object keyValue = bean.get(mapKey);
                        wrapper.addToMap(bean, keyValue);
                    }
                    else {
                        wrapper.addToCollection(bean);
                    }
                    if (++loadRowCount == maxRows) {
                        hasHitMaxRows = true;
                        break;
                    }
                    continue;
                }
            }
            final BeanCollection<?> beanColl = wrapper.getBeanCollection();
            if (hasHitMaxRows && rset.next()) {
                beanColl.setHasMoreRows(true);
            }
            if (!useBackgroundToContinueFetch) {
                beanColl.setFinishedFetch(true);
            }
            if (request.isLogSummary()) {
                final long exeTime = System.currentTimeMillis() - startTime;
                final String msg = "SqlQuery  rows[" + loadRowCount + "] time[" + exeTime + "] bind[" + bindLog + "] finished[" + beanColl.isFinishedFetch() + "]";
                t.logInternal(msg);
            }
            if (query.isCancelled()) {
                DefaultRelationalQueryEngine.logger.fine("Query was cancelled during execution rows:" + loadRowCount);
            }
            return beanColl;
        }
        catch (Exception e) {
            final String m = Message.msg("fetch.error", e.getMessage(), sql);
            throw new PersistenceException(m, e);
        }
        finally {
            if (!useBackgroundToContinueFetch) {
                try {
                    if (rset != null) {
                        rset.close();
                    }
                }
                catch (SQLException e2) {
                    DefaultRelationalQueryEngine.logger.log(Level.SEVERE, null, e2);
                }
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                }
                catch (SQLException e2) {
                    DefaultRelationalQueryEngine.logger.log(Level.SEVERE, null, e2);
                }
            }
        }
    }
    
    protected String[] getPropertyNames(final ResultSet rset) throws SQLException {
        final ArrayList<String> propNames = new ArrayList<String>();
        final ResultSetMetaData rsmd = rset.getMetaData();
        for (int columnsPlusOne = rsmd.getColumnCount() + 1, i = 1; i < columnsPlusOne; ++i) {
            final String columnName = rsmd.getColumnLabel(i);
            propNames.add(columnName);
        }
        return propNames.toArray(new String[propNames.size()]);
    }
    
    protected SqlRow readRow(final RelationalQueryRequest request, final ResultSet rset, final String[] propNames, final int initialCapacity) throws SQLException {
        final SqlRow bean = new DefaultSqlRow(initialCapacity, 0.75f, this.dbTrueValue);
        int index = 0;
        for (int i = 0; i < propNames.length; ++i) {
            ++index;
            final Object value = rset.getObject(index);
            bean.set(propNames[i], value);
        }
        return bean;
    }
    
    static {
        logger = Logger.getLogger(DefaultRelationalQueryEngine.class.getName());
    }
}
