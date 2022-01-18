// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import java.util.HashMap;

public class DeployBeanInfo<T>
{
    private final HashMap<String, DeployTableJoin> tableJoinMap;
    private final DeployUtil util;
    private final DeployBeanDescriptor<T> descriptor;
    
    public DeployBeanInfo(final DeployUtil util, final DeployBeanDescriptor<T> descriptor) {
        this.tableJoinMap = new HashMap<String, DeployTableJoin>();
        this.util = util;
        this.descriptor = descriptor;
    }
    
    public String toString() {
        return "" + this.descriptor;
    }
    
    public DeployBeanDescriptor<T> getDescriptor() {
        return this.descriptor;
    }
    
    public DeployUtil getUtil() {
        return this.util;
    }
    
    public DeployTableJoin getTableJoin(final String tableName) {
        final String key = tableName.toLowerCase();
        DeployTableJoin tableJoin = this.tableJoinMap.get(key);
        if (tableJoin == null) {
            tableJoin = new DeployTableJoin();
            tableJoin.setTable(tableName);
            tableJoin.setType("join");
            this.descriptor.addTableJoin(tableJoin);
            this.tableJoinMap.put(key, tableJoin);
        }
        return tableJoin;
    }
    
    public void setBeanJoinType(final DeployBeanPropertyAssocOne<?> beanProp, final boolean outerJoin) {
        String joinType = "join";
        if (outerJoin) {
            joinType = "left outer join";
        }
        final DeployTableJoin tableJoin = beanProp.getTableJoin();
        tableJoin.setType(joinType);
    }
}
