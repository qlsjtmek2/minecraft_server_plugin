// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.index.Term;
import javax.persistence.PersistenceException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.store.FSDirectory;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.Junction;
import java.sql.Timestamp;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryListener;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.List;
import java.util.logging.Level;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import org.apache.lucene.store.Directory;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.Analyzer;
import java.util.logging.Logger;

public class LIndexIo
{
    private static final Logger logger;
    private final LuceneIndexManager manager;
    private final String indexDir;
    private final LIndex index;
    private final Analyzer analyzer;
    private final IndexWriter.MaxFieldLength maxFieldLength;
    private final Class<?> beanType;
    private final OrmQueryDetail ormQueryDetail;
    private final Directory directory;
    private final BeanDescriptor<?> beanDescriptor;
    private final IndexWriter indexWriter;
    private final LIndexIoSearcher ioSearcher;
    private final HoldAwareIndexDeletionPolicy commitDeletionPolicy;
    private final String[] updateProps;
    private final Object writeMonitor;
    private final Object workQueueMonitor;
    private final ArrayList<LIndexWork> workQueue;
    private final ArrayList<Runnable> notifyCommitRunnables;
    private long lastUpdateTime;
    private long queueCommitStart;
    private int queueCommitCount;
    private int totalCommitCount;
    private long totalCommitNanos;
    private long totalPostCommitNanos;
    
    public LIndexIo(final LuceneIndexManager manager, final String indexDir, final LIndex index, final String[] updateProps) throws IOException {
        this.writeMonitor = new Object();
        this.workQueueMonitor = new Object();
        this.workQueue = new ArrayList<LIndexWork>();
        this.notifyCommitRunnables = new ArrayList<Runnable>();
        this.manager = manager;
        this.indexDir = indexDir;
        this.index = index;
        this.updateProps = updateProps;
        this.analyzer = index.getAnalyzer();
        this.maxFieldLength = index.getMaxFieldLength();
        this.beanType = index.getBeanType();
        this.ormQueryDetail = index.getOrmQueryDetail();
        this.directory = this.createDirectory();
        this.beanDescriptor = index.getBeanDescriptor();
        this.commitDeletionPolicy = new HoldAwareIndexDeletionPolicy(indexDir);
        this.indexWriter = this.createIndexWriter();
        this.ioSearcher = this.createIoSearcher();
    }
    
    public LIndexVersion getLastestVersion() {
        return this.ioSearcher.getLastestVersion();
    }
    
    public long getLastVersion() {
        return this.commitDeletionPolicy.getLastVersion();
    }
    
    public LIndexCommitInfo obtainLastIndexCommitIfNewer(final long remoteIndexVersion) {
        return this.commitDeletionPolicy.obtainLastIndexCommitIfNewer(remoteIndexVersion);
    }
    
    public File getIndexDir() {
        return new File(this.indexDir);
    }
    
    public LIndexFileInfo getLocalFile(final String fileName) {
        final File f = new File(this.indexDir, fileName);
        return new LIndexFileInfo(f);
    }
    
    public void refresh(final boolean nearRealTime) {
        this.ioSearcher.refresh(nearRealTime);
    }
    
    public LIndexFileInfo getFile(final long remoteIndexVersion, final String fileName) {
        this.commitDeletionPolicy.touch(remoteIndexVersion);
        final File f = new File(this.indexDir, fileName);
        return new LIndexFileInfo(f);
    }
    
    public void releaseIndexCommit(final long remoteIndexVersion) {
        this.commitDeletionPolicy.releaseIndexCommit(remoteIndexVersion);
    }
    
