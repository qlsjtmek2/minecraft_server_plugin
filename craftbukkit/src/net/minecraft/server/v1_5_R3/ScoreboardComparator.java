// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Comparator;

final class ScoreboardComparator implements Comparator
{
    public int a(final ScoreboardScore scoreboardScore, final ScoreboardScore scoreboardScore2) {
        if (scoreboardScore.getScore() > scoreboardScore2.getScore()) {
            return 1;
        }
        if (scoreboardScore.getScore() < scoreboardScore2.getScore()) {
            return -1;
        }
        return 0;
    }
}
