// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class ScoreboardBaseCriteria implements IScoreboardCriteria
{
    private final String g;
    
    public ScoreboardBaseCriteria(final String g) {
        this.g = g;
        IScoreboardCriteria.a.put(g, this);
    }
    
    public String getName() {
        return this.g;
    }
    
    public int getScoreModifier(final List list) {
        return 0;
    }
    
    public boolean isReadOnly() {
        return false;
    }
}
