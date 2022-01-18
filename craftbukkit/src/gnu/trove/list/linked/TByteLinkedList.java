// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.linked;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import gnu.trove.procedure.TByteProcedure;
import java.util.Random;
import gnu.trove.function.TByteFunction;
import java.util.Arrays;
import gnu.trove.TByteCollection;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TByteIterator;
import java.io.Externalizable;
import gnu.trove.list.TByteList;

public class TByteLinkedList implements TByteList, Externalizable
{
    byte no_entry_value;
    int size;
    TByteLink head;
    TByteLink tail;
    
    public TByteLinkedList() {
        this.head = null;
        this.tail = this.head;
    }
    
    public TByteLinkedList(final byte no_entry_value) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = no_entry_value;
    }
    
    public TByteLinkedList(final TByteList list) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = list.getNoEntryValue();
        for (final byte next : list) {
            this.add(next);
        }
    }
    
    public byte getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean add(final byte val) {
        final TByteLink l = new TByteLink(val);
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
    
    public void add(final byte[] vals) {
        for (final byte val : vals) {
            this.add(val);
        }
    }
    
    public void add(final byte[] vals, final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            final byte val = vals[offset + i];
            this.add(val);
        }
    }
    
    public void insert(final int offset, final byte value) {
        final TByteLinkedList tmp = new TByteLinkedList();
        tmp.add(value);
        this.insert(offset, tmp);
    }
    
    public void insert(final int offset, final byte[] values) {
        this.insert(offset, link(values, 0, values.length));
    }
    
    public void insert(final int offset, final byte[] values, final int valOffset, final int len) {
        this.insert(offset, link(values, valOffset, len));
    }
    
    void insert(final int offset, final TByteLinkedList tmp) {
        final TByteLink l = this.getLinkAt(offset);
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
            final TByteLink prev = l.getPrevious();
            l.getPrevious().setNext(tmp.head);
            tmp.tail.setNext(l);
            l.setPrevious(tmp.tail);
            tmp.head.setPrevious(prev);
        }
    }
    
    static TByteLinkedList link(final byte[] values, final int valOffset, final int len) {
        final TByteLinkedList ret = new TByteLinkedList();
        for (int i = 0; i < len; ++i) {
            ret.add(values[valOffset + i]);
        }
        return ret;
    }
    
    public byte get(final int offset) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TByteLink l = this.getLinkAt(offset);
        if (no(l)) {
            return this.no_entry_value;
        }
        return l.getValue();
    }
    
    public TByteLink getLinkAt(final int offset) {
        if (offset >= this.size()) {
            return null;
        }
        if (offset <= this.size() >>> 1) {
            return getLink(this.head, 0, offset, true);
        }
        return getLink(this.tail, this.size() - 1, offset, false);
    }
    
    private static TByteLink getLink(final TByteLink l, final int idx, final int offset) {
        return getLink(l, idx, offset, true);
    }
    
    private static TByteLink getLink(TByteLink l, final int idx, final int offset, final boolean next) {
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
    
    public byte set(final int offset, final byte val) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TByteLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new IndexOutOfBoundsException("at offset " + offset);
        }
        final byte prev = l.getValue();
        l.setValue(val);
        return prev;
    }
    
    public void set(final int offset, final byte[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final byte[] values, final int valOffset, final int length) {
        for (int i = 0; i < length; ++i) {
            final byte value = values[valOffset + i];
            this.set(offset + i, value);
        }
    }
    
    public byte replace(final int offset, final byte val) {
        return this.set(offset, val);
    }
    
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean remove(final byte value) {
        boolean changed = false;
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                changed = true;
                this.removeLink(l);
            }
        }
        return changed;
    }
    
    private void removeLink(final TByteLink l) {
        if (no(l)) {
            return;
        }
        --this.size;
        final TByteLink prev = l.getPrevious();
        final TByteLink next = l.getNext();
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
            if (!(o instanceof Byte)) {
                return false;
            }
            final Byte i = (Byte)o;
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TByteCollection collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final byte i : collection) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final byte[] array) {
        if (this.isEmpty()) {
            return false;
        }
        for (final byte i : array) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Byte> collection) {
        boolean ret = false;
        for (final Byte v : collection) {
            if (this.add(v)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final TByteCollection collection) {
        boolean ret = false;
        for (final byte i : collection) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final byte[] array) {
        boolean ret = false;
        for (final byte i : array) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean modified = false;
        final TByteIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final TByteCollection collection) {
        boolean modified = false;
        final TByteIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final byte[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TByteIterator iter = this.iterator();
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
        final TByteIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final TByteCollection collection) {
        boolean modified = false;
        final TByteIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final byte[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TByteIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) >= 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public byte removeAt(final int offset) {
        final TByteLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new ArrayIndexOutOfBoundsException("no elemenet at " + offset);
        }
        final byte prev = l.getValue();
        this.removeLink(l);
        return prev;
    }
    
    public void remove(final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            this.removeAt(offset);
        }
    }
    
    public void transformValues(final TByteFunction function) {
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            l.setValue(function.execute(l.getValue()));
        }
    }
    
    public void reverse() {
        final TByteLink h = this.head;
        final TByteLink t = this.tail;
        TByteLink l = this.head;
        while (got(l)) {
            final TByteLink next = l.getNext();
            final TByteLink prev = l.getPrevious();
            final TByteLink tmp = l;
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
        final TByteLink start = this.getLinkAt(from);
        final TByteLink stop = this.getLinkAt(to);
        TByteLink tmp = null;
        final TByteLink tmpHead = start.getPrevious();
        TByteLink l = start;
        while (l != stop) {
            final TByteLink next = l.getNext();
            final TByteLink prev = l.getPrevious();
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
            final TByteLink l = this.getLinkAt(rand.nextInt(this.size()));
            this.removeLink(l);
            this.add(l.getValue());
        }
    }
    
    public TByteList subList(final int begin, final int end) {
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
        final TByteLinkedList ret = new TByteLinkedList();
        TByteLink tmp = this.getLinkAt(begin);
        for (int i = begin; i < end; ++i) {
            ret.add(tmp.getValue());
            tmp = tmp.getNext();
        }
        return ret;
    }
    
    public byte[] toArray() {
        return this.toArray(new byte[this.size], 0, this.size);
    }
    
    public byte[] toArray(final int offset, final int len) {
        return this.toArray(new byte[len], offset, 0, len);
    }
    
    public byte[] toArray(final byte[] dest) {
        return this.toArray(dest, 0, this.size);
    }
    
    public byte[] toArray(final byte[] dest, final int offset, final int len) {
        return this.toArray(dest, offset, 0, len);
    }
    
    public byte[] toArray(final byte[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this.size()) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        TByteLink tmp = this.getLinkAt(source_pos);
        for (int i = 0; i < len; ++i) {
            dest[dest_pos + i] = tmp.getValue();
            tmp = tmp.getNext();
        }
        return dest;
    }
    
    public boolean forEach(final TByteProcedure procedure) {
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TByteProcedure procedure) {
        for (TByteLink l = this.tail; got(l); l = l.getPrevious()) {
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
        final TByteList tmp = this.subList(fromIndex, toIndex);
        final byte[] vals = tmp.toArray();
        Arrays.sort(vals);
        this.set(fromIndex, vals);
    }
    
    public void fill(final byte val) {
        this.fill(0, this.size, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final byte val) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        TByteLink l = this.getLinkAt(fromIndex);
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
    
    public int binarySearch(final byte value) {
        return this.binarySearch(value, 0, this.size());
    }
    
    public int binarySearch(final byte value, final int fromIndex, final int toIndex) {
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
        TByteLink fromLink = this.getLinkAt(fromIndex);
        int to = toIndex;
        while (from < to) {
            final int mid = from + to >>> 1;
            final TByteLink middle = getLink(fromLink, from, mid);
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
    
    public int indexOf(final byte value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final byte value) {
        int count = offset;
        for (TByteLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                return count;
            }
            ++count;
        }
        return -1;
    }
    
    public int lastIndexOf(final byte value) {
        return this.lastIndexOf(0, value);
    }
    
    public int lastIndexOf(final int offset, final byte value) {
        if (this.isEmpty()) {
            return -1;
        }
        int last = -1;
        int count = offset;
        for (TByteLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                last = count;
            }
            ++count;
        }
        return last;
    }
    
    public boolean contains(final byte value) {
        if (this.isEmpty()) {
            return false;
        }
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                return true;
            }
        }
        return false;
    }
    
    public TByteIterator iterator() {
        return new TByteIterator() {
            TByteLink l = TByteLinkedList.this.head;
            TByteLink current;
            
            public byte next() {
                if (TByteLinkedList.no(this.l)) {
                    throw new NoSuchElementException();
                }
                final byte ret = this.l.getValue();
                this.current = this.l;
                this.l = this.l.getNext();
                return ret;
            }
            
            public boolean hasNext() {
                return TByteLinkedList.got(this.l);
            }
            
            public void remove() {
                if (this.current == null) {
                    throw new IllegalStateException();
                }
                TByteLinkedList.this.removeLink(this.current);
                this.current = null;
            }
        };
    }
    
    public TByteList grep(final TByteProcedure condition) {
        final TByteList ret = new TByteLinkedList();
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            if (condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public TByteList inverseGrep(final TByteProcedure condition) {
        final TByteList ret = new TByteLinkedList();
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            if (!condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public byte max() {
        byte ret = -128;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            if (ret < l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public byte min() {
        byte ret = 127;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            if (ret > l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public byte sum() {
        byte sum = 0;
        for (TByteLink l = this.head; got(l); l = l.getNext()) {
            sum += l.getValue();
        }
        return sum;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeByte(this.no_entry_value);
        out.writeInt(this.size);
        for (final byte next : this) {
            out.writeByte(next);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.no_entry_value = in.readByte();
        for (int len = in.readInt(), i = 0; i < len; ++i) {
            this.add(in.readByte());
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
        final TByteLinkedList that = (TByteLinkedList)o;
        if (this.no_entry_value != that.no_entry_value) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        final TByteIterator iterator = this.iterator();
        final TByteIterator thatIterator = that.iterator();
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
        final TByteIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result = 31 * result + HashFunctions.hash(iterator.next());
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        final TByteIterator it = this.iterator();
        while (it.hasNext()) {
            final byte next = it.next();
            buf.append(next);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    
    static class TByteLink
    {
        byte value;
        TByteLink previous;
        TByteLink next;
        
        TByteLink(final byte value) {
            this.value = value;
        }
        
        public byte getValue() {
            return this.value;
        }
        
        public void setValue(final byte value) {
            this.value = value;
        }
        
        public TByteLink getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(final TByteLink previous) {
            this.previous = previous;
        }
        
        public TByteLink getNext() {
            return this.next;
        }
        
        public void setNext(final TByteLink next) {
            this.next = next;
        }
    }
    
    class RemoveProcedure implements TByteProcedure
    {
        boolean changed;
        
        RemoveProcedure() {
            this.changed = false;
        }
        
        public boolean execute(final byte value) {
            if (TByteLinkedList.this.remove(value)) {
                this.changed = true;
            }
            return true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
    }
}
