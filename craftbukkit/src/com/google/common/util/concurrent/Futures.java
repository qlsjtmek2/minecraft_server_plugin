// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import com.google.common.collect.Lists;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.BlockingQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Arrays;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import com.google.common.base.Function;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Preconditions;
import java.util.concurrent.Future;
import java.lang.reflect.Constructor;
import com.google.common.collect.Ordering;
import com.google.common.annotations.Beta;

@Beta
public final class Futures
{
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST;
    
    @Deprecated
    public static <V> UninterruptibleFuture<V> makeUninterruptible(final Future<V> future) {
        Preconditions.checkNotNull(future);
        if (future instanceof UninterruptibleFuture) {
            return (UninterruptibleFuture<V>)(UninterruptibleFuture)future;
        }
        return new UninterruptibleFuture<V>() {
            public boolean cancel(final boolean mayInterruptIfRunning) {
                return future.cancel(mayInterruptIfRunning);
            }
            
            public boolean isCancelled() {
                return future.isCancelled();
            }
            
            public boolean isDone() {
                return future.isDone();
            }
            
            public V get(final long timeout, final TimeUnit unit) throws TimeoutException, ExecutionException {
                return Uninterruptibles.getUninterruptibly(future, timeout, unit);
            }
            
            public V get() throws ExecutionException {
                return Uninterruptibles.getUninterruptibly(future);
            }
        };
    }
    
    @Deprecated
    public static <V> ListenableFuture<V> makeListenable(final Future<V> future) {
        return JdkFutureAdapters.listenInPoolThread(future);
    }
    
    @Deprecated
    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(final Future<V> future, final Function<Exception, X> mapper) {
        return new MappingCheckedFuture<V, X>(makeListenable(future), mapper);
    }
    
    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(final ListenableFuture<V> future, final Function<Exception, X> mapper) {
        return new MappingCheckedFuture<V, X>(Preconditions.checkNotNull(future), mapper);
    }
    
    public static <V> ListenableFuture<V> immediateFuture(@Nullable final V value) {
        final SettableFuture<V> future = SettableFuture.create();
        future.set(value);
        return future;
    }
    
