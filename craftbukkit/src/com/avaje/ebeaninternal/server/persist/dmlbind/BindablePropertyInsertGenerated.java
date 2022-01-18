// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;

public class BindablePropertyInsertGenerated extends BindableProperty
{
    private final GeneratedProperty gen;
    
    public BindablePropertyInsertGenerated(final BeanProperty prop, final GeneratedProperty gen) {
        super(prop);
        this.gen = gen;
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, true);
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, false);
    }
    
    private void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean, final boolean bindNull) throws SQLException {
        final Object value = this.gen.getInsertValue(this.prop, bean);
        if (bean != null) {
            this.prop.setValueIntercept(bean, value);
            request.registerAdditionalProperty(this.prop.getName());
        }
        request.bind(value, this.prop, this.prop.getName(), bindNull);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        request.appendColumn(this.prop.getDbColumn());
    }
}
