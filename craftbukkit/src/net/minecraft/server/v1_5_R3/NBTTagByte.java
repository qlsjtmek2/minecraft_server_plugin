// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagByte extends NBTBase
{
    public byte data;
    
    public NBTTagByte(final String s) {
        super(s);
    }
    
    public NBTTagByte(final String s, final byte data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeByte(this.data);
    }
    
    void load(final DataInput dataInput) {
        this.data = dataInput.readByte();
    }
    
    public byte getTypeId() {
        return 1;
    }
    
    public String toString() {
        return "" + this.data;
    }
    
    public NBTBase clone() {
        return new NBTTagByte(this.getName(), this.data);
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagByte)o).data;
    }
    
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}
