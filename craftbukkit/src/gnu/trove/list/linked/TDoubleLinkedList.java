// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list.linked;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import gnu.trove.procedure.TDoubleProcedure;
import java.util.Random;
import gnu.trove.function.TDoubleFunction;
import java.util.Arrays;
import gnu.trove.TDoubleCollection;
import java.util.Iterator;
import java.util.Collection;
import gnu.trove.iterator.TDoubleIterator;
import java.io.Externalizable;
import gnu.trove.list.TDoubleList;

public class TDoubleLinkedList implements TDoubleList, Externalizable
{
    double no_entry_value;
    int size;
    TDoubleLink head;
    TDoubleLink tail;
    
    public TDoubleLinkedList() {
        this.head = null;
        this.tail = this.head;
    }
    
    public TDoubleLinkedList(final double no_entry_value) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = no_entry_value;
    }
    
    public TDoubleLinkedList(final TDoubleList list) {
        this.head = null;
        this.tail = this.head;
        this.no_entry_value = list.getNoEntryValue();
        for (final double next : list) {
            this.add(next);
        }
    }
    
    public double getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean add(final double val) {
        final TDoubleLink l = new TDoubleLink(val);
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
    
    public void add(final double[] vals) {
        for (final double val : vals) {
            this.add(val);
        }
    }
    
    public void add(final double[] vals, final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            final double val = vals[offset + i];
            this.add(val);
        }
    }
    
    public void insert(final int offset, final double value) {
        final TDoubleLinkedList tmp = new TDoubleLinkedList();
        tmp.add(value);
        this.insert(offset, tmp);
    }
    
    public void insert(final int offset, final double[] values) {
        this.insert(offset, link(values, 0, values.length));
    }
    
    public void insert(final int offset, final double[] values, final int valOffset, final int len) {
        this.insert(offset, link(values, valOffset, len));
    }
    
    void insert(final int offset, final TDoubleLinkedList tmp) {
        final TDoubleLink l = this.getLinkAt(offset);
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
            final TDoubleLink prev = l.getPrevious();
            l.getPrevious().setNext(tmp.head);
            tmp.tail.setNext(l);
            l.setPrevious(tmp.tail);
            tmp.head.setPrevious(prev);
        }
    }
    
    static TDoubleLinkedList link(final double[] values, final int valOffset, final int len) {
        final TDoubleLinkedList ret = new TDoubleLinkedList();
        for (int i = 0; i < len; ++i) {
            ret.add(values[valOffset + i]);
        }
        return ret;
    }
    
    public double get(final int offset) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TDoubleLink l = this.getLinkAt(offset);
        if (no(l)) {
            return this.no_entry_value;
        }
        return l.getValue();
    }
    
    public TDoubleLink getLinkAt(final int offset) {
        if (offset >= this.size()) {
            return null;
        }
        if (offset <= this.size() >>> 1) {
            return getLink(this.head, 0, offset, true);
        }
        return getLink(this.tail, this.size() - 1, offset, false);
    }
    
    private static TDoubleLink getLink(final TDoubleLink l, final int idx, final int offset) {
        return getLink(l, idx, offset, true);
    }
    
    private static TDoubleLink getLink(TDoubleLink l, final int idx, final int offset, final boolean next) {
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
    
    public double set(final int offset, final double val) {
        if (offset > this.size) {
            throw new IndexOutOfBoundsException("index " + offset + " exceeds size " + this.size);
        }
        final TDoubleLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new IndexOutOfBoundsException("at offset " + offset);
        }
        final double prev = l.getValue();
        l.setValue(val);
        return prev;
    }
    
    public void set(final int offset, final double[] values) {
        this.set(offset, values, 0, values.length);
    }
    
    public void set(final int offset, final double[] values, final int valOffset, final int length) {
        for (int i = 0; i < length; ++i) {
            final double value = values[valOffset + i];
            this.set(offset + i, value);
        }
    }
    
    public double replace(final int offset, final double val) {
        return this.set(offset, val);
    }
    
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean remove(final double value) {
        boolean changed = false;
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                changed = true;
                this.removeLink(l);
            }
        }
        return changed;
    }
    
    private void removeLink(final TDoubleLink l) {
        if (no(l)) {
            return;
        }
        --this.size;
        final TDoubleLink prev = l.getPrevious();
        final TDoubleLink next = l.getNext();
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
            if (!(o instanceof Double)) {
                return false;
            }
            final Double i = (Double)o;
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TDoubleCollection collection) {
        if (this.isEmpty()) {
            return false;
        }
        for (final double i : collection) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final double[] array) {
        if (this.isEmpty()) {
            return false;
        }
        for (final double i : array) {
            if (!this.contains(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Double> collection) {
        boolean ret = false;
        for (final Double v : collection) {
            if (this.add(v)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final TDoubleCollection collection) {
        boolean ret = false;
        for (final double i : collection) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean addAll(final double[] array) {
        boolean ret = false;
        for (final double i : array) {
            if (this.add(i)) {
                ret = true;
            }
        }
        return ret;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean modified = false;
        final TDoubleIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final TDoubleCollection collection) {
        boolean modified = false;
        final TDoubleIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (!collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean retainAll(final double[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TDoubleIterator iter = this.iterator();
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
        final TDoubleIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final TDoubleCollection collection) {
        boolean modified = false;
        final TDoubleIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (collection.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean removeAll(final double[] array) {
        Arrays.sort(array);
        boolean modified = false;
        final TDoubleIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (Arrays.binarySearch(array, iter.next()) >= 0) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public double removeAt(final int offset) {
        final TDoubleLink l = this.getLinkAt(offset);
        if (no(l)) {
            throw new ArrayIndexOutOfBoundsException("no elemenet at " + offset);
        }
        final double prev = l.getValue();
        this.removeLink(l);
        return prev;
    }
    
    public void remove(final int offset, final int length) {
        for (int i = 0; i < length; ++i) {
            this.removeAt(offset);
        }
    }
    
    public void transformValues(final TDoubleFunction function) {
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            l.setValue(function.execute(l.getValue()));
        }
    }
    
    public void reverse() {
        final TDoubleLink h = this.head;
        final TDoubleLink t = this.tail;
        TDoubleLink l = this.head;
        while (got(l)) {
            final TDoubleLink next = l.getNext();
            final TDoubleLink prev = l.getPrevious();
            final TDoubleLink tmp = l;
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
        final TDoubleLink start = this.getLinkAt(from);
        final TDoubleLink stop = this.getLinkAt(to);
        TDoubleLink tmp = null;
        final TDoubleLink tmpHead = start.getPrevious();
        TDoubleLink l = start;
        while (l != stop) {
            final TDoubleLink next = l.getNext();
            final TDoubleLink prev = l.getPrevious();
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
            final TDoubleLink l = this.getLinkAt(rand.nextInt(this.size()));
            this.removeLink(l);
            this.add(l.getValue());
        }
    }
    
    public TDoubleList subList(final int begin, final int end) {
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
        final TDoubleLinkedList ret = new TDoubleLinkedList();
        TDoubleLink tmp = this.getLinkAt(begin);
        for (int i = begin; i < end; ++i) {
            ret.add(tmp.getValue());
            tmp = tmp.getNext();
        }
        return ret;
    }
    
    public double[] toArray() {
        return this.toArray(new double[this.size], 0, this.size);
    }
    
    public double[] toArray(final int offset, final int len) {
        return this.toArray(new double[len], offset, 0, len);
    }
    
    public double[] toArray(final double[] dest) {
        return this.toArray(dest, 0, this.size);
    }
    
    public double[] toArray(final double[] dest, final int offset, final int len) {
        return this.toArray(dest, offset, 0, len);
    }
    
    public double[] toArray(final double[] dest, final int source_pos, final int dest_pos, final int len) {
        if (len == 0) {
            return dest;
        }
        if (source_pos < 0 || source_pos >= this.size()) {
            throw new ArrayIndexOutOfBoundsException(source_pos);
        }
        TDoubleLink tmp = this.getLinkAt(source_pos);
        for (int i = 0; i < len; ++i) {
            dest[dest_pos + i] = tmp.getValue();
            tmp = tmp.getNext();
        }
        return dest;
    }
    
    public boolean forEach(final TDoubleProcedure procedure) {
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            if (!procedure.execute(l.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachDescending(final TDoubleProcedure procedure) {
        for (TDoubleLink l = this.tail; got(l); l = l.getPrevious()) {
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
        final TDoubleList tmp = this.subList(fromIndex, toIndex);
        final double[] vals = tmp.toArray();
        Arrays.sort(vals);
        this.set(fromIndex, vals);
    }
    
    public void fill(final double val) {
        this.fill(0, this.size, val);
    }
    
    public void fill(final int fromIndex, final int toIndex, final double val) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        TDoubleLink l = this.getLinkAt(fromIndex);
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
    
    public int binarySearch(final double value) {
        return this.binarySearch(value, 0, this.size());
    }
    
    public int binarySearch(final double value, final int fromIndex, final int toIndex) {
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
        TDoubleLink fromLink = this.getLinkAt(fromIndex);
        int to = toIndex;
        while (from < to) {
            final int mid = from + to >>> 1;
            final TDoubleLink middle = getLink(fromLink, from, mid);
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
    
    public int indexOf(final double value) {
        return this.indexOf(0, value);
    }
    
    public int indexOf(final int offset, final double value) {
        int count = offset;
        for (TDoubleLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                return count;
            }
            ++count;
        }
        return -1;
    }
    
    public int lastIndexOf(final double value) {
        return this.lastIndexOf(0, value);
    }
    
    public int lastIndexOf(final int offset, final double value) {
        if (this.isEmpty()) {
            return -1;
        }
        int last = -1;
        int count = offset;
        for (TDoubleLink l = this.getLinkAt(offset); got(l.getNext()); l = l.getNext()) {
            if (l.getValue() == value) {
                last = count;
            }
            ++count;
        }
        return last;
    }
    
    public boolean contains(final double value) {
        if (this.isEmpty()) {
            return false;
        }
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            if (l.getValue() == value) {
                return true;
            }
        }
        return false;
    }
    
    public TDoubleIterator iterator() {
        return new TDoubleIterator() {
            TDoubleLink l = TDoubleLinkedList.this.head;
            TDoubleLink current;
            
            public double next() {
                if (TDoubleLinkedList.no(this.l)) {
                    throw new NoSuchElementException();
                }
                final double ret = this.l.getValue();
                this.current = this.l;
                this.l = this.l.getNext();
                return ret;
            }
            
            public boolean hasNext() {
                return TDoubleLinkedList.got(this.l);
            }
            
            public void remove() {
                if (this.current == null) {
                    throw new IllegalStateException();
                }
                TDoubleLinkedList.this.removeLink(this.current);
                this.current = null;
            }
        };
    }
    
    public TDoubleList grep(final TDoubleProcedure condition) {
        final TDoubleList ret = new TDoubleLinkedList();
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            if (condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public TDoubleList inverseGrep(final TDoubleProcedure condition) {
        final TDoubleList ret = new TDoubleLinkedList();
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            if (!condition.execute(l.getValue())) {
                ret.add(l.getValue());
            }
        }
        return ret;
    }
    
    public double max() {
        double ret = Double.NEGATIVE_INFINITY;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            if (ret < l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public double min() {
        double ret = Double.POSITIVE_INFINITY;
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            if (ret > l.getValue()) {
                ret = l.getValue();
            }
        }
        return ret;
    }
    
    public double sum() {
        double sum = 0.0;
        for (TDoubleLink l = this.head; got(l); l = l.getNext()) {
            sum += l.getValue();
        }
        return sum;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeDouble(this.no_entry_value);
        out.writeInt(this.size);
        for (final double next : this) {
            out.writeDouble(next);
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.no_entry_value = in.readDouble();
        for (int len = in.readInt(), i = 0; i < len; ++i) {
            this.add(in.readDouble());
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
        final TDoubleLinkedList that = (TDoubleLinkedList)o;
        if (this.no_entry_value != that.no_entry_value) {
            return false;
        }
        if (this.size != that.size) {
            return false;
        }
        final TDoubleIterator iterator = this.iterator();
        final TDoubleIterator thatIterator = that.iterator();
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
        final TDoubleIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result = 31 * result + HashFunctions.hash(iterator.next());
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        final TDoubleIterator it = this.iterator();
        while (it.hasNext()) {
            final double next = it.next();
            buf.append(next);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    
    static class TDoubleLink
    {
        double value;
        TDoubleLink previous;
        TDoubleLink next;
        
        TDoubleLink(final double value) {
            this.value = value;
        }
        
        public double getValue() {
            return this.value;
        }
        
        public void setValue(final double value) {
            this.value = value;
        }
        
        public TDoubleLink getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(final TDoubleLink previous) {
            this.previous = previous;
        }
        
        public TDoubleLink getNext() {
            return this.next;
        }
        
        public void setNext(final TDoubleLink next) {
            this.next = next;
        }
    }
    
    class RemoveProcedure implements TDoubleProcedure
    {
        boolean changed;
        
        RemoveProcedure() {
            this.changed = false;
        }
        
        public boolean execute(final double value) {
            if (TDoubleLinkedList.this.remove(value)) {
                this.changed = true;
            }
            return true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
    }
}
