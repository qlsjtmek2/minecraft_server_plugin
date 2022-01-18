// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.Map;

public class PacketCounter
{
    public static boolean a;
    private static final Map b;
    private static final Map c;
    private static final Object d;
    
    public static void a(final int n, final long n2) {
        if (!PacketCounter.a) {
            return;
        }
        synchronized (PacketCounter.d) {
            if (PacketCounter.b.containsKey(n)) {
                PacketCounter.b.put(n, PacketCounter.b.get(n) + 1L);
                PacketCounter.c.put(n, PacketCounter.c.get(n) + n2);
            }
            else {
                PacketCounter.b.put(n, 1L);
                PacketCounter.c.put(n, n2);
            }
        }
    }
    
    static {
        PacketCounter.a = true;
        b = new HashMap();
        c = new HashMap();
        d = new Object();
    }
}
