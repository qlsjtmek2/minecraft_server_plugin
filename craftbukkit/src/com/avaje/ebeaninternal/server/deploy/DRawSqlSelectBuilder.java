// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.List;
import com.avaje.ebeaninternal.server.querydefn.SimpleTextParser;
import com.avaje.ebean.config.NamingConvention;

public class DRawSqlSelectBuilder
{
    public static final String $_AND_HAVING = "${andHaving}";
    public static final String $_HAVING = "${having}";
    public static final String $_AND_WHERE = "${andWhere}";
    public static final String $_WHERE = "${where}";
    private static final String ORDER_BY = "order by";
    private final BeanDescriptor<?> desc;
    private final NamingConvention namingConvention;
    private final DRawSqlMeta meta;
    private final boolean debug;
    private String sql;
    private final SimpleTextParser textParser;
    private List<DRawSqlColumnInfo> selectColumns;
    private int placeHolderWhere;
    private int placeHolderAndWhere;
    private int placeHolderHaving;
    private int placeHolderAndHaving;
    private boolean hasPlaceHolders;
    private int selectPos;
    private int fromPos;
    private int wherePos;
    private int groupByPos;
    private int havingPos;
    private int orderByPos;
    private boolean whereExprAnd;
    private int whereExprPos;
    private boolean havingExprAnd;
    private int havingExprPos;
    private String tableAlias;
    
    public DRawSqlSelectBuilder(final NamingConvention namingConvention, final BeanDescriptor<?> desc, final DRawSqlMeta sqlSelectMeta) {
        this.selectPos = -1;
        this.fromPos = -1;
        this.wherePos = -1;
        this.groupByPos = -1;
        this.havingPos = -1;
        this.orderByPos = -1;
        this.whereExprPos = -1;
        this.havingExprPos = -1;
        this.namingConvention = namingConvention;
        this.desc = desc;
        this.tableAlias = sqlSelectMeta.getTableAlias();
        this.meta = sqlSelectMeta;
        this.debug = sqlSelectMeta.isDebug();
        this.sql = sqlSelectMeta.getQuery().trim();
        this.hasPlaceHolders = this.findAndRemovePlaceHolders();
        this.textParser = new SimpleTextParser(this.sql);
    }
    
    protected NamingConvention getNamingConvention() {
        return this.namingConvention;
    }
    
