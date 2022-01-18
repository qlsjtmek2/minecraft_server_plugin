// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

final class DRawSqlColumnsParser
{
    private final int end;
    private final String sqlSelect;
    private int pos;
    private int indexPos;
    
    public static RawSql.ColumnMapping parse(final String sqlSelect) {
        return new DRawSqlColumnsParser(sqlSelect).parse();
    }
    
    private DRawSqlColumnsParser(final String sqlSelect) {
        this.sqlSelect = sqlSelect;
        this.end = sqlSelect.length();
    }
    
    private RawSql.ColumnMapping parse() {
        final ArrayList<RawSql.ColumnMapping.Column> columns = new ArrayList<RawSql.ColumnMapping.Column>();
        while (this.pos <= this.end) {
            final RawSql.ColumnMapping.Column c = this.nextColumnInfo();
            columns.add(c);
        }
        return new RawSql.ColumnMapping(columns);
    }
    
    private RawSql.ColumnMapping.Column nextColumnInfo() {
        final int start = this.pos;
        this.nextComma();
        String colInfo = this.sqlSelect.substring(start, this.pos++);
        colInfo = colInfo.trim();
        String[] split = colInfo.split(" ");
        if (split.length > 1) {
            final ArrayList<String> tmp = new ArrayList<String>(split.length);
            for (int i = 0; i < split.length; ++i) {
                if (split[i].trim().length() > 0) {
                    tmp.add(split[i].trim());
                }
            }
            split = tmp.toArray(new String[tmp.size()]);
        }
        if (split.length == 1) {
            return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], null);
        }
        if (split.length == 2) {
            return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], split[1]);
        }
        if (split.length != 3) {
            final String msg = "Expecting Max 3 words parsing column " + colInfo + ". Got " + Arrays.toString(split);
            throw new PersistenceException(msg);
        }
        if (!split[1].equalsIgnoreCase("as")) {
            final String msg = "Expecting AS keyword parsing column " + colInfo;
            throw new PersistenceException(msg);
        }
        return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], split[2]);
    }
    
    private int nextComma() {
        boolean inQuote = false;
        while (this.pos < this.end) {
            final char c = this.sqlSelect.charAt(this.pos);
            if (c == '\'') {
                inQuote = !inQuote;
            }
            else if (!inQuote && c == ',') {
                return this.pos;
            }
            ++this.pos;
        }
        return this.pos;
    }
}
