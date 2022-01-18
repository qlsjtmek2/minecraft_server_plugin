// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilder;
import java.util.IllegalFormatException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.Properties;
import com.google.common.cache.Cache;

public class LocaleLanguage
{
    private static Cache<String, Properties> languages;
    private static LocaleLanguage a;
    private volatile Properties b;
    private static TreeMap c;
    private String e;
    
    public LocaleLanguage(final String s) {
        this.b = new Properties();
        this.a(s, false);
    }
    
    public static LocaleLanguage a() {
        return LocaleLanguage.a;
    }
    
    private static void e() {
        final TreeMap treemap = new TreeMap();
        try {
            final BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(LocaleLanguage.class.getResourceAsStream("/lang/languages.txt"), "UTF-8"));
            for (String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine()) {
                final String[] astring = s.trim().split("=");
                if (astring != null && astring.length == 2) {
                    treemap.put(astring[0], astring[1]);
                }
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            return;
        }
        (LocaleLanguage.c = treemap).put("en_US", "English (US)");
    }
    
    public TreeMap b() {
        return LocaleLanguage.c;
    }
    
    private static void a(final Properties properties, final String s) throws IOException {
        BufferedReader bufferedreader = null;
        bufferedreader = new BufferedReader(new InputStreamReader(LocaleLanguage.class.getResourceAsStream("/lang/" + s + ".lang"), "UTF-8"));
        try {
            for (String s2 = bufferedreader.readLine(); s2 != null; s2 = bufferedreader.readLine()) {
                s2 = s2.trim();
                if (!s2.startsWith("#")) {
                    final String[] astring = s2.split("=");
                    if (astring != null && astring.length == 2) {
                        properties.setProperty(astring[0], astring[1]);
                    }
                }
            }
        }
        finally {
            bufferedreader.close();
        }
    }
    
    public synchronized void a(final String s, final boolean flag) {
        if (flag || !s.equals(this.e)) {
            this.e = s;
            this.b = LocaleLanguage.languages.getUnchecked(s);
        }
    }
    
    private static Properties loadLanguage(final String s) {
        final Properties properties = new Properties();
        try {
            a(properties, "en_US");
        }
        catch (IOException ex) {}
        if (!"en_US".equals(s)) {
            try {
                a(properties, s);
            }
            catch (IOException ioexception1) {
                ioexception1.printStackTrace();
            }
        }
        return properties;
    }
    
    public String a(final String s) {
        return this.b.getProperty(s, s);
    }
    
    public String a(final String s, final Object... aobject) {
        final String s2 = this.b.getProperty(s, s);
        try {
            return String.format(s2, aobject);
        }
        catch (IllegalFormatException illegalformatexception) {
            return "Format error: " + s2;
        }
    }
    
    public boolean b(final String s) {
        return this.b.containsKey(s);
    }
    
    public String c(final String s) {
        return this.b.getProperty(s + ".name", "");
    }
    
    static {
        LocaleLanguage.languages = CacheBuilder.newBuilder().weakValues().build((CacheLoader<? super String, Properties>)new CacheLoader<String, Properties>() {
            public Properties load(final String key) {
                return loadLanguage(key);
            }
        });
        LocaleLanguage.a = new LocaleLanguage("en_US");
        e();
    }
}
