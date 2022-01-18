// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SharedConstants
{
    public static final String allowedCharacters;
    public static final char[] b;
    
    private static String a() {
        String string = "";
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(SharedConstants.class.getResourceAsStream("/font.txt"), "UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    string += line;
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex) {}
        return string;
    }
    
    public static final boolean isAllowedChatCharacter(final char c) {
        return c != '§' && (SharedConstants.allowedCharacters.indexOf(c) >= 0 || c > ' ');
    }
    
    public static String a(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (final char c : s.toCharArray()) {
            if (isAllowedChatCharacter(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    static {
        allowedCharacters = a();
        b = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
    }
}
