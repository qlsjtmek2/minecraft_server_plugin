// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.linked;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import gnu.trove.procedure.TLongProcedure;
import java.util.Random;
import gnu.trove.function.TLongFunction;
import java.util.Arrays;
import gnu.trove.TLongCollection;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TLongIterator;
import java.io.Externalizable;
import gnu.trove.list.TLongList;

public class TLongLinkedList implements TLongList, Externalizable
{
    long no_entry_value;
    int size;
    TLongLink head;
    TLongLink tail;
    
    public TLongLinkedList() {
        this.head = null;
        this.tail = this.head;
    }
    
    public TLongLinkedList(final long no_entry_value) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = no_entry_value;
    }
    
    public TLongLinkedList(final TLongList list) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = list.getNoEntryValue();
        for (final long next : list) {
            this.add(next);
        }
    }
    
    public long getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean add(final long val) {
        final TLongLink l = new TLongLink(val);
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
    
    public void add(final long[] vals) {
        for (final long val : vals) {
            this.add(val);
        }
    }
    
    public void add(final long[] vals, final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            final long val = vals[offset + i];
            this.add(val);
        }
    }
    
    public void insert(final int offset, final long value) {
        final TLongLinkedList tmp = new TLongLinkedList();
        tmp.add(value);
        this.insert(offset, tmp);
    }
    
    public void insert(final int offset, final long[] values) {
        this.insert(offset, link(values, 0, values.length));
    }
    
    public void insert(final int offset, final long[] values, final int valOffset, final int len) {
        this.insert(offset, link(values, valOffset, len));
    }
    
    void insert(final int offset, final TLongLinkedList tmp) {
        final TLongLink l = this.getLinkAt(offset);
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
            final TLongLink prev = l.getPrevious();
            l.getPrevious().setNext(tmp.head);
            tmp.tail.setNext(l);
            l.setPrevious(tmp.tail);
            tmp.head.setPrevious(prev);
        }
    }
    
    static TLongLinkedList link(final long[] values, final int valOffset, final int len) {
        final TLongLinkedList ret = new TLongLinkedList();
        for (int i = 0; i < len; ++i) {
            ret.add(values[valOffset + i]);
        }
        return ret;
    }
    
    public long get(final int offset) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TLongLink l = this.getLinkAt(offset);
        if (no(l)) {
            return this.no_entry_value;
        }
        return l.getValue();
    }
    
    public TLongLink getLinkAt(final int offset) {
        if (offset >= this.size()) {
            return null;
        }
        if (offset <= this.size() >>> 1) {
            return getLink(this.head, 0, offset, true);
        }
        return getLink(this.tail, this.size() - 1, offset, false);
    }
    
    private static TLongLink getLink(final TLongLink l, final int idx, final int offset) {
        return getLink(l, idx, offset, true);
    }
    
    private static TLongLink getLink(TLongLink l, final int idx, final int offset, final boolean next) {
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
    
    public long set(final int offset, final long val) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TLongLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new IndexOutOfBoundsException("at offset " + offset);
        }
        final long prev = l.getValue();
        l.setValue(val);
        return prev;
    }
    
    public void set(final int offset, final long[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final long[] values, final int valOffset, final int length) {
        for (int i = 0; i < length; ++i) {
            final long value = values[valOffset + i];
            this.set(offset + i, value);
        }
    }
    
    public long replace(final int offset, final long val) {
        return this.set(offset, val);
    }
    
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean remove(final long value) {
        boolean changed = false;
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                changed = true;
                this.removeLink(l);
            }
        }
        return changed;
    }
    
    private void removeLink(final TLongLink l) {
        if (no(l)) {
            return;
        }
        --this.size;
        final TLongLink prev = l.getPrevious();
        final TLongLink next = l.getNext();
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
            if (!(o instanceof Long)) {
                return false;
            }
            final Long i = (Long)o;
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TLongCollection collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final long i : collection) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final long[] array) {
        if (this.isEmpty()) {
            return false;
        }
        for (final long i : array) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Long> collection) {
        boolean ret = false;
        for (final Long v : collection) {
            if (this.add(v)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final TLongCollection collection) {
        boolean ret = false;
        for (final long i : collection) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final long[] array) {
        boolean ret = false;
        for (final long i : array) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean modified = false;
        final TLongIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final TLongCollection collection) {
        boolean modified = false;
        final TLongIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final long[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TLongIterator iter = this.iterator();
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
        final TLongIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final TLongCollection collection) {
        boolean modified = false;
        final TLongIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final long[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TLongIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) >= 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public long removeAt(final int offset) {
        final TLongLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new ArrayIndexOutOfBoundsException("no elemenet at " + offset);
        }
        final long prev = l.getValue();
        this.removeLink(l);
        return prev;
    }
    
    public void remove(final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            this.removeAt(offset);
        }
    }
    
    public void transformValues(final TLongFunction function) {
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            l.setValue(function.execute(l.getValue()));
        }
    }
    
    public void reverse() {
        final TLongLink h = this.head;
        final TLongLink t = this.tail;
        TLongLink l = this.head;
        while (got(l)) {
            final TLongLink next = l.getNext();
            final TLongLink prev = l.getPrevious();
            final TLongLink tmp = l;
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
        final TLongLink start = this.getLinkAt(from);
        final TLongLink stop = this.getLinkAt(to);
        TLongLink tmp = null;
        final TLongLink tmpHead = start.getPrevious();
        TLongLink l = start;
        while (l != stop) {
            final TLongLink next = l.getNext();
            final TLongLink prev = l.getPrevious();
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
            final TLongLink l = this.getLinkAt(rand.nextInt(this.size()));
            this.removeLink(l);
            this.add(l.getValue());
        }
    }
    
    public TLongList subList(final int begin, final int end) {
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
        final TLongLinkedList ret = new TLongLinkedList();
        TLongLink tmp = this.getLinkAt(begin);
        for (int i = begin; i < end; ++i) {
            ret.add(tmp.getValue());
            tmp = tmp.getNext();
        }
        return ret;
    }
    
    public long[] toArray() {
        return this.toArray(new long[this.size], 0, this.size);
    }
    
    public long[] toArray(final int offset, final int len) {
        return this.toArray(new long[len], offset, 0, len);
    }
    
    public long[] toArray(final long[] dest) {
        return this.toArray(dest, 0, this.size);
    }
    
    public long[] toArray(final long[] dest, final int offset, final int len) {
        return this.toArray(dest, offset, 0, len);
    }
    
    public long[] toArray(final long[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this.size()) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        TLongLink tmp = this.getLinkAt(source_pos);
        for (int i = 0; i < len; ++i) {
            dest[dest_pos + i] = tmp.getValue();
            tmp = tmp.getNext();
        }
        return dest;
    }
    
    public boolean forEach(final TLongProcedure procedure) {
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TLongProcedure procedure) {
        for (TLongLink l = this.tail; got(l); l = l.getPrevious()) {
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
        final TLongList tmp = this.subList(fromIndex, toIndex);
        final long[] vals = tmp.toArray();
        Arrays.sort(vals);
        this.set(fromIndex, vals);
    }
    
    public void fill(final long val) {
        this.fill(0, this.size, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final long val) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        TLongLink l = this.getLinkAt(fromIndex);
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
    
    public int binarySearch(final long value) {
        return this.binarySearch(value, 0, this.size());
    }
    
    public int binarySearch(final long value, final int fromIndex, final int toIndex) {
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
        TLongLink fromLink = this.getLinkAt(fromIndex);
        int to = toIndex;
        while (from < to) {
            final int mid = from + to >>> 1;
            final TLongLink middle = getLink(fromLink, from, mid);
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
    
    public int indexOf(final long value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final long value) {
        int count = offset;
        for (TLongLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                return count;
            }
            ++count;
        }
        return -1;
    }
    
    public int lastIndexOf(final long value) {
        return this.lastIndexOf(0, value);
    }
    
    public int lastIndexOf(final int offset, final long value) {
        if (this.isEmpty()) {
            return -1;
        }
        int last = -1;
        int count = offset;
        for (TLongLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                last = count;
            }
            ++count;
        }
        return last;
    }
    
    public boolean contains(final long value) {
        if (this.isEmpty()) {
            return false;
        }
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                return true;
            }
        }
        return false;
    }
    
    public TLongIterator iterator() {
        return new TLongIterator() {
            TLongLink l = TLongLinkedList.this.head;
            TLongLink current;
            
            public long next() {
                if (TLongLinkedList.no(this.l)) {
                    throw new NoSuchElementException();
                }
                final long ret = this.l.getValue();
                this.current = this.l;
                this.l = this.l.getNext();
                return ret;
            }
            
            public boolean hasNext() {
                return TLongLinkedList.got(this.l);
            }
            
            public void remove() {
                if (this.current == null) {
                    throw new IllegalStateException();
                }
                TLongLinkedList.this.removeLink(this.current);
                this.current = null;
            }
        };
    }
    
    public TLongList grep(final TLongProcedure condition) {
        final TLongList ret = new TLongLinkedList();
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            if (condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public TLongList inverseGrep(final TLongProcedure condition) {
        final TLongList ret = new TLongLinkedList();
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            if (!condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public long max() {
        long ret = Long.MIN_VALUE;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            if (ret < l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public long min() {
        long ret = Long.MAX_VALUE;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            if (ret > l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public long sum() {
        long sum = 0L;
        for (TLongLink l = this.head; got(l); l = l.getNext()) {
            sum += l.getValue();
        }
        return sum;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeLong(this.no_entry_value);
        out.writeInt(this.size);
        for (final long next : this) {
            out.writeLong(next);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.no_entry_value = in.readLong();
        for (int len = in.readInt(), i = 0; i < len; ++i) {
            this.add(in.readLong());
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
        final TLongLinkedList that = (TLongLinkedList)o;
        if (this.no_entry_value != that.no_entry_value) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        final TLongIterator iterator = this.iterator();
        final TLongIterator thatIterator = that.iterator();
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
        final TLongIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result = 31 * result + HashFunctions.hash(iterator.next());
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        final TLongIterator it = this.iterator();
        while (it.hasNext()) {
            final long next = it.next();
            buf.append(next);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    
    static class TLongLink
    {
        long value;
        TLongLink previous;
        TLongLink next;
        
        TLongLink(final long value) {
            this.value = value;
        }
        
        public long getValue() {
            return this.value;
        }
        
        public void setValue(final long value) {
            this.value = value;
        }
        
        public TLongLink getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(final TLongLink previous) {
            this.previous = previous;
        }
        
        public TLongLink getNext() {
            return this.next;
        }
        
        public void setNext(final TLongLink next) {
            this.next = next;
        }
    }
    
    class RemoveProcedure implements TLongProcedure
    {
        boolean changed;
        
        RemoveProcedure() {
            this.changed = false;
        }
        
        public boolean execute(final long value) {
            if (TLongLinkedList.this.remove(value)) {
                this.changed = true;
            }
            return true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
    }
}
