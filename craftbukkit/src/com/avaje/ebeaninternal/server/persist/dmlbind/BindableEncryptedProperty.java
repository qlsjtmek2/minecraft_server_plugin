// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class BindableEncryptedProperty implements Bindable
{
    private final BeanProperty prop;
    private final boolean bindEncryptDataFirst;
    
    public BindableEncryptedProperty(final BeanProperty prop, final boolean bindEncryptDataFirst) {
        this.prop = prop;
        this.bindEncryptDataFirst = bindEncryptDataFirst;
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
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        request.appendColumn(this.prop.getDbColumn(), this.prop.getDbBind());
    }
    
    public void dmlAppend(final GenerateDmlRequest request, final boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        request.appendColumn(this.prop.getDbColumn(), "=", this.prop.getDbBind());
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final boolean checkIncludes, final Object bean) {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        if (bean == null || request.isDbNull(this.prop.getValue(bean))) {
            request.appendColumnIsNull(this.prop.getDbColumn());
        }
        else {
            request.appendColumn("? = ", this.prop.getDecryptSql());
        }
    }
    
    public void dmlBind(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        Object value = null;
        if (bean != null) {
            value = this.prop.getValue(bean);
        }
        final String encryptKeyValue = this.prop.getEncryptKey().getStringValue();
        if (!this.bindEncryptDataFirst) {
            request.bindNoLog(encryptKeyValue, 12, this.prop.getName() + "=****");
        }
        request.bindNoLog(value, this.prop, this.prop.getName(), true);
        if (this.bindEncryptDataFirst) {
            request.bindNoLog(encryptKeyValue, 12, this.prop.getName() + "=****");
        }
    }
    
    public void dmlBindWhere(final BindableRequest request, final boolean checkIncludes, final Object bean) throws SQLException {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        Object value = null;
        if (bean != null) {
            value = this.prop.getValue(bean);
        }
        final String encryptKeyValue = this.prop.getEncryptKey().getStringValue();
        request.bind(value, this.prop, this.prop.getName(), false);
        request.bindNoLog(encryptKeyValue, 12, this.prop.getName() + "=****");
    }
}
