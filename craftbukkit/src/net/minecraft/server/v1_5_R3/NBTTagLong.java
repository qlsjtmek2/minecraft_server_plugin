// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagLong extends NBTBase
{
    public long data;
    
    public NBTTagLong(final String s) {
        super(s);
    }
    
    public NBTTagLong(final String s, final long data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeLong(this.data);
    }
    
    void load(final DataInput dataInput) {
        this.data = dataInput.readLong();
    }
    
    public byte getTypeId() {
        return 4;
    }
    
    public String toString() {
        return "" + this.data;
    }
    
    public NBTBase clone() {
        return new NBTTagLong(this.getName(), this.data);
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagLong)o).data;
    }
    
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }
}
