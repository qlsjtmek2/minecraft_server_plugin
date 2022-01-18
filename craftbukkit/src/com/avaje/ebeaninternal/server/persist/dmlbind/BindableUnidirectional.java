// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import javax.persistence.PersistenceException;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

public class BindableUnidirectional implements Bindable
{
    private final BeanPropertyAssocOne<?> unidirectional;
    private final ImportedId importedId;
    private final BeanDescriptor<?> desc;
    
    public BindableUnidirectional(final BeanDescriptor<?> desc, final BeanPropertyAssocOne<?> unidirectional) {
        this.desc = desc;
        this.unidirectional = unidirectional;
        this.importedId = unidirectional.getImportedId();
    }
    
    public String toString() {
        return "BindableShadowFKey " + this.unidirectional;
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
        throw new PersistenceException("Never called (for insert only)");
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.importedId.dmlAppend(request);
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        throw new RuntimeException("Never called");
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, true);
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, false);
    }
    
    private void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean, final boolean bindNull) throws SQLException {
        final PersistRequestBean<?> persistRequest = request.getPersistRequest();
        final Object parentBean = persistRequest.getParentBean();
        if (parentBean == null) {
            final Class<?> localType = this.desc.getBeanType();
            final Class<?> targetType = this.unidirectional.getTargetType();
            String msg = "Error inserting bean [" + localType + "] with unidirectional relationship. ";
            msg = msg + "For inserts you must use cascade save on the master bean [" + targetType + "].";
            throw new PersistenceException(msg);
        }
        this.importedId.bind(request, parentBean, bindNull);
    }
}
