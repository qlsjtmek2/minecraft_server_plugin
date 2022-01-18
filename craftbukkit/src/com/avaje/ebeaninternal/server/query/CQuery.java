// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.bean.NodeUsageCollector;
import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import java.util.logging.Level;
import java.sql.Connection;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.api.LoadContext;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelpFactory;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import java.util.HashMap;
import com.avaje.ebean.bean.NodeUsageListener;
import java.lang.ref.WeakReference;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebean.bean.ObjectGraphNode;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import java.util.Map;
import com.avaje.ebean.QueryListener;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelp;
import com.avaje.ebean.bean.BeanCollection;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;

public class CQuery<T> implements DbReadContext, CancelableQuery
{
    private static final Logger logger;
    private static final int GLOBAL_ROW_LIMIT = 1000000;
    private int rowCount;
    private int loadedBeanCount;
    private boolean noMoreRows;
    private Object loadedBeanId;
    boolean loadedBeanChanged;
    private Object loadedBean;
    private Object prevLoadedBean;
    private Object loadedManyBean;
    private Object prevDetailCollection;
    private Object currentDetailCollection;
    private final BeanCollection<T> collection;
    private final BeanCollectionHelp<T> help;
    private final OrmQueryRequest<T> request;
    private final BeanDescriptor<T> desc;
    private final SpiQuery<T> query;
    private final OrmQueryDetail queryDetail;
    private final QueryListener<T> queryListener;
    private Map<String, String> currentPathMap;
    private String currentPrefix;
    private final boolean manyIncluded;
    private final CQueryPredicates predicates;
    private final SqlTree sqlTree;
    private final boolean rawSql;
    private final String sql;
    private final String logWhereSql;
    private final boolean rowNumberIncluded;
    private final SqlTreeNode rootNode;
    private final BeanPropertyAssocMany<?> manyProperty;
    private final ElPropertyValue manyPropertyEl;
    private final int backgroundFetchAfter;
    private final int maxRowsLimit;
    private boolean hasHitBackgroundFetchAfter;
    private final PersistenceContext persistenceContext;
    private DataReader dataReader;
    private PreparedStatement pstmt;
    private boolean cancelled;
    private String bindLog;
    private final CQueryPlan queryPlan;
    private long startNano;
    private final SpiQuery.Mode queryMode;
    private final boolean autoFetchProfiling;
    private final ObjectGraphNode autoFetchParentNode;
    private final AutoFetchManager autoFetchManager;
    private final WeakReference<NodeUsageListener> autoFetchManagerRef;
    private final HashMap<String, ReferenceOptions> referenceOptionsMap;
    private int executionTimeMicros;
    private final int parentState;
    private final SpiExpressionList<?> filterMany;
    private BeanCollectionAdd currentDetailAdd;
    
    public CQuery(final OrmQueryRequest<T> request, final CQueryPredicates predicates, final CQueryPlan queryPlan) {
        this.referenceOptionsMap = new HashMap<String, ReferenceOptions>();
        this.request = request;
        this.queryPlan = queryPlan;
        this.query = request.getQuery();
        this.queryDetail = this.query.getDetail();
        this.queryMode = this.query.getMode();
        this.parentState = request.getParentState();
        this.autoFetchManager = this.query.getAutoFetchManager();
        this.autoFetchProfiling = (this.autoFetchManager != null);
        this.autoFetchParentNode = (this.autoFetchProfiling ? this.query.getParentNode() : null);
        this.autoFetchManagerRef = (this.autoFetchProfiling ? new WeakReference<NodeUsageListener>(this.autoFetchManager) : null);
        this.query.setGeneratedSql(queryPlan.getSql());
        this.sqlTree = queryPlan.getSqlTree();
        this.rootNode = this.sqlTree.getRootNode();
        this.manyProperty = this.sqlTree.getManyProperty();
        this.manyPropertyEl = this.sqlTree.getManyPropertyEl();
        this.manyIncluded = this.sqlTree.isManyIncluded();
        if (this.manyIncluded) {
            final String manyPropertyName = this.sqlTree.getManyPropertyName();
            final OrmQueryProperties chunk = this.query.getDetail().getChunk(manyPropertyName, false);
            this.filterMany = chunk.getFilterMany();
        }
        else {
            this.filterMany = null;
        }
        this.sql = queryPlan.getSql();
        this.rawSql = queryPlan.isRawSql();
        this.rowNumberIncluded = queryPlan.isRowNumberIncluded();
        this.logWhereSql = queryPlan.getLogWhereSql();
        this.desc = request.getBeanDescriptor();
        this.predicates = predicates;
        this.queryListener = this.query.getListener();
        if (this.queryListener == null) {
            this.persistenceContext = request.getPersistenceContext();
        }
        else {
            this.persistenceContext = new DefaultPersistenceContext();
        }
        this.maxRowsLimit = ((this.query.getMaxRows() > 0) ? this.query.getMaxRows() : 1000000);
        this.backgroundFetchAfter = ((this.query.getBackgroundFetchAfter() > 0) ? this.query.getBackgroundFetchAfter() : Integer.MAX_VALUE);
        this.help = this.createHelp(request);
        this.collection = (BeanCollection<T>)((this.help != null) ? this.help.createEmpty(false) : null);
    }
    
