// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.sql.SQLException;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.Arrays;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

public class BindableIdEmbedded implements BindableId
{
    private final BeanPropertyAssocOne<?> embId;
    private final BeanProperty[] props;
    private final MatchedImportedProperty[] matches;
    
    public BindableIdEmbedded(final BeanPropertyAssocOne<?> embId, final BeanDescriptor<?> desc) {
        this.embId = embId;
        this.props = embId.getTargetDescriptor().propertiesBaseScalar();
        this.matches = MatchedImportedProperty.build(this.props, desc);
    }
    
    public boolean isConcatenated() {
        return true;
    }
    
    public String getIdentityColumn() {
        return null;
    }
    
    public String toString() {
        return this.embId + " props:" + Arrays.toString(this.props);
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, true);
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, false);
    }
    
    private void dmlBind(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean, final boolean bindNull) throws SQLException {
        if (checkIncludes && !bindRequest.isIncluded(this.embId)) {
            return;
        }
        final Object idValue = this.embId.getValue(bean);
        for (int i = 0; i < this.props.length; ++i) {
            final Object value = this.props[i].getValue(idValue);
            bindRequest.bind(value, this.props[i], this.props[i].getDbColumn(), bindNull);
        }
        bindRequest.setIdValue(idValue);
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        if (checkIncludes && !request.isIncluded(this.embId)) {
            return;
        }
        this.dmlAppend(request, false);
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.embId)) {
            return;
        }
        for (int i = 0; i < this.props.length; ++i) {
            request.appendColumn(this.props[i].getDbColumn());
        }
    }
    
    public boolean deriveConcatenatedId(final PersistRequestBean<?> persist) {
        if (this.matches == null) {
            final String m = "Matches for the concatinated key columns where not found? I expect that the concatinated key was null, and this bean does not have ManyToOne assoc beans matching the primary key columns?";
            throw new PersistenceException(m);
        }
        final Object bean = persist.getBean();
        final Object newId = this.embId.createEmbeddedId();
        for (int i = 0; i < this.matches.length; ++i) {
            this.matches[i].populate(bean, newId);
        }
        this.embId.setValueIntercept(bean, newId);
        return true;
    }
}
