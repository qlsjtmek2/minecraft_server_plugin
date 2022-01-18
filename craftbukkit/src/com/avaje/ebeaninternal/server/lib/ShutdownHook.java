// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib;

class ShutdownHook extends Thread
{
    public void run() {
        ShutdownManager.shutdown();
    }
}
