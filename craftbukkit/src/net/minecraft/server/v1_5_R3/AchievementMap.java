// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AchievementMap
{
    public static AchievementMap a;
    private Map b;
    
    private AchievementMap() {
        this.b = new HashMap();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] split = line.split(",");
                this.b.put(Integer.parseInt(split[0]), split[1]);
            }
            bufferedReader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String a(final int n) {
        return AchievementMap.a.b.get(n);
    }
    
    static {
        AchievementMap.a = new AchievementMap();
    }
}
