// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.TxScope;

public class HelpScopeTrans
{
    public static ScopeTrans createScopeTrans(final TxScope txScope) {
        final EbeanServer server = Ebean.getServer(txScope.getServerName());
        final SpiEbeanServer iserver = (SpiEbeanServer)server;
        return iserver.createScopeTrans(txScope);
    }
    
    public static void onExitScopeTrans(final Object returnOrThrowable, final int opCode, final ScopeTrans scopeTrans) {
        scopeTrans.onExit(returnOrThrowable, opCode);
    }
}
