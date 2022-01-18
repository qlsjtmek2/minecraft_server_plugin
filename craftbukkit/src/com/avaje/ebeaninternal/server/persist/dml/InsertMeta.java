// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableDiscriminator;
import java.util.Set;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;

public final class InsertMeta
{
    private final String sqlNullId;
    private final String sqlWithId;
    private final BindableId id;
    private final Bindable discriminator;
    private final Bindable all;
    private final boolean supportsGetGeneratedKeys;
    private final boolean concatinatedKey;
    private final String tableName;
    private final String selectLastInsertedId;
    private final Bindable shadowFKey;
    private final String[] identityDbColumns;
    private final boolean emptyStringToNull;
    
    public InsertMeta(final DatabasePlatform dbPlatform, final BeanDescriptor<?> desc, final Bindable shadowFKey, final BindableId id, final Bindable all) {
        this.emptyStringToNull = dbPlatform.isTreatEmptyStringsAsNull();
        this.tableName = desc.getBaseTable();
        this.discriminator = getDiscriminator(desc);
        this.id = id;
        this.all = all;
        this.shadowFKey = shadowFKey;
        this.sqlWithId = this.genSql(false, null);
        if (id.isConcatenated()) {
            this.concatinatedKey = true;
            this.identityDbColumns = null;
            this.sqlNullId = null;
            this.supportsGetGeneratedKeys = false;
            this.selectLastInsertedId = null;
        }
        else {
            this.concatinatedKey = false;
            this.identityDbColumns = new String[] { id.getIdentityColumn() };
            this.sqlNullId = this.genSql(true, null);
            this.supportsGetGeneratedKeys = dbPlatform.getDbIdentity().isSupportsGetGeneratedKeys();
            this.selectLastInsertedId = desc.getSelectLastInsertedId();
        }
    }
    
    private static Bindable getDiscriminator(final BeanDescriptor<?> desc) {
        final InheritInfo inheritInfo = desc.getInheritInfo();
        if (inheritInfo != null) {
            return new BindableDiscriminator(inheritInfo);
        }
        return null;
    }
    
    public boolean isEmptyStringToNull() {
        return this.emptyStringToNull;
    }
    
    public boolean isConcatinatedKey() {
        return this.concatinatedKey;
    }
    
    public String[] getIdentityDbColumns() {
        return this.identityDbColumns;
    }
    
    public String getSelectLastInsertedId() {
        return this.selectLastInsertedId;
    }
    
    public boolean supportsGetGeneratedKeys() {
        return this.supportsGetGeneratedKeys;
    }
    
    public boolean deriveConcatenatedId(final PersistRequestBean<?> persist) {
        return this.id.deriveConcatenatedId(persist);
    }
    
    public void bind(final DmlHandler request, final Object bean, final boolean withId) throws SQLException {
        if (withId) {
            this.id.dmlBind(request, false, bean);
        }
        if (this.shadowFKey != null) {
            this.shadowFKey.dmlBind(request, false, bean);
        }
        if (this.discriminator != null) {
            this.discriminator.dmlBind(request, false, bean);
        }
        this.all.dmlBind(request, false, bean);
    }
    
    public String getSql(final boolean withId) {
        if (withId) {
            return this.sqlWithId;
        }
        return this.sqlNullId;
    }
    
    private String genSql(final boolean nullId, final Set<String> loadedProps) {
        final GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringToNull, loadedProps, null);
        request.setInsertSetMode();
        request.append("insert into ").append(this.tableName);
        request.append(" (");
        if (!nullId) {
            this.id.dmlInsert(request, false);
        }
        if (this.shadowFKey != null) {
            this.shadowFKey.dmlInsert(request, false);
        }
        if (this.discriminator != null) {
            this.discriminator.dmlInsert(request, false);
        }
        this.all.dmlInsert(request, false);
        request.append(") values (");
        request.append(request.getInsertBindBuffer());
        request.append(")");
        return request.toString();
    }
}
