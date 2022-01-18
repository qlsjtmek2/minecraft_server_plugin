// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.util.ArrayList;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class IndexUpdates
{
    private final BeanDescriptor<?> beanDescriptor;
    private List<TransactionEventTable.TableIUD> tableList;
    private BeanPersistIds deleteIds;
    private BeanPersistIds beanPersistIds;
    private BeanDeltaList deltaList;
    private boolean invalidate;
    
    public IndexUpdates(final BeanDescriptor<?> beanDescriptor) {
        this.beanDescriptor = beanDescriptor;
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public boolean isInvalidate() {
        return this.invalidate;
    }
    
    public void setInvalidate(final boolean invalidate) {
        this.invalidate = invalidate;
    }
    
    public void addTableIUD(final TransactionEventTable.TableIUD tableIud) {
        if (this.tableList == null) {
            this.tableList = new ArrayList<TransactionEventTable.TableIUD>(4);
        }
        this.tableList.add(tableIud);
    }
    
    public List<TransactionEventTable.TableIUD> getTableList() {
        return this.tableList;
    }
    
    public void setTableList(final List<TransactionEventTable.TableIUD> tableList) {
        this.tableList = tableList;
    }
    
    public BeanPersistIds getBeanPersistIds() {
        return this.beanPersistIds;
    }
    
    public void setBeanPersistIds(final BeanPersistIds beanPersistIds) {
        this.beanPersistIds = beanPersistIds;
    }
    
    public BeanPersistIds getDeleteIds() {
        return this.deleteIds;
    }
    
    public void setDeleteIds(final BeanPersistIds deleteIds) {
        this.deleteIds = deleteIds;
    }
    
    public BeanDeltaList getDeltaList() {
        return this.deltaList;
    }
    
    public void setDeltaList(final BeanDeltaList deltaList) {
        this.deltaList = deltaList;
    }
}
