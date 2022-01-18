// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.linked;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import gnu.trove.procedure.TShortProcedure;
import java.util.Random;
import gnu.trove.function.TShortFunction;
import java.util.Arrays;
import gnu.trove.TShortCollection;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TShortIterator;
import java.io.Externalizable;
import gnu.trove.list.TShortList;

public class TShortLinkedList implements TShortList, Externalizable
{
    short no_entry_value;
    int size;
    TShortLink head;
    TShortLink tail;
    
    public TShortLinkedList() {
        this.head = null;
        this.tail = this.head;
    }
    
    public TShortLinkedList(final short no_entry_value) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = no_entry_value;
    }
    
    public TShortLinkedList(final TShortList list) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = list.getNoEntryValue();
        for (final short next : list) {
            this.add(next);
        }
    }
    
    public short getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean add(final short val) {
        final TShortLink l = new TShortLink(val);
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
    
    public void add(final short[] vals) {
        for (final short val : vals) {
            this.add(val);
        }
    }
    
    public void add(final short[] vals, final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            final short val = vals[offset + i];
            this.add(val);
        }
    }
    
    public void insert(final int offset, final short value) {
        final TShortLinkedList tmp = new TShortLinkedList();
        tmp.add(value);
        this.insert(offset, tmp);
    }
    
    public void insert(final int offset, final short[] values) {
        this.insert(offset, link(values, 0, values.length));
    }
    
    public void insert(final int offset, final short[] values, final int valOffset, final int len) {
        this.insert(offset, link(values, valOffset, len));
    }
    
    void insert(final int offset, final TShortLinkedList tmp) {
        final TShortLink l = this.getLinkAt(offset);
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
            final TShortLink prev = l.getPrevious();
            l.getPrevious().setNext(tmp.head);
            tmp.tail.setNext(l);
            l.setPrevious(tmp.tail);
            tmp.head.setPrevious(prev);
        }
    }
    
    static TShortLinkedList link(final short[] values, final int valOffset, final int len) {
        final TShortLinkedList ret = new TShortLinkedList();
        for (int i = 0; i < len; ++i) {
            ret.add(values[valOffset + i]);
        }
        return ret;
    }
    
    public short get(final int offset) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TShortLink l = this.getLinkAt(offset);
        if (no(l)) {
            return this.no_entry_value;
        }
        return l.getValue();
    }
    
    public TShortLink getLinkAt(final int offset) {
        if (offset >= this.size()) {
            return null;
        }
        if (offset <= this.size() >>> 1) {
            return getLink(this.head, 0, offset, true);
        }
        return getLink(this.tail, this.size() - 1, offset, false);
    }
    
    private static TShortLink getLink(final TShortLink l, final int idx, final int offset) {
        return getLink(l, idx, offset, true);
    }
    
    private static TShortLink getLink(TShortLink l, final int idx, final int offset, final boolean next) {
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
    
    public short set(final int offset, final short val) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TShortLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new IndexOutOfBoundsException("at offset " + offset);
        }
        final short prev = l.getValue();
        l.setValue(val);
        return prev;
    }
    
    public void set(final int offset, final short[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final short[] values, final int valOffset, final int length) {
        for (int i = 0; i < length; ++i) {
            final short value = values[valOffset + i];
            this.set(offset + i, value);
        }
    }
    
    public short replace(final int offset, final short val) {
        return this.set(offset, val);
    }
    
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean remove(final short value) {
        boolean changed = false;
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                changed = true;
                this.removeLink(l);
            }
        }
        return changed;
    }
    
    private void removeLink(final TShortLink l) {
        if (no(l)) {
            return;
        }
        --this.size;
        final TShortLink prev = l.getPrevious();
        final TShortLink next = l.getNext();
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
            if (!(o instanceof Short)) {
                return false;
            }
            final Short i = (Short)o;
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TShortCollection collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final short i : collection) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final short[] array) {
        if (this.isEmpty()) {
            return false;
        }
        for (final short i : array) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Short> collection) {
        boolean ret = false;
        for (final Short v : collection) {
            if (this.add(v)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final TShortCollection collection) {
        boolean ret = false;
        for (final short i : collection) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final short[] array) {
        boolean ret = false;
        for (final short i : array) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean modified = false;
        final TShortIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final TShortCollection collection) {
        boolean modified = false;
        final TShortIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final short[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TShortIterator iter = this.iterator();
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
        final TShortIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final TShortCollection collection) {
        boolean modified = false;
        final TShortIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final short[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TShortIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) >= 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public short removeAt(final int offset) {
        final TShortLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new ArrayIndexOutOfBoundsException("no elemenet at " + offset);
        }
        final short prev = l.getValue();
        this.removeLink(l);
        return prev;
    }
    
    public void remove(final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            this.removeAt(offset);
        }
    }
    
    public void transformValues(final TShortFunction function) {
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            l.setValue(function.execute(l.getValue()));
        }
    }
    
    public void reverse() {
        final TShortLink h = this.head;
        final TShortLink t = this.tail;
        TShortLink l = this.head;
        while (got(l)) {
            final TShortLink next = l.getNext();
            final TShortLink prev = l.getPrevious();
            final TShortLink tmp = l;
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
        final TShortLink start = this.getLinkAt(from);
        final TShortLink stop = this.getLinkAt(to);
        TShortLink tmp = null;
        final TShortLink tmpHead = start.getPrevious();
        TShortLink l = start;
        while (l != stop) {
            final TShortLink next = l.getNext();
            final TShortLink prev = l.getPrevious();
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
            final TShortLink l = this.getLinkAt(rand.nextInt(this.size()));
            this.removeLink(l);
            this.add(l.getValue());
        }
    }
    
    public TShortList subList(final int begin, final int end) {
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
        final TShortLinkedList ret = new TShortLinkedList();
        TShortLink tmp = this.getLinkAt(begin);
        for (int i = begin; i < end; ++i) {
            ret.add(tmp.getValue());
            tmp = tmp.getNext();
        }
        return ret;
    }
    
    public short[] toArray() {
        return this.toArray(new short[this.size], 0, this.size);
    }
    
    public short[] toArray(final int offset, final int len) {
        return this.toArray(new short[len], offset, 0, len);
    }
    
    public short[] toArray(final short[] dest) {
        return this.toArray(dest, 0, this.size);
    }
    
    public short[] toArray(final short[] dest, final int offset, final int len) {
        return this.toArray(dest, offset, 0, len);
    }
    
    public short[] toArray(final short[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this.size()) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        TShortLink tmp = this.getLinkAt(source_pos);
        for (int i = 0; i < len; ++i) {
            dest[dest_pos + i] = tmp.getValue();
            tmp = tmp.getNext();
        }
        return dest;
    }
    
    public boolean forEach(final TShortProcedure procedure) {
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TShortProcedure procedure) {
        for (TShortLink l = this.tail; got(l); l = l.getPrevious()) {
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
        final TShortList tmp = this.subList(fromIndex, toIndex);
        final short[] vals = tmp.toArray();
        Arrays.sort(vals);
        this.set(fromIndex, vals);
    }
    
    public void fill(final short val) {
        this.fill(0, this.size, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final short val) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        TShortLink l = this.getLinkAt(fromIndex);
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
    
    public int binarySearch(final short value) {
        return this.binarySearch(value, 0, this.size());
    }
    
    public int binarySearch(final short value, final int fromIndex, final int toIndex) {
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
        TShortLink fromLink = this.getLinkAt(fromIndex);
        int to = toIndex;
        while (from < to) {
            final int mid = from + to >>> 1;
            final TShortLink middle = getLink(fromLink, from, mid);
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
    
    public int indexOf(final short value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final short value) {
        int count = offset;
        for (TShortLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                return count;
            }
            ++count;
        }
        return -1;
    }
    
    public int lastIndexOf(final short value) {
        return this.lastIndexOf(0, value);
    }
    
    public int lastIndexOf(final int offset, final short value) {
        if (this.isEmpty()) {
            return -1;
        }
        int last = -1;
        int count = offset;
        for (TShortLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                last = count;
            }
            ++count;
        }
        return last;
    }
    
    public boolean contains(final short value) {
        if (this.isEmpty()) {
            return false;
        }
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                return true;
            }
        }
        return false;
    }
    
    public TShortIterator iterator() {
        return new TShortIterator() {
            TShortLink l = TShortLinkedList.this.head;
            TShortLink current;
            
            public short next() {
                if (TShortLinkedList.no(this.l)) {
                    throw new NoSuchElementException();
                }
                final short ret = this.l.getValue();
                this.current = this.l;
                this.l = this.l.getNext();
                return ret;
            }
            
            public boolean hasNext() {
                return TShortLinkedList.got(this.l);
            }
            
            public void remove() {
                if (this.current == null) {
                    throw new IllegalStateException();
                }
                TShortLinkedList.this.removeLink(this.current);
                this.current = null;
            }
        };
    }
    
    public TShortList grep(final TShortProcedure condition) {
        final TShortList ret = new TShortLinkedList();
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            if (condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public TShortList inverseGrep(final TShortProcedure condition) {
        final TShortList ret = new TShortLinkedList();
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            if (!condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public short max() {
        short ret = -32768;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            if (ret < l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public short min() {
        short ret = 32767;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            if (ret > l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public short sum() {
        short sum = 0;
        for (TShortLink l = this.head; got(l); l = l.getNext()) {
            sum += l.getValue();
        }
        return sum;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeShort(this.no_entry_value);
        out.writeInt(this.size);
        for (final short next : this) {
            out.writeShort(next);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.no_entry_value = in.readShort();
        for (int len = in.readInt(), i = 0; i < len; ++i) {
            this.add(in.readShort());
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
        final TShortLinkedList that = (TShortLinkedList)o;
        if (this.no_entry_value != that.no_entry_value) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        final TShortIterator iterator = this.iterator();
        final TShortIterator thatIterator = that.iterator();
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
        final TShortIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result = 31 * result + HashFunctions.hash(iterator.next());
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        final TShortIterator it = this.iterator();
        while (it.hasNext()) {
            final short next = it.next();
            buf.append(next);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    
    static class TShortLink
    {
        short value;
        TShortLink previous;
        TShortLink next;
        
        TShortLink(final short value) {
            this.value = value;
        }
        
        public short getValue() {
            return this.value;
        }
        
        public void setValue(final short value) {
            this.value = value;
        }
        
        public TShortLink getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(final TShortLink previous) {
            this.previous = previous;
        }
        
        public TShortLink getNext() {
            return this.next;
        }
        
        public void setNext(final TShortLink next) {
            this.next = next;
        }
    }
    
    class RemoveProcedure implements TShortProcedure
    {
        boolean changed;
        
        RemoveProcedure() {
            this.changed = false;
        }
        
        public boolean execute(final short value) {
            if (TShortLinkedList.this.remove(value)) {
                this.changed = true;
            }
            return true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
    }
}