    private BeanCollectionHelp<T> createHelp(final OrmQueryRequest<T> request) {
        if (request.isFindById()) {
            return null;
        }
        final Query.Type manyType = request.getQuery().getType();
        if (manyType == null) {
            return null;
        }
        return BeanCollectionHelpFactory.create(request);
    }
    
    public int getParentState() {
        return this.parentState;
    }
    
    public void propagateState(final Object e) {
        if (this.parentState != 0 && e instanceof EntityBean) {
            ((EntityBean)e)._ebean_getIntercept().setState(this.parentState);
        }
    }
    
    public DataReader getDataReader() {
        return this.dataReader;
    }
    
    public SpiQuery.Mode getQueryMode() {
        return this.queryMode;
    }
    
    public boolean isVanillaMode() {
        return this.request.isVanillaMode();
    }
    
    public CQueryPredicates getPredicates() {
        return this.predicates;
    }
    
    public LoadContext getGraphContext() {
        return this.request.getGraphContext();
    }
    
    public SpiOrmQueryRequest<?> getQueryRequest() {
        return this.request;
    }
    
    public void cancel() {
        synchronized (this) {
            this.cancelled = true;
            if (this.pstmt != null) {
                try {
                    this.pstmt.cancel();
                }
                catch (SQLException e) {
                    final String msg = "Error cancelling query";
                    throw new PersistenceException(msg, e);
                }
            }
        }
    }
    
    public boolean prepareBindExecuteQuery() throws SQLException {
        synchronized (this) {
            if (this.cancelled || this.query.isCancelled()) {
                this.cancelled = true;
                return false;
            }
            this.startNano = System.nanoTime();
            if (this.request.isLuceneQuery()) {
                this.dataReader = this.queryPlan.createDataReader(null);
            }
            else {
                final SpiTransaction t = this.request.getTransaction();
                final Connection conn = t.getInternalConnection();
                this.pstmt = conn.prepareStatement(this.sql);
                if (this.query.getTimeout() > 0) {
                    this.pstmt.setQueryTimeout(this.query.getTimeout());
                }
                if (this.query.getBufferFetchSizeHint() > 0) {
                    this.pstmt.setFetchSize(this.query.getBufferFetchSizeHint());
                }
                final DataBind dataBind = new DataBind(this.pstmt);
                this.queryPlan.bindEncryptedProperties(dataBind);
                this.bindLog = this.predicates.bind(dataBind);
                final ResultSet rset = this.pstmt.executeQuery();
                this.dataReader = this.queryPlan.createDataReader(rset);
            }
            return true;
        }
    }
    
    public void close() {
        try {
            if (this.dataReader != null) {
                this.dataReader.close();
                this.dataReader = null;
            }
        }
        catch (SQLException e) {
            CQuery.logger.log(Level.SEVERE, null, e);
        }
        try {
            if (this.pstmt != null) {
                this.pstmt.close();
                this.pstmt = null;
            }
        }
        catch (SQLException e) {
            CQuery.logger.log(Level.SEVERE, null, e);
        }
    }
    
    public ReferenceOptions getReferenceOptionsFor(final BeanPropertyAssocOne<?> beanProp) {
        String beanPropName = beanProp.getName();
        if (this.currentPrefix != null) {
            beanPropName = this.currentPrefix + "." + beanPropName;
        }
        ReferenceOptions opt = this.referenceOptionsMap.get(beanPropName);
        if (opt == null) {
            final OrmQueryProperties chunk = this.queryDetail.getChunk(beanPropName, false);
            if (chunk != null) {
                opt = chunk.getReferenceOptions();
            }
            if (opt == null) {
                opt = beanProp.getTargetDescriptor().getReferenceOptions();
            }
            this.referenceOptionsMap.put(beanPropName, opt);
        }
        return opt;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }
    
