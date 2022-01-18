// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutput;
import java.io.DataInput;

public class NBTTagEnd extends NBTBase
{
    public NBTTagEnd() {
        super(null);
    }
    
    void load(final DataInput dataInput) {
    }
    
    void write(final DataOutput dataOutput) {
    }
    
    public byte getTypeId() {
        return 0;
    }
    
    public String toString() {
        return "END";
    }
    
    public NBTBase clone() {
        return new NBTTagEnd();
    }
}
