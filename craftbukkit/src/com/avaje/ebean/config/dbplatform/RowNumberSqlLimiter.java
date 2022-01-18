// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class RowNumberSqlLimiter implements SqlLimiter
{
    private static final String ROW_NUMBER_OVER = "row_number() over (order by ";
    private static final String ROW_NUMBER_AS = ") as rn, ";
    final String rowNumberWindowAlias;
    
    public RowNumberSqlLimiter(final String rowNumberWindowAlias) {
        this.rowNumberWindowAlias = rowNumberWindowAlias;
    }
    
    public RowNumberSqlLimiter() {
        this("as limitresult");
    }
    
    public SqlLimitResponse limit(final SqlLimitRequest request) {
        final StringBuilder sb = new StringBuilder(500);
        final int firstRow = request.getFirstRow();
        int lastRow = request.getMaxRows();
        if (lastRow > 0) {
            lastRow = lastRow + firstRow + 1;
        }
        sb.append("select * from (").append('\n');
        sb.append("select ");
        if (request.isDistinct()) {
            sb.append("distinct ");
        }
        sb.append("row_number() over (order by ");
        sb.append(request.getDbOrderBy());
        sb.append(") as rn, ");
        sb.append(request.getDbSql());
        sb.append('\n').append(") ");
        sb.append(this.rowNumberWindowAlias);
        sb.append(" where ");
        if (firstRow > 0) {
            sb.append(" rn > ").append(firstRow);
            if (lastRow > 0) {
                sb.append(" and ");
            }
        }
        if (lastRow > 0) {
            sb.append(" rn <= ").append(lastRow);
        }
        return new SqlLimitResponse(sb.toString(), true);
    }
}
