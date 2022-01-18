// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.Map;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class SqlTreeAlias
{
    private int counter;
    private int manyWhereCounter;
    private TreeSet<String> joinProps;
    private HashSet<String> embeddedPropertyJoins;
    private TreeSet<String> manyWhereJoinProps;
    private HashMap<String, String> aliasMap;
    private HashMap<String, String> manyWhereAliasMap;
    private final String rootTableAlias;
    
    public SqlTreeAlias(final String rootTableAlias) {
        this.joinProps = new TreeSet<String>();
        this.manyWhereJoinProps = new TreeSet<String>();
        this.aliasMap = new HashMap<String, String>();
        this.manyWhereAliasMap = new HashMap<String, String>();
        this.rootTableAlias = rootTableAlias;
    }
    
    public void addManyWhereJoins(final Set<String> manyWhereJoins) {
        if (manyWhereJoins != null) {
            for (final String include : manyWhereJoins) {
                this.addPropertyJoin(include, this.manyWhereJoinProps);
            }
        }
    }
    
    private void addEmbeddedPropertyJoin(final String embProp) {
        if (this.embeddedPropertyJoins == null) {
            this.embeddedPropertyJoins = new HashSet<String>();
        }
        this.embeddedPropertyJoins.add(embProp);
    }
    
    public void addJoin(final Set<String> propJoins, final BeanDescriptor<?> desc) {
        if (propJoins != null) {
            for (final String propJoin : propJoins) {
                final ElPropertyDeploy elProp = desc.getElPropertyDeploy(propJoin);
                if (elProp != null && elProp.getBeanProperty().isEmbedded()) {
                    final String[] split = SplitName.split(propJoin);
                    this.addPropertyJoin(split[0], this.joinProps);
                    this.addEmbeddedPropertyJoin(propJoin);
                }
                else {
                    this.addPropertyJoin(propJoin, this.joinProps);
                }
            }
        }
    }
    
    private void addPropertyJoin(final String include, final TreeSet<String> set) {
        if (set.add(include)) {
            final String[] split = SplitName.split(include);
            if (split[0] != null) {
                this.addPropertyJoin(split[0], set);
            }
        }
    }
    
    public void buildAlias() {
        Iterator<String> i = this.joinProps.iterator();
        while (i.hasNext()) {
            this.calcAlias(i.next());
        }
        i = this.manyWhereJoinProps.iterator();
        while (i.hasNext()) {
            this.calcAliasManyWhere(i.next());
        }
        this.mapEmbeddedPropertyAlias();
    }
    
    private void mapEmbeddedPropertyAlias() {
        if (this.embeddedPropertyJoins != null) {
            for (final String propJoin : this.embeddedPropertyJoins) {
                final String[] split = SplitName.split(propJoin);
                final String alias = this.getTableAlias(split[0]);
                this.aliasMap.put(propJoin, alias);
            }
        }
    }
    
    private String calcAlias(final String prefix) {
        final String alias = this.nextTableAlias();
        this.aliasMap.put(prefix, alias);
        return alias;
    }
    
    private String calcAliasManyWhere(final String prefix) {
        final String alias = this.nextManyWhereTableAlias();
        this.manyWhereAliasMap.put(prefix, alias);
        return alias;
    }
    
    public String getTableAlias(final String prefix) {
        if (prefix == null) {
            return this.rootTableAlias;
        }
        final String s = this.aliasMap.get(prefix);
        if (s == null) {
            return this.calcAlias(prefix);
        }
        return s;
    }
    
    public String getTableAliasManyWhere(final String prefix) {
        if (prefix == null) {
            return this.rootTableAlias;
        }
        String s = this.manyWhereAliasMap.get(prefix);
        if (s == null) {
            s = this.aliasMap.get(prefix);
        }
        if (s == null) {
            final String msg = "Could not determine table alias for [" + prefix + "] manyMap[" + this.manyWhereAliasMap + "] aliasMap[" + this.aliasMap + "]";
            throw new RuntimeException(msg);
        }
        return s;
    }
    
    public String parseWhere(String clause) {
        clause = this.parseRootAlias(clause);
        clause = this.parseAliasMap(clause, this.manyWhereAliasMap);
        return this.parseAliasMap(clause, this.aliasMap);
    }
    
    public String parse(String clause) {
        clause = this.parseRootAlias(clause);
        return this.parseAliasMap(clause, this.aliasMap);
    }
    
    private String parseRootAlias(final String clause) {
        if (this.rootTableAlias == null) {
            return clause.replace("${}", "");
        }
        return clause.replace("${}", this.rootTableAlias + ".");
    }
    
    private String parseAliasMap(String clause, final HashMap<String, String> parseAliasMap) {
        for (final Map.Entry<String, String> e : parseAliasMap.entrySet()) {
            final String k = "${" + e.getKey() + "}";
            clause = clause.replace(k, e.getValue() + ".");
        }
        return clause;
    }
    
    private String nextTableAlias() {
        return "t" + ++this.counter;
    }
    
    private String nextManyWhereTableAlias() {
        return "u" + ++this.manyWhereCounter;
    }
}
