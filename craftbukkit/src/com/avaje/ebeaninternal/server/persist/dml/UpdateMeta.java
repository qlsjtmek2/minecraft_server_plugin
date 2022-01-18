// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.api.SpiUpdatePlan;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;

public final class UpdateMeta
{
    private final String sqlVersion;
    private final String sqlNone;
    private final Bindable set;
    private final Bindable id;
    private final Bindable version;
    private final Bindable all;
    private final String tableName;
    private final UpdatePlan modeNoneUpdatePlan;
    private final UpdatePlan modeVersionUpdatePlan;
    private final boolean emptyStringAsNull;
    
    public UpdateMeta(final boolean emptyStringAsNull, final BeanDescriptor<?> desc, final Bindable set, final Bindable id, final Bindable version, final Bindable all) {
        this.emptyStringAsNull = emptyStringAsNull;
        this.tableName = desc.getBaseTable();
        this.set = set;
        this.id = id;
        this.version = version;
        this.all = all;
        this.sqlNone = this.genSql(ConcurrencyMode.NONE, null, null);
        this.sqlVersion = this.genSql(ConcurrencyMode.VERSION, null, null);
        this.modeNoneUpdatePlan = new UpdatePlan(ConcurrencyMode.NONE, this.sqlNone, set);
        this.modeVersionUpdatePlan = new UpdatePlan(ConcurrencyMode.VERSION, this.sqlVersion, set);
    }
    
    public boolean isEmptyStringAsNull() {
        return this.emptyStringAsNull;
    }
    
    public String getTableName() {
        return this.tableName;
    }
    
    public void bind(final PersistRequestBean<?> persist, final DmlHandler bind, final SpiUpdatePlan updatePlan) throws SQLException {
        final Object bean = persist.getBean();
        bind.bindLogAppend(" set[");
        bind.setCheckDelta(true);
        updatePlan.bindSet(bind, bean);
        bind.setCheckDelta(false);
        bind.bindLogAppend("] where[");
        this.id.dmlBind(bind, false, bean);
        switch (persist.getConcurrencyMode()) {
            case VERSION: {
                this.version.dmlBind(bind, false, bean);
                break;
            }
            case ALL: {
                final Object oldBean = persist.getOldValues();
                this.all.dmlBindWhere(bind, true, oldBean);
                break;
            }
        }
    }
    
    public SpiUpdatePlan getUpdatePlan(final PersistRequestBean<?> request) {
        final ConcurrencyMode mode = request.determineConcurrencyMode();
        if (request.isDynamicUpdateSql()) {
            return this.getDynamicUpdatePlan(mode, request);
        }
        switch (mode) {
            case NONE: {
                return this.modeNoneUpdatePlan;
            }
            case VERSION: {
                return this.modeVersionUpdatePlan;
            }
            case ALL: {
                final Object oldValues = request.getOldValues();
                if (oldValues == null) {
                    throw new PersistenceException("OldValues are null?");
                }
                final String sql = this.genDynamicWhere(request.getUpdatedProperties(), request.getLoadedProperties(), oldValues);
                return new UpdatePlan(ConcurrencyMode.ALL, sql, this.set);
            }
            default: {
                throw new RuntimeException("Invalid mode " + mode);
            }
        }
    }
    
    private SpiUpdatePlan getDynamicUpdatePlan(final ConcurrencyMode mode, final PersistRequestBean<?> persistRequest) {
        final Set<String> updatedProps = persistRequest.getUpdatedProperties();
        if (ConcurrencyMode.ALL.equals(mode)) {
            final String sql = this.genSql(mode, persistRequest, null);
            if (sql == null) {
                return UpdatePlan.EMPTY_SET_CLAUSE;
            }
            return new UpdatePlan(null, mode, sql, this.set, updatedProps);
        }
        else {
            int hash = mode.hashCode();
            hash = hash * 31 + ((updatedProps == null) ? 0 : updatedProps.hashCode());
            final Integer key = hash;
            final BeanDescriptor<?> beanDescriptor = persistRequest.getBeanDescriptor();
            SpiUpdatePlan updatePlan = beanDescriptor.getUpdatePlan(key);
            if (updatePlan != null) {
                return updatePlan;
            }
            final List<Bindable> list = new ArrayList<Bindable>();
            this.set.addChanged(persistRequest, list);
            final BindableList bindableList = new BindableList(list);
            final String sql2 = this.genSql(mode, persistRequest, bindableList);
            updatePlan = new UpdatePlan(key, mode, sql2, bindableList, null);
            beanDescriptor.putUpdatePlan(key, updatePlan);
            return updatePlan;
        }
    }
    
    private String genSql(final ConcurrencyMode conMode, final PersistRequestBean<?> persistRequest, final BindableList bindableList) {
        GenerateDmlRequest request;
        if (persistRequest == null) {
            request = new GenerateDmlRequest(this.emptyStringAsNull);
        }
        else {
            request = persistRequest.createGenerateDmlRequest(this.emptyStringAsNull);
        }
        request.append("update ").append(this.tableName).append(" set ");
        request.setUpdateSetMode();
        if (bindableList != null) {
            bindableList.dmlAppend(request, false);
        }
        else {
            this.set.dmlAppend(request, true);
        }
        if (request.getBindColumnCount() == 0) {
            return null;
        }
        request.append(" where ");
        request.setWhereIdMode();
        this.id.dmlAppend(request, false);
        if (ConcurrencyMode.VERSION.equals(conMode)) {
            if (this.version == null) {
                return null;
            }
            this.version.dmlAppend(request, false);
        }
        else if (ConcurrencyMode.ALL.equals(conMode)) {
            this.all.dmlWhere(request, true, request.getOldValues());
        }
        return request.toString();
    }
    
    private String genDynamicWhere(final Set<String> loadedProps, final Set<String> whereProps, final Object oldBean) {
        final GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull, loadedProps, whereProps, oldBean);
        request.append(this.sqlNone);
        request.setWhereMode();
        this.all.dmlWhere(request, true, oldBean);
        return request.toString();
    }
}
