// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.RawSql;
import java.util.List;
import com.avaje.ebeaninternal.server.type.RsetDataReaderIndexed;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.sql.ResultSet;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

public class CQueryPlanRawSql extends CQueryPlan
{
    private final int[] rsetIndexPositions;
    
    public CQueryPlanRawSql(final OrmQueryRequest<?> request, final SqlLimitResponse sqlRes, final SqlTree sqlTree, final String logWhereSql) {
        super(request, sqlRes, sqlTree, true, logWhereSql, null);
        this.rsetIndexPositions = this.createIndexPositions(request, sqlTree);
    }
    
    public DataReader createDataReader(final ResultSet rset) {
        return new RsetDataReaderIndexed(rset, this.rsetIndexPositions, this.isRowNumberIncluded());
    }
    
    private int[] createIndexPositions(final OrmQueryRequest<?> request, final SqlTree sqlTree) {
        final List<String> chain = sqlTree.buildSelectExpressionChain();
        final RawSql.ColumnMapping columnMapping = request.getQuery().getRawSql().getColumnMapping();
        final int[] indexPositions = new int[chain.size()];
        for (int i = 0; i < chain.size(); ++i) {
            final String expr = chain.get(i);
            final int indexPos = 1 + columnMapping.getIndexPosition(expr);
            indexPositions[i] = indexPos;
        }
        return indexPositions;
    }
}
