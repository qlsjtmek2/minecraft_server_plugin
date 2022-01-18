// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import javax.persistence.PersistenceException;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class BindableIdScalar implements BindableId
{
    private final BeanProperty uidProp;
    
    public BindableIdScalar(final BeanProperty uidProp) {
        this.uidProp = uidProp;
    }
    
    public boolean isConcatenated() {
        return false;
    }
    
    public String getIdentityColumn() {
        return this.uidProp.getDbColumn();
    }
    
    public String toString() {
        return this.uidProp.toString();
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
    }
    
    public boolean deriveConcatenatedId(final PersistRequestBean<?> persist) {
        throw new PersistenceException("Should not be called? only for concatinated keys");
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        request.appendColumn(this.uidProp.getDbColumn());
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        request.appendColumn(this.uidProp.getDbColumn());
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, true);
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, false);
    }
    
    private void dmlBind(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean, final boolean bindNull) throws SQLException {
        final Object value = this.uidProp.getValue(bean);
        bindRequest.bind(value, this.uidProp, this.uidProp.getName(), bindNull);
        bindRequest.setIdValue(value);
    }
}
