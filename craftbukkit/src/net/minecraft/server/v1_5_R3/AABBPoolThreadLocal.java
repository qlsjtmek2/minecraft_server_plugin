// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class AABBPoolThreadLocal extends ThreadLocal
{
    protected AABBPool a() {
        return new AABBPool(300, 2000);
    }
}