    public void shutdown() {
        synchronized (this.writeMonitor) {
            try {
                if (this.queueCommitStart > 0L) {
                    this.indexWriter.commit();
                }
            }
            catch (Exception e) {
                String msg = "Error committing queued changes for IndexWriter for " + this.indexDir;
                LIndexIo.logger.log(Level.SEVERE, msg, e);
                e.printStackTrace();
                try {
                    this.indexWriter.close();
                }
                catch (Exception e) {
                    msg = "Error closing IndexWriter for " + this.indexDir;
                    LIndexIo.logger.log(Level.SEVERE, msg, e);
                    e.printStackTrace();
                }
            }
            finally {
                try {
                    this.indexWriter.close();
                }
                catch (Exception e2) {
                    final String msg2 = "Error closing IndexWriter for " + this.indexDir;
                    LIndexIo.logger.log(Level.SEVERE, msg2, e2);
                    e2.printStackTrace();
                }
            }
        }
    }
    
    protected void manage(final LuceneIndexManager indexManager) {
        this.processWorkQueue();
        this.commit(false);
    }
    
    protected void addWorkToQueue(final LIndexWork work) {
        synchronized (this.workQueueMonitor) {
            this.workQueue.add(work);
        }
    }
    
    protected void processWorkQueue() {
        synchronized (this.workQueueMonitor) {
            if (!this.workQueue.isEmpty()) {
                LIndexWork.WorkType maxWorkType = null;
                for (int i = 0; i < this.workQueue.size(); ++i) {
                    final LIndexWork work = this.workQueue.get(i);
                    if (maxWorkType == null || maxWorkType.ordinal() < work.getWorkType().ordinal()) {
                        maxWorkType = work.getWorkType();
                    }
                }
                final List<LIndexWork> workQueueClone = (List<LIndexWork>)this.workQueue.clone();
                this.workQueue.clear();
                final Callable<Integer> workCallable = this.getWorkCallable(maxWorkType, workQueueClone);
                final FutureTask<Integer> ft = new FutureTask<Integer>(workCallable);
                for (int j = 0; j < workQueueClone.size(); ++j) {
                    workQueueClone.get(j).getFuture().setTask(ft);
                }
                this.manager.getServer().getBackgroundExecutor().execute(ft);
            }
        }
    }
    
    private Callable<Integer> getWorkCallable(final LIndexWork.WorkType maxWorkType, final List<LIndexWork> workQueueClone) {
        switch (maxWorkType) {
            case REBUILD: {
                return this.newRebuildCallable(workQueueClone);
            }
            case QUERY_UPDATE: {
                return this.newQueryUpdateCallable(workQueueClone);
            }
            case TXN_UPDATE: {
                return this.newTxnUpdateCallable(workQueueClone);
            }
            default: {
                throw new IllegalStateException("Unknown workType " + maxWorkType);
            }
        }
    }
    
    private Callable<Integer> newTxnUpdateCallable(final List<LIndexWork> workQueueClone) {
        return new Callable<Integer>() {
            public String toString() {
                return "TxnUpdate";
            }
            
            public Integer call() throws IOException {
                int totalDocs = 0;
                for (int i = 0; i < workQueueClone.size(); ++i) {
                    final LIndexWork lIndexWork = workQueueClone.get(i);
                    final IndexUpdates indexUpdates = lIndexWork.getIndexUpdates();
                    final LIndexDeltaHandler h = LIndexIo.this.createDeltaHandler(indexUpdates);
                    totalDocs += h.process();
                }
                LIndexIo.this.queueCommit(workQueueClone);
                return totalDocs;
            }
        };
    }
    
    private Callable<Integer> newRebuildCallable(final List<LIndexWork> workQueueClone) {
        return new QueryUpdater(true, (List)workQueueClone);
    }
    
    private Callable<Integer> newQueryUpdateCallable(final List<LIndexWork> workQueueClone) {
        return new QueryUpdater(false, (List)workQueueClone);
    }
    
    private LIndexDeltaHandler createDeltaHandler(final IndexUpdates indexUpdates) {
        final LIndexSearch search = this.getIndexSearch();
        final IndexWriter indexWriter = this.indexWriter;
        final DocFieldWriter docFieldWriter = this.index.createDocFieldWriter();
        return new LIndexDeltaHandler(this.index, search, indexWriter, this.analyzer, this.beanDescriptor, docFieldWriter, indexUpdates);
    }
    
