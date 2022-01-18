// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IScoreboardCriteria
{
    public static final Map a = new HashMap();
    public static final IScoreboardCriteria b = new ScoreboardBaseCriteria("dummy");
    public static final IScoreboardCriteria c = new ScoreboardBaseCriteria("deathCount");
    public static final IScoreboardCriteria d = new ScoreboardBaseCriteria("playerKillCount");
    public static final IScoreboardCriteria e = new ScoreboardBaseCriteria("totalKillCount");
    public static final IScoreboardCriteria f = new ScoreboardHealthCriteria("health");
    
    String getName();
    
    int getScoreModifier(final List p0);
    
    boolean isReadOnly();
}