    protected BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }
    
    protected boolean isDebug() {
        return this.debug;
    }
    
    protected void debug(final String msg) {
        if (this.debug) {
            System.out.println("debug> " + msg);
        }
    }
    
    public DeployNamedQuery parse() {
        if (this.debug) {
            this.debug("");
            this.debug("Parsing sql-select in " + this.getErrName());
        }
        if (!this.hasPlaceHolders()) {
            this.parseSqlFindKeywords(true);
        }
        this.selectColumns = this.findSelectColumns(this.meta.getColumnMapping());
        this.whereExprPos = this.findWhereExprPosition();
        this.havingExprPos = this.findHavingExprPosition();
        String preWhereExprSql = this.removeWhitespace(this.findPreWhereExprSql());
        final String preHavingExprSql = this.removeWhitespace(this.findPreHavingExprSql());
        preWhereExprSql = this.trimSelectKeyword(preWhereExprSql);
        final String orderBySql = this.findOrderBySql();
        final DRawSqlSelect rawSqlSelect = new DRawSqlSelect(this.desc, this.selectColumns, this.tableAlias, preWhereExprSql, this.whereExprAnd, preHavingExprSql, this.havingExprAnd, orderBySql, this.meta);
        return new DeployNamedQuery(rawSqlSelect);
    }
    
    private boolean findAndRemovePlaceHolders() {
        this.placeHolderWhere = this.removePlaceHolder("${where}");
        this.placeHolderAndWhere = this.removePlaceHolder("${andWhere}");
        this.placeHolderHaving = this.removePlaceHolder("${having}");
        this.placeHolderAndHaving = this.removePlaceHolder("${andHaving}");
        return this.hasPlaceHolders();
    }
    
    private int removePlaceHolder(final String placeHolder) {
        final int pos = this.sql.indexOf(placeHolder);
        if (pos > -1) {
            final int after = pos + placeHolder.length() + 1;
            if (after > this.sql.length()) {
                this.sql = this.sql.substring(0, pos);
            }
            else {
                this.sql = this.sql.substring(0, pos) + this.sql.substring(after);
            }
        }
        return pos;
    }
    
    private boolean hasPlaceHolders() {
        return this.placeHolderWhere > -1 || this.placeHolderAndWhere > -1 || this.placeHolderHaving > -1 || this.placeHolderAndHaving > -1;
    }
    
    private String trimSelectKeyword(final String preWhereExprSql) {
        if (preWhereExprSql.length() < 7) {
            throw new RuntimeException("Expecting at least 7 chars in [" + preWhereExprSql + "]");
        }
        final String select = preWhereExprSql.substring(0, 7);
        if (!select.equalsIgnoreCase("select ")) {
            throw new RuntimeException("Expecting [" + preWhereExprSql + "] to start with \"select\"");
        }
        return preWhereExprSql.substring(7);
    }
    
    private String findOrderBySql() {
        if (this.orderByPos > -1) {
            final int pos = this.orderByPos + "order by".length();
            return this.sql.substring(pos);
        }
        return null;
    }
    
    private String findPreHavingExprSql() {
        if (this.havingExprPos > this.whereExprPos) {
            return this.sql.substring(this.whereExprPos, this.havingExprPos - 1);
        }
        if (this.whereExprPos > -1) {
            return this.sql.substring(this.whereExprPos);
        }
        return null;
    }
    
    private String findPreWhereExprSql() {
        if (this.whereExprPos > -1) {
            return this.sql.substring(0, this.whereExprPos - 1);
        }
        return this.sql;
    }
    
    protected String getErrName() {
        return "entity[" + this.desc.getFullName() + "] query[" + this.meta.getName() + "]";
    }
    
    private List<DRawSqlColumnInfo> findSelectColumns(String selectClause) {
        if (selectClause == null || selectClause.trim().length() == 0) {
            if (this.hasPlaceHolders) {
                if (this.debug) {
                    this.debug("... No explicit ColumnMapping, so parse the sql looking for SELECT and FROM keywords.");
                }
                this.parseSqlFindKeywords(false);
            }
            if (this.selectPos == -1 || this.fromPos == -1) {
                String msg = "Error in [" + this.getErrName() + "] parsing sql looking ";
                msg += "for SELECT and FROM keywords.";
                msg = msg + " select:" + this.selectPos + " from:" + this.fromPos;
                msg += ".  You could use an explicit columnMapping to bypass this error.";
                throw new RuntimeException(msg);
            }
            this.selectPos += "select".length();
            selectClause = this.sql.substring(this.selectPos, this.fromPos);
        }
        selectClause = selectClause.trim();
        if (this.debug) {
            this.debug("ColumnMapping ... [" + selectClause + "]");
        }
        return new DRawSqlSelectColumnsParser(this, selectClause).parse();
    }
    
    private void parseSqlFindKeywords(final boolean allKeywords) {
        this.debug("Parsing query looking for SELECT...");
        this.selectPos = this.textParser.findWordLower("select");
        if (this.selectPos == -1) {
            final String msg = "Error in " + this.getErrName() + " parsing sql, can not find SELECT keyword in:";
            throw new RuntimeException(msg + this.sql);
        }
        this.debug("Parsing query looking for FROM... SELECT found at " + this.selectPos);
        this.fromPos = this.textParser.findWordLower("from");
        if (this.fromPos == -1) {
            final String msg = "Error in " + this.getErrName() + " parsing sql, can not find FROM keyword in:";
            throw new RuntimeException(msg + this.sql);
        }
        if (!allKeywords) {
            return;
        }
        this.debug("Parsing query looking for WHERE... FROM found at " + this.fromPos);
        this.wherePos = this.textParser.findWordLower("where");
        if (this.wherePos == -1) {
            this.debug("Parsing query looking for GROUP... no WHERE found");
            this.groupByPos = this.textParser.findWordLower("group", this.fromPos + 5);
        }
        else {
            this.debug("Parsing query looking for GROUP... WHERE found at " + this.wherePos);
            this.groupByPos = this.textParser.findWordLower("group");
        }
        if (this.groupByPos > -1) {
            this.debug("Parsing query looking for HAVING... GROUP found at " + this.groupByPos);
            this.havingPos = this.textParser.findWordLower("having");
        }
        int startOrderBy = this.havingPos;
        if (startOrderBy == -1) {
            startOrderBy = this.groupByPos;
        }
        if (startOrderBy == -1) {
            startOrderBy = this.wherePos;
        }
        if (startOrderBy == -1) {
            startOrderBy = this.fromPos;
        }
        this.debug("Parsing query looking for ORDER... starting at " + startOrderBy);
        this.orderByPos = this.textParser.findWordLower("order", startOrderBy);
    }
    
    private int findWhereExprPosition() {
        if (this.hasPlaceHolders) {
            if (this.placeHolderWhere > -1) {
                return this.placeHolderWhere;
            }
            this.whereExprAnd = true;
            return this.placeHolderAndWhere;
        }
        else {
            this.whereExprAnd = (this.wherePos > 0);
            if (this.groupByPos > 0) {
                return this.groupByPos;
            }
            if (this.havingPos > 0) {
                return this.havingPos;
            }
            if (this.orderByPos > 0) {
                return this.orderByPos;
            }
            return -1;
        }
    }
    
    private int findHavingExprPosition() {
        if (this.hasPlaceHolders) {
            if (this.placeHolderHaving > -1) {
                return this.placeHolderHaving;
            }
            this.havingExprAnd = true;
            return this.placeHolderAndHaving;
        }
        else {
            this.havingExprAnd = (this.havingPos > 0);
            if (this.orderByPos > 0) {
                return this.orderByPos;
            }
            return -1;
        }
    }
    
    private String removeWhitespace(final String sql) {
        if (sql == null) {
            return "";
        }
        boolean removeWhitespace = false;
        final int length = sql.length();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            final char c = sql.charAt(i);
            if (removeWhitespace) {
                if (!Character.isWhitespace(c)) {
                    sb.append(c);
                    removeWhitespace = false;
                }
            }
            else if (c == '\r' || c == '\n') {
                sb.append('\n');
                removeWhitespace = true;
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
