// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.sql.SQLException;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;

public class BindablePropertyUpdateGenerated extends BindableProperty
{
    private final GeneratedProperty gen;
    
    public BindablePropertyUpdateGenerated(final BeanProperty prop, final GeneratedProperty gen) {
        super(prop);
        this.gen = gen;
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        list.add(this);
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
        final Object value = this.gen.getUpdateValue(this.prop, bean);
        request.bind(value, this.prop, this.prop.getName(), bindNull);
        if (request.isIncluded(this.prop)) {
            request.registerUpdateGenValue(this.prop, bean, value);
        }
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        request.appendColumn(this.prop.getDbColumn());
    }
}
