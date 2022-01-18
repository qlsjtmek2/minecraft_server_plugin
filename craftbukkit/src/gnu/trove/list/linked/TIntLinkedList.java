// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.linked;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import gnu.trove.procedure.TIntProcedure;
import java.util.Random;
import gnu.trove.function.TIntFunction;
import java.util.Arrays;
import gnu.trove.TIntCollection;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TIntIterator;
import java.io.Externalizable;
import gnu.trove.list.TIntList;

public class TIntLinkedList implements TIntList, Externalizable
{
    int no_entry_value;
    int size;
    TIntLink head;
    TIntLink tail;
    
    public TIntLinkedList() {
        this.head = null;
        this.tail = this.head;
    }
    
    public TIntLinkedList(final int no_entry_value) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = no_entry_value;
    }
    
    public TIntLinkedList(final TIntList list) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = list.getNoEntryValue();
        for (final int next : list) {
            this.add(next);
        }
    }
    
    public int getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean add(final int val) {
        final TIntLink l = new TIntLink(val);
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
    
    public void add(final int[] vals) {
        for (final int val : vals) {
            this.add(val);
        }
    }
    
    public void add(final int[] vals, final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            final int val = vals[offset + i];
            this.add(val);
        }
    }
    
    public void insert(final int offset, final int value) {
        final TIntLinkedList tmp = new TIntLinkedList();
        tmp.add(value);
        this.insert(offset, tmp);
    }
    
    public void insert(final int offset, final int[] values) {
        this.insert(offset, link(values, 0, values.length));
    }
    
    public void insert(final int offset, final int[] values, final int valOffset, final int len) {
        this.insert(offset, link(values, valOffset, len));
    }
    
    void insert(final int offset, final TIntLinkedList tmp) {
        final TIntLink l = this.getLinkAt(offset);
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
            final TIntLink prev = l.getPrevious();
            l.getPrevious().setNext(tmp.head);
            tmp.tail.setNext(l);
            l.setPrevious(tmp.tail);
            tmp.head.setPrevious(prev);
        }
    }
    
    static TIntLinkedList link(final int[] values, final int valOffset, final int len) {
        final TIntLinkedList ret = new TIntLinkedList();
        for (int i = 0; i < len; ++i) {
            ret.add(values[valOffset + i]);
        }
        return ret;
    }
    
    public int get(final int offset) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TIntLink l = this.getLinkAt(offset);
        if (no(l)) {
            return this.no_entry_value;
        }
        return l.getValue();
    }
    
    public TIntLink getLinkAt(final int offset) {
        if (offset >= this.size()) {
            return null;
        }
        if (offset <= this.size() >>> 1) {
            return getLink(this.head, 0, offset, true);
        }
        return getLink(this.tail, this.size() - 1, offset, false);
    }
    
    private static TIntLink getLink(final TIntLink l, final int idx, final int offset) {
        return getLink(l, idx, offset, true);
    }
    
    private static TIntLink getLink(TIntLink l, final int idx, final int offset, final boolean next) {
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
    
    public int set(final int offset, final int val) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TIntLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new IndexOutOfBoundsException("at offset " + offset);
        }
        final int prev = l.getValue();
        l.setValue(val);
        return prev;
    }
    
    public void set(final int offset, final int[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final int[] values, final int valOffset, final int length) {
        for (int i = 0; i < length; ++i) {
            final int value = values[valOffset + i];
            this.set(offset + i, value);
        }
    }
    
    public int replace(final int offset, final int val) {
        return this.set(offset, val);
    }
    
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean remove(final int value) {
        boolean changed = false;
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                changed = true;
                this.removeLink(l);
            }
        }
        return changed;
    }
    
    private void removeLink(final TIntLink l) {
        if (no(l)) {
            return;
        }
        --this.size;
        final TIntLink prev = l.getPrevious();
        final TIntLink next = l.getNext();
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
            if (!(o instanceof Integer)) {
                return false;
            }
            final Integer i = (Integer)o;
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TIntCollection collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final int i : collection) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final int[] array) {
        if (this.isEmpty()) {
            return false;
        }
        for (final int i : array) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Integer> collection) {
        boolean ret = false;
        for (final Integer v : collection) {
            if (this.add(v)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final TIntCollection collection) {
        boolean ret = false;
        for (final int i : collection) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final int[] array) {
        boolean ret = false;
        for (final int i : array) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean modified = false;
        final TIntIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final TIntCollection collection) {
        boolean modified = false;
        final TIntIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final int[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TIntIterator iter = this.iterator();
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
        final TIntIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final TIntCollection collection) {
        boolean modified = false;
        final TIntIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final int[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TIntIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) >= 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public int removeAt(final int offset) {
        final TIntLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new ArrayIndexOutOfBoundsException("no elemenet at " + offset);
        }
        final int prev = l.getValue();
        this.removeLink(l);
        return prev;
    }
    
    public void remove(final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            this.removeAt(offset);
        }
    }
    
    public void transformValues(final TIntFunction function) {
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            l.setValue(function.execute(l.getValue()));
        }
    }
    
    public void reverse() {
        final TIntLink h = this.head;
        final TIntLink t = this.tail;
        TIntLink l = this.head;
        while (got(l)) {
            final TIntLink next = l.getNext();
            final TIntLink prev = l.getPrevious();
            final TIntLink tmp = l;
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
        final TIntLink start = this.getLinkAt(from);
        final TIntLink stop = this.getLinkAt(to);
        TIntLink tmp = null;
        final TIntLink tmpHead = start.getPrevious();
        TIntLink l = start;
        while (l != stop) {
            final TIntLink next = l.getNext();
            final TIntLink prev = l.getPrevious();
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
            final TIntLink l = this.getLinkAt(rand.nextInt(this.size()));
            this.removeLink(l);
            this.add(l.getValue());
        }
    }
    
    public TIntList subList(final int begin, final int end) {
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
        final TIntLinkedList ret = new TIntLinkedList();
        TIntLink tmp = this.getLinkAt(begin);
        for (int i = begin; i < end; ++i) {
            ret.add(tmp.getValue());
            tmp = tmp.getNext();
        }
        return ret;
    }
    
    public int[] toArray() {
        return this.toArray(new int[this.size], 0, this.size);
    }
    
    public int[] toArray(final int offset, final int len) {
        return this.toArray(new int[len], offset, 0, len);
    }
    
    public int[] toArray(final int[] dest) {
        return this.toArray(dest, 0, this.size);
    }
    
    public int[] toArray(final int[] dest, final int offset, final int len) {
        return this.toArray(dest, offset, 0, len);
    }
    
    public int[] toArray(final int[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this.size()) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        TIntLink tmp = this.getLinkAt(source_pos);
        for (int i = 0; i < len; ++i) {
            dest[dest_pos + i] = tmp.getValue();
            tmp = tmp.getNext();
        }
        return dest;
    }
    
    public boolean forEach(final TIntProcedure procedure) {
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TIntProcedure procedure) {
        for (TIntLink l = this.tail; got(l); l = l.getPrevious()) {
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
        final TIntList tmp = this.subList(fromIndex, toIndex);
        final int[] vals = tmp.toArray();
        Arrays.sort(vals);
        this.set(fromIndex, vals);
    }
    
    public void fill(final int val) {
        this.fill(0, this.size, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final int val) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        TIntLink l = this.getLinkAt(fromIndex);
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
    
    public int binarySearch(final int value) {
        return this.binarySearch(value, 0, this.size());
    }
    
    public int binarySearch(final int value, final int fromIndex, final int toIndex) {
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
        TIntLink fromLink = this.getLinkAt(fromIndex);
        int to = toIndex;
        while (from < to) {
            final int mid = from + to >>> 1;
            final TIntLink middle = getLink(fromLink, from, mid);
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
    
    public int indexOf(final int value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final int value) {
        int count = offset;
        for (TIntLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                return count;
            }
            ++count;
        }
        return -1;
    }
    
    public int lastIndexOf(final int value) {
        return this.lastIndexOf(0, value);
    }
    
    public int lastIndexOf(final int offset, final int value) {
        if (this.isEmpty()) {
            return -1;
        }
        int last = -1;
        int count = offset;
        for (TIntLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                last = count;
            }
            ++count;
        }
        return last;
    }
    
    public boolean contains(final int value) {
        if (this.isEmpty()) {
            return false;
        }
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                return true;
            }
        }
        return false;
    }
    
    public TIntIterator iterator() {
        return new TIntIterator() {
            TIntLink l = TIntLinkedList.this.head;
            TIntLink current;
            
            public int next() {
                if (TIntLinkedList.no(this.l)) {
                    throw new NoSuchElementException();
                }
                final int ret = this.l.getValue();
                this.current = this.l;
                this.l = this.l.getNext();
                return ret;
            }
            
            public boolean hasNext() {
                return TIntLinkedList.got(this.l);
            }
            
            public void remove() {
                if (this.current == null) {
                    throw new IllegalStateException();
                }
                TIntLinkedList.this.removeLink(this.current);
                this.current = null;
            }
        };
    }
    
    public TIntList grep(final TIntProcedure condition) {
        final TIntList ret = new TIntLinkedList();
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            if (condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public TIntList inverseGrep(final TIntProcedure condition) {
        final TIntList ret = new TIntLinkedList();
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            if (!condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public int max() {
        int ret = Integer.MIN_VALUE;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            if (ret < l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public int min() {
        int ret = Integer.MAX_VALUE;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            if (ret > l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public int sum() {
        int sum = 0;
        for (TIntLink l = this.head; got(l); l = l.getNext()) {
            sum += l.getValue();
        }
        return sum;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeInt(this.no_entry_value);
        out.writeInt(this.size);
        for (final int next : this) {
            out.writeInt(next);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.no_entry_value = in.readInt();
        for (int len = in.readInt(), i = 0; i < len; ++i) {
            this.add(in.readInt());
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
        final TIntLinkedList that = (TIntLinkedList)o;
        if (this.no_entry_value != that.no_entry_value) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        final TIntIterator iterator = this.iterator();
        final TIntIterator thatIterator = that.iterator();
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
        final TIntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result = 31 * result + HashFunctions.hash(iterator.next());
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        final TIntIterator it = this.iterator();
        while (it.hasNext()) {
            final int next = it.next();
            buf.append(next);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    
    static class TIntLink
    {
        int value;
        TIntLink previous;
        TIntLink next;
        
        TIntLink(final int value) {
            this.value = value;
        }
        
        public int getValue() {
            return this.value;
        }
        
        public void setValue(final int value) {
            this.value = value;
        }
        
        public TIntLink getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(final TIntLink previous) {
            this.previous = previous;
        }
        
        public TIntLink getNext() {
            return this.next;
        }
        
        public void setNext(final TIntLink next) {
            this.next = next;
        }
    }
    
    class RemoveProcedure implements TIntProcedure
    {
        boolean changed;
        
        RemoveProcedure() {
            this.changed = false;
        }
        
        public boolean execute(final int value) {
            if (TIntLinkedList.this.remove(value)) {
                this.changed = true;
            }
            return true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
    }
}
