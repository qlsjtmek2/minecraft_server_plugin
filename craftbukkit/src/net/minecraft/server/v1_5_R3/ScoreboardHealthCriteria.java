// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;

public class ScoreboardHealthCriteria extends ScoreboardBaseCriteria
{
    public ScoreboardHealthCriteria(final String s) {
        super(s);
    }
    
    public int getScoreModifier(final List list) {
        float n = 0.0f;
        for (final EntityHuman entityHuman : list) {
            int n2 = entityHuman.getHealth();
            final float n3 = entityHuman.getMaxHealth();
            if (n2 < 0) {
                n2 = 0;
            }
            if (n2 > n3) {
                n2 = entityHuman.getMaxHealth();
            }
            n += n2 / n3;
        }
        if (list.size() > 0) {
            n /= list.size();
        }
        return MathHelper.d(n * 19.0f) + ((n > 0.0f) ? 1 : 0);
    }
    
    public boolean isReadOnly() {
        return true;
    }
}
