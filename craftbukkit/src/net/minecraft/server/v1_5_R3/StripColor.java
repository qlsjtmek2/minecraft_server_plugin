// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.regex.Pattern;

public class StripColor
{
    private static final Pattern a;
    
    public static String a(final String s) {
        return StripColor.a.matcher(s).replaceAll("");
    }
    
    static {
        a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    }
}
