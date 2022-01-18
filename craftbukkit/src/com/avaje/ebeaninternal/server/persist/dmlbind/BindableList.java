// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.List;

public class BindableList implements Bindable
{
    private final Bindable[] items;
    
    public BindableList(final List<Bindable> list) {
        this.items = list.toArray(new Bindable[list.size()]);
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].addChanged(request, list);
        }
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlInsert(request, checkIncludes);
        }
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlAppend(request, checkIncludes);
        }
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlWhere(request, checkIncludes, bean);
        }
    }
    
    public void dmlBind(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBind(bindRequest, checkIncludes, bean);
        }
    }
    
    public void dmlBindWhere(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBindWhere(bindRequest, checkIncludes, bean);
        }
    }
}
