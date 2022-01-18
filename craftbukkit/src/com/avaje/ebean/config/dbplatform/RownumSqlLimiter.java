// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class RownumSqlLimiter implements SqlLimiter
{
    private final String rnum;
    private final boolean useFirstRowsHint;
    
    public RownumSqlLimiter() {
        this("rn_", true);
    }
    
    public RownumSqlLimiter(final String rnum, final boolean useFirstRowsHint) {
        this.rnum = rnum;
        this.useFirstRowsHint = useFirstRowsHint;
    }
    
    public SqlLimitResponse limit(final SqlLimitRequest request) {
        final StringBuilder sb = new StringBuilder(500);
        final int firstRow = request.getFirstRow();
        int lastRow = request.getMaxRows();
        if (lastRow > 0) {
            lastRow = lastRow + firstRow + 1;
        }
        sb.append("select * ").append('\n').append("from ( ");
        sb.append("select ");
        if (this.useFirstRowsHint && request.getMaxRows() > 0) {
            sb.append("/*+ FIRST_ROWS(").append(request.getMaxRows() + 1).append(") */ ");
        }
        sb.append("rownum ").append(this.rnum).append(", a.* ").append('\n');
        sb.append("       from (");
        sb.append(" select ");
        if (request.isDistinct()) {
            sb.append("distinct ");
        }
        sb.append(request.getDbSql());
        sb.append('\n').append("            ) a ");
        if (lastRow > 0) {
            sb.append('\n').append("       where rownum <= ").append(lastRow);
        }
        sb.append('\n').append("     ) ");
        if (firstRow > 0) {
            sb.append('\n').append("where ");
            sb.append(this.rnum).append(" > ").append(firstRow);
        }
        return new SqlLimitResponse(sb.toString(), true);
    }
}
