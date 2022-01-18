// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import com.avaje.ebeaninternal.server.querydefn.SimpleTextParser;

class DRawSqlParser
{
    public static final String $_AND_HAVING = "${andHaving}";
    public static final String $_HAVING = "${having}";
    public static final String $_AND_WHERE = "${andWhere}";
    public static final String $_WHERE = "${where}";
    private static final String ORDER_BY = "order by";
    private final SimpleTextParser textParser;
    private String sql;
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
    
    public static RawSql.Sql parse(final String sql) {
        return new DRawSqlParser(sql).parse();
    }
    
    private DRawSqlParser(final String sql) {
        this.selectPos = -1;
        this.fromPos = -1;
        this.wherePos = -1;
        this.groupByPos = -1;
        this.havingPos = -1;
        this.orderByPos = -1;
        this.whereExprPos = -1;
        this.havingExprPos = -1;
        this.sql = sql;
        this.hasPlaceHolders = this.findAndRemovePlaceHolders();
        this.textParser = new SimpleTextParser(sql);
    }
    
    private RawSql.Sql parse() {
        if (!this.hasPlaceHolders()) {
            this.parseSqlFindKeywords(true);
        }
        this.whereExprPos = this.findWhereExprPosition();
        this.havingExprPos = this.findHavingExprPosition();
        String preFrom = this.removeWhitespace(this.findPreFromSql());
        final String preWhere = this.removeWhitespace(this.findPreWhereSql());
        final String preHaving = this.removeWhitespace(this.findPreHavingSql());
        final String orderBySql = this.findOrderBySql();
        preFrom = this.trimSelectKeyword(preFrom);
        return new RawSql.Sql(this.sql.hashCode(), preFrom, preWhere, this.whereExprAnd, preHaving, this.havingExprAnd, orderBySql);
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
    
    private String trimSelectKeyword(String preWhereExprSql) {
        preWhereExprSql = preWhereExprSql.trim();
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
            return this.sql.substring(pos).trim();
        }
        return null;
    }
    
    private String findPreHavingSql() {
        if (this.havingExprPos > this.whereExprPos) {
            return this.sql.substring(this.whereExprPos, this.havingExprPos - 1);
        }
        if (this.whereExprPos <= -1) {
            return null;
        }
        if (this.orderByPos == -1) {
            return this.sql.substring(this.whereExprPos);
        }
        if (this.whereExprPos == this.orderByPos) {
            return "";
        }
        return this.sql.substring(this.whereExprPos, this.orderByPos - 1);
    }
    
    private String findPreFromSql() {
        return this.sql.substring(0, this.fromPos - 1);
    }
    
    private String findPreWhereSql() {
        if (this.whereExprPos > -1) {
            return this.sql.substring(this.fromPos, this.whereExprPos - 1);
        }
        return this.sql.substring(this.fromPos);
    }
    
    private void parseSqlFindKeywords(final boolean allKeywords) {
        this.selectPos = this.textParser.findWordLower("select");
        if (this.selectPos == -1) {
            final String msg = "Error parsing sql, can not find SELECT keyword in:";
            throw new RuntimeException(msg + this.sql);
        }
        this.fromPos = this.textParser.findWordLower("from");
        if (this.fromPos == -1) {
            final String msg = "Error parsing sql, can not find FROM keyword in:";
            throw new RuntimeException(msg + this.sql);
        }
        if (!allKeywords) {
            return;
        }
        this.wherePos = this.textParser.findWordLower("where");
        if (this.wherePos == -1) {
            this.groupByPos = this.textParser.findWordLower("group", this.fromPos + 5);
        }
        else {
            this.groupByPos = this.textParser.findWordLower("group");
        }
        if (this.groupByPos > -1) {
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
        final String s = sb.toString();
        return s.trim();
    }
}
