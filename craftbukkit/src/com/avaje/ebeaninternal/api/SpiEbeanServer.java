// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.lang.reflect.Type;
import com.avaje.ebeaninternal.server.query.CQueryEngine;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebean.TxScope;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebean.bean.CallStack;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.BeanLoader;
import com.avaje.ebean.EbeanServer;

public interface SpiEbeanServer extends EbeanServer, BeanLoader, BeanCollectionLoader
{
    boolean isDefaultDeleteMissingChildren();
    
    boolean isDefaultUpdateNullProperties();
    
    boolean isVanillaMode();
    
    DatabasePlatform getDatabasePlatform();
    
    PstmtBatch getPstmtBatch();
    
    CallStack createCallStack();
    
    DdlGenerator getDdlGenerator();
    
    AutoFetchManager getAutoFetchManager();
    
    LuceneIndexManager getLuceneIndexManager();
    
    void clearQueryStatistics();
    
    List<BeanDescriptor<?>> getBeanDescriptors();
    
     <T> BeanDescriptor<T> getBeanDescriptor(final Class<T> p0);
    
    BeanDescriptor<?> getBeanDescriptorById(final String p0);
    
    List<BeanDescriptor<?>> getBeanDescriptors(final String p0);
    
    void externalModification(final TransactionEventTable p0);
    
    SpiTransaction createServerTransaction(final boolean p0, final int p1);
    
    SpiTransaction getCurrentServerTransaction();
    
    ScopeTrans createScopeTrans(final TxScope p0);
    
    SpiTransaction createQueryTransaction();
    
    void remoteTransactionEvent(final RemoteTransactionEvent p0);
    
     <T> SpiOrmQueryRequest<T> createQueryRequest(final Query.Type p0, final Query<T> p1, final Transaction p2);
    
     <T> CQuery<T> compileQuery(final Query<T> p0, final Transaction p1);
    
    CQueryEngine getQueryEngine();
    
     <T> List<Object> findIdsWithCopy(final Query<T> p0, final Transaction p1);
    
     <T> int findRowCountWithCopy(final Query<T> p0, final Transaction p1);
    
    void loadBean(final LoadBeanRequest p0);
    
    void loadMany(final LoadManyRequest p0);
    
    int getLazyLoadBatchSize();
    
    boolean isSupportedType(final Type p0);
}
