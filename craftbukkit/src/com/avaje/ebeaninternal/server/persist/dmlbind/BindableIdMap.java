// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import javax.persistence.PersistenceException;
import java.util.LinkedHashMap;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.Arrays;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class BindableIdMap implements BindableId
{
    private final BeanProperty[] uids;
    private final MatchedImportedProperty[] matches;
    
    public BindableIdMap(final BeanProperty[] uids, final BeanDescriptor<?> desc) {
        this.uids = uids;
        this.matches = MatchedImportedProperty.build(uids, desc);
    }
    
    public boolean isConcatenated() {
        return true;
    }
    
    public String getIdentityColumn() {
        return null;
    }
    
    public String toString() {
        return Arrays.toString(this.uids);
    }
    
    public void addChanged(final PersistRequestBean<?> request, final List<Bindable> list) {
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        this.dmlAppend(request, false);
    }
    
    public void dmlInsert(final GenerateDmlRequest request, final boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        for (int i = 0; i < this.uids.length; ++i) {
            request.appendColumn(this.uids[i].getDbColumn());
        }
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, true);
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, false);
    }
    
    private void dmlBind(final BindableRequest bindRequest, final boolean checkIncludes, final Object bean, final boolean bindNull) throws SQLException {
        final LinkedHashMap<String, Object> mapId = new LinkedHashMap<String, Object>();
        for (int i = 0; i < this.uids.length; ++i) {
            final Object value = this.uids[i].getValue(bean);
            bindRequest.bind(value, this.uids[i], this.uids[i].getName(), bindNull);
            mapId.put(this.uids[i].getName(), value);
        }
        bindRequest.setIdValue(mapId);
    }
    
    public boolean deriveConcatenatedId(final PersistRequestBean<?> persist) {
        if (this.matches == null) {
            final String m = "Matches for the concatinated key columns where not found? I expect that the concatinated key was null, and this bean does not have ManyToOne assoc beans matching the primary key columns?";
            throw new PersistenceException(m);
        }
        final Object bean = persist.getBean();
        for (int i = 0; i < this.matches.length; ++i) {
            this.matches[i].populate(bean, bean);
        }
        return true;
    }
}
