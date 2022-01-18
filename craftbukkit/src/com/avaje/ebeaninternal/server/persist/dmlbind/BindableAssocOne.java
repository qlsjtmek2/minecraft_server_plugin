// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

public class BindableAssocOne implements Bindable
{
    private final BeanPropertyAssocOne<?> assocOne;
    private final ImportedId importedId;
    
    public BindableAssocOne(final BeanPropertyAssocOne<?> assocOne) {
        this.assocOne = assocOne;
        this.importedId = assocOne.getImportedId();
    }
    
    public String toString() {
        return "BindableAssocOne " + this.assocOne;
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        if (request.hasChanged(this.assocOne)) {
            list.add(this);
        }
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.assocOne)) {
            return;
        }
        this.importedId.dmlAppend(request);
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        if (checkIncludes && !request.isIncludedWhere(this.assocOne)) {
            return;
        }
        final Object assocBean = this.assocOne.getValue(bean);
        this.importedId.dmlWhere(request, assocBean);
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !request.isIncluded(this.assocOne)) {
            return;
        }
        this.dmlBind(request, bean, true);
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !request.isIncludedWhere(this.assocOne)) {
            return;
        }
        this.dmlBind(request, bean, false);
    }
    
    private void dmlBind(final BindableRequest request, final Object bean, final boolean bindNull) throws SQLException {
        final Object assocBean = this.assocOne.getValue(bean);
        this.importedId.bind(request, assocBean, bindNull);
    }
}
