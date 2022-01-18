// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import java.util.List;
import com.avaje.ebeaninternal.server.expression.IdInExpression;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import java.util.Map;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.EbeanServer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IntersectionRow
{
    private final String tableName;
    private final LinkedHashMap<String, Object> values;
    private ArrayList<Object> excludeIds;
    private BeanDescriptor<?> excludeDescriptor;
    
    public IntersectionRow(final String tableName) {
        this.values = new LinkedHashMap<String, Object>();
        this.tableName = tableName;
    }
    
    public void setExcludeIds(final ArrayList<Object> excludeIds, final BeanDescriptor<?> excludeDescriptor) {
        this.excludeIds = excludeIds;
        this.excludeDescriptor = excludeDescriptor;
    }
    
    public void put(final String key, final Object value) {
        this.values.put(key, value);
    }
    
    public SqlUpdate createInsert(final EbeanServer server) {
        final BindParams bindParams = new BindParams();
        final StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(this.tableName).append(" (");
        int count = 0;
        final Iterator<Map.Entry<String, Object>> it = this.values.entrySet().iterator();
        while (it.hasNext()) {
            if (count++ > 0) {
                sb.append(", ");
            }
            final Map.Entry<String, Object> entry = it.next();
            sb.append(entry.getKey());
            bindParams.setParameter(count, entry.getValue());
        }
        sb.append(") values (");
        for (int i = 0; i < count; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(")");
        return new DefaultSqlUpdate(server, sb.toString(), bindParams);
    }
    
    public SqlUpdate createDelete(final EbeanServer server) {
        final BindParams bindParams = new BindParams();
        final StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(this.tableName).append(" where ");
        int count = 0;
        final Iterator<Map.Entry<String, Object>> it = this.values.entrySet().iterator();
        while (it.hasNext()) {
            if (count++ > 0) {
                sb.append(" and ");
            }
            final Map.Entry<String, Object> entry = it.next();
            sb.append(entry.getKey());
            sb.append(" = ?");
            bindParams.setParameter(count, entry.getValue());
        }
        if (this.excludeIds != null) {
            final IdInExpression idIn = new IdInExpression(this.excludeIds);
            final DefaultExpressionRequest er = new DefaultExpressionRequest(this.excludeDescriptor);
            idIn.addSqlNoAlias(er);
            idIn.addBindValues(er);
            sb.append(" and not ( ");
            sb.append(er.getSql());
            sb.append(" ) ");
            final ArrayList<Object> bindValues = er.getBindValues();
            for (int i = 0; i < bindValues.size(); ++i) {
                bindParams.setParameter(++count, bindValues.get(i));
            }
        }
        return new DefaultSqlUpdate(server, sb.toString(), bindParams);
    }
    
    public SqlUpdate createDeleteChildren(final EbeanServer server) {
        final BindParams bindParams = new BindParams();
        final StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(this.tableName).append(" where ");
        int count = 0;
        final Iterator<Map.Entry<String, Object>> it = this.values.entrySet().iterator();
        while (it.hasNext()) {
            if (count++ > 0) {
                sb.append(" and ");
            }
            final Map.Entry<String, Object> entry = it.next();
            sb.append(entry.getKey());
            sb.append(" = ?");
            bindParams.setParameter(count, entry.getValue());
        }
        return new DefaultSqlUpdate(server, sb.toString(), bindParams);
    }
}
