// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

import java.util.Collections;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import java.util.LinkedHashSet;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebean.event.BeanQueryRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.util.FilterExpressionList;
import com.avaje.ebeaninternal.api.SpiExpressionFactory;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebean.OrderBy;
import java.util.List;
import java.util.Set;
import com.avaje.ebean.FetchConfig;
import java.io.Serializable;

public class OrmQueryProperties implements Serializable
{
    private static final long serialVersionUID = -8785582703966455658L;
    private String parentPath;
    private String path;
    private String properties;
    private String trimmedProperties;
    private int queryFetchBatch;
    private boolean queryFetchAll;
    private int lazyFetchBatch;
    private FetchConfig fetchConfig;
    private boolean cache;
    private boolean readOnly;
    private Set<String> included;
    private Set<String> includedBeanJoin;
    private Set<String> secondaryQueryJoins;
    private List<OrmQueryProperties> secondaryChildren;
    private OrderBy orderBy;
    private SpiExpressionList filterMany;
    
    public OrmQueryProperties(final String path) {
        this.queryFetchBatch = -1;
        this.lazyFetchBatch = -1;
        this.path = path;
        this.parentPath = SplitName.parent(path);
    }
    
    public OrmQueryProperties() {
        this(null);
    }
    
    public OrmQueryProperties(final String path, final String properties) {
        this(path);
        this.setProperties(properties);
    }
    
    public void addSecJoinOrderProperty(final OrderBy.Property orderProp) {
        if (this.orderBy == null) {
            this.orderBy = new OrderBy();
        }
        this.orderBy.add(orderProp);
    }
    
    public void setFetchConfig(final FetchConfig fetchConfig) {
        if (fetchConfig != null) {
            this.fetchConfig = fetchConfig;
            this.lazyFetchBatch = fetchConfig.getLazyBatchSize();
            this.queryFetchBatch = fetchConfig.getQueryBatchSize();
            this.queryFetchAll = fetchConfig.isQueryAll();
        }
    }
    
    public FetchConfig getFetchConfig() {
        return this.fetchConfig;
    }
    
    public void setProperties(final String properties) {
        this.properties = properties;
        this.trimmedProperties = properties;
        this.parseProperties();
        if (!this.isAllProperties()) {
            final Set<String> parsed = parseIncluded(this.trimmedProperties);
            if (parsed.contains("*")) {
                this.included = null;
            }
            else {
                this.included = parsed;
            }
        }
        else {
            this.included = null;
        }
    }
    
    private boolean isAllProperties() {
        return this.trimmedProperties == null || this.trimmedProperties.length() == 0 || "*".equals(this.trimmedProperties);
    }
    
    public <T> SpiExpressionList<T> filterMany(final Query<T> rootQuery) {
        if (this.filterMany == null) {
            final FilterExprPath exprPath = new FilterExprPath(this.path);
            final SpiExpressionFactory queryEf = (SpiExpressionFactory)rootQuery.getExpressionFactory();
            final ExpressionFactory filterEf = queryEf.createExpressionFactory(exprPath);
            this.filterMany = new FilterExpressionList(exprPath, filterEf, rootQuery);
            this.queryFetchAll = true;
            this.queryFetchBatch = 100;
            this.lazyFetchBatch = 100;
        }
        return (SpiExpressionList<T>)this.filterMany;
    }
    
    public SpiExpressionList<?> getFilterManyTrimPath(final int trimPath) {
        if (this.filterMany == null) {
            return null;
        }
        this.filterMany.trimPath(trimPath);
        return (SpiExpressionList<?>)this.filterMany;
    }
    
    public SpiExpressionList<?> getFilterMany() {
        return (SpiExpressionList<?>)this.filterMany;
    }
    
    public void setFilterMany(final SpiExpressionList<?> filterMany) {
        this.filterMany = filterMany;
    }
    
    public void setDefaultProperties(final String properties, final Set<String> included) {
        this.properties = properties;
        this.trimmedProperties = properties;
        this.included = included;
    }
    
    public void setTunedProperties(final OrmQueryProperties tunedProperties) {
        this.properties = tunedProperties.properties;
        this.trimmedProperties = tunedProperties.trimmedProperties;
        this.included = tunedProperties.included;
    }
    
    public void configureBeanQuery(final SpiQuery<?> query) {
        if (this.trimmedProperties != null && this.trimmedProperties.length() > 0) {
            query.select(this.trimmedProperties);
            if (this.filterMany != null) {
                query.setFilterMany(this.path, this.filterMany);
            }
        }
        if (this.secondaryChildren != null) {
            final int trimPath = this.path.length() + 1;
            for (int i = 0; i < this.secondaryChildren.size(); ++i) {
                final OrmQueryProperties p = this.secondaryChildren.get(i);
                String path = p.getPath();
                path = path.substring(trimPath);
                query.fetch(path, p.getProperties(), p.getFetchConfig());
                query.setFilterMany(path, p.getFilterManyTrimPath(trimPath));
            }
        }
        if (this.orderBy != null) {
            final List<OrderBy.Property> orderByProps = (List<OrderBy.Property>)this.orderBy.getProperties();
            for (int i = 0; i < orderByProps.size(); ++i) {
                orderByProps.get(i).trim(this.path);
            }
            query.setOrder(this.orderBy);
        }
    }
    
