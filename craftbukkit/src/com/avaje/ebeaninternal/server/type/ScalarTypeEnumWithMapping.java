// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.query.LuceneIndexDataReader;
import java.sql.SQLException;
import java.util.Iterator;

public class ScalarTypeEnumWithMapping extends ScalarTypeEnumStandard.EnumBase implements ScalarType, ScalarTypeEnum
{
    private final EnumToDbValueMap beanDbMap;
    private final int length;
    
    public ScalarTypeEnumWithMapping(final EnumToDbValueMap<?> beanDbMap, final Class<?> enumType, final int length) {
        super(enumType, false, beanDbMap.getDbType());
        this.beanDbMap = beanDbMap;
        this.length = length;
    }
    
    public String getContraintInValues() {
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("(");
        final Iterator<?> it = this.beanDbMap.dbValues();
        while (it.hasNext()) {
            final Object dbValue = it.next();
            if (i++ > 0) {
                sb.append(",");
            }
            if (!this.beanDbMap.isIntegerType()) {
                sb.append("'");
            }
            sb.append(dbValue.toString());
            if (!this.beanDbMap.isIntegerType()) {
                sb.append("'");
            }
        }
        sb.append(")");
        return sb.toString();
    }
    
    public int getLength() {
        return this.length;
    }
    
    public void bind(final DataBind b, final Object value) throws SQLException {
        this.beanDbMap.bind(b, value);
    }
    
    public Object read(final DataReader dataReader) throws SQLException {
        if (dataReader instanceof LuceneIndexDataReader) {
            final String s = dataReader.getString();
            return (s == null) ? null : this.parse(s);
        }
        return this.beanDbMap.read(dataReader);
    }
    
    public Object toBeanType(final Object dbValue) {
        return this.beanDbMap.getBeanValue(dbValue);
    }
    
    public Object toJdbcType(final Object beanValue) {
        return this.beanDbMap.getDbValue(beanValue);
    }
}
