// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

import com.avaje.ebean.config.EncryptKey;
import java.util.Iterator;
import java.util.Collection;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.BindParams;

public class BindParamsParser
{
    public static final String ENCRYPTKEY_PREFIX = "encryptkey_";
    public static final String ENCRYPTKEY_GAP = "___";
    private static final int ENCRYPTKEY_PREFIX_LEN;
    private static final int ENCRYPTKEY_GAP_LEN;
    private static final String quote = "'";
    private static final String colon = ":";
    private final BindParams params;
    private final String sql;
    private final BeanDescriptor<?> beanDescriptor;
    
    public static String parse(final BindParams params, final String sql) {
        return parse(params, sql, null);
    }
    
    public static String parse(final BindParams params, final String sql, final BeanDescriptor<?> beanDescriptor) {
        return new BindParamsParser(params, sql, beanDescriptor).parseSql();
    }
    
    public static BindParams.OrderedList parseNamedParams(final BindParams params, final String sql) {
        return new BindParamsParser(params, sql, null).parseSqlNamedParams();
    }
    
    private BindParamsParser(final BindParams params, final String sql, final BeanDescriptor<?> beanDescriptor) {
        this.params = params;
        this.sql = sql;
        this.beanDescriptor = beanDescriptor;
    }
    
    private BindParams.OrderedList parseSqlNamedParams() {
        final BindParams.OrderedList orderedList = new BindParams.OrderedList();
        this.parseNamedParams(orderedList);
        return orderedList;
    }
    
    private String parseSql() {
        final String preparedSql = this.params.getPreparedSql();
        if (preparedSql != null && preparedSql.length() > 0) {
            return preparedSql;
        }
        String prepardSql;
        if (this.params.requiresNamedParamsPrepare()) {
            final BindParams.OrderedList orderedList = new BindParams.OrderedList(this.params.positionedParameters());
            this.parseNamedParams(orderedList);
            prepardSql = orderedList.getPreparedSql();
        }
        else {
            prepardSql = this.sql;
        }
        this.params.setPreparedSql(prepardSql);
        return prepardSql;
    }
    
    private void parseNamedParams(final BindParams.OrderedList orderedList) {
        this.parseNamedParams(0, orderedList);
    }
    
    private void parseNamedParams(final int startPos, final BindParams.OrderedList orderedList) {
        if (this.sql == null) {
            throw new PersistenceException("query does not contain any named bind parameters?");
        }
        if (startPos > this.sql.length()) {
            return;
        }
        final int beginQuotePos = this.sql.indexOf("'", startPos);
        final int nameParamStart = this.sql.indexOf(":", startPos);
        if (beginQuotePos > 0 && beginQuotePos < nameParamStart) {
            final int endQuotePos = this.sql.indexOf("'", beginQuotePos + 1);
            final String sub = this.sql.substring(startPos, endQuotePos + 1);
            orderedList.appendSql(sub);
            this.parseNamedParams(endQuotePos + 1, orderedList);
        }
        else if (nameParamStart < 0) {
            final String sub2 = this.sql.substring(startPos, this.sql.length());
            orderedList.appendSql(sub2);
        }
        else {
            int endOfParam = nameParamStart + 1;
            do {
                final char c = this.sql.charAt(endOfParam);
                if (c != '_' && !Character.isLetterOrDigit(c)) {
                    break;
                }
            } while (++endOfParam < this.sql.length());
            final String paramName = this.sql.substring(nameParamStart + 1, endOfParam);
            BindParams.Param param;
            if (paramName.startsWith("encryptkey_")) {
                param = this.addEncryptKeyParam(paramName);
            }
            else {
                param = this.params.getParameter(paramName);
            }
            if (param == null) {
                final String msg = "Bind value is not set or null for [" + paramName + "] in [" + this.sql + "]";
                throw new PersistenceException(msg);
            }
            final String sub3 = this.sql.substring(startPos, nameParamStart);
            orderedList.appendSql(sub3);
            final Object inValue = param.getInValue();
            if (inValue != null && inValue instanceof Collection) {
                final Collection<?> collection = (Collection<?>)inValue;
                final Iterator<?> it = collection.iterator();
                int c2 = 0;
                while (it.hasNext()) {
                    final Object elVal = it.next();
                    if (++c2 > 1) {
                        orderedList.appendSql(",");
                    }
                    orderedList.appendSql("?");
                    final BindParams.Param elParam = new BindParams.Param();
                    elParam.setInValue(elVal);
                    orderedList.add(elParam);
                }
            }
            else {
                orderedList.add(param);
                orderedList.appendSql("?");
            }
            this.parseNamedParams(endOfParam, orderedList);
        }
    }
    
    private BindParams.Param addEncryptKeyParam(final String keyNamedParam) {
        final int pos = keyNamedParam.indexOf("___", BindParamsParser.ENCRYPTKEY_PREFIX_LEN);
        final String tableName = keyNamedParam.substring(BindParamsParser.ENCRYPTKEY_PREFIX_LEN, pos);
        final String columnName = keyNamedParam.substring(pos + BindParamsParser.ENCRYPTKEY_GAP_LEN);
        final EncryptKey key = this.beanDescriptor.getEncryptKey(tableName, columnName);
        final String strKey = key.getStringValue();
        return this.params.setEncryptionKey(keyNamedParam, strKey);
    }
    
    static {
        ENCRYPTKEY_PREFIX_LEN = "encryptkey_".length();
        ENCRYPTKEY_GAP_LEN = "___".length();
    }
}
