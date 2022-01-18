// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class BindableProperty implements Bindable
{
    protected final BeanProperty prop;
    
    public BindableProperty(final BeanProperty prop) {
        this.prop = prop;
    }
    
    public String toString() {
        return this.prop.toString();
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        if (request.hasChanged(this.prop)) {
            list.add(this);
        }
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        request.appendColumn(this.prop.getDbColumn());
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        if (checkIncludes && !request.isIncludedWhere(this.prop)) {
            return;
        }
        if (bean == null || request.isDbNull(this.prop.getValue(bean))) {
            request.appendColumnIsNull(this.prop.getDbColumn());
        }
        else {
            request.appendColumn(this.prop.getDbColumn());
        }
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        this.dmlBind(request, bean, true);
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !request.isIncludedWhere(this.prop)) {
            return;
        }
        this.dmlBind(request, bean, false);
    }
    
    private void dmlBind(final BindableRequest request, final Object bean, final boolean bindNull) throws SQLException {
        Object value = null;
        if (bean != null) {
            value = this.prop.getValue(bean);
        }
        request.bind(value, this.prop, this.prop.getName(), bindNull);
    }
}
