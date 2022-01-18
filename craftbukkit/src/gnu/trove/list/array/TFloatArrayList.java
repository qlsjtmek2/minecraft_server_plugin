// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.array;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.impl.HashFunctions;
import java.util.Random;
import gnu.trove.function.TFloatFunction;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TFloatIterator;
import java.util.Arrays;
import gnu.trove.TFloatCollection;
import java.io.Externalizable;
import gnu.trove.list.TFloatList;

public class TFloatArrayList implements TFloatList, Externalizable
{
    static final long serialVersionUID = 1L;
    protected float[] _data;
    protected int _pos;
    protected static final int DEFAULT_CAPACITY = 10;
    protected float no_entry_value;
    
    public TFloatArrayList() {
        this(10, 0.0f);
    }
    
    public TFloatArrayList(final int capacity) {
        this(capacity, 0.0f);
    }
    
    public TFloatArrayList(final int capacity, final float no_entry_value) {
        this._data = new float[capacity];
        this._pos = 0;
        this.no_entry_value = no_entry_value;
    }
    
    public TFloatArrayList(final TFloatCollection collection) {
        this(collection.size());
        this.addAll(collection);
    }
    
    public TFloatArrayList(final float[] values) {
        this(values.length);
        this.add(values);
    }
    
    protected TFloatArrayList(final float[] values, final float no_entry_value, final boolean wrap) {
        if (!wrap) {
            throw new IllegalStateException("Wrong call");
        }
        if (values == null) {
            throw new IllegalArgumentException("values can not be null");
        }
        this._data = values;
        this._pos = values.length;
        this.no_entry_value = no_entry_value;
    }
    
    public static TFloatArrayList wrap(final float[] values) {
        return wrap(values, 0.0f);
    }
    
    public static TFloatArrayList wrap(final float[] values, final float no_entry_value) {
        return new TFloatArrayList(values, no_entry_value, true) {
            public void ensureCapacity(final int capacity) {
                if (capacity > this._data.length) {
                    throw new IllegalStateException("Can not grow ArrayList wrapped external array");
                }
            }
        };
    }
    
    public float getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public void ensureCapacity(final int capacity) {
        if (capacity > this._data.length) {
            final int newCap = Math.max(this._data.length << 1, capacity);
            final float[] tmp = new float[newCap];
            System.arraycopy(this._data, 0, tmp, 0, this._data.length);
            this._data = tmp;
        }
    }
    
    public int size() {
        return this._pos;
    }
    
    public boolean isEmpty() {
        return this._pos == 0;
    }
    
    public void trimToSize() {
        if (this._data.length > this.size()) {
            final float[] tmp = new float[this.size()];
            this.toArray(tmp, 0, tmp.length);
            this._data = tmp;
        }
    }
    
    public boolean add(final float val) {
        this.ensureCapacity(this._pos + 1);
        this._data[this._pos++] = val;
        return true;
    }
    
    public void add(final float[] vals) {
        this.add(vals, 0, vals.length);
    }
    
    public void add(final float[] vals, final int offset, final int length) {
        this.ensureCapacity(this._pos + length);
        System.arraycopy(vals, offset, this._data, this._pos, length);
        this._pos += length;
    }
    
    public void insert(final int offset, final float value) {
        if (offset == this._pos) {
            this.add(value);
            return;
        }
        this.ensureCapacity(this._pos + 1);
        System.arraycopy(this._data, offset, this._data, offset + 1, this._pos - offset);
        this._data[offset] = value;
        ++this._pos;
    }
    
    public void insert(final int offset, final float[] values) {
        this.insert(offset, values, 0, values.length);
    }
    
    public void insert(final int offset, final float[] values, final int valOffset, final int len) {
        if (offset == this._pos) {
            this.add(values, valOffset, len);
            return;
        }
        this.ensureCapacity(this._pos + len);
        System.arraycopy(this._data, offset, this._data, offset + len, this._pos - offset);
        System.arraycopy(values, valOffset, this._data, offset, len);
        this._pos += len;
    }
    
