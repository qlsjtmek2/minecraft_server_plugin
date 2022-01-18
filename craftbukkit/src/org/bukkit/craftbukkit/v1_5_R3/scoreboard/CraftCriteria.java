// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

import java.util.Iterator;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.v1_5_R3.ScoreboardObjective;
import net.minecraft.server.v1_5_R3.IScoreboardCriteria;
import java.util.Map;

final class CraftCriteria
{
    static final Map<String, CraftCriteria> DEFAULTS;
    static final CraftCriteria DUMMY;
    final IScoreboardCriteria criteria;
    final String bukkitName;
    
    private CraftCriteria(final String bukkitName) {
        this.bukkitName = bukkitName;
        this.criteria = CraftCriteria.DUMMY.criteria;
    }
    
    private CraftCriteria(final IScoreboardCriteria criteria) {
        this.criteria = criteria;
        this.bukkitName = criteria.getName();
    }
    
    static CraftCriteria getFromNMS(final ScoreboardObjective objective) {
        return CraftCriteria.DEFAULTS.get(objective.getCriteria().getName());
    }
    
    static CraftCriteria getFromBukkit(final String name) {
        final CraftCriteria criteria = CraftCriteria.DEFAULTS.get(name);
        if (criteria != null) {
            return criteria;
        }
        return new CraftCriteria(name);
    }
    
    public boolean equals(final Object that) {
        return that instanceof CraftCriteria && ((CraftCriteria)that).bukkitName.equals(this.bukkitName);
    }
    
    public int hashCode() {
        return this.bukkitName.hashCode() ^ CraftCriteria.class.hashCode();
    }
    
    static {
        final ImmutableMap.Builder<String, CraftCriteria> defaults = ImmutableMap.builder();
        for (final Map.Entry<?, ?> entry : IScoreboardCriteria.a.entrySet()) {
            final String name = entry.getKey().toString();
            final IScoreboardCriteria criteria = (IScoreboardCriteria)entry.getValue();
            if (!criteria.getName().equals(name)) {
                throw new AssertionError((Object)("Unexpected entry " + name + " to criteria " + criteria + "(" + criteria.getName() + ")"));
            }
            defaults.put(name, new CraftCriteria(criteria));
        }
        DEFAULTS = defaults.build();
        DUMMY = CraftCriteria.DEFAULTS.get("dummy");
    }
}