    public LIndexSearch getIndexSearch() {
        return this.ioSearcher.getIndexSearch();
    }
    
    private void queueCommit(final List<LIndexWork> workQueueClone) {
        synchronized (this.workQueueMonitor) {
            if (this.queueCommitStart == 0L) {
                this.queueCommitStart = System.currentTimeMillis();
            }
            ++this.queueCommitCount;
            for (final LIndexWork w : workQueueClone) {
                this.notifyCommitRunnables.add(w.getFuture().getCommitRunnable());
            }
        }
    }
    
    protected void addNotifyCommitRunnable(final Runnable r) {
        synchronized (this.workQueueMonitor) {
            this.notifyCommitRunnables.add(r);
        }
    }
    
    protected long getQueueCommitStart(final boolean reset) {
        synchronized (this.workQueueMonitor) {
            final long start = this.queueCommitStart;
            if (reset) {
                this.queueCommitStart = 0L;
                this.queueCommitCount = 0;
            }
            return start;
        }
    }
    
    private boolean commit(final boolean force) {
        synchronized (this.writeMonitor) {
            long start = 0L;
            long count = 0L;
            final ArrayList<Runnable> notifyRunnables = new ArrayList<Runnable>();
            synchronized (this.workQueueMonitor) {
                start = this.queueCommitStart;
                count = this.queueCommitCount;
                this.queueCommitStart = 0L;
                this.queueCommitCount = 0;
                notifyRunnables.addAll(this.notifyCommitRunnables);
                this.notifyCommitRunnables.clear();
            }
            try {
                if (!force && start == 0L) {
                    if (!notifyRunnables.isEmpty()) {
                        for (int i = 0; i < notifyRunnables.size(); ++i) {
                            this.addNotifyCommitRunnable(notifyRunnables.get(i));
                        }
                    }
                    return false;
                }
                if (LIndexIo.logger.isLoggable(Level.INFO)) {
                    String delayMsg;
                    if (this.queueCommitStart > 0L) {
                        final long delay = System.currentTimeMillis() - start;
                        delayMsg = " queueDelayMillis:" + delay + " queueCount:" + count;
                    }
                    else {
                        delayMsg = "";
                    }
                    final String m = "Lucene commit " + this.indexDir + delayMsg;
                    LIndexIo.logger.info(m);
                }
                final long nanoStart = System.nanoTime();
                this.indexWriter.commit();
                final long nanoCommit = System.nanoTime();
                final long nanoCommitExe = nanoCommit - nanoStart;
                this.ioSearcher.postCommit();
                for (int j = 0; j < notifyRunnables.size(); ++j) {
                    notifyRunnables.get(j).run();
                }
                final long nanoPostCommitExe = System.nanoTime() - nanoCommitExe;
                ++this.totalCommitCount;
                this.totalCommitNanos += nanoCommitExe;
                this.totalPostCommitNanos += nanoPostCommitExe;
                final IndexEvent indexEvent = new IndexEvent(1, this.index.getName());
                this.manager.notifyCluster(indexEvent);
                return true;
            }
            catch (IOException e) {
                final String msg = "Error committing changes on index " + this.indexDir;
                throw new PersistenceLuceneException(msg, e);
            }
        }
    }
    
    private int rebuildIndex(final List<LIndexWork> workQueueClone) throws IOException {
        synchronized (this.writeMonitor) {
            LIndexIo.logger.info("Lucene rebuild " + this.indexDir);
            try {
                this.indexWriter.deleteAll();
                this.lastUpdateTime = System.currentTimeMillis();
                final SpiQuery<?> query = this.createQuery();
                final WriteListener writeListener = new WriteListener(this.index, this.indexWriter, false);
                query.setListener(writeListener);
                this.manager.getServer().findList(query, null);
                return writeListener.getCount();
            }
            finally {
                this.queueCommit(workQueueClone);
                this.commit(false);
            }
        }
    }
    
