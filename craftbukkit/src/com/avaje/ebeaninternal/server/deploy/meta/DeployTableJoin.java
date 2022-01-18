// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import javax.persistence.JoinColumn;
import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
import java.util.ArrayList;

public class DeployTableJoin
{
    private boolean importedPrimaryKey;
    private String table;
    private String type;
    private ArrayList<DeployBeanProperty> properties;
    private ArrayList<DeployTableJoinColumn> columns;
    private BeanCascadeInfo cascadeInfo;
    
    public DeployTableJoin() {
        this.type = "join";
        this.properties = new ArrayList<DeployBeanProperty>();
        this.columns = new ArrayList<DeployTableJoinColumn>();
        this.cascadeInfo = new BeanCascadeInfo();
    }
    
    public String toString() {
        return this.type + " " + this.table + " " + this.columns;
    }
    
    public boolean isImportedPrimaryKey() {
        return this.importedPrimaryKey;
    }
    
    public void setImportedPrimaryKey(final boolean importedPrimaryKey) {
        this.importedPrimaryKey = importedPrimaryKey;
    }
    
    public boolean hasJoinColumns() {
        return this.columns.size() > 0;
    }
    
    public BeanCascadeInfo getCascadeInfo() {
        return this.cascadeInfo;
    }
    
    public void setColumns(final DeployTableJoinColumn[] cols, final boolean reverse) {
        this.columns = new ArrayList<DeployTableJoinColumn>();
        for (int i = 0; i < cols.length; ++i) {
            this.addJoinColumn(cols[i].copy(reverse));
        }
    }
    
    public void addJoinColumn(final DeployTableJoinColumn pair) {
        this.columns.add(pair);
    }
    
    public void addJoinColumn(final boolean order, final JoinColumn jc, final BeanTable beanTable) {
        if (!"".equals(jc.table())) {
            this.setTable(jc.table());
        }
        this.addJoinColumn(new DeployTableJoinColumn(order, jc, beanTable));
    }
    
    public void addJoinColumn(final boolean order, final JoinColumn[] jcArray, final BeanTable beanTable) {
        for (int i = 0; i < jcArray.length; ++i) {
            this.addJoinColumn(order, jcArray[i], beanTable);
        }
    }
    
    public DeployTableJoinColumn[] columns() {
        return this.columns.toArray(new DeployTableJoinColumn[this.columns.size()]);
    }
    
    public DeployBeanProperty[] properties() {
        return this.properties.toArray(new DeployBeanProperty[this.properties.size()]);
    }
    
    public String getTable() {
        return this.table;
    }
    
    public void setTable(final String table) {
        this.table = table;
    }
    
    public String getType() {
        return this.type;
    }
    
    public boolean isOuterJoin() {
        return this.type.equals("left outer join");
    }
    
    public void setType(String joinType) {
        joinType = joinType.toUpperCase();
        if (joinType.equalsIgnoreCase("join")) {
            this.type = "join";
        }
        else if (joinType.indexOf("LEFT") > -1) {
            this.type = "left outer join";
        }
        else if (joinType.indexOf("OUTER") > -1) {
            this.type = "left outer join";
        }
        else {
            if (joinType.indexOf("INNER") <= -1) {
                throw new RuntimeException(Message.msg("join.type.unknown", joinType));
            }
            this.type = "join";
        }
    }
    
    public DeployTableJoin createInverse(final String tableName) {
        final DeployTableJoin inverse = new DeployTableJoin();
        return this.copyTo(inverse, true, tableName);
    }
    
    public DeployTableJoin copyTo(final DeployTableJoin destJoin, final boolean reverse, final String tableName) {
        destJoin.setTable(tableName);
        destJoin.setType(this.type);
        destJoin.setColumns(this.columns(), reverse);
        return destJoin;
    }
}
