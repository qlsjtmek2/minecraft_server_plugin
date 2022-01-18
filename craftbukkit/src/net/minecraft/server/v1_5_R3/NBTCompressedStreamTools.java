// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.OutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.util.zip.GZIPInputStream;
import java.io.InputStream;

public class NBTCompressedStreamTools
{
    public static NBTTagCompound a(final InputStream inputStream) {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));
        try {
            return a((DataInput)dataInputStream);
        }
        finally {
            dataInputStream.close();
        }
    }
    
    public static void a(final NBTTagCompound nbtTagCompound, final OutputStream outputStream) {
        final DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream));
        try {
            a(nbtTagCompound, (DataOutput)dataOutputStream);
        }
        finally {
            dataOutputStream.close();
        }
    }
    
    public static NBTTagCompound a(final byte[] array) {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(array))));
        try {
            return a((DataInput)dataInputStream);
        }
        finally {
            dataInputStream.close();
        }
    }
    
    public static byte[] a(final NBTTagCompound nbtTagCompound) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(byteArrayOutputStream));
        try {
            a(nbtTagCompound, (DataOutput)dataOutputStream);
        }
        finally {
            dataOutputStream.close();
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public static NBTTagCompound a(final DataInput dataInput) {
        final NBTBase b = NBTBase.b(dataInput);
        if (b instanceof NBTTagCompound) {
            return (NBTTagCompound)b;
        }
        throw new IOException("Root tag must be a named compound tag");
    }
    
    public static void a(final NBTTagCompound nbtTagCompound, final DataOutput dataOutput) {
        NBTBase.a(nbtTagCompound, dataOutput);
    }
}
