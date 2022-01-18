// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebean.bean.EntityBean;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.util.Arrays;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

public class BindableEmbedded implements Bindable
{
    private final Bindable[] items;
    private final BeanPropertyAssocOne<?> embProp;
    
    public BindableEmbedded(final BeanPropertyAssocOne<?> embProp, final List<Bindable> list) {
        this.embProp = embProp;
        this.items = list.toArray(new Bindable[list.size()]);
    }
    
    public String toString() {
        return "BindableEmbedded " + this.embProp + " items:" + Arrays.toString(this.items);
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.embProp)) {
            return;
        }
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlAppend(request, false);
        }
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object origBean) {
        if (checkIncludes && !request.isIncludedWhere(this.embProp)) {
            return;
        }
        final Object embBean = this.embProp.getValue(origBean);
        final Object oldValues = this.getOldValue(embBean);
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlWhere(request, false, oldValues);
        }
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        if (request.hasChanged(this.embProp)) {
            list.add(this);
        }
    }
    
    public void dmlBind(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !bindRequest.isIncluded(this.embProp)) {
            return;
        }
        final Object embBean = this.embProp.getValue(bean);
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBind(bindRequest, false, embBean);
        }
    }
    
    public void dmlBindWhere(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !bindRequest.isIncludedWhere(this.embProp)) {
            return;
        }
        final Object embBean = this.embProp.getValue(bean);
        final Object oldEmbBean = this.getOldValue(embBean);
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBindWhere(bindRequest, false, oldEmbBean);
        }
    }
    
    private Object getOldValue(final Object embBean) {
        Object oldValues = null;
        if (embBean instanceof EntityBean) {
            oldValues = ((EntityBean)embBean)._ebean_getIntercept().getOldValues();
        }
        if (oldValues == null) {
            oldValues = embBean;
        }
        return oldValues;
    }
}
