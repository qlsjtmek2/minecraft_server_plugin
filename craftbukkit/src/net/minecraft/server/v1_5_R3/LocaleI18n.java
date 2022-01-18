// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class LocaleI18n
{
    private static LocaleLanguage a;
    
    public static String get(final String s) {
        return LocaleI18n.a.a(s);
    }
    
    public static String get(final String s, final Object... aobject) {
        return LocaleI18n.a.a(s, aobject);
    }
    
    public static boolean b(final String s) {
        return LocaleI18n.a.b(s);
    }
    
    static {
        LocaleI18n.a = LocaleLanguage.a();
    }
}
