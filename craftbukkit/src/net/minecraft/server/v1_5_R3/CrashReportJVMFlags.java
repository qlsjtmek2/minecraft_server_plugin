// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Callable;

class CrashReportJVMFlags implements Callable
{
    final /* synthetic */ CrashReport a;
    
    CrashReportJVMFlags(final CrashReport a) {
        this.a = a;
    }
    
    public String a() {
        final List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        int n = 0;
        final StringBuilder sb = new StringBuilder();
        for (final String s : inputArguments) {
            if (s.startsWith("-X")) {
                if (n++ > 0) {
                    sb.append(" ");
                }
                sb.append(s);
            }
        }
        return String.format("%d total; %s", n, sb.toString());
    }
}
