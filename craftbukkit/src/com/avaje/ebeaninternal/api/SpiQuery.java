// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.query.CancelableQuery;
import com.avaje.ebean.QueryListener;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import java.util.ArrayList;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.CallStack;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import com.avaje.ebean.bean.BeanCollectionTouched;
import com.avaje.ebean.Query;

public interface SpiQuery<T> extends Query<T>
{
    boolean selectAllForLazyLoadProperty();
    
    void setMode(final Mode p0);
    
    Mode getMode();
    
    void deriveSharedInstance();
    
    void setSharedInstance();
    
    boolean isSharedInstance();
    
    void setParentState(final int p0);
    
    BeanCollectionTouched getBeanCollectionTouched();
    
    void setBeanCollectionTouched(final BeanCollectionTouched p0);
    
    void setIdList(final List<Object> p0);
    
    List<Object> getIdList();
    
    SpiQuery<T> copy();
    
    void setType(final Type p0);
    
    String getLoadDescription();
    
    String getLoadMode();
    
    void setLoadDescription(final String p0, final String p1);
    
    void setBeanDescriptor(final BeanDescriptor<?> p0);
    
    boolean initManyWhereJoins();
    
    ManyWhereJoins getManyWhereJoins();
    
    void setSelectId();
    
    void setFilterMany(final String p0, final ExpressionList<?> p1);
    
    List<OrmQueryProperties> removeQueryJoins();
    
    List<OrmQueryProperties> removeLazyJoins();
    
    void setLazyLoadManyPath(final String p0);
    
    void convertManyFetchJoinsToQueryJoins(final boolean p0, final int p1);
    
    PersistenceContext getPersistenceContext();
    
    void setPersistenceContext(final PersistenceContext p0);
    
    boolean isDetailEmpty();
    
    Boolean isAutofetch();
    
    AutoFetchManager getAutoFetchManager();
    
    void setAutoFetchManager(final AutoFetchManager p0);
    
    ObjectGraphNode setOrigin(final CallStack p0);
    
    void setParentNode(final ObjectGraphNode p0);
    
    void setLazyLoadProperty(final String p0);
    
    String getLazyLoadProperty();
    
    String getLazyLoadManyPath();
    
    ObjectGraphNode getParentNode();
    
    boolean isUsageProfiling();
    
    void setUsageProfiling(final boolean p0);
    
    String getName();
    
    int queryAutofetchHash();
    
    int queryPlanHash(final BeanQueryRequest<?> p0);
    
    int queryBindHash();
    
    int queryHash();
    
    boolean isSqlSelect();
    
    boolean isRawSql();
    
    OrderBy<T> getOrderBy();
    
    String getAdditionalWhere();
    
    SpiExpressionList<T> getWhereExpressions();
    
    SpiExpressionList<T> getHavingExpressions();
    
    String getAdditionalHaving();
    
    boolean hasMaxRowsOrFirstRow();
    
    Boolean isUseBeanCache();
    
    boolean isUseQueryCache();
    
    boolean isLoadBeanCache();
    
    Boolean isReadOnly();
    
    void contextAdd(final EntityBean p0);
    
    Class<T> getBeanType();
    
    int getTimeout();
    
    ArrayList<EntityBean> getContextAdditions();
    
    BindParams getBindParams();
    
    String getQuery();
    
    void setDetail(final OrmQueryDetail p0);
    
    boolean tuneFetchProperties(final OrmQueryDetail p0);
    
    void setAutoFetchTuned(final boolean p0);
    
    OrmQueryDetail getDetail();
    
    TableJoin getIncludeTableJoin();
    
    void setIncludeTableJoin(final TableJoin p0);
    
    String getMapKey();
    
    int getBackgroundFetchAfter();
    
    int getMaxRows();
    
    int getFirstRow();
    
    boolean isDistinct();
    
    boolean isVanillaMode(final boolean p0);
    
    void setDefaultSelectClause();
    
    String getRawWhereClause();
    
    Object getId();
    
    QueryListener<T> getListener();
    
    boolean createOwnTransaction();
    
    void setGeneratedSql(final String p0);
    
    int getBufferFetchSizeHint();
    
    boolean isFutureFetch();
    
    void setFutureFetch(final boolean p0);
    
    void setCancelableQuery(final CancelableQuery p0);
    
    boolean isCancelled();
    
    public enum Mode
    {
        NORMAL(false), 
        LAZYLOAD_MANY(false), 
        LAZYLOAD_BEAN(true), 
        REFRESH_BEAN(true);
        
        private final boolean loadContextBean;
        
        private Mode(final boolean loadContextBean) {
            this.loadContextBean = loadContextBean;
        }
        
        public boolean isLoadContextBean() {
            return this.loadContextBean;
        }
    }
}
