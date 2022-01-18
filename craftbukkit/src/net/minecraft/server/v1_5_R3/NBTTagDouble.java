// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagDouble extends NBTBase
{
    public double data;
    
    public NBTTagDouble(final String s) {
        super(s);
    }
    
    public NBTTagDouble(final String s, final double data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeDouble(this.data);
    }
    
    void load(final DataInput dataInput) {
        this.data = dataInput.readDouble();
    }
    
    public byte getTypeId() {
        return 6;
    }
    
    public String toString() {
        return "" + this.data;
    }
    
    public NBTBase clone() {
        return new NBTTagDouble(this.getName(), this.data);
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagDouble)o).data;
    }
    
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
}
