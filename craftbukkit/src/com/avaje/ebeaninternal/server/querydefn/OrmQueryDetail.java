// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

import java.util.HashSet;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.FetchConfig;
import com.avaje.ebeaninternal.server.query.SplitName;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.event.BeanQueryRequest;
import java.util.Iterator;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.io.Serializable;

public class OrmQueryDetail implements Serializable
{
    private static final long serialVersionUID = -2510486880141461807L;
    private OrmQueryProperties baseProps;
    private LinkedHashMap<String, OrmQueryProperties> fetchPaths;
    private LinkedHashSet<String> includes;
    
    public OrmQueryDetail() {
        this.baseProps = new OrmQueryProperties();
        this.fetchPaths = new LinkedHashMap<String, OrmQueryProperties>(8);
        this.includes = new LinkedHashSet<String>(8);
    }
    
    public OrmQueryDetail copy() {
        final OrmQueryDetail copy = new OrmQueryDetail();
        copy.baseProps = this.baseProps.copy();
        for (final Map.Entry<String, OrmQueryProperties> entry : this.fetchPaths.entrySet()) {
            copy.fetchPaths.put(entry.getKey(), entry.getValue().copy());
        }
        copy.includes = new LinkedHashSet<String>(this.includes);
        return copy;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        int hc = (this.baseProps == null) ? 1 : this.baseProps.queryPlanHash(request);
        if (this.fetchPaths != null) {
            for (final OrmQueryProperties p : this.fetchPaths.values()) {
                hc = hc * 31 + p.queryPlanHash(request);
            }
        }
        return hc;
    }
    
    public boolean isAutoFetchEqual(final OrmQueryDetail otherDetail) {
        return this.autofetchPlanHash() == otherDetail.autofetchPlanHash();
    }
    
