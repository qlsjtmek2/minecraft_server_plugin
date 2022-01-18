// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Arrays;
import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagIntArray extends NBTBase
{
    public int[] data;
    
    public NBTTagIntArray(final String s) {
        super(s);
    }
    
    public NBTTagIntArray(final String s, final int[] data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeInt(this.data.length);
        for (int i = 0; i < this.data.length; ++i) {
            dataOutput.writeInt(this.data[i]);
        }
    }
    
    void load(final DataInput dataInput) {
        final int int1 = dataInput.readInt();
        this.data = new int[int1];
        for (int i = 0; i < int1; ++i) {
            this.data[i] = dataInput.readInt();
        }
    }
    
    public byte getTypeId() {
        return 11;
    }
    
    public String toString() {
        return "[" + this.data.length + " bytes]";
    }
    
    public NBTBase clone() {
        final int[] array = new int[this.data.length];
        System.arraycopy(this.data, 0, array, 0, this.data.length);
        return new NBTTagIntArray(this.getName(), array);
    }
    
    public boolean equals(final Object o) {
        if (super.equals(o)) {
            final NBTTagIntArray nbtTagIntArray = (NBTTagIntArray)o;
            return (this.data == null && nbtTagIntArray.data == null) || (this.data != null && Arrays.equals(this.data, nbtTagIntArray.data));
        }
        return false;
    }
    
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }
}
