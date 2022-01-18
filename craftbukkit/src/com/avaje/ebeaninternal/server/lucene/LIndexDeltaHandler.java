// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Query;
import java.io.IOException;
import java.util.logging.Level;
import java.util.LinkedHashMap;
import java.util.Collections;
import org.apache.lucene.index.Term;
import com.avaje.ebeaninternal.api.SpiQuery;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.io.Serializable;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import java.util.Set;
import org.apache.lucene.document.Document;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import java.util.logging.Logger;

public class LIndexDeltaHandler
{
    private static final Logger logger;
    private final LIndex index;
    private final LIndexSearch search;
    private final IndexSearcher searcher;
    private final IndexWriter indexWriter;
    private final Analyzer analyzer;
    private final BeanDescriptor<?> beanDescriptor;
    private final DocFieldWriter docFieldWriter;
    private final IndexUpdates indexUpdates;
    private final List<BeanDelta> deltaBeans;
    private final Document document;
    private Set<Object> deltaBeanKeys;
    private int deltaCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;
    private int deleteByIdCount;
    
    public LIndexDeltaHandler(final LIndex index, final LIndexSearch search, final IndexWriter indexWriter, final Analyzer analyzer, final BeanDescriptor<?> beanDescriptor, final DocFieldWriter docFieldWriter, final IndexUpdates indexUpdates) {
        this.document = new Document();
        this.index = index;
        this.search = search;
        this.searcher = search.getIndexSearcher();
        this.indexWriter = indexWriter;
        this.analyzer = analyzer;
        this.beanDescriptor = beanDescriptor;
        this.docFieldWriter = docFieldWriter;
        this.indexUpdates = indexUpdates;
        final BeanDeltaList deltaList = indexUpdates.getDeltaList();
        this.deltaBeans = ((deltaList == null) ? null : deltaList.getDeltaBeans());
    }
    
    public int process() {
        this.deltaBeanKeys = this.processDeltaBeans();
        this.deltaCount = this.deltaBeanKeys.size();
        final BeanPersistIds deleteById = this.indexUpdates.getDeleteIds();
        if (deleteById != null) {
            this.deleteByIdCount = this.processDeletes(deleteById.getDeleteIds());
        }
        final BeanPersistIds beanPersistIds = this.indexUpdates.getBeanPersistIds();
        if (beanPersistIds != null) {
            this.deleteCount = this.processDeletes(beanPersistIds.getDeleteIds());
            this.processInserts(beanPersistIds.getInsertIds());
            this.processUpdates(beanPersistIds.getUpdateIds());
        }
        final String msg = String.format("Lucene update index %s deltas[%s] insert[%s] update[%s] delete[%s]", this.index, this.deltaCount, this.insertCount, this.updateCount, this.deleteCount + this.deleteByIdCount);
        LIndexDeltaHandler.logger.info(msg);
        return this.deltaCount + this.insertCount + this.updateCount + this.deleteCount + this.deleteByIdCount;
    }
    
    private void processUpdates(final List<Serializable> updateIds) {
        if (updateIds == null || updateIds.isEmpty()) {
            return;
        }
        final ArrayList<Object> filterIdList = new ArrayList<Object>();
        for (int i = 0; i < updateIds.size(); ++i) {
            final Serializable id = updateIds.get(i);
            if (!this.deltaBeanKeys.contains(id)) {
                filterIdList.add(id);
            }
        }
        if (!filterIdList.isEmpty()) {
            final SpiQuery<?> ormQuery = this.index.createQuery();
            ormQuery.where().idIn(filterIdList);
            final List<?> list = ormQuery.findList();
            for (int j = 0; j < list.size(); ++j) {
                final Object bean = list.get(j);
                try {
                    final Object id2 = this.beanDescriptor.getId(bean);
                    final Term term = this.index.createIdTerm(id2);
                    this.docFieldWriter.writeValue(bean, this.document);
                    this.indexWriter.updateDocument(term, this.document);
                }
                catch (Exception e) {
                    throw new PersistenceException(e);
                }
            }
            this.updateCount = list.size();
        }
    }
    
    private void processInserts(final List<Serializable> insertIds) {
        if (insertIds == null || insertIds.isEmpty()) {
            return;
        }
        final SpiQuery<?> ormQuery = this.index.createQuery();
        ormQuery.where().idIn(insertIds);
        final List<?> list = ormQuery.findList();
        for (int i = 0; i < list.size(); ++i) {
            final Object bean = list.get(i);
            try {
                this.docFieldWriter.writeValue(bean, this.document);
                this.indexWriter.addDocument(this.document);
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
        this.insertCount = list.size();
    }
    
    private int processDeletes(final List<Serializable> deleteIds) {
        if (deleteIds == null || deleteIds.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < deleteIds.size(); ++i) {
            final Serializable id = deleteIds.get(i);
            final Term term = this.index.createIdTerm(id);
            try {
                this.indexWriter.deleteDocuments(term);
            }
            catch (Exception e) {
                throw new PersistenceLuceneException(e);
            }
        }
        return deleteIds.size();
    }
    
    private Set<Object> processDeltaBeans() {
        if (this.deltaBeans == null) {
            return Collections.emptySet();
        }
        try {
            final LinkedHashMap<Object, Object> beanMap = this.getBeans();
            for (int i = 0; i < this.deltaBeans.size(); ++i) {
                final BeanDelta deltaBean = this.deltaBeans.get(i);
                final Object id = deltaBean.getId();
                final Object bean = beanMap.get(id);
                if (bean == null) {
                    throw new PersistenceLuceneException("Unmatched bean " + deltaBean.getId());
                }
                deltaBean.apply(bean);
                this.docFieldWriter.writeValue(bean, this.document);
                try {
                    final Term term = this.index.createIdTerm(id);
                    this.indexWriter.updateDocument(term, this.document, this.analyzer);
                }
                catch (Exception e) {
                    throw new PersistenceLuceneException(e);
                }
            }
            return beanMap.keySet();
        }
        finally {
            this.closeResources();
        }
    }
    
    private void closeResources() {
        try {
            this.search.releaseClose();
        }
        catch (Exception e) {
            LIndexDeltaHandler.logger.log(Level.SEVERE, "Error with IndexReader decRef()", e);
        }
    }
    
    private LinkedHashMap<Object, Object> getBeans() {
        final Query query = this.createQuery();
        final LinkedHashMap<Object, Object> beanMap = new LinkedHashMap<Object, Object>();
        try {
            final TopDocs topDocs = this.searcher.search(query, this.deltaBeans.size() * 2);
            final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < scoreDocs.length; ++i) {
                final int doc = scoreDocs[i].doc;
                final Document document = this.searcher.doc(doc);
                final Object bean = this.index.readDocument(document);
                final Object id = this.beanDescriptor.getId(bean);
                beanMap.put(id, bean);
            }
            return beanMap;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private Query createQuery() {
        final BooleanQuery b = new BooleanQuery();
        for (int i = 0; i < this.deltaBeans.size(); ++i) {
            final BeanDelta d = this.deltaBeans.get(i);
            final Object id = d.getId();
            final Term term = this.index.createIdTerm(id);
            final TermQuery tq = new TermQuery(term);
            b.add((Query)tq, BooleanClause.Occur.SHOULD);
        }
        return (Query)b;
    }
    
    static {
        logger = Logger.getLogger(LIndexDeltaHandler.class.getName());
    }
}
