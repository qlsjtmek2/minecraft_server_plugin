// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.util.TimerTask;
import java.net.MalformedURLException;
import java.util.UUID;
import java.util.HashMap;
import java.util.Timer;
import java.net.URL;
import java.util.Map;

public class MojangStatisticsGenerator
{
    private Map a;
    private final String b;
    private final URL c;
    private final IMojangStatistics d;
    private final Timer e;
    private final Object f;
    private final long g;
    private boolean h;
    private int i;
    
    public MojangStatisticsGenerator(final String s, final IMojangStatistics d) {
        this.a = new HashMap();
        this.b = UUID.randomUUID().toString();
        this.e = new Timer("Snooper Timer", true);
        this.f = new Object();
        this.g = System.currentTimeMillis();
        this.h = false;
        this.i = 0;
        try {
            this.c = new URL("http://snoop.minecraft.net/" + s + "?version=" + 1);
        }
        catch (MalformedURLException ex) {
            throw new IllegalArgumentException();
        }
        this.d = d;
    }
    
    public void a() {
        if (this.h) {
            return;
        }
        this.h = true;
        this.g();
        this.e.schedule(new MojangStatisticsTask(this), 0L, 900000L);
    }
    
    private void g() {
        this.h();
        this.a("snooper_token", this.b);
        this.a("os_name", System.getProperty("os.name"));
        this.a("os_version", System.getProperty("os.version"));
        this.a("os_architecture", System.getProperty("os.arch"));
        this.a("java_version", System.getProperty("java.version"));
        this.a("version", "1.5.2");
        this.d.b(this);
    }
    
    private void h() {
        final List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        int n = 0;
        for (final String s : inputArguments) {
            if (s.startsWith("-X")) {
                this.a("jvm_arg[" + n++ + "]", s);
            }
        }
        this.a("jvm_args", n);
    }
    
    public void b() {
        this.a("memory_total", Runtime.getRuntime().totalMemory());
        this.a("memory_max", Runtime.getRuntime().maxMemory());
        this.a("memory_free", Runtime.getRuntime().freeMemory());
        this.a("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.a("run_time", (System.currentTimeMillis() - this.g) / 60L * 1000L);
        this.d.a(this);
    }
    
    public void a(final String s, final Object o) {
        synchronized (this.f) {
            this.a.put(s, o);
        }
    }
    
    public boolean d() {
        return this.h;
    }
    
    public void e() {
        this.e.cancel();
    }
}
