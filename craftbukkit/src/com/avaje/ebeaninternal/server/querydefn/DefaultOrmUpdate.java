// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

import com.avaje.ebean.Update;
import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebean.EbeanServer;
import java.io.Serializable;
import com.avaje.ebeaninternal.api.SpiUpdate;

public final class DefaultOrmUpdate<T> implements SpiUpdate<T>, Serializable
{
    private static final long serialVersionUID = -8791423602246515438L;
    private final transient EbeanServer server;
    private final Class<?> beanType;
    private final String name;
    private final BindParams bindParams;
    private final String updateStatement;
    private boolean notifyCache;
    private int timeout;
    private String generatedSql;
    private final String baseTable;
    private final OrmUpdateType type;
    
    public DefaultOrmUpdate(final Class<?> beanType, final EbeanServer server, final String baseTable, final String updateStatement) {
        this.bindParams = new BindParams();
        this.notifyCache = true;
        this.beanType = beanType;
        this.server = server;
        this.baseTable = baseTable;
        this.name = "";
        this.updateStatement = updateStatement;
        this.type = this.deriveType(updateStatement);
    }
    
    public DefaultOrmUpdate(final Class<?> beanType, final EbeanServer server, final String baseTable, final DeployNamedUpdate namedUpdate) {
        this.bindParams = new BindParams();
        this.notifyCache = true;
        this.beanType = beanType;
        this.server = server;
        this.baseTable = baseTable;
        this.name = namedUpdate.getName();
        this.notifyCache = namedUpdate.isNotifyCache();
        this.updateStatement = namedUpdate.getSqlUpdateStatement();
        this.type = this.deriveType(this.updateStatement);
    }
    
    public DefaultOrmUpdate<T> setTimeout(final int secs) {
        this.timeout = secs;
        return this;
    }
    
    public Class<?> getBeanType() {
        return this.beanType;
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    private OrmUpdateType deriveType(String updateStatement) {
        updateStatement = updateStatement.trim();
        final int spacepos = updateStatement.indexOf(32);
        if (spacepos == -1) {
            return OrmUpdateType.UNKNOWN;
        }
        final String firstWord = updateStatement.substring(0, spacepos);
        if (firstWord.equalsIgnoreCase("update")) {
            return OrmUpdateType.UPDATE;
        }
        if (firstWord.equalsIgnoreCase("insert")) {
            return OrmUpdateType.INSERT;
        }
        if (firstWord.equalsIgnoreCase("delete")) {
            return OrmUpdateType.DELETE;
        }
        return OrmUpdateType.UNKNOWN;
    }
    
    public int execute() {
        return this.server.execute(this);
    }
    
    public DefaultOrmUpdate<T> setNotifyCache(final boolean notifyCache) {
        this.notifyCache = notifyCache;
        return this;
    }
    
    public boolean isNotifyCache() {
        return this.notifyCache;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUpdateStatement() {
        return this.updateStatement;
    }
    
    public DefaultOrmUpdate<T> set(final int position, final Object value) {
        this.bindParams.setParameter(position, value);
        return this;
    }
    
    public DefaultOrmUpdate<T> setParameter(final int position, final Object value) {
        this.bindParams.setParameter(position, value);
        return this;
    }
    
    public DefaultOrmUpdate<T> setNull(final int position, final int jdbcType) {
        this.bindParams.setNullParameter(position, jdbcType);
        return this;
    }
    
    public DefaultOrmUpdate<T> setNullParameter(final int position, final int jdbcType) {
        this.bindParams.setNullParameter(position, jdbcType);
        return this;
    }
    
    public DefaultOrmUpdate<T> set(final String name, final Object value) {
        this.bindParams.setParameter(name, value);
        return this;
    }
    
    public DefaultOrmUpdate<T> setParameter(final String name, final Object param) {
        this.bindParams.setParameter(name, param);
        return this;
    }
    
    public DefaultOrmUpdate<T> setNull(final String name, final int jdbcType) {
        this.bindParams.setNullParameter(name, jdbcType);
        return this;
    }
    
    public DefaultOrmUpdate<T> setNullParameter(final String name, final int jdbcType) {
        this.bindParams.setNullParameter(name, jdbcType);
        return this;
    }
    
    public BindParams getBindParams() {
        return this.bindParams;
    }
    
    public String getGeneratedSql() {
        return this.generatedSql;
    }
    
    public void setGeneratedSql(final String generatedSql) {
        this.generatedSql = generatedSql;
    }
    
    public String getBaseTable() {
        return this.baseTable;
    }
    
    public OrmUpdateType getOrmUpdateType() {
        return this.type;
    }
}
