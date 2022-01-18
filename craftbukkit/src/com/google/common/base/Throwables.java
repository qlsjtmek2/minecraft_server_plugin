// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.google.common.annotations.Beta;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public final class Throwables
{
    public static <X extends Throwable> void propagateIfInstanceOf(@Nullable final Throwable throwable, final Class<X> declaredType) throws X, Throwable {
        if (throwable != null && declaredType.isInstance(throwable)) {
            throw declaredType.cast(throwable);
        }
    }
    
    public static void propagateIfPossible(@Nullable final Throwable throwable) {
        propagateIfInstanceOf(throwable, Error.class);
        propagateIfInstanceOf(throwable, RuntimeException.class);
    }
    
    public static <X extends Throwable> void propagateIfPossible(@Nullable final Throwable throwable, final Class<X> declaredType) throws X, Throwable {
        propagateIfInstanceOf(throwable, (Class<Throwable>)declaredType);
        propagateIfPossible(throwable);
    }
    
    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable final Throwable throwable, final Class<X1> declaredType1, final Class<X2> declaredType2) throws X1, X2, Throwable {
        Preconditions.checkNotNull(declaredType2);
        propagateIfInstanceOf(throwable, declaredType1);
        propagateIfPossible(throwable, declaredType2);
    }
    
    public static RuntimeException propagate(final Throwable throwable) {
        propagateIfPossible(Preconditions.checkNotNull(throwable));
        throw new RuntimeException(throwable);
    }
    
    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
        }
        return throwable;
    }
    
    @Beta
    public static List<Throwable> getCausalChain(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        final List<Throwable> causes = new ArrayList<Throwable>(4);
        while (throwable != null) {
            causes.add(throwable);
            throwable = throwable.getCause();
        }
        return Collections.unmodifiableList((List<? extends Throwable>)causes);
    }
    
    public static String getStackTraceAsString(final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
    
    @Deprecated
    @Beta
    public static Exception throwCause(final Exception exception, final boolean combineStackTraces) throws Exception {
        final Throwable cause = exception.getCause();
        if (cause == null) {
            throw exception;
        }
        if (combineStackTraces) {
            final StackTraceElement[] causeTrace = cause.getStackTrace();
            final StackTraceElement[] outerTrace = exception.getStackTrace();
            final StackTraceElement[] combined = new StackTraceElement[causeTrace.length + outerTrace.length];
            System.arraycopy(causeTrace, 0, combined, 0, causeTrace.length);
            System.arraycopy(outerTrace, 0, combined, causeTrace.length, outerTrace.length);
            cause.setStackTrace(combined);
        }
        if (cause instanceof Exception) {
            throw (Exception)cause;
        }
        if (cause instanceof Error) {
            throw (Error)cause;
        }
        throw exception;
    }
}