    private int autofetchPlanHash() {
        int hc = (this.baseProps == null) ? 1 : this.baseProps.autofetchPlanHash();
        if (this.fetchPaths != null) {
            for (final OrmQueryProperties p : this.fetchPaths.values()) {
                hc = hc * 31 + p.autofetchPlanHash();
            }
        }
        return hc;
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.baseProps != null) {
            sb.append("select ").append(this.baseProps);
        }
        if (this.fetchPaths != null) {
            for (final OrmQueryProperties join : this.fetchPaths.values()) {
                sb.append(" fetch ").append(join);
            }
        }
        return sb.toString();
    }
    
    public int hashCode() {
        throw new RuntimeException("should not use");
    }
    
    public void select(final String columns) {
        this.baseProps = new OrmQueryProperties(null, columns);
    }
    
    public boolean containsProperty(final String property) {
        return this.baseProps == null || this.baseProps.isIncluded(property);
    }
    
    public void setBase(final OrmQueryProperties baseProps) {
        this.baseProps = baseProps;
    }
    
    public List<OrmQueryProperties> removeSecondaryQueries() {
        return this.removeSecondaryQueries(false);
    }
    
    public List<OrmQueryProperties> removeSecondaryLazyQueries() {
        return this.removeSecondaryQueries(true);
    }
    
    private List<OrmQueryProperties> removeSecondaryQueries(final boolean lazyQuery) {
        final ArrayList<String> matchingPaths = new ArrayList<String>(2);
        for (final OrmQueryProperties chunk : this.fetchPaths.values()) {
            final boolean match = lazyQuery ? chunk.isLazyFetch() : chunk.isQueryFetch();
            if (match) {
                matchingPaths.add(chunk.getPath());
            }
        }
        if (matchingPaths.size() == 0) {
            return null;
        }
        Collections.sort(matchingPaths);
        final ArrayList<OrmQueryProperties> props = new ArrayList<OrmQueryProperties>(2);
        for (int i = 0; i < matchingPaths.size(); ++i) {
            final String path = matchingPaths.get(i);
            this.includes.remove(path);
            final OrmQueryProperties secQuery = this.fetchPaths.remove(path);
            if (secQuery != null) {
                props.add(secQuery);
                final Iterator<OrmQueryProperties> pass2It = this.fetchPaths.values().iterator();
                while (pass2It.hasNext()) {
                    final OrmQueryProperties pass2Prop = pass2It.next();
                    if (secQuery.isChild(pass2Prop)) {
                        pass2It.remove();
                        this.includes.remove(pass2Prop.getPath());
                        secQuery.add(pass2Prop);
                    }
                }
            }
        }
        for (int i = 0; i < props.size(); ++i) {
            final String path = props.get(i).getPath();
            final String[] split = SplitName.split(path);
            final OrmQueryProperties chunk2 = this.getChunk(split[0], true);
            chunk2.addSecondaryQueryJoin(split[1]);
        }
        return props;
    }
    
    public boolean tuneFetchProperties(final OrmQueryDetail tunedDetail) {
        boolean tuned = false;
        final OrmQueryProperties tunedRoot = tunedDetail.getChunk(null, false);
        if (tunedRoot != null && tunedRoot.hasProperties()) {
            tuned = true;
            this.baseProps.setTunedProperties(tunedRoot);
            for (final OrmQueryProperties tunedChunk : tunedDetail.fetchPaths.values()) {
                final OrmQueryProperties chunk = this.getChunk(tunedChunk.getPath(), false);
                if (chunk != null) {
                    chunk.setTunedProperties(tunedChunk);
                }
                else {
                    this.putFetchPath(tunedChunk.copy());
                }
            }
        }
        return tuned;
    }
    
    public void putFetchPath(final OrmQueryProperties chunk) {
        final String path = chunk.getPath();
        this.fetchPaths.put(path, chunk);
        this.includes.add(path);
    }
    
    public void clear() {
        this.includes.clear();
        this.fetchPaths.clear();
    }
    
    public OrmQueryProperties addFetch(final String path, final String partialProps, final FetchConfig fetchConfig) {
        final OrmQueryProperties chunk = this.getChunk(path, true);
        chunk.setProperties(partialProps);
        chunk.setFetchConfig(fetchConfig);
        return chunk;
    }
    
    public void sortFetchPaths(final BeanDescriptor<?> d) {
        final LinkedHashMap<String, OrmQueryProperties> sorted = new LinkedHashMap<String, OrmQueryProperties>(this.fetchPaths.size());
        for (final OrmQueryProperties p : this.fetchPaths.values()) {
            this.sortFetchPaths(d, p, sorted);
        }
        this.fetchPaths = sorted;
    }
    
    private void sortFetchPaths(final BeanDescriptor<?> d, final OrmQueryProperties p, final LinkedHashMap<String, OrmQueryProperties> sorted) {
        final String path = p.getPath();
        if (!sorted.containsKey(path)) {
            final String parentPath = p.getParentPath();
            if (parentPath == null || sorted.containsKey(parentPath)) {
                sorted.put(path, p);
            }
            else {
                OrmQueryProperties parentProp = this.fetchPaths.get(parentPath);
                if (parentProp == null) {
                    final ElPropertyValue el = d.getElGetValue(parentPath);
                    if (el == null) {
                        final String msg = "Path [" + parentPath + "] not valid from " + d.getFullName();
                        throw new PersistenceException(msg);
                    }
                    final BeanPropertyAssoc<?> assocOne = (BeanPropertyAssoc<?>)el.getBeanProperty();
                    parentProp = new OrmQueryProperties(parentPath, assocOne.getTargetIdProperty());
                }
                if (parentProp != null) {
                    this.sortFetchPaths(d, parentProp, sorted);
                }
                sorted.put(path, p);
            }
        }
    }
    
    public void convertManyFetchJoinsToQueryJoins(final BeanDescriptor<?> beanDescriptor, final String lazyLoadManyPath, final boolean allowOne, final int queryBatch) {
        final ArrayList<OrmQueryProperties> manyChunks = new ArrayList<OrmQueryProperties>(3);
        String manyFetchProperty = null;
        boolean fetchJoinFirstMany = allowOne;
        this.sortFetchPaths(beanDescriptor);
        for (final String fetchPath : this.fetchPaths.keySet()) {
            final ElPropertyDeploy elProp = beanDescriptor.getElPropertyDeploy(fetchPath);
            if (elProp.containsManySince(manyFetchProperty)) {
                final OrmQueryProperties chunk = this.fetchPaths.get(fetchPath);
                if (!chunk.isFetchJoin() || this.isLazyLoadManyRoot(lazyLoadManyPath, chunk) || this.hasParentSecJoin(lazyLoadManyPath, chunk)) {
                    continue;
                }
                if (fetchJoinFirstMany) {
                    fetchJoinFirstMany = false;
                    manyFetchProperty = fetchPath;
                }
                else {
                    manyChunks.add(chunk);
                }
            }
        }
        for (int i = 0; i < manyChunks.size(); ++i) {
            manyChunks.get(i).setQueryFetch(queryBatch, true);
        }
    }
    
    private boolean isLazyLoadManyRoot(final String lazyLoadManyPath, final OrmQueryProperties chunk) {
        return lazyLoadManyPath != null && lazyLoadManyPath.equals(chunk.getPath());
    }
    
    private boolean hasParentSecJoin(final String lazyLoadManyPath, final OrmQueryProperties chunk) {
        final OrmQueryProperties parent = this.getParent(chunk);
        return parent != null && (lazyLoadManyPath == null || !lazyLoadManyPath.equals(parent.getPath())) && (!parent.isFetchJoin() || this.hasParentSecJoin(lazyLoadManyPath, parent));
    }
    
    private OrmQueryProperties getParent(final OrmQueryProperties chunk) {
        final String parentPath = chunk.getParentPath();
        return (parentPath == null) ? null : this.fetchPaths.get(parentPath);
    }
    
    public void setDefaultSelectClause(final BeanDescriptor<?> desc) {
        if (desc.hasDefaultSelectClause() && !this.hasSelectClause()) {
            if (this.baseProps == null) {
                this.baseProps = new OrmQueryProperties();
            }
            this.baseProps.setDefaultProperties(desc.getDefaultSelectClause(), desc.getDefaultSelectClauseSet());
        }
        for (final OrmQueryProperties joinProps : this.fetchPaths.values()) {
            if (!joinProps.hasSelectClause()) {
                final BeanDescriptor<?> assocDesc = desc.getBeanDescriptor(joinProps.getPath());
                if (!assocDesc.hasDefaultSelectClause()) {
                    continue;
                }
                joinProps.setDefaultProperties(assocDesc.getDefaultSelectClause(), assocDesc.getDefaultSelectClauseSet());
            }
        }
    }
    
    public boolean hasSelectClause() {
        return this.baseProps != null && this.baseProps.hasSelectClause();
    }
    
    public boolean isEmpty() {
        return this.fetchPaths.isEmpty() && (this.baseProps == null || !this.baseProps.hasProperties());
    }
    
    public boolean isJoinsEmpty() {
        return this.fetchPaths.isEmpty();
    }
    
    public void includeBeanJoin(final String parentPath, final String propertyName) {
        final OrmQueryProperties parentChunk = this.getChunk(parentPath, true);
        parentChunk.includeBeanJoin(propertyName);
    }
    
    public OrmQueryProperties getChunk(final String path, final boolean create) {
        if (path == null) {
            return this.baseProps;
        }
        OrmQueryProperties props = this.fetchPaths.get(path);
        if (create && props == null) {
            props = new OrmQueryProperties(path);
            this.putFetchPath(props);
            return props;
        }
        return props;
    }
    
    public boolean includes(final String path) {
        final OrmQueryProperties chunk = this.fetchPaths.get(path);
        return chunk != null && !chunk.isCache();
    }
    
    public HashSet<String> getIncludes() {
        return this.includes;
    }
}
