// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.internal.chmv8;

import java.util.concurrent.atomic.AtomicReference;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.Collection;
import java.security.PrivilegedActionException;
import java.security.AccessController;
import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import sun.misc.Unsafe;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.Serializable;
import java.util.concurrent.ConcurrentMap;

public final class ConcurrentHashMapV8<K, V> implements ConcurrentMap<K, V>, Serializable
{
    private static final long serialVersionUID = 7249069246763182397L;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final int DEFAULT_CAPACITY = 16;
    static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int TREE_THRESHOLD = 8;
    private static final int MIN_TRANSFER_STRIDE = 16;
    static final int MOVED = Integer.MIN_VALUE;
    static final int HASH_BITS = Integer.MAX_VALUE;
    static final int NCPU;
    static final AtomicInteger counterHashCodeGenerator;
    static final int SEED_INCREMENT = 1640531527;
    static final ThreadLocal<CounterHashCode> threadCounterHashCode;
    transient volatile Node<V>[] table;
    private transient volatile Node<V>[] nextTable;
    private transient volatile long baseCount;
    private transient volatile int sizeCtl;
    private transient volatile int transferIndex;
    private transient volatile int transferOrigin;
    private transient volatile int counterBusy;
    private transient volatile CounterCell[] counterCells;
    private transient KeySetView<K, V> keySet;
    private transient ValuesView<K, V> values;
    private transient EntrySetView<K, V> entrySet;
    private Segment<K, V>[] segments;
    private static final Unsafe U;
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    private static final long TRANSFERORIGIN;
    private static final long BASECOUNT;
    private static final long COUNTERBUSY;
    private static final long CELLVALUE;
    private static final long ABASE;
    private static final int ASHIFT;
    
    static final <V> Node<V> tabAt(final Node<V>[] tab, final int i) {
        return (Node<V>)ConcurrentHashMapV8.U.getObjectVolatile(tab, (i << ConcurrentHashMapV8.ASHIFT) + ConcurrentHashMapV8.ABASE);
    }
    
    private static final <V> boolean casTabAt(final Node<V>[] tab, final int i, final Node<V> c, final Node<V> v) {
        return ConcurrentHashMapV8.U.compareAndSwapObject(tab, (i << ConcurrentHashMapV8.ASHIFT) + ConcurrentHashMapV8.ABASE, c, v);
    }
    
    private static final <V> void setTabAt(final Node<V>[] tab, final int i, final Node<V> v) {
        ConcurrentHashMapV8.U.putObjectVolatile(tab, (i << ConcurrentHashMapV8.ASHIFT) + ConcurrentHashMapV8.ABASE, v);
    }
    
    private static final int spread(int h) {
        h ^= (h >>> 18 ^ h >>> 12);
        return (h ^ h >>> 10) & Integer.MAX_VALUE;
    }
    
    private final void replaceWithTreeBin(final Node<V>[] tab, final int index, final Object key) {
        if (key instanceof Comparable) {
            final TreeBin<V> t = new TreeBin<V>();
            for (Node<V> e = tabAt(tab, index); e != null; e = e.next) {
                t.putTreeNode(e.hash, e.key, e.val);
            }
            setTabAt(tab, index, new Node<V>(Integer.MIN_VALUE, t, null, null));
        }
    }
    
    private final V internalGet(final Object k) {
        final int h = spread(k.hashCode());
        Node<V>[] tab = this.table;
    Label_0013:
        while (tab != null) {
            Node<V> e = tabAt(tab, tab.length - 1 & h);
            while (e != null) {
                final int eh;
                if ((eh = e.hash) < 0) {
                    final Object ek;
                    if ((ek = e.key) instanceof TreeBin) {
                        return ((TreeBin)ek).getValue(h, k);
                    }
                    tab = (Node<V>[])ek;
                    continue Label_0013;
                }
                else {
                    final Object ek;
                    final V ev;
                    if (eh == h && (ev = e.val) != null && ((ek = e.key) == k || k.equals(ek))) {
                        return ev;
                    }
                    e = e.next;
                }
            }
            break;
        }
        return null;
    }
    
    private final V internalReplace(final Object k, final V v, final Object cv) {
        final int h = spread(k.hashCode());
        V oldVal = null;
        Node<V>[] tab = this.table;
        while (tab != null) {
            final int i;
            final Node<V> f;
            if ((f = tabAt(tab, i = (tab.length - 1 & h))) == null) {
                break;
            }
            final int fh;
            if ((fh = f.hash) < 0) {
                final Object fk;
                if ((fk = f.key) instanceof TreeBin) {
                    final TreeBin<V> t = (TreeBin<V>)fk;
                    boolean validated = false;
                    boolean deleted = false;
                    t.acquire(0);
                    try {
                        if (tabAt(tab, i) == f) {
                            validated = true;
                            final TreeNode<V> p = t.getTreeNode(h, k, t.root);
                            if (p != null) {
                                final V pv = p.val;
                                if (cv == null || cv == pv || cv.equals(pv)) {
                                    oldVal = pv;
                                    if ((p.val = v) == null) {
                                        deleted = true;
                                        t.deleteTreeNode(p);
                                    }
                                }
                            }
                        }
                    }
                    finally {
                        t.release(0);
                    }
                    if (!validated) {
                        continue;
                    }
                    if (deleted) {
                        this.addCount(-1L, -1);
                        break;
                    }
                    break;
                }
                else {
                    tab = (Node<V>[])fk;
                }
            }
            else {
                if (fh != h && f.next == null) {
                    break;
                }
                boolean validated2 = false;
                boolean deleted2 = false;
                synchronized (f) {
                    Label_0418: {
                        if (tabAt(tab, i) == f) {
                            validated2 = true;
                            Node<V> e = f;
                            Node<V> pred = null;
                            V ev;
                            Object ek;
                            while (e.hash != h || (ev = e.val) == null || ((ek = e.key) != k && !k.equals(ek))) {
                                pred = e;
                                if ((e = e.next) == null) {
                                    break Label_0418;
                                }
                            }
                            if (cv == null || cv == ev || cv.equals(ev)) {
                                oldVal = ev;
                                if ((e.val = v) == null) {
                                    deleted2 = true;
                                    final Node<V> en = e.next;
                                    if (pred != null) {
                                        pred.next = en;
                                    }
                                    else {
                                        setTabAt(tab, i, en);
                                    }
                                }
                            }
                        }
                    }
                }
                if (!validated2) {
                    continue;
                }
                if (deleted2) {
                    this.addCount(-1L, -1);
                    break;
                }
                break;
            }
        }
        return oldVal;
    }
    
    private final V internalPut(final K k, final V v, final boolean onlyIfAbsent) {
        if (k == null || v == null) {
            throw new NullPointerException();
        }
        final int h = spread(k.hashCode());
        int len = 0;
        Node<V>[] tab = this.table;
        while (true) {
            if (tab == null) {
                tab = this.initTable();
            }
            else {
                final int i;
                final Node<V> f;
                if ((f = tabAt(tab, i = (tab.length - 1 & h))) == null) {
                    if (casTabAt(tab, i, null, new Node<V>(h, k, v, null))) {
                        break;
                    }
                    continue;
                }
                else {
                    final int fh;
                    if ((fh = f.hash) < 0) {
                        final Object fk;
                        if ((fk = f.key) instanceof TreeBin) {
                            final TreeBin<V> t = (TreeBin<V>)fk;
                            V oldVal = null;
                            t.acquire(0);
                            try {
                                if (tabAt(tab, i) == f) {
                                    len = 2;
                                    final TreeNode<V> p = t.putTreeNode(h, k, v);
                                    if (p != null) {
                                        oldVal = p.val;
                                        if (!onlyIfAbsent) {
                                            p.val = v;
                                        }
                                    }
                                }
                            }
                            finally {
                                t.release(0);
                            }
                            if (len == 0) {
                                continue;
                            }
                            if (oldVal != null) {
                                return oldVal;
                            }
                            break;
                        }
                        else {
                            tab = (Node<V>[])fk;
                        }
                    }
                    else {
                        final Object fk;
                        final V fv;
                        if (onlyIfAbsent && fh == h && (fv = f.val) != null && ((fk = f.key) == k || k.equals(fk))) {
                            return fv;
                        }
                        V oldVal2 = null;
                        synchronized (f) {
                            Label_0426: {
                                if (tabAt(tab, i) == f) {
                                    len = 1;
                                    Node<V> e = f;
                                    V ev;
                                    Object ek;
                                    while (e.hash != h || (ev = e.val) == null || ((ek = e.key) != k && !k.equals(ek))) {
                                        final Node<V> last = e;
                                        if ((e = e.next) == null) {
                                            last.next = (Node<V>)new Node<Object>(h, k, (V)v, null);
                                            if (len >= 8) {
                                                this.replaceWithTreeBin(tab, i, k);
                                            }
                                            break Label_0426;
                                        }
                                        else {
                                            ++len;
                                        }
                                    }
                                    oldVal2 = ev;
                                    if (!onlyIfAbsent) {
                                        e.val = v;
                                    }
                                }
                            }
                        }
                        if (len == 0) {
                            continue;
                        }
                        if (oldVal2 != null) {
                            return oldVal2;
                        }
                        break;
                    }
                }
            }
        }
        this.addCount(1L, len);
        return null;
    }
    
    private final V internalComputeIfAbsent(final K k, final Fun<? super K, ? extends V> mf) {
        if (k == null || mf == null) {
            throw new NullPointerException();
        }
        final int h = spread(k.hashCode());
        V val = null;
        int len = 0;
        Node<V>[] tab = this.table;
        while (true) {
            if (tab == null) {
                tab = this.initTable();
            }
            else {
                final int i;
                final Node<V> f;
                if ((f = tabAt(tab, i = (tab.length - 1 & h))) == null) {
                    final Node<V> node = new Node<V>(h, k, null, null);
                    synchronized (node) {
                        if (casTabAt(tab, i, null, node)) {
                            len = 1;
                            try {
                                if ((val = (V)mf.apply((Object)k)) != null) {
                                    node.val = val;
                                }
                            }
                            finally {
                                if (val == null) {
                                    setTabAt(tab, i, null);
                                }
                            }
                        }
                    }
                    if (len != 0) {
                        break;
                    }
                    continue;
                }
                else if (f.hash < 0) {
                    final Object fk;
                    if ((fk = f.key) instanceof TreeBin) {
                        final TreeBin<V> t = (TreeBin<V>)fk;
                        boolean added = false;
                        t.acquire(0);
                        try {
                            if (tabAt(tab, i) == f) {
                                len = 1;
                                final TreeNode<V> p = t.getTreeNode(h, k, t.root);
                                if (p != null) {
                                    val = p.val;
                                }
                                else if ((val = (V)mf.apply((Object)k)) != null) {
                                    added = true;
                                    len = 2;
                                    t.putTreeNode(h, k, val);
                                }
                            }
                        }
                        finally {
                            t.release(0);
                        }
                        if (len == 0) {
                            continue;
                        }
                        if (!added) {
                            return val;
                        }
                        break;
                    }
                    else {
                        tab = (Node<V>[])fk;
                    }
                }
                else {
                    for (Node<V> e = f; e != null; e = e.next) {
                        final V ev;
                        final Object ek;
                        if (e.hash == h && (ev = e.val) != null && ((ek = e.key) == k || k.equals(ek))) {
                            return ev;
                        }
                    }
                    boolean added2 = false;
                    synchronized (f) {
                        Label_0559: {
                            if (tabAt(tab, i) == f) {
                                len = 1;
                                Node<V> e2 = f;
                                V ev2;
                                Object ek2;
                                while (e2.hash != h || (ev2 = e2.val) == null || ((ek2 = e2.key) != k && !k.equals(ek2))) {
                                    final Node<V> last = e2;
                                    if ((e2 = e2.next) == null) {
                                        if ((val = (V)mf.apply((Object)k)) == null) {
                                            break Label_0559;
                                        }
                                        added2 = true;
                                        last.next = (Node<V>)new Node<Object>(h, k, (V)val, null);
                                        if (len >= 8) {
                                            this.replaceWithTreeBin(tab, i, k);
                                        }
                                        break Label_0559;
                                    }
                                    else {
                                        ++len;
                                    }
                                }
                                val = ev2;
                            }
                        }
                    }
                    if (len == 0) {
                        continue;
                    }
                    if (!added2) {
                        return val;
                    }
                    break;
                }
            }
        }
        if (val != null) {
            this.addCount(1L, len);
        }
        return val;
    }
    
