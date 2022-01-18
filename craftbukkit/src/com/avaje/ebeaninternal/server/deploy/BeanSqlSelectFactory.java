// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

public class BeanSqlSelectFactory
{
    public static BeanSqlSelect create(String sql) {
        sql = trim(sql);
        final BeanSqlSelect.PredicatesType predicatesType = determinePredicatesType(sql);
        final boolean hasOrderBy = determineHasOrderBy(sql);
        return new BeanSqlSelect(sql, predicatesType, hasOrderBy);
    }
    
    private static boolean determineHasOrderBy(final String sql) {
        return sql.indexOf("${ORDER_BY}") > 0;
    }
    
    private static BeanSqlSelect.PredicatesType determinePredicatesType(final String sql) {
        if (sql.indexOf("${HAVING_PREDICATES}") > 0) {
            return BeanSqlSelect.PredicatesType.HAVING;
        }
        if (sql.indexOf("${WHERE_PREDICATES}") > 0) {
            return BeanSqlSelect.PredicatesType.WHERE;
        }
        if (sql.indexOf("${AND_PREDICATES}") > 0) {
            return BeanSqlSelect.PredicatesType.AND;
        }
        return BeanSqlSelect.PredicatesType.NONE;
    }
    
    private static String trim(final String sql) {
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