    public void setLoadedBean(final Object bean, final Object id) {
        if (id == null || !id.equals(this.loadedBeanId)) {
            if (this.manyIncluded) {
                if (this.rowCount > 1) {
                    this.loadedBeanChanged = true;
                }
                this.prevLoadedBean = this.loadedBean;
                this.loadedBeanId = id;
            }
            this.loadedBean = bean;
        }
    }
    
    public void setLoadedManyBean(final Object manyValue) {
        this.loadedManyBean = manyValue;
    }
    
    public T getLoadedBean() {
        if (this.manyIncluded) {
            if (this.prevDetailCollection instanceof BeanCollection) {
                ((BeanCollection)this.prevDetailCollection).setModifyListening(this.manyProperty.getModifyListenMode());
            }
            else if (this.currentDetailCollection instanceof BeanCollection) {
                ((BeanCollection)this.currentDetailCollection).setModifyListening(this.manyProperty.getModifyListenMode());
            }
        }
        if (this.prevLoadedBean != null) {
            return (T)this.prevLoadedBean;
        }
        return (T)this.loadedBean;
    }
    
    private boolean hasMoreRows() throws SQLException {
        synchronized (this) {
            return !this.cancelled && this.dataReader.next();
        }
    }
    
    private boolean readRow() throws SQLException {
        synchronized (this) {
            if (this.cancelled) {
                return false;
            }
            if (!this.dataReader.next()) {
                return false;
            }
            ++this.rowCount;
            this.dataReader.resetColumnPosition();
            if (this.rowNumberIncluded) {
                this.dataReader.incrementPos(1);
            }
            this.rootNode.load(this, null, this.parentState);
            return true;
        }
    }
    
    public int getQueryExecutionTimeMicros() {
        return this.executionTimeMicros;
    }
    
    public boolean readBean() throws SQLException {
        final boolean result = this.readBeanInternal(true);
        this.updateExecutionStatistics();
        return result;
    }
    
    private boolean readBeanInternal(final boolean inForeground) throws SQLException {
        if (this.loadedBeanCount >= this.maxRowsLimit) {
            this.collection.setHasMoreRows(this.hasMoreRows());
            return false;
        }
        if (inForeground && this.loadedBeanCount >= this.backgroundFetchAfter) {
            this.hasHitBackgroundFetchAfter = true;
            this.collection.setFinishedFetch(false);
            return false;
        }
        if (!this.manyIncluded) {
            return this.readRow();
        }
        if (this.noMoreRows) {
            return false;
        }
        if (this.rowCount == 0) {
            if (!this.readRow()) {
                return false;
            }
            this.createNewDetailCollection();
        }
        if (this.readIntoCurrentDetailCollection()) {
            this.createNewDetailCollection();
            return true;
        }
        this.prevDetailCollection = null;
        this.prevLoadedBean = null;
        return this.noMoreRows = true;
    }
    
    private boolean readIntoCurrentDetailCollection() throws SQLException {
        while (this.readRow()) {
            if (this.loadedBeanChanged) {
                this.loadedBeanChanged = false;
                return true;
            }
            this.addToCurrentDetailCollection();
        }
        return false;
    }
    
    private void createNewDetailCollection() {
        this.prevDetailCollection = this.currentDetailCollection;
        if (this.queryMode.equals(SpiQuery.Mode.LAZYLOAD_MANY)) {
            this.currentDetailCollection = this.manyPropertyEl.elGetValue(this.loadedBean);
        }
        else {
            this.currentDetailCollection = this.manyProperty.createEmpty(this.request.isVanillaMode());
            this.manyPropertyEl.elSetValue(this.loadedBean, this.currentDetailCollection, false, false);
        }
        if (this.filterMany != null && !this.request.isVanillaMode()) {
            ((BeanCollection)this.currentDetailCollection).setFilterMany(this.filterMany);
        }
        this.currentDetailAdd = this.manyProperty.getBeanCollectionAdd(this.currentDetailCollection, null);
        this.addToCurrentDetailCollection();
    }
    
    private void addToCurrentDetailCollection() {
        if (this.loadedManyBean != null) {
            this.currentDetailAdd.addBean(this.loadedManyBean);
        }
    }
    
    public BeanCollection<T> continueFetchingInBackground() throws SQLException {
        this.readTheRows(false);
        this.collection.setFinishedFetch(true);
        return this.collection;
    }
    
    public BeanCollection<T> readCollection() throws SQLException {
        this.readTheRows(true);
        this.updateExecutionStatistics();
        return this.collection;
    }
    
