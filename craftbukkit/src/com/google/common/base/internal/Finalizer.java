// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base.internal;

import java.lang.reflect.Method;
import java.lang.ref.Reference;
import java.util.logging.Level;
import java.lang.reflect.Field;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;
import java.util.logging.Logger;

public class Finalizer extends Thread
{
    private static final Logger logger;
    private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
    private final WeakReference<Class<?>> finalizableReferenceClassReference;
    private final PhantomReference<Object> frqReference;
    private final ReferenceQueue<Object> queue;
    private static final Field inheritableThreadLocals;
    
    public static ReferenceQueue<Object> startFinalizer(final Class<?> finalizableReferenceClass, final Object frq) {
        if (!finalizableReferenceClass.getName().equals("com.google.common.base.FinalizableReference")) {
            throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
        }
        final Finalizer finalizer = new Finalizer(finalizableReferenceClass, frq);
        finalizer.start();
        return finalizer.queue;
    }
    
    private Finalizer(final Class<?> finalizableReferenceClass, final Object frq) {
        super(Finalizer.class.getName());
        this.queue = new ReferenceQueue<Object>();
        this.finalizableReferenceClassReference = new WeakReference<Class<?>>(finalizableReferenceClass);
        this.frqReference = new PhantomReference<Object>(frq, this.queue);
        this.setDaemon(true);
        try {
            if (Finalizer.inheritableThreadLocals != null) {
                Finalizer.inheritableThreadLocals.set(this, null);
            }
        }
        catch (Throwable t) {
            Finalizer.logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", t);
        }
    }
    
    public void run() {
        try {
            try {
                while (true) {
                    this.cleanUp(this.queue.remove());
                }
            }
            catch (InterruptedException e) {}
        }
        catch (ShutDown shutDown) {}
    }
    
    private void cleanUp(Reference<?> reference) throws ShutDown {
        final Method finalizeReferentMethod = this.getFinalizeReferentMethod();
        do {
            reference.clear();
            if (reference == this.frqReference) {
                throw new ShutDown();
            }
            try {
                finalizeReferentMethod.invoke(reference, new Object[0]);
            }
            catch (Throwable t) {
                Finalizer.logger.log(Level.SEVERE, "Error cleaning up after reference.", t);
            }
        } while ((reference = this.queue.poll()) != null);
    }
    
    private Method getFinalizeReferentMethod() throws ShutDown {
        final Class<?> finalizableReferenceClass = this.finalizableReferenceClassReference.get();
        if (finalizableReferenceClass == null) {
            throw new ShutDown();
        }
        try {
            return finalizableReferenceClass.getMethod("finalizeReferent", (Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException e) {
            throw new AssertionError((Object)e);
        }
    }
    
    public static Field getInheritableThreadLocalsField() {
        try {
            final Field inheritableThreadLocals = Thread.class.getDeclaredField("inheritableThreadLocals");
            inheritableThreadLocals.setAccessible(true);
            return inheritableThreadLocals;
        }
        catch (Throwable t) {
            Finalizer.logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
            return null;
        }
    }
    
    static {
        logger = Logger.getLogger(Finalizer.class.getName());
        inheritableThreadLocals = getInheritableThreadLocalsField();
    }
    
    private static class ShutDown extends Exception
    {
    }
}
