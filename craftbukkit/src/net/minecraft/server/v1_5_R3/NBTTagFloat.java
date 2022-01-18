// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagFloat extends NBTBase
{
    public float data;
    
    public NBTTagFloat(final String s) {
        super(s);
    }
    
    public NBTTagFloat(final String s, final float data) {
        super(s);
        this.data = data;
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeFloat(this.data);
    }
    
    void load(final DataInput dataInput) {
        this.data = dataInput.readFloat();
    }
    
    public byte getTypeId() {
        return 5;
    }
    
    public String toString() {
        return "" + this.data;
    }
    
    public NBTBase clone() {
        return new NBTTagFloat(this.getName(), this.data);
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagFloat)o).data;
    }
    
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
}
