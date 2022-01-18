// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.UUID;
import java.util.Timer;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.TimerTask;

class MojangStatisticsTask extends TimerTask
{
    final /* synthetic */ MojangStatisticsGenerator a;
    
    MojangStatisticsTask(final MojangStatisticsGenerator a) {
        this.a = a;
    }
    
    public void run() {
        if (!this.a.d.getSnooperEnabled()) {
            return;
        }
        final HashMap<String, Integer> hashMap;
        synchronized (this.a.f) {
            hashMap = new HashMap<String, Integer>(this.a.a);
            hashMap.put("snooper_count", this.a.i++);
        }
        HttpUtilities.a(this.a.d.getLogger(), this.a.c, hashMap, true);
    }
}
