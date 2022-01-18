// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.util.ValueUtil;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanFkeyProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;

public class ImportedIdEmbedded implements ImportedId
{
    final BeanPropertyAssoc<?> owner;
    final BeanPropertyAssocOne<?> foreignAssocOne;
    final ImportedIdSimple[] imported;
    
    public ImportedIdEmbedded(final BeanPropertyAssoc<?> owner, final BeanPropertyAssocOne<?> foreignAssocOne, final ImportedIdSimple[] imported) {
        this.owner = owner;
        this.foreignAssocOne = foreignAssocOne;
        this.imported = imported;
    }
    
    public void addFkeys(final String name) {
        final BeanProperty[] embeddedProps = this.foreignAssocOne.getProperties();
        for (int i = 0; i < this.imported.length; ++i) {
            final String n = name + "." + this.foreignAssocOne.getName() + "." + embeddedProps[i].getName();
            final BeanFkeyProperty fkey = new BeanFkeyProperty(null, n, this.imported[i].localDbColumn, this.foreignAssocOne.getDeployOrder());
            this.owner.getBeanDescriptor().add(fkey);
        }
    }
    
    public boolean isScalar() {
        return false;
    }
    
    public String getLogicalName() {
        return this.owner.getName() + "." + this.foreignAssocOne.getName();
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
        Object embeddedId = null;
        if (bean != null) {
            embeddedId = this.foreignAssocOne.getValue(bean);
        }
        if (embeddedId == null) {
            for (int i = 0; i < this.imported.length; ++i) {
                if (this.imported[i].owner.isDbUpdatable()) {
                    request.appendColumnIsNull(this.imported[i].localDbColumn);
                }
            }
        }
        else {
            for (int i = 0; i < this.imported.length; ++i) {
                if (this.imported[i].owner.isDbUpdatable()) {
                    final Object value = this.imported[i].foreignProperty.getValue(embeddedId);
                    if (value == null) {
                        request.appendColumnIsNull(this.imported[i].localDbColumn);
                    }
                    else {
                        request.appendColumn(this.imported[i].localDbColumn);
                    }
                }
            }
        }
    }
    
    public boolean hasChanged(final Object bean, final Object oldValues) {
        final Object id = this.foreignAssocOne.getValue(bean);
        final Object oldId = this.foreignAssocOne.getValue(oldValues);
        return !ValueUtil.areEqual(id, oldId);
    }
    
    public void bind(final BindableRequest request, final Object bean, final boolean bindNull) throws SQLException {
        Object embeddedId = null;
        if (bean != null) {
            embeddedId = this.foreignAssocOne.getValue(bean);
        }
        if (embeddedId == null) {
            for (int i = 0; i < this.imported.length; ++i) {
                if (this.imported[i].owner.isUpdateable()) {
                    request.bind(null, this.imported[i].foreignProperty, this.imported[i].localDbColumn, true);
                }
            }
        }
        else {
            for (int i = 0; i < this.imported.length; ++i) {
                if (this.imported[i].owner.isUpdateable()) {
                    final Object scalarValue = this.imported[i].foreignProperty.getValue(embeddedId);
                    request.bind(scalarValue, this.imported[i].foreignProperty, this.imported[i].localDbColumn, true);
                }
            }
        }
    }
    
    public void buildImport(final IntersectionRow row, final Object other) {
        final Object embeddedId = this.foreignAssocOne.getValue(other);
        if (embeddedId == null) {
            final String msg = "Foreign Key value null?";
            throw new PersistenceException(msg);
        }
        for (int i = 0; i < this.imported.length; ++i) {
            final Object scalarValue = this.imported[i].foreignProperty.getValue(embeddedId);
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
