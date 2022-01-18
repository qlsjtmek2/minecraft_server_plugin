// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.linked;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import gnu.trove.procedure.TCharProcedure;
import java.util.Random;
import gnu.trove.function.TCharFunction;
import java.util.Arrays;
import gnu.trove.TCharCollection;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TCharIterator;
import java.io.Externalizable;
import gnu.trove.list.TCharList;

public class TCharLinkedList implements TCharList, Externalizable
{
    char no_entry_value;
    int size;
    TCharLink head;
    TCharLink tail;
    
    public TCharLinkedList() {
        this.head = null;
        this.tail = this.head;
    }
    
    public TCharLinkedList(final char no_entry_value) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = no_entry_value;
    }
    
    public TCharLinkedList(final TCharList list) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = list.getNoEntryValue();
        for (final char next : list) {
            this.add(next);
        }
    }
    
    public char getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean add(final char val) {
        final TCharLink l = new TCharLink(val);
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
    
    public void add(final char[] vals) {
        for (final char val : vals) {
            this.add(val);
        }
    }
    
    public void add(final char[] vals, final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            final char val = vals[offset + i];
            this.add(val);
        }
    }
    
    public void insert(final int offset, final char value) {
        final TCharLinkedList tmp = new TCharLinkedList();
        tmp.add(value);
        this.insert(offset, tmp);
    }
    
    public void insert(final int offset, final char[] values) {
        this.insert(offset, link(values, 0, values.length));
    }
    
    public void insert(final int offset, final char[] values, final int valOffset, final int len) {
        this.insert(offset, link(values, valOffset, len));
    }
    
    void insert(final int offset, final TCharLinkedList tmp) {
        final TCharLink l = this.getLinkAt(offset);
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
            final TCharLink prev = l.getPrevious();
            l.getPrevious().setNext(tmp.head);
            tmp.tail.setNext(l);
            l.setPrevious(tmp.tail);
            tmp.head.setPrevious(prev);
        }
    }
    
    static TCharLinkedList link(final char[] values, final int valOffset, final int len) {
        final TCharLinkedList ret = new TCharLinkedList();
        for (int i = 0; i < len; ++i) {
            ret.add(values[valOffset + i]);
        }
        return ret;
    }
    
    public char get(final int offset) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TCharLink l = this.getLinkAt(offset);
        if (no(l)) {
            return this.no_entry_value;
        }
        return l.getValue();
    }
    
    public TCharLink getLinkAt(final int offset) {
        if (offset >= this.size()) {
            return null;
        }
        if (offset <= this.size() >>> 1) {
            return getLink(this.head, 0, offset, true);
        }
        return getLink(this.tail, this.size() - 1, offset, false);
    }
    
    private static TCharLink getLink(final TCharLink l, final int idx, final int offset) {
        return getLink(l, idx, offset, true);
    }
    
    private static TCharLink getLink(TCharLink l, final int idx, final int offset, final boolean next) {
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
    
    public char set(final int offset, final char val) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TCharLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new IndexOutOfBoundsException("at offset " + offset);
        }
        final char prev = l.getValue();
        l.setValue(val);
        return prev;
    }
    
    public void set(final int offset, final char[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final char[] values, final int valOffset, final int length) {
        for (int i = 0; i < length; ++i) {
            final char value = values[valOffset + i];
            this.set(offset + i, value);
        }
    }
    
    public char replace(final int offset, final char val) {
        return this.set(offset, val);
    }
    
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean remove(final char value) {
        boolean changed = false;
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                changed = true;
                this.removeLink(l);
            }
        }
        return changed;
    }
    
    private void removeLink(final TCharLink l) {
        if (no(l)) {
            return;
        }
        --this.size;
        final TCharLink prev = l.getPrevious();
        final TCharLink next = l.getNext();
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
            if (!(o instanceof Character)) {
                return false;
            }
            final Character i = (Character)o;
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TCharCollection collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final char i : collection) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final char[] array) {
        if (this.isEmpty()) {
            return false;
        }
        for (final char i : array) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Character> collection) {
        boolean ret = false;
        for (final Character v : collection) {
            if (this.add(v)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final TCharCollection collection) {
        boolean ret = false;
        for (final char i : collection) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final char[] array) {
        boolean ret = false;
        for (final char i : array) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean modified = false;
        final TCharIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final TCharCollection collection) {
        boolean modified = false;
        final TCharIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final char[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TCharIterator iter = this.iterator();
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
        final TCharIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final TCharCollection collection) {
        boolean modified = false;
        final TCharIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final char[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TCharIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) >= 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public char removeAt(final int offset) {
        final TCharLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new ArrayIndexOutOfBoundsException("no elemenet at " + offset);
        }
        final char prev = l.getValue();
        this.removeLink(l);
        return prev;
    }
    
    public void remove(final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            this.removeAt(offset);
        }
    }
    
    public void transformValues(final TCharFunction function) {
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            l.setValue(function.execute(l.getValue()));
        }
    }
    
    public void reverse() {
        final TCharLink h = this.head;
        final TCharLink t = this.tail;
        TCharLink l = this.head;
        while (got(l)) {
            final TCharLink next = l.getNext();
            final TCharLink prev = l.getPrevious();
            final TCharLink tmp = l;
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
        final TCharLink start = this.getLinkAt(from);
        final TCharLink stop = this.getLinkAt(to);
        TCharLink tmp = null;
        final TCharLink tmpHead = start.getPrevious();
        TCharLink l = start;
        while (l != stop) {
            final TCharLink next = l.getNext();
            final TCharLink prev = l.getPrevious();
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
            final TCharLink l = this.getLinkAt(rand.nextInt(this.size()));
            this.removeLink(l);
            this.add(l.getValue());
        }
    }
    
    public TCharList subList(final int begin, final int end) {
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
        final TCharLinkedList ret = new TCharLinkedList();
        TCharLink tmp = this.getLinkAt(begin);
        for (int i = begin; i < end; ++i) {
            ret.add(tmp.getValue());
            tmp = tmp.getNext();
        }
        return ret;
    }
    
    public char[] toArray() {
        return this.toArray(new char[this.size], 0, this.size);
    }
    
    public char[] toArray(final int offset, final int len) {
        return this.toArray(new char[len], offset, 0, len);
    }
    
    public char[] toArray(final char[] dest) {
        return this.toArray(dest, 0, this.size);
    }
    
    public char[] toArray(final char[] dest, final int offset, final int len) {
        return this.toArray(dest, offset, 0, len);
    }
    
    public char[] toArray(final char[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this.size()) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        TCharLink tmp = this.getLinkAt(source_pos);
        for (int i = 0; i < len; ++i) {
            dest[dest_pos + i] = tmp.getValue();
            tmp = tmp.getNext();
        }
        return dest;
    }
    
    public boolean forEach(final TCharProcedure procedure) {
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TCharProcedure procedure) {
        for (TCharLink l = this.tail; got(l); l = l.getPrevious()) {
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
        final TCharList tmp = this.subList(fromIndex, toIndex);
        final char[] vals = tmp.toArray();
        Arrays.sort(vals);
        this.set(fromIndex, vals);
    }
    
    public void fill(final char val) {
        this.fill(0, this.size, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final char val) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        TCharLink l = this.getLinkAt(fromIndex);
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
    
    public int binarySearch(final char value) {
        return this.binarySearch(value, 0, this.size());
    }
    
    public int binarySearch(final char value, final int fromIndex, final int toIndex) {
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
        TCharLink fromLink = this.getLinkAt(fromIndex);
        int to = toIndex;
        while (from < to) {
            final int mid = from + to >>> 1;
            final TCharLink middle = getLink(fromLink, from, mid);
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
    
    public int indexOf(final char value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final char value) {
        int count = offset;
        for (TCharLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                return count;
            }
            ++count;
        }
        return -1;
    }
    
    public int lastIndexOf(final char value) {
        return this.lastIndexOf(0, value);
    }
    
    public int lastIndexOf(final int offset, final char value) {
        if (this.isEmpty()) {
            return -1;
        }
        int last = -1;
        int count = offset;
        for (TCharLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                last = count;
            }
            ++count;
        }
        return last;
    }
    
    public boolean contains(final char value) {
        if (this.isEmpty()) {
            return false;
        }
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                return true;
            }
        }
        return false;
    }
    
    public TCharIterator iterator() {
        return new TCharIterator() {
            TCharLink l = TCharLinkedList.this.head;
            TCharLink current;
            
            public char next() {
                if (TCharLinkedList.no(this.l)) {
                    throw new NoSuchElementException();
                }
                final char ret = this.l.getValue();
                this.current = this.l;
                this.l = this.l.getNext();
                return ret;
            }
            
            public boolean hasNext() {
                return TCharLinkedList.got(this.l);
            }
            
            public void remove() {
                if (this.current == null) {
                    throw new IllegalStateException();
                }
                TCharLinkedList.this.removeLink(this.current);
                this.current = null;
            }
        };
    }
    
    public TCharList grep(final TCharProcedure condition) {
        final TCharList ret = new TCharLinkedList();
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            if (condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public TCharList inverseGrep(final TCharProcedure condition) {
        final TCharList ret = new TCharLinkedList();
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            if (!condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public char max() {
        char ret = '\0';
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            if (ret < l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public char min() {
        char ret = '\uffff';
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            if (ret > l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public char sum() {
        char sum = '\0';
        for (TCharLink l = this.head; got(l); l = l.getNext()) {
            sum += l.getValue();
        }
        return sum;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeChar(this.no_entry_value);
        out.writeInt(this.size);
        for (final char next : this) {
            out.writeChar(next);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.no_entry_value = in.readChar();
        for (int len = in.readInt(), i = 0; i < len; ++i) {
            this.add(in.readChar());
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
        final TCharLinkedList that = (TCharLinkedList)o;
        if (this.no_entry_value != that.no_entry_value) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        final TCharIterator iterator = this.iterator();
        final TCharIterator thatIterator = that.iterator();
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
        final TCharIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result = 31 * result + HashFunctions.hash(iterator.next());
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        final TCharIterator it = this.iterator();
        while (it.hasNext()) {
            final char next = it.next();
            buf.append(next);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    
    static class TCharLink
    {
        char value;
        TCharLink previous;
        TCharLink next;
        
        TCharLink(final char value) {
            this.value = value;
        }
        
        public char getValue() {
            return this.value;
        }
        
        public void setValue(final char value) {
            this.value = value;
        }
        
        public TCharLink getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(final TCharLink previous) {
            this.previous = previous;
        }
        
        public TCharLink getNext() {
            return this.next;
        }
        
        public void setNext(final TCharLink next) {
            this.next = next;
        }
    }
    
    class RemoveProcedure implements TCharProcedure
    {
        boolean changed;
        
        RemoveProcedure() {
            this.changed = false;
        }
        
        public boolean execute(final char value) {
            if (TCharLinkedList.this.remove(value)) {
                this.changed = true;
            }
            return true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
    }
}