    public void configureManyQuery(final SpiQuery<?> query) {
        if (this.trimmedProperties != null && this.trimmedProperties.length() > 0) {
            query.fetch(query.getLazyLoadManyPath(), this.trimmedProperties);
        }
        if (this.filterMany != null) {
            query.setFilterMany(this.path, this.filterMany);
        }
        if (this.secondaryChildren != null) {
            final int trimlen = this.path.length() - query.getLazyLoadManyPath().length();
            for (int i = 0; i < this.secondaryChildren.size(); ++i) {
                final OrmQueryProperties p = this.secondaryChildren.get(i);
                String path = p.getPath();
                path = path.substring(trimlen);
                query.fetch(path, p.getProperties(), p.getFetchConfig());
                query.setFilterMany(path, p.getFilterManyTrimPath(trimlen));
            }
        }
        if (this.orderBy != null) {
            query.setOrder(this.orderBy);
        }
    }
    
    public OrmQueryProperties copy() {
        final OrmQueryProperties copy = new OrmQueryProperties();
        copy.parentPath = this.parentPath;
        copy.path = this.path;
        copy.properties = this.properties;
        copy.cache = this.cache;
        copy.readOnly = this.readOnly;
        copy.queryFetchAll = this.queryFetchAll;
        copy.queryFetchBatch = this.queryFetchBatch;
        copy.lazyFetchBatch = this.lazyFetchBatch;
        copy.filterMany = this.filterMany;
        if (this.included != null) {
            copy.included = new HashSet<String>(this.included);
        }
        if (this.includedBeanJoin != null) {
            copy.includedBeanJoin = new HashSet<String>(this.includedBeanJoin);
        }
        return copy;
    }
    
    public boolean hasSelectClause() {
        return "*".equals(this.trimmedProperties) || this.included != null;
    }
    
    public String toString() {
        String s = "";
        if (this.path != null) {
            s = s + this.path + " ";
        }
        if (this.properties != null) {
            s = s + "(" + this.properties + ") ";
        }
        else if (this.included != null) {
            s = s + "(" + this.included + ") ";
        }
        return s;
    }
    
    public boolean isChild(final OrmQueryProperties possibleChild) {
        return possibleChild.getPath().startsWith(this.path + ".");
    }
    
    public void add(final OrmQueryProperties child) {
        if (this.secondaryChildren == null) {
            this.secondaryChildren = new ArrayList<OrmQueryProperties>();
        }
        this.secondaryChildren.add(child);
    }
    
    public int autofetchPlanHash() {
        int hc = (this.path != null) ? this.path.hashCode() : 1;
        if (this.properties != null) {
            hc = hc * 31 + this.properties.hashCode();
        }
        else {
            hc = hc * 31 + ((this.included != null) ? this.included.hashCode() : 1);
        }
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        int hc = (this.path != null) ? this.path.hashCode() : 1;
        if (this.properties != null) {
            hc = hc * 31 + this.properties.hashCode();
        }
        else {
            hc = hc * 31 + ((this.included != null) ? this.included.hashCode() : 1);
        }
        hc = hc * 31 + ((this.filterMany != null) ? this.filterMany.queryPlanHash(request) : 1);
        return hc;
    }
    
    public String getProperties() {
        return this.properties;
    }
    
    public ReferenceOptions getReferenceOptions() {
        if (this.cache || this.readOnly) {
            return new ReferenceOptions(this.cache, this.readOnly, null);
        }
        return null;
    }
    
    public boolean hasProperties() {
        return this.properties != null || this.included != null;
    }
    
    public boolean isIncludedBeanJoin(final String propertyName) {
        return this.includedBeanJoin != null && this.includedBeanJoin.contains(propertyName);
    }
    
    public void includeBeanJoin(final String propertyName) {
        if (this.includedBeanJoin == null) {
            this.includedBeanJoin = new HashSet<String>();
        }
        this.includedBeanJoin.add(propertyName);
    }
    
    public boolean allProperties() {
        return this.included == null;
    }
    
    public Iterator<String> getSelectProperties() {
        if (this.secondaryQueryJoins == null) {
            return this.included.iterator();
        }
        final LinkedHashSet<String> temp = new LinkedHashSet<String>(this.secondaryQueryJoins.size() + this.included.size());
        temp.addAll((Collection<?>)this.included);
        temp.addAll((Collection<?>)this.secondaryQueryJoins);
        return temp.iterator();
    }
    
    public void addSecondaryQueryJoin(final String property) {
        if (this.secondaryQueryJoins == null) {
            this.secondaryQueryJoins = new HashSet<String>(4);
        }
        this.secondaryQueryJoins.add(property);
    }
    
