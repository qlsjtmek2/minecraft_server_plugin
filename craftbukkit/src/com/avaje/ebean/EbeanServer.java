// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import com.avaje.ebean.text.json.JsonContext;
import com.avaje.ebean.cache.ServerCacheManager;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.OptimisticLockException;
import java.util.Set;
import java.util.List;
import com.avaje.ebean.text.csv.CsvReader;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.util.Map;
import com.avaje.ebean.config.lucene.LuceneIndex;

public interface EbeanServer
{
    AdminLogging getAdminLogging();
    
    AdminAutofetch getAdminAutofetch();
    
    LuceneIndex getLuceneIndex(final Class<?> p0);
    
    String getName();
    
    ExpressionFactory getExpressionFactory();
    
    BeanState getBeanState(final Object p0);
    
    Object getBeanId(final Object p0);
    
    Map<String, ValuePair> diff(final Object p0, final Object p1);
    
    InvalidValue validate(final Object p0);
    
    InvalidValue[] validate(final Object p0, final String p1, final Object p2);
    
     <T> T createEntityBean(final Class<T> p0);
    
    ObjectInputStream createProxyObjectInputStream(final InputStream p0);
    
     <T> CsvReader<T> createCsvReader(final Class<T> p0);
    
     <T> Query<T> createNamedQuery(final Class<T> p0, final String p1);
    
     <T> Query<T> createQuery(final Class<T> p0, final String p1);
    
     <T> Query<T> createQuery(final Class<T> p0);
    
     <T> Query<T> find(final Class<T> p0);
    
    Object nextId(final Class<?> p0);
    
     <T> Filter<T> filter(final Class<T> p0);
    
     <T> void sort(final List<T> p0, final String p1);
    
     <T> Update<T> createNamedUpdate(final Class<T> p0, final String p1);
    
     <T> Update<T> createUpdate(final Class<T> p0, final String p1);
    
    SqlQuery createSqlQuery(final String p0);
    
    SqlQuery createNamedSqlQuery(final String p0);
    
    SqlUpdate createSqlUpdate(final String p0);
    
    CallableSql createCallableSql(final String p0);
    
    SqlUpdate createNamedSqlUpdate(final String p0);
    
    Transaction createTransaction();
    
    Transaction createTransaction(final TxIsolation p0);
    
    Transaction beginTransaction();
    
    Transaction beginTransaction(final TxIsolation p0);
    
    Transaction currentTransaction();
    
    void commitTransaction();
    
    void rollbackTransaction();
    
    void endTransaction();
    
    void logComment(final String p0);
    
    void refresh(final Object p0);
    
    void refreshMany(final Object p0, final String p1);
    
     <T> T find(final Class<T> p0, final Object p1);
    
     <T> T getReference(final Class<T> p0, final Object p1);
    
     <T> int findRowCount(final Query<T> p0, final Transaction p1);
    
     <T> List<Object> findIds(final Query<T> p0, final Transaction p1);
    
     <T> QueryIterator<T> findIterate(final Query<T> p0, final Transaction p1);
    
     <T> void findVisit(final Query<T> p0, final QueryResultVisitor<T> p1, final Transaction p2);
    
     <T> List<T> findList(final Query<T> p0, final Transaction p1);
    
     <T> FutureRowCount<T> findFutureRowCount(final Query<T> p0, final Transaction p1);
    
     <T> FutureIds<T> findFutureIds(final Query<T> p0, final Transaction p1);
    
     <T> FutureList<T> findFutureList(final Query<T> p0, final Transaction p1);
    
    SqlFutureList findFutureList(final SqlQuery p0, final Transaction p1);
    
     <T> PagingList<T> findPagingList(final Query<T> p0, final Transaction p1, final int p2);
    
     <T> Set<T> findSet(final Query<T> p0, final Transaction p1);
    
     <T> Map<?, T> findMap(final Query<T> p0, final Transaction p1);
    
     <T> T findUnique(final Query<T> p0, final Transaction p1);
    
    List<SqlRow> findList(final SqlQuery p0, final Transaction p1);
    
    Set<SqlRow> findSet(final SqlQuery p0, final Transaction p1);
    
    Map<?, SqlRow> findMap(final SqlQuery p0, final Transaction p1);
    
    SqlRow findUnique(final SqlQuery p0, final Transaction p1);
    
    void save(final Object p0) throws OptimisticLockException;
    
    int save(final Iterator<?> p0) throws OptimisticLockException;
    
    int save(final Collection<?> p0) throws OptimisticLockException;
    
    void delete(final Object p0) throws OptimisticLockException;
    
    int delete(final Iterator<?> p0) throws OptimisticLockException;
    
    int delete(final Collection<?> p0) throws OptimisticLockException;
    
    int delete(final Class<?> p0, final Object p1);
    
    int delete(final Class<?> p0, final Object p1, final Transaction p2);
    
    void delete(final Class<?> p0, final Collection<?> p1);
    
    void delete(final Class<?> p0, final Collection<?> p1, final Transaction p2);
    
    int execute(final SqlUpdate p0);
    
    int execute(final Update<?> p0);
    
    int execute(final Update<?> p0, final Transaction p1);
    
    int execute(final CallableSql p0);
    
    void externalModification(final String p0, final boolean p1, final boolean p2, final boolean p3);
    
     <T> T find(final Class<T> p0, final Object p1, final Transaction p2);
    
    void save(final Object p0, final Transaction p1) throws OptimisticLockException;
    
    int save(final Iterator<?> p0, final Transaction p1) throws OptimisticLockException;
    
    void update(final Object p0);
    
    void update(final Object p0, final Transaction p1);
    
    void update(final Object p0, final Set<String> p1);
    
    void update(final Object p0, final Set<String> p1, final Transaction p2);
    
    void update(final Object p0, final Set<String> p1, final Transaction p2, final boolean p3, final boolean p4);
    
    void insert(final Object p0);
    
    void insert(final Object p0, final Transaction p1);
    
    int deleteManyToManyAssociations(final Object p0, final String p1);
    
    int deleteManyToManyAssociations(final Object p0, final String p1, final Transaction p2);
    
    void saveManyToManyAssociations(final Object p0, final String p1);
    
    void saveManyToManyAssociations(final Object p0, final String p1, final Transaction p2);
    
    void saveAssociation(final Object p0, final String p1);
    
    void saveAssociation(final Object p0, final String p1, final Transaction p2);
    
    void delete(final Object p0, final Transaction p1) throws OptimisticLockException;
    
    int delete(final Iterator<?> p0, final Transaction p1) throws OptimisticLockException;
    
    int execute(final SqlUpdate p0, final Transaction p1);
    
    int execute(final CallableSql p0, final Transaction p1);
    
    void execute(final TxScope p0, final TxRunnable p1);
    
    void execute(final TxRunnable p0);
    
     <T> T execute(final TxScope p0, final TxCallable<T> p1);
    
     <T> T execute(final TxCallable<T> p0);
    
    ServerCacheManager getServerCacheManager();
    
    BackgroundExecutor getBackgroundExecutor();
    
    void runCacheWarming();
    
    void runCacheWarming(final Class<?> p0);
    
    JsonContext createJsonContext();
}