    private int updateIndex(final List<LIndexWork> workQueueClone) throws IOException {
        synchronized (this.writeMonitor) {
            LIndexIo.logger.info("Lucene update " + this.indexDir);
            try {
                final long updateTime = System.currentTimeMillis();
                final SpiQuery<?> query = this.createUpdateQuery();
                this.lastUpdateTime = updateTime;
                final WriteListener writeListener = new WriteListener(this.index, this.indexWriter, true);
                query.setListener(writeListener);
                this.manager.getServer().findList(query, null);
                return writeListener.getCount();
            }
            finally {
                this.queueCommit(workQueueClone);
            }
        }
    }
    
    private SpiQuery<?> createUpdateQuery() {
        final SpiQuery<?> q = this.createQuery();
        final Junction<?> disjunction = q.where().disjunction();
        final Timestamp lastUpdate = new Timestamp(this.lastUpdateTime);
        for (int i = 0; i < this.updateProps.length; ++i) {
            disjunction.ge(this.updateProps[i], lastUpdate);
        }
        return q;
    }
    
    protected SpiQuery<?> createQuery() {
        final SpiEbeanServer server = this.manager.getServer();
        final SpiQuery<?> query = (SpiQuery<?>)(SpiQuery)server.createQuery(this.beanType);
        query.setUseIndex(Query.UseIndex.NO);
        query.getDetail().tuneFetchProperties(this.ormQueryDetail);
        return query;
    }
    
    private Directory createDirectory() throws IOException {
        final File dir = new File(this.indexDir);
        return (Directory)FSDirectory.open(dir);
    }
    
    private IndexWriter createIndexWriter() {
        try {
            final boolean create = true;
            return new IndexWriter(this.directory, this.analyzer, create, (IndexDeletionPolicy)this.commitDeletionPolicy, this.maxFieldLength);
        }
        catch (IOException e) {
            final String msg = "Error getting Lucene IndexWriter for " + this.indexDir;
            throw new PersistenceLuceneException(msg, e);
        }
    }
    
    private LIndexIoSearcher createIoSearcher() {
        return new LIndexIoSearcherDefault(this.indexWriter, this.index.getName());
    }
    
    static {
        logger = Logger.getLogger(LIndexIo.class.getName());
    }
    
    class QueryUpdater implements Callable<Integer>
    {
        private final boolean rebuild;
        private final List<LIndexWork> workQueueClone;
        
        private QueryUpdater(final boolean rebuild, final List<LIndexWork> workQueueClone) {
            this.rebuild = rebuild;
            this.workQueueClone = workQueueClone;
        }
        
        public String toString() {
            return this.rebuild ? "Rebuild" : "QueryUpdate";
        }
        
        public Integer call() throws Exception {
            if (this.rebuild) {
                return LIndexIo.this.rebuildIndex(this.workQueueClone);
            }
            return LIndexIo.this.updateIndex(this.workQueueClone);
        }
    }
    
    private static class WriteListener implements QueryListener
    {
        private final boolean updateMode;
        private final LIndex index;
        private final BeanDescriptor beanDescriptor;
        private final IndexWriter indexWriter;
        private final DocFieldWriter docFieldWriter;
        private final Document document;
        private int count;
        
        private WriteListener(final LIndex index, final IndexWriter indexWriter, final boolean updateMode) {
            this.document = new Document();
            this.updateMode = updateMode;
            this.index = index;
            this.beanDescriptor = index.getBeanDescriptor();
            this.indexWriter = indexWriter;
            this.docFieldWriter = index.createDocFieldWriter();
        }
        
        public void process(final Object bean) {
            try {
                if (this.updateMode) {
                    final Object id = this.beanDescriptor.getId(bean);
                    final Term term = this.index.createIdTerm(id);
                    this.indexWriter.deleteDocuments(term);
                }
                this.docFieldWriter.writeValue(bean, this.document);
                this.indexWriter.addDocument(this.document);
                ++this.count;
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
        
        public int getCount() {
            return this.count;
        }
    }
}
