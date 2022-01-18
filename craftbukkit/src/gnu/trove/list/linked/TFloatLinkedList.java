// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.linked;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import gnu.trove.procedure.TFloatProcedure;
import java.util.Random;
import gnu.trove.function.TFloatFunction;
import java.util.Arrays;
import gnu.trove.TFloatCollection;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TFloatIterator;
import java.io.Externalizable;
import gnu.trove.list.TFloatList;

public class TFloatLinkedList implements TFloatList, Externalizable
{
    float no_entry_value;
    int size;
    TFloatLink head;
    TFloatLink tail;
    
    public TFloatLinkedList() {
        this.head = null;
        this.tail = this.head;
    }
    
    public TFloatLinkedList(final float no_entry_value) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = no_entry_value;
    }
    
    public TFloatLinkedList(final TFloatList list) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = list.getNoEntryValue();
        for (final float next : list) {
            this.add(next);
        }
    }
    
    public float getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean add(final float val) {
        final TFloatLink l = new TFloatLink(val);
        if (no(this.head)) {
            this.head = l;
            this.tail = l;
        }
        else {
            l.setPrevious(this.tail);
            this.tail.setNext(l);
            this.tail = l;
        }
        ++this.size;
        return true;
    }
    
    public void add(final float[] vals) {
        for (final float val : vals) {
            this.add(val);
        }
    }
    
    public void add(final float[] vals, final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            final float val = vals[offset + i];
            this.add(val);
        }
    }
    
    public void insert(final int offset, final float value) {
        final TFloatLinkedList tmp = new TFloatLinkedList();
        tmp.add(value);
        this.insert(offset, tmp);
    }
    
    public void insert(final int offset, final float[] values) {
        this.insert(offset, link(values, 0, values.length));
    }
    
    public void insert(final int offset, final float[] values, final int valOffset, final int len) {
        this.insert(offset, link(values, valOffset, len));
    }
    
    void insert(final int offset, final TFloatLinkedList tmp) {
        final TFloatLink l = this.getLinkAt(offset);
        this.size += tmp.size;
        if (l == this.head) {
            tmp.tail.setNext(this.head);
            this.head.setPrevious(tmp.tail);
            this.head = tmp.head;
            return;
        }
        if (no(l)) {
            if (this.size == 0) {
                this.head = tmp.head;
                this.tail = tmp.tail;
            }
            else {
                this.tail.setNext(tmp.head);
                tmp.head.setPrevious(this.tail);
                this.tail = tmp.tail;
            }
        }
        else {
            final TFloatLink prev = l.getPrevious();
            l.getPrevious().setNext(tmp.head);
            tmp.tail.setNext(l);
            l.setPrevious(tmp.tail);
            tmp.head.setPrevious(prev);
        }
    }
    
    static TFloatLinkedList link(final float[] values, final int valOffset, final int len) {
        final TFloatLinkedList ret = new TFloatLinkedList();
        for (int i = 0; i < len; ++i) {
            ret.add(values[valOffset + i]);
        }
        return ret;
    }
    
    public float get(final int offset) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TFloatLink l = this.getLinkAt(offset);
        if (no(l)) {
            return this.no_entry_value;
        }
        return l.getValue();
    }
    
    public TFloatLink getLinkAt(final int offset) {
        if (offset >= this.size()) {
            return null;
        }
        if (offset <= this.size() >>> 1) {
            return getLink(this.head, 0, offset, true);
        }
        return getLink(this.tail, this.size() - 1, offset, false);
    }
    
    private static TFloatLink getLink(final TFloatLink l, final int idx, final int offset) {
        return getLink(l, idx, offset, true);
    }
    
    private static TFloatLink getLink(TFloatLink l, final int idx, final int offset, final boolean next) {
        int i = idx;
        while (got(l)) {
            if (i == offset) {
                return l;
            }
            i += (next ? 1 : -1);
            l = (next ? l.getNext() : l.getPrevious());
        }
        return null;
    }
    
    public float set(final int offset, final float val) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TFloatLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new IndexOutOfBoundsException("at offset " + offset);
        }
        final float prev = l.getValue();
        l.setValue(val);
        return prev;
    }
    
    public void set(final int offset, final float[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final float[] values, final int valOffset, final int length) {
        for (int i = 0; i < length; ++i) {
            final float value = values[valOffset + i];
            this.set(offset + i, value);
        }
    }
    
    public float replace(final int offset, final float val) {
        return this.set(offset, val);
    }
    
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean remove(final float value) {
        boolean changed = false;
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                changed = true;
                this.removeLink(l);
            }
        }
        return changed;
    }
    
    private void removeLink(final TFloatLink l) {
        if (no(l)) {
            return;
        }
        --this.size;
        final TFloatLink prev = l.getPrevious();
        final TFloatLink next = l.getNext();
        if (got(prev)) {
            prev.setNext(next);
        }
        else {
            this.head = next;
        }
        if (got(next)) {
            next.setPrevious(prev);
        }
        else {
            this.tail = prev;
        }
        l.setNext(null);
        l.setPrevious(null);
    }
    
    public boolean containsAll(final Collection<?> collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final Object o : collection) {
            if (!(o instanceof Float)) {
                return false;
            }
            final Float i = (Float)o;
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TFloatCollection collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final float i : collection) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final float[] array) {
        if (this.isEmpty()) {
            return false;
        }
        for (final float i : array) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Float> collection) {
        boolean ret = false;
        for (final Float v : collection) {
            if (this.add(v)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final TFloatCollection collection) {
        boolean ret = false;
        for (final float i : collection) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final float[] array) {
        boolean ret = false;
        for (final float i : array) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
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
        Arrays.sort(array);
        boolean modified = false;
        final TFloatIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) < 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final Collection<?> collection) {
        boolean modified = false;
        final TFloatIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final TFloatCollection collection) {
        boolean modified = false;
        final TFloatIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final float[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TFloatIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) >= 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public float removeAt(final int offset) {
        final TFloatLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new ArrayIndexOutOfBoundsException("no elemenet at " + offset);
        }
        final float prev = l.getValue();
        this.removeLink(l);
        return prev;
    }
    
    public void remove(final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            this.removeAt(offset);
        }
    }
    
    public void transformValues(final TFloatFunction function) {
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            l.setValue(function.execute(l.getValue()));
        }
    }
    
    public void reverse() {
        final TFloatLink h = this.head;
        final TFloatLink t = this.tail;
        TFloatLink l = this.head;
        while (got(l)) {
            final TFloatLink next = l.getNext();
            final TFloatLink prev = l.getPrevious();
            final TFloatLink tmp = l;
            l = l.getNext();
            tmp.setNext(prev);
            tmp.setPrevious(next);
        }
        this.head = t;
        this.tail = h;
    }
    
    public void reverse(final int from, final int to) {
        if (from > to) {
            throw new IllegalArgumentException("from > to : " + from + ">" + to);
        }
        final TFloatLink start = this.getLinkAt(from);
        final TFloatLink stop = this.getLinkAt(to);
        TFloatLink tmp = null;
        final TFloatLink tmpHead = start.getPrevious();
        TFloatLink l = start;
        while (l != stop) {
            final TFloatLink next = l.getNext();
            final TFloatLink prev = l.getPrevious();
            tmp = l;
            l = l.getNext();
            tmp.setNext(prev);
            tmp.setPrevious(next);
        }
        if (got(tmp)) {
            tmpHead.setNext(tmp);
            stop.setPrevious(tmpHead);
        }
        start.setNext(stop);
        stop.setPrevious(start);
    }
    
    public void shuffle(final Random rand) {
        for (int i = 0; i < this.size; ++i) {
            final TFloatLink l = this.getLinkAt(rand.nextInt(this.size()));
            this.removeLink(l);
            this.add(l.getValue());
        }
    }
    
    public TFloatList subList(final int begin, final int end) {
        if (end < begin) {
            throw new IllegalArgumentException("begin index " + begin + " greater than end index " + end);
        }
        if (this.size < begin) {
            throw new IllegalArgumentException("begin index " + begin + " greater than last index " + this.size);
        }
        if (begin < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (end > this.size) {
            throw new IndexOutOfBoundsException("end index < " + this.size);
        }
        final TFloatLinkedList ret = new TFloatLinkedList();
        TFloatLink tmp = this.getLinkAt(begin);
        for (int i = begin; i < end; ++i) {
            ret.add(tmp.getValue());
            tmp = tmp.getNext();
        }
        return ret;
    }
    
    public float[] toArray() {
        return this.toArray(new float[this.size], 0, this.size);
    }
    
    public float[] toArray(final int offset, final int len) {
        return this.toArray(new float[len], offset, 0, len);
    }
    
    public float[] toArray(final float[] dest) {
        return this.toArray(dest, 0, this.size);
    }
    
    public float[] toArray(final float[] dest, final int offset, final int len) {
        return this.toArray(dest, offset, 0, len);
    }
    
    public float[] toArray(final float[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this.size()) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        TFloatLink tmp = this.getLinkAt(source_pos);
        for (int i = 0; i < len; ++i) {
            dest[dest_pos + i] = tmp.getValue();
            tmp = tmp.getNext();
        }
        return dest;
    }
    
    public boolean forEach(final TFloatProcedure procedure) {
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TFloatProcedure procedure) {
        for (TFloatLink l = this.tail; got(l); l = l.getPrevious()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public void sort() {
        this.sort(0, this.size);
    }
    
    public void sort(final int fromIndex, final int toIndex) {
        final TFloatList tmp = this.subList(fromIndex, toIndex);
        final float[] vals = tmp.toArray();
        Arrays.sort(vals);
        this.set(fromIndex, vals);
    }
    
    public void fill(final float val) {
        this.fill(0, this.size, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final float val) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        TFloatLink l = this.getLinkAt(fromIndex);
        if (toIndex > this.size) {
            for (int i = fromIndex; i < this.size; ++i) {
                l.setValue(val);
                l = l.getNext();
            }
            for (int i = this.size; i < toIndex; ++i) {
                this.add(val);
            }
        }
        else {
            for (int i = fromIndex; i < toIndex; ++i) {
                l.setValue(val);
                l = l.getNext();
            }
        }
    }
    
    public int binarySearch(final float value) {
        return this.binarySearch(value, 0, this.size());
    }
    
    public int binarySearch(final float value, final int fromIndex, final int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (toIndex > this.size) {
            throw new IndexOutOfBoundsException("end index > size: " + toIndex + " > " + this.size);
        }
        if (toIndex < fromIndex) {
            return -(fromIndex + 1);
        }
        int from = fromIndex;
        TFloatLink fromLink = this.getLinkAt(fromIndex);
        int to = toIndex;
        while (from < to) {
            final int mid = from + to >>> 1;
            final TFloatLink middle = getLink(fromLink, from, mid);
            if (middle.getValue() == value) {
                return mid;
            }
            if (middle.getValue() < value) {
                from = mid + 1;
                fromLink = middle.next;
            }
            else {
                to = mid - 1;
            }
        }
        return -(from + 1);
    }
    
    public int indexOf(final float value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final float value) {
        int count = offset;
        for (TFloatLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                return count;
            }
            ++count;
        }
        return -1;
    }
    
    public int lastIndexOf(final float value) {
        return this.lastIndexOf(0, value);
    }
    
    public int lastIndexOf(final int offset, final float value) {
        if (this.isEmpty()) {
            return -1;
        }
        int last = -1;
        int count = offset;
        for (TFloatLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                last = count;
            }
            ++count;
        }
        return last;
    }
    
    public boolean contains(final float value) {
        if (this.isEmpty()) {
            return false;
        }
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                return true;
            }
        }
        return false;
    }
    
    public TFloatIterator iterator() {
        return new TFloatIterator() {
            TFloatLink l = TFloatLinkedList.this.head;
            TFloatLink current;
            
            public float next() {
                if (TFloatLinkedList.no(this.l)) {
                    throw new NoSuchElementException();
                }
                final float ret = this.l.getValue();
                this.current = this.l;
                this.l = this.l.getNext();
                return ret;
            }
            
            public boolean hasNext() {
                return TFloatLinkedList.got(this.l);
            }
            
            public void remove() {
                if (this.current == null) {
                    throw new IllegalStateException();
                }
                TFloatLinkedList.this.removeLink(this.current);
                this.current = null;
            }
        };
    }
    
    public TFloatList grep(final TFloatProcedure condition) {
        final TFloatList ret = new TFloatLinkedList();
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            if (condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public TFloatList inverseGrep(final TFloatProcedure condition) {
        final TFloatList ret = new TFloatLinkedList();
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            if (!condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public float max() {
        float ret = Float.NEGATIVE_INFINITY;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            if (ret < l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public float min() {
        float ret = Float.POSITIVE_INFINITY;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            if (ret > l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public float sum() {
        float sum = 0.0f;
        for (TFloatLink l = this.head; got(l); l = l.getNext()) {
            sum += l.getValue();
        }
        return sum;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeFloat(this.no_entry_value);
        out.writeInt(this.size);
        for (final float next : this) {
            out.writeFloat(next);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.no_entry_value = in.readFloat();
        for (int len = in.readInt(), i = 0; i < len; ++i) {
            this.add(in.readFloat());
        }
    }
    
    static boolean got(final Object ref) {
        return ref != null;
    }
    
    static boolean no(final Object ref) {
        return ref == null;
    }
    
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TFloatLinkedList that = (TFloatLinkedList)o;
        if (this.no_entry_value != that.no_entry_value) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        final TFloatIterator iterator = this.iterator();
        final TFloatIterator thatIterator = that.iterator();
        while (iterator.hasNext()) {
            if (!thatIterator.hasNext()) {
                return false;
            }
            if (iterator.next() != thatIterator.next()) {
                return false;
            }
        }
        return true;
    }
    
    public int hashCode() {
        int result = HashFunctions.hash(this.no_entry_value);
        result = 31 * result + this.size;
        final TFloatIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result = 31 * result + HashFunctions.hash(iterator.next());
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        final TFloatIterator it = this.iterator();
        while (it.hasNext()) {
            final float next = it.next();
            buf.append(next);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    
    static class TFloatLink
    {
        float value;
        TFloatLink previous;
        TFloatLink next;
        
        TFloatLink(final float value) {
            this.value = value;
        }
        
        public float getValue() {
            return this.value;
        }
        
        public void setValue(final float value) {
            this.value = value;
        }
        
        public TFloatLink getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(final TFloatLink previous) {
            this.previous = previous;
        }
        
        public TFloatLink getNext() {
            return this.next;
        }
        
        public void setNext(final TFloatLink next) {
            this.next = next;
        }
    }
    
    class RemoveProcedure implements TFloatProcedure
    {
        boolean changed;
        
        RemoveProcedure() {
            this.changed = false;
        }
        
        public boolean execute(final float value) {
            if (TFloatLinkedList.this.remove(value)) {
                this.changed = true;
            }
            return true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
    }
}
