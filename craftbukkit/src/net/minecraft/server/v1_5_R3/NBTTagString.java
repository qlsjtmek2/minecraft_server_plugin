// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagString extends NBTBase
{
    public String data;
    
    public NBTTagString(final String s) {
        super(s);
    }
    
    public NBTTagString(final String s, final String data) {
        super(s);
        this.data = data;
        if (data == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }
    
    void write(final DataOutput dataOutput) {
        dataOutput.writeUTF(this.data);
    }
    
    void load(final DataInput dataInput) {
        this.data = dataInput.readUTF();
    }
    
    public byte getTypeId() {
        return 8;
    }
    
    public String toString() {
        return "" + this.data;
    }
    
    public NBTBase clone() {
        return new NBTTagString(this.getName(), this.data);
    }
    
    public boolean equals(final Object o) {
        if (super.equals(o)) {
            final NBTTagString nbtTagString = (NBTTagString)o;
            return (this.data == null && nbtTagString.data == null) || (this.data != null && this.data.equals(nbtTagString.data));
        }
        return false;
    }
    
    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }
}
