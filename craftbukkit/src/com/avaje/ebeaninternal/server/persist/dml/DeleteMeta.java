// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import java.util.Set;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;

public final class DeleteMeta
{
    private final String sqlVersion;
    private final String sqlNone;
    private final Bindable id;
    private final Bindable version;
    private final Bindable all;
    private final String tableName;
    private final boolean emptyStringAsNull;
    
    public DeleteMeta(final boolean emptyStringAsNull, final BeanDescriptor<?> desc, final Bindable id, final Bindable version, final Bindable all) {
        this.emptyStringAsNull = emptyStringAsNull;
        this.tableName = desc.getBaseTable();
        this.id = id;
        this.version = version;
        this.all = all;
        this.sqlNone = this.genSql(ConcurrencyMode.NONE);
        this.sqlVersion = this.genSql(ConcurrencyMode.VERSION);
    }
    
    public boolean isEmptyStringAsNull() {
        return this.emptyStringAsNull;
    }
    
    public String getTableName() {
        return this.tableName;
    }
    
    public void bind(final PersistRequestBean<?> persist, final DmlHandler bind) throws SQLException {
        final Object bean = persist.getBean();
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
    
    public String getSql(final PersistRequestBean<?> request) throws SQLException {
        switch (request.determineConcurrencyMode()) {
            case NONE: {
                return this.sqlNone;
            }
            case VERSION: {
                return this.sqlVersion;
            }
            case ALL: {
                return this.genDynamicWhere(request.getLoadedProperties(), request.getOldValues());
            }
            default: {
                throw new RuntimeException("Invalid mode " + request.determineConcurrencyMode());
            }
        }
    }
    
    private String genSql(final ConcurrencyMode conMode) {
        final GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull);
        request.append("delete from ").append(this.tableName);
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
            throw new RuntimeException("Never called for ConcurrencyMode.ALL");
        }
        return request.toString();
    }
    
    private String genDynamicWhere(final Set<String> includedProps, final Object oldBean) throws SQLException {
        final GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull, includedProps, oldBean);
        request.append(this.sqlNone);
        request.setWhereMode();
        this.all.dmlWhere(request, true, oldBean);
        return request.toString();
    }
}
