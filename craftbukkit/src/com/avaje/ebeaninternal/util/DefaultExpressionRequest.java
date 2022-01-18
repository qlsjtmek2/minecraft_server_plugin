// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.util;

import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;

public class DefaultExpressionRequest implements SpiExpressionRequest
{
    private final SpiOrmQueryRequest<?> queryRequest;
    private final BeanDescriptor<?> beanDescriptor;
    private final StringBuilder sb;
    private final ArrayList<Object> bindValues;
    private final DeployParser deployParser;
    private int paramIndex;
    private LIndex luceneIndex;
    
    public DefaultExpressionRequest(final SpiOrmQueryRequest<?> queryRequest, final DeployParser deployParser) {
        this.sb = new StringBuilder();
        this.bindValues = new ArrayList<Object>();
        this.queryRequest = queryRequest;
        this.beanDescriptor = queryRequest.getBeanDescriptor();
        this.deployParser = deployParser;
    }
    
    public DefaultExpressionRequest(final SpiOrmQueryRequest<?> queryRequest, final LIndex index) {
        this.sb = new StringBuilder();
        this.bindValues = new ArrayList<Object>();
        this.queryRequest = queryRequest;
        this.beanDescriptor = queryRequest.getBeanDescriptor();
        this.deployParser = null;
        this.luceneIndex = index;
    }
    
    public DefaultExpressionRequest(final BeanDescriptor<?> beanDescriptor) {
        this.sb = new StringBuilder();
        this.bindValues = new ArrayList<Object>();
        this.beanDescriptor = beanDescriptor;
        this.queryRequest = null;
        this.deployParser = null;
    }
    
    public LIndex getLuceneIndex() {
        return this.luceneIndex;
    }
    
    public String parseDeploy(final String logicalProp) {
        final String s = this.deployParser.getDeployWord(logicalProp);
        return (s == null) ? logicalProp : s;
    }
    
    public int nextParameter() {
        return ++this.paramIndex;
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public SpiOrmQueryRequest<?> getQueryRequest() {
        return this.queryRequest;
    }
    
    public SpiExpressionRequest append(final String sql) {
        this.sb.append(sql);
        return this;
    }
    
    public void addBindValue(final Object bindValue) {
        this.bindValues.add(bindValue);
    }
    
    public boolean includeProperty(final String propertyName) {
        return true;
    }
    
    public String getSql() {
        return this.sb.toString();
    }
    
    public ArrayList<Object> getBindValues() {
        return this.bindValues;
    }
}