    protected void updateExecutionStatistics() {
        try {
            final long exeNano = System.nanoTime() - this.startNano;
            this.executionTimeMicros = (int)exeNano / 1000;
            if (this.autoFetchProfiling) {
                this.autoFetchManager.collectQueryInfo(this.autoFetchParentNode, this.loadedBeanCount, this.executionTimeMicros);
            }
            this.queryPlan.executionTime(this.loadedBeanCount, this.executionTimeMicros);
        }
        catch (Exception e) {
            CQuery.logger.log(Level.SEVERE, null, e);
        }
    }
    
    public QueryIterator<T> readIterate(final int bufferSize, final OrmQueryRequest<T> request) {
        if (bufferSize > 0) {
            return new CQueryIteratorWithBuffer<T>(this, request, bufferSize);
        }
        return new CQueryIteratorSimple<T>(this, request);
    }
    
    private void readTheRows(final boolean inForeground) throws SQLException {
        while (this.hasNextBean(inForeground)) {
            if (this.queryListener != null) {
                this.queryListener.process(this.getLoadedBean());
            }
            else {
                this.help.add(this.collection, this.getLoadedBean());
            }
        }
    }
    
    protected boolean hasNextBean(final boolean inForeground) throws SQLException {
        if (!this.readBeanInternal(inForeground)) {
            return false;
        }
        ++this.loadedBeanCount;
        return true;
    }
    
    public String getLoadedRowDetail() {
        if (!this.manyIncluded) {
            return String.valueOf(this.rowCount);
        }
        return this.loadedBeanCount + ":" + this.rowCount;
    }
    
    public void register(String path, final EntityBeanIntercept ebi) {
        path = this.getPath(path);
        this.request.getGraphContext().register(path, ebi);
    }
    
    public void register(String path, final BeanCollection<?> bc) {
        path = this.getPath(path);
        this.request.getGraphContext().register(path, bc);
    }
    
    public boolean useBackgroundToContinueFetch() {
        return this.hasHitBackgroundFetchAfter;
    }
    
    public String getName() {
        return this.query.getName();
    }
    
    public boolean isRawSql() {
        return this.rawSql;
    }
    
    public String getLogWhereSql() {
        return this.logWhereSql;
    }
    
    public BeanPropertyAssocMany<?> getManyProperty() {
        return this.manyProperty;
    }
    
    public String getSummary() {
        return this.sqlTree.getSummary();
    }
    
    public SqlTree getSqlTree() {
        return this.sqlTree;
    }
    
    public String getBindLog() {
        return this.bindLog;
    }
    
    public SpiTransaction getTransaction() {
        return this.request.getTransaction();
    }
    
    public String getBeanType() {
        return this.desc.getFullName();
    }
    
    public String getBeanName() {
        return this.desc.getName();
    }
    
    public String getGeneratedSql() {
        return this.sql;
    }
    
    public PersistenceException createPersistenceException(final SQLException e) {
        return createPersistenceException(e, this.getTransaction(), this.bindLog, this.sql);
    }
    
    public static PersistenceException createPersistenceException(final SQLException e, final SpiTransaction t, final String bindLog, final String sql) {
        if (t.isLogSummary()) {
            final String errMsg = StringHelper.replaceStringMulti(e.getMessage(), new String[] { "\r", "\n" }, "\\n ");
            final String msg = "ERROR executing query:   bindLog[" + bindLog + "] error[" + errMsg + "]";
            t.logInternal(msg);
        }
        t.getConnection();
        final String m = Message.msg("fetch.sqlerror", e.getMessage(), bindLog, sql);
        return new PersistenceException(m, e);
    }
    
    public boolean isAutoFetchProfiling() {
        return this.autoFetchProfiling && this.query.isUsageProfiling();
    }
    
    private String getPath(final String propertyName) {
        if (this.currentPrefix == null) {
            return propertyName;
        }
        if (propertyName == null) {
            return this.currentPrefix;
        }
        final String path = this.currentPathMap.get(propertyName);
        if (path != null) {
            return path;
        }
        return this.currentPrefix + "." + propertyName;
    }
    
    public void profileBean(final EntityBeanIntercept ebi, final String prefix) {
        final ObjectGraphNode node = this.request.getGraphContext().getObjectGraphNode(prefix);
        ebi.setNodeUsageCollector(new NodeUsageCollector(node, this.autoFetchManagerRef));
    }
    
    public void setCurrentPrefix(final String currentPrefix, final Map<String, String> currentPathMap) {
        this.currentPrefix = currentPrefix;
        this.currentPathMap = currentPathMap;
    }
    
    static {
        logger = Logger.getLogger(CQuery.class.getName());
    }
}
