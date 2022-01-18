// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.util.Arrays;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;

public class BindableCompound implements Bindable
{
    private final Bindable[] items;
    private final BeanPropertyCompound compound;
    
    public BindableCompound(final BeanPropertyCompound embProp, final List<Bindable> list) {
        this.compound = embProp;
        this.items = list.toArray(new Bindable[list.size()]);
    }
    
    public String toString() {
        return "BindableCompound " + this.compound + " items:" + Arrays.toString(this.items);
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.compound)) {
            return;
        }
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlAppend(request, false);
        }
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object origBean) {
        if (checkIncludes && !request.isIncludedWhere(this.compound)) {
            return;
        }
        final Object valueObject = this.compound.getValue(origBean);
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlWhere(request, false, valueObject);
        }
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        if (request.hasChanged(this.compound)) {
            list.add(this);
        }
    }
    
    public void dmlBind(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !bindRequest.isIncluded(this.compound)) {
            return;
        }
        final Object valueObject = this.compound.getValue(bean);
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBind(bindRequest, false, valueObject);
        }
    }
    
    public void dmlBindWhere(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !bindRequest.isIncludedWhere(this.compound)) {
            return;
        }
        final Object valueObject = this.compound.getValue(bean);
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBindWhere(bindRequest, false, valueObject);
        }
    }
}
