// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.logging.Level;
import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import com.google.common.collect.Lists;
import java.util.Queue;
import java.util.logging.Logger;

public final class ExecutionList
{
    private static final Logger log;
    private final Queue<RunnableExecutorPair> runnables;
    private boolean executed;
    
    public ExecutionList() {
        this.runnables = (Queue<RunnableExecutorPair>)Lists.newLinkedList();
        this.executed = false;
    }
    
    public void add(final Runnable runnable, final Executor executor) {
        Preconditions.checkNotNull(runnable, (Object)"Runnable was null.");
        Preconditions.checkNotNull(executor, (Object)"Executor was null.");
        boolean executeImmediate = false;
        synchronized (this.runnables) {
            if (!this.executed) {
                this.runnables.add(new RunnableExecutorPair(runnable, executor));
            }
            else {
                executeImmediate = true;
            }
        }
        if (executeImmediate) {
            new RunnableExecutorPair(runnable, executor).execute();
        }
    }
    
    @Deprecated
    @Beta
    public void run() {
        this.execute();
    }
    
    public void execute() {
        synchronized (this.runnables) {
            if (this.executed) {
                return;
            }
            this.executed = true;
        }
        while (!this.runnables.isEmpty()) {
            this.runnables.poll().execute();
        }
    }
    
    static {
        log = Logger.getLogger(ExecutionList.class.getName());
    }
    
    private static class RunnableExecutorPair
    {
        final Runnable runnable;
        final Executor executor;
        
        RunnableExecutorPair(final Runnable runnable, final Executor executor) {
            this.runnable = runnable;
            this.executor = executor;
        }
        
        void execute() {
            try {
                this.executor.execute(this.runnable);
            }
            catch (RuntimeException e) {
                ExecutionList.log.log(Level.SEVERE, "RuntimeException while executing runnable " + this.runnable + " with executor " + this.executor, e);
            }
        }
    }
}