    public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@Nullable final V value) {
        final SettableFuture<V> future = SettableFuture.create();
        future.set(value);
        return makeChecked(future, (Function<Exception, X>)new Function<Exception, X>() {
            public X apply(final Exception e) {
                throw new AssertionError((Object)"impossible");
            }
        });
    }
    
    public static <V> ListenableFuture<V> immediateFailedFuture(final Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        final SettableFuture<V> future = SettableFuture.create();
        future.setException(throwable);
        return future;
    }
    
    public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(final X exception) {
        Preconditions.checkNotNull(exception);
        return makeChecked(immediateFailedFuture(exception), (Function<Exception, X>)new Function<Exception, X>() {
            public X apply(final Exception e) {
                return exception;
            }
        });
    }
    
    public static <I, O> ListenableFuture<O> chain(final ListenableFuture<I> input, final Function<? super I, ? extends ListenableFuture<? extends O>> function) {
        return chain(input, function, (Executor)MoreExecutors.sameThreadExecutor());
    }
    
    public static <I, O> ListenableFuture<O> chain(final ListenableFuture<I> input, final Function<? super I, ? extends ListenableFuture<? extends O>> function, final Executor exec) {
        final ChainingListenableFuture<I, O> chain = new ChainingListenableFuture<I, O>((Function)function, (ListenableFuture)input);
        input.addListener(chain, exec);
        return (ListenableFuture<O>)chain;
    }
    
    public static <I, O> ListenableFuture<O> transform(final ListenableFuture<I> future, final Function<? super I, ? extends O> function) {
        return transform(future, function, (Executor)MoreExecutors.sameThreadExecutor());
    }
    
    public static <I, O> ListenableFuture<O> transform(final ListenableFuture<I> future, final Function<? super I, ? extends O> function, final Executor exec) {
        Preconditions.checkNotNull(function);
        final Function<I, ListenableFuture<O>> wrapperFunction = new Function<I, ListenableFuture<O>>() {
            public ListenableFuture<O> apply(final I input) {
                final O output = function.apply(input);
                return Futures.immediateFuture(output);
            }
        };
        return (ListenableFuture<O>)chain((ListenableFuture<Object>)future, (Function<? super Object, ? extends ListenableFuture<?>>)wrapperFunction, exec);
    }
    
    @Beta
    public static <I, O> Future<O> lazyTransform(final Future<I> future, final Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(function);
        return new Future<O>() {
            public boolean cancel(final boolean mayInterruptIfRunning) {
                return future.cancel(mayInterruptIfRunning);
            }
            
            public boolean isCancelled() {
                return future.isCancelled();
            }
            
            public boolean isDone() {
                return future.isDone();
            }
            
            public O get() throws InterruptedException, ExecutionException {
                return this.applyTransformation(future.get());
            }
            
            public O get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return this.applyTransformation(future.get(timeout, unit));
            }
            
            private O applyTransformation(final I input) throws ExecutionException {
                try {
                    return function.apply(input);
                }
                catch (Throwable t) {
                    throw new ExecutionException(t);
                }
            }
        };
    }
    
    @Deprecated
    public static <I, O> Future<O> transform(final Future<I> future, final Function<? super I, ? extends O> function) {
        if (future instanceof ListenableFuture) {
            return (Future<O>)transform((ListenableFuture<Object>)(ListenableFuture)future, (Function<? super Object, ?>)function);
        }
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(function);
        return new Future<O>() {
            private final Object lock = new Object();
            private boolean set = false;
            private O value = null;
            private ExecutionException exception = null;
            
            public O get() throws InterruptedException, ExecutionException {
                return this.apply(future.get());
            }
            
            public O get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return this.apply(future.get(timeout, unit));
            }
            
            private O apply(final I raw) throws ExecutionException {
                synchronized (this.lock) {
                    if (!this.set) {
                        try {
                            this.value = function.apply(raw);
                        }
                        catch (RuntimeException e) {
                            this.exception = new ExecutionException(e);
                        }
                        catch (Error e2) {
                            this.exception = new ExecutionException(e2);
                        }
                        this.set = true;
                    }
                    if (this.exception != null) {
                        throw this.exception;
                    }
                    return this.value;
                }
            }
            
            public boolean cancel(final boolean mayInterruptIfRunning) {
                return future.cancel(mayInterruptIfRunning);
            }
            
            public boolean isCancelled() {
                return future.isCancelled();
            }
            
            public boolean isDone() {
                return future.isDone();
            }
        };
    }
    
    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(final ListenableFuture<? extends V>... futures) {
        return (ListenableFuture<List<V>>)new ListFuture(ImmutableList.copyOf((ListenableFuture<? extends V>[])futures), true, MoreExecutors.sameThreadExecutor());
    }
    
    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(final Iterable<? extends ListenableFuture<? extends V>> futures) {
        return (ListenableFuture<List<V>>)new ListFuture(ImmutableList.copyOf((Iterable<? extends ListenableFuture<?>>)futures), true, MoreExecutors.sameThreadExecutor());
    }
    
    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(final ListenableFuture<? extends V>... futures) {
        return (ListenableFuture<List<V>>)new ListFuture(ImmutableList.copyOf((ListenableFuture<? extends V>[])futures), false, MoreExecutors.sameThreadExecutor());
    }
    
    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(final Iterable<? extends ListenableFuture<? extends V>> futures) {
        return (ListenableFuture<List<V>>)new ListFuture(ImmutableList.copyOf((Iterable<? extends ListenableFuture<?>>)futures), false, MoreExecutors.sameThreadExecutor());
    }
    
    public static <V> void addCallback(final ListenableFuture<V> future, final FutureCallback<? super V> callback) {
        addCallback(future, callback, MoreExecutors.sameThreadExecutor());
    }
    
    public static <V> void addCallback(final ListenableFuture<V> future, final FutureCallback<? super V> callback, final Executor executor) {
        Preconditions.checkNotNull(callback);
        final Runnable callbackListener = new Runnable() {
            public void run() {
                try {
                    final V value = Uninterruptibles.getUninterruptibly(future);
                    callback.onSuccess(value);
                }
                catch (ExecutionException e) {
                    callback.onFailure(e.getCause());
                }
                catch (RuntimeException e2) {
                    callback.onFailure(e2);
                }
                catch (Error e3) {
                    callback.onFailure(e3);
                }
            }
        };
        future.addListener(callbackListener, executor);
    }
    
    @Beta
    public static <V, X extends Exception> V get(final Future<V> future, final Class<X> exceptionClass) throws X, Exception {
        Preconditions.checkNotNull(future);
        Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", exceptionClass);
        try {
            return future.get();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        }
        catch (ExecutionException e2) {
            wrapAndThrowExceptionOrError(e2.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }
    
    @Beta
    public static <V, X extends Exception> V get(final Future<V> future, final long timeout, final TimeUnit unit, final Class<X> exceptionClass) throws X, Exception {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(unit);
        Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", exceptionClass);
        try {
            return future.get(timeout, unit);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        }
        catch (TimeoutException e2) {
            throw newWithCause(exceptionClass, e2);
        }
        catch (ExecutionException e3) {
            wrapAndThrowExceptionOrError(e3.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }
    
    private static <X extends Exception> void wrapAndThrowExceptionOrError(final Throwable cause, final Class<X> exceptionClass) throws X, Exception {
        if (cause instanceof Error) {
            throw new ExecutionError((Error)cause);
        }
        if (cause instanceof RuntimeException) {
            throw new UncheckedExecutionException(cause);
        }
        throw newWithCause(exceptionClass, cause);
    }
    
    @Beta
    public static <V> V getUnchecked(final Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return Uninterruptibles.getUninterruptibly(future);
        }
        catch (ExecutionException e) {
            wrapAndThrowUnchecked(e.getCause());
            throw new AssertionError();
        }
    }
    
    private static void wrapAndThrowUnchecked(final Throwable cause) {
        if (cause instanceof Error) {
            throw new ExecutionError((Error)cause);
        }
        throw new UncheckedExecutionException(cause);
    }
    
    private static <X extends Exception> X newWithCause(final Class<X> exceptionClass, final Throwable cause) {
        final List<Constructor<X>> constructors = Arrays.asList((Constructor<X>[])exceptionClass.getConstructors());
        for (final Constructor<X> constructor : preferringStrings(constructors)) {
            final X instance = newFromConstructor(constructor, cause);
            if (instance != null) {
                if (instance.getCause() == null) {
                    instance.initCause(cause);
                }
                return instance;
            }
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + exceptionClass + " in response to chained exception", cause);
    }
    
    private static <X extends Exception> List<Constructor<X>> preferringStrings(final List<Constructor<X>> constructors) {
        return Futures.WITH_STRING_PARAM_FIRST.sortedCopy(constructors);
    }
    
    @Nullable
    private static <X> X newFromConstructor(final Constructor<X> constructor, final Throwable cause) {
        final Class<?>[] paramTypes = constructor.getParameterTypes();
        final Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; ++i) {
            final Class<?> paramType = paramTypes[i];
            if (paramType.equals(String.class)) {
                params[i] = cause.toString();
            }
            else {
                if (!paramType.equals(Throwable.class)) {
                    return null;
                }
                params[i] = cause;
            }
        }
        try {
            return constructor.newInstance(params);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
        catch (InstantiationException e2) {
            return null;
        }
        catch (IllegalAccessException e3) {
            return null;
        }
        catch (InvocationTargetException e4) {
            return null;
        }
    }
    
    static {
        WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf((Function<Object, ? extends Comparable>)new Function<Constructor<?>, Boolean>() {
            public Boolean apply(final Constructor<?> input) {
                return Arrays.asList(input.getParameterTypes()).contains(String.class);
            }
        }).reverse();
    }
    
    private static class ChainingListenableFuture<I, O> extends AbstractFuture<O> implements Runnable
    {
        private Function<? super I, ? extends ListenableFuture<? extends O>> function;
        private ListenableFuture<? extends I> inputFuture;
        private volatile ListenableFuture<? extends O> outputFuture;
        private final BlockingQueue<Boolean> mayInterruptIfRunningChannel;
        private final CountDownLatch outputCreated;
        
        private ChainingListenableFuture(final Function<? super I, ? extends ListenableFuture<? extends O>> function, final ListenableFuture<? extends I> inputFuture) {
            this.mayInterruptIfRunningChannel = new LinkedBlockingQueue<Boolean>(1);
            this.outputCreated = new CountDownLatch(1);
            this.function = Preconditions.checkNotNull(function);
            this.inputFuture = Preconditions.checkNotNull(inputFuture);
        }
        
        public O get() throws InterruptedException, ExecutionException {
            if (!this.isDone()) {
                final ListenableFuture<? extends I> inputFuture = this.inputFuture;
                if (inputFuture != null) {
                    inputFuture.get();
                }
                this.outputCreated.await();
                final ListenableFuture<? extends O> outputFuture = this.outputFuture;
                if (outputFuture != null) {
                    outputFuture.get();
                }
            }
            return super.get();
        }
        
        public O get(long timeout, TimeUnit unit) throws TimeoutException, ExecutionException, InterruptedException {
            if (!this.isDone()) {
                if (unit != TimeUnit.NANOSECONDS) {
                    timeout = TimeUnit.NANOSECONDS.convert(timeout, unit);
                    unit = TimeUnit.NANOSECONDS;
                }
                final ListenableFuture<? extends I> inputFuture = this.inputFuture;
                if (inputFuture != null) {
                    final long start = System.nanoTime();
                    inputFuture.get(timeout, unit);
                    timeout -= Math.max(0L, System.nanoTime() - start);
                }
                final long start = System.nanoTime();
                if (!this.outputCreated.await(timeout, unit)) {
                    throw new TimeoutException();
                }
                timeout -= Math.max(0L, System.nanoTime() - start);
                final ListenableFuture<? extends O> outputFuture = this.outputFuture;
                if (outputFuture != null) {
                    outputFuture.get(timeout, unit);
                }
            }
            return super.get(timeout, unit);
        }
        
        public boolean cancel(final boolean mayInterruptIfRunning) {
            if (super.cancel(mayInterruptIfRunning)) {
                Uninterruptibles.putUninterruptibly(this.mayInterruptIfRunningChannel, mayInterruptIfRunning);
                this.cancel(this.inputFuture, mayInterruptIfRunning);
                this.cancel(this.outputFuture, mayInterruptIfRunning);
                return true;
            }
            return false;
        }
        
        private void cancel(@Nullable final Future<?> future, final boolean mayInterruptIfRunning) {
            if (future != null) {
                future.cancel(mayInterruptIfRunning);
            }
        }
        
        public void run() {
            try {
                I sourceResult;
                try {
                    sourceResult = Uninterruptibles.getUninterruptibly((Future<I>)this.inputFuture);
                }
                catch (CancellationException e5) {
                    this.cancel(false);
                    return;
                }
                catch (ExecutionException e) {
                    this.setException(e.getCause());
                    return;
                }
                final ListenableFuture<? extends O> outputFuture2 = (ListenableFuture<? extends O>)this.function.apply((Object)sourceResult);
                this.outputFuture = outputFuture2;
                final ListenableFuture<? extends O> outputFuture = outputFuture2;
                if (this.isCancelled()) {
                    outputFuture.cancel(Uninterruptibles.takeUninterruptibly(this.mayInterruptIfRunningChannel));
                    this.outputFuture = null;
                    return;
                }
                outputFuture.addListener(new Runnable() {
                    public void run() {
                        try {
                            ChainingListenableFuture.this.set(Uninterruptibles.getUninterruptibly((Future<V>)outputFuture));
                        }
                        catch (CancellationException e2) {
                            ChainingListenableFuture.this.cancel(false);
                        }
                        catch (ExecutionException e) {
                            ChainingListenableFuture.this.setException(e.getCause());
                        }
                        finally {
                            ChainingListenableFuture.this.outputFuture = null;
                        }
                    }
                }, MoreExecutors.sameThreadExecutor());
            }
            catch (UndeclaredThrowableException e2) {
                this.setException(e2.getCause());
            }
            catch (RuntimeException e3) {
                this.setException(e3);
            }
            catch (Error e4) {
                this.setException(e4);
            }
            finally {
                this.function = null;
                this.inputFuture = null;
                this.outputCreated.countDown();
            }
        }
    }
    
    private static class ListFuture<V> extends AbstractFuture<List<V>>
    {
        ImmutableList<? extends ListenableFuture<? extends V>> futures;
        final boolean allMustSucceed;
        final AtomicInteger remaining;
        List<V> values;
        
        ListFuture(final ImmutableList<? extends ListenableFuture<? extends V>> futures, final boolean allMustSucceed, final Executor listenerExecutor) {
            this.futures = futures;
            this.values = (List<V>)Lists.newArrayListWithCapacity(futures.size());
            this.allMustSucceed = allMustSucceed;
            this.remaining = new AtomicInteger(futures.size());
            this.init(listenerExecutor);
        }
        
        private void init(final Executor listenerExecutor) {
            this.addListener(new Runnable() {
                public void run() {
                    ListFuture.this.values = null;
                    ListFuture.this.futures = null;
                }
            }, MoreExecutors.sameThreadExecutor());
            if (this.futures.isEmpty()) {
                this.set((List<V>)Lists.newArrayList((Iterable<?>)this.values));
                return;
            }
            for (int i = 0; i < this.futures.size(); ++i) {
                this.values.add(null);
            }
            final ImmutableList<? extends ListenableFuture<? extends V>> localFutures = this.futures;
            for (int j = 0; j < localFutures.size(); ++j) {
                final ListenableFuture<? extends V> listenable = localFutures.get(j);
                final int index = j;
                listenable.addListener(new Runnable() {
                    public void run() {
                        ListFuture.this.setOneValue(index, listenable);
                    }
                }, listenerExecutor);
            }
        }
        
        private void setOneValue(final int index, final Future<? extends V> future) {
            List<V> localValues = this.values;
            if (this.isDone() || localValues == null) {
                Preconditions.checkState(this.allMustSucceed, (Object)"Future was done before all dependencies completed");
                return;
            }
            try {
                Preconditions.checkState(future.isDone(), (Object)"Tried to set value from future which is not done");
                localValues.set(index, Uninterruptibles.getUninterruptibly((Future<V>)future));
            }
            catch (CancellationException e4) {
                if (this.allMustSucceed) {
                    this.cancel(false);
                }
            }
            catch (ExecutionException e) {
                if (this.allMustSucceed) {
                    this.setException(e.getCause());
                }
            }
            catch (RuntimeException e2) {
                if (this.allMustSucceed) {
                    this.setException(e2);
                }
            }
            catch (Error e3) {
                this.setException(e3);
            }
            finally {
                final int newRemaining = this.remaining.decrementAndGet();
                Preconditions.checkState(newRemaining >= 0, (Object)"Less than 0 remaining futures");
                if (newRemaining == 0) {
                    localValues = this.values;
                    if (localValues != null) {
                        this.set((List<V>)Lists.newArrayList((Iterable<?>)localValues));
                    }
                    else {
                        Preconditions.checkState(this.isDone());
                    }
                }
            }
        }
        
        public List<V> get() throws InterruptedException, ExecutionException {
            this.callAllGets();
            return super.get();
        }
        
        private void callAllGets() throws InterruptedException {
            final List<? extends ListenableFuture<? extends V>> oldFutures = this.futures;
            if (oldFutures != null && !this.isDone()) {
                for (final ListenableFuture<? extends V> future : oldFutures) {
                    while (!future.isDone()) {
                        try {
                            future.get();
                            continue;
                        }
                        catch (Error e) {
                            throw e;
                        }
                        catch (InterruptedException e2) {
                            throw e2;
                        }
                        catch (Throwable e3) {
                            if (this.allMustSucceed) {
                                return;
                            }
                            continue;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    private static class MappingCheckedFuture<V, X extends Exception> extends AbstractCheckedFuture<V, X>
    {
        final Function<Exception, X> mapper;
        
        MappingCheckedFuture(final ListenableFuture<V> delegate, final Function<Exception, X> mapper) {
            super(delegate);
            this.mapper = Preconditions.checkNotNull(mapper);
        }
        
        protected X mapException(final Exception e) {
            return this.mapper.apply(e);
        }
    }
}
