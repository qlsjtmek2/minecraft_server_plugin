// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagShort extends NBTBase
{
    public short data;
    
    public NBTTagShort(final String s) {
        super(s);
    }
    
    public NBTTagShort(final String s, final short data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeShort(this.data);
    }
    
    void load(final DataInput dataInput) {
        this.data = dataInput.readShort();
    }
    
    public byte getTypeId() {
        return 2;
    }
    
    public String toString() {
        return "" + this.data;
    }
    
    public NBTBase clone() {
        return new NBTTagShort(this.getName(), this.data);
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagShort)o).data;
    }
    
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}
