// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebeaninternal.server.deploy.DeployPropertyParser;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiQuery;

public class LdapQueryDeployHelper
{
    private final LdapOrmQueryRequest<?> request;
    private final SpiQuery<?> query;
    private final BeanDescriptor<?> desc;
    private String filterExpr;
    private Object[] filterValues;
    
    public LdapQueryDeployHelper(final LdapOrmQueryRequest<?> request) {
        this.request = request;
        this.query = request.getQuery();
        this.desc = request.getBeanDescriptor();
        this.parse();
    }
    
    public String[] getSelectedProperties() {
        final OrmQueryProperties chunk = this.query.getDetail().getChunk(null, false);
        if (chunk.allProperties()) {
            return null;
        }
        final ArrayList<String> ldapSelectProps = new ArrayList<String>();
        final Iterator<String> selectProperties = chunk.getSelectProperties();
        while (selectProperties.hasNext()) {
            String propName = selectProperties.next();
            final BeanProperty p = this.desc.getBeanProperty(propName);
            if (p != null) {
                propName = p.getDbColumn();
            }
            ldapSelectProps.add(propName);
        }
        return ldapSelectProps.toArray(new String[ldapSelectProps.size()]);
    }
    
    private void parse() {
        final DeployPropertyParser deployParser = this.desc.createDeployPropertyParser();
        String baseWhere = this.query.getAdditionalWhere();
        if (baseWhere != null) {
            baseWhere = deployParser.parse(baseWhere);
        }
        final SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
        if (whereExp != null) {
            final DefaultExpressionRequest expReq = new DefaultExpressionRequest(this.request, deployParser);
            final ArrayList<?> bindValues = whereExp.buildBindValues(expReq);
            this.filterValues = bindValues.toArray(new Object[bindValues.size()]);
            final String exprWhere = whereExp.buildSql(expReq);
            if (baseWhere != null) {
                this.filterExpr = "(&" + baseWhere + exprWhere + ")";
            }
            else {
                this.filterExpr = exprWhere;
            }
        }
        else {
            this.filterExpr = baseWhere;
        }
    }
    
    public String getFilterExpr() {
        return this.filterExpr;
    }
    
    public Object[] getFilterValues() {
        return this.filterValues;
    }
}
