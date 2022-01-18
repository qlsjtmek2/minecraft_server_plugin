// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.List;
import java.util.Collections;
import java.util.regex.Pattern;

public class PlayerSelector
{
    private static final Pattern a;
    private static final Pattern b;
    private static final Pattern c;
    
    public static EntityPlayer getPlayer(final ICommandListener commandListener, final String s) {
        final EntityPlayer[] players = getPlayers(commandListener, s);
        if (players == null || players.length != 1) {
            return null;
        }
        return players[0];
    }
    
    public static String getPlayerNames(final ICommandListener commandListener, final String s) {
        final EntityPlayer[] players = getPlayers(commandListener, s);
        if (players == null || players.length == 0) {
            return null;
        }
        final String[] array = new String[players.length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = players[i].getScoreboardDisplayName();
        }
        return CommandAbstract.a((Object[])array);
    }
    
    public static EntityPlayer[] getPlayers(final ICommandListener commandListener, final String s) {
        final Matcher matcher = PlayerSelector.a.matcher(s);
        if (!matcher.matches()) {
            return null;
        }
        final Map h = h(matcher.group(2));
        final String group = matcher.group(1);
        int n = c(group);
        int n2 = d(group);
        int n3 = f(group);
        int n4 = e(group);
        int k = g(group);
        int n5 = EnumGamemode.NONE.a();
        final ChunkCoordinates b = commandListener.b();
        final Map a = a(h);
        String s2 = null;
        String s3 = null;
        if (h.containsKey("rm")) {
            n = MathHelper.a(h.get("rm"), n);
        }
        if (h.containsKey("r")) {
            n2 = MathHelper.a(h.get("r"), n2);
        }
        if (h.containsKey("lm")) {
            n3 = MathHelper.a(h.get("lm"), n3);
        }
        if (h.containsKey("l")) {
            n4 = MathHelper.a(h.get("l"), n4);
        }
        if (h.containsKey("x")) {
            b.x = MathHelper.a(h.get("x"), b.x);
        }
        if (h.containsKey("y")) {
            b.y = MathHelper.a(h.get("y"), b.y);
        }
        if (h.containsKey("z")) {
            b.z = MathHelper.a(h.get("z"), b.z);
        }
        if (h.containsKey("m")) {
            n5 = MathHelper.a(h.get("m"), n5);
        }
        if (h.containsKey("c")) {
            k = MathHelper.a(h.get("c"), k);
        }
        if (h.containsKey("team")) {
            s3 = h.get("team");
        }
        if (h.containsKey("name")) {
            s2 = h.get("name");
        }
        if (group.equals("p") || group.equals("a")) {
            final List a2 = MinecraftServer.getServer().getPlayerList().a(b, n, n2, k, n5, n3, n4, a, s2, s3);
            return (a2 == null || a2.isEmpty()) ? new EntityPlayer[0] : a2.toArray(new EntityPlayer[0]);
        }
        if (group.equals("r")) {
            final List a3 = MinecraftServer.getServer().getPlayerList().a(b, n, n2, 0, n5, n3, n4, a, s2, s3);
            Collections.shuffle(a3);
            final List<?> subList = a3.subList(0, Math.min(k, a3.size()));
            return (subList == null || subList.isEmpty()) ? new EntityPlayer[0] : subList.toArray(new EntityPlayer[0]);
        }
        return null;
    }
    
    public static Map a(final Map map) {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for (final String s : map.keySet()) {
            if (s.startsWith("score_") && s.length() > "score_".length()) {
                hashMap.put(s.substring("score_".length()), MathHelper.a((String)map.get(s), 1));
            }
        }
        return hashMap;
    }
    
    public static boolean isList(final String s) {
        final Matcher matcher = PlayerSelector.a.matcher(s);
        if (matcher.matches()) {
            final Map h = h(matcher.group(2));
            int n = g(matcher.group(1));
            if (h.containsKey("c")) {
                n = MathHelper.a(h.get("c"), n);
            }
            return n != 1;
        }
        return false;
    }
    
    public static boolean isPattern(final String s, final String s2) {
        final Matcher matcher = PlayerSelector.a.matcher(s);
        if (matcher.matches()) {
            final String group = matcher.group(1);
            return s2 == null || s2.equals(group);
        }
        return false;
    }
    
    public static boolean isPattern(final String s) {
        return isPattern(s, null);
    }
    
    private static final int c(final String s) {
        return 0;
    }
    
    private static final int d(final String s) {
        return 0;
    }
    
    private static final int e(final String s) {
        return Integer.MAX_VALUE;
    }
    
    private static final int f(final String s) {
        return 0;
    }
    
    private static final int g(final String s) {
        if (s.equals("a")) {
            return 0;
        }
        return 1;
    }
    
    private static Map h(final String s) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        if (s == null) {
            return hashMap;
        }
        final Matcher matcher = PlayerSelector.b.matcher(s);
        int n = 0;
        int end = -1;
        while (matcher.find()) {
            String s2 = null;
            switch (n++) {
                case 0: {
                    s2 = "x";
                    break;
                }
                case 1: {
                    s2 = "y";
                    break;
                }
                case 2: {
                    s2 = "z";
                    break;
                }
                case 3: {
                    s2 = "r";
                    break;
                }
            }
            if (s2 != null && matcher.group(1).length() > 0) {
                hashMap.put(s2, matcher.group(1));
            }
            end = matcher.end();
        }
        if (end < s.length()) {
            final Matcher matcher2 = PlayerSelector.c.matcher((end == -1) ? s : s.substring(end));
            while (matcher2.find()) {
                hashMap.put(matcher2.group(1), matcher2.group(2));
            }
        }
        return hashMap;
    }
    
    static {
        a = Pattern.compile("^@([parf])(?:\\[([\\w=,!-]*)\\])?$");
        b = Pattern.compile("\\G([-!]?\\w*)(?:$|,)");
        c = Pattern.compile("\\G(\\w+)=([-!]?\\w*)(?:$|,)");
    }
}
