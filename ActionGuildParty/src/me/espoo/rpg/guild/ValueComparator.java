package me.espoo.rpg.guild;

import java.util.Comparator;
import java.util.HashMap;

public class ValueComparator implements Comparator<String> {
    HashMap<String, Integer> base;
     
    public ValueComparator(HashMap<String, Integer> base) {
        this.base = base;
    }
 
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}