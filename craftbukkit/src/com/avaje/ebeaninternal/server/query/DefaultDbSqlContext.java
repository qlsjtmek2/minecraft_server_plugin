// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.ArrayList;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.util.ArrayStack;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;

public class DefaultDbSqlContext implements DbSqlContext
{
    private static final String NEW_LINE = "\n";
    private static final String COMMA = ", ";
    private static final String PERIOD = ".";
    private final String tableAliasPlaceHolder;
    private final String columnAliasPrefix;
    private final ArrayStack<String> tableAliasStack;
    private final ArrayStack<String> joinStack;
    private final ArrayStack<String> prefixStack;
    private final boolean useColumnAlias;
    private int columnIndex;
    private StringBuilder sb;
    private HashSet<String> formulaJoins;
    private HashSet<String> tableJoins;
    private SqlTreeAlias alias;
    private String currentPrefix;
    private ArrayList<BeanProperty> encryptedProps;
    
    public DefaultDbSqlContext(final SqlTreeAlias alias, final String tableAliasPlaceHolder) {
        this.tableAliasStack = new ArrayStack<String>();
        this.joinStack = new ArrayStack<String>();
        this.prefixStack = new ArrayStack<String>();
        this.sb = new StringBuilder(140);
        this.tableAliasPlaceHolder = tableAliasPlaceHolder;
        this.columnAliasPrefix = null;
        this.useColumnAlias = false;
        this.alias = alias;
    }
    
    public DefaultDbSqlContext(final SqlTreeAlias alias, final String tableAliasPlaceHolder, final String columnAliasPrefix, final boolean alwaysUseColumnAlias) {
        this.tableAliasStack = new ArrayStack<String>();
        this.joinStack = new ArrayStack<String>();
        this.prefixStack = new ArrayStack<String>();
        this.sb = new StringBuilder(140);
        this.alias = alias;
        this.tableAliasPlaceHolder = tableAliasPlaceHolder;
        this.columnAliasPrefix = columnAliasPrefix;
        this.useColumnAlias = alwaysUseColumnAlias;
    }
    
    public void addEncryptedProp(final BeanProperty p) {
        if (this.encryptedProps == null) {
            this.encryptedProps = new ArrayList<BeanProperty>();
        }
        this.encryptedProps.add(p);
    }
    
    public BeanProperty[] getEncryptedProps() {
        if (this.encryptedProps == null) {
            return null;
        }
        return this.encryptedProps.toArray(new BeanProperty[this.encryptedProps.size()]);
    }
    
    public String peekJoin() {
        return this.joinStack.peek();
    }
    
    public void popJoin() {
        this.joinStack.pop();
    }
    
    public void pushJoin(final String node) {
        this.joinStack.push(node);
    }
    
    public void addJoin(final String type, final String table, final TableJoinColumn[] cols, final String a1, final String a2) {
        if (this.tableJoins == null) {
            this.tableJoins = new HashSet<String>();
        }
        final String joinKey = table + "-" + a1 + "-" + a2;
        if (this.tableJoins.contains(joinKey)) {
            return;
        }
        this.tableJoins.add(joinKey);
        this.sb.append("\n");
        this.sb.append(type);
        this.sb.append(" ").append(table).append(" ");
        this.sb.append(a2);
        this.sb.append(" on ");
        for (int i = 0; i < cols.length; ++i) {
            final TableJoinColumn pair = cols[i];
            if (i > 0) {
                this.sb.append(" and ");
            }
            this.sb.append(a2);
            this.sb.append(".").append(pair.getForeignDbColumn());
            this.sb.append(" = ");
            this.sb.append(a1);
            this.sb.append(".").append(pair.getLocalDbColumn());
        }
        this.sb.append(" ");
    }
    
    public String getTableAlias(final String prefix) {
        return this.alias.getTableAlias(prefix);
    }
    
    public String getTableAliasManyWhere(final String prefix) {
        return this.alias.getTableAliasManyWhere(prefix);
    }
    
    public void pushSecondaryTableAlias(final String alias) {
        this.tableAliasStack.push(alias);
    }
    
    public String getRelativePrefix(final String propName) {
        return (this.currentPrefix == null) ? propName : (this.currentPrefix + "." + propName);
    }
    
    public void pushTableAlias(final String prefix) {
        this.prefixStack.push(this.currentPrefix);
        this.currentPrefix = prefix;
        this.tableAliasStack.push(this.getTableAlias(prefix));
    }
    
    public void popTableAlias() {
        this.tableAliasStack.pop();
        this.currentPrefix = this.prefixStack.pop();
    }
    
    public StringBuilder getBuffer() {
        return this.sb;
    }
    
    public DefaultDbSqlContext append(final String s) {
        this.sb.append(s);
        return this;
    }
    
    public DefaultDbSqlContext append(final char s) {
        this.sb.append(s);
        return this;
    }
    
    public void appendFormulaJoin(final String sqlFormulaJoin, final boolean forceOuterJoin) {
        final String tableAlias = this.tableAliasStack.peek();
        final String converted = StringHelper.replaceString(sqlFormulaJoin, this.tableAliasPlaceHolder, tableAlias);
        if (this.formulaJoins == null) {
            this.formulaJoins = new HashSet<String>();
        }
        else if (this.formulaJoins.contains(converted)) {
            return;
        }
        this.formulaJoins.add(converted);
        this.sb.append("\n");
        if (forceOuterJoin && "join".equals(sqlFormulaJoin.substring(0, 4).toLowerCase())) {
            this.append(" left outer ");
        }
        this.sb.append(converted);
        this.sb.append(" ");
    }
    
    public void appendFormulaSelect(final String sqlFormulaSelect) {
        final String tableAlias = this.tableAliasStack.peek();
        final String converted = StringHelper.replaceString(sqlFormulaSelect, this.tableAliasPlaceHolder, tableAlias);
        this.sb.append(", ");
        this.sb.append(converted);
    }
    
    public void appendColumn(final String column) {
        this.appendColumn(this.tableAliasStack.peek(), column);
    }
    
    public void appendColumn(final String tableAlias, final String column) {
        this.sb.append(", ");
        if (column.indexOf("${}") > -1) {
            final String x = StringHelper.replaceString(column, "${}", tableAlias);
            this.sb.append(x);
        }
        else {
            this.sb.append(tableAlias);
            this.sb.append(".");
            this.sb.append(column);
        }
        if (this.useColumnAlias) {
            this.sb.append(" ");
            this.sb.append(this.columnAliasPrefix);
            this.sb.append(this.columnIndex);
        }
        ++this.columnIndex;
    }
    
    public String peekTableAlias() {
        return this.tableAliasStack.peek();
    }
    
    public void appendRawColumn(final String rawcolumnWithTableAlias) {
        this.sb.append(", ");
        this.sb.append(rawcolumnWithTableAlias);
        if (this.useColumnAlias) {
            this.sb.append(" ");
            this.sb.append(this.columnAliasPrefix);
            this.sb.append(this.columnIndex);
        }
        ++this.columnIndex;
    }
    
    public int length() {
        return this.sb.length();
    }
    
    public String getContent() {
        final String s = this.sb.toString();
        this.sb = new StringBuilder();
        return s;
    }
    
    public String toString() {
        return "DefaultDbSqlContext: " + this.sb.toString();
    }
}
