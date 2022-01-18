// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

import javax.persistence.PersistenceException;

public class OrmQueryDetailParser
{
    private final OrmQueryDetail detail;
    private int maxRows;
    private int firstRow;
    private String rawWhereClause;
    private String rawOrderBy;
    private final SimpleTextParser parser;
    
    public OrmQueryDetailParser(final String oql) {
        this.detail = new OrmQueryDetail();
        this.parser = new SimpleTextParser(oql);
    }
    
    public void parse() throws PersistenceException {
        this.parser.nextWord();
        this.processInitial();
    }
    
    protected void assign(final DefaultOrmQuery<?> query) {
        query.setOrmQueryDetail(this.detail);
        query.setFirstRow(this.firstRow);
        query.setMaxRows(this.maxRows);
        query.setRawWhereClause(this.rawWhereClause);
        query.order(this.rawOrderBy);
    }
    
    private void processInitial() {
        if (this.parser.isMatch("find")) {
            final OrmQueryProperties props = this.readFindFetch();
            this.detail.setBase(props);
        }
        else {
            this.process();
        }
        while (!this.parser.isFinished()) {
            this.process();
        }
    }
    
    private boolean isFetch() {
        return this.parser.isMatch("fetch") || this.parser.isMatch("join");
    }
    
    private void process() {
        if (this.isFetch()) {
            final OrmQueryProperties props = this.readFindFetch();
            this.detail.putFetchPath(props);
        }
        else if (this.parser.isMatch("where")) {
            this.readWhere();
        }
        else if (this.parser.isMatch("order", "by")) {
            this.readOrderBy();
        }
        else {
            if (!this.parser.isMatch("limit")) {
                throw new PersistenceException("Query expected 'fetch', 'where','order by' or 'limit' keyword but got [" + this.parser.getWord() + "] \r " + this.parser.getOql());
            }
            this.readLimit();
        }
    }
    
    private void readLimit() {
        try {
            final String maxLimit = this.parser.nextWord();
            this.maxRows = Integer.parseInt(maxLimit);
            final String offsetKeyword = this.parser.nextWord();
            if (offsetKeyword != null) {
                if (!this.parser.isMatch("offset")) {
                    throw new PersistenceException("expected offset keyword but got " + this.parser.getWord());
                }
                final String firstRowLimit = this.parser.nextWord();
                this.firstRow = Integer.parseInt(firstRowLimit);
                this.parser.nextWord();
            }
        }
        catch (NumberFormatException e) {
            final String msg = "Expected an integer for maxRows or firstRows in limit offset clause";
            throw new PersistenceException(msg, e);
        }
    }
    
    private void readOrderBy() {
        this.parser.nextWord();
        final StringBuilder sb = new StringBuilder();
        while (this.parser.nextWord() != null && !this.parser.isMatch("limit")) {
            final String w = this.parser.getWord();
            if (!w.startsWith("(")) {
                sb.append(" ");
            }
            sb.append(w);
        }
        this.rawOrderBy = sb.toString().trim();
        if (!this.parser.isFinished()) {
            this.readLimit();
        }
    }
    
    private void readWhere() {
        int nextMode = 0;
        final StringBuilder sb = new StringBuilder();
        while (this.parser.nextWord() != null) {
            if (this.parser.isMatch("order", "by")) {
                nextMode = 1;
                break;
            }
            if (this.parser.isMatch("limit")) {
                nextMode = 2;
                break;
            }
            sb.append(" ").append(this.parser.getWord());
        }
        final String whereClause = sb.toString().trim();
        if (whereClause.length() > 0) {
            this.rawWhereClause = whereClause;
        }
        if (nextMode == 1) {
            this.readOrderBy();
        }
        else if (nextMode == 2) {
            this.readLimit();
        }
    }
    
    private OrmQueryProperties readFindFetch() {
        boolean readAlias = false;
        String props = null;
        final String path = this.parser.nextWord();
        String token = null;
        while ((token = this.parser.nextWord()) != null) {
            if (!readAlias && this.parser.isMatch("as")) {
                this.parser.nextWord();
                readAlias = true;
            }
            else {
                if ('(' == token.charAt(0)) {
                    props = token;
                    this.parser.nextWord();
                    break;
                }
                if (this.isFindFetchEnd()) {
                    break;
                }
                if (readAlias) {
                    throw new PersistenceException("Expected (props) or new 'fetch' 'where' but got " + token);
                }
                readAlias = true;
            }
        }
        if (props != null) {
            props = props.substring(1, props.length() - 1);
        }
        return new OrmQueryProperties(path, props);
    }
    
    private boolean isFindFetchEnd() {
        return this.isFetch() || this.parser.isMatch("where") || this.parser.isMatch("order", "by");
    }
}
