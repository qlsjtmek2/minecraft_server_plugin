// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.hawtjni.runtime;

import java.util.List;
import java.util.Collections;
import java.io.PrintStream;
import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public class NativeStats
{
    private final HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot;
    
    public NativeStats(final StatsInterface... classes) {
        this(Arrays.asList(classes));
    }
    
    public NativeStats(final Collection<StatsInterface> classes) {
        this(snapshot(classes));
    }
    
    private NativeStats(final HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot) {
        this.snapshot = snapshot;
    }
    
    public void reset() {
        for (final ArrayList<NativeFunction> functions : this.snapshot.values()) {
            for (final NativeFunction function : functions) {
                function.reset();
            }
        }
    }
    
    public void update() {
        for (final Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            final StatsInterface si = entry.getKey();
            for (final NativeFunction function : entry.getValue()) {
                function.setCounter(si.functionCounter(function.getOrdinal()));
            }
        }
    }
    
    public NativeStats snapshot() {
        final NativeStats copy = this.copy();
        copy.update();
        return copy;
    }
    
    public NativeStats copy() {
        final HashMap<StatsInterface, ArrayList<NativeFunction>> rc = new HashMap<StatsInterface, ArrayList<NativeFunction>>(this.snapshot.size() * 2);
        for (final Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            final ArrayList<NativeFunction> list = new ArrayList<NativeFunction>(entry.getValue().size());
            for (final NativeFunction function : entry.getValue()) {
                list.add(function.copy());
            }
            rc.put(entry.getKey(), list);
        }
        return new NativeStats(rc);
    }
    
    public NativeStats diff() {
        final HashMap<StatsInterface, ArrayList<NativeFunction>> rc = new HashMap<StatsInterface, ArrayList<NativeFunction>>(this.snapshot.size() * 2);
        for (final Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            final StatsInterface si = entry.getKey();
            final ArrayList<NativeFunction> list = new ArrayList<NativeFunction>(entry.getValue().size());
            for (final NativeFunction original : entry.getValue()) {
                final NativeFunction copy = original.copy();
                copy.setCounter(si.functionCounter(copy.getOrdinal()));
                copy.subtract(original);
                list.add(copy);
            }
            rc.put(si, list);
        }
        return new NativeStats(rc);
    }
    
    public void dump(final PrintStream ps) {
        boolean firstSI = true;
        for (final Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            final StatsInterface si = entry.getKey();
            final ArrayList<NativeFunction> funcs = entry.getValue();
            int total = 0;
            for (final NativeFunction func : funcs) {
                total += func.getCounter();
            }
            if (!firstSI) {
                ps.print(", ");
            }
            firstSI = false;
            ps.print("[");
            if (total > 0) {
                ps.println("{ ");
                ps.println("  \"class\": \"" + si.getNativeClass() + "\",");
                ps.println("  \"total\": " + total + ", ");
                ps.print("  \"functions\": {");
                boolean firstFunc = true;
                for (final NativeFunction func2 : funcs) {
                    if (func2.getCounter() > 0) {
                        if (!firstFunc) {
                            ps.print(",");
                        }
                        firstFunc = false;
                        ps.println();
                        ps.print("    \"" + func2.getName() + "\": " + func2.getCounter());
                    }
                }
                ps.println();
                ps.println("  }");
                ps.print("}");
            }
            ps.print("]");
        }
    }
    
    private static HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot(final Collection<StatsInterface> classes) {
        final HashMap<StatsInterface, ArrayList<NativeFunction>> rc = new HashMap<StatsInterface, ArrayList<NativeFunction>>();
        for (final StatsInterface sc : classes) {
            final int count = sc.functionCount();
            final ArrayList<NativeFunction> functions = new ArrayList<NativeFunction>(count);
            for (int i = 0; i < count; ++i) {
                final String name = sc.functionName(i);
                functions.add(new NativeFunction(i, name, 0));
            }
            Collections.sort(functions);
            rc.put(sc, functions);
        }
        return rc;
    }
    
    public static class NativeFunction implements Comparable<NativeFunction>
    {
        private final int ordinal;
        private final String name;
        private int counter;
        
        public NativeFunction(final int ordinal, final String name, final int callCount) {
            this.ordinal = ordinal;
            this.name = name;
            this.counter = callCount;
        }
        
        void subtract(final NativeFunction func) {
            this.counter -= func.counter;
        }
        
        public int getCounter() {
            return this.counter;
        }
        
        public void setCounter(final int counter) {
            this.counter = counter;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getOrdinal() {
            return this.ordinal;
        }
        
        public int compareTo(final NativeFunction func) {
            return func.counter - this.counter;
        }
        
        public void reset() {
            this.counter = 0;
        }
        
        public NativeFunction copy() {
            return new NativeFunction(this.ordinal, this.name, this.counter);
        }
    }
    
    public interface StatsInterface
    {
        String getNativeClass();
        
        int functionCount();
        
        String functionName(final int p0);
        
        int functionCounter(final int p0);
    }
}
