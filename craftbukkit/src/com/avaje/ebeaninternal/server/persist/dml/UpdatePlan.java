// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.util.Set;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.api.SpiUpdatePlan;

public class UpdatePlan implements SpiUpdatePlan
{
    public static final UpdatePlan EMPTY_SET_CLAUSE;
    private final Integer key;
    private final ConcurrencyMode mode;
    private final String sql;
    private final Bindable set;
    private final Set<String> properties;
    private final boolean checkIncludes;
    private final long timeCreated;
    private final boolean emptySetClause;
    private Long timeLastUsed;
    
    public UpdatePlan(final ConcurrencyMode mode, final String sql, final Bindable set) {
        this(null, mode, sql, set, null);
    }
    
    public UpdatePlan(final Integer key, final ConcurrencyMode mode, final String sql, final Bindable set, final Set<String> properties) {
        this.emptySetClause = false;
        this.key = key;
        this.mode = mode;
        this.sql = sql;
        this.set = set;
        this.properties = properties;
        this.checkIncludes = (properties != null);
        this.timeCreated = System.currentTimeMillis();
    }
    
    private UpdatePlan() {
        this.emptySetClause = true;
        this.key = 0;
        this.mode = ConcurrencyMode.NONE;
        this.sql = null;
        this.set = null;
        this.properties = null;
        this.checkIncludes = false;
        this.timeCreated = 0L;
    }
    
    public boolean isEmptySetClause() {
        return this.emptySetClause;
    }
    
    public void bindSet(final DmlHandler bind, final Object bean) throws SQLException {
        this.set.dmlBind(bind, this.checkIncludes, bean);
        final Long touched = System.currentTimeMillis();
        this.timeLastUsed = touched;
    }
    
    public long getTimeCreated() {
        return this.timeCreated;
    }
    
    public Long getTimeLastUsed() {
        return this.timeLastUsed;
    }
    
    public Integer getKey() {
        return this.key;
    }
    
    public ConcurrencyMode getMode() {
        return this.mode;
    }
    
    public String getSql() {
        return this.sql;
    }
    
    public Bindable getSet() {
        return this.set;
    }
    
    public Set<String> getProperties() {
        return this.properties;
    }
    
    static {
        EMPTY_SET_CLAUSE = new UpdatePlan();
    }
}
