// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Comparator;

class PackageNameComparator implements Comparator
{
    final /* synthetic */ CrashReportSuspiciousClasses a;
    
    PackageNameComparator(final CrashReportSuspiciousClasses a) {
        this.a = a;
    }
    
    public int a(final Class clazz, final Class clazz2) {
        return ((clazz.getPackage() == null) ? "" : clazz.getPackage().getName()).compareTo((clazz2.getPackage() == null) ? "" : clazz2.getPackage().getName());
    }
}
