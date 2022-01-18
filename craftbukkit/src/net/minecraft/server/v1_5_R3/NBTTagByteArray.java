// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Arrays;
import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagByteArray extends NBTBase
{
    public byte[] data;
    
    public NBTTagByteArray(final String s) {
        super(s);
    }
    
    public NBTTagByteArray(final String s, final byte[] data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeInt(this.data.length);
        dataOutput.write(this.data);
    }
    
    void load(final DataInput dataInput) {
        dataInput.readFully(this.data = new byte[dataInput.readInt()]);
    }
    
    public byte getTypeId() {
        return 7;
    }
    
    public String toString() {
        return "[" + this.data.length + " bytes]";
    }
    
    public NBTBase clone() {
        final byte[] array = new byte[this.data.length];
        System.arraycopy(this.data, 0, array, 0, this.data.length);
        return new NBTTagByteArray(this.getName(), array);
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && Arrays.equals(this.data, ((NBTTagByteArray)o).data);
    }
    
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }
}
