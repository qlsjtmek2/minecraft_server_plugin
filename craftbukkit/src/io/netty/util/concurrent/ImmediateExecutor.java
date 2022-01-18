// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.Executor;

public final class ImmediateExecutor implements Executor
{
    public static final ImmediateExecutor INSTANCE;
    
    @Override
    public void execute(final Runnable command) {
        command.run();
    }
    
    static {
        INSTANCE = new ImmediateExecutor();
    }
}
