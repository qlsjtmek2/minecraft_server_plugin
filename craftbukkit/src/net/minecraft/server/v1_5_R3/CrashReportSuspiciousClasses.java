// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Callable;

class CrashReportSuspiciousClasses implements Callable
{
    final /* synthetic */ CrashReport a;
    
    CrashReportSuspiciousClasses(final CrashReport a) {
        this.a = a;
    }
    
    public String a() {
        final StringBuilder sb = new StringBuilder();
        final Field declaredField = ClassLoader.class.getDeclaredField("classes");
        declaredField.setAccessible(true);
        final ArrayList list = new ArrayList<Object>((Collection<?>)declaredField.get(CrashReport.class.getClassLoader()));
        int n = 1;
        final boolean b = !CrashReport.class.getCanonicalName().equals("net.minecraft.CrashReport");
        final HashMap<String, Integer> hashMap = (HashMap<String, Integer>)new HashMap<Object, Integer>();
        String s = "";
        Collections.sort((List<E>)list, new PackageNameComparator(this));
        for (final Class clazz : list) {
            if (clazz == null) {
                continue;
            }
            final String canonicalName = clazz.getCanonicalName();
            if (canonicalName == null) {
                continue;
            }
            if (canonicalName.startsWith("org.lwjgl.")) {
                continue;
            }
            if (canonicalName.startsWith("paulscode.")) {
                continue;
            }
            if (canonicalName.startsWith("net.minecraft.v1_5_R3.org.bouncycastle.")) {
                continue;
            }
            if (canonicalName.startsWith("argo.")) {
                continue;
            }
            if (canonicalName.startsWith("com.jcraft.")) {
                continue;
            }
            if (canonicalName.startsWith("com.fasterxml.")) {
                continue;
            }
            if (canonicalName.equals("util.GLX")) {
                continue;
            }
            if (b) {
                if (canonicalName.length() <= 3) {
                    continue;
                }
                if (canonicalName.equals("net.minecraft.client.MinecraftApplet")) {
                    continue;
                }
                if (canonicalName.equals("net.minecraft.client.Minecraft")) {
                    continue;
                }
                if (canonicalName.equals("net.minecraft.client.ClientBrandRetriever")) {
                    continue;
                }
                if (canonicalName.equals("net.minecraft.server.v1_5_R3.MinecraftServer")) {
                    continue;
                }
            }
            else if (canonicalName.startsWith("net.minecraft")) {
                continue;
            }
            final Package package1 = clazz.getPackage();
            final String s2 = (package1 == null) ? "" : package1.getName();
            if (hashMap.containsKey(s2)) {
                final int intValue = hashMap.get(s2);
                hashMap.put(s2, intValue + 1);
                if (intValue == 3) {
                    if (n == 0) {
                        sb.append(", ");
                    }
                    sb.append("...");
                    n = 0;
                    continue;
                }
                if (intValue > 3) {
                    continue;
                }
            }
            else {
                hashMap.put(s2, 1);
            }
            if (s != s2 && s.length() > 0) {
                sb.append("], ");
            }
            if (n == 0 && s == s2) {
                sb.append(", ");
            }
            if (s != s2) {
                sb.append("[");
                sb.append(s2);
                sb.append(".");
            }
            sb.append(clazz.getSimpleName());
            s = s2;
            n = 0;
        }
        if (n != 0) {
            sb.append("No suspicious classes found.");
        }
        else {
            sb.append("]");
        }
        return sb.toString();
    }
}
