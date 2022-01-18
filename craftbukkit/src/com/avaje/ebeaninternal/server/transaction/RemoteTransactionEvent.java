// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.util.Collection;
import java.util.HashSet;
import java.io.IOException;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.util.ArrayList;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.Set;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import java.util.List;
import java.io.Serializable;

public class RemoteTransactionEvent implements Serializable, Runnable
{
    private static final long serialVersionUID = 757920022500956949L;
    private List<BeanPersistIds> beanPersistList;
    private List<TransactionEventTable.TableIUD> tableList;
    private List<BeanDeltaList> beanDeltaLists;
    private BeanDeltaMap beanDeltaMap;
    private List<IndexEvent> indexEventList;
    private Set<IndexInvalidate> indexInvalidations;
    private DeleteByIdMap deleteByIdMap;
    private String serverName;
    private transient SpiEbeanServer server;
    
    public RemoteTransactionEvent(final String serverName) {
        this.beanPersistList = new ArrayList<BeanPersistIds>();
        this.serverName = serverName;
    }
    
    public RemoteTransactionEvent(final SpiEbeanServer server) {
        this.beanPersistList = new ArrayList<BeanPersistIds>();
        this.server = server;
    }
    
    public void run() {
        this.server.remoteTransactionEvent(this);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.beanDeltaMap != null) {
            sb.append(this.beanDeltaMap);
        }
        sb.append(this.beanPersistList);
        if (this.tableList != null) {
            sb.append(this.tableList);
        }
        return sb.toString();
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        if (this.indexInvalidations != null) {
            for (final IndexInvalidate indexInvalidate : this.indexInvalidations) {
                indexInvalidate.writeBinaryMessage(msgList);
            }
        }
        if (this.tableList != null) {
            for (int i = 0; i < this.tableList.size(); ++i) {
                this.tableList.get(i).writeBinaryMessage(msgList);
            }
        }
        if (this.deleteByIdMap != null) {
            for (final BeanPersistIds deleteIds : this.deleteByIdMap.values()) {
                deleteIds.writeBinaryMessage(msgList);
            }
        }
        if (this.beanPersistList != null) {
            for (int i = 0; i < this.beanPersistList.size(); ++i) {
                this.beanPersistList.get(i).writeBinaryMessage(msgList);
            }
        }
        if (this.beanDeltaLists != null) {
            for (int i = 0; i < this.beanDeltaLists.size(); ++i) {
                this.beanDeltaLists.get(i).writeBinaryMessage(msgList);
            }
        }
        if (this.indexEventList != null) {
            for (int i = 0; i < this.indexEventList.size(); ++i) {
                this.indexEventList.get(i).writeBinaryMessage(msgList);
            }
        }
    }
    
    public boolean isEmpty() {
        return this.beanPersistList.isEmpty() && (this.tableList == null || this.tableList.isEmpty());
    }
    
    public void addBeanPersistIds(final BeanPersistIds beanPersist) {
        this.beanPersistList.add(beanPersist);
    }
    
    public void addIndexInvalidate(final IndexInvalidate indexInvalidate) {
        if (this.indexInvalidations == null) {
            this.indexInvalidations = new HashSet<IndexInvalidate>();
        }
        this.indexInvalidations.add(indexInvalidate);
    }
    
    public void addTableIUD(final TransactionEventTable.TableIUD tableIud) {
        if (this.tableList == null) {
            this.tableList = new ArrayList<TransactionEventTable.TableIUD>(4);
        }
        this.tableList.add(tableIud);
    }
    
    public void addBeanDeltaList(final BeanDeltaList deltaList) {
        if (this.beanDeltaLists == null) {
            this.beanDeltaLists = new ArrayList<BeanDeltaList>();
        }
        this.beanDeltaLists.add(deltaList);
    }
    
    public void addBeanDelta(final BeanDelta beanDelta) {
        if (this.beanDeltaMap == null) {
            this.beanDeltaMap = new BeanDeltaMap();
        }
        this.beanDeltaMap.addBeanDelta(beanDelta);
    }
    
    public void addIndexEvent(final IndexEvent indexEvent) {
        if (this.indexEventList == null) {
            this.indexEventList = new ArrayList<IndexEvent>(2);
        }
        this.indexEventList.add(indexEvent);
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public SpiEbeanServer getServer() {
        return this.server;
    }
    
    public void setServer(final SpiEbeanServer server) {
        this.server = server;
    }
    
    public DeleteByIdMap getDeleteByIdMap() {
        return this.deleteByIdMap;
    }
    
    public void setDeleteByIdMap(final DeleteByIdMap deleteByIdMap) {
        this.deleteByIdMap = deleteByIdMap;
    }
    
    public Set<IndexInvalidate> getIndexInvalidations() {
        return this.indexInvalidations;
    }
    
    public List<IndexEvent> getIndexEventList() {
        return this.indexEventList;
    }
    
    public List<TransactionEventTable.TableIUD> getTableIUDList() {
        return this.tableList;
    }
    
    public List<BeanPersistIds> getBeanPersistList() {
        return this.beanPersistList;
    }
    
    public List<BeanDeltaList> getBeanDeltaLists() {
        if (this.beanDeltaMap != null) {
            this.beanDeltaLists.addAll(this.beanDeltaMap.deltaLists());
        }
        return this.beanDeltaLists;
    }
}