    public float get(final int offset) {
        if (offset >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        return this._data[offset];
    }
    
    public float getQuick(final int offset) {
        return this._data[offset];
    }
    
    public float set(final int offset, final float val) {
        if (offset >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        final float prev_val = this._data[offset];
        this._data[offset] = val;
        return prev_val;
    }
    
    public float replace(final int offset, final float val) {
        if (offset >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        final float old = this._data[offset];
        this._data[offset] = val;
        return old;
    }
    
    public void set(final int offset, final float[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final float[] values, final int valOffset, final int length) {
        if (offset < 0 || offset + length > this._pos) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        System.arraycopy(values, valOffset, this._data, offset, length);
    }
    
    public void setQuick(final int offset, final float val) {
        this._data[offset] = val;
    }
    
    public void clear() {
        this.clear(10);
    }
    
    public void clear(final int capacity) {
        this._data = new float[capacity];
        this._pos = 0;
    }
    
    public void reset() {
        this._pos = 0;
        Arrays.fill(this._data, this.no_entry_value);
    }
    
    public void resetQuick() {
        this._pos = 0;
    }
    
    public boolean remove(final float value) {
        for (int index = 0; index < this._pos; ++index) {
            if (value == this._data[index]) {
                this.remove(index, 1);
                return true;
            }
        }
        return false;
    }
    
    public float removeAt(final int offset) {
        final float old = this.get(offset);
        this.remove(offset, 1);
        return old;
    }
    
    public void remove(final int offset, final int length) {
        if (length == 0) {
            return;
        }
        if (offset < 0 || offset >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        if (offset == 0) {
            System.arraycopy(this._data, length, this._data, 0, this._pos - length);
        }
        else if (this._pos - length != offset) {
            System.arraycopy(this._data, offset + length, this._data, offset, this._pos - (offset + length));
        }
        this._pos -= length;
    }
    
    public TFloatIterator iterator() {
        return new TFloatArrayIterator(0);
    }
    
    public boolean containsAll(final Collection<?> collection) {
        for (final Object element : collection) {
            if (!(element instanceof Float)) {
                return false;
            }
            final float c = (float)element;
            if (!this.contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TFloatCollection collection) {
        if (this == collection) {
            return true;
        }
        for (final float element : collection) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final float[] array) {
        int i = array.length;
        while (i-- > 0) {
            if (!this.contains(array[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Float> collection) {
        boolean changed = false;
        for (final Float element : collection) {
            final float e = element;
            if (this.add(e)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean addAll(final TFloatCollection collection) {
        boolean changed = false;
        for (final float element : collection) {
            if (this.add(element)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean addAll(final float[] array) {
        boolean changed = false;
        for (final float element : array) {
            if (this.add(element)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean modified = false;
        final TFloatIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final TFloatCollection collection) {
        if (this == collection) {
            return false;
        }
        boolean modified = false;
        final TFloatIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final float[] array) {
        boolean changed = false;
        Arrays.sort(array);
        final float[] data = this._data;
        int i = data.length;
        while (i-- > 0) {
            if (Arrays.binarySearch(array, data[i]) < 0) {
                this.remove(i, 1);
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean removeAll(final Collection<?> collection) {
        boolean changed = false;
        for (final Object element : collection) {
            if (element instanceof Float) {
                final float c = (float)element;
                if (!this.remove(c)) {
                    continue;
                }
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean removeAll(final TFloatCollection collection) {
        if (collection == this) {
            this.clear();
            return true;
        }
        boolean changed = false;
        for (final float element : collection) {
            if (this.remove(element)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean removeAll(final float[] array) {
        boolean changed = false;
        int i = array.length;
        while (i-- > 0) {
            if (this.remove(array[i])) {
                changed = true;
            }
        }
        return changed;
    }
    
    public void transformValues(final TFloatFunction function) {
        int i = this._pos;
        while (i-- > 0) {
            this._data[i] = function.execute(this._data[i]);
        }
    }
    
    public void reverse() {
        this.reverse(0, this._pos);
    }
    
    public void reverse(final int from, final int to) {
        if (from == to) {
            return;
        }
        if (from > to) {
            throw new IllegalArgumentException("from cannot be greater than to");
        }
        for (int i = from, j = to - 1; i < j; ++i, --j) {
            this.swap(i, j);
        }
    }
    
    public void shuffle(final Random rand) {
        int i = this._pos;
        while (i-- > 1) {
            this.swap(i, rand.nextInt(i));
        }
    }
    
    private void swap(final int i, final int j) {
        final float tmp = this._data[i];
        this._data[i] = this._data[j];
        this._data[j] = tmp;
    }
    
    public TFloatList subList(final int begin, final int end) {
        if (end < begin) {
            throw new IllegalArgumentException("end index " + end + " greater than begin index " + begin);
        }
        if (begin < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (end > this._data.length) {
            throw new IndexOutOfBoundsException("end index < " + this._data.length);
        }
        final TFloatArrayList list = new TFloatArrayList(end - begin);
        for (int i = begin; i < end; ++i) {
            list.add(this._data[i]);
        }
        return list;
    }
    
    public float[] toArray() {
        return this.toArray(0, this._pos);
    }
    
    public float[] toArray(final int offset, final int len) {
        final float[] rv = new float[len];
        this.toArray(rv, offset, len);
        return rv;
    }
    
    public float[] toArray(final float[] dest) {
        int len = dest.length;
        if (dest.length > this._pos) {
            len = this._pos;
            dest[len] = this.no_entry_value;
        }
        this.toArray(dest, 0, len);
        return dest;
    }
    
    public float[] toArray(final float[] dest, final int offset, final int len) {
        if (len == 0) {
            return dest;
        }
        if (offset < 0 || offset >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        System.arraycopy(this._data, offset, dest, 0, len);
        return dest;
    }
    
    public float[] toArray(final float[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        System.arraycopy(this._data, source_pos, dest, dest_pos, len);
        return dest;
    }
    
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TFloatArrayList)) {
            return false;
        }
        final TFloatArrayList that = (TFloatArrayList)other;
        if (that.size() != this.size()) {
            return false;
        }
        int i = this._pos;
        while (i-- > 0) {
            if (this._data[i] != that._data[i]) {
                return false;
            }
        }
        return true;
    }
    
    public int hashCode() {
        int h = 0;
        int i = this._pos;
        while (i-- > 0) {
            h += HashFunctions.hash(this._data[i]);
        }
        return h;
    }
    
    public boolean forEach(final TFloatProcedure procedure) {
        for (int i = 0; i < this._pos; ++i) {
            if (!procedure.execute(this._data[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TFloatProcedure procedure) {
        int i = this._pos;
        while (i-- > 0) {
            if (!procedure.execute(this._data[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void sort() {
        Arrays.sort(this._data, 0, this._pos);
    }
    
    public void sort(final int fromIndex, final int toIndex) {
        Arrays.sort(this._data, fromIndex, toIndex);
    }
    
    public void fill(final float val) {
        Arrays.fill(this._data, 0, this._pos, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final float val) {
        if (toIndex > this._pos) {
            this.ensureCapacity(toIndex);
            this._pos = toIndex;
        }
        Arrays.fill(this._data, fromIndex, toIndex, val);
    }
    
    public int binarySearch(final float value) {
        return this.binarySearch(value, 0, this._pos);
    }
    
    public int binarySearch(final float value, final int fromIndex, final int toIndex) {
        if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException(fromIndex);
        }
        if (toIndex > this._pos) {
            throw new ArrayIndexOutOfBoundsException(toIndex);
        }
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            final int mid = low + high >>> 1;
            final float midVal = this._data[mid];
            if (midVal < value) {
                low = mid + 1;
            }
            else {
                if (midVal <= value) {
                    return mid;
                }
                high = mid - 1;
            }
        }
        return -(low + 1);
    }
    
    public int indexOf(final float value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final float value) {
        for (int i = offset; i < this._pos; ++i) {
            if (this._data[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final float value) {
        return this.lastIndexOf(this._pos, value);
    }
    
    public int lastIndexOf(final int offset, final float value) {
        int i = offset;
        while (i-- > 0) {
            if (this._data[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean contains(final float value) {
        return this.lastIndexOf(value) >= 0;
    }
    
    public TFloatList grep(final TFloatProcedure condition) {
        final TFloatArrayList list = new TFloatArrayList();
        for (int i = 0; i < this._pos; ++i) {
            if (condition.execute(this._data[i])) {
                list.add(this._data[i]);
            }
        }
        return list;
    }
    
    public TFloatList inverseGrep(final TFloatProcedure condition) {
        final TFloatArrayList list = new TFloatArrayList();
        for (int i = 0; i < this._pos; ++i) {
            if (!condition.execute(this._data[i])) {
                list.add(this._data[i]);
            }
        }
        return list;
    }
    
    public float max() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        float max = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < this._pos; ++i) {
            if (this._data[i] > max) {
                max = this._data[i];
            }
        }
        return max;
    }
    
    public float min() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        float min = Float.POSITIVE_INFINITY;
        for (int i = 0; i < this._pos; ++i) {
            if (this._data[i] < min) {
                min = this._data[i];
            }
        }
        return min;
    }
    
    public float sum() {
        float sum = 0.0f;
        for (int i = 0; i < this._pos; ++i) {
            sum += this._data[i];
        }
        return sum;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        for (int i = 0, end = this._pos - 1; i < end; ++i) {
            buf.append(this._data[i]);
            buf.append(", ");
        }
        if (this.size() > 0) {
            buf.append(this._data[this._pos - 1]);
        }
        buf.append("}");
        return buf.toString();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeInt(this._pos);
        out.writeFloat(this.no_entry_value);
        final int len = this._data.length;
        out.writeInt(len);
        for (int i = 0; i < len; ++i) {
            out.writeFloat(this._data[i]);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._pos = in.readInt();
        this.no_entry_value = in.readFloat();
        final int len = in.readInt();
        this._data = new float[len];
        for (int i = 0; i < len; ++i) {
            this._data[i] = in.readFloat();
        }
    }
    
    class TFloatArrayIterator implements TFloatIterator
    {
        private int cursor;
        int lastRet;
        
        TFloatArrayIterator(final int index) {
            this.cursor = 0;
            this.lastRet = -1;
            this.cursor = index;
        }
        
        public boolean hasNext() {
            return this.cursor < TFloatArrayList.this.size();
        }
        
        public float next() {
            try {
                final float next = TFloatArrayList.this.get(this.cursor);
                this.lastRet = this.cursor++;
                return next;
            }
            catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }
        
        public void remove() {
            if (this.lastRet == -1) {
                throw new IllegalStateException();
            }
            try {
                TFloatArrayList.this.remove(this.lastRet, 1);
                if (this.lastRet < this.cursor) {
                    --this.cursor;
                }
                this.lastRet = -1;
            }
            catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
