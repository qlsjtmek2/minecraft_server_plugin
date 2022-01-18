// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.impl.hash;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.HashSet;
import java.util.Set;
import think.rpgitems.lib.gnu.trove.procedure.TObjectProcedure;
import java.util.Arrays;

public abstract class TObjectHash<T> extends THash
{
    static final long serialVersionUID = -3461112548087185871L;
    public transient Object[] _set;
    public static final Object REMOVED;
    public static final Object FREE;
    protected boolean consumeFreeSlot;
    
    public TObjectHash() {
    }
    
    public TObjectHash(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TObjectHash(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public int capacity() {
        return this._set.length;
    }
    
    protected void removeAt(final int index) {
        this._set[index] = TObjectHash.REMOVED;
        super.removeAt(index);
    }
    
    public int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        Arrays.fill(this._set = new Object[capacity], TObjectHash.FREE);
        return capacity;
    }
    
    public boolean forEach(final TObjectProcedure<? super T> procedure) {
        final Object[] set = this._set;
        int i = set.length;
        while (i-- > 0) {
            if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && !procedure.execute((Object)set[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean contains(final Object obj) {
        return this.index(obj) >= 0;
    }
    
    protected int index(final Object obj) {
        if (obj == null) {
            return this.indexForNull();
        }
        final int hash = this.hash(obj) & Integer.MAX_VALUE;
        final int index = hash % this._set.length;
        final Object cur = this._set[index];
        if (cur == TObjectHash.FREE) {
            return -1;
        }
        if (cur == obj || this.equals(obj, cur)) {
            return index;
        }
        return this.indexRehashed(obj, index, hash, cur);
    }
    
    private int indexRehashed(final Object obj, int index, final int hash, Object cur) {
        final Object[] set = this._set;
        final int length = set.length;
        final int probe = 1 + hash % (length - 2);
        final int loopIndex = index;
        do {
            index -= probe;
            if (index < 0) {
                index += length;
            }
            cur = set[index];
            if (cur == TObjectHash.FREE) {
                return -1;
            }
            if (cur == obj || this.equals(obj, cur)) {
                return index;
            }
        } while (index != loopIndex);
        return -1;
    }
    
    private int indexForNull() {
        int index = 0;
        for (final Object o : this._set) {
            if (o == null) {
                return index;
            }
            if (o == TObjectHash.FREE) {
                return -1;
            }
            ++index;
        }
        return -1;
    }
    
    @Deprecated
    protected int insertionIndex(final T obj) {
        return this.insertKey(obj);
    }
    
    protected int insertKey(final T key) {
        this.consumeFreeSlot = false;
        if (key == null) {
            return this.insertKeyForNull();
        }
        final int hash = this.hash(key) & Integer.MAX_VALUE;
        final int index = hash % this._set.length;
        final Object cur = this._set[index];
        if (cur == TObjectHash.FREE) {
            this.consumeFreeSlot = true;
            this._set[index] = key;
            return index;
        }
        if (cur == key || this.equals(key, cur)) {
            return -index - 1;
        }
        return this.insertKeyRehash(key, index, hash, cur);
    }
    
    private int insertKeyRehash(final T key, int index, final int hash, Object cur) {
        final Object[] set = this._set;
        final int length = set.length;
        final int probe = 1 + hash % (length - 2);
        final int loopIndex = index;
        int firstRemoved = -1;
        do {
            if (cur == TObjectHash.REMOVED && firstRemoved == -1) {
                firstRemoved = index;
            }
            index -= probe;
            if (index < 0) {
                index += length;
            }
            cur = set[index];
            if (cur == TObjectHash.FREE) {
                if (firstRemoved != -1) {
                    this._set[firstRemoved] = key;
                    return firstRemoved;
                }
                this.consumeFreeSlot = true;
                this._set[index] = key;
                return index;
            }
            else {
                if (cur == key || this.equals(key, cur)) {
                    return -index - 1;
                }
                continue;
            }
        } while (index != loopIndex);
        if (firstRemoved != -1) {
            this._set[firstRemoved] = key;
            return firstRemoved;
        }
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }
    
    private int insertKeyForNull() {
        int index = 0;
        int firstRemoved = -1;
        final Object[] arr$ = this._set;
        final int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            final Object o = arr$[i$];
            if (o == TObjectHash.REMOVED && firstRemoved == -1) {
                firstRemoved = index;
            }
            if (o == TObjectHash.FREE) {
                if (firstRemoved != -1) {
                    this._set[firstRemoved] = null;
                    return firstRemoved;
                }
                this.consumeFreeSlot = true;
                this._set[index] = null;
                return index;
            }
            else {
                if (o == null) {
                    return -index - 1;
                }
                ++index;
                ++i$;
            }
        }
        if (firstRemoved != -1) {
            this._set[firstRemoved] = null;
            return firstRemoved;
        }
        throw new IllegalStateException("Could not find insertion index for null key. Key set full!?!!");
    }
    
    protected final void throwObjectContractViolation(final Object o1, final Object o2) throws IllegalArgumentException {
        throw this.buildObjectContractViolation(o1, o2, "");
    }
    
    protected final void throwObjectContractViolation(final Object o1, final Object o2, final int size, final int oldSize, final Object[] oldKeys) throws IllegalArgumentException {
        final String extra = this.dumpExtraInfo(o1, o2, this.size(), oldSize, oldKeys);
        throw this.buildObjectContractViolation(o1, o2, extra);
    }
    
    protected final IllegalArgumentException buildObjectContractViolation(final Object o1, final Object o2, final String extra) {
        return new IllegalArgumentException("Equal objects must have equal hashcodes. During rehashing, Trove discovered that the following two objects claim to be equal (as in java.lang.Object.equals()) but their hashCodes (or those calculated by your TObjectHashingStrategy) are not equal.This violates the general contract of java.lang.Object.hashCode().  See bullet point two in that method's documentation. object #1 =" + objectInfo(o1) + "; object #2 =" + objectInfo(o2) + "\n" + extra);
    }
    
    protected boolean equals(final Object notnull, final Object two) {
        return two != null && two != TObjectHash.REMOVED && notnull.equals(two);
    }
    
    protected int hash(final Object notnull) {
        return notnull.hashCode();
    }
    
    protected static String reportPotentialConcurrentMod(final int newSize, final int oldSize) {
        if (newSize != oldSize) {
            return "[Warning] apparent concurrent modification of the key set. Size before and after rehash() do not match " + oldSize + " vs " + newSize;
        }
        return "";
    }
    
    protected String dumpExtraInfo(final Object newVal, final Object oldVal, final int currentSize, final int oldSize, final Object[] oldKeys) {
        final StringBuilder b = new StringBuilder();
        b.append(this.dumpKeyTypes(newVal, oldVal));
        b.append(reportPotentialConcurrentMod(currentSize, oldSize));
        b.append(detectKeyLoss(oldKeys, oldSize));
        if (newVal == oldVal) {
            b.append("Inserting same object twice, rehashing bug. Object= ").append(oldVal);
        }
        return b.toString();
    }
    
    private static String detectKeyLoss(final Object[] keys, final int oldSize) {
        final StringBuilder buf = new StringBuilder();
        final Set<Object> k = makeKeySet(keys);
        if (k.size() != oldSize) {
            buf.append("\nhashCode() and/or equals() have inconsistent implementation");
            buf.append("\nKey set lost entries, now got ").append(k.size()).append(" instead of ").append(oldSize);
            buf.append(". This can manifest itself as an apparent duplicate key.");
        }
        return buf.toString();
    }
    
    private static Set<Object> makeKeySet(final Object[] keys) {
        final Set<Object> types = new HashSet<Object>();
        for (final Object o : keys) {
            if (o != TObjectHash.FREE && o != TObjectHash.REMOVED) {
                types.add(o);
            }
        }
        return types;
    }
    
    private static String equalsSymmetryInfo(final Object a, final Object b) {
        final StringBuilder buf = new StringBuilder();
        if (a == b) {
            return "a == b";
        }
        if (a.getClass() != b.getClass()) {
            buf.append("Class of objects differ a=").append(a.getClass()).append(" vs b=").append(b.getClass());
            final boolean aEb = a.equals(b);
            final boolean bEa = b.equals(a);
            if (aEb != bEa) {
                buf.append("\nequals() of a or b object are asymmetric");
                buf.append("\na.equals(b) =").append(aEb);
                buf.append("\nb.equals(a) =").append(bEa);
            }
        }
        return buf.toString();
    }
    
    protected static String objectInfo(final Object o) {
        return ((o == null) ? "class null" : o.getClass()) + " id= " + System.identityHashCode(o) + " hashCode= " + ((o == null) ? 0 : o.hashCode()) + " toString= " + String.valueOf(o);
    }
    
    private String dumpKeyTypes(final Object newVal, final Object oldVal) {
        final StringBuilder buf = new StringBuilder();
        final Set<Class<?>> types = new HashSet<Class<?>>();
        for (final Object o : this._set) {
            if (o != TObjectHash.FREE && o != TObjectHash.REMOVED) {
                if (o != null) {
                    types.add(o.getClass());
                }
                else {
                    types.add(null);
                }
            }
        }
        if (types.size() > 1) {
            buf.append("\nMore than one type used for keys. Watch out for asymmetric equals(). Read about the 'Liskov substitution principle' and the implications for equals() in java.");
            buf.append("\nKey types: ").append(types);
            buf.append(equalsSymmetryInfo(newVal, oldVal));
        }
        return buf.toString();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
    }
    
    static {
        REMOVED = new Object();
        FREE = new Object();
    }
}
