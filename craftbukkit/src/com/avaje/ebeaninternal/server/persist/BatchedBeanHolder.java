// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import java.util.ArrayList;

public class BatchedBeanHolder
{
    private final BatchControl control;
    private final String shortDesc;
    private final int order;
    private ArrayList<PersistRequest> inserts;
    private ArrayList<PersistRequest> updates;
    private ArrayList<PersistRequest> deletes;
    private HashSet<Integer> beanHashCodes;
    
    public BatchedBeanHolder(final BatchControl control, final BeanDescriptor<?> beanDescriptor, final int order) {
        this.beanHashCodes = new HashSet<Integer>();
        this.control = control;
        this.shortDesc = beanDescriptor.getName() + ":" + order;
        this.order = order;
    }
    
    public int getOrder() {
        return this.order;
    }
    
    public void executeNow() {
        if (this.inserts != null) {
            this.control.executeNow(this.inserts);
            this.inserts.clear();
        }
        if (this.updates != null) {
            this.control.executeNow(this.updates);
            this.updates.clear();
        }
        if (this.deletes != null) {
            this.control.executeNow(this.deletes);
            this.deletes.clear();
        }
        this.beanHashCodes.clear();
    }
    
    public String toString() {
        return this.shortDesc;
    }
    
    public ArrayList<PersistRequest> getList(final PersistRequestBean<?> request) {
        final Integer objHashCode = System.identityHashCode(request.getBean());
        if (!this.beanHashCodes.add(objHashCode)) {
            return null;
        }
        switch (request.getType()) {
            case INSERT: {
                if (this.inserts == null) {
                    this.inserts = new ArrayList<PersistRequest>();
                }
                return this.inserts;
            }
            case UPDATE: {
                if (this.updates == null) {
                    this.updates = new ArrayList<PersistRequest>();
                }
                return this.updates;
            }
            case DELETE: {
                if (this.deletes == null) {
                    this.deletes = new ArrayList<PersistRequest>();
                }
                return this.deletes;
            }
            default: {
                throw new RuntimeException("Invalid type code " + request.getType());
            }
        }
    }
}
