// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import java.util.HashMap;
import java.util.Collection;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.util.Map;
import com.avaje.ebeaninternal.api.SpiEbeanServer;

public class IndexUpdatesBuilder
{
    private final SpiEbeanServer server;
    private final Map<String, IndexUpdates> map;
    private final RemoteTransactionEvent txnEvent;
    
    public static Collection<IndexUpdates> create(final SpiEbeanServer server, final RemoteTransactionEvent txnEvent) {
        return new IndexUpdatesBuilder(server, txnEvent).create();
    }
    
    private IndexUpdatesBuilder(final SpiEbeanServer server, final RemoteTransactionEvent txnEvent) {
        this.map = new HashMap<String, IndexUpdates>();
        this.server = server;
        this.txnEvent = txnEvent;
    }
    
    private Collection<IndexUpdates> create() {
        final Set<IndexInvalidate> indexInvalidations = this.txnEvent.getIndexInvalidations();
        if (indexInvalidations != null) {
            for (final IndexInvalidate indexInvalidate : indexInvalidations) {
                final LIndex index = this.server.getLuceneIndexManager().getIndex(indexInvalidate.getIndexName());
                final BeanDescriptor<?> d = index.getBeanDescriptor();
                this.getEventByType(d).setInvalidate(true);
            }
        }
        final List<TransactionEventTable.TableIUD> tableIUDList = this.txnEvent.getTableIUDList();
        if (tableIUDList != null) {
            for (int i = 0; i < tableIUDList.size(); ++i) {
                final TransactionEventTable.TableIUD tableIUD = tableIUDList.get(i);
                final List<BeanDescriptor<?>> descList = this.server.getBeanDescriptors(tableIUD.getTable());
                if (descList != null) {
                    for (int j = 0; j < descList.size(); ++j) {
                        final BeanDescriptor<?> d2 = descList.get(j);
                        this.getEventByType(d2).addTableIUD(tableIUD);
                    }
                }
            }
        }
        final DeleteByIdMap deleteByIdMap = this.txnEvent.getDeleteByIdMap();
        if (deleteByIdMap != null) {
            for (final BeanPersistIds delIds : deleteByIdMap.values()) {
                this.getEventByType(delIds.getBeanDescriptor()).setDeleteIds(delIds);
            }
        }
        final List<BeanPersistIds> beanPersistList = this.txnEvent.getBeanPersistList();
        if (beanPersistList != null) {
            for (int k = 0; k < beanPersistList.size(); ++k) {
                final BeanPersistIds b = beanPersistList.get(k);
                this.getEventByType(b.getBeanDescriptor()).setBeanPersistIds(b);
            }
        }
        final List<BeanDeltaList> beanDeltaLists = this.txnEvent.getBeanDeltaLists();
        if (beanDeltaLists != null) {
            for (int l = 0; l < beanDeltaLists.size(); ++l) {
                final BeanDeltaList d3 = beanDeltaLists.get(l);
                this.getEventByType(d3.getBeanDescriptor()).setDeltaList(d3);
            }
        }
        return this.map.values();
    }
    
    private IndexUpdates getEventByType(final BeanDescriptor<?> d) {
        final String beanDescKey = d.getBeanType().getName();
        IndexUpdates eventByType = this.map.get(beanDescKey);
        if (eventByType == null) {
            eventByType = new IndexUpdates(d);
            this.map.put(beanDescKey, eventByType);
        }
        return eventByType;
    }
}
