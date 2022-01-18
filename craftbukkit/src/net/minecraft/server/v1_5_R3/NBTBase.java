// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public abstract class NBTBase
{
    public static final String[] b;
    private String name;
    
    abstract void write(final DataOutput p0);
    
    abstract void load(final DataInput p0);
    
    public abstract byte getTypeId();
    
    protected NBTBase(final String name) {
        if (name == null) {
            this.name = "";
        }
        else {
            this.name = name;
        }
    }
    
    public NBTBase setName(final String name) {
        if (name == null) {
            this.name = "";
        }
        else {
            this.name = name;
        }
        return this;
    }
    
    public String getName() {
        if (this.name == null) {
            return "";
        }
        return this.name;
    }
    
    public static NBTBase b(final DataInput dataInput) {
        final byte byte1 = dataInput.readByte();
        if (byte1 == 0) {
            return new NBTTagEnd();
        }
        final String utf = dataInput.readUTF();
        final NBTBase tag = createTag(byte1, utf);
        try {
            tag.load(dataInput);
        }
        catch (IOException throwable) {
            final CrashReport a = CrashReport.a(throwable, "Loading NBT data");
            final CrashReportSystemDetails a2 = a.a("NBT Tag");
            a2.a("Tag name", utf);
            a2.a("Tag type", byte1);
            throw new ReportedException(a);
        }
        return tag;
    }
    
    public static void a(final NBTBase nbtBase, final DataOutput dataOutput) {
        dataOutput.writeByte(nbtBase.getTypeId());
        if (nbtBase.getTypeId() == 0) {
            return;
        }
        dataOutput.writeUTF(nbtBase.getName());
        nbtBase.write(dataOutput);
    }
    
    public static NBTBase createTag(final byte b, final String paramString) {
        switch (b) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte(paramString);
            }
            case 2: {
                return new NBTTagShort(paramString);
            }
            case 3: {
                return new NBTTagInt(paramString);
            }
            case 4: {
                return new NBTTagLong(paramString);
            }
            case 5: {
                return new NBTTagFloat(paramString);
            }
            case 6: {
                return new NBTTagDouble(paramString);
            }
            case 7: {
                return new NBTTagByteArray(paramString);
            }
            case 11: {
                return new NBTTagIntArray(paramString);
            }
            case 8: {
                return new NBTTagString(paramString);
            }
            case 9: {
                return new NBTTagList(paramString);
            }
            case 10: {
                return new NBTTagCompound(paramString);
            }
            default: {
                return null;
            }
        }
    }
    
    public static String getTagName(final byte b) {
        switch (b) {
            case 0: {
                return "TAG_End";
            }
            case 1: {
                return "TAG_Byte";
            }
            case 2: {
                return "TAG_Short";
            }
            case 3: {
                return "TAG_Int";
            }
            case 4: {
                return "TAG_Long";
            }
            case 5: {
                return "TAG_Float";
            }
            case 6: {
                return "TAG_Double";
            }
            case 7: {
                return "TAG_Byte_Array";
            }
            case 11: {
                return "TAG_Int_Array";
            }
            case 8: {
                return "TAG_String";
            }
            case 9: {
                return "TAG_List";
            }
            case 10: {
                return "TAG_Compound";
            }
            default: {
                return "UNKNOWN";
            }
        }
    }
    
    public abstract NBTBase clone();
    
    public boolean equals(final Object o) {
        if (!(o instanceof NBTBase)) {
            return false;
        }
        final NBTBase nbtBase = (NBTBase)o;
        return this.getTypeId() == nbtBase.getTypeId() && (this.name != null || nbtBase.name == null) && (this.name == null || nbtBase.name != null) && (this.name == null || this.name.equals(nbtBase.name));
    }
    
    public int hashCode() {
        return this.name.hashCode() ^ this.getTypeId();
    }
    
    static {
        b = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
    }
}
