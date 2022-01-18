// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagInt extends NBTBase
{
    public int data;
    
    public NBTTagInt(final String s) {
        super(s);
    }
    
    public NBTTagInt(final String s, final int data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeInt(this.data);
    }
    
    void load(final DataInput dataInput) {
        this.data = dataInput.readInt();
    }
    
    public byte getTypeId() {
        return 3;
    }
    
    public String toString() {
        return "" + this.data;
    }
    
    public NBTBase clone() {
        return new NBTTagInt(this.getName(), this.data);
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagInt)o).data;
    }
    
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}
