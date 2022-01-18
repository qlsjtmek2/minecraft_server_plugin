// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.util.Set;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import java.util.TreeSet;
import java.io.Serializable;

public class ManyWhereJoins implements Serializable
{
    private static final long serialVersionUID = -6490181101871795417L;
    private final TreeSet<String> joins;
    
    public ManyWhereJoins() {
        this.joins = new TreeSet<String>();
    }
    
    public void add(final ElPropertyDeploy elProp) {
        String join = elProp.getElPrefix();
        final BeanProperty p = elProp.getBeanProperty();
        if (p instanceof BeanPropertyAssocMany) {
            join = this.addManyToJoin(join, p.getName());
        }
        if (join != null) {
            this.joins.add(join);
            final String secondaryTableJoinPrefix = p.getSecondaryTableJoinPrefix();
            if (secondaryTableJoinPrefix != null) {
                this.joins.add(join + "." + secondaryTableJoinPrefix);
            }
            this.addParentJoins(join);
        }
    }
    
    private String addManyToJoin(final String join, final String manyPropName) {
        if (join == null) {
            return manyPropName;
        }
        return join + "." + manyPropName;
    }
    
    private void addParentJoins(final String join) {
        final String[] split = SplitName.split(join);
        if (split[0] != null) {
            this.joins.add(split[0]);
            this.addParentJoins(split[0]);
        }
    }
    
    public boolean isEmpty() {
        return this.joins.isEmpty();
    }
    
    public Set<String> getJoins() {
        return this.joins;
    }
}