    public Set<String> getAllIncludedProperties() {
        if (this.included == null) {
            return null;
        }
        if (this.includedBeanJoin == null && this.secondaryQueryJoins == null) {
            return new LinkedHashSet<String>(this.included);
        }
        final LinkedHashSet<String> s = new LinkedHashSet<String>(2 * (this.included.size() + 5));
        if (this.included != null) {
            s.addAll((Collection<?>)this.included);
        }
        if (this.includedBeanJoin != null) {
            s.addAll((Collection<?>)this.includedBeanJoin);
        }
        if (this.secondaryQueryJoins != null) {
            s.addAll((Collection<?>)this.secondaryQueryJoins);
        }
        return s;
    }
    
    public boolean isIncluded(final String propName) {
        return (this.includedBeanJoin == null || !this.includedBeanJoin.contains(propName)) && (this.included == null || this.included.contains(propName));
    }
    
    public OrmQueryProperties setQueryFetchBatch(final int queryFetchBatch) {
        this.queryFetchBatch = queryFetchBatch;
        return this;
    }
    
    public OrmQueryProperties setQueryFetchAll(final boolean queryFetchAll) {
        this.queryFetchAll = queryFetchAll;
        return this;
    }
    
    public OrmQueryProperties setQueryFetch(final int batch, final boolean queryFetchAll) {
        this.queryFetchBatch = batch;
        this.queryFetchAll = queryFetchAll;
        return this;
    }
    
    public OrmQueryProperties setLazyFetchBatch(final int lazyFetchBatch) {
        this.lazyFetchBatch = lazyFetchBatch;
        return this;
    }
    
    public boolean isFetchJoin() {
        return !this.isQueryFetch() && !this.isLazyFetch();
    }
    
    public boolean isQueryFetch() {
        return this.queryFetchBatch > -1;
    }
    
    public int getQueryFetchBatch() {
        return this.queryFetchBatch;
    }
    
    public boolean isQueryFetchAll() {
        return this.queryFetchAll;
    }
    
    public boolean isLazyFetch() {
        return this.lazyFetchBatch > -1;
    }
    
    public int getLazyFetchBatch() {
        return this.lazyFetchBatch;
    }
    
    public boolean isReadOnly() {
        return this.readOnly;
    }
    
    public boolean isCache() {
        return this.cache;
    }
    
    public String getParentPath() {
        return this.parentPath;
    }
    
    public String getPath() {
        return this.path;
    }
    
    private void parseProperties() {
        if (this.trimmedProperties == null) {
            return;
        }
        int pos = this.trimmedProperties.indexOf("+readonly");
        if (pos > -1) {
            this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, "+readonly", "");
            this.readOnly = true;
        }
        pos = this.trimmedProperties.indexOf("+cache");
        if (pos > -1) {
            this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, "+cache", "");
            this.cache = true;
        }
        pos = this.trimmedProperties.indexOf("+query");
        if (pos > -1) {
            this.queryFetchBatch = this.parseBatchHint(pos, "+query");
        }
        pos = this.trimmedProperties.indexOf("+lazy");
        if (pos > -1) {
            this.lazyFetchBatch = this.parseBatchHint(pos, "+lazy");
        }
        this.trimmedProperties = this.trimmedProperties.trim();
        while (this.trimmedProperties.startsWith(",")) {
            this.trimmedProperties = this.trimmedProperties.substring(1).trim();
        }
    }
    
    private int parseBatchHint(final int pos, final String option) {
        final int startPos = pos + option.length();
        final int endPos = this.findEndPos(startPos, this.trimmedProperties);
        if (endPos == -1) {
            this.trimmedProperties = StringHelper.replaceString(this.trimmedProperties, option, "");
            return 0;
        }
        final String batchParam = this.trimmedProperties.substring(startPos + 1, endPos);
        if (endPos + 1 >= this.trimmedProperties.length()) {
            this.trimmedProperties = this.trimmedProperties.substring(0, pos);
        }
        else {
            this.trimmedProperties = this.trimmedProperties.substring(0, pos) + this.trimmedProperties.substring(endPos + 1);
        }
        return Integer.parseInt(batchParam);
    }
    
    private int findEndPos(final int pos, final String props) {
        if (pos >= props.length() || props.charAt(pos) != '(') {
            return -1;
        }
        final int endPara = props.indexOf(41, pos + 1);
        if (endPara == -1) {
            final String m = "Error could not find ')' in " + props + " after position " + pos;
            throw new RuntimeException(m);
        }
        return endPara;
    }
    
    private static Set<String> parseIncluded(final String rawList) {
        final String[] res = rawList.split(",");
        final LinkedHashSet<String> set = new LinkedHashSet<String>(res.length + 3);
        String temp = null;
        for (int i = 0; i < res.length; ++i) {
            temp = res[i].trim();
            if (temp.length() > 0) {
                set.add(temp);
            }
        }
        if (set.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableSet((Set<? extends String>)set);
    }
}
