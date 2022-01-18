// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexUpdateFuture;
import java.util.List;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.api.SpiQuery;
import org.apache.lucene.document.Document;
import java.util.Set;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.index.Term;
import java.io.File;
import java.io.IOException;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.Analyzer;
import com.avaje.ebean.config.lucene.LuceneIndex;

public class LIndex implements LuceneIndex
{
    private final DefaultLuceneIndexManager manager;
    private final String name;
    private final Analyzer analyzer;
    private final IndexWriter.MaxFieldLength maxFieldLength;
    private final LIndexFields fieldDefn;
    private final BeanDescriptor<?> desc;
    private final OrmQueryDetail ormQueryDetail;
    private final LIndexIo indexIo;
    private final LIndexFieldId idField;
    private final Object syncMonitor;
    private boolean runningSync;
    private LIndexSync queuedSync;
    
    public LIndex(final DefaultLuceneIndexManager manager, final String indexName, final String indexDir, final Analyzer analyzer, final IndexWriter.MaxFieldLength maxFieldLength, final BeanDescriptor<?> desc, final LIndexFields fieldDefn, final String[] updateProps) throws IOException {
        this.syncMonitor = new Object();
        this.manager = manager;
        this.name = desc.getFullName();
        this.analyzer = analyzer;
        this.maxFieldLength = maxFieldLength;
        this.desc = desc;
        this.fieldDefn = fieldDefn;
        this.idField = fieldDefn.getIdField();
        this.ormQueryDetail = fieldDefn.getOrmQueryDetail();
        this.indexIo = new LIndexIo(manager, indexDir, this, updateProps);
        manager.addIndex(this);
        fieldDefn.registerIndexWithProperties(this);
    }
    
    protected void syncFinished(final boolean success) {
        synchronized (this.syncMonitor) {
            this.runningSync = false;
        }
    }
    
    public void queueSync(final String masterHost) {
        synchronized (this.syncMonitor) {
            final LIndexSync sync = new LIndexSync(this, masterHost);
            if (!this.runningSync) {
                this.runningSync = true;
                this.manager.execute(sync);
            }
            else {
                this.queuedSync = sync;
            }
        }
    }
    
    public void manage(final LuceneIndexManager indexManager) {
        synchronized (this.syncMonitor) {
            this.indexIo.manage(indexManager);
            if (!this.runningSync && this.queuedSync != null) {
                final LIndexSync sync = this.queuedSync;
                this.runningSync = true;
                this.queuedSync = null;
                this.manager.execute(sync);
            }
        }
    }
    
    public void addNotifyCommitRunnable(final Runnable r) {
        this.indexIo.addNotifyCommitRunnable(r);
    }
    
    public LIndexVersion getLastestVersion() {
        return this.indexIo.getLastestVersion();
    }
    
    public File getIndexDir() {
        return this.indexIo.getIndexDir();
    }
    
    public void refresh(final boolean nearRealTime) {
        this.indexIo.refresh(nearRealTime);
    }
    
    public LIndexFileInfo getLocalFile(final String fileName) {
        return this.indexIo.getLocalFile(fileName);
    }
    
    public LIndexCommitInfo obtainLastIndexCommitIfNewer(final long remoteIndexVersion) {
        return this.indexIo.obtainLastIndexCommitIfNewer(remoteIndexVersion);
    }
    
    public void releaseIndexCommit(final long remoteIndexVersion) {
        this.indexIo.releaseIndexCommit(remoteIndexVersion);
    }
    
    public LIndexFileInfo getFile(final long remoteIndexVersion, final String fileName) {
        return this.indexIo.getFile(remoteIndexVersion, fileName);
    }
    
    public Term createIdTerm(final Object id) {
        return this.idField.createTerm(id);
    }
    
    public void shutdown() {
        this.indexIo.shutdown();
    }
    
    public LIndexUpdateFuture rebuild() {
        final LIndexUpdateFuture future = new LIndexUpdateFuture(this.desc.getBeanType());
        this.indexIo.addWorkToQueue(LIndexWork.newRebuild(future));
        return future;
    }
    
    public LIndexUpdateFuture update() {
        final LIndexUpdateFuture future = new LIndexUpdateFuture(this.desc.getBeanType());
        this.indexIo.addWorkToQueue(LIndexWork.newQueryUpdate(future));
        return future;
    }
    
    public String toString() {
        return this.name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<?> getBeanType() {
        return this.desc.getBeanType();
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }
    
    public LIndexSearch getIndexSearch() {
        return this.indexIo.getIndexSearch();
    }
    
    public Analyzer getAnalyzer() {
        return this.analyzer;
    }
    
    public IndexWriter.MaxFieldLength getMaxFieldLength() {
        return this.maxFieldLength;
    }
    
    public QueryParser createQueryParser(final String fieldName) {
        final QueryParser p = this.fieldDefn.createQueryParser(fieldName);
        p.setDefaultOperator(QueryParser.Operator.AND);
        return p;
    }
    
    public LIndexFields getIndexFieldDefn() {
        return this.fieldDefn;
    }
    
    public Set<String> getResolvePropertyNames() {
        return this.fieldDefn.getResolvePropertyNames();
    }
    
    public OrmQueryDetail getOrmQueryDetail() {
        return this.ormQueryDetail;
    }
    
    public Object readDocument(final Document doc) {
        final Object bean = this.desc.createEntityBean();
        this.fieldDefn.readDocument(doc, bean);
        return bean;
    }
    
    public DocFieldWriter createDocFieldWriter() {
        return this.fieldDefn.createDocFieldWriter();
    }
    
    public SpiQuery<?> createQuery() {
        return this.indexIo.createQuery();
    }
    
    public LIndexUpdateFuture process(final IndexUpdates indexUpdates) {
        final List<TransactionEventTable.TableIUD> tableList = indexUpdates.getTableList();
        if (tableList != null && tableList.size() > 0) {
            boolean bulkDelete = false;
            for (int i = 0; i < tableList.size(); ++i) {
                final TransactionEventTable.TableIUD bulkTableEvent = tableList.get(i);
                if (bulkTableEvent.isDelete()) {
                    bulkDelete = true;
                }
            }
            if (bulkDelete) {
                return this.rebuild();
            }
            return this.update();
        }
        else {
            if (indexUpdates.isInvalidate()) {
                return this.update();
            }
            final LIndexUpdateFuture f = new LIndexUpdateFuture(this.desc.getBeanType());
            this.indexIo.addWorkToQueue(LIndexWork.newTxnUpdate(f, indexUpdates));
            return f;
        }
    }
}
