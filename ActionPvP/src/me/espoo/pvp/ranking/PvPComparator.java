package me.espoo.pvp.ranking;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import me.espoo.pvp.yml.PVPPlayer;

public class PvPComparator<T> implements Comparator<T> {
	public int compare(T arg1, T arg2) {
        PVPPlayer p1 = (PVPPlayer) arg1;
        PVPPlayer p2 = (PVPPlayer) arg2;
        final int delta1 = p1.getKill() - p2.getKill();
        if (delta1 > 0) {
            return -1;
        }
        if (delta1 != 0) {
            return 1;
        }
        final int delta2 = (int) (p1.getKillDivDeath() - p2.getKillDivDeath());
        if (delta2 > 0) {
            return -1;
        }
        if (delta2 != 0) {
            return 1;
        }
        final int delta3 = p1.getName().length() - p2.getName().length();
        if (delta3 > 0) {
            return -1;
        }
        if (delta3 != 0) {
            return 1;
        }
        final Set<String> set = new TreeSet<String>();
        set.add(p1.getName());
        set.add(p2.getName());
        if (set.toArray()[0].toString().equals(p1.getName())) {
            return -1;
        }
        return 1;
	}
}
