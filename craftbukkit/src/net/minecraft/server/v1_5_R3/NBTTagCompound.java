// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;
import java.util.Collection;
import java.io.DataInput;
import java.util.Iterator;
import java.io.DataOutput;
import java.util.HashMap;
import java.util.Map;

public class NBTTagCompound extends NBTBase
{
    private Map map;
    
    public NBTTagCompound() {
        super("");
        this.map = new HashMap();
    }
    
    public NBTTagCompound(final String s) {
        super(s);
        this.map = new HashMap();
    }
    
    void write(final DataOutput dataOutput) {
        final Iterator<NBTBase> iterator = this.map.values().iterator();
        while (iterator.hasNext()) {
            NBTBase.a(iterator.next(), dataOutput);
        }
        dataOutput.writeByte(0);
    }
    
    void load(final DataInput dataInput) {
        this.map.clear();
        NBTBase b;
        while ((b = NBTBase.b(dataInput)).getTypeId() != 0) {
            this.map.put(b.getName(), b);
        }
    }
    
    public Collection c() {
        return this.map.values();
    }
    
    public byte getTypeId() {
        return 10;
    }
    
    public void set(final String name, final NBTBase nbtBase) {
        this.map.put(name, nbtBase.setName(name));
    }
    
    public void setByte(final String s, final byte b) {
        this.map.put(s, new NBTTagByte(s, b));
    }
    
    public void setShort(final String s, final short n) {
        this.map.put(s, new NBTTagShort(s, n));
    }
    
    public void setInt(final String s, final int n) {
        this.map.put(s, new NBTTagInt(s, n));
    }
    
    public void setLong(final String s, final long n) {
        this.map.put(s, new NBTTagLong(s, n));
    }
    
    public void setFloat(final String s, final float n) {
        this.map.put(s, new NBTTagFloat(s, n));
    }
    
    public void setDouble(final String s, final double n) {
        this.map.put(s, new NBTTagDouble(s, n));
    }
    
    public void setString(final String s, final String s2) {
        this.map.put(s, new NBTTagString(s, s2));
    }
    
    public void setByteArray(final String s, final byte[] array) {
        this.map.put(s, new NBTTagByteArray(s, array));
    }
    
    public void setIntArray(final String s, final int[] array) {
        this.map.put(s, new NBTTagIntArray(s, array));
    }
    
    public void setCompound(final String name, final NBTTagCompound nbtTagCompound) {
        this.map.put(name, nbtTagCompound.setName(name));
    }
    
    public void setBoolean(final String s, final boolean b) {
        this.setByte(s, (byte)(b ? 1 : 0));
    }
    
    public NBTBase get(final String s) {
        return this.map.get(s);
    }
    
    public boolean hasKey(final String s) {
        return this.map.containsKey(s);
    }
    
    public byte getByte(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return 0;
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 1, ex));
        }
    }
    
    public short getShort(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return 0;
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 2, ex));
        }
    }
    
    public int getInt(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return 0;
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 3, ex));
        }
    }
    
    public long getLong(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return 0L;
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 4, ex));
        }
    }
    
    public float getFloat(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return 0.0f;
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 5, ex));
        }
    }
    
    public double getDouble(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return 0.0;
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 6, ex));
        }
    }
    
    public String getString(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return "";
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 8, ex));
        }
    }
    
    public byte[] getByteArray(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return new byte[0];
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 7, ex));
        }
    }
    
    public int[] getIntArray(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return new int[0];
            }
            return this.map.get(s).data;
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 11, ex));
        }
    }
    
    public NBTTagCompound getCompound(final String s) {
        try {
            if (!this.map.containsKey(s)) {
                return new NBTTagCompound(s);
            }
            return this.map.get(s);
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(s, 10, ex));
        }
    }
    
    public NBTTagList getList(final String paramString) {
        try {
            if (!this.map.containsKey(paramString)) {
                return new NBTTagList(paramString);
            }
            return this.map.get(paramString);
        }
        catch (ClassCastException ex) {
            throw new ReportedException(this.a(paramString, 9, ex));
        }
    }
    
    public boolean getBoolean(final String s) {
        return this.getByte(s) != 0;
    }
    
    public void remove(final String s) {
        this.map.remove(s);
    }
    
    public String toString() {
        String s = this.getName() + ":[";
        for (final String s2 : this.map.keySet()) {
            s = s + s2 + ":" + this.map.get(s2) + ",";
        }
        return s + "]";
    }
    
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    private CrashReport a(final String s, final int n, final ClassCastException throwable) {
        final CrashReport a = CrashReport.a(throwable, "Reading NBT data");
        final CrashReportSystemDetails a2 = a.a("Corrupt NBT tag", 1);
        a2.a("Tag type found", new CrashReportCorruptNBTTag(this, s));
        a2.a("Tag type expected", new CrashReportCorruptNBTTag2(this, n));
        a2.a("Tag name", s);
        if (this.getName() != null && this.getName().length() > 0) {
            a2.a("Tag parent", this.getName());
        }
        return a;
    }
    
    public NBTBase clone() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound(this.getName());
        for (final String s : this.map.keySet()) {
            nbtTagCompound.set(s, ((NBTBase)this.map.get(s)).clone());
        }
        return nbtTagCompound;
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && this.map.entrySet().equals(((NBTTagCompound)o).map.entrySet());
    }
    
    public int hashCode() {
        return super.hashCode() ^ this.map.hashCode();
    }
}
