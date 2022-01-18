// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.util.ValueUtil;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import com.avaje.ebeaninternal.server.deploy.BeanFkeyProperty;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;

public final class ImportedIdSimple implements ImportedId, Comparable<ImportedIdSimple>
{
    private static final EntryComparator COMPARATOR;
    protected final BeanPropertyAssoc<?> owner;
    protected final String localDbColumn;
    protected final String logicalName;
    protected final BeanProperty foreignProperty;
    protected final int position;
    
    public ImportedIdSimple(final BeanPropertyAssoc<?> owner, final String localDbColumn, final BeanProperty foreignProperty, final int position) {
        this.owner = owner;
        this.localDbColumn = InternString.intern(localDbColumn);
        this.foreignProperty = foreignProperty;
        this.position = position;
        this.logicalName = InternString.intern(owner.getName() + "." + foreignProperty.getName());
    }
    
    public static ImportedIdSimple[] sort(final List<ImportedIdSimple> list) {
        final ImportedIdSimple[] importedIds = list.toArray(new ImportedIdSimple[list.size()]);
        Arrays.sort(importedIds, ImportedIdSimple.COMPARATOR);
        return importedIds;
    }
    
    public boolean equals(final Object obj) {
        return obj == this;
    }
    
    public int compareTo(final ImportedIdSimple other) {
        return (this.position < other.position) ? -1 : ((this.position == other.position) ? 0 : 1);
    }
    
    public void addFkeys(final String name) {
        final BeanFkeyProperty fkey = new BeanFkeyProperty(null, name + "." + this.foreignProperty.getName(), this.localDbColumn, this.owner.getDeployOrder());
        this.owner.getBeanDescriptor().add(fkey);
    }
    
    public boolean isScalar() {
        return true;
    }
    
    public String getLogicalName() {
        return this.logicalName;
    }
    
    public String getDbColumn() {
        return this.localDbColumn;
    }
    
    private Object getIdValue(final Object bean) {
        return this.foreignProperty.getValueWithInheritance(bean);
    }
    
    public void buildImport(final IntersectionRow row, final Object other) {
        final Object value = this.getIdValue(other);
        if (value == null) {
            final String msg = "Foreign Key value null?";
            throw new PersistenceException(msg);
        }
        row.put(this.localDbColumn, value);
    }
    
    public void sqlAppend(final DbSqlContext ctx) {
        ctx.appendColumn(this.localDbColumn);
    }
    
    public void dmlAppend(final GenerateDmlRequest request) {
        request.appendColumn(this.localDbColumn);
    }
    
    public void dmlWhere(final GenerateDmlRequest request, final Object bean) {
        if (this.owner.isDbUpdatable()) {
            Object value = null;
            if (bean != null) {
                value = this.getIdValue(bean);
            }
            if (value == null) {
                request.appendColumnIsNull(this.localDbColumn);
            }
            else {
                request.appendColumn(this.localDbColumn);
            }
        }
    }
    
    public boolean hasChanged(final Object bean, final Object oldValues) {
        final Object id = this.getIdValue(bean);
        if (oldValues != null) {
            final Object oldId = this.getIdValue(oldValues);
            return !ValueUtil.areEqual(id, oldId);
        }
        return true;
    }
    
    public void bind(final BindableRequest request, final Object bean, final boolean bindNull) throws SQLException {
        Object value = null;
        if (bean != null) {
            value = this.getIdValue(bean);
        }
        request.bind(value, this.foreignProperty, this.localDbColumn, bindNull);
    }
    
    public BeanProperty findMatchImport(final String matchDbColumn) {
        if (matchDbColumn.equals(this.localDbColumn)) {
            return this.foreignProperty;
        }
        return null;
    }
    
    static {
        COMPARATOR = new EntryComparator();
    }
    
    private static final class EntryComparator implements Comparator<ImportedIdSimple>
    {
        public int compare(final ImportedIdSimple o1, final ImportedIdSimple o2) {
            return o1.compareTo(o2);
        }
    }
}
