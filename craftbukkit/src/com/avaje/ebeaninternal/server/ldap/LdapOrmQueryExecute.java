// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchResult;
import java.util.logging.Level;
import java.util.ArrayList;
import javax.naming.directory.SearchControls;
import java.util.List;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import javax.naming.NamingException;
import javax.naming.Name;
import java.util.Arrays;
import javax.naming.directory.DirContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiQuery;
import java.util.logging.Logger;

public class LdapOrmQueryExecute<T>
{
    private static final Logger logger;
    private final SpiQuery<?> query;
    private final BeanDescriptor<T> beanDescriptor;
    private final DirContext dc;
    private final LdapBeanBuilder<T> beanBuilder;
    private final String filterExpr;
    private final Object[] filterValues;
    private final String[] selectProps;
    
    public LdapOrmQueryExecute(final LdapOrmQueryRequest<T> request, final boolean defaultVanillaMode, final DirContext dc) {
        this.query = request.getQuery();
        this.beanDescriptor = request.getBeanDescriptor();
        this.dc = dc;
        final boolean vanillaMode = this.query.isVanillaMode(defaultVanillaMode);
        this.beanBuilder = new LdapBeanBuilder<T>(this.beanDescriptor, vanillaMode);
        final LdapQueryDeployHelper deployHelper = new LdapQueryDeployHelper(request);
        this.selectProps = deployHelper.getSelectedProperties();
        this.filterExpr = deployHelper.getFilterExpr();
        this.filterValues = deployHelper.getFilterValues();
    }
    
    public T findId() {
        final Object id = this.query.getId();
        try {
            final LdapName dn = this.beanDescriptor.createLdapNameById(id);
            String[] findAttrs = this.selectProps;
            if (findAttrs == null) {
                findAttrs = this.beanDescriptor.getDefaultSelectDbArray();
            }
            final String debugQuery = "Name:" + dn + " attrs:" + Arrays.toString(findAttrs);
            final Attributes attrs = this.dc.getAttributes(dn, findAttrs);
            final T bean = this.beanBuilder.readAttributes(attrs);
            this.query.setGeneratedSql(debugQuery);
            return bean;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
    
    public List<T> findList() {
        final SearchControls sc = new SearchControls();
        sc.setSearchScope(1);
        final List<T> list = new ArrayList<T>();
        try {
            final LdapName dn = this.beanDescriptor.createLdapName(null);
            String debugQuery = "Name:" + dn;
            if (this.selectProps != null) {
                sc.setReturningAttributes(this.selectProps);
                debugQuery = debugQuery + " select:" + Arrays.toString(this.selectProps);
            }
            if (LdapOrmQueryExecute.logger.isLoggable(Level.INFO)) {
                LdapOrmQueryExecute.logger.info("Ldap Query  Name:" + dn + " filterExpr:" + this.filterExpr);
            }
            debugQuery = debugQuery + " filterExpr:" + this.filterExpr;
            NamingEnumeration<SearchResult> result;
            if (this.filterValues == null || this.filterValues.length == 0) {
                result = this.dc.search(dn, this.filterExpr, sc);
            }
            else {
                debugQuery = debugQuery + " filterValues:" + Arrays.toString(this.filterValues);
                result = this.dc.search(dn, this.filterExpr, this.filterValues, sc);
            }
            this.query.setGeneratedSql(debugQuery);
            if (result != null) {
                while (result.hasMoreElements()) {
                    final SearchResult row = result.nextElement();
                    final T bean = this.beanBuilder.readAttributes(row.getAttributes());
                    list.add(bean);
                }
            }
            return list;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
    
    static {
        logger = Logger.getLogger(LdapOrmQueryExecute.class.getName());
    }
}
