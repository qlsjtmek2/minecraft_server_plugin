// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import javax.persistence.PersistenceException;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;

public class BindableDiscriminator implements Bindable
{
    private final String columnName;
    private final Object discValue;
    private final int sqlType;
    
    public BindableDiscriminator(final InheritInfo inheritInfo) {
        this.columnName = inheritInfo.getDiscriminatorColumn();
        this.discValue = inheritInfo.getDiscriminatorValue();
        this.sqlType = inheritInfo.getDiscriminatorType();
    }
    
    public String toString() {
        return this.columnName + " = " + this.discValue;
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        throw new PersistenceException("Never called (only for inserts)");
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        request.appendColumn(this.columnName);
    }
    
    public void dmlBind(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        bindRequest.bind(this.columnName, this.discValue, this.sqlType);
    }
    
    public void dmlBindWhere(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean) throws SQLException {
        bindRequest.bind(this.columnName, this.discValue, this.sqlType);
    }
}
