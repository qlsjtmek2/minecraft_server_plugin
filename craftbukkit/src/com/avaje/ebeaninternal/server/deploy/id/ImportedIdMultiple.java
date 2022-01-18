// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.util.ValueUtil;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;

public class ImportedIdMultiple implements ImportedId
{
    final BeanPropertyAssoc<?> owner;
    final ImportedIdSimple[] imported;
    
    public ImportedIdMultiple(final BeanPropertyAssoc<?> owner, final ImportedIdSimple[] imported) {
        this.owner = owner;
        this.imported = imported;
    }
    
    public void addFkeys(final String name) {
    }
    
    public String getLogicalName() {
        return null;
    }
    
    public boolean isScalar() {
        return false;
    }
    
    public String getDbColumn() {
        return null;
    }
    
    public void sqlAppend(final DbSqlContext ctx) {
        for (int i = 0; i < this.imported.length; ++i) {
            ctx.appendColumn(this.imported[i].localDbColumn);
        }
    }
    
    public void dmlAppend(final GenerateDmlRequest request) {
        for (int i = 0; i < this.imported.length; ++i) {
            request.appendColumn(this.imported[i].localDbColumn);
        }
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final Object bean) {
        if (bean == null) {
            for (int i = 0; i < this.imported.length; ++i) {
                request.appendColumnIsNull(this.imported[i].localDbColumn);
            }
        }
        else {
            for (int i = 0; i < this.imported.length; ++i) {
                final Object value = this.imported[i].foreignProperty.getValue(bean);
                if (value == null) {
                    request.appendColumnIsNull(this.imported[i].localDbColumn);
                }
                else {
                    request.appendColumn(this.imported[i].localDbColumn);
                }
            }
        }
    }
    
    public boolean hasChanged(final Object bean, final Object oldValues) {
        for (int i = 0; i < this.imported.length; ++i) {
            final Object id = this.imported[i].foreignProperty.getValue(bean);
            final Object oldId = this.imported[i].foreignProperty.getValue(oldValues);
            if (!ValueUtil.areEqual(id, oldId)) {
                return true;
            }
        }
        return false;
    }
    
    public void bind(final BindableRequest request, final Object bean, final boolean bindNull) throws SQLException {
        for (int i = 0; i < this.imported.length; ++i) {
            if (this.imported[i].owner.isUpdateable()) {
                final Object scalarValue = this.imported[i].foreignProperty.getValue(bean);
                request.bind(scalarValue, this.imported[i].foreignProperty, this.imported[i].localDbColumn, true);
            }
        }
    }
    
    public void buildImport(final IntersectionRow row, final Object other) {
        for (int i = 0; i < this.imported.length; ++i) {
            final Object scalarValue = this.imported[i].foreignProperty.getValue(other);
            row.put(this.imported[i].localDbColumn, scalarValue);
        }
    }
    
    public BeanProperty findMatchImport(final String matchDbColumn) {
        BeanProperty p = null;
        for (int i = 0; i < this.imported.length; ++i) {
            p = this.imported[i].findMatchImport(matchDbColumn);
            if (p != null) {
                return p;
            }
        }
        return p;
    }
}