    private final V internalCompute(final K k, final boolean onlyIfPresent, final BiFun<? super K, ? super V, ? extends V> mf) {
        if (k == null || mf == null) {
            throw new NullPointerException();
        }
        final int h = spread(k.hashCode());
        V val = null;
        int delta = 0;
        int len = 0;
        Node<V>[] tab = this.table;
        while (true) {
            if (tab == null) {
                tab = this.initTable();
            }
            else {
                final int i;
                final Node<V> f;
                if ((f = tabAt(tab, i = (tab.length - 1 & h))) == null) {
                    if (onlyIfPresent) {
                        break;
                    }
                    final Node<V> node = new Node<V>(h, k, null, null);
                    synchronized (node) {
                        if (casTabAt(tab, i, null, node)) {
                            try {
                                len = 1;
                                if ((val = (V)mf.apply((Object)k, (Object)null)) != null) {
                                    node.val = val;
                                    delta = 1;
                                }
                            }
                            finally {
                                if (delta == 0) {
                                    setTabAt(tab, i, null);
                                }
                            }
                        }
                    }
                    if (len != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    final int fh;
                    if ((fh = f.hash) < 0) {
                        final Object fk;
                        if ((fk = f.key) instanceof TreeBin) {
                            final TreeBin<V> t = (TreeBin<V>)fk;
                            t.acquire(0);
                            try {
                                if (tabAt(tab, i) == f) {
                                    len = 1;
                                    final TreeNode<V> p = t.getTreeNode(h, k, t.root);
                                    if (p == null && onlyIfPresent) {
                                        break;
                                    }
                                    final V pv = (p == null) ? null : p.val;
                                    if ((val = (V)mf.apply((Object)k, (Object)pv)) != null) {
                                        if (p != null) {
                                            p.val = val;
                                        }
                                        else {
                                            len = 2;
                                            delta = 1;
                                            t.putTreeNode(h, k, val);
                                        }
                                    }
                                    else if (p != null) {
                                        delta = -1;
                                        t.deleteTreeNode(p);
                                    }
                                }
                            }
                            finally {
                                t.release(0);
                            }
                            if (len != 0) {
                                break;
                            }
                            continue;
                        }
                        else {
                            tab = (Node<V>[])fk;
                        }
                    }
                    else {
                        synchronized (f) {
                            Label_0628: {
                                if (tabAt(tab, i) == f) {
                                    len = 1;
                                    Node<V> e = f;
                                    Node<V> pred = null;
                                    V ev;
                                    Object ek;
                                    while (f.hash != h || (ev = f.val) == null || ((ek = f.key) != k && !k.equals(ek))) {
                                        pred = f;
                                        if ((e = f.next) == null) {
                                            if (onlyIfPresent || (val = (V)mf.apply((Object)k, (Object)null)) == null) {
                                                break Label_0628;
                                            }
                                            f.next = (Node<V>)new Node<Object>(h, k, (V)val, null);
                                            delta = 1;
                                            if (len >= 8) {
                                                this.replaceWithTreeBin(tab, i, k);
                                            }
                                            break Label_0628;
                                        }
                                        else {
                                            ++len;
                                        }
                                    }
                                    val = (V)mf.apply((Object)k, (Object)ev);
                                    if (val != null) {
                                        f.val = val;
                                    }
                                    else {
                                        delta = -1;
                                        final Node<V> en = f.next;
                                        if (pred != null) {
                                            pred.next = en;
                                        }
                                        else {
                                            setTabAt(tab, i, en);
                                        }
                                    }
                                }
                            }
                        }
                        if (len != 0) {
                            break;
                        }
                        continue;
                    }
                }
            }
        }
        if (delta != 0) {
            this.addCount(delta, len);
        }
        return val;
    }
    
    private final V internalMerge(final K k, final V v, final BiFun<? super V, ? super V, ? extends V> mf) {
        if (k == null || v == null || mf == null) {
            throw new NullPointerException();
        }
        final int h = spread(k.hashCode());
        V val = null;
        int delta = 0;
        int len = 0;
        Node<V>[] tab = this.table;
        while (true) {
            if (tab == null) {
                tab = this.initTable();
            }
            else {
                final int i;
                final Node<V> f;
                if ((f = tabAt(tab, i = (tab.length - 1 & h))) == null) {
                    if (casTabAt(tab, i, null, new Node<V>(h, k, v, null))) {
                        delta = 1;
                        val = v;
                        break;
                    }
                    continue;
                }
                else if (f.hash < 0) {
                    final Object fk;
                    if ((fk = f.key) instanceof TreeBin) {
                        final TreeBin<V> t = (TreeBin<V>)fk;
                        t.acquire(0);
                        try {
                            if (tabAt(tab, i) == f) {
                                len = 1;
                                final TreeNode<V> p = t.getTreeNode(h, k, t.root);
                                val = ((p == null) ? v : mf.apply((Object)p.val, (Object)v));
                                if (val != null) {
                                    if (p != null) {
                                        p.val = val;
                                    }
                                    else {
                                        len = 2;
                                        delta = 1;
                                        t.putTreeNode(h, k, val);
                                    }
                                }
                                else if (p != null) {
                                    delta = -1;
                                    t.deleteTreeNode(p);
                                }
                            }
                        }
                        finally {
                            t.release(0);
                        }
                        if (len != 0) {
                            break;
                        }
                        continue;
                    }
                    else {
                        tab = (Node<V>[])fk;
                    }
                }
                else {
                    synchronized (f) {
                        Label_0498: {
                            if (tabAt(tab, i) == f) {
                                len = 1;
                                Node<V> e = f;
                                Node<V> pred = null;
                                V ev;
                                Object ek;
                                while (f.hash != h || (ev = f.val) == null || ((ek = f.key) != k && !k.equals(ek))) {
                                    pred = f;
                                    if ((e = f.next) == null) {
                                        val = v;
                                        f.next = (Node<V>)new Node<Object>(h, k, (V)val, null);
                                        delta = 1;
                                        if (len >= 8) {
                                            this.replaceWithTreeBin(tab, i, k);
                                        }
                                        break Label_0498;
                                    }
                                    else {
                                        ++len;
                                    }
                                }
                                val = (V)mf.apply((Object)ev, (Object)v);
                                if (val != null) {
                                    f.val = val;
                                }
                                else {
                                    delta = -1;
                                    final Node<V> en = f.next;
                                    if (pred != null) {
                                        pred.next = en;
                                    }
                                    else {
                                        setTabAt(tab, i, en);
                                    }
                                }
                            }
                        }
                    }
                    if (len != 0) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (delta != 0) {
            this.addCount(delta, len);
        }
        return val;
    }
    
    private final void internalPutAll(final Map<? extends K, ? extends V> m) {
        this.tryPresize(m.size());
        long delta = 0L;
        boolean npe = false;
        try {
            for (final Map.Entry<?, ? extends V> entry : m.entrySet()) {
                final Object k;
                final V v;
                if (entry == null || (k = entry.getKey()) == null || (v = (V)entry.getValue()) == null) {
                    npe = true;
                    break;
                }
                final int h = spread(k.hashCode());
                Node<V>[] tab = this.table;
                while (true) {
                    if (tab == null) {
                        tab = this.initTable();
                    }
                    else {
                        final int i;
                        final Node<V> f;
                        if ((f = tabAt(tab, i = (tab.length - 1 & h))) == null) {
                            if (casTabAt(tab, i, null, new Node<V>(h, k, v, null))) {
                                ++delta;
                                break;
                            }
                            continue;
                        }
                        else {
                            final int fh;
                            if ((fh = f.hash) < 0) {
                                final Object fk;
                                if ((fk = f.key) instanceof TreeBin) {
                                    final TreeBin<V> t = (TreeBin<V>)fk;
                                    boolean validated = false;
                                    t.acquire(0);
                                    try {
                                        if (tabAt(tab, i) == f) {
                                            validated = true;
                                            final TreeNode<V> p = t.getTreeNode(h, k, t.root);
                                            if (p != null) {
                                                p.val = v;
                                            }
                                            else {
                                                t.putTreeNode(h, k, v);
                                                ++delta;
                                            }
                                        }
                                    }
                                    finally {
                                        t.release(0);
                                    }
                                    if (validated) {
                                        break;
                                    }
                                    continue;
                                }
                                else {
                                    tab = (Node<V>[])fk;
                                }
                            }
                            else {
                                int len = 0;
                                synchronized (f) {
                                    Label_0466: {
                                        if (tabAt(tab, i) == f) {
                                            len = 1;
                                            Node<V> e = f;
                                            V ev;
                                            Object ek;
                                            while (e.hash != h || (ev = e.val) == null || ((ek = e.key) != k && !k.equals(ek))) {
                                                final Node<V> last = e;
                                                if ((e = e.next) == null) {
                                                    ++delta;
                                                    last.next = (Node<V>)new Node<Object>(h, k, (V)v, null);
                                                    if (len >= 8) {
                                                        this.replaceWithTreeBin(tab, i, k);
                                                    }
                                                    break Label_0466;
                                                }
                                                else {
                                                    ++len;
                                                }
                                            }
                                            e.val = v;
                                        }
                                    }
                                }
                                if (len == 0) {
                                    continue;
                                }
                                if (len > 1) {
                                    this.addCount(delta, len);
                                    delta = 0L;
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        finally {
            if (delta != 0L) {
                this.addCount(delta, 2);
            }
        }
        if (npe) {
            throw new NullPointerException();
        }
    }
    
    private final void internalClear() {
        long delta = 0L;
        int i = 0;
        Node<V>[] tab = this.table;
        while (tab != null && i < tab.length) {
            final Node<V> f = tabAt(tab, i);
            if (f == null) {
                ++i;
            }
            else if (f.hash < 0) {
                final Object fk;
                if ((fk = f.key) instanceof TreeBin) {
                    final TreeBin<V> t = (TreeBin<V>)fk;
                    t.acquire(0);
                    try {
                        if (tabAt(tab, i) != f) {
                            continue;
                        }
                        for (Node<V> p = t.first; p != null; p = p.next) {
                            if (p.val != null) {
                                p.val = null;
                                --delta;
                            }
                        }
                        t.first = null;
                        t.root = null;
                        ++i;
                    }
                    finally {
                        t.release(0);
                    }
                }
                else {
                    tab = (Node<V>[])fk;
                }
            }
            else {
                synchronized (f) {
                    if (tabAt(tab, i) != f) {
                        continue;
                    }
                    Node<V> e = f;
                    while (f != null) {
                        if (f.val != null) {
                            f.val = null;
                            --delta;
                        }
                        e = f.next;
                    }
                    setTabAt(tab, i, null);
                    ++i;
                }
            }
        }
        if (delta != 0L) {
            this.addCount(delta, -1);
        }
    }
    
    private static final int tableSizeFor(final int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : ((n >= 1073741824) ? 1073741824 : (n + 1));
    }
    
    private final Node<V>[] initTable() {
        Node<V>[] tab;
        while ((tab = this.table) == null) {
            int sc;
            if ((sc = this.sizeCtl) < 0) {
                Thread.yield();
            }
            else {
                if (ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sc, -1)) {
                    try {
                        if ((tab = this.table) == null) {
                            final int n = (sc > 0) ? sc : 16;
                            final Node[] tb = new Node[n];
                            tab = (this.table = (Node<V>[])tb);
                            sc = n - (n >>> 2);
                        }
                    }
                    finally {
                        this.sizeCtl = sc;
                    }
                    break;
                }
                continue;
            }
        }
        return tab;
    }
    
    private final void addCount(final long x, final int check) {
        long s = 0L;
        Label_0139: {
            final CounterCell[] as;
            if ((as = this.counterCells) == null) {
                final Unsafe u = ConcurrentHashMapV8.U;
                final long basecount = ConcurrentHashMapV8.BASECOUNT;
                final long b = this.baseCount;
                if (u.compareAndSwapLong(this, basecount, b, s = b + x)) {
                    break Label_0139;
                }
            }
            boolean uncontended = true;
            final CounterHashCode hc;
            final int m;
            final CounterCell a;
            final long v;
            if ((hc = ConcurrentHashMapV8.threadCounterHashCode.get()) == null || as == null || (m = as.length - 1) < 0 || (a = as[m & hc.code]) == null || !(uncontended = ConcurrentHashMapV8.U.compareAndSwapLong(a, ConcurrentHashMapV8.CELLVALUE, v = a.value, v + x))) {
                this.fullAddCount(x, hc, uncontended);
                return;
            }
            if (check <= 1) {
                return;
            }
            s = this.sumCount();
        }
        if (check >= 0) {
            int sc;
            Node<V>[] tab;
            while (s >= (sc = this.sizeCtl) && (tab = this.table) != null && tab.length < 1073741824) {
                if (sc < 0) {
                    if (sc == -1 || this.transferIndex <= this.transferOrigin) {
                        break;
                    }
                    final Node<V>[] nt;
                    if ((nt = this.nextTable) == null) {
                        break;
                    }
                    if (ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sc, sc - 1)) {
                        this.transfer(tab, nt);
                    }
                }
                else if (ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sc, -2)) {
                    this.transfer(tab, null);
                }
                s = this.sumCount();
            }
        }
    }
    
    private final void tryPresize(final int size) {
        final int c = (size >= 536870912) ? 1073741824 : tableSizeFor(size + (size >>> 1) + 1);
        int sc;
        while ((sc = this.sizeCtl) >= 0) {
            final Node<V>[] tab = this.table;
            int n;
            if (tab == null || (n = tab.length) == 0) {
                n = ((sc > c) ? sc : c);
                if (!ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sc, -1)) {
                    continue;
                }
                try {
                    if (this.table != tab) {
                        continue;
                    }
                    final Node[] tb = new Node[n];
                    this.table = (Node<V>[])tb;
                    sc = n - (n >>> 2);
                }
                finally {
                    this.sizeCtl = sc;
                }
            }
            else {
                if (c <= sc) {
                    break;
                }
                if (n >= 1073741824) {
                    break;
                }
                if (tab != this.table || !ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sc, -2)) {
                    continue;
                }
                this.transfer(tab, null);
            }
        }
    }
    
    private final void transfer(final Node<V>[] tab, Node<V>[] nextTab) {
        final int n = tab.length;
        int stride;
        if ((stride = ((ConcurrentHashMapV8.NCPU > 1) ? ((n >>> 3) / ConcurrentHashMapV8.NCPU) : n)) < 16) {
            stride = 16;
        }
        if (nextTab == null) {
            try {
                final Node[] tb = new Node[n << 1];
                nextTab = (Node<V>[])tb;
            }
            catch (Throwable ex) {
                this.sizeCtl = Integer.MAX_VALUE;
                return;
            }
            this.nextTable = nextTab;
            this.transferOrigin = n;
            this.transferIndex = n;
            final Node<V> rev = new Node<V>(Integer.MIN_VALUE, tab, null, null);
            int k = n;
            while (k > 0) {
                int m;
                int nextk;
                for (nextk = (m = ((k > stride) ? (k - stride) : 0)); m < k; ++m) {
                    nextTab[m] = rev;
                }
                for (m = n + nextk; m < n + k; ++m) {
                    nextTab[m] = rev;
                }
                ConcurrentHashMapV8.U.putOrderedInt(this, ConcurrentHashMapV8.TRANSFERORIGIN, k = nextk);
            }
        }
        final int nextn = nextTab.length;
        final Node<V> fwd = new Node<V>(Integer.MIN_VALUE, nextTab, null, null);
        boolean advance = true;
        int i = 0;
        int bound = 0;
        while (true) {
            if (advance) {
                if (--i >= bound) {
                    advance = false;
                }
                else {
                    final int nextIndex;
                    if ((nextIndex = this.transferIndex) <= this.transferOrigin) {
                        i = -1;
                        advance = false;
                    }
                    else {
                        final int n2;
                        final int nextBound;
                        if (!ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.TRANSFERINDEX, n2, nextBound = (((n2 = nextIndex) > stride) ? (nextIndex - stride) : 0))) {
                            continue;
                        }
                        bound = nextBound;
                        i = nextIndex - 1;
                        advance = false;
                    }
                }
            }
            else {
                if (i < 0 || i >= n || i + n >= nextn) {
                    break;
                }
                final Node<V> f;
                if ((f = tabAt(tab, i)) == null) {
                    if (!casTabAt(tab, i, null, fwd)) {
                        continue;
                    }
                    setTabAt(nextTab, i, null);
                    setTabAt(nextTab, i + n, null);
                    advance = true;
                }
                else if (f.hash >= 0) {
                    synchronized (f) {
                        if (tabAt(tab, i) != f) {
                            continue;
                        }
                        int runBit = f.hash & n;
                        Node<V> lastRun = f;
                        Node<V> lo = null;
                        Node<V> hi = null;
                        for (Node<V> p = f.next; p != null; p = p.next) {
                            final int b = p.hash & n;
                            if (b != runBit) {
                                runBit = b;
                                lastRun = p;
                            }
                        }
                        if (runBit == 0) {
                            lo = lastRun;
                        }
                        else {
                            hi = lastRun;
                        }
                        for (Node<V> p = f; p != lastRun; p = p.next) {
                            final int ph = p.hash;
                            final Object pk = p.key;
                            final V pv = p.val;
                            if ((ph & n) == 0x0) {
                                lo = new Node<V>(ph, pk, pv, lo);
                            }
                            else {
                                hi = new Node<V>(ph, pk, pv, hi);
                            }
                        }
                        setTabAt(nextTab, i, lo);
                        setTabAt(nextTab, i + n, hi);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                }
                else {
                    final Object fk;
                    if ((fk = f.key) instanceof TreeBin) {
                        final TreeBin<V> t = (TreeBin<V>)fk;
                        t.acquire(0);
                        try {
                            if (tabAt(tab, i) != f) {
                                continue;
                            }
                            final TreeBin<V> lt = new TreeBin<V>();
                            final TreeBin<V> ht = new TreeBin<V>();
                            int lc = 0;
                            int hc = 0;
                            for (Node<V> e = t.first; e != null; e = e.next) {
                                final int h = e.hash;
                                final Object j = e.key;
                                final V v = e.val;
                                if ((h & n) == 0x0) {
                                    ++lc;
                                    lt.putTreeNode(h, j, v);
                                }
                                else {
                                    ++hc;
                                    ht.putTreeNode(h, j, v);
                                }
                            }
                            Node<V> ln;
                            if (lc < 8) {
                                ln = null;
                                for (Node<V> p2 = lt.first; p2 != null; p2 = p2.next) {
                                    ln = new Node<V>(p2.hash, p2.key, p2.val, ln);
                                }
                            }
                            else {
                                ln = new Node<V>(Integer.MIN_VALUE, lt, null, null);
                            }
                            setTabAt(nextTab, i, ln);
                            Node<V> hn;
                            if (hc < 8) {
                                hn = null;
                                for (Node<V> p2 = ht.first; p2 != null; p2 = p2.next) {
                                    hn = new Node<V>(p2.hash, p2.key, p2.val, hn);
                                }
                            }
                            else {
                                hn = new Node<V>(Integer.MIN_VALUE, ht, null, null);
                            }
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                        finally {
                            t.release(0);
                        }
                    }
                    else {
                        advance = true;
                    }
                }
            }
        }
        int sc;
        while (!ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sc = this.sizeCtl, ++sc)) {}
        if (sc == -1) {
            this.nextTable = null;
            this.table = nextTab;
            this.sizeCtl = (n << 1) - (n >>> 1);
        }
    }
    
    final long sumCount() {
        final CounterCell[] as = this.counterCells;
        long sum = this.baseCount;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                final CounterCell a;
                if ((a = as[i]) != null) {
                    sum += a.value;
                }
            }
        }
        return sum;
    }
    
    private final void fullAddCount(final long x, CounterHashCode hc, boolean wasUncontended) {
        int h;
        if (hc == null) {
            hc = new CounterHashCode();
            final int s = ConcurrentHashMapV8.counterHashCodeGenerator.addAndGet(1640531527);
            final CounterHashCode counterHashCode = hc;
            final int code = (s == 0) ? 1 : s;
            counterHashCode.code = code;
            h = code;
            ConcurrentHashMapV8.threadCounterHashCode.set(hc);
        }
        else {
            h = hc.code;
        }
        boolean collide = false;
        while (true) {
            final CounterCell[] as;
            final int n;
            if ((as = this.counterCells) != null && (n = as.length) > 0) {
                final CounterCell a;
                if ((a = as[n - 1 & h]) == null) {
                    if (this.counterBusy == 0) {
                        final CounterCell r = new CounterCell(x);
                        if (this.counterBusy == 0 && ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.COUNTERBUSY, 0, 1)) {
                            boolean created = false;
                            try {
                                final CounterCell[] rs;
                                final int m;
                                final int j;
                                if ((rs = this.counterCells) != null && (m = rs.length) > 0 && rs[j = (m - 1 & h)] == null) {
                                    rs[j] = r;
                                    created = true;
                                }
                            }
                            finally {
                                this.counterBusy = 0;
                            }
                            if (created) {
                                break;
                            }
                            continue;
                        }
                    }
                    collide = false;
                }
                else if (!wasUncontended) {
                    wasUncontended = true;
                }
                else {
                    final long v;
                    if (ConcurrentHashMapV8.U.compareAndSwapLong(a, ConcurrentHashMapV8.CELLVALUE, v = a.value, v + x)) {
                        break;
                    }
                    if (this.counterCells != as || n >= ConcurrentHashMapV8.NCPU) {
                        collide = false;
                    }
                    else if (!collide) {
                        collide = true;
                    }
                    else if (this.counterBusy == 0 && ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.COUNTERBUSY, 0, 1)) {
                        try {
                            if (this.counterCells == as) {
                                final CounterCell[] rs2 = new CounterCell[n << 1];
                                for (int i = 0; i < n; ++i) {
                                    rs2[i] = as[i];
                                }
                                this.counterCells = rs2;
                            }
                        }
                        finally {
                            this.counterBusy = 0;
                        }
                        collide = false;
                        continue;
                    }
                }
                h ^= h << 13;
                h ^= h >>> 17;
                h ^= h << 5;
            }
            else if (this.counterBusy == 0 && this.counterCells == as && ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.COUNTERBUSY, 0, 1)) {
                boolean init = false;
                try {
                    if (this.counterCells == as) {
                        final CounterCell[] rs3 = new CounterCell[2];
                        rs3[h & 0x1] = new CounterCell(x);
                        this.counterCells = rs3;
                        init = true;
                    }
                }
                finally {
                    this.counterBusy = 0;
                }
                if (init) {
                    break;
                }
                continue;
            }
            else {
                final long v;
                if (ConcurrentHashMapV8.U.compareAndSwapLong(this, ConcurrentHashMapV8.BASECOUNT, v = this.baseCount, v + x)) {
                    break;
                }
                continue;
            }
        }
        hc.code = h;
    }
    
    public ConcurrentHashMapV8() {
    }
    
    public ConcurrentHashMapV8(final int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }
        final int cap = (initialCapacity >= 536870912) ? 1073741824 : tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1);
        this.sizeCtl = cap;
    }
    
    public ConcurrentHashMapV8(final Map<? extends K, ? extends V> m) {
        this.sizeCtl = 16;
        this.internalPutAll(m);
    }
    
    public ConcurrentHashMapV8(final int initialCapacity, final float loadFactor) {
        this(initialCapacity, loadFactor, 1);
    }
    
    public ConcurrentHashMapV8(int initialCapacity, final float loadFactor, final int concurrencyLevel) {
        if (loadFactor <= 0.0f || initialCapacity < 0 || concurrencyLevel <= 0) {
            throw new IllegalArgumentException();
        }
        if (initialCapacity < concurrencyLevel) {
            initialCapacity = concurrencyLevel;
        }
        final long size = (long)(1.0 + initialCapacity / loadFactor);
        final int cap = (size >= 1073741824L) ? 1073741824 : tableSizeFor((int)size);
        this.sizeCtl = cap;
    }
    
    public static <K> KeySetView<K, Boolean> newKeySet() {
        return new KeySetView<K, Boolean>(new ConcurrentHashMapV8<K, Boolean>(), Boolean.TRUE);
    }
    
    public static <K> KeySetView<K, Boolean> newKeySet(final int initialCapacity) {
        return new KeySetView<K, Boolean>(new ConcurrentHashMapV8<K, Boolean>(initialCapacity), Boolean.TRUE);
    }
    
    @Override
    public boolean isEmpty() {
        return this.sumCount() <= 0L;
    }
    
    @Override
    public int size() {
        final long n = this.sumCount();
        return (n < 0L) ? 0 : ((n > 2147483647L) ? Integer.MAX_VALUE : ((int)n));
    }
    
    public long mappingCount() {
        final long n = this.sumCount();
        return (n < 0L) ? 0L : n;
    }
    
    @Override
    public V get(final Object key) {
        return this.internalGet(key);
    }
    
    public V getValueOrDefault(final Object key, final V defaultValue) {
        final V v;
        return ((v = this.internalGet(key)) == null) ? defaultValue : v;
    }
    
    @Override
    public boolean containsKey(final Object key) {
        return this.internalGet(key) != null;
    }
    
    @Override
    public boolean containsValue(final Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            if (v == value || value.equals(v)) {
                return true;
            }
        }
        return false;
    }
    
    @Deprecated
    public boolean contains(final Object value) {
        return this.containsValue(value);
    }
    
    @Override
    public V put(final K key, final V value) {
        return this.internalPut(key, value, false);
    }
    
    @Override
    public V putIfAbsent(final K key, final V value) {
        return this.internalPut(key, value, true);
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        this.internalPutAll(m);
    }
    
    public V computeIfAbsent(final K key, final Fun<? super K, ? extends V> mappingFunction) {
        return this.internalComputeIfAbsent(key, mappingFunction);
    }
    
    public V computeIfPresent(final K key, final BiFun<? super K, ? super V, ? extends V> remappingFunction) {
        return this.internalCompute(key, true, remappingFunction);
    }
    
    public V compute(final K key, final BiFun<? super K, ? super V, ? extends V> remappingFunction) {
        return this.internalCompute(key, false, remappingFunction);
    }
    
    public V merge(final K key, final V value, final BiFun<? super V, ? super V, ? extends V> remappingFunction) {
        return this.internalMerge(key, value, remappingFunction);
    }
    
    @Override
    public V remove(final Object key) {
        return this.internalReplace(key, null, null);
    }
    
    @Override
    public boolean remove(final Object key, final Object value) {
        return value != null && this.internalReplace(key, null, value) != null;
    }
    
    @Override
    public boolean replace(final K key, final V oldValue, final V newValue) {
        if (key == null || oldValue == null || newValue == null) {
            throw new NullPointerException();
        }
        return this.internalReplace(key, newValue, oldValue) != null;
    }
    
    @Override
    public V replace(final K key, final V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        return this.internalReplace(key, value, null);
    }
    
    @Override
    public void clear() {
        this.internalClear();
    }
    
    @Override
    public KeySetView<K, V> keySet() {
        final KeySetView<K, V> ks = this.keySet;
        return (ks != null) ? ks : (this.keySet = new KeySetView<K, V>(this, null));
    }
    
    public KeySetView<K, V> keySet(final V mappedValue) {
        if (mappedValue == null) {
            throw new NullPointerException();
        }
        return new KeySetView<K, V>(this, mappedValue);
    }
    
    @Override
    public ValuesView<K, V> values() {
        final ValuesView<K, V> vs = this.values;
        return (vs != null) ? vs : (this.values = new ValuesView<K, V>(this));
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        final EntrySetView<K, V> es = this.entrySet;
        return (es != null) ? es : (this.entrySet = new EntrySetView<K, V>(this));
    }
    
    public Enumeration<K> keys() {
        return new KeyIterator<K, Object>(this);
    }
    
    public Enumeration<V> elements() {
        return new ValueIterator<Object, V>(this);
    }
    
    public Spliterator<K> keySpliterator() {
        return new KeyIterator<K, Object>(this);
    }
    
    public Spliterator<V> valueSpliterator() {
        return new ValueIterator<Object, V>(this);
    }
    
    public Spliterator<Map.Entry<K, V>> entrySpliterator() {
        return new EntryIterator<K, V>(this);
    }
    
    @Override
    public int hashCode() {
        int h = 0;
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            h += (it.nextKey.hashCode() ^ v.hashCode());
        }
        return h;
    }
    
    @Override
    public String toString() {
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        V v;
        if ((v = it.advance()) != null) {
            while (true) {
                final Object k = it.nextKey;
                sb.append((k == this) ? "(this Map)" : k);
                sb.append('=');
                sb.append((v == this) ? "(this Map)" : v);
                if ((v = it.advance()) == null) {
                    break;
                }
                sb.append(',').append(' ');
            }
        }
        return sb.append('}').toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != this) {
            if (!(o instanceof Map)) {
                return false;
            }
            final Map<?, ?> m = (Map<?, ?>)o;
            final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
            V val;
            while ((val = it.advance()) != null) {
                final Object v = m.get(it.nextKey);
                if (v == null || (v != val && !v.equals(val))) {
                    return false;
                }
            }
            for (final Map.Entry<?, ?> e : m.entrySet()) {
                final Object mk;
                final Object mv;
                final Object v2;
                if ((mk = e.getKey()) == null || (mv = e.getValue()) == null || (v2 = this.internalGet(mk)) == null || (mv != v2 && !mv.equals(v2))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static <K, V> AbstractMap.SimpleEntry<K, V> entryFor(final K k, final V v) {
        return new AbstractMap.SimpleEntry<K, V>(k, v);
    }
    
    private void writeObject(final ObjectOutputStream s) throws IOException {
        if (this.segments == null) {
            this.segments = (Segment<K, V>[])new Segment[16];
            for (int i = 0; i < this.segments.length; ++i) {
                this.segments[i] = new Segment<K, V>(0.75f);
            }
        }
        s.defaultWriteObject();
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            s.writeObject(it.nextKey);
            s.writeObject(v);
        }
        s.writeObject(null);
        s.writeObject(null);
        this.segments = null;
    }
    
    private void readObject(final ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.segments = null;
        long size = 0L;
        Node<V> p = null;
        while (true) {
            final K k = (K)s.readObject();
            final V v = (V)s.readObject();
            if (k == null || v == null) {
                break;
            }
            final int h = spread(k.hashCode());
            p = new Node<V>(h, k, v, p);
            ++size;
        }
        if (p != null) {
            boolean init = false;
            int n;
            if (size >= 536870912L) {
                n = 1073741824;
            }
            else {
                final int sz = (int)size;
                n = tableSizeFor(sz + (sz >>> 1) + 1);
            }
            int sc = this.sizeCtl;
            boolean collide = false;
            if (n > sc && ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sc, -1)) {
                try {
                    if (this.table == null) {
                        init = true;
                        final Node[] rt = new Node[n];
                        final Node<V>[] tab = (Node<V>[])rt;
                        final int mask = n - 1;
                        while (p != null) {
                            final int j = p.hash & mask;
                            final Node<V> next = p.next;
                            final Node<V> node = p;
                            final Node<V> tab3 = tabAt(tab, j);
                            node.next = tab3;
                            final Node<V> q = tab3;
                            setTabAt(tab, j, p);
                            if (!collide && q != null && q.hash == p.hash) {
                                collide = true;
                            }
                            p = next;
                        }
                        this.table = tab;
                        this.addCount(size, -1);
                        sc = n - (n >>> 2);
                    }
                }
                finally {
                    this.sizeCtl = sc;
                }
                if (collide) {
                    final Node<V>[] tab2 = this.table;
                    for (int i = 0; i < tab2.length; ++i) {
                        int c = 0;
                        for (Node<V> e = tabAt(tab2, i); e != null; e = e.next) {
                            if (++c > 8 && e.key instanceof Comparable) {
                                this.replaceWithTreeBin(tab2, i, e.key);
                                break;
                            }
                        }
                    }
                }
            }
            if (!init) {
                while (p != null) {
                    this.internalPut(p.key, p.val, false);
                    p = p.next;
                }
            }
        }
    }
    
    public void forEachSequentially(final BiAction<K, V> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            action.apply((K)it.nextKey, v);
        }
    }
    
    public <U> void forEachSequentially(final BiFun<? super K, ? super V, ? extends U> transformer, final Action<U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)transformer.apply((Object)it.nextKey, (Object)v)) != null) {
                action.apply(u);
            }
        }
    }
    
    public <U> U searchSequentially(final BiFun<? super K, ? super V, ? extends U> searchFunction) {
        if (searchFunction == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)searchFunction.apply((Object)it.nextKey, (Object)v)) != null) {
                return u;
            }
        }
        return null;
    }
    
    public <U> U reduceSequentially(final BiFun<? super K, ? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        U r = null;
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)transformer.apply((Object)it.nextKey, (Object)v)) != null) {
                r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
            }
        }
        return r;
    }
    
    public double reduceToDoubleSequentially(final ObjectByObjectToDouble<? super K, ? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        double r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Object)it.nextKey, (Object)v));
        }
        return r;
    }
    
    public long reduceToLongSequentially(final ObjectByObjectToLong<? super K, ? super V> transformer, final long basis, final LongByLongToLong reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        long r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Object)it.nextKey, (Object)v));
        }
        return r;
    }
    
    public int reduceToIntSequentially(final ObjectByObjectToInt<? super K, ? super V> transformer, final int basis, final IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        int r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Object)it.nextKey, (Object)v));
        }
        return r;
    }
    
    public void forEachKeySequentially(final Action<K> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        while (it.advance() != null) {
            action.apply((K)it.nextKey);
        }
    }
    
    public <U> void forEachKeySequentially(final Fun<? super K, ? extends U> transformer, final Action<U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        while (it.advance() != null) {
            final U u;
            if ((u = (U)transformer.apply((Object)it.nextKey)) != null) {
                action.apply(u);
            }
        }
        ForkJoinTasks.forEachKey((ConcurrentHashMapV8<Object, Object>)this, (Fun<? super Object, ? extends U>)transformer, action).invoke();
    }
    
    public <U> U searchKeysSequentially(final Fun<? super K, ? extends U> searchFunction) {
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        while (it.advance() != null) {
            final U u;
            if ((u = (U)searchFunction.apply((Object)it.nextKey)) != null) {
                return u;
            }
        }
        return null;
    }
    
    public K reduceKeysSequentially(final BiFun<? super K, ? super K, ? extends K> reducer) {
        if (reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        K r = null;
        while (it.advance() != null) {
            final K u = (K)it.nextKey;
            r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
        }
        return r;
    }
    
    public <U> U reduceKeysSequentially(final Fun<? super K, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        U r = null;
        while (it.advance() != null) {
            final U u;
            if ((u = (U)transformer.apply((Object)it.nextKey)) != null) {
                r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
            }
        }
        return r;
    }
    
    public double reduceKeysToDoubleSequentially(final ObjectToDouble<? super K> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        double r = basis;
        while (it.advance() != null) {
            r = reducer.apply(r, transformer.apply((Object)it.nextKey));
        }
        return r;
    }
    
    public long reduceKeysToLongSequentially(final ObjectToLong<? super K> transformer, final long basis, final LongByLongToLong reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        long r = basis;
        while (it.advance() != null) {
            r = reducer.apply(r, transformer.apply((Object)it.nextKey));
        }
        return r;
    }
    
    public int reduceKeysToIntSequentially(final ObjectToInt<? super K> transformer, final int basis, final IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        int r = basis;
        while (it.advance() != null) {
            r = reducer.apply(r, transformer.apply((Object)it.nextKey));
        }
        return r;
    }
    
    public void forEachValueSequentially(final Action<V> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            action.apply(v);
        }
    }
    
    public <U> void forEachValueSequentially(final Fun<? super V, ? extends U> transformer, final Action<U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)transformer.apply((Object)v)) != null) {
                action.apply(u);
            }
        }
    }
    
    public <U> U searchValuesSequentially(final Fun<? super V, ? extends U> searchFunction) {
        if (searchFunction == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)searchFunction.apply((Object)v)) != null) {
                return u;
            }
        }
        return null;
    }
    
    public V reduceValuesSequentially(final BiFun<? super V, ? super V, ? extends V> reducer) {
        if (reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V r = null;
        V v;
        while ((v = it.advance()) != null) {
            r = ((r == null) ? v : reducer.apply((Object)r, (Object)v));
        }
        return r;
    }
    
    public <U> U reduceValuesSequentially(final Fun<? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        U r = null;
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)transformer.apply((Object)v)) != null) {
                r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
            }
        }
        return r;
    }
    
    public double reduceValuesToDoubleSequentially(final ObjectToDouble<? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        double r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Object)v));
        }
        return r;
    }
    
    public long reduceValuesToLongSequentially(final ObjectToLong<? super V> transformer, final long basis, final LongByLongToLong reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        long r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Object)v));
        }
        return r;
    }
    
    public int reduceValuesToIntSequentially(final ObjectToInt<? super V> transformer, final int basis, final IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        int r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Object)v));
        }
        return r;
    }
    
    public void forEachEntrySequentially(final Action<Map.Entry<K, V>> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            action.apply((Map.Entry<K, V>)entryFor(it.nextKey, v));
        }
    }
    
    public <U> void forEachEntrySequentially(final Fun<Map.Entry<K, V>, ? extends U> transformer, final Action<U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)transformer.apply((Map.Entry<K, V>)entryFor(it.nextKey, v))) != null) {
                action.apply(u);
            }
        }
    }
    
    public <U> U searchEntriesSequentially(final Fun<Map.Entry<K, V>, ? extends U> searchFunction) {
        if (searchFunction == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)searchFunction.apply((Map.Entry<K, V>)entryFor(it.nextKey, v))) != null) {
                return u;
            }
        }
        return null;
    }
    
    public Map.Entry<K, V> reduceEntriesSequentially(final BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer) {
        if (reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        Map.Entry<K, V> r = null;
        V v;
        while ((v = it.advance()) != null) {
            final Map.Entry<K, V> u = (Map.Entry<K, V>)entryFor(it.nextKey, v);
            r = ((r == null) ? u : reducer.apply(r, u));
        }
        return r;
    }
    
    public <U> U reduceEntriesSequentially(final Fun<Map.Entry<K, V>, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        U r = null;
        V v;
        while ((v = it.advance()) != null) {
            final U u;
            if ((u = (U)transformer.apply((Map.Entry<K, V>)entryFor(it.nextKey, v))) != null) {
                r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
            }
        }
        return r;
    }
    
    public double reduceEntriesToDoubleSequentially(final ObjectToDouble<Map.Entry<K, V>> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        double r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Map.Entry<K, V>)entryFor(it.nextKey, v)));
        }
        return r;
    }
    
    public long reduceEntriesToLongSequentially(final ObjectToLong<Map.Entry<K, V>> transformer, final long basis, final LongByLongToLong reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        long r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Map.Entry<K, V>)entryFor(it.nextKey, v)));
        }
        return r;
    }
    
    public int reduceEntriesToIntSequentially(final ObjectToInt<Map.Entry<K, V>> transformer, final int basis, final IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        final Traverser<K, V, Object> it = new Traverser<K, V, Object>(this);
        int r = basis;
        V v;
        while ((v = it.advance()) != null) {
            r = reducer.apply(r, transformer.apply((Map.Entry<K, V>)entryFor(it.nextKey, v)));
        }
        return r;
    }
    
    public void forEachInParallel(final BiAction<K, V> action) {
        ForkJoinTasks.forEach(this, action).invoke();
    }
    
    public <U> void forEachInParallel(final BiFun<? super K, ? super V, ? extends U> transformer, final Action<U> action) {
        ForkJoinTasks.forEach((ConcurrentHashMapV8<Object, Object>)this, (BiFun<? super Object, ? super Object, ? extends U>)transformer, action).invoke();
    }
    
    public <U> U searchInParallel(final BiFun<? super K, ? super V, ? extends U> searchFunction) {
        return ForkJoinTasks.search((ConcurrentHashMapV8<Object, Object>)this, (BiFun<? super Object, ? super Object, ? extends U>)searchFunction).invoke();
    }
    
    public <U> U reduceInParallel(final BiFun<? super K, ? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        return ForkJoinTasks.reduce((ConcurrentHashMapV8<Object, Object>)this, (BiFun<? super Object, ? super Object, ? extends U>)transformer, reducer).invoke();
    }
    
    public double reduceToDoubleInParallel(final ObjectByObjectToDouble<? super K, ? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        return ForkJoinTasks.reduceToDouble((ConcurrentHashMapV8<Object, Object>)this, (ObjectByObjectToDouble<? super Object, ? super Object>)transformer, basis, reducer).invoke();
    }
    
    public long reduceToLongInParallel(final ObjectByObjectToLong<? super K, ? super V> transformer, final long basis, final LongByLongToLong reducer) {
        return ForkJoinTasks.reduceToLong((ConcurrentHashMapV8<Object, Object>)this, (ObjectByObjectToLong<? super Object, ? super Object>)transformer, basis, reducer).invoke();
    }
    
    public int reduceToIntInParallel(final ObjectByObjectToInt<? super K, ? super V> transformer, final int basis, final IntByIntToInt reducer) {
        return ForkJoinTasks.reduceToInt((ConcurrentHashMapV8<Object, Object>)this, (ObjectByObjectToInt<? super Object, ? super Object>)transformer, basis, reducer).invoke();
    }
    
    public void forEachKeyInParallel(final Action<K> action) {
        ForkJoinTasks.forEachKey((ConcurrentHashMapV8<K, Object>)this, action).invoke();
    }
    
    public <U> void forEachKeyInParallel(final Fun<? super K, ? extends U> transformer, final Action<U> action) {
        ForkJoinTasks.forEachKey((ConcurrentHashMapV8<Object, Object>)this, (Fun<? super Object, ? extends U>)transformer, action).invoke();
    }
    
    public <U> U searchKeysInParallel(final Fun<? super K, ? extends U> searchFunction) {
        return ForkJoinTasks.searchKeys((ConcurrentHashMapV8<Object, Object>)this, (Fun<? super Object, ? extends U>)searchFunction).invoke();
    }
    
    public K reduceKeysInParallel(final BiFun<? super K, ? super K, ? extends K> reducer) {
        return ForkJoinTasks.reduceKeys((ConcurrentHashMapV8<K, Object>)this, reducer).invoke();
    }
    
    public <U> U reduceKeysInParallel(final Fun<? super K, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        return ForkJoinTasks.reduceKeys((ConcurrentHashMapV8<Object, Object>)this, (Fun<? super Object, ? extends U>)transformer, reducer).invoke();
    }
    
    public double reduceKeysToDoubleInParallel(final ObjectToDouble<? super K> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        return ForkJoinTasks.reduceKeysToDouble((ConcurrentHashMapV8<Object, Object>)this, (ObjectToDouble<? super Object>)transformer, basis, reducer).invoke();
    }
    
    public long reduceKeysToLongInParallel(final ObjectToLong<? super K> transformer, final long basis, final LongByLongToLong reducer) {
        return ForkJoinTasks.reduceKeysToLong((ConcurrentHashMapV8<Object, Object>)this, (ObjectToLong<? super Object>)transformer, basis, reducer).invoke();
    }
    
    public int reduceKeysToIntInParallel(final ObjectToInt<? super K> transformer, final int basis, final IntByIntToInt reducer) {
        return ForkJoinTasks.reduceKeysToInt((ConcurrentHashMapV8<Object, Object>)this, (ObjectToInt<? super Object>)transformer, basis, reducer).invoke();
    }
    
    public void forEachValueInParallel(final Action<V> action) {
        ForkJoinTasks.forEachValue((ConcurrentHashMapV8<Object, V>)this, action).invoke();
    }
    
    public <U> void forEachValueInParallel(final Fun<? super V, ? extends U> transformer, final Action<U> action) {
        ForkJoinTasks.forEachValue((ConcurrentHashMapV8<Object, Object>)this, (Fun<? super Object, ? extends U>)transformer, action).invoke();
    }
    
    public <U> U searchValuesInParallel(final Fun<? super V, ? extends U> searchFunction) {
        return ForkJoinTasks.searchValues((ConcurrentHashMapV8<Object, Object>)this, (Fun<? super Object, ? extends U>)searchFunction).invoke();
    }
    
    public V reduceValuesInParallel(final BiFun<? super V, ? super V, ? extends V> reducer) {
        return ForkJoinTasks.reduceValues((ConcurrentHashMapV8<Object, V>)this, reducer).invoke();
    }
    
    public <U> U reduceValuesInParallel(final Fun<? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        return ForkJoinTasks.reduceValues((ConcurrentHashMapV8<Object, Object>)this, (Fun<? super Object, ? extends U>)transformer, reducer).invoke();
    }
    
    public double reduceValuesToDoubleInParallel(final ObjectToDouble<? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        return ForkJoinTasks.reduceValuesToDouble((ConcurrentHashMapV8<Object, Object>)this, (ObjectToDouble<? super Object>)transformer, basis, reducer).invoke();
    }
    
    public long reduceValuesToLongInParallel(final ObjectToLong<? super V> transformer, final long basis, final LongByLongToLong reducer) {
        return ForkJoinTasks.reduceValuesToLong((ConcurrentHashMapV8<Object, Object>)this, (ObjectToLong<? super Object>)transformer, basis, reducer).invoke();
    }
    
    public int reduceValuesToIntInParallel(final ObjectToInt<? super V> transformer, final int basis, final IntByIntToInt reducer) {
        return ForkJoinTasks.reduceValuesToInt((ConcurrentHashMapV8<Object, Object>)this, (ObjectToInt<? super Object>)transformer, basis, reducer).invoke();
    }
    
    public void forEachEntryInParallel(final Action<Map.Entry<K, V>> action) {
        ForkJoinTasks.forEachEntry(this, action).invoke();
    }
    
    public <U> void forEachEntryInParallel(final Fun<Map.Entry<K, V>, ? extends U> transformer, final Action<U> action) {
        ForkJoinTasks.forEachEntry(this, transformer, action).invoke();
    }
    
    public <U> U searchEntriesInParallel(final Fun<Map.Entry<K, V>, ? extends U> searchFunction) {
        return ForkJoinTasks.searchEntries(this, searchFunction).invoke();
    }
    
    public Map.Entry<K, V> reduceEntriesInParallel(final BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer) {
        return ForkJoinTasks.reduceEntries(this, reducer).invoke();
    }
    
    public <U> U reduceEntriesInParallel(final Fun<Map.Entry<K, V>, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
        return ForkJoinTasks.reduceEntries(this, transformer, reducer).invoke();
    }
    
    public double reduceEntriesToDoubleInParallel(final ObjectToDouble<Map.Entry<K, V>> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
        return ForkJoinTasks.reduceEntriesToDouble(this, transformer, basis, reducer).invoke();
    }
    
    public long reduceEntriesToLongInParallel(final ObjectToLong<Map.Entry<K, V>> transformer, final long basis, final LongByLongToLong reducer) {
        return ForkJoinTasks.reduceEntriesToLong(this, transformer, basis, reducer).invoke();
    }
    
    public int reduceEntriesToIntInParallel(final ObjectToInt<Map.Entry<K, V>> transformer, final int basis, final IntByIntToInt reducer) {
        return ForkJoinTasks.reduceEntriesToInt(this, transformer, basis, reducer).invoke();
    }
    
    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException tryReflectionInstead) {
            try {
                return AccessController.doPrivileged((PrivilegedExceptionAction<Unsafe>)new PrivilegedExceptionAction<Unsafe>() {
                    @Override
                    public Unsafe run() throws Exception {
                        final Class<Unsafe> k = Unsafe.class;
                        for (final Field f : k.getDeclaredFields()) {
                            f.setAccessible(true);
                            final Object x = f.get(null);
                            if (k.isInstance(x)) {
                                return k.cast(x);
                            }
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }
                });
            }
            catch (PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics", e.getCause());
            }
        }
    }
    
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        counterHashCodeGenerator = new AtomicInteger();
        threadCounterHashCode = new ThreadLocal<CounterHashCode>();
        try {
            U = getUnsafe();
            final Class<?> k = ConcurrentHashMapV8.class;
            SIZECTL = ConcurrentHashMapV8.U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = ConcurrentHashMapV8.U.objectFieldOffset(k.getDeclaredField("transferIndex"));
            TRANSFERORIGIN = ConcurrentHashMapV8.U.objectFieldOffset(k.getDeclaredField("transferOrigin"));
            BASECOUNT = ConcurrentHashMapV8.U.objectFieldOffset(k.getDeclaredField("baseCount"));
            COUNTERBUSY = ConcurrentHashMapV8.U.objectFieldOffset(k.getDeclaredField("counterBusy"));
            final Class<?> ck = CounterCell.class;
            CELLVALUE = ConcurrentHashMapV8.U.objectFieldOffset(ck.getDeclaredField("value"));
            final Class<?> sc = Node[].class;
            ABASE = ConcurrentHashMapV8.U.arrayBaseOffset(sc);
            final int scale = ConcurrentHashMapV8.U.arrayIndexScale(sc);
            if ((scale & scale - 1) != 0x0) {
                throw new Error("data type scale not a power of two");
            }
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }
    
    static final class CounterCell
    {
        volatile long p0;
        volatile long p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;
        volatile long value;
        volatile long q0;
        volatile long q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        
        CounterCell(final long x) {
            this.value = x;
        }
    }
    
    static final class CounterHashCode
    {
        int code;
    }
    
    static class Node<V>
    {
        final int hash;
        final Object key;
        volatile V val;
        volatile Node<V> next;
        
        Node(final int hash, final Object key, final V val, final Node<V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }
    
    static final class TreeNode<V> extends Node<V>
    {
        TreeNode<V> parent;
        TreeNode<V> left;
        TreeNode<V> right;
        TreeNode<V> prev;
        boolean red;
        
        TreeNode(final int hash, final Object key, final V val, final Node<V> next, final TreeNode<V> parent) {
            super(hash, key, val, next);
            this.parent = parent;
        }
    }
    
    static final class TreeBin<V> extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 2249069246763182397L;
        transient TreeNode<V> root;
        transient TreeNode<V> first;
        
        public final boolean isHeldExclusively() {
            return this.getState() > 0;
        }
        
        public final boolean tryAcquire(final int ignore) {
            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        
        public final boolean tryRelease(final int ignore) {
            this.setExclusiveOwnerThread(null);
            this.setState(0);
            return true;
        }
        
        public final int tryAcquireShared(final int ignore) {
            int c;
            while ((c = this.getState()) <= 0) {
                if (this.compareAndSetState(c, c - 1)) {
                    return 1;
                }
            }
            return -1;
        }
        
        public final boolean tryReleaseShared(final int ignore) {
            int c;
            while (!this.compareAndSetState(c = this.getState(), c + 1)) {}
            return c == -1;
        }
        
        private void rotateLeft(final TreeNode<V> p) {
            if (p != null) {
                final TreeNode<V> r = p.right;
                final TreeNode<V> left = r.left;
                p.right = left;
                final TreeNode<V> rl;
                if ((rl = left) != null) {
                    rl.parent = p;
                }
                final TreeNode<V> treeNode = r;
                final TreeNode<V> parent = p.parent;
                treeNode.parent = parent;
                final TreeNode<V> pp;
                if ((pp = parent) == null) {
                    this.root = r;
                }
                else if (pp.left == p) {
                    pp.left = r;
                }
                else {
                    pp.right = r;
                }
                r.left = p;
                p.parent = r;
            }
        }
        
        private void rotateRight(final TreeNode<V> p) {
            if (p != null) {
                final TreeNode<V> l = p.left;
                final TreeNode<V> right = l.right;
                p.left = right;
                final TreeNode<V> lr;
                if ((lr = right) != null) {
                    lr.parent = p;
                }
                final TreeNode<V> treeNode = l;
                final TreeNode<V> parent = p.parent;
                treeNode.parent = parent;
                final TreeNode<V> pp;
                if ((pp = parent) == null) {
                    this.root = l;
                }
                else if (pp.right == p) {
                    pp.right = l;
                }
                else {
                    pp.left = l;
                }
                l.right = p;
                p.parent = l;
            }
        }
        
        final TreeNode<V> getTreeNode(final int h, final Object k, TreeNode<V> p) {
            final Class<?> c = k.getClass();
            while (p != null) {
                final int ph;
                int dir;
                if ((ph = p.hash) == h) {
                    final Object pk;
                    if ((pk = p.key) == k || k.equals(pk)) {
                        return p;
                    }
                    final Class<?> pc;
                    if ((c != (pc = pk.getClass()) || !(k instanceof Comparable) || (dir = ((Comparable)k).compareTo(pk)) == 0) && (dir = ((c == pc) ? 0 : c.getName().compareTo(pc.getName()))) == 0) {
                        TreeNode<V> r = null;
                        final TreeNode<V> pr;
                        if ((pr = p.right) != null && h >= pr.hash && (r = this.getTreeNode(h, k, pr)) != null) {
                            return r;
                        }
                        final TreeNode<V> pl;
                        if ((pl = p.left) == null || h > pl.hash) {
                            return null;
                        }
                        dir = -1;
                    }
                }
                else {
                    dir = ((h < ph) ? -1 : 1);
                }
                p = ((dir > 0) ? p.right : p.left);
            }
            return null;
        }
        
        final V getValue(final int h, final Object k) {
            ConcurrentHashMapV8.Node<V> r = null;
            int c = this.getState();
            for (ConcurrentHashMapV8.Node<V> e = this.first; e != null; e = e.next) {
                if (c <= 0 && this.compareAndSetState(c, c - 1)) {
                    try {
                        r = this.getTreeNode(h, k, this.root);
                    }
                    finally {
                        this.releaseShared(0);
                    }
                    break;
                }
                if (e.hash == h && k.equals(e.key)) {
                    r = e;
                    break;
                }
                c = this.getState();
            }
            return (r == null) ? null : r.val;
        }
        
        final TreeNode<V> putTreeNode(final int h, final Object k, final V v) {
            final Class<?> c = k.getClass();
            TreeNode<V> pp;
            TreeNode<V> p;
            int dir;
            int ph;
            Object pk;
            Class<?> pc;
            TreeNode<V> s;
            TreeNode<V> r;
            TreeNode<V> pr;
            for (pp = this.root, p = null, dir = 0; pp != null; pp = ((dir > 0) ? p.right : p.left)) {
                p = pp;
                if ((ph = p.hash) == h) {
                    if ((pk = p.key) == k || k.equals(pk)) {
                        return p;
                    }
                    if (c != (pc = pk.getClass()) || !(k instanceof Comparable) || (dir = ((Comparable)k).compareTo(pk)) == 0) {
                        s = null;
                        r = null;
                        if ((dir = ((c == pc) ? 0 : c.getName().compareTo(pc.getName()))) == 0) {
                            if ((pr = p.right) != null && h >= pr.hash && (r = this.getTreeNode(h, k, pr)) != null) {
                                return r;
                            }
                            dir = -1;
                        }
                        else if ((pr = p.right) != null && h >= pr.hash) {
                            s = pr;
                        }
                        if (s != null && (r = this.getTreeNode(h, k, s)) != null) {
                            return r;
                        }
                    }
                }
                else {
                    dir = ((h < ph) ? -1 : 1);
                }
            }
            final TreeNode<V> f = this.first;
            final TreeNode<V> first = new TreeNode<V>(h, k, v, f, p);
            this.first = first;
            TreeNode<V> x = first;
            if (p == null) {
                this.root = x;
            }
            else {
                if (f != null) {
                    f.prev = x;
                }
                if (dir <= 0) {
                    p.left = x;
                }
                else {
                    p.right = x;
                }
                x.red = true;
                TreeNode<V> xp;
                TreeNode<V> xpp;
                while (x != null && (xp = x.parent) != null && xp.red && (xpp = xp.parent) != null) {
                    final TreeNode<V> xppl = xpp.left;
                    if (xp == xppl) {
                        final TreeNode<V> y = xpp.right;
                        if (y != null && y.red) {
                            y.red = false;
                            xp.red = false;
                            xpp.red = true;
                            x = xpp;
                        }
                        else {
                            if (x == xp.right) {
                                this.rotateLeft(x = xp);
                                xpp = (((xp = x.parent) == null) ? null : xp.parent);
                            }
                            if (xp == null) {
                                continue;
                            }
                            xp.red = false;
                            if (xpp == null) {
                                continue;
                            }
                            xpp.red = true;
                            this.rotateRight(xpp);
                        }
                    }
                    else {
                        final TreeNode<V> y = xppl;
                        if (y != null && y.red) {
                            y.red = false;
                            xp.red = false;
                            xpp.red = true;
                            x = xpp;
                        }
                        else {
                            if (x == xp.left) {
                                this.rotateRight(x = xp);
                                xpp = (((xp = x.parent) == null) ? null : xp.parent);
                            }
                            if (xp == null) {
                                continue;
                            }
                            xp.red = false;
                            if (xpp == null) {
                                continue;
                            }
                            xpp.red = true;
                            this.rotateLeft(xpp);
                        }
                    }
                }
                r = this.root;
                if (r != null && r.red) {
                    r.red = false;
                }
            }
            return null;
        }
        
        final void deleteTreeNode(final TreeNode<V> p) {
            final TreeNode<V> next = (TreeNode<V>)(TreeNode)p.next;
            final TreeNode<V> pred = p.prev;
            if (pred == null) {
                this.first = next;
            }
            else {
                pred.next = next;
            }
            if (next != null) {
                next.prev = pred;
            }
            final TreeNode<V> pl = p.left;
            final TreeNode<V> pr = p.right;
            TreeNode<V> replacement;
            if (pl != null && pr != null) {
                TreeNode<V> s;
                TreeNode<V> sl;
                for (s = pr; (sl = s.left) != null; s = sl) {}
                final boolean c = s.red;
                s.red = p.red;
                p.red = c;
                final TreeNode<V> sr = s.right;
                final TreeNode<V> pp = p.parent;
                if (s == pr) {
                    p.parent = s;
                    s.right = p;
                }
                else {
                    final TreeNode<V> sp = s.parent;
                    if ((p.parent = sp) != null) {
                        if (s == sp.left) {
                            sp.left = p;
                        }
                        else {
                            sp.right = p;
                        }
                    }
                    if ((s.right = pr) != null) {
                        pr.parent = s;
                    }
                }
                p.left = null;
                if ((p.right = sr) != null) {
                    sr.parent = p;
                }
                if ((s.left = pl) != null) {
                    pl.parent = s;
                }
                if ((s.parent = pp) == null) {
                    this.root = s;
                }
                else if (p == pp.left) {
                    pp.left = s;
                }
                else {
                    pp.right = s;
                }
                replacement = sr;
            }
            else {
                replacement = ((pl != null) ? pl : pr);
            }
            TreeNode<V> pp2 = p.parent;
            if (replacement == null) {
                if (pp2 == null) {
                    this.root = null;
                    return;
                }
                replacement = p;
            }
            else {
                if ((replacement.parent = pp2) == null) {
                    this.root = replacement;
                }
                else if (p == pp2.left) {
                    pp2.left = replacement;
                }
                else {
                    pp2.right = replacement;
                }
                final TreeNode<V> left = null;
                p.parent = (TreeNode<V>)left;
                p.right = (TreeNode<V>)left;
                p.left = (TreeNode<V>)left;
            }
            if (!p.red) {
                TreeNode<V> x = replacement;
                while (x != null) {
                    TreeNode<V> xp;
                    if (x.red || (xp = x.parent) == null) {
                        x.red = false;
                        break;
                    }
                    final TreeNode<V> xpl;
                    if (x == (xpl = xp.left)) {
                        TreeNode<V> sib = xp.right;
                        if (sib != null && sib.red) {
                            sib.red = false;
                            xp.red = true;
                            this.rotateLeft(xp);
                            sib = (((xp = x.parent) == null) ? null : xp.right);
                        }
                        if (sib == null) {
                            x = xp;
                        }
                        else {
                            final TreeNode<V> sl2 = sib.left;
                            TreeNode<V> sr2 = sib.right;
                            if ((sr2 == null || !sr2.red) && (sl2 == null || !sl2.red)) {
                                sib.red = true;
                                x = xp;
                            }
                            else {
                                if (sr2 == null || !sr2.red) {
                                    if (sl2 != null) {
                                        sl2.red = false;
                                    }
                                    sib.red = true;
                                    this.rotateRight(sib);
                                    sib = (((xp = x.parent) == null) ? null : xp.right);
                                }
                                if (sib != null) {
                                    sib.red = (xp != null && xp.red);
                                    if ((sr2 = sib.right) != null) {
                                        sr2.red = false;
                                    }
                                }
                                if (xp != null) {
                                    xp.red = false;
                                    this.rotateLeft(xp);
                                }
                                x = this.root;
                            }
                        }
                    }
                    else {
                        TreeNode<V> sib = xpl;
                        if (sib != null && sib.red) {
                            sib.red = false;
                            xp.red = true;
                            this.rotateRight(xp);
                            sib = (((xp = x.parent) == null) ? null : xp.left);
                        }
                        if (sib == null) {
                            x = xp;
                        }
                        else {
                            TreeNode<V> sl2 = sib.left;
                            final TreeNode<V> sr2 = sib.right;
                            if ((sl2 == null || !sl2.red) && (sr2 == null || !sr2.red)) {
                                sib.red = true;
                                x = xp;
                            }
                            else {
                                if (sl2 == null || !sl2.red) {
                                    if (sr2 != null) {
                                        sr2.red = false;
                                    }
                                    sib.red = true;
                                    this.rotateLeft(sib);
                                    sib = (((xp = x.parent) == null) ? null : xp.left);
                                }
                                if (sib != null) {
                                    sib.red = (xp != null && xp.red);
                                    if ((sl2 = sib.left) != null) {
                                        sl2.red = false;
                                    }
                                }
                                if (xp != null) {
                                    xp.red = false;
                                    this.rotateRight(xp);
                                }
                                x = this.root;
                            }
                        }
                    }
                }
            }
            if (p == replacement && (pp2 = p.parent) != null) {
                if (p == pp2.left) {
                    pp2.left = null;
                }
                else if (p == pp2.right) {
                    pp2.right = null;
                }
                p.parent = null;
            }
        }
    }
    
    static class Traverser<K, V, R> extends CountedCompleter<R>
    {
        final ConcurrentHashMapV8<K, V> map;
        Node<V> next;
        Object nextKey;
        V nextVal;
        Node<V>[] tab;
        int index;
        int baseIndex;
        int baseLimit;
        int baseSize;
        int batch;
        
        Traverser(final ConcurrentHashMapV8<K, V> map) {
            this.map = map;
        }
        
        Traverser(final ConcurrentHashMapV8<K, V> map, final Traverser<K, V, ?> it, final int batch) {
            super(it);
            this.batch = batch;
            this.map = map;
            if (map != null && it != null) {
                Node<V>[] t;
                if ((t = it.tab) == null) {
                    final Node<V>[] table = map.table;
                    it.tab = table;
                    if ((t = table) != null) {
                        final int length = t.length;
                        it.baseSize = length;
                        it.baseLimit = length;
                    }
                }
                this.tab = t;
                this.baseSize = it.baseSize;
                final int baseLimit = it.baseLimit;
                this.baseLimit = baseLimit;
                final int hi = baseLimit;
                final int baseLimit2 = hi + it.baseIndex + 1 >>> 1;
                this.baseIndex = baseLimit2;
                this.index = baseLimit2;
                it.baseLimit = baseLimit2;
            }
        }
        
        final V advance() {
            Node<V> e = this.next;
            V ev = null;
        Label_0220:
            do {
                if (e != null) {
                    e = e.next;
                }
                while (e == null) {
                    Node<V>[] t;
                    int n;
                    if ((t = this.tab) != null) {
                        n = t.length;
                    }
                    else {
                        final ConcurrentHashMapV8<K, V> m;
                        if ((m = this.map) == null) {
                            break Label_0220;
                        }
                        final Node<V>[] table = m.table;
                        this.tab = table;
                        if ((t = table) == null) {
                            break Label_0220;
                        }
                        final int length = t.length;
                        this.baseSize = length;
                        this.baseLimit = length;
                        n = length;
                    }
                    final int b;
                    int i;
                    if ((b = this.baseIndex) >= this.baseLimit || (i = this.index) < 0) {
                        break Label_0220;
                    }
                    if (i >= n) {
                        break Label_0220;
                    }
                    if ((e = ConcurrentHashMapV8.tabAt(t, i)) != null && e.hash < 0) {
                        final Object ek;
                        if (!((ek = e.key) instanceof TreeBin)) {
                            this.tab = (Node<V>[])ek;
                            continue;
                        }
                        e = (Node<V>)((TreeBin)ek).first;
                    }
                    this.index = (((i += this.baseSize) < n) ? i : (this.baseIndex = b + 1));
                }
                this.nextKey = e.key;
            } while ((ev = e.val) == null);
            this.next = e;
            return this.nextVal = ev;
        }
        
        public final void remove() {
            Object k = this.nextKey;
            if (k == null && (this.advance() == null || (k = this.nextKey) == null)) {
                throw new IllegalStateException();
            }
            ((ConcurrentHashMapV8<Object, Object>)this.map).internalReplace(k, null, null);
        }
        
        public final boolean hasNext() {
            return this.nextVal != null || this.advance() != null;
        }
        
        public final boolean hasMoreElements() {
            return this.hasNext();
        }
        
        @Override
        public void compute() {
        }
        
        final int preSplit() {
            int b;
            final ConcurrentHashMapV8<K, V> m;
            if ((b = this.batch) < 0 && (m = this.map) != null) {
                Node<V>[] t;
                if ((t = this.tab) == null) {
                    final Node<V>[] table = m.table;
                    this.tab = table;
                    if ((t = table) != null) {
                        final int length = t.length;
                        this.baseSize = length;
                        this.baseLimit = length;
                    }
                }
                if (t != null) {
                    final long n = m.sumCount();
                    final ForkJoinPool pool;
                    final int par = ((pool = ForkJoinTask.getPool()) == null) ? ForkJoinPool.getCommonPoolParallelism() : pool.getParallelism();
                    final int sp = par << 3;
                    b = ((n <= 0L) ? 0 : ((n < sp) ? ((int)n) : sp));
                }
            }
            b = ((b <= 1 || this.baseIndex == this.baseLimit) ? 0 : (b >>> 1));
            if ((this.batch = b) > 0) {
                this.addToPendingCount(1);
            }
            return b;
        }
    }
    
    static final class KeyIterator<K, V> extends Traverser<K, V, Object> implements Spliterator<K>, Enumeration<K>
    {
        KeyIterator(final ConcurrentHashMapV8<K, V> map) {
            super(map);
        }
        
        KeyIterator(final ConcurrentHashMapV8<K, V> map, final Traverser<K, V, Object> it) {
            super(map, it, -1);
        }
        
        @Override
        public KeyIterator<K, V> split() {
            if (this.nextKey != null) {
                throw new IllegalStateException();
            }
            return new KeyIterator<K, V>((ConcurrentHashMapV8<K, V>)this.map, this);
        }
        
        @Override
        public final K next() {
            if (this.nextVal == null && this.advance() == null) {
                throw new NoSuchElementException();
            }
            final Object k = this.nextKey;
            this.nextVal = null;
            return (K)k;
        }
        
        @Override
        public final K nextElement() {
            return this.next();
        }
    }
    
    static final class ValueIterator<K, V> extends Traverser<K, V, Object> implements Spliterator<V>, Enumeration<V>
    {
        ValueIterator(final ConcurrentHashMapV8<K, V> map) {
            super(map);
        }
        
        ValueIterator(final ConcurrentHashMapV8<K, V> map, final Traverser<K, V, Object> it) {
            super(map, it, -1);
        }
        
        @Override
        public ValueIterator<K, V> split() {
            if (this.nextKey != null) {
                throw new IllegalStateException();
            }
            return new ValueIterator<K, V>((ConcurrentHashMapV8<K, V>)this.map, this);
        }
        
        @Override
        public final V next() {
            V v;
            if ((v = (V)this.nextVal) == null && (v = this.advance()) == null) {
                throw new NoSuchElementException();
            }
            this.nextVal = null;
            return v;
        }
        
        @Override
        public final V nextElement() {
            return this.next();
        }
    }
    
    static final class EntryIterator<K, V> extends Traverser<K, V, Object> implements Spliterator<Map.Entry<K, V>>
    {
        EntryIterator(final ConcurrentHashMapV8<K, V> map) {
            super(map);
        }
        
        EntryIterator(final ConcurrentHashMapV8<K, V> map, final Traverser<K, V, Object> it) {
            super(map, it, -1);
        }
        
        @Override
        public EntryIterator<K, V> split() {
            if (this.nextKey != null) {
                throw new IllegalStateException();
            }
            return new EntryIterator<K, V>((ConcurrentHashMapV8<K, V>)this.map, this);
        }
        
        @Override
        public final Map.Entry<K, V> next() {
            V v;
            if ((v = (V)this.nextVal) == null && (v = this.advance()) == null) {
                throw new NoSuchElementException();
            }
            final Object k = this.nextKey;
            this.nextVal = null;
            return new MapEntry<K, V>((K)k, v, (ConcurrentHashMapV8<K, V>)this.map);
        }
    }
    
    static final class MapEntry<K, V> implements Map.Entry<K, V>
    {
        final K key;
        V val;
        final ConcurrentHashMapV8<K, V> map;
        
        MapEntry(final K key, final V val, final ConcurrentHashMapV8<K, V> map) {
            this.key = key;
            this.val = val;
            this.map = map;
        }
        
        @Override
        public final K getKey() {
            return this.key;
        }
        
        @Override
        public final V getValue() {
            return this.val;
        }
        
        @Override
        public final int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }
        
        @Override
        public final String toString() {
            return this.key + "=" + this.val;
        }
        
        @Override
        public final boolean equals(final Object o) {
            final Map.Entry<?, ?> e;
            final Object k;
            final Object v;
            return o instanceof Map.Entry && (k = (e = (Map.Entry<?, ?>)o).getKey()) != null && (v = e.getValue()) != null && (k == this.key || k.equals(this.key)) && (v == this.val || v.equals(this.val));
        }
        
        @Override
        public final V setValue(final V value) {
            if (value == null) {
                throw new NullPointerException();
            }
            final V v = this.val;
            this.val = value;
            this.map.put(this.key, value);
            return v;
        }
    }
    
    static class Segment<K, V> implements Serializable
    {
        private static final long serialVersionUID = 2249069246763182397L;
        final float loadFactor;
        
        Segment(final float lf) {
            this.loadFactor = lf;
        }
    }
    
    abstract static class CHMView<K, V>
    {
        final ConcurrentHashMapV8<K, V> map;
        private static final String oomeMsg = "Required array size too large";
        
        CHMView(final ConcurrentHashMapV8<K, V> map) {
            this.map = map;
        }
        
        public ConcurrentHashMapV8<K, V> getMap() {
            return this.map;
        }
        
        public final int size() {
            return this.map.size();
        }
        
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }
        
        public final void clear() {
            this.map.clear();
        }
        
        public abstract Iterator<?> iterator();
        
        public abstract boolean contains(final Object p0);
        
        public abstract boolean remove(final Object p0);
        
        public final Object[] toArray() {
            final long sz = this.map.mappingCount();
            if (sz > 2147483639L) {
                throw new OutOfMemoryError("Required array size too large");
            }
            int n = (int)sz;
            Object[] r = new Object[n];
            int i = 0;
            final Iterator<?> it = this.iterator();
            while (it.hasNext()) {
                if (i == n) {
                    if (n >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (n >= 1073741819) {
                        n = 2147483639;
                    }
                    else {
                        n += (n >>> 1) + 1;
                    }
                    r = Arrays.copyOf(r, n);
                }
                r[i++] = it.next();
            }
            return (i == n) ? r : Arrays.copyOf(r, i);
        }
        
        public final <T> T[] toArray(final T[] a) {
            final long sz = this.map.mappingCount();
            if (sz > 2147483639L) {
                throw new OutOfMemoryError("Required array size too large");
            }
            final int m = (int)sz;
            T[] r = (T[])((a.length >= m) ? a : ((Object[])Array.newInstance(a.getClass().getComponentType(), m)));
            int n = r.length;
            int i = 0;
            final Iterator<?> it = this.iterator();
            while (it.hasNext()) {
                if (i == n) {
                    if (n >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (n >= 1073741819) {
                        n = 2147483639;
                    }
                    else {
                        n += (n >>> 1) + 1;
                    }
                    r = Arrays.copyOf(r, n);
                }
                r[i++] = (T)it.next();
            }
            if (a == r && i < n) {
                r[i] = null;
                return r;
            }
            return (i == n) ? r : Arrays.copyOf(r, i);
        }
        
        @Override
        public final int hashCode() {
            int h = 0;
            final Iterator<?> it = this.iterator();
            while (it.hasNext()) {
                h += it.next().hashCode();
            }
            return h;
        }
        
        @Override
        public final String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            final Iterator<?> it = this.iterator();
            if (it.hasNext()) {
                while (true) {
                    final Object e = it.next();
                    sb.append((e == this) ? "(this Collection)" : e);
                    if (!it.hasNext()) {
                        break;
                    }
                    sb.append(',').append(' ');
                }
            }
            return sb.append(']').toString();
        }
        
        public final boolean containsAll(final Collection<?> c) {
            if (c != this) {
                for (final Object e : c) {
                    if (e == null || !this.contains(e)) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        public final boolean removeAll(final Collection<?> c) {
            boolean modified = false;
            final Iterator<?> it = this.iterator();
            while (it.hasNext()) {
                if (c.contains(it.next())) {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public final boolean retainAll(final Collection<?> c) {
            boolean modified = false;
            final Iterator<?> it = this.iterator();
            while (it.hasNext()) {
                if (!c.contains(it.next())) {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }
    }
    
    public static class KeySetView<K, V> extends CHMView<K, V> implements Set<K>, Serializable
    {
        private static final long serialVersionUID = 7249069246763182397L;
        private final V value;
        
        KeySetView(final ConcurrentHashMapV8<K, V> map, final V value) {
            super(map);
            this.value = value;
        }
        
        public V getMappedValue() {
            return this.value;
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.map.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.map.remove(o) != null;
        }
        
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator<K, Object>(this.map);
        }
        
        @Override
        public boolean add(final K e) {
            final V v;
            if ((v = this.value) == null) {
                throw new UnsupportedOperationException();
            }
            return ((ConcurrentHashMapV8<Object, Object>)this.map).internalPut(e, v, true) == null;
        }
        
        @Override
        public boolean addAll(final Collection<? extends K> c) {
            boolean added = false;
            final V v;
            if ((v = this.value) == null) {
                throw new UnsupportedOperationException();
            }
            for (final K e : c) {
                if (((ConcurrentHashMapV8<Object, Object>)this.map).internalPut(e, v, true) == null) {
                    added = true;
                }
            }
            return added;
        }
        
        @Override
        public boolean equals(final Object o) {
            final Set<?> c;
            return o instanceof Set && ((c = (Set<?>)o) == this || (this.containsAll(c) && c.containsAll(this)));
        }
    }
    
    public static final class ValuesView<K, V> extends CHMView<K, V> implements Collection<V>
    {
        ValuesView(final ConcurrentHashMapV8<K, V> map) {
            super(map);
        }
        
        @Override
        public final boolean contains(final Object o) {
            return this.map.containsValue(o);
        }
        
        @Override
        public final boolean remove(final Object o) {
            if (o != null) {
                final Iterator<V> it = new ValueIterator<Object, V>(this.map);
                while (it.hasNext()) {
                    if (o.equals(it.next())) {
                        it.remove();
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Override
        public final Iterator<V> iterator() {
            return new ValueIterator<Object, V>(this.map);
        }
        
        @Override
        public final boolean add(final V e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public final boolean addAll(final Collection<? extends V> c) {
            throw new UnsupportedOperationException();
        }
    }
    
    public static final class EntrySetView<K, V> extends CHMView<K, V> implements Set<Map.Entry<K, V>>
    {
        EntrySetView(final ConcurrentHashMapV8<K, V> map) {
            super(map);
        }
        
        @Override
        public final boolean contains(final Object o) {
            final Map.Entry<?, ?> e;
            final Object k;
            final Object r;
            final Object v;
            return o instanceof Map.Entry && (k = (e = (Map.Entry<?, ?>)o).getKey()) != null && (r = this.map.get(k)) != null && (v = e.getValue()) != null && (v == r || v.equals(r));
        }
        
        @Override
        public final boolean remove(final Object o) {
            final Map.Entry<?, ?> e;
            final Object k;
            final Object v;
            return o instanceof Map.Entry && (k = (e = (Map.Entry<?, ?>)o).getKey()) != null && (v = e.getValue()) != null && this.map.remove(k, v);
        }
        
        @Override
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator<K, V>(this.map);
        }
        
        @Override
        public final boolean add(final Map.Entry<K, V> e) {
            return ((ConcurrentHashMapV8<Object, Object>)this.map).internalPut(e.getKey(), e.getValue(), false) == null;
        }
        
        @Override
        public final boolean addAll(final Collection<? extends Map.Entry<K, V>> c) {
            boolean added = false;
            for (final Map.Entry<K, V> e : c) {
                if (this.add(e)) {
                    added = true;
                }
            }
            return added;
        }
        
        @Override
        public boolean equals(final Object o) {
            final Set<?> c;
            return o instanceof Set && ((c = (Set<?>)o) == this || (this.containsAll(c) && c.containsAll(this)));
        }
    }
    
    public static class ForkJoinTasks
    {
        public static <K, V> ForkJoinTask<Void> forEach(final ConcurrentHashMapV8<K, V> map, final BiAction<K, V> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachMappingTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (BiAction<Object, Object>)action);
        }
        
        public static <K, V, U> ForkJoinTask<Void> forEach(final ConcurrentHashMapV8<K, V> map, final BiFun<? super K, ? super V, ? extends U> transformer, final Action<U> action) {
            if (transformer == null || action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachTransformedMappingTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (BiFun<? super Object, ? super Object, ?>)transformer, (Action<Object>)action);
        }
        
        public static <K, V, U> ForkJoinTask<U> search(final ConcurrentHashMapV8<K, V> map, final BiFun<? super K, ? super V, ? extends U> searchFunction) {
            if (searchFunction == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new SearchMappingsTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (BiFun<? super Object, ? super Object, ?>)searchFunction, new AtomicReference<Object>());
        }
        
        public static <K, V, U> ForkJoinTask<U> reduce(final ConcurrentHashMapV8<K, V> map, final BiFun<? super K, ? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new MapReduceMappingsTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (BiFun<? super Object, ? super Object, ?>)transformer, (BiFun<? super Object, ? super Object, ?>)reducer);
        }
        
        public static <K, V> ForkJoinTask<Double> reduceToDouble(final ConcurrentHashMapV8<K, V> map, final ObjectByObjectToDouble<? super K, ? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Double>)new MapReduceMappingsToDoubleTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectByObjectToDouble<? super Object, ? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Long> reduceToLong(final ConcurrentHashMapV8<K, V> map, final ObjectByObjectToLong<? super K, ? super V> transformer, final long basis, final LongByLongToLong reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Long>)new MapReduceMappingsToLongTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectByObjectToLong<? super Object, ? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Integer> reduceToInt(final ConcurrentHashMapV8<K, V> map, final ObjectByObjectToInt<? super K, ? super V> transformer, final int basis, final IntByIntToInt reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Integer>)new MapReduceMappingsToIntTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectByObjectToInt<? super Object, ? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Void> forEachKey(final ConcurrentHashMapV8<K, V> map, final Action<K> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachKeyTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Action<Object>)action);
        }
        
        public static <K, V, U> ForkJoinTask<Void> forEachKey(final ConcurrentHashMapV8<K, V> map, final Fun<? super K, ? extends U> transformer, final Action<U> action) {
            if (transformer == null || action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachTransformedKeyTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Fun<? super Object, ?>)transformer, (Action<Object>)action);
        }
        
        public static <K, V, U> ForkJoinTask<U> searchKeys(final ConcurrentHashMapV8<K, V> map, final Fun<? super K, ? extends U> searchFunction) {
            if (searchFunction == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new SearchKeysTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Fun<? super Object, ?>)searchFunction, new AtomicReference<Object>());
        }
        
        public static <K, V> ForkJoinTask<K> reduceKeys(final ConcurrentHashMapV8<K, V> map, final BiFun<? super K, ? super K, ? extends K> reducer) {
            if (reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<K>)new ReduceKeysTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (BiFun<? super Object, ? super Object, ?>)reducer);
        }
        
        public static <K, V, U> ForkJoinTask<U> reduceKeys(final ConcurrentHashMapV8<K, V> map, final Fun<? super K, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new MapReduceKeysTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (Fun<? super Object, ?>)transformer, (BiFun<? super Object, ? super Object, ?>)reducer);
        }
        
        public static <K, V> ForkJoinTask<Double> reduceKeysToDouble(final ConcurrentHashMapV8<K, V> map, final ObjectToDouble<? super K> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Double>)new MapReduceKeysToDoubleTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToDouble<? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Long> reduceKeysToLong(final ConcurrentHashMapV8<K, V> map, final ObjectToLong<? super K> transformer, final long basis, final LongByLongToLong reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Long>)new MapReduceKeysToLongTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToLong<? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Integer> reduceKeysToInt(final ConcurrentHashMapV8<K, V> map, final ObjectToInt<? super K> transformer, final int basis, final IntByIntToInt reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Integer>)new MapReduceKeysToIntTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToInt<? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Void> forEachValue(final ConcurrentHashMapV8<K, V> map, final Action<V> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachValueTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Action<Object>)action);
        }
        
        public static <K, V, U> ForkJoinTask<Void> forEachValue(final ConcurrentHashMapV8<K, V> map, final Fun<? super V, ? extends U> transformer, final Action<U> action) {
            if (transformer == null || action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachTransformedValueTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Fun<? super Object, ?>)transformer, (Action<Object>)action);
        }
        
        public static <K, V, U> ForkJoinTask<U> searchValues(final ConcurrentHashMapV8<K, V> map, final Fun<? super V, ? extends U> searchFunction) {
            if (searchFunction == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new SearchValuesTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Fun<? super Object, ?>)searchFunction, new AtomicReference<Object>());
        }
        
        public static <K, V> ForkJoinTask<V> reduceValues(final ConcurrentHashMapV8<K, V> map, final BiFun<? super V, ? super V, ? extends V> reducer) {
            if (reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<V>)new ReduceValuesTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (BiFun<? super Object, ? super Object, ?>)reducer);
        }
        
        public static <K, V, U> ForkJoinTask<U> reduceValues(final ConcurrentHashMapV8<K, V> map, final Fun<? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new MapReduceValuesTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (Fun<? super Object, ?>)transformer, (BiFun<? super Object, ? super Object, ?>)reducer);
        }
        
        public static <K, V> ForkJoinTask<Double> reduceValuesToDouble(final ConcurrentHashMapV8<K, V> map, final ObjectToDouble<? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Double>)new MapReduceValuesToDoubleTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToDouble<? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Long> reduceValuesToLong(final ConcurrentHashMapV8<K, V> map, final ObjectToLong<? super V> transformer, final long basis, final LongByLongToLong reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Long>)new MapReduceValuesToLongTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToLong<? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Integer> reduceValuesToInt(final ConcurrentHashMapV8<K, V> map, final ObjectToInt<? super V> transformer, final int basis, final IntByIntToInt reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Integer>)new MapReduceValuesToIntTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToInt<? super Object>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Void> forEachEntry(final ConcurrentHashMapV8<K, V> map, final Action<Map.Entry<K, V>> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachEntryTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Action<Map.Entry<Object, Object>>)action);
        }
        
        public static <K, V, U> ForkJoinTask<Void> forEachEntry(final ConcurrentHashMapV8<K, V> map, final Fun<Map.Entry<K, V>, ? extends U> transformer, final Action<U> action) {
            if (transformer == null || action == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Void>)new ForEachTransformedEntryTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Fun<Map.Entry<Object, Object>, ?>)transformer, (Action<Object>)action);
        }
        
        public static <K, V, U> ForkJoinTask<U> searchEntries(final ConcurrentHashMapV8<K, V> map, final Fun<Map.Entry<K, V>, ? extends U> searchFunction) {
            if (searchFunction == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new SearchEntriesTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, (Fun<Map.Entry<Object, Object>, ?>)searchFunction, new AtomicReference<Object>());
        }
        
        public static <K, V> ForkJoinTask<Map.Entry<K, V>> reduceEntries(final ConcurrentHashMapV8<K, V> map, final BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer) {
            if (reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Map.Entry<K, V>>)new ReduceEntriesTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (BiFun<Map.Entry<Object, Object>, Map.Entry<Object, Object>, ? extends Map.Entry<Object, Object>>)reducer);
        }
        
        public static <K, V, U> ForkJoinTask<U> reduceEntries(final ConcurrentHashMapV8<K, V> map, final Fun<Map.Entry<K, V>, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<U>)new MapReduceEntriesTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (Fun<Map.Entry<Object, Object>, ?>)transformer, (BiFun<? super Object, ? super Object, ?>)reducer);
        }
        
        public static <K, V> ForkJoinTask<Double> reduceEntriesToDouble(final ConcurrentHashMapV8<K, V> map, final ObjectToDouble<Map.Entry<K, V>> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Double>)new MapReduceEntriesToDoubleTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToDouble<Map.Entry<Object, Object>>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Long> reduceEntriesToLong(final ConcurrentHashMapV8<K, V> map, final ObjectToLong<Map.Entry<K, V>> transformer, final long basis, final LongByLongToLong reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Long>)new MapReduceEntriesToLongTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToLong<Map.Entry<Object, Object>>)transformer, basis, reducer);
        }
        
        public static <K, V> ForkJoinTask<Integer> reduceEntriesToInt(final ConcurrentHashMapV8<K, V> map, final ObjectToInt<Map.Entry<K, V>> transformer, final int basis, final IntByIntToInt reducer) {
            if (transformer == null || reducer == null) {
                throw new NullPointerException();
            }
            return (ForkJoinTask<Integer>)new MapReduceEntriesToIntTask((ConcurrentHashMapV8<Object, Object>)map, null, -1, null, (ObjectToInt<Map.Entry<Object, Object>>)transformer, basis, reducer);
        }
    }
    
    static final class ForEachKeyTask<K, V> extends Traverser<K, V, Void>
    {
        final Action<K> action;
        
        ForEachKeyTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Action<K> action) {
            super(m, p, b);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Action<K> action;
            if ((action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachKeyTask((ConcurrentHashMapV8<K, V>)this.map, this, b, action).fork();
                }
                while (this.advance() != null) {
                    action.apply((K)this.nextKey);
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachValueTask<K, V> extends Traverser<K, V, Void>
    {
        final Action<V> action;
        
        ForEachValueTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Action<V> action) {
            super(m, p, b);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Action<V> action;
            if ((action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachValueTask((ConcurrentHashMapV8<K, V>)this.map, this, b, action).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    action.apply(v);
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachEntryTask<K, V> extends Traverser<K, V, Void>
    {
        final Action<Map.Entry<K, V>> action;
        
        ForEachEntryTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Action<Map.Entry<K, V>> action) {
            super(m, p, b);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Action<Map.Entry<K, V>> action;
            if ((action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachEntryTask((ConcurrentHashMapV8<K, V>)this.map, this, b, action).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    action.apply((Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v));
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachMappingTask<K, V> extends Traverser<K, V, Void>
    {
        final BiAction<K, V> action;
        
        ForEachMappingTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final BiAction<K, V> action) {
            super(m, p, b);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final BiAction<K, V> action;
            if ((action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachMappingTask((ConcurrentHashMapV8<K, V>)this.map, this, b, action).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    action.apply((K)this.nextKey, v);
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachTransformedKeyTask<K, V, U> extends Traverser<K, V, Void>
    {
        final Fun<? super K, ? extends U> transformer;
        final Action<U> action;
        
        ForEachTransformedKeyTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Fun<? super K, ? extends U> transformer, final Action<U> action) {
            super(m, p, b);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Fun<? super K, ? extends U> transformer;
            final Action<U> action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachTransformedKeyTask(this.map, this, b, transformer, action).fork();
                }
                while (this.advance() != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Object)this.nextKey)) != null) {
                        action.apply(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachTransformedValueTask<K, V, U> extends Traverser<K, V, Void>
    {
        final Fun<? super V, ? extends U> transformer;
        final Action<U> action;
        
        ForEachTransformedValueTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Fun<? super V, ? extends U> transformer, final Action<U> action) {
            super(m, p, b);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Fun<? super V, ? extends U> transformer;
            final Action<U> action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachTransformedValueTask(this.map, this, b, transformer, action).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Object)v)) != null) {
                        action.apply(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachTransformedEntryTask<K, V, U> extends Traverser<K, V, Void>
    {
        final Fun<Map.Entry<K, V>, ? extends U> transformer;
        final Action<U> action;
        
        ForEachTransformedEntryTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Fun<Map.Entry<K, V>, ? extends U> transformer, final Action<U> action) {
            super(m, p, b);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Fun<Map.Entry<K, V>, ? extends U> transformer;
            final Action<U> action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachTransformedEntryTask(this.map, this, b, transformer, action).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v))) != null) {
                        action.apply(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachTransformedMappingTask<K, V, U> extends Traverser<K, V, Void>
    {
        final BiFun<? super K, ? super V, ? extends U> transformer;
        final Action<U> action;
        
        ForEachTransformedMappingTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final BiFun<? super K, ? super V, ? extends U> transformer, final Action<U> action) {
            super(m, p, b);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final BiFun<? super K, ? super V, ? extends U> transformer;
            final Action<U> action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    new ForEachTransformedMappingTask(this.map, this, b, transformer, action).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Object)this.nextKey, (Object)v)) != null) {
                        action.apply(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class SearchKeysTask<K, V, U> extends Traverser<K, V, U>
    {
        final Fun<? super K, ? extends U> searchFunction;
        final AtomicReference<U> result;
        
        SearchKeysTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Fun<? super K, ? extends U> searchFunction, final AtomicReference<U> result) {
            super(m, p, b);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final U getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final Fun<? super K, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                while (result.get() == null) {
                    final int b;
                    if ((b = this.preSplit()) <= 0) {
                        while (result.get() == null) {
                            if (this.advance() == null) {
                                this.propagateCompletion();
                                break;
                            }
                            final U u;
                            if ((u = (U)searchFunction.apply((Object)this.nextKey)) == null) {
                                continue;
                            }
                            if (result.compareAndSet(null, u)) {
                                this.quietlyCompleteRoot();
                                break;
                            }
                            break;
                        }
                        return;
                    }
                    new SearchKeysTask(this.map, this, b, searchFunction, result).fork();
                }
            }
        }
    }
    
    static final class SearchValuesTask<K, V, U> extends Traverser<K, V, U>
    {
        final Fun<? super V, ? extends U> searchFunction;
        final AtomicReference<U> result;
        
        SearchValuesTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Fun<? super V, ? extends U> searchFunction, final AtomicReference<U> result) {
            super(m, p, b);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final U getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final Fun<? super V, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                while (result.get() == null) {
                    final int b;
                    if ((b = this.preSplit()) <= 0) {
                        while (result.get() == null) {
                            final V v;
                            if ((v = this.advance()) == null) {
                                this.propagateCompletion();
                                break;
                            }
                            final U u;
                            if ((u = (U)searchFunction.apply((Object)v)) == null) {
                                continue;
                            }
                            if (result.compareAndSet(null, u)) {
                                this.quietlyCompleteRoot();
                                break;
                            }
                            break;
                        }
                        return;
                    }
                    new SearchValuesTask(this.map, this, b, searchFunction, result).fork();
                }
            }
        }
    }
    
    static final class SearchEntriesTask<K, V, U> extends Traverser<K, V, U>
    {
        final Fun<Map.Entry<K, V>, ? extends U> searchFunction;
        final AtomicReference<U> result;
        
        SearchEntriesTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final Fun<Map.Entry<K, V>, ? extends U> searchFunction, final AtomicReference<U> result) {
            super(m, p, b);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final U getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final Fun<Map.Entry<K, V>, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                while (result.get() == null) {
                    final int b;
                    if ((b = this.preSplit()) <= 0) {
                        while (result.get() == null) {
                            final V v;
                            if ((v = this.advance()) == null) {
                                this.propagateCompletion();
                                break;
                            }
                            final U u;
                            if ((u = (U)searchFunction.apply((Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v))) != null) {
                                if (result.compareAndSet(null, u)) {
                                    this.quietlyCompleteRoot();
                                }
                            }
                        }
                        return;
                    }
                    new SearchEntriesTask(this.map, this, b, searchFunction, result).fork();
                }
            }
        }
    }
    
    static final class SearchMappingsTask<K, V, U> extends Traverser<K, V, U>
    {
        final BiFun<? super K, ? super V, ? extends U> searchFunction;
        final AtomicReference<U> result;
        
        SearchMappingsTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final BiFun<? super K, ? super V, ? extends U> searchFunction, final AtomicReference<U> result) {
            super(m, p, b);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final U getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final BiFun<? super K, ? super V, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                while (result.get() == null) {
                    final int b;
                    if ((b = this.preSplit()) <= 0) {
                        while (result.get() == null) {
                            final V v;
                            if ((v = this.advance()) == null) {
                                this.propagateCompletion();
                                break;
                            }
                            final U u;
                            if ((u = (U)searchFunction.apply((Object)this.nextKey, (Object)v)) == null) {
                                continue;
                            }
                            if (result.compareAndSet(null, u)) {
                                this.quietlyCompleteRoot();
                                break;
                            }
                            break;
                        }
                        return;
                    }
                    new SearchMappingsTask(this.map, this, b, searchFunction, result).fork();
                }
            }
        }
    }
    
    static final class ReduceKeysTask<K, V> extends Traverser<K, V, K>
    {
        final BiFun<? super K, ? super K, ? extends K> reducer;
        K result;
        ReduceKeysTask<K, V> rights;
        ReduceKeysTask<K, V> nextRight;
        
        ReduceKeysTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final ReduceKeysTask<K, V> nextRight, final BiFun<? super K, ? super K, ? extends K> reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }
        
        @Override
        public final K getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun<? super K, ? super K, ? extends K> reducer;
            if ((reducer = this.reducer) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new ReduceKeysTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, reducer)).fork();
                }
                K r = null;
                while (this.advance() != null) {
                    final K u = (K)this.nextKey;
                    r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final ReduceKeysTask<K, V> t = (ReduceKeysTask)c;
                    ReduceKeysTask<K, V> nextRight;
                    for (ReduceKeysTask<K, V> s = t.rights; s != null; s = nextRight) {
                        final K sr;
                        if ((sr = s.result) != null) {
                            final K tr;
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply((Object)tr, (Object)sr));
                        }
                        final ReduceKeysTask<K, V> reduceKeysTask = t;
                        nextRight = s.nextRight;
                        reduceKeysTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class ReduceValuesTask<K, V> extends Traverser<K, V, V>
    {
        final BiFun<? super V, ? super V, ? extends V> reducer;
        V result;
        ReduceValuesTask<K, V> rights;
        ReduceValuesTask<K, V> nextRight;
        
        ReduceValuesTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final ReduceValuesTask<K, V> nextRight, final BiFun<? super V, ? super V, ? extends V> reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }
        
        @Override
        public final V getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun<? super V, ? super V, ? extends V> reducer;
            if ((reducer = this.reducer) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new ReduceValuesTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, reducer)).fork();
                }
                V r = null;
                V v;
                while ((v = this.advance()) != null) {
                    final V u = v;
                    r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final ReduceValuesTask<K, V> t = (ReduceValuesTask)c;
                    ReduceValuesTask<K, V> nextRight;
                    for (ReduceValuesTask<K, V> s = t.rights; s != null; s = nextRight) {
                        final V sr;
                        if ((sr = s.result) != null) {
                            final V tr;
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply((Object)tr, (Object)sr));
                        }
                        final ReduceValuesTask<K, V> reduceValuesTask = t;
                        nextRight = s.nextRight;
                        reduceValuesTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class ReduceEntriesTask<K, V> extends Traverser<K, V, Map.Entry<K, V>>
    {
        final BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
        Map.Entry<K, V> result;
        ReduceEntriesTask<K, V> rights;
        ReduceEntriesTask<K, V> nextRight;
        
        ReduceEntriesTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final ReduceEntriesTask<K, V> nextRight, final BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }
        
        @Override
        public final Map.Entry<K, V> getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
            if ((reducer = this.reducer) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new ReduceEntriesTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, reducer)).fork();
                }
                Map.Entry<K, V> r = null;
                V v;
                while ((v = this.advance()) != null) {
                    final Map.Entry<K, V> u = (Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v);
                    r = ((r == null) ? u : reducer.apply(r, u));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final ReduceEntriesTask<K, V> t = (ReduceEntriesTask)c;
                    ReduceEntriesTask<K, V> nextRight;
                    for (ReduceEntriesTask<K, V> s = t.rights; s != null; s = nextRight) {
                        final Map.Entry<K, V> sr;
                        if ((sr = s.result) != null) {
                            final Map.Entry<K, V> tr;
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        final ReduceEntriesTask<K, V> reduceEntriesTask = t;
                        nextRight = s.nextRight;
                        reduceEntriesTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceKeysTask<K, V, U> extends Traverser<K, V, U>
    {
        final Fun<? super K, ? extends U> transformer;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceKeysTask<K, V, U> rights;
        MapReduceKeysTask<K, V, U> nextRight;
        
        MapReduceKeysTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceKeysTask<K, V, U> nextRight, final Fun<? super K, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final U getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final Fun<? super K, ? extends U> transformer;
            final BiFun<? super U, ? super U, ? extends U> reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceKeysTask<K, V, U>(this.map, this, b, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                while (this.advance() != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Object)this.nextKey)) != null) {
                        r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
                    }
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceKeysTask<K, V, U> t = (MapReduceKeysTask)c;
                    MapReduceKeysTask<K, V, U> nextRight;
                    for (MapReduceKeysTask<K, V, U> s = t.rights; s != null; s = nextRight) {
                        final U sr;
                        if ((sr = s.result) != null) {
                            final U tr;
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply((Object)tr, (Object)sr));
                        }
                        final MapReduceKeysTask<K, V, U> mapReduceKeysTask = t;
                        nextRight = s.nextRight;
                        mapReduceKeysTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceValuesTask<K, V, U> extends Traverser<K, V, U>
    {
        final Fun<? super V, ? extends U> transformer;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceValuesTask<K, V, U> rights;
        MapReduceValuesTask<K, V, U> nextRight;
        
        MapReduceValuesTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceValuesTask<K, V, U> nextRight, final Fun<? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final U getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final Fun<? super V, ? extends U> transformer;
            final BiFun<? super U, ? super U, ? extends U> reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceValuesTask<K, V, U>(this.map, this, b, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                V v;
                while ((v = this.advance()) != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Object)v)) != null) {
                        r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
                    }
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceValuesTask<K, V, U> t = (MapReduceValuesTask)c;
                    MapReduceValuesTask<K, V, U> nextRight;
                    for (MapReduceValuesTask<K, V, U> s = t.rights; s != null; s = nextRight) {
                        final U sr;
                        if ((sr = s.result) != null) {
                            final U tr;
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply((Object)tr, (Object)sr));
                        }
                        final MapReduceValuesTask<K, V, U> mapReduceValuesTask = t;
                        nextRight = s.nextRight;
                        mapReduceValuesTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceEntriesTask<K, V, U> extends Traverser<K, V, U>
    {
        final Fun<Map.Entry<K, V>, ? extends U> transformer;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceEntriesTask<K, V, U> rights;
        MapReduceEntriesTask<K, V, U> nextRight;
        
        MapReduceEntriesTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceEntriesTask<K, V, U> nextRight, final Fun<Map.Entry<K, V>, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final U getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final Fun<Map.Entry<K, V>, ? extends U> transformer;
            final BiFun<? super U, ? super U, ? extends U> reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceEntriesTask<K, V, U>(this.map, this, b, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                V v;
                while ((v = this.advance()) != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v))) != null) {
                        r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
                    }
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceEntriesTask<K, V, U> t = (MapReduceEntriesTask)c;
                    MapReduceEntriesTask<K, V, U> nextRight;
                    for (MapReduceEntriesTask<K, V, U> s = t.rights; s != null; s = nextRight) {
                        final U sr;
                        if ((sr = s.result) != null) {
                            final U tr;
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply((Object)tr, (Object)sr));
                        }
                        final MapReduceEntriesTask<K, V, U> mapReduceEntriesTask = t;
                        nextRight = s.nextRight;
                        mapReduceEntriesTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceMappingsTask<K, V, U> extends Traverser<K, V, U>
    {
        final BiFun<? super K, ? super V, ? extends U> transformer;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceMappingsTask<K, V, U> rights;
        MapReduceMappingsTask<K, V, U> nextRight;
        
        MapReduceMappingsTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceMappingsTask<K, V, U> nextRight, final BiFun<? super K, ? super V, ? extends U> transformer, final BiFun<? super U, ? super U, ? extends U> reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final U getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun<? super K, ? super V, ? extends U> transformer;
            final BiFun<? super U, ? super U, ? extends U> reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceMappingsTask<K, V, U>(this.map, this, b, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                V v;
                while ((v = this.advance()) != null) {
                    final U u;
                    if ((u = (U)transformer.apply((Object)this.nextKey, (Object)v)) != null) {
                        r = ((r == null) ? u : reducer.apply((Object)r, (Object)u));
                    }
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceMappingsTask<K, V, U> t = (MapReduceMappingsTask)c;
                    MapReduceMappingsTask<K, V, U> nextRight;
                    for (MapReduceMappingsTask<K, V, U> s = t.rights; s != null; s = nextRight) {
                        final U sr;
                        if ((sr = s.result) != null) {
                            final U tr;
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply((Object)tr, (Object)sr));
                        }
                        final MapReduceMappingsTask<K, V, U> mapReduceMappingsTask = t;
                        nextRight = s.nextRight;
                        mapReduceMappingsTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceKeysToDoubleTask<K, V> extends Traverser<K, V, Double>
    {
        final ObjectToDouble<? super K> transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceKeysToDoubleTask<K, V> rights;
        MapReduceKeysToDoubleTask<K, V> nextRight;
        
        MapReduceKeysToDoubleTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceKeysToDoubleTask<K, V> nextRight, final ObjectToDouble<? super K> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToDouble<? super K> transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceKeysToDoubleTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                while (this.advance() != null) {
                    r = reducer.apply(r, transformer.apply((Object)this.nextKey));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceKeysToDoubleTask<K, V> t = (MapReduceKeysToDoubleTask)c;
                    MapReduceKeysToDoubleTask<K, V> nextRight;
                    for (MapReduceKeysToDoubleTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceKeysToDoubleTask<K, V> mapReduceKeysToDoubleTask = t;
                        nextRight = s.nextRight;
                        mapReduceKeysToDoubleTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceValuesToDoubleTask<K, V> extends Traverser<K, V, Double>
    {
        final ObjectToDouble<? super V> transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceValuesToDoubleTask<K, V> rights;
        MapReduceValuesToDoubleTask<K, V> nextRight;
        
        MapReduceValuesToDoubleTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceValuesToDoubleTask<K, V> nextRight, final ObjectToDouble<? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToDouble<? super V> transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceValuesToDoubleTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Object)v));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceValuesToDoubleTask<K, V> t = (MapReduceValuesToDoubleTask)c;
                    MapReduceValuesToDoubleTask<K, V> nextRight;
                    for (MapReduceValuesToDoubleTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceValuesToDoubleTask<K, V> mapReduceValuesToDoubleTask = t;
                        nextRight = s.nextRight;
                        mapReduceValuesToDoubleTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceEntriesToDoubleTask<K, V> extends Traverser<K, V, Double>
    {
        final ObjectToDouble<Map.Entry<K, V>> transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceEntriesToDoubleTask<K, V> rights;
        MapReduceEntriesToDoubleTask<K, V> nextRight;
        
        MapReduceEntriesToDoubleTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceEntriesToDoubleTask<K, V> nextRight, final ObjectToDouble<Map.Entry<K, V>> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToDouble<Map.Entry<K, V>> transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceEntriesToDoubleTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v)));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceEntriesToDoubleTask<K, V> t = (MapReduceEntriesToDoubleTask)c;
                    MapReduceEntriesToDoubleTask<K, V> nextRight;
                    for (MapReduceEntriesToDoubleTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceEntriesToDoubleTask<K, V> mapReduceEntriesToDoubleTask = t;
                        nextRight = s.nextRight;
                        mapReduceEntriesToDoubleTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceMappingsToDoubleTask<K, V> extends Traverser<K, V, Double>
    {
        final ObjectByObjectToDouble<? super K, ? super V> transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceMappingsToDoubleTask<K, V> rights;
        MapReduceMappingsToDoubleTask<K, V> nextRight;
        
        MapReduceMappingsToDoubleTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceMappingsToDoubleTask<K, V> nextRight, final ObjectByObjectToDouble<? super K, ? super V> transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectByObjectToDouble<? super K, ? super V> transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceMappingsToDoubleTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Object)this.nextKey, (Object)v));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceMappingsToDoubleTask<K, V> t = (MapReduceMappingsToDoubleTask)c;
                    MapReduceMappingsToDoubleTask<K, V> nextRight;
                    for (MapReduceMappingsToDoubleTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceMappingsToDoubleTask<K, V> mapReduceMappingsToDoubleTask = t;
                        nextRight = s.nextRight;
                        mapReduceMappingsToDoubleTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceKeysToLongTask<K, V> extends Traverser<K, V, Long>
    {
        final ObjectToLong<? super K> transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceKeysToLongTask<K, V> rights;
        MapReduceKeysToLongTask<K, V> nextRight;
        
        MapReduceKeysToLongTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceKeysToLongTask<K, V> nextRight, final ObjectToLong<? super K> transformer, final long basis, final LongByLongToLong reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToLong<? super K> transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceKeysToLongTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                while (this.advance() != null) {
                    r = reducer.apply(r, transformer.apply((Object)this.nextKey));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceKeysToLongTask<K, V> t = (MapReduceKeysToLongTask)c;
                    MapReduceKeysToLongTask<K, V> nextRight;
                    for (MapReduceKeysToLongTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceKeysToLongTask<K, V> mapReduceKeysToLongTask = t;
                        nextRight = s.nextRight;
                        mapReduceKeysToLongTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceValuesToLongTask<K, V> extends Traverser<K, V, Long>
    {
        final ObjectToLong<? super V> transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceValuesToLongTask<K, V> rights;
        MapReduceValuesToLongTask<K, V> nextRight;
        
        MapReduceValuesToLongTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceValuesToLongTask<K, V> nextRight, final ObjectToLong<? super V> transformer, final long basis, final LongByLongToLong reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToLong<? super V> transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceValuesToLongTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Object)v));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceValuesToLongTask<K, V> t = (MapReduceValuesToLongTask)c;
                    MapReduceValuesToLongTask<K, V> nextRight;
                    for (MapReduceValuesToLongTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceValuesToLongTask<K, V> mapReduceValuesToLongTask = t;
                        nextRight = s.nextRight;
                        mapReduceValuesToLongTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceEntriesToLongTask<K, V> extends Traverser<K, V, Long>
    {
        final ObjectToLong<Map.Entry<K, V>> transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceEntriesToLongTask<K, V> rights;
        MapReduceEntriesToLongTask<K, V> nextRight;
        
        MapReduceEntriesToLongTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceEntriesToLongTask<K, V> nextRight, final ObjectToLong<Map.Entry<K, V>> transformer, final long basis, final LongByLongToLong reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToLong<Map.Entry<K, V>> transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceEntriesToLongTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v)));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceEntriesToLongTask<K, V> t = (MapReduceEntriesToLongTask)c;
                    MapReduceEntriesToLongTask<K, V> nextRight;
                    for (MapReduceEntriesToLongTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceEntriesToLongTask<K, V> mapReduceEntriesToLongTask = t;
                        nextRight = s.nextRight;
                        mapReduceEntriesToLongTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceMappingsToLongTask<K, V> extends Traverser<K, V, Long>
    {
        final ObjectByObjectToLong<? super K, ? super V> transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceMappingsToLongTask<K, V> rights;
        MapReduceMappingsToLongTask<K, V> nextRight;
        
        MapReduceMappingsToLongTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceMappingsToLongTask<K, V> nextRight, final ObjectByObjectToLong<? super K, ? super V> transformer, final long basis, final LongByLongToLong reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectByObjectToLong<? super K, ? super V> transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceMappingsToLongTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Object)this.nextKey, (Object)v));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceMappingsToLongTask<K, V> t = (MapReduceMappingsToLongTask)c;
                    MapReduceMappingsToLongTask<K, V> nextRight;
                    for (MapReduceMappingsToLongTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceMappingsToLongTask<K, V> mapReduceMappingsToLongTask = t;
                        nextRight = s.nextRight;
                        mapReduceMappingsToLongTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceKeysToIntTask<K, V> extends Traverser<K, V, Integer>
    {
        final ObjectToInt<? super K> transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceKeysToIntTask<K, V> rights;
        MapReduceKeysToIntTask<K, V> nextRight;
        
        MapReduceKeysToIntTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceKeysToIntTask<K, V> nextRight, final ObjectToInt<? super K> transformer, final int basis, final IntByIntToInt reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToInt<? super K> transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceKeysToIntTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                while (this.advance() != null) {
                    r = reducer.apply(r, transformer.apply((Object)this.nextKey));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceKeysToIntTask<K, V> t = (MapReduceKeysToIntTask)c;
                    MapReduceKeysToIntTask<K, V> nextRight;
                    for (MapReduceKeysToIntTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceKeysToIntTask<K, V> mapReduceKeysToIntTask = t;
                        nextRight = s.nextRight;
                        mapReduceKeysToIntTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceValuesToIntTask<K, V> extends Traverser<K, V, Integer>
    {
        final ObjectToInt<? super V> transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceValuesToIntTask<K, V> rights;
        MapReduceValuesToIntTask<K, V> nextRight;
        
        MapReduceValuesToIntTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceValuesToIntTask<K, V> nextRight, final ObjectToInt<? super V> transformer, final int basis, final IntByIntToInt reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToInt<? super V> transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceValuesToIntTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Object)v));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceValuesToIntTask<K, V> t = (MapReduceValuesToIntTask)c;
                    MapReduceValuesToIntTask<K, V> nextRight;
                    for (MapReduceValuesToIntTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceValuesToIntTask<K, V> mapReduceValuesToIntTask = t;
                        nextRight = s.nextRight;
                        mapReduceValuesToIntTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceEntriesToIntTask<K, V> extends Traverser<K, V, Integer>
    {
        final ObjectToInt<Map.Entry<K, V>> transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceEntriesToIntTask<K, V> rights;
        MapReduceEntriesToIntTask<K, V> nextRight;
        
        MapReduceEntriesToIntTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceEntriesToIntTask<K, V> nextRight, final ObjectToInt<Map.Entry<K, V>> transformer, final int basis, final IntByIntToInt reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToInt<Map.Entry<K, V>> transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceEntriesToIntTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Map.Entry<K, V>)ConcurrentHashMapV8.entryFor(this.nextKey, v)));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceEntriesToIntTask<K, V> t = (MapReduceEntriesToIntTask)c;
                    MapReduceEntriesToIntTask<K, V> nextRight;
                    for (MapReduceEntriesToIntTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceEntriesToIntTask<K, V> mapReduceEntriesToIntTask = t;
                        nextRight = s.nextRight;
                        mapReduceEntriesToIntTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceMappingsToIntTask<K, V> extends Traverser<K, V, Integer>
    {
        final ObjectByObjectToInt<? super K, ? super V> transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceMappingsToIntTask<K, V> rights;
        MapReduceMappingsToIntTask<K, V> nextRight;
        
        MapReduceMappingsToIntTask(final ConcurrentHashMapV8<K, V> m, final Traverser<K, V, ?> p, final int b, final MapReduceMappingsToIntTask<K, V> nextRight, final ObjectByObjectToInt<? super K, ? super V> transformer, final int basis, final IntByIntToInt reducer) {
            super(m, p, b);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectByObjectToInt<? super K, ? super V> transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int r = this.basis;
                int b;
                while ((b = this.preSplit()) > 0) {
                    (this.rights = new MapReduceMappingsToIntTask<K, V>((ConcurrentHashMapV8<K, V>)this.map, this, b, this.rights, transformer, r, reducer)).fork();
                }
                V v;
                while ((v = this.advance()) != null) {
                    r = reducer.apply(r, transformer.apply((Object)this.nextKey, (Object)v));
                }
                this.result = r;
                for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                    final MapReduceMappingsToIntTask<K, V> t = (MapReduceMappingsToIntTask)c;
                    MapReduceMappingsToIntTask<K, V> nextRight;
                    for (MapReduceMappingsToIntTask<K, V> s = t.rights; s != null; s = nextRight) {
                        t.result = reducer.apply(t.result, s.result);
                        final MapReduceMappingsToIntTask<K, V> mapReduceMappingsToIntTask = t;
                        nextRight = s.nextRight;
                        mapReduceMappingsToIntTask.rights = nextRight;
                    }
                }
            }
        }
    }
    
    public interface ObjectByObjectToInt<A, B>
    {
        int apply(final A p0, final B p1);
    }
    
    public interface IntByIntToInt
    {
        int apply(final int p0, final int p1);
    }
    
    public interface ObjectToInt<A>
    {
        int apply(final A p0);
    }
    
    public interface ObjectByObjectToLong<A, B>
    {
        long apply(final A p0, final B p1);
    }
    
    public interface LongByLongToLong
    {
        long apply(final long p0, final long p1);
    }
    
    public interface ObjectToLong<A>
    {
        long apply(final A p0);
    }
    
    public interface ObjectByObjectToDouble<A, B>
    {
        double apply(final A p0, final B p1);
    }
    
    public interface DoubleByDoubleToDouble
    {
        double apply(final double p0, final double p1);
    }
    
    public interface ObjectToDouble<A>
    {
        double apply(final A p0);
    }
    
    public interface BiFun<A, B, T>
    {
        T apply(final A p0, final B p1);
    }
    
    public interface Fun<A, T>
    {
        T apply(final A p0);
    }
    
    public interface Action<A>
    {
        void apply(final A p0);
    }
    
    public interface BiAction<A, B>
    {
        void apply(final A p0, final B p1);
    }
    
    public interface Spliterator<T> extends Iterator<T>
    {
        Spliterator<T> split();
    }
    
    public interface IntToInt
    {
        int apply(final int p0);
    }
    
    public interface LongToLong
    {
        long apply(final long p0);
    }
    
    public interface DoubleToDouble
    {
        double apply(final double p0);
    }
    
    public interface Generator<T>
    {
        T apply();
    }
}
