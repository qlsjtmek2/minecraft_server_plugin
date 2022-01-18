// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.api.SpiQuery;
import java.util.Set;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;

public class SqlBeanLoad
{
    private final DbReadContext ctx;
    private final Object bean;
    private final Class<?> type;
    private final Object originalOldValues;
    private final boolean isLazyLoad;
    private final Set<String> excludes;
    private final boolean setOriginalOldValues;
    private final boolean rawSql;
    
    public SqlBeanLoad(final DbReadContext ctx, final Class<?> type, final Object bean, final SpiQuery.Mode queryMode) {
        this.ctx = ctx;
        this.rawSql = ctx.isRawSql();
        this.type = type;
        this.isLazyLoad = queryMode.equals(SpiQuery.Mode.LAZYLOAD_BEAN);
        this.bean = bean;
        if (bean instanceof EntityBean) {
            final EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
            this.excludes = (this.isLazyLoad ? ebi.getLoadedProps() : null);
            if (this.excludes != null) {
                this.originalOldValues = ebi.getOldValues();
            }
            else {
                this.originalOldValues = null;
            }
            this.setOriginalOldValues = (this.originalOldValues != null);
        }
        else {
            this.excludes = null;
            this.originalOldValues = null;
            this.setOriginalOldValues = false;
        }
    }
    
    public boolean isLazyLoad() {
        return this.isLazyLoad;
    }
    
    public void loadIgnore(final int increment) {
        this.ctx.getDataReader().incrementPos(increment);
    }
    
    public Object load(final BeanProperty prop) throws SQLException {
        if (!this.rawSql && prop.isTransient()) {
            return null;
        }
        if (this.bean == null || (this.excludes != null && this.excludes.contains(prop.getName())) || (this.type != null && !prop.isAssignableFrom(this.type))) {
            prop.loadIgnore(this.ctx);
            return null;
        }
        try {
            final Object dbVal = prop.read(this.ctx);
            if (this.isLazyLoad) {
                prop.setValue(this.bean, dbVal);
            }
            else {
                prop.setValueIntercept(this.bean, dbVal);
            }
            if (this.setOriginalOldValues) {
                prop.setValue(this.originalOldValues, dbVal);
            }
            return dbVal;
        }
        catch (Exception e) {
            final String msg = "Error loading on " + prop.getFullBeanName();
            throw new PersistenceException(msg, e);
        }
    }
    
    public void loadAssocMany(final BeanPropertyAssocMany<?> prop) {
    }
}
